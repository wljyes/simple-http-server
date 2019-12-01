package handler;

import entity.RequestEntity;
import entity.ResponseEntity;
import factory.ResponseEntityFactory;
import org.dom4j.DocumentException;
import servlet.Servlet;
import util.ConfigUtils;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ServletRequestHandler implements HttpRequestHandler {
    private Map<String, String> servletNameMap = new HashMap<>(); // path -> servlet-name
    private Map<String, Servlet> servletMap = new HashMap<>(); //servlet-name -> servlet
    @Override
    public boolean canHandle(String uri) {
        return servletNameMap.containsKey("/" + uri);
    }

    public ServletRequestHandler() {
        try {
            servletNameMap.putAll(ConfigUtils.getServletMapFromXML("servlet-config.xml"));
        } catch (DocumentException e) {
            System.out.println("解析servlet-config配置文件出错");
            e.printStackTrace();
        }
    }

    @Override
    public ResponseEntity handle(RequestEntity requestEntity) {
        //通过反射，执行servlet的方法，获取返回数据
        String servletName = servletNameMap.get("/" + requestEntity.getUri()); //根据路径获取对应的servlet名字
        //如果servlet已经加载过，则直接用，servlet只实例化一次
        if (servletMap.containsKey(servletName)) {
            return servletMap.get(servletName).service(requestEntity);
        }
        //没有实例化过的servlet实例化
        try {
            Class servletClass =  Class.forName(servletName);
            if (Servlet.class.isAssignableFrom(servletClass)) { //isAssignableFrom检查调用者所表示的类是否为参数所表示的类的接口或超类
                //注意Class对象之间并无原对象间的继承关系
                Servlet servlet = (Servlet) servletClass.newInstance();
                //缓存servlet对象
                servletMap.put(servletName, servlet);
                return servlet.service(requestEntity);
            } else { //映射的类未继承于Servlet，抛出异常
                throw new RuntimeException(new ClassCastException(servletName + "未继承于Servlet类"));
            }
        } catch (ClassNotFoundException e) {
            System.out.println("没有对应的Servlet类:" + servletName);
            e.printStackTrace();
            return ResponseEntityFactory.responseEntity_404(Calendar.getInstance());
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }
}

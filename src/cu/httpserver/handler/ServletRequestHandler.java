package cu.httpserver.handler;

import cu.httpserver.annotation.RequestMapping;
import cu.httpserver.entity.RequestEntity;
import cu.httpserver.entity.ResponseEntity;
import cu.httpserver.factory.ResponseEntityFactory;
import org.dom4j.DocumentException;
import cu.httpserver.servlet.Servlet;
import cu.httpserver.util.ConfigUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class ServletRequestHandler implements HttpRequestHandler {
    private Map<String, String> servletNameMap = new HashMap<>(); // path -> cu.httpserver.servlet-name
    private Map<String, Servlet> servletMap = new HashMap<>(); //cu.httpserver.servlet-name -> cu.httpserver.servlet
    @Override
    public boolean canHandle(String uri) {
        return servletNameMap.containsKey("/" + uri);
    }

    public ServletRequestHandler() {
        try {
            boolean enableAnnotation = Boolean
                    .parseBoolean(ConfigUtils.getPropertyValue("enableAnnotationMapping", "http-server.properties", "false"));
            if (enableAnnotation) { //注解驱动路由,启动时加载
                loadRoutesFromAnnotation();
            } else {
                loadRoutesFromXML(); //xml配置驱动路由，懒加载servlet实例
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadRoutesFromXML() {
        try {
            servletNameMap.putAll(ConfigUtils.getServletMapFromXML("servlet-config.xml"));
        } catch (DocumentException e) {
            System.out.println("解析servlet-config配置文件出错");
            e.printStackTrace();
        }
    }

    private void loadRoutesFromAnnotation() {
        try {
            String scanPackage = ConfigUtils.getPropertyValue("scanPackage", "http-server.properties");
            if (scanPackage != null) {
                Enumeration<URL> enumeration = Thread.currentThread().getContextClassLoader().getResources(scanPackage.replaceAll("\\.", "/"));
                while (enumeration.hasMoreElements()) {
                    URL resource = enumeration.nextElement();
                    String protocol = resource.getProtocol();
                    if (protocol.equalsIgnoreCase("file")) {
                        loadRoutesFromAnnotation(scanPackage, resource.getPath());
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadRoutesFromAnnotation(String packageName, String packagePath) {
        File[] files = new File(packagePath).listFiles(file -> file.isDirectory() || file.getName().endsWith(".class"));
        if (files != null) {
            for (File file : files) {
                String filename = file.getName();
                if (file.isDirectory()) {
                    packageName = packageName + "." + filename;
                    packagePath = packagePath + "/" + filename;
                    loadRoutesFromAnnotation(packageName, packagePath);
                } else {
                    filename = packageName + "." + filename.substring(0, filename.lastIndexOf("."));
                    try {
                        Class<?> aClass = Class.forName(filename);
                        if (Servlet.class.isAssignableFrom(aClass) && !Servlet.class.equals(aClass)) {
                            RequestMapping requestMapping = aClass.getAnnotation(RequestMapping.class);
                            if (requestMapping != null) {
                                String uri = requestMapping.uri();
                                servletNameMap.put(uri, aClass.getSimpleName());
                                servletMap.put(aClass.getSimpleName(), (Servlet) aClass.newInstance());
                            }
                        }
                    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
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

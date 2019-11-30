package handler;

import entity.RequestEntity;
import entity.ResponseEntity;
import servlet.Servlet;

import java.util.HashMap;
import java.util.Map;

public class ServletRequestHandler implements HttpRequestHandler {
    private Map<String, String> servletNameMap = new HashMap<>();
    private Map<String, Servlet> servletMap = new HashMap<>();
    @Override
    public boolean canHandle(String uri) {
        if (uri.contains(".")) {
            return false;
        }
        return true;
    }

    public ServletRequestHandler() {
        //todo:xml文件解析，装配servletNameMap，懒加载servletMap
    }

    @Override
    public ResponseEntity handle(RequestEntity requestEntity) {
        //todo: 读取配置文件或扫描注解，搜集mapping的servlet
        //通过反射，执行servlet的方法，获取返回数据
        return null;
    }
}

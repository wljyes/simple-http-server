package cu.httpserver.factory;

import cu.httpserver.entity.RequestEntity;
import cu.httpserver.entity.ResponseEntity;
import cu.httpserver.handler.HttpRequestHandler;
import cu.httpserver.handler.ServletRequestHandler;
import cu.httpserver.handler.StaticResourceRequestHandler;
import cu.httpserver.util.ConfigUtils;

import java.io.FileNotFoundException;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

public class RequestHandleList {
    private List<HttpRequestHandler> handlerList = new LinkedList<>();
    private static RequestHandleList requestHandleList;

    private RequestHandleList() {
        //读配置文件
        String propName = "http-server.properties";
        try {
            Properties prop = ConfigUtils.getProperties(propName);
            boolean enableStatic = Boolean.parseBoolean(prop.getProperty("enableStaticResource", "true"));
            boolean enableServlet = Boolean.parseBoolean(prop.getProperty("enableServlet", "false"));

            if (enableStatic) {
                handlerList.add(new StaticResourceRequestHandler());
            }
            if (enableServlet) {
                handlerList.add(new ServletRequestHandler());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //默认处理器，错误处理
        handlerList.add(new HttpRequestHandler() {
            @Override
            public boolean canHandle(String uri) {
                return true;
            }

            @Override
            public ResponseEntity handle(RequestEntity requestEntity) {
                //todo: handle error
                return ResponseEntityFactory.responseEntity_404(Calendar.getInstance());
            }
        });
    }

    //类初始化时加载handleList，单例化，读取配置文件选择加载那些handler
    static {
        requestHandleList = new RequestHandleList();
    }

    public static RequestHandleList getRequestHandleList() {
        if (requestHandleList != null) {
            return requestHandleList;
        }
        requestHandleList = new RequestHandleList();

        return requestHandleList;
    }

    public ResponseEntity handleRequest(RequestEntity entity) {
        ResponseEntity responseEntity;
        for (HttpRequestHandler handler : handlerList) {
            if (handler.canHandle(entity.getUri())) {
                responseEntity = handler.handle(entity);
                return responseEntity;
            }
        }
        return null;
    }
}

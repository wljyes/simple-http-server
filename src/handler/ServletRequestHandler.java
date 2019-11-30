package handler;

import entity.RequestEntity;
import entity.ResponseEntity;

public class ServletRequestHandler implements HttpRequestHandler {
    @Override
    public boolean canHandle(String uri) {
        if (uri.contains(".")) {
            return false;
        }
        return true;
    }

    @Override
    public ResponseEntity handle(RequestEntity requestEntity) {
        //todo: 读取配置文件或扫描注解，搜集mapping的servlet
        //通过反射，执行servlet的方法，获取返回数据
        return null;
    }
}

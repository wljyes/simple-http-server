package cu.httpserver.handler;

import cu.httpserver.entity.RequestEntity;
import cu.httpserver.entity.ResponseEntity;

/**
 * 用于解耦服务器请求的接收与处理
 */
public interface HttpRequestHandler {

    /**
     * 用于责任链模式，判断当前handler是否能处理这个请求
     * @param uri http请求头中的uri
     * @return 若能处理则返回<code>true</code>, 否则返回<code>false</code>
     */
    boolean canHandle(String uri);

    /**
     * 处理http请求，返回http response
     * @param requestEntity http请求报文实体
     * @return http响应报文实体
     */
     ResponseEntity handle(RequestEntity requestEntity);

}

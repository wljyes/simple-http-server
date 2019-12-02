package factory;

import entity.ResponseEntity;
import util.StringUtils;

import java.util.Calendar;

//todo:建立一个表，根据响应码返回response通用方法
public class ResponseEntityFactory {
    public static String HTTP_VERSION = "HTTP/1.1";

    private static final int LENGTH_403 = 47;
    private static final String CONTENT_403 = "<html><body><h1>403 FORBIDDEN</h1><body></html>";
    private static final int LENGTH_404 = 48;
    private static final String CONTENT_404 = "<html><body><h1>404 NOT FOUND</h1></body></html>";
    private static final int LENGTH_405 = 57;
    private static final String CONTENT_405 = "<html><body><h1>405 Method Not Allowed</h1></body></html>";

    private static final ResponseEntity ENTITY_403_PROTOTYPE = ResponseEntityBuilder.builder().
            httpVersion("HTTP/1.1").status("403", "Forbidden").contentLength(LENGTH_403)
            .contentType("text/html").body(CONTENT_403).build();

    private static final ResponseEntity ENTITY_404_PROTOTYPE = ResponseEntityBuilder.builder().
            httpVersion(HTTP_VERSION).status("404", "Not Found").contentLength(LENGTH_404)
            .contentType("text/html").body(CONTENT_404).build();

    private static final ResponseEntity ENTITY_405_PROTOTYPE = ResponseEntityBuilder.builder()
            .httpVersion(HTTP_VERSION).status("405", "Method Not Allowed").contentLength(LENGTH_405)
            .contentType("text/html").body(CONTENT_405).build();

    public static ResponseEntity responseEntity(ResponseEntity prototype) {
        ResponseEntity responseEntity = new ResponseEntity(prototype);
        responseEntity.getHeaders().put("Date", StringUtils.getHttpFormatDate(Calendar.getInstance()));
        return responseEntity;
    }

    public static ResponseEntity responseEntity_403(Calendar calendar) {
        ResponseEntity responseEntity = new ResponseEntity(ENTITY_403_PROTOTYPE);
        responseEntity.getHeaders().put("Date", StringUtils.getHttpFormatDate(calendar));
        return responseEntity;
    }

    public static ResponseEntity responseEntity_404(Calendar calendar) {
        ResponseEntity responseEntity = new ResponseEntity(ENTITY_404_PROTOTYPE);
        responseEntity.getHeaders().put("Date", StringUtils.getHttpFormatDate(calendar));
        return responseEntity;
    }

    public static ResponseEntity responseEntity_405(Calendar calendar) {
        ResponseEntity responseEntity = new ResponseEntity(ENTITY_405_PROTOTYPE);
        responseEntity.getHeaders().put("Date", StringUtils.getHttpFormatDate(calendar));
        return responseEntity;
    }

    public static ResponseEntityBuilder responseEntityBuilder_200() {
        return ResponseEntityBuilder.builder().httpVersion(HTTP_VERSION)
                .status("200", "OK");
    }
}

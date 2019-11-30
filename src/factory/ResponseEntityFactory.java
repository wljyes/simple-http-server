package factory;

import entity.ResponseEntity;
import util.StringUtils;

import java.util.Calendar;

public class ResponseEntityFactory {
    private static String HTTP_VERSION = "HTTP/1.1";

    private static final int LENGTH_403 = 47;
    public static final String CONTENT_403 = "<html><body><h1>403 FORBIDDEN</h1><body></html>";
    public static final int LENGTH_404 = 48;
    public static final String CONTENT_404 = "<html><body><h1>404 NOT FOUND</h1></body></html>";

    public static final ResponseEntity ENTITY_403_PROTOTYPE = ResponseEntityBuilder.builder().
            httpVersion("HTTP/1.1").status("403", "FORBIDDEN").contentLength(LENGTH_403)
            .contentType("text/html").body(CONTENT_403).build();

    public static final ResponseEntity ENTITY_404_PROTOTYPE = ResponseEntityBuilder.builder().
            httpVersion(HTTP_VERSION).status("404", "NOT FOUND").contentLength(LENGTH_404)
            .contentType("text/html").body(CONTENT_404).build();

    public static ResponseEntity responseEntity_403(Calendar calendar) {
        try {
            ResponseEntity responseEntity = (ResponseEntity) ENTITY_403_PROTOTYPE.clone();
            responseEntity.getHeaders().put("Date", StringUtils.getHttpFormatDate(calendar));
            return responseEntity;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return ResponseEntityBuilder.builder().
                httpVersion("HTTP/1.1").status("403", "FORBIDDEN").contentLength(LENGTH_403)
                .date(calendar).contentType("text/html").body(CONTENT_403).build();
    }

    public static ResponseEntity responseEntity_404(Calendar calendar) {
        try {
            ResponseEntity responseEntity = (ResponseEntity) ENTITY_404_PROTOTYPE.clone();
            responseEntity.getHeaders().put("Date", StringUtils.getHttpFormatDate(calendar));
            return responseEntity;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return ResponseEntityBuilder.builder().
                httpVersion("HTTP/1.1").status("404", "NOT FOUND").contentLength(LENGTH_404)
                .date(calendar).contentType("text/html").body(CONTENT_404).build();
    }

    public static ResponseEntityBuilder responseEntityBuilder_200() {
        return ResponseEntityBuilder.builder().httpVersion(HTTP_VERSION)
                .status("200", "OK");
    }
}

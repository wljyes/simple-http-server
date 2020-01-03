package cu.httpserver.factory;

import cu.httpserver.entity.ResponseEntity;
import cu.httpserver.util.StringUtils;

import java.util.*;

public class ResponseEntityBuilder {
    private String httpVersion;
    private String code;
    private String reasonPhrase;
    private Map<String,String> headers = new HashMap<>();
    private String responseBody;

    private ResponseEntityBuilder() {
    }

    public static ResponseEntityBuilder builder() {
        return new ResponseEntityBuilder();
    }

    public ResponseEntityBuilder httpVersion(String httpVersion) {
        this.httpVersion = httpVersion;
        return this;
    }

    public ResponseEntityBuilder status(String code, String reasonPhrase) {
        this.code = code;
        this.reasonPhrase = reasonPhrase;
        return this;
    }

    public ResponseEntityBuilder header(String key, String value) {
        this.headers.put(key, value);
        return this;
    }

    public ResponseEntityBuilder date(Calendar calendar) {
        this.headers.put("Date", StringUtils.getHttpFormatDate(calendar));
        return this;
    }

    public ResponseEntityBuilder contentLength(int length) {
        this.headers.put("Content-Length", String.valueOf(length));
        return this;
    }

    public ResponseEntityBuilder contentType(String type) {
        this.headers.put("Content-Type", type);
        return this;
    }

    public ResponseEntityBuilder body(String body) {
        this.responseBody = body;
        return this;
    }

    public ResponseEntity build() {
        ResponseEntity responseEntity = new ResponseEntity();
        responseEntity.setHttpVersion(httpVersion);
        responseEntity.setStatusCode(code);
        responseEntity.setReasonPhrase(reasonPhrase);
        responseEntity.setResponseBody(responseBody);
        responseEntity.getHeaders().putAll(headers);
        return responseEntity;
    }
}

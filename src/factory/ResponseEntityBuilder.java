package factory;

import entity.ResponseEntity;
import util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.*;

public class ResponseEntityBuilder {
    private ResponseEntity responseEntity = new ResponseEntity();

    private ResponseEntityBuilder() {}

    public static ResponseEntityBuilder builder() {
        return new ResponseEntityBuilder();
    }

    public ResponseEntityBuilder httpVersion(String httpVersion) {
        responseEntity.setHttpVersion(httpVersion);
        return this;
    }

    public ResponseEntityBuilder status(String code, String reasonPhrase) {
        responseEntity.setStatusCode(code);
        responseEntity.setReasonPhrase(reasonPhrase);
        return this;
    }

    public ResponseEntityBuilder header(String key, String value) {
        responseEntity.getHeaders().put(key, value);
        return this;
    }

    public ResponseEntityBuilder date(Calendar calendar) {
        responseEntity.getHeaders().put("Date", StringUtils.getHttpFormatDate(calendar));
        return this;
    }

    public ResponseEntityBuilder contentLength(int length) {
        responseEntity.getHeaders().put("Content-Length", String.valueOf(length));
        return this;
    }

    public ResponseEntityBuilder contentType(String type) {
        responseEntity.getHeaders().put("Content-Type", type);
        return this;
    }

    public ResponseEntityBuilder body(String body) {
        responseEntity.setResponseBody(body);
        return this;
    }

    public ResponseEntity build() {
        return responseEntity;
    }
}

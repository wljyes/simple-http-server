package entity;

import java.util.HashMap;
import java.util.Map;

public class RequestEntity {
    private HttpRequestMethod requestMethod;
    private String uri;
    private String protocolVersion;
    private Headers headers;
    private Map<String, String> parameters;

    public HttpRequestMethod getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(HttpRequestMethod requestMethod) {
        this.requestMethod = requestMethod;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getProtocolVersion() {
        return protocolVersion;
    }

    public void setProtocolVersion(String protocolVersion) {
        this.protocolVersion = protocolVersion;
    }

    public Headers getHeaders() {
        return headers;
    }

    public void setHeaders(Headers headers) {
        this.headers = headers;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    public static class Headers {
        private Map<String, String> headers;

        public Map<String, String> getHeaders() {
            return headers;
        }

        public Headers() {
            headers = new HashMap<>();
        }

        public void put(String key, String value) {
            headers.put(key, value);
        }

        public String get(String key) {
            return headers.get(key);
        }
    }
}


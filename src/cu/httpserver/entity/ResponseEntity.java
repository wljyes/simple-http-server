package cu.httpserver.entity;

import cu.httpserver.factory.ResponseEntityBuilder;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ResponseEntity {
    private String httpVersion;
    private String statusCode;
    private String reasonPhrase;
    private Headers headers = new Headers();
    private String responseBody;

    public ResponseEntity() {}

    /**
     * 拷贝构造器
     * @param prototype 要拷贝的原型
     */
    public ResponseEntity(ResponseEntity prototype) {
        this.httpVersion = prototype.getHttpVersion();
        this.statusCode = prototype.statusCode;
        this.reasonPhrase = prototype.reasonPhrase;
        this.responseBody = prototype.responseBody;
        this.headers.putAll(prototype.headers);
    }

    @Override
    public String toString() {
        return httpVersion + " " + statusCode + " " + reasonPhrase + "\n" +
                headers.toString() +
                "\n" +
                responseBody;
    }

    public String getHttpVersion() {
        return httpVersion;
    }

    public void setHttpVersion(String httpVersion) {
        this.httpVersion = httpVersion;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getReasonPhrase() {
        return reasonPhrase;
    }

    public void setReasonPhrase(String reasonPhrase) {
        this.reasonPhrase = reasonPhrase;
    }

    public Headers getHeaders() {
        return headers;
    }

    public void setHeaders(Headers headers) {
        this.headers = headers;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }

    public static class Headers implements Map<String, String> {
        private Map<String, String> map = new HashMap<>();

        Headers() {
            map.put("Date", "");
            map.put("Content-Length", "");
            map.put("Content-Type", "");
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            for (Entry<String, String> entry : map.entrySet()) {
                sb.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
            }
            return sb.toString();
        }

        @Override
        public int size() {
            return map.size();
        }

        @Override
        public boolean isEmpty() {
            return map.isEmpty();
        }

        @Override
        public boolean containsKey(Object key) {
            return map.containsKey(key);
        }

        @Override
        public boolean containsValue(Object value) {
            return map.containsValue(value);
        }

        @Override
        public String get(Object key) {
            return map.get(key);
        }

        @Override
        public String put(String key, String value) {
            return map.put(key, value);
        }

        @Override
        public String remove(Object key) {
            return map.remove(key);
        }

        @Override
        public void putAll(Map<? extends String, ? extends String> m) {
            map.putAll(m);
        }

        @Override
        public void clear() {
            map.clear();
        }

        @Override
        public Set<String> keySet() {
            return map.keySet();
        }

        @Override
        public Collection<String> values() {
            return map.values();
        }

        @Override
        public Set<Entry<String, String>> entrySet() {
            return map.entrySet();
        }
    }

}

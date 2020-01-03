package cu.httpserver.exception;

public class HttpRequestParseException extends Exception {
    public HttpRequestParseException(String message) {
        super(message);
    }
}

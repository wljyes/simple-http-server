package exception;

public class UnSupportRequestMethodException extends HttpRequestParseException {
    public UnSupportRequestMethodException(String message) {
        super(message);
    }
}

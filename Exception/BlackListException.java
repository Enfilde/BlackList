package Exception;

public class BlackListException extends Exception{
    public BlackListException() {
        super();
    }

    public BlackListException(String message) {
        super(message);
    }

    public BlackListException(String message, Throwable cause) {
        super(message, cause);
    }

    public BlackListException(Throwable cause) {
        super(cause);
    }

    protected BlackListException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

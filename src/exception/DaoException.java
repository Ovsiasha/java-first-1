package exception;

public class DaoException extends RuntimeException {
    public DaoException(String string, Throwable throwable) {
        super(throwable);
    }


}

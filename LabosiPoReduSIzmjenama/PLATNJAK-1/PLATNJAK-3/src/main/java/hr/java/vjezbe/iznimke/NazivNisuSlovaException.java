package hr.java.vjezbe.iznimke;

/**
 * exceptiopn that generates when in name of subject are numbers
 */
public class NazivNisuSlovaException extends RuntimeException {
    public NazivNisuSlovaException() {
    }

    public NazivNisuSlovaException(String message) {
        super(message);
    }

    public NazivNisuSlovaException(String message, Throwable cause) {
        super(message, cause);
    }

    public NazivNisuSlovaException(Throwable cause) {
        super(cause);
    }

    public NazivNisuSlovaException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

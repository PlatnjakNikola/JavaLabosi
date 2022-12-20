package hr.java.vjezbe.iznimke;


import java.io.Serial;
import java.io.Serializable;

public class NemoguceOdreditiProsjekStudentaException extends Exception implements Serializable{

    @Serial
    private static final long serialVersionUID = -8338886340658715694L;

    public NemoguceOdreditiProsjekStudentaException() {
    }

    public NemoguceOdreditiProsjekStudentaException(String message) {
        super(message);
    }

    public NemoguceOdreditiProsjekStudentaException(String message, Throwable cause) {
        super(message, cause);
    }

    public NemoguceOdreditiProsjekStudentaException(Throwable cause) {
        super(cause);
    }

    public NemoguceOdreditiProsjekStudentaException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

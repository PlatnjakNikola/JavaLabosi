package hr.java.vjezbe.iznimke;

import java.io.Serial;
import java.io.Serializable;

public class NemaIspitaZaStudentaException extends Exception implements Serializable {
    @Serial
    private static final long serialVersionUID = -4680484493781904825L;

    public NemaIspitaZaStudentaException() {
    }

    public NemaIspitaZaStudentaException(String message) {
        super(message);
    }

    public NemaIspitaZaStudentaException(String message, Throwable cause) {
        super(message, cause);
    }

    public NemaIspitaZaStudentaException(Throwable cause) {
        super(cause);
    }

    public NemaIspitaZaStudentaException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

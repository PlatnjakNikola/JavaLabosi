package hr.java.vjezbe.iznimke;

import java.io.Serial;

/**
 * exceptiopn that generates when size of name is not in boundaries
 */
public class NazivDuzinaExeption extends Exception{
    @Serial
    private static final long serialVersionUID = -4424757158676024116L;

    public NazivDuzinaExeption() {
    }

    public NazivDuzinaExeption(String message) {
        super(message);
    }

    public NazivDuzinaExeption(String message, Throwable cause) {
        super(message, cause);
    }

    public NazivDuzinaExeption(Throwable cause) {
        super(cause);
    }

    public NazivDuzinaExeption(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

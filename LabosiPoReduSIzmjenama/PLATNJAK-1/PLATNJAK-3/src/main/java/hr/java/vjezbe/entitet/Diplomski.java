package hr.java.vjezbe.entitet;

/**
 * interface that extends class Visokoskolska
 */
public interface Diplomski extends Visokoskolska{
    /**
     * function that finds student for Rectors reward
     * @return student that won Rectors reward
     */
    Student odrediStudentaZaRektorovuNagradu();
}

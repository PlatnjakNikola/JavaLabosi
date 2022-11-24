package hr.java.vjezbe.entitet;

import java.time.LocalDateTime;

/**
 * class of exams for creating objects that user can store data in it
 */
public class Ispit {
    private Predmet predmetKojiSePolaze;
    private Student studentKojiPolazeIspit;
    private Integer ocjena;
    private LocalDateTime datumIVrijeme;

    /**
     * constructor for saving data into class
     * @param predmetKojiSePolaze saves subject on that term
     * @param studentKojiPolazeIspit saves students that thake that term
     * @param ocjena saves mark that student got
     * @param datumIVrijeme save time that term was held
     */
    public Ispit(Predmet predmetKojiSePolaze, Student studentKojiPolazeIspit, Integer ocjena, LocalDateTime datumIVrijeme) {
        this.predmetKojiSePolaze = predmetKojiSePolaze;
        this.studentKojiPolazeIspit = studentKojiPolazeIspit;
        this.ocjena = ocjena;
        this.datumIVrijeme = datumIVrijeme;
    }

    public Predmet getPredmetaKojiSePolaze() {
        return predmetKojiSePolaze;
    }

    public void setPredmetaKojiSePolaze(Predmet predmetKojiSePolaze) {
        this.predmetKojiSePolaze = predmetKojiSePolaze;
    }

    public Student getStudentKojiPolazeIspit() {
        return studentKojiPolazeIspit;
    }

    public void setStudentKojiPolazeIspit(Student studentKojiPolazeIspit) {
        this.studentKojiPolazeIspit = studentKojiPolazeIspit;
    }

    public Integer getOcjena() {
        return ocjena;
    }

    public void setOcjena(Integer ocjena) {
        this.ocjena = ocjena;
    }

    public LocalDateTime getDatumIVrijeme() {
        return datumIVrijeme;
    }

    public void setDatumIVrijeme(LocalDateTime datumIVrijeme) {
        this.datumIVrijeme = datumIVrijeme;
    }
}

package hr.java.vjezbe.entitet;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * class of exams for creating objects that user can store data in it
 */
public final class Ispit implements Online{
    private final List<String> onlineSoftveri = Arrays.asList("Synap", "Test Invite", "TestGorilla", "ExamOnline", "TCExam","eSkill");
    private Predmet predmetKojiSePolaze;
    private Student studentKojiPolazeIspit;
    private Integer ocjena;
    private LocalDateTime datumIVrijeme;

    private Dvorana dvoranaIspita;


    /**
     * constructor for saving data into class exame is taken
     * @param predmetKojiSePolaze saves subject on that term
     * @param studentKojiPolazeIspit saves students that thake that term
     * @param ocjena saves mark that student got
     * @param datumIVrijeme save time that term was held
     * @param dvoranaIspita saves name of place where
     */
    public Ispit(Predmet predmetKojiSePolaze, Student studentKojiPolazeIspit, Integer ocjena, LocalDateTime datumIVrijeme, Dvorana dvoranaIspita) {
        this.predmetKojiSePolaze = predmetKojiSePolaze;
        this.studentKojiPolazeIspit = studentKojiPolazeIspit;
        this.ocjena = ocjena;
        this.datumIVrijeme = datumIVrijeme;
        this.dvoranaIspita = dvoranaIspita;
    }

    public Dvorana getDvoranaIspita() {
        return dvoranaIspita;
    }

    public void setDvoranaIspita(Dvorana dvoranaIspita) {
        this.dvoranaIspita = dvoranaIspita;
    }

    public Predmet getPredmetKojiSePolaze() {
        return predmetKojiSePolaze;
    }

    public void setPredmetKojiSePolaze(Predmet predmetKojiSePolaze) {
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

    /**
     * method for giving number of students that can participate on online exam
     * @param softver contains name of software
     * @return number of students
     */
    @Override
    public int brojStudenataNaOnlineIspitu(String softver) {
        if(onlineSoftveri.contains(softver)){
            return 100;
        }
        return 20;
    }
}

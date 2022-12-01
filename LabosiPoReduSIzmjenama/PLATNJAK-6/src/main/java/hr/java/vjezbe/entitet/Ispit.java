package hr.java.vjezbe.entitet;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * class of exams for creating objects that user can store data in it
 */
public final class Ispit extends Entitet implements Online{
    private final List<String> onlineSoftveri = Arrays.asList("Synap", "Test Invite", "TestGorilla", "ExamOnline", "TCExam","eSkill");
    private Predmet predmetKojiSePolaze;
    private Student studentKojiPolazeIspit;
    private Ocjena ocjena;
    private LocalDateTime datumIVrijeme;

    private Dvorana dvoranaIspita;

    /**
     * constructor for saving data into class
     * @param predmetKojiSePolaze saves subject on that term
     * @param studentKojiPolazeIspit saves students that thake that term
     * @param ocjena saves mark that student got
     * @param datumIVrijeme save time that term was held
     */
    public Ispit(long id, Predmet predmetKojiSePolaze, Student studentKojiPolazeIspit, Integer ocjena, LocalDateTime datumIVrijeme, Dvorana dvoranaIspita) {
        super(id);
        this.predmetKojiSePolaze = predmetKojiSePolaze;
        this.studentKojiPolazeIspit = studentKojiPolazeIspit;
        this.ocjena = switch (ocjena){
            case 1 -> Ocjena.NEDOVOLJAN;
            case 2 -> Ocjena.DOVOLJAN;
            case 3 -> Ocjena.DOBAR;
            case 4 -> Ocjena.VRLO_DOBAR;
            default -> Ocjena.ODLICAN;
        };
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

    public Ocjena getOcjena() {
        return ocjena;
    }

    public void setOcjena(Integer ocjena) {
        this.ocjena = switch (ocjena){
            case 1 -> Ocjena.NEDOVOLJAN;
            case 2 -> Ocjena.DOVOLJAN;
            case 3 -> Ocjena.DOBAR;
            case 4 -> Ocjena.VRLO_DOBAR;
            default -> Ocjena.ODLICAN;
        };
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

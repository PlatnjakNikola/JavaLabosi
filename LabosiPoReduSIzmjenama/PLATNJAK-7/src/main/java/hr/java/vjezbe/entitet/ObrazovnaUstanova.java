package hr.java.vjezbe.entitet;

import java.util.List;
import java.util.Set;

public abstract class ObrazovnaUstanova extends Entitet{
    private String naziv;
    private List<Predmet> predmets;
    private Set<Profesor> profesors;
    private Set<Student> students;
    private List<Ispit> ispits;

    /**
     * constructor that stores values to object of type ObrazovnaUstanova
     * @param naziv has name of educational institution
     * @param predmets has array of subject
     * @param profesors is array of professors
     * @param students is array of students
     * @param ispits is array of exams
     */
    public ObrazovnaUstanova(long id, String naziv, List<Predmet> predmets, Set<Profesor> profesors, Set<Student> students, List<Ispit> ispits) {
        super(id);
        this.naziv = naziv;
        this.predmets = predmets;
        this.profesors = profesors;
        this.students = students;
        this.ispits = ispits;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public List<Predmet> getPredmets() {
        return predmets;
    }

    public void setPredmets(List<Predmet> predmets) {
        this.predmets = predmets;
    }

    public Set<Profesor> getProfesors() {
        return profesors;
    }

    public void setProfesors(Set<Profesor> profesors) {
        this.profesors = profesors;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    public List<Ispit> getIspits() {
        return ispits;
    }

    public void setIspits(List<Ispit> ispits) {
        this.ispits = ispits;
    }

    /**
     * function that gives most successful student of specific year
     * @param godina is year in witch function finds most successful student
     * @return most successful student
     */
    public abstract Student odrediNajuspjesnijegStudentaNaGodini(int godina);
}

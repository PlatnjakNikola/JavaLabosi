package hr.java.vjezbe.entitet;

public abstract class ObrazovnaUstanova {
    private String naziv;
    private Predmet[] predmets;
    private Profesor[] profesors;
    private Student[] students;
    private Ispit[] ispits;

    /**
     * constructor that stores values to object of type ObrazovnaUstanova
     * @param naziv has name of educational institution
     * @param predmets has array of subject
     * @param profesors is array of professors
     * @param students is array of students
     * @param ispits is array of exams
     */
    public ObrazovnaUstanova(String naziv, Predmet[] predmets, Profesor[] profesors, Student[] students, Ispit[] ispits) {
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

    public Predmet[] getPredmets() {
        return predmets;
    }

    public void setPredmets(Predmet[] predmets) {
        this.predmets = predmets;
    }

    public Profesor[] getProfesors() {
        return profesors;
    }

    public void setProfesors(Profesor[] profesors) {
        this.profesors = profesors;
    }

    public Student[] getStudents() {
        return students;
    }

    public void setStudents(Student[] students) {
        this.students = students;
    }

    public Ispit[] getIspits() {
        return ispits;
    }

    public void setIspits(Ispit[] ispits) {
        this.ispits = ispits;
    }

    /**
     * function that gives most successful student of specific year
     * @param godina is year in witch function finds most successful student
     * @return most successful student
     */
    public abstract Student odrediNajuspjesnijegStudentaNaGodini(int godina);
}

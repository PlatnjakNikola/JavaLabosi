package hr.java.vjezbe.entitet;

import java.time.LocalDate;
import java.util.Objects;

/**
 * class of students for creating objects that user can store data in it
 */
public class Student extends Osoba {
    private String jmbag;
    private LocalDate datumRodjenja;

    /**
     * constructor for saving data into class
     * @param ime saves name of student
     * @param prezime saves surname of student
     * @param jmbag saves jmbag of student
     * @param datumRodjenja saves date of birth of student
     */
    public Student(String ime, String prezime, String jmbag, LocalDate datumRodjenja) {
        super(ime,prezime);
        this.jmbag = jmbag;
        this.datumRodjenja = datumRodjenja;
    }

    public String getJmbag() {
        return jmbag;
    }

    public void setJmbag(String jmbag) {
        this.jmbag = jmbag;
    }

    public LocalDate getDatumRodjenja() {
        return datumRodjenja;
    }

    public void setDatumRodjenja(LocalDate datumRodjenja) {
        this.datumRodjenja = datumRodjenja;
    }

    /**
     * method for displaying name and surname of student
     * @return name and surname of student
     */
    public String ImeIPrezimeStudenta(){
        return super.getImeIPrezime();
    }



}

package hr.java.vjezbe.entitet;

import java.time.LocalDate;

/**
 * class of students for creating objects that user can store data in it
 */
public class Student {
    private String ime, prezime, jmbag;
    private LocalDate datumRodjenja;

    /**
     * constructor for saving data into class
     * @param ime saves name of student
     * @param prezime saves surname of student
     * @param jmbag saves jmbag of student
     * @param datumRodjenja saves date of birth of student
     */
    public Student(String ime, String prezime, String jmbag, LocalDate datumRodjenja) {
        this.ime = ime;
        this.prezime = prezime;
        this.jmbag = jmbag;
        this.datumRodjenja = datumRodjenja;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
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
        return getIme() + " " + getPrezime();
    }
}

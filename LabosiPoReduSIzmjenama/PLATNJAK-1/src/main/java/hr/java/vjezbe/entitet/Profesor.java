package hr.java.vjezbe.entitet;

/**
 * class of Professors for creating objects that user can store data in it
 */
public class Profesor {
    private String sifra, ime, prezime, titula;

    /**
     * constructor for saving data into class
     * @param sifra saves code of professor
     * @param ime saves name of professor
     * @param prezime saves surname of profssor
     * @param titula saves title pf professor
     */
    public Profesor(String sifra, String ime, String prezime, String titula) {
        this.sifra = sifra;
        this.ime = ime;
        this.prezime = prezime;
        this.titula = titula;
    }

    public String getSifra() {
        return sifra;
    }

    public void setSifra(String sifra) {
        this.sifra = sifra;
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

    public String getTitula() {
        return titula;
    }

    public void setTitula(String titula) {
        this.titula = titula;
    }

    /**
     * method for displaying name and surname of professor
     * @return name and surname of professor
     */
    public String ImeIPrezimeProfesora(){
        return getIme() + " " + getPrezime();
    }
}

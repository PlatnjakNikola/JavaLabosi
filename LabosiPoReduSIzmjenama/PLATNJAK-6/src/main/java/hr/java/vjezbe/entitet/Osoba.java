package hr.java.vjezbe.entitet;

public abstract class Osoba extends Entitet{
    private String ime, prezime;

    /**
     * constructor for saving name and surname of person
     * @param ime is name of person
     * @param prezime is surname of person
     */
    public Osoba(long id, String ime, String prezime) {
        super(id);
        this.ime = ime;
        this.prezime = prezime;
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

    public String getImeIPrezime(){
        return getIme() + " " + getPrezime();
    }
}

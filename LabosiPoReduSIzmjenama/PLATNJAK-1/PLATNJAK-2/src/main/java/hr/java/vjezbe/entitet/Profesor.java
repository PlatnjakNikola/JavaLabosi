package hr.java.vjezbe.entitet;


/**
 * class of Professors for creating objects that user can store data in it
 */
public class Profesor extends Osoba {
    private String sifra;
    private String titula;

    /**
     * constructor for saving data into class using builder pattern
     * @param builder builds value to its variable
     */
    private Profesor(Builder builder){
        super(builder.ime, builder.prezime);
        this.sifra = builder.sifra;
        this.titula = builder.titula;
    }

    public String getSifra() {
        return sifra;
    }

    public void setSifra(String sifra) {
        this.sifra = sifra;
    }

    public String getTitula() {
        return titula;
    }

    public void setTitula(String titula) {
        this.titula = titula;
    }


    /**
     * class Builder for entering attributes for professors
     */
    public static class Builder{
        private final String sifra;
        private String titula;
        private String ime;
        private String prezime;

        /**
         * this method contains important values of professor that has to be entered
         * @param sifra is password of professor
         */
        public Builder(String sifra){
            this.sifra = sifra;
        }
        public Builder withIme(String ime){
            this.ime = ime;
            return this;
        }
        public Builder withPrezime(String prezime){
            this.prezime = prezime;
            return this;
        }
        public Builder withTitula(String titula){
            this.titula = titula;
            return this;
        }

        /**
         * function that returns object of type professor
         * @return object of type professor
         */
        public Profesor build(){
            return new Profesor(this);
        }


    }

    /**
     * method for displaying name and surname of professor
     * @return name and surname of professor
     */
    public String ImeIPrezimeProfesora(){
        return super.getImeIPrezime();
    }
}

package hr.java.vjezbe.entitet;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * class of subjects for creating objects that user can store data in it
 */
public class Predmet{
    private String sifra, naziv;
    private Integer brojEctsBodova;
    private Profesor nositelj;
    private Set<Student> studentiKojiSlusajuPredmet;


    /**
     * constructor for saving data into class
     * @param sifra saves code of subject
     * @param naziv saves name of subject
     * @param brojEctsBodova saves number of ECTS that subject has
     * @param nositelj saves professor that teaches that subject
     */
    public Predmet(String sifra, String naziv, Integer brojEctsBodova, Profesor nositelj) {
        this.sifra = sifra;
        this.naziv = naziv;
        this.brojEctsBodova = brojEctsBodova;
        this.nositelj = nositelj;
    }


    public List<Student> getStudentiKojiSlusajuPredmet() {
        return studentiKojiSlusajuPredmet.stream().toList();
    }

    public void setStudentiKojiSlusajuPredmet(List<Student> studentiKojiSlusajuPredmet) {
        this.studentiKojiSlusajuPredmet = new HashSet<>(studentiKojiSlusajuPredmet);
    }

    public String getSifra() {
        return sifra;
    }

    public void setSifra(String sifra) {
        this.sifra = sifra;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public Integer getBrojEctsBodova() {
        return brojEctsBodova;
    }

    public void setBrojEctsBodova(Integer brojEctsBodova) {
        this.brojEctsBodova = brojEctsBodova;
    }

    public Profesor getNositelj() {
        return nositelj;
    }

    public void setNositelj(Profesor nositelj) {
        this.nositelj = nositelj;
    }

    public Integer getBrojstudenata() {
        return studentiKojiSlusajuPredmet.size();
    }

}

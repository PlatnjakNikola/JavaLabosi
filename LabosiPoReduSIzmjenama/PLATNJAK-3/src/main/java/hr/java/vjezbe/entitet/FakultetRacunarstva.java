package hr.java.vjezbe.entitet;

import hr.java.vjezbe.glavna.Glavna;
import hr.java.vjezbe.iznimke.NemoguceOdreditiProsjekStudentaException;
import hr.java.vjezbe.iznimke.PostojiViseNajmladjihStudenataException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.LoggerFactory;

/**
 * class FakultetRacunarstva extends abstract class Obrazovnaustanova and implements interface Diplomski
 */
public class FakultetRacunarstva extends ObrazovnaUstanova implements Diplomski{

    /**
     * constructor for storing values to object
     * @param naziv contains name of Fakultetracunarstva
     * @param predmets contains subjects at Fakultetracunarstva
     * @param profesors contains profesors at Fakultetracunarstva
     * @param students contains students at Fakultetracunarstva
     * @param ispits contains exams at Fakultetracunarstva
     */
    public FakultetRacunarstva(String naziv, Predmet[] predmets, Profesor[] profesors, Student[] students, Ispit[] ispits) {
        super(naziv, predmets, profesors, students, ispits);
    }

    /**
     * method for getting most successful student in a year
     * @param godina contains year of most declaring most succesful student
     * @return object type Student
     */
    @Override
    public Student odrediNajuspjesnijegStudentaNaGodini(int godina) {
        List<Ispit> ispitiUZadanojGodini= new ArrayList<>();
        Student[] najUspjesnijiStudent = new Student[1];
        int najveciBrojIzvrsnihIspita=-1;
        for(Ispit ispit:getIspits()){
            if(ispit.getDatumIVrijeme().getYear() == godina){
                ispitiUZadanojGodini.add(ispit);
            }
        }
        for(Student student: getStudents()){
            Ispit[] ispitiPoStudentu = filtrirajIspitePoStudentu(ispitiUZadanojGodini.toArray(new Ispit[0]), student);

            Ispit[] brojIzvrsnoNapisanihIspita = filtrirajIzvrsnoNapisaneIspite(ispitiPoStudentu);
            if(brojIzvrsnoNapisanihIspita.length > najveciBrojIzvrsnihIspita){
                najveciBrojIzvrsnihIspita = brojIzvrsnoNapisanihIspita.length;
                najUspjesnijiStudent[0] = student;
            }
        }
        return najUspjesnijiStudent[0];
    }


    /**
     * filter exams that are passed with 5
     * @param ispits contains all the exams
     * @return array of exams
     */
    private Ispit[] filtrirajIzvrsnoNapisaneIspite(Ispit[] ispits){
        List<Ispit> listaFiltriranihIspita = new ArrayList<>();
        for(Ispit ispit: ispits){
            if(ispit.getOcjena() == 5){
                listaFiltriranihIspita.add(ispit);
            }
        }
        return listaFiltriranihIspita.toArray(new Ispit[0]);
    }


    /**
     * method for getting student that got Rectors award
     * @return object type Student
     */
    @Override
    public Student odrediStudentaZaRektorovuNagradu() {
        Student[] studentSNajvisimProsjekom = new Student[1];
        BigDecimal maxOcjena = BigDecimal.ONE;
        ArrayList<Student> najmladjiStudenti = new ArrayList<>();
        for(Student student: getStudents()){
            Ispit[] ispitiPoStudentu = filtrirajIspitePoStudentu(getIspits(), student);
            try{
                BigDecimal prosjekOcjenaNaIspitima = odrediProsjekOcjenaNaIspitima(ispitiPoStudentu);
                if(prosjekOcjenaNaIspitima.compareTo(maxOcjena) > 0){
                    maxOcjena = prosjekOcjenaNaIspitima;
                    studentSNajvisimProsjekom[0] = student;
                    najmladjiStudenti.clear();
                    najmladjiStudenti.add(student);
                }
                else if(prosjekOcjenaNaIspitima.compareTo(maxOcjena) == 0){
                    if(student.getDatumRodjenja().isAfter(studentSNajvisimProsjekom[0].getDatumRodjenja())){
                        studentSNajvisimProsjekom[0] = student;
                    }
                    else if(student.getDatumRodjenja().isEqual(studentSNajvisimProsjekom[0].getDatumRodjenja())){
                        najmladjiStudenti.add(student);
                    }
                }
            }catch(NemoguceOdreditiProsjekStudentaException ex){
                LoggerFactory.getLogger(Glavna.class).info(ex.getMessage(), ex);
                System.out.println(ex.getMessage());
            }
        }
        if(najmladjiStudenti.size() > 1){
            StringBuilder studenti = new StringBuilder();
            for(Student s: najmladjiStudenti){
                studenti.append(s.getImeIPrezime()).append(" ");
            }
            throw new PostojiViseNajmladjihStudenataException("Pronađeno je više najmlađih studenata s istim datumom rođenja," +
                    "a to su : " + studenti);
        }
        return studentSNajvisimProsjekom[0];
    }


    /**
     * method for calculating final grade of student
     * @param ocjenaDiplomskog contains grade of master
     * @param ocjenaObraneDiplomskog contains grade of defending masters
     * @return number of final grade
     */
    @Override
    public BigDecimal izracunajKonacnuOcjenuStudijaZaStudenta(BigDecimal prosjekOcjena, int ocjenaDiplomskog, int ocjenaObraneDiplomskog) {

        return prosjekOcjena.multiply(new BigDecimal(3))
                .add(new BigDecimal(ocjenaDiplomskog))
                .add(new BigDecimal(ocjenaObraneDiplomskog))
                .divide(new BigDecimal(5), RoundingMode.HALF_UP);
    }
}

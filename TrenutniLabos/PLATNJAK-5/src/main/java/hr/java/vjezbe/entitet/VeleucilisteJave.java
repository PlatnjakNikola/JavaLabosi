package hr.java.vjezbe.entitet;

import hr.java.vjezbe.glavna.Glavna;
import hr.java.vjezbe.iznimke.NemoguceOdreditiProsjekStudentaException;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

public class VeleucilisteJave extends ObrazovnaUstanova implements Visokoskolska{

    /**
     * constructor for saving data into object of type VeleucilisteJave
     * @param naziv contains name of object
     * @param predmets is array of subject of institution VeleucilisteJave
     * @param profesors is array of professors of institution Veleuciliste Jave
     * @param students is array of students of institution Veleuciliste Jave
     * @param ispits is array of exams of institution Veleuciliste Jave
     */
    public VeleucilisteJave(String naziv, List<Predmet> predmets, List<Profesor> profesors, List<Student> students, List<Ispit> ispits) {
        super(naziv, predmets, profesors, students, ispits);
    }

    /**
     * function that returns most successful student of the year
     * @param godina is year in witch function finds most successful student
     * @return most successful student
     */
    @Override
    public Student odrediNajuspjesnijegStudentaNaGodini(int godina) {
        List<Ispit> ispitiUZadanojGodini= new ArrayList<>();
        List<Student> najUspjesnijiStudent = new ArrayList<>();
        BigDecimal maxOcjena = BigDecimal.ONE;
        for(Ispit ispit:getIspits()){
            if(ispit.getDatumIVrijeme().getYear() == godina){
                ispitiUZadanojGodini.add(ispit);
            }
        }
        for(Student student: getStudents()){
            List<Ispit> ispitiPoStudentu = filtrirajIspitePoStudentu(ispitiUZadanojGodini, student);
            try{
                BigDecimal prosjekOcjenaNaIspitima = odrediProsjekOcjenaNaIspitima(ispitiPoStudentu);
                if(prosjekOcjenaNaIspitima.compareTo(maxOcjena) >= 0){
                    maxOcjena = prosjekOcjenaNaIspitima;
                    najUspjesnijiStudent.add(student);
                }
            }catch(NemoguceOdreditiProsjekStudentaException ex){
                LoggerFactory.getLogger(Glavna.class).info("Metoda OdradiNajuspjesnijegStudenta u klasi " +
                        "Veleucilistejave naisao na studenta s ocjenom 1.", ex);
            }catch (ArithmeticException ex){
                LoggerFactory.getLogger(Glavna.class).info("Ne postoje ispiti za studenta "+ student.ImeIPrezimeStudenta() + " u " +
                        godina + ".", ex);
            }

        }
        if(najUspjesnijiStudent.size()==0){
            System.out.println("Nemoguce odrediti prosjek jer ne postoje ispiti u toj godini za studente ");
            return new Student("_", "_", "_", LocalDate.now());
        }
        return najUspjesnijiStudent.get(najUspjesnijiStudent.size() - 1);
    }

    /**
     * function that calculate final grade of study of student
     * @param ocjenaPismenogDijela is grade of final exam
     * @param ocjenaObraneZavrsnogRada is grade of defending final exam
     * @return funal grade of student
     */
    @Override
    public BigDecimal izracunajKonacnuOcjenuStudijaZaStudenta(BigDecimal prosjekOcjena, Ocjena ocjenaPismenogDijela, Ocjena ocjenaObraneZavrsnogRada) {
        //noinspection BigDecimalMethodWithoutRoundingCalled
        return prosjekOcjena.multiply(BigDecimal.TWO)
                .add(new BigDecimal(ocjenaPismenogDijela.getOcjena()))
                .add(new BigDecimal(ocjenaObraneZavrsnogRada.getOcjena()))
                .divide(new BigDecimal(4));
    }

}

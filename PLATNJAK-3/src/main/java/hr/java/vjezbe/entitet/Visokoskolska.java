package hr.java.vjezbe.entitet;

import hr.java.vjezbe.iznimke.NemoguceOdreditiProsjekStudentaException;

import java.math.BigDecimal;
import java.util.ArrayList;

import java.util.List;


public interface Visokoskolska {
    /**
     * function that calculate final grade of study of student
     * @param ocjenaPismenogDijela is grade of final exam
     * @param ocjenaObraneZavrsnogRada is grade of defending final exam
     * @return funal grade of student
     */
    BigDecimal izracunajKonacnuOcjenuStudijaZaStudenta(BigDecimal prosjekOcjena, int ocjenaPismenogDijela, int ocjenaObraneZavrsnogRada);


    /**
     * function that give average grade of exams
     * @param ispits is array of exams
     * @return average grade
     * @throws NemoguceOdreditiProsjekStudentaException is exception when it is not possible to get average grade of student
     */
    default BigDecimal odrediProsjekOcjenaNaIspitima(Ispit[] ispits) throws NemoguceOdreditiProsjekStudentaException{
        int suma = 0;
        for(Ispit ispit: ispits){
            if (ispit.getOcjena() == 1) {
                throw new NemoguceOdreditiProsjekStudentaException("Student " + ispit.getStudentKojiPolazeIspit().ImeIPrezimeStudenta() +
                        " je iz ispita " + ispit.getPredmetKojiSePolaze().getNaziv() + " dobio nedovoljan(1)!!!");
            }
            else
                suma += ispit.getOcjena();
        }
        return new BigDecimal(suma / ispits.length);
    }

    /**
     * function that removes failed exams from array
     * @param ispits is array of all exams
     * @return array of ispits
     */
    private Ispit[] filtrirajPolozeneIspite(Ispit[] ispits){
        List<Ispit> listaFiltriranihIspita = new ArrayList<>();
        for(Ispit ispit: ispits){
            if(ispit.getOcjena() > 1){
                listaFiltriranihIspita.add(ispit);
            }
        }
        return listaFiltriranihIspita.toArray(new Ispit[0]);
    }

    /**
     * function that filters all exams that student has taken into one array
     * @param ispits array of all exams
     * @param student is students for which needs to filter exams
     * @return array of filtered exams
     */
    default Ispit[] filtrirajIspitePoStudentu(Ispit[] ispits, Student student){
        List<Ispit> ispitiKojimaJeStudentPristupao = new ArrayList<>();
        for(Ispit ispit:ispits){
            if(ispit.getStudentKojiPolazeIspit().getJmbag().equals(student.getJmbag()))
                ispitiKojimaJeStudentPristupao.add(ispit);
        }
        return ispitiKojimaJeStudentPristupao.toArray(new Ispit[0]);
    }

}

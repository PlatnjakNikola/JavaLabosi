package hr.java.vjezbe.entitet;

import hr.java.vjezbe.iznimke.NemaIspitaZaStudentaException;
import hr.java.vjezbe.iznimke.NemoguceOdreditiProsjekStudentaException;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public interface Visokoskolska {
    /**
     * function that calculate final grade of study of student
     * @param ocjenaPismenogDijela is grade of final exam
     * @param ocjenaObraneZavrsnogRada is grade of defending final exam
     * @return funal grade of student
     */
    BigDecimal izracunajKonacnuOcjenuStudijaZaStudenta(BigDecimal prosjekOcjena, Ocjena ocjenaPismenogDijela, Ocjena ocjenaObraneZavrsnogRada);

    /**
     * function that give average grade of exams
     * @param ispits array of exams
     * @return average grade
     */
    default BigDecimal odrediProsjekOcjenaNaIspitima(List<Ispit> ispits) throws NemoguceOdreditiProsjekStudentaException, NemaIspitaZaStudentaException {
        int suma = 0;
        if (ispits.size() == 0){
            throw new NemaIspitaZaStudentaException("Student nije pisao niti jedan ispit");
        }
        for(Ispit ispit: ispits){
            if (ispit.getOcjena() == Ocjena.NEDOVOLJAN) {
                throw new NemoguceOdreditiProsjekStudentaException("Student " + ispit.getStudentKojiPolazeIspit().ImeIPrezimeStudenta() +
                        " je iz ispita " + ispit.getPredmetKojiSePolaze().getNaziv() + " dobio nedovoljan(1)!!!");
            }
            else
                suma += ispit.getOcjena().getOcjena();
        }
        return new BigDecimal(suma / ispits.size());
    }

    /**
     * function that removes failed exams from array
     * @param ispits is array of all exams
     * @return array of ispits
     */
    private List<Ispit> filtrirajPolozeneIspite(List<Ispit> ispits){
        return  ispits.stream().filter(ispit -> ispit.getOcjena() != Ocjena.NEDOVOLJAN).collect(Collectors.toList());
    }

    /**
     * function that filters all exams that student has taken into one array
     * @param ispits array of all exams
     * @param student is students for which needs to filter exams
     * @return array of filtered exams
     */
    default List<Ispit> filtrirajIspitePoStudentu(List<Ispit> ispits, Student student){
        return ispits.stream()
                .filter(ispit -> ispit.getStudentKojiPolazeIspit().getJmbag().equals(student.getJmbag())
                                && !ispit.getPredmetKojiSePolaze().getSifra().matches("ZV(.*)")
                                && !ispit.getPredmetKojiSePolaze().getSifra().matches("OB(.*)"))
                .collect(Collectors.toList());
    }

}

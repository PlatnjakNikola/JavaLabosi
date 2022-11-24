package hr.java.vjezbe.glavna;

import hr.java.vjezbe.entitet.*;
import hr.java.vjezbe.iznimke.NazivDuzinaExeption;
import hr.java.vjezbe.iznimke.NazivNisuSlovaException;
import hr.java.vjezbe.iznimke.NemoguceOdreditiProsjekStudentaException;
import hr.java.vjezbe.iznimke.PostojiViseNajmladjihStudenataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static hr.java.vjezbe.Global.Globals.*;

/**
 * main class for starting program
 */

public class Glavna {

    private static final Logger logger = LoggerFactory.getLogger(Glavna.class);
    private static final Scanner unos = new Scanner(System.in);

    /**
     * method for starting program
     * @param args takes argument from command
     */
    public static void main(String[] args) {

        /*logger.info("Početak programa. ");
        boolean provjera = true;
        do{
            try{
                String naziv_predmeta = nazivPredmeta();
                provjera = false;
            } catch (NazivDuzinaExeption e){
                logger.error("Nije pravilna duzina", e);
                System.out.println(e.getMessage());
            }catch (NazivNisuSlovaException ex){
                logger.error("Nije pravilan unos", ex);
                System.out.println(ex.getMessage());
            }
        }while(provjera);*/


        System.out.print("Unesite koliko zelite ustanova: ");
        int brojUstanova = unosIntegera();
        ObrazovnaUstanova[] obrazovneUsanove = new ObrazovnaUstanova[brojUstanova];
        logger.info("Unos ustanova. ");
        for(int i = 0; i < brojUstanova; ++i){
            System.out.println("Unesite podatke za " + (i+1) + ". ustanovu:");

            logger.info("Unos profesora. ");
            Profesor[] professors = unosProfesora();

            logger.info("Unos predmeta. ");
            Predmet[] predmets = unosPredmeta(professors);

            logger.info("Unos studenata. ");
            Student[] students = unosStudenta();

            logger.info("Unos ispita. ");
            Ispit[] ispits = unosIspita(predmets, students);

            ispisStudenataKojiSuDobiliOdlican(ispits);

            logger.info("Spremanje informacija u ustanove. ");
            obrazovneUsanove[i] = unosObrazovnaUstanova(odabirObrazovneUstanove(), professors, predmets, students, ispits);
            if(obrazovneUsanove[i] == null){
                break;
            }
        }

    }

    public static String nazivPredmeta() throws NazivDuzinaExeption, NazivNisuSlovaException {

        System.out.print("Unesite naziv predmeta: ");
        String regex = "^.{3,25}$";
        Pattern pattern = Pattern.compile(regex);
        String naziv = unos.nextLine();

        Matcher matcher = pattern.matcher(naziv);
        if(!matcher.matches()){
            throw new NazivDuzinaExeption("Niste unijeli ispravnu duljinu naziva predmeta.");
        }

        for (int i = 0; i < naziv.length(); i++) {
            if (naziv.charAt(i) >= '0'
                    && naziv.charAt(i) <= '9') {
                throw new NazivNisuSlovaException("Niste unijeli samo slova.");
            }
        }
        return naziv;
    }

    /**
     * function for entering data about teachers
     * @return array of teachers
     */
    public static Profesor[] unosProfesora(){
        Profesor[] profesors = new Profesor[BROJPROFESORA];
        for(int i = 0; i < BROJPROFESORA; ++i){
            System.out.println("Unesite "+ (i + 1) + ". profesora: ");
            System.out.print("Unesite sifru profesora: ");
            String sifra = unos.nextLine();
            System.out.print("Unesite ime profesora: ");
            String ime = unos.nextLine();
            System.out.print("Unesite prezime profesora: ");
            String prezime = unos.nextLine();
            System.out.print("Unesite titulu profesora: ");
            String titula = unos.nextLine();
            profesors[i] = new Profesor.Builder(sifra)
                                    .withIme(ime)
                                    .withPrezime(prezime)
                                    .withTitula(titula)
                                    .build();
        }
        return profesors;
    }


    /**
     * function for displaying all the professors
     * @param profesors is a variable that stores data about teacher
     */
    public static void ispisProfesora(Profesor[]profesors){
        for(int i = 0; i < profesors.length; ++i){
            System.out.println((i + 1) + ". " + profesors[i].ImeIPrezimeProfesora());
        }
    }


    /**
     * function for entering data about subjects
     * @param profesors is a variable that stores data about teacher
     * @return array of subjects
     */
    public static Predmet[] unosPredmeta(Profesor[]profesors){
        Predmet[] predmets = new Predmet[BROJPREDMETA];
        for(int i = 0; i < BROJPREDMETA; ++i){
            System.out.println("Unesite "+ (i + 1) + ". predmet: ");
            System.out.print("Unesite sifru predmeta: ");
            String sifra = unos.nextLine();

            System.out.print("Unesite naziv predmeta: ");
            String regex = "^[a-zA-Z](\\s?[a-zA-Z]){3,25}$";
            Pattern pattern = Pattern.compile(regex);
            String naziv = unos.nextLine();

            Matcher matcher = pattern.matcher(naziv);
            while(!matcher.matches()){
                System.out.print("Unesite naziv predmeta: ");
                naziv = unos.nextLine();
                matcher = pattern.matcher(naziv);
            }


            System.out.print("Unesite broj ECTS bodova za predmet: '" + naziv + "': ");
            Integer brojECTSA = unosIntegera();

            System.out.println("Odaberite profesora ");
            ispisProfesora(profesors);
            System.out.print("Odabir >> ");
            int brojProfesora = unosIntegera();
            Profesor profesor = profesors[brojProfesora-1];

            System.out.print("Unesite broj studenata za predmet: '" + naziv + "': ");
            int brojStudenata = unosIntegera();
            predmets[i] = new Predmet(sifra, naziv, brojECTSA, profesor);
            predmets[i].setBrojstudenata(brojStudenata);
        }
        return predmets;
    }


    /**
     * function for displaying all the subjects
     * @param predmets is a variable that stores data about subjects
     */
    public static void ispisPredmeta(Predmet[] predmets){
        for(int i = 0; i < predmets.length; ++i){
            System.out.println((i + 1) + ". " + predmets[i].getNaziv());
        }
    }


    /**
     * function for entering data about students
     * @return array of students
     */
    public static Student[] unosStudenta(){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
        Student[] students = new Student[BROJSTUDENATA];
        for(int i = 0; i < BROJSTUDENATA; ++i){
            System.out.println("Unesite "+ (i + 1) + ". studenta: ");
            System.out.print("Unesite ime studenta: ");
            String ime = unos.nextLine();

            System.out.print("Unesite prezime studenta: ");
            String prezime = unos.nextLine();

            System.out.print("Unesite datum rođenja studenta" + ime + " " + prezime + " u formatu (dd.MM.yyyy.): ");
            String datum = unos.nextLine();
            LocalDate datumRodjenja = LocalDate.parse(datum, dateTimeFormatter);

            System.out.print("Unesite JMBAG studenta: ");
            String jmbag = unos.nextLine();


            students[i] = new Student(ime, prezime,jmbag, datumRodjenja);
        }
        return students;
    }


    /**
     * function for displaying all the students
     * @param students is a variable that stores data about students
     */
    public static void ispisStudenata(Student[]students){
        for(int i = 0; i < students.length; ++i){
            System.out.println((i + 1) + ". " + students[i].ImeIPrezimeStudenta());
        }
    }


    /**
     * function for entering data about exams
     * @param predmets is a variable that stores data about subjects
     * @param students is a variable that stores data about students
     * @return array of exams
     */
    public static Ispit[] unosIspita(Predmet[] predmets,Student[] students){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.'T'HH:mm");
        Ispit[] ispits = new Ispit[BROJISPITA];
        for(int i = 0; i < BROJISPITA; ++i){
            System.out.println((i + 1) + ". Ispitni rok: ");

            System.out.println("Odaberite predmet: ");
            ispisPredmeta(predmets);
            System.out.print("Odabir >> ");
            int odabirPredmeta = unosIntegera();

            System.out.print("Unesite naziv dvorane: ");
            String nazivDvorane = unos.nextLine();

            System.out.print("Unesite zgradu dvorane: ");
            String zgradaDvorane = unos.nextLine();

            System.out.println("Odaberite studenta: ");
            ispisStudenata(students);
            System.out.print("Odabir >> ");
            int odabirStudenta = unosIntegera();


            System.out.print("Unesite ocjenu na ispitu (1-5): ");
            int ocjena = unosIntegera();


            System.out.print("Unesite datum i vrijeme ispita u formatu (dd.MM.yyyy.THH:mm): ");
            String odabirVremenaPisanja = unos.nextLine();
            LocalDateTime vrijemePisanjaIspita = LocalDateTime.parse(odabirVremenaPisanja, dateTimeFormatter);
            while(vrijemePisanjaIspita.getDayOfYear() < LocalDateTime.now().minusDays(7).getDayOfYear()){
                odabirVremenaPisanja = unos.nextLine();
                vrijemePisanjaIspita = LocalDateTime.parse(odabirVremenaPisanja, dateTimeFormatter);
                logger.info("Unesen je datum previse u proslosti.");
            }

            ispits[i] = new Ispit(predmets[odabirPredmeta - 1],students[odabirStudenta - 1],
                                    ocjena, vrijemePisanjaIspita, new Dvorana(nazivDvorane, zgradaDvorane));

        }
        return ispits;
    }


    /**
     * function for displaying all the exams
     * @param ispits is a variable that stores data about exam
     */
    public static void ispisIspita(Ispit[] ispits){
        for (Ispit ispit : ispits) {
            String ocjena = ispit.getOcjena().toString();
            switch (ocjena) {
                case "1":
                    ocjena = "nedvoljan";
                    break;
                case "2":
                    ocjena = "dovoljan";
                    break;
                case "3":
                    ocjena = "dobar";
                    break;
                case "4":
                    ocjena = "vrlo dobar";
                    break;
                case "5":
                    ocjena = "odlican";
                    break;
            }
            System.out.println("Student " + ispit.getStudentKojiPolazeIspit().ImeIPrezimeStudenta() +
                    " je ostvario ocjenu " + ocjena + " na predmetu '" + ispit.getPredmetKojiSePolaze().getNaziv() + "'.");
        }
    }


    /**
     * function for displaying all students that got 5 on exam
     * @param ispits is array of exams
     */
    public static void ispisStudenataKojiSuDobiliOdlican(Ispit[] ispits){
        for (Ispit ispit : ispits) {
            if(ispit.getOcjena() == 5) {
                System.out.println("Student " + ispit.getStudentKojiPolazeIspit().ImeIPrezimeStudenta() +
                        " je ostvario ocjenu odlican na predmetu '" + ispit.getPredmetKojiSePolaze().getNaziv() + "'.");
            }
        }
    }



    /**
     * function for selecting university for storing data first
     * @return name that represent university
     */
    public static String odabirObrazovneUstanove(){
        System.out.println("Odaberite čije podatke želite unositi: ");
        System.out.println("1. Veleučiliušte Jave");
        System.out.println("2. Fakultet računalstva");
        System.out.print("Odabir >> ");
        int odabirUcilista = unos.nextInt();
        unos.nextLine();
        if(odabirUcilista == 1){
            return "java";
        }
        return "fer";
    }


    /**
     * function for saving educational institution
     * @param odabirUcilista has type of educational institution
     * @param profesors is array of profesors that institution has
     * @param predmets is arrayy of subjects that institution has
     * @param students is array of students that institution has
     * @param ispits is array of exams that was taken on institution
     * @return object of institution for saving institution
     */
    public static ObrazovnaUstanova unosObrazovnaUstanova (String odabirUcilista, Profesor[] profesors,
                                     Predmet[] predmets, Student[] students, Ispit[]ispits){
        System.out.print("Unesite naziv obrazovne ustanove: ");
        String nazivUstanove = unos.nextLine();
        int obranaZavrsnogRada, ocjenaZavrsnogRada;
        ObrazovnaUstanova obrazovnaUstanova;
        if(odabirUcilista.equals("java")){
            obrazovnaUstanova = new VeleucilisteJave(nazivUstanove, predmets, profesors, students, ispits);
        }
        else{
            obrazovnaUstanova = new FakultetRacunarstva(nazivUstanove, predmets, profesors, students, ispits);
        }

        for(Student s: students){

            if(obrazovnaUstanova instanceof VeleucilisteJave){
                try{
                    BigDecimal prosjecnaOcjena = ((VeleucilisteJave)obrazovnaUstanova).odrediProsjekOcjenaNaIspitima(
                            ((VeleucilisteJave) obrazovnaUstanova).filtrirajIspitePoStudentu(ispits, s));

                    System.out.print("Unesite ocjenu završnog rada za studenta " + s.getImeIPrezime() + ": ");
                    ocjenaZavrsnogRada = unosIntegera();

                    System.out.print("Unesite ocjenu obrane završnog rada za studenta " + s.getImeIPrezime() + ": ");
                    obranaZavrsnogRada = unosIntegera();

                    BigDecimal konacnaOcjena = ((VeleucilisteJave) obrazovnaUstanova)
                            .izracunajKonacnuOcjenuStudijaZaStudenta(prosjecnaOcjena, ocjenaZavrsnogRada, obranaZavrsnogRada);
                    System.out.print("Konačna ocjena studija studenta " + s.getImeIPrezime() +" je: ");
                    System.out.println(konacnaOcjena);

                }catch(NemoguceOdreditiProsjekStudentaException ex){
                    logger.info(ex.getMessage(), ex);
                    System.out.println(ex.getMessage());
                    System.out.println("Zbog toga ima prosjek nedovoljan(1)");
                }
            }
            else{
                try{
                    BigDecimal prosjecnaOcjena = ((FakultetRacunarstva)obrazovnaUstanova).odrediProsjekOcjenaNaIspitima(
                            ((FakultetRacunarstva) obrazovnaUstanova).filtrirajIspitePoStudentu(ispits, s));

                    System.out.print("Unesite ocjenu završnog rada za studenta " + s.getImeIPrezime() + ": ");
                    ocjenaZavrsnogRada = unosIntegera();

                    System.out.print("Unesite ocjenu obrane završnog rada za studenta " + s.getImeIPrezime() + ": ");
                    obranaZavrsnogRada = unosIntegera();

                    BigDecimal konacnaOcjena = ((FakultetRacunarstva) obrazovnaUstanova)
                            .izracunajKonacnuOcjenuStudijaZaStudenta(prosjecnaOcjena, ocjenaZavrsnogRada, obranaZavrsnogRada);
                    System.out.print("Konačna ocjena studija studenta " + s.getImeIPrezime() +" je: ");
                    System.out.println(konacnaOcjena);

                }catch(NemoguceOdreditiProsjekStudentaException ex){
                    logger.info(ex.getMessage(), ex);
                    System.out.println(ex.getMessage());
                    System.out.println("Zbog toga ima prosjek nedovoljan(1)");
                }
            }
        }
        System.out.print("Najbolji student 2022. godine je: ");
        Student najStudent = obrazovnaUstanova.odrediNajuspjesnijegStudentaNaGodini(2022);
        System.out.println(najStudent.ImeIPrezimeStudenta() + " JMBAG: " + najStudent.getJmbag());

        //odabrati studenta za Rektorovu nagradu
        if(obrazovnaUstanova instanceof FakultetRacunarstva){
            try{
                Student rektorovaNagradaStudent = ((FakultetRacunarstva) obrazovnaUstanova).odrediStudentaZaRektorovuNagradu();
                System.out.println("Student koji je osvojio rektorovu nagradu je : " +
                        rektorovaNagradaStudent.getImeIPrezime() + " JMBAG: " + rektorovaNagradaStudent.getJmbag());
            }catch (PostojiViseNajmladjihStudenataException ex){
                System.out.println("Program završava sa radom.");
                logger.info("pronadjeno vise najmladjih studenata", ex);
                System.out.println(ex.getMessage());
                return null;
            }

        }
        return obrazovnaUstanova;
    }


    /**
     * function for checking if value is number
     * @return number
     */
    public static int unosIntegera(){
        boolean provjera = false;
        int broj = 0;
        do{
            try{
                broj = unos.nextInt();
                unos.nextLine();
                provjera = true;
            }
            catch(InputMismatchException ex){
                System.out.println("Morate unijeti brojčane vrijednosti.");
                unos.nextLine();
                logger.error("Nije upisana brojcana vrijednost. ", ex);
            }
        }while(!provjera);
        return broj;
    }

}

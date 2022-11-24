package hr.java.vjezbe.glavna;

import hr.java.vjezbe.entitet.*;
import hr.java.vjezbe.iznimke.NemoguceOdreditiProsjekStudentaException;
import hr.java.vjezbe.iznimke.PostojiViseNajmladjihStudenataException;
import hr.java.vjezbe.soritranje.predmetComparator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static hr.java.vjezbe.Global.Globals.*;

/**
 * main class for starting program
 */
public class Glavna {
    private static final Logger logger = LoggerFactory.getLogger(Glavna.class);
    public static final Scanner unos = new Scanner(System.in);

    public static void main(String[] args) {
        List<Predmet> predmeti = new ArrayList<>();
        predmeti.add(new Predmet("", "8.1", 8, null));
        predmeti.add(new Predmet("", "6", 6, null));
        predmeti.add(new Predmet("", "8.2", 8, null));
        predmeti.add(new Predmet("", "4", 4, null));
        predmeti.add(new Predmet("", "1", 1, null));

        List<Predmet> predmeti2 = izdvojitiPredmete(predmeti);
        ispisizdvojenihPredmeta(predmeti2);
        System.out.println("___________________");
        sortiranje(predmeti2);



        /*
        logger.info("Početak programa. ");

        System.out.print("Unesite koliko zelite ustanova: ");
        int brojUstanova = unosIntegera();
        List<ObrazovnaUstanova> obrazovneUsanove = new ArrayList<>();
        logger.info("Unos ustanova. ");
        for(int i = 0; i < brojUstanova; ++i){
            System.out.println("Unesite podatke za " + (i+1) + ". ustanovu:");

            logger.info("Unos profesora. ");
            List<Profesor> professors = unosProfesora();

            logger.info("Unos predmeta. ");
            Map<Profesor, List<Predmet>> mapaProfesoraIpredmeta = unosPredmeta(professors);
            List<Predmet> sviPredmeti = new ArrayList<>();
            mapaProfesoraIpredmeta.forEach((k,v)-> sviPredmeti.addAll(v));
            ispisMapeProfesorStudent(mapaProfesoraIpredmeta);


            logger.info("Unos studenata. ");
            List<Student> students = unosStudenta();

            logger.info("Unos ispita. ");
            List<Ispit> ispits = unosIspita(sviPredmeti, students);
            ispisPredmetaIStudenata(ispits, sviPredmeti);

            ispisStudenataKojiSuDobiliOdlican(ispits);

            logger.info("Spremanje informacija u ustanove. ");
            obrazovneUsanove.add(unosObrazovnaUstanova(odabirObrazovneUstanove(), professors, sviPredmeti, students, ispits));
            if(obrazovneUsanove.get(i) == null){
                break;
            }
        }*/

    }

    /**
     * function for entering data about teachers
     * @return array of teachers
     */
    public static List<Profesor> unosProfesora(){
        List<Profesor> profesors = new ArrayList<>(BROJPROFESORA);
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
            profesors.add( new Profesor.Builder(sifra)
                                    .withIme(ime)
                                    .withPrezime(prezime)
                                    .withTitula(titula)
                                    .build() );
        }
        return profesors;
    }


    /**
     * function for displaying all the professors
     * @param profesors is a variable that stores data about teacher
     */
    public static void ispisProfesora(List<Profesor> profesors){
        for(int i = 0; i < profesors.size(); ++i){
            System.out.println((i + 1) + ". " + profesors.get(i).ImeIPrezimeProfesora());
        }
    }


    /**
     * function for entering data about subjects
     * @param profesors is a variable that stores data about teacher
     * @return array of subjects
     */
    public static Map<Profesor, List<Predmet>> unosPredmeta(List<Profesor> profesors){
        Map<Profesor, List<Predmet>> mapaPredmeta = new HashMap<>();
        for(int i = 0; i < BROJPREDMETA; ++i){
            System.out.println("Unesite "+ (i + 1) + ". predmet: ");
            System.out.print("Unesite sifru predmeta: ");
            String sifra = unos.nextLine();

            System.out.print("Unesite naziv predmeta: ");
            String regex = "^[a-zA-Z](\\s?[a-zA-Z]){3,40}$";
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
            Profesor profesor = profesors.get(brojProfesora - 1);

            if (!mapaPredmeta.containsKey(profesor)) {
                mapaPredmeta.put(profesor, new ArrayList<>());
            }
            mapaPredmeta.get(profesor).add(new Predmet(sifra, naziv, brojECTSA, profesor));
        }
        return mapaPredmeta;
    }


    /**
     * function for displaying all the subjects
     * @param predmets is a variable that stores data about subjects
     */
    public static void ispisPredmeta(List<Predmet> predmets){
        for(int i = 0; i < predmets.size(); ++i){
            System.out.println((i + 1) + ". " + predmets.get(i).getNaziv());
        }
    }


    /**
     * function for entering data about students
     * @return array of students
     */
    public static List<Student> unosStudenta(){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
        List<Student> students = new ArrayList<>(BROJSTUDENATA);
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


            students.add( new Student(ime, prezime,jmbag, datumRodjenja) );
        }
        return students;
    }


    /**
     * function for displaying all the students
     * @param students is a variable that stores data about students
     */
    public static void ispisStudenata(List<Student> students){
        for(int i = 0; i < students.size(); ++i){
            System.out.println((i + 1) + ". " + students.get(i).ImeIPrezimeStudenta());
        }
    }


    /**
     * function for entering data about exams
     * @param predmets is a variable that stores data about subjects
     * @param students is a variable that stores data about students
     * @return array of exams
     */
    public static List<Ispit> unosIspita(List<Predmet> predmets,List<Student> students){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.'T'HH:mm");
        List<Ispit> ispits = new ArrayList<>(BROJISPITA);
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
            int ocjena = dodajOcjenuIspita();

            System.out.print("Unesite datum i vrijeme ispita u formatu (dd.MM.yyyy.THH:mm): ");
            String odabirVremenaPisanja = unos.nextLine();
            LocalDateTime vrijemePisanjaIspita = LocalDateTime.parse(odabirVremenaPisanja, dateTimeFormatter);

            ispits.add(new Ispit(predmets.get(odabirPredmeta - 1),students.get(odabirStudenta - 1),
                                    ocjena, vrijemePisanjaIspita, new Dvorana(nazivDvorane, zgradaDvorane)));

        }
        return ispits;
    }


    /**
     * function for displaying all the exams
     * @param ispits is a variable that stores data about exam
     */
    public static void ispisIspita(List<Ispit> ispits){
        for (Ispit ispit : ispits) {
            System.out.println("Student " + ispit.getStudentKojiPolazeIspit().ImeIPrezimeStudenta() + " je ostvario ocjenu " +
                                switch (ispit.getOcjena()) {
                                    case NEDOVOLJAN -> "nedovoljan";
                                    case DOVOLJAN -> "dovoljan";
                                    case DOBAR -> "dobar";
                                    case VRLO_DOBAR -> "vrlo dobar";
                                    case ODLICAN -> "odlican";
                                } + " na predmetu '" + ispit.getPredmetKojiSePolaze().getNaziv() + "'.");
        }
    }


    /**
     * function for displaying all students that got 5 on exam
     * @param ispits is array of exams
     */
    public static void ispisStudenataKojiSuDobiliOdlican(List<Ispit> ispits){
        for (Ispit ispit : ispits) {
            if(ispit.getOcjena() == Ocjena.ODLICAN) {
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
    public static ObrazovnaUstanova unosObrazovnaUstanova (String odabirUcilista, List<Profesor> profesors,
                                     List<Predmet> predmets, List<Student> students, List<Ispit> ispits){
        System.out.print("Unesite naziv obrazovne ustanove: ");
        String nazivUstanove = unos.nextLine();
        Ocjena obranaZavrsnogRada, ocjenaZavrsnogRada;
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
                    ocjenaZavrsnogRada = convertIntToOcjena(dodajOcjenuIspita());

                    System.out.print("Unesite ocjenu obrane završnog rada za studenta " + s.getImeIPrezime() + ": ");
                    obranaZavrsnogRada = convertIntToOcjena(dodajOcjenuIspita());

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
                    ocjenaZavrsnogRada = convertIntToOcjena(unosIntegera());

                    System.out.print("Unesite ocjenu obrane završnog rada za studenta " + s.getImeIPrezime() + ": ");
                    obranaZavrsnogRada = convertIntToOcjena(unosIntegera());

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

        Student najStudent = obrazovnaUstanova.odrediNajuspjesnijegStudentaNaGodini(2022);

        if(!najStudent.getIme().equals("_")){
            System.out.print("Najbolji student 2022. godine je: ");
            System.out.println(najStudent.ImeIPrezimeStudenta() + " JMBAG: " + najStudent.getJmbag());

        }


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

    /**
     * function for returning grade of subject
     * @return grade of subject
     */
    private static int dodajOcjenuIspita(){
        do {
            int ocjena = unosIntegera();
            if(ocjena < 1 || ocjena > 5){
                System.out.println("Niste upisali ocjenu izmedju 1-5.\nMolim vas unesite ponovo");
                System.out.print("Unos >> ");
            }
            else
                return ocjena;

        }while(true);
    }

    /**
     * function for converting int to enum type Ocjena
     * @param ocjena is value of grade
     * @return enum type Ocjena
     */
    private static Ocjena convertIntToOcjena(int ocjena){

        return switch (ocjena){
            case 1 -> Ocjena.NEDOVOLJAN;
            case 2 -> Ocjena.DOVOLJAN;
            case 3 -> Ocjena.DOBAR;
            case 4 -> Ocjena.VRLO_DOBAR;
            default -> Ocjena.ODLICAN;
        };
    }

    /**
     * function for displaying all Professors and all subyects that he teaches
     * @param mapa contains Professors and their subjects
     */
    public static void ispisMapeProfesorStudent(Map<Profesor, List<Predmet>> mapa) {
        mapa.forEach((k,v)-> {
                System.out.print("Profesor " + k.getImeIPrezime() +" predaje : ");
                ispisIzListe(v);
        });
    }


    /**
     * function for displaying elements of list
     * @param lista contains list of elements of type Predmet
     */
    public static void ispisIzListe(List<Predmet>lista) {
        for (int i = 0; i < lista.size(); i++) {
            System.out.print(lista.get(i).getNaziv());
            if(i == lista.size() - 1) {
                System.out.println();
            }
            else{
                System.out.print(", ");
            }
        }
    }

    public static void ispisPredmetaIStudenata(List<Ispit> ispits, List<Predmet> predmets){
        for(Predmet p: predmets){
            System.out.println("Studenti upisani na predmet " + p.getNaziv() + " su :");
            int brojac = 0;
            for(Ispit ispit : ispits){
                if(ispit.getPredmetKojiSePolaze().getNaziv().equals(p.getNaziv())){
                    brojac++;
                    System.out.println(ispit.getStudentKojiPolazeIspit().getImeIPrezime());
                }
            }
            if(brojac == 0){
                System.out.println("Nema studenata upisanih na predmet: "+ p.getNaziv());
            }
        }
    }


    public static List<Predmet> izdvojitiPredmete(List<Predmet>listaPredmeta){
        return listaPredmeta.stream().filter(predmet -> predmet.getBrojEctsBodova() > 5).collect(Collectors.toList());
    }
    public static void ispisizdvojenihPredmeta(List<Predmet> predmeti){
        predmeti.forEach(predmet -> System.out.println(predmet.getNaziv()));
    }
    public static void sortiranje(List<Predmet>predmeti){
        predmeti.sort(new predmetComparator());
        int brojECtSA = predmeti.get(0).getBrojEctsBodova();

        predmeti.forEach(predmet -> {
            if(predmet.getBrojEctsBodova()== brojECtSA)
                System.out.println(predmet.getNaziv());
        });
    }

}

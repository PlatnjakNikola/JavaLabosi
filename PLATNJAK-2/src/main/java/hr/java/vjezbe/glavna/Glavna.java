package hr.java.vjezbe.glavna;

import hr.java.vjezbe.entitet.*;

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

    public static void main(String[] args) {
        Scanner unos = new Scanner(System.in);

        System.out.print("Unesite koliko zelite ustanova: ");
        int brojUstanova = unos.nextInt();
        unos.nextLine();
        ObrazovnaUstanova[] obrazovneUsanove = new ObrazovnaUstanova[brojUstanova];
        for(int i = 0; i < brojUstanova; ++i){
            System.out.println("Unesite podatke za " + (i+1) + ". ustanovu:");

            Profesor[] professors = unosProfesora(unos);
            Predmet[] predmets = unosPredmeta(unos, professors);
            Student[] students = unosStudenta(unos);
            Ispit[] ispits = unosIspita(unos, predmets, students);
            ispisStudenataKojiSuDobiliOdlican(ispits);

            obrazovneUsanove[i] = unosObrazovnaUstanova(odabirObrazovneUstanove(unos),unos, professors, predmets, students, ispits);

        }
        System.out.println(obrazovneUsanove[1].getNaziv());

        //ispisMinMaxOcjeneStudenta(ispits);




    }

    /**
     * function for entering data about teachers
     * @param unos is variable for loading input of a user
     * @return array of teachers
     */
    public static Profesor[] unosProfesora(Scanner unos){
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
     * @param unos is variable for loading input of a user
     * @param profesors is a variable that stores data about teacher
     * @return array of subjects
     */
    public static Predmet[] unosPredmeta(Scanner unos, Profesor[]profesors){
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
            Integer brojECTSA = unos.nextInt();
            unos.nextLine();

            System.out.println("Odaberite profesora ");
            ispisProfesora(profesors);
            System.out.print("Odabir >> ");
            int brojProfesora = unos.nextInt();
            unos.nextLine();
            Profesor profesor = profesors[brojProfesora-1];

            System.out.print("Unesite broj studenata za predmet: '" + naziv + "': ");
            int brojStudenata = unos.nextInt();
            unos.nextLine();
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
     * @param unos is variable for loading input of a user
     * @return array of students
     */
    public static Student[] unosStudenta(Scanner unos){
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
     * @param unos is variable for loading input of a user
     * @param predmets is a variable that stores data about subjects
     * @param students is a variable that stores data about students
     * @return array of exams
     */
    public static Ispit[] unosIspita(Scanner unos, Predmet[] predmets,Student[] students){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.'T'HH:mm");
        Ispit[] ispits = new Ispit[BROJISPITA];
        for(int i = 0; i < BROJISPITA; ++i){
            System.out.println((i + 1) + ". Ispitni rok: ");

            System.out.println("Odaberite predmet: ");
            ispisPredmeta(predmets);
            System.out.print("Odabir >> ");
            int odabirPredmeta = unos.nextInt();
            unos.nextLine();

            System.out.print("Unesite naziv dvorane: ");
            String nazivDvorane = unos.nextLine();

            System.out.print("Unesite zgradu dvorane: ");
            String zgradaDvorane = unos.nextLine();

            System.out.println("Odaberite studenta: ");
            ispisStudenata(students);
            System.out.print("Odabir >> ");
            int odabirStudenta = unos.nextInt();
            unos.nextLine();

            System.out.print("Unesite ocjenu na ispitu (1-5): ");
            int ocjena = unos.nextInt();
            unos.nextLine();


            System.out.print("Unesite datum i vrijeme ispita u formatu (dd.MM.yyyy.THH:mm): ");
            String odabirVremenaPisanja = unos.nextLine();
            LocalDateTime vrijemePisanjaIspita = LocalDateTime.parse(odabirVremenaPisanja, dateTimeFormatter);

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
     * @param unos load input from user
     * @return name that represent university
     */
    public static String odabirObrazovneUstanove(Scanner unos){
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
     * @param input loads users input
     * @param profesors is array of profesors that institution has
     * @param predmets is arrayy of subjects that institution has
     * @param students is array of students that institution has
     * @param ispits is array of exams that was taken on institution
     * @return object of institution for saving institution
     */
    public static ObrazovnaUstanova unosObrazovnaUstanova (String odabirUcilista, Scanner input, Profesor[] profesors,
                                     Predmet[] predmets, Student[] students, Ispit[]ispits){
        System.out.print("Unesite naziv obrazovne ustanove: ");
        String nazivUstanove = input.nextLine();
        int obranaZavrsnogRada, ocjenaZavrsnogRada;
        ObrazovnaUstanova obrazovnaUstanova;
        if(odabirUcilista.equals("java")){
            obrazovnaUstanova = new VeleucilisteJave(nazivUstanove, predmets, profesors, students, ispits);
        }
        else{
            obrazovnaUstanova = new FakultetRacunarstva(nazivUstanove, predmets, profesors, students, ispits);
        }

        for(Student s: students){
            System.out.print("Unesite ocjenu završnog rada za studenta " + s.getImeIPrezime() + ": ");
            ocjenaZavrsnogRada = input.nextInt();
            input.nextLine();

            System.out.print("Unesite ocjenu obrane završnog rada za studenta " + s.getImeIPrezime() + ": ");
            obranaZavrsnogRada = input.nextInt();
            input.nextLine();
            //dodati ova dva ispite u polje ispita

            System.out.print("Konačna ocjena studija studenta " + s.getImeIPrezime() +" je: ");
            if(obrazovnaUstanova instanceof VeleucilisteJave){
                System.out.println(((VeleucilisteJave)obrazovnaUstanova)
                                        .izracunajKonacnuOcjenuStudijaZaStudenta(((VeleucilisteJave)obrazovnaUstanova).filtrirajIspitePoStudentu(ispits, s),
                                ocjenaZavrsnogRada, obranaZavrsnogRada ));
            }
            else{
                System.out.println(((FakultetRacunarstva)obrazovnaUstanova)
                        .izracunajKonacnuOcjenuStudijaZaStudenta(((FakultetRacunarstva)obrazovnaUstanova).filtrirajIspitePoStudentu(ispits, s),
                                ocjenaZavrsnogRada, obranaZavrsnogRada ));

            }

        }
        System.out.print("Najbolji student 2022. godine je: ");
        Student najStudent = obrazovnaUstanova.odrediNajuspjesnijegStudentaNaGodini(2022);
        System.out.println(najStudent.ImeIPrezimeStudenta() + " JMBAG: " + najStudent.getJmbag());

        //odabrati studenta za Rektorovu nagradu
        if(obrazovnaUstanova instanceof FakultetRacunarstva){
            Student rektorovaNagradaStudent = ((FakultetRacunarstva) obrazovnaUstanova).odrediStudentaZaRektorovuNagradu();
            System.out.println("Student koji je osvojio rektorovu nagradu je : " +
                    rektorovaNagradaStudent.getImeIPrezime() + " JMBAG: " + rektorovaNagradaStudent.getJmbag());
        }
        return obrazovnaUstanova;
    }


}

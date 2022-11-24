package hr.java.vjezbe.glavna;

import hr.java.vjezbe.entitet.Ispit;
import hr.java.vjezbe.entitet.Predmet;
import hr.java.vjezbe.entitet.Profesor;
import hr.java.vjezbe.entitet.Student;

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
        Profesor[] professors = unosProfesora(unos);
        Predmet[] predmets = unosPredmeta(unos, professors);
        Student[] students = unosStudenta(unos);
        Ispit[] ispits = unosIspita(unos, predmets, students);
        ispisIspita(ispits);
        ispisStudenataKojiSuDobiliOdlican(ispits);
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
            profesors[i] = new Profesor(sifra, ime, prezime, titula);
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
            //String regex = "^[a-zA-Z](\\s?[a-zA-Z]){3,25}$";
            //Pattern pattern = Pattern.compile(regex);
            String naziv = unos.nextLine();

            //Matcher matcher = pattern.matcher(naziv);
            /*while(!matcher.matches()){
                System.out.print("Unesite naziv predmeta: ");
                naziv = unos.nextLine();
                matcher = pattern.matcher(naziv);
            }*/


            System.out.print("Unesite broj ECTS bodova za predmet: '" + naziv + "': ");
            Integer brojECTSA = unos.nextInt();
            unos.nextLine();

            System.out.println("Odaberite profesora ");
            ispisProfesora(profesors);
            int brojProfesora = unos.nextInt();
            unos.nextLine();
            Profesor profesor = profesors[brojProfesora-1];

            System.out.println("Unesite broj studenata za predmet: '" + naziv + "': ");
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

            System.out.println("Unesite JMBAG studenta: ");
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
            int odabirPredmeta = unos.nextInt();
            unos.nextLine();

            System.out.println("Odaberite studenta: ");
            ispisStudenata(students);
            int odabirStudenta = unos.nextInt();
            unos.nextLine();

            System.out.print("Unesite ocjenu na ispitu (1-5): ");
            int ocjena = unos.nextInt();
            unos.nextLine();


            System.out.print("Unesite datum i vrijeme ispita u formatu (dd.MM.yyyy.THH:mm): ");
            String odabirVremenaPisanja = unos.nextLine();
            LocalDateTime vrijemePisanjaIspita = LocalDateTime.parse(odabirVremenaPisanja, dateTimeFormatter);

            ispits[i] = new Ispit(predmets[odabirPredmeta - 1],students[odabirStudenta - 1], ocjena, vrijemePisanjaIspita);
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
                    " je ostvario ocjenu " + ocjena + " na predmetu '" + ispit.getPredmetaKojiSePolaze().getNaziv() + "'.");
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
                        " je ostvario ocjenu odlican na predmetu '" + ispit.getPredmetaKojiSePolaze().getNaziv() + "'.");
            }
        }
    }

    /*public static void ispisMinMaxOcjeneStudenta(Ispit[] ispits){

        Map<String, Integer> students = new HashMap<>();
        Map<String, Integer> maxStudents = new HashMap<>();

        for(Ispit i:ispits){
            String student=i.getStudentKojiPolazeIspit().ImeIPrezimeStudenta();
            if(students.containsKey(student)){
                if(i.getOcjena() < students.get(student)){
                    students.put(student, i.getOcjena());
                }
                if(i.getOcjena() > maxStudents.get(student)){
                    maxStudents.put(student, i.getOcjena());
                }
            }else{
                students.put(student, i.getOcjena());
                maxStudents.put(student, i.getOcjena());

            }
        }

        System.out.println("najmanje ocjene studenta su: ");
        for (String name: students.keySet()) {
            String value = students.get(name).toString();
            System.out.println(name + " " + value);
        }
        System.out.println("Najvece ocjene studenta su: ");
        for (String name: maxStudents.keySet()) {
            String value = students.get(name).toString();
            System.out.println(name + " " + value);
        }
    }*/


}

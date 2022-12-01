package hr.java.vjezbe.glavna;

import com.sun.org.apache.xml.internal.security.signature.ObjectContainer;
import hr.java.vjezbe.entitet.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class GlavnaDatoteke {

    private static final Logger logger = LoggerFactory.getLogger(hr.java.vjezbe.glavna.Glavna.class);
    public static final Scanner unos = new Scanner(System.in);

    public static void main(String[] args) {

    }



    public static Set<Profesor> dohvatiProfesore(){

        final String FILE_NAME = "profesori.txt";
        Set<Profesor> profesoriSet= new HashSet<>();

        try (BufferedReader in = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            int brojac = 0;
            String sifra="", titula="", ime="", prezime="";
            long id = -1;

            while ((line = in.readLine()) != null) {
                switch (brojac) {
                    case 0 -> { id = Long.parseLong(line); brojac++; }
                    case 1 -> { sifra = line; brojac++; }
                    case 2 -> { ime = line; brojac++; }
                    case 3 -> { prezime = line; brojac++; }
                    default -> {
                        titula = line;
                        profesoriSet.add(new Profesor.Builder(id, sifra)
                                .withIme(ime)
                                .withPrezime(prezime)
                                .withTitula(titula)
                                .build());
                        brojac = 0;
                    }
                }
            }
        } catch (IOException e) {
            logger.info("greska kod otvaranja predmeti.txt",e);
        }
        return profesoriSet;
    }

    public static Set<Student> dohvatiStudente(){

        final String FILE_NAME = "studenti.txt";
        Set<Student> students= new HashSet<>();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.");

        try (BufferedReader in = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            int brojac = 0;
            String ime="", prezime="", jmbag="";
            LocalDate datumRodjenja;
            long id = -1;

            while ((line = in.readLine()) != null) {
                switch (brojac) {
                    case 0 -> { id = Long.parseLong(line); brojac++;}
                    case 1 -> { ime = line; brojac++; }
                    case 2 -> { prezime = line; brojac++; }
                    case 3 -> { jmbag = line; brojac++; }
                    default -> {
                        datumRodjenja = LocalDate.parse(line, dateTimeFormatter);
                        students.add(new Student(id, ime, prezime, jmbag, datumRodjenja));
                        brojac = 0;
                    }
                }
            }
        } catch (IOException e) {
            logger.info("greska kod otvaranja studenti.txt",e);
        }
        return students;
    }

    public static List<Predmet> dohvatiPredmete(Set<Profesor>profesors, Set<Student>students){
        List<Predmet>predmets = new ArrayList<>();
        final String FILE_NAME = "predmeti.txt";

        try (BufferedReader in = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            String sifra="", naziv="";
            int brojEctesa =-1, brojac = 0;
            long id = -1, idProfesora = 0;

            while ((line = in.readLine()) != null) {
                switch (brojac) {
                    case 0 -> { id = Long.parseLong(line); brojac++; }
                    case 1 -> { sifra = line; brojac++; }
                    case 2 -> { naziv = line; brojac++; }
                    case 3 -> { brojEctesa = Integer.parseInt(line); brojac++; }
                    case 4 -> {
                        idProfesora = Long.parseLong(line);
                        Profesor profesor = nadjiElement(profesors, idProfesora);
                        Predmet predmet = new Predmet(id, sifra, naziv, brojEctesa, profesor);
                        predmets.add(predmet);
                        brojac++;
                    }
                    default -> {
                        line = line.replaceAll("\\s+","" );
                        List<String> idStudenata = List.of(line.split(","));
                        Set<Student> studentiZaPredmet = dohvatiSetSvihElemenata(students, idStudenata);

                        //dohvacanje treturnog predmeta preko indexa zadnjeg elementa
                        // i postavljanje studenata koji ga slusaju u njega
                        predmets.get(predmets.size() - 1)
                                .setStudentiKojiSlusajuPredmet(new ArrayList<>(studentiZaPredmet));
                        brojac = 0;
                    }
                }
            }
        } catch (IOException e) {
            logger.info("greska kod otvaranja predmeti.txt",e);
        }
        return predmets;
    }

    public static List<Ispit> dohvatiIspite(List<Predmet>predmeti, Set<Student>studenti, Set<Profesor>profesori){
        List<Ispit>ispits = new ArrayList<>();
        final String FILE_NAME = "ispiti.txt";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.'T'HH:mm");

        try (BufferedReader in = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            Predmet predmet = null;
            Student student = null;
            Profesor profesor= null;
            int ocjena=0;
            LocalDateTime vrijemePisanja = null;
            String imeDvorane="", imeZgrade="";
            int brojac = 0;
            long id = -1, idStudenta = 0, idPredmeta = 0;

            while ((line = in.readLine()) != null) {
                switch (brojac) {
                    case 0 -> { id = Long.parseLong(line); brojac++; }
                    case 1 -> {
                        idPredmeta = Long.parseLong(line);
                        predmet = nadjiElement(predmeti, idPredmeta);
                        brojac++;
                    }
                    case 2 -> {
                        idStudenta = Long.parseLong(line);
                        student = nadjiElement(studenti, idStudenta);
                        brojac++;
                    }
                    case 3 -> {
                        ocjena = Integer.parseInt(line);
                        brojac++;
                    }
                    case 4 -> {
                        vrijemePisanja = LocalDateTime.parse(line, dateTimeFormatter);
                        brojac++;
                    }case 5 -> {
                        imeDvorane = line;
                        brojac++;
                    }
                    default -> {
                        imeZgrade = line;
                        Dvorana dvorana = new Dvorana(imeDvorane, imeZgrade);

                        ispits.add(new Ispit(id, predmet, student, ocjena, vrijemePisanja, dvorana));
                        brojac = 0;
                    }
                }
            }
        } catch (IOException e) {
            logger.info("greska kod otvaranja ispiti.txt",e);
        }
        return ispits;
    }

    public static Set<ObrazovnaUstanova> dohvatiObrazovneUstanove(Set<Student>students, Set<Profesor>profesors,
                                                                  List<Ispit>ispits, List<Predmet>predmets){
        Set<ObrazovnaUstanova>obrazovnaUstanova = new HashSet<>();

        return obrazovnaUstanova;
    }

    public static <T> T nadjiElement(Collection<T> elementi, long id){
        for(T p: elementi){
            if(id == ((Entitet)p).getId()){
                return p;
            }
        }
        return null;
    }

    public static <T> Set<T> dohvatiSetSvihElemenata(Collection<T>elementi, List<String>id_evi){
        Set<T>elementiZaVratiti = new HashSet<>();
        for(String id: id_evi){
            for(T s: elementi){
                if(Integer.parseInt(id) == ((Entitet)s).getId()){
                    elementiZaVratiti.add(s);
                }
            }
        }
        return elementiZaVratiti;
    }
    public static <T> List<T> dohvatiListuSvihElemenata(Collection<T>elementi, List<String>id_evi){
        List<T>elementiZaVratiti = new ArrayList<>();
        for(String id: id_evi){
            for(T s: elementi){
                if(Integer.parseInt(id) == ((Entitet)s).getId()){
                    elementiZaVratiti.add(s);
                }
            }
        }
        return elementiZaVratiti;
    }


}

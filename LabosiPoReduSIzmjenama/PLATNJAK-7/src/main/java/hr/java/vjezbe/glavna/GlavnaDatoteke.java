package hr.java.vjezbe.glavna;

import hr.java.vjezbe.entitet.*;
import hr.java.vjezbe.iznimke.NemaIspitaZaStudentaException;
import hr.java.vjezbe.iznimke.NemoguceOdreditiProsjekStudentaException;
import hr.java.vjezbe.iznimke.PostojiViseNajmladjihStudenataException;
import hr.java.vjezbe.soritranje.ObrazovneUstanoveSorter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class GlavnaDatoteke {

    private static final Logger logger = LoggerFactory.getLogger(Glavna.class);

    public static void main(String[] args) {

        System.out.println("Ucitavanje Studenata...");
        Set<Student> students = dohvatiStudente();
        cekajSekundu();


        System.out.println("Ucitavanje Profesora...");
        Set<Profesor>profesors = dohvatiProfesore();
        cekajSekundu();


        System.out.println("Ucitavanje Predmeta...");
        List<Predmet> predmets = dohvatiPredmete(profesors, students);
        cekajSekundu();

        System.out.println("Ucitavanje Ispita...");
        List<Ispit> ispits = dohvatiIspite(predmets, students);
        cekajSekundu();

        System.out.println("Ucitavanje Sveucilišta...");
        Sveuciliste<ObrazovnaUstanova> sveuciliste = dohvatiObrazovneUstanove(students, profesors, ispits, predmets);
        cekajSekundu();

        ispisPodatakaIzSveucilista(sveuciliste);
        ispisObrazovnihUstanova(sveuciliste);
    }



    public static Set<Profesor> dohvatiProfesore(){

        final String FILE_NAME = "dat/profesori.txt";
        Set<Profesor> profesoriSet= new HashSet<>();

        try (BufferedReader in = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            int brojac = 0;
            String sifra="", titula, ime="", prezime="";
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

        final String FILE_NAME = "dat/studenti.txt";
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
        final String FILE_NAME = "dat/predmeti.txt";

        try (BufferedReader in = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            String sifra="", naziv="";
            int brojEctesa =-1, brojac = 0;
            long id = -1, idProfesora;

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
                        List<String> idStudenata = dohvatiListuIdeva(line);
                        Set<Student> studentiZaPredmet = new HashSet<>(dohvatiSveElemenate(students, idStudenata));

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

    public static List<Ispit> dohvatiIspite(List<Predmet>predmeti, Set<Student>studenti){
        List<Ispit>ispits = new ArrayList<>();
        final String FILE_NAME = "dat/ispiti.txt";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.'T'HH:mm");
        try (BufferedReader in = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            Predmet predmet = null;
            Student student = null;
            int ocjena=0;
            LocalDateTime vrijemePisanja = null;
            String imeDvorane="", imeZgrade;
            int brojac = 0;
            long id = -1, idStudenta, idPredmeta;

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

    public static Sveuciliste<ObrazovnaUstanova> dohvatiObrazovneUstanove(Set<Student>students, Set<Profesor>profesors,
                                                                          List<Ispit>ispits, List<Predmet>predmets){
        Set<ObrazovnaUstanova> obrazovnaUstanova = new HashSet<>();
        final String FILE_NAME = "dat/ustanove.txt";
        try (BufferedReader in = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            int brojac = 0;
            String nazivUstanove = "";
            long id = -1;
            List<Predmet> predmetiUstanove = new ArrayList<>();
            Set<Profesor> profesoriUstanove = new HashSet<>();
            Set<Student> studentiUstanove = new HashSet<>();
            List<Ispit> ispitiUstanove = new ArrayList<>();
            while ((line = in.readLine()) != null) {

                switch (brojac) {
                    case 0 -> { id = Long.parseLong(line); brojac++; }
                    case 1 -> {
                        nazivUstanove = line;
                        brojac++;
                    }
                    case 2 -> {
                        List<String> listaiId_aStudenata = dohvatiListuIdeva(line);
                        studentiUstanove = new HashSet<>(dohvatiSveElemenate(students, listaiId_aStudenata));
                        brojac++;
                    }
                    case 3 -> {
                        List<String> listaiId_aProfesora = dohvatiListuIdeva(line);
                        profesoriUstanove = new HashSet<>(dohvatiSveElemenate(profesors, listaiId_aProfesora));
                        brojac++;
                    }
                    case 4 -> {
                        List<String> listaiId_aPredmeta = dohvatiListuIdeva(line);
                        predmetiUstanove = new ArrayList<>(dohvatiSveElemenate(predmets, listaiId_aPredmeta));
                        brojac++;
                    }
                    case 5 -> {
                        List<String> listaiId_aIspita = dohvatiListuIdeva(line);
                        ispitiUstanove = new ArrayList<>(dohvatiSveElemenate(ispits, listaiId_aIspita));
                        brojac++;
                    }
                    default -> {

                        if(line.equals("FER")){
                            obrazovnaUstanova.add(new FakultetRacunarstva(id, nazivUstanove, predmetiUstanove, profesoriUstanove, studentiUstanove, ispitiUstanove));

                        }else{
                            obrazovnaUstanova.add(new VeleucilisteJave(id, nazivUstanove, predmetiUstanove, profesoriUstanove, studentiUstanove, ispitiUstanove));
                        }
                        brojac = 0;
                    }
                }
            }
        } catch (IOException e) {
            logger.info("greska kod otvaranja ispiti.txt",e);
        }
        Sveuciliste<ObrazovnaUstanova> sveuciliste = new Sveuciliste<>(obrazovnaUstanova.size());
        obrazovnaUstanova.forEach(sveuciliste::dodajObrazovnuUstanovu);

        return sveuciliste;
    }


    public static List<String> dohvatiListuIdeva(String id_evi){
        id_evi = id_evi.replaceAll("\\s+","" );
        return List.of(id_evi.split(","));
    }

    public static <T> T nadjiElement(Collection<T> elementi, long id){
        for(T p: elementi){
            if(id == ((Entitet)p).getId()){
                return p;
            }
        }
        return null;
    }

    public static <T> List<T> dohvatiSveElemenate(Collection<T>elementi, List<String>id_evi){
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

    public static void ispisPodatakaIzSveucilista(Sveuciliste<ObrazovnaUstanova>sveuciliste){
        for(ObrazovnaUstanova obrazovnaUstanova:sveuciliste.getListaUstanova()){
            System.out.println("____________________________Podaci unutar Ustanove____________________________");
            System.out.println("Za ustanovu koja se zove "+ obrazovnaUstanova.getNaziv());
            System.out.println();

            ispisProfesorPredmet(obrazovnaUstanova.getProfesors(), obrazovnaUstanova.getPredmets());
            ispisStudenataPoPredmetima(obrazovnaUstanova.getPredmets());
            ispisStudenataKojiSuDobiliOdlican(obrazovnaUstanova.getIspits());

            if(obrazovnaUstanova instanceof VeleucilisteJave){
                podaciZaVeleucilisteJave((VeleucilisteJave) obrazovnaUstanova);
            }
            else if(obrazovnaUstanova instanceof FakultetRacunarstva){
                if(podaciZaFakultetRacunalstva((FakultetRacunarstva) obrazovnaUstanova).equals("prekid"))
                    break;
            }
        }
    }

    public static String podaciZaFakultetRacunalstva(FakultetRacunarstva obrazovnaUstanova){
        for(Student student: obrazovnaUstanova.getStudents()){
            try{
                BigDecimal prosjecnaOcjena = obrazovnaUstanova.odrediProsjekOcjenaNaIspitima(
                        obrazovnaUstanova.filtrirajIspitePoStudentu(obrazovnaUstanova.getIspits(), student));

                Ocjena ocjenaZavrsnogRada = dodajOcjenuZavrsnogRadaIspita(student, obrazovnaUstanova.getIspits());

                Ocjena obranaZavrsnogRada = dodajOcjenuObraneRadaIspita(student, obrazovnaUstanova.getIspits());

                if(ocjenaZavrsnogRada != null && obranaZavrsnogRada != null){

                    BigDecimal konacnaOcjena = obrazovnaUstanova.izracunajKonacnuOcjenuStudijaZaStudenta(prosjecnaOcjena, ocjenaZavrsnogRada, obranaZavrsnogRada);
                    System.out.print("Konačna ocjena studija studenta " + student.getImeIPrezime() +" je: ");
                    System.out.println(konacnaOcjena);
                }

            }catch(NemoguceOdreditiProsjekStudentaException ex){
                logger.info(ex.getMessage(), ex);
                System.out.println(ex.getMessage());
                System.out.println("Zbog toga ima prosjek nedovoljan(1)");
            }catch (NemaIspitaZaStudentaException e){
                logger.info(e.getMessage(), e);
                System.out.println();
            }
        }
        Student najStudent = obrazovnaUstanova.odrediNajuspjesnijegStudentaNaGodini(2022);

        if(!najStudent.getIme().equals("_")){
            System.out.print("Najbolji student 2022. godine je: ");
            System.out.println(najStudent.ImeIPrezimeStudenta() + " JMBAG: " + najStudent.getJmbag());
        }

        try{
            Student rektorovaNagradaStudent = obrazovnaUstanova.odrediStudentaZaRektorovuNagradu();
            System.out.println("Student koji je osvojio rektorovu nagradu je : " +
                    rektorovaNagradaStudent.getImeIPrezime() + " JMBAG: " + rektorovaNagradaStudent.getJmbag());
        }catch (PostojiViseNajmladjihStudenataException ex){
            System.out.println("Program završava sa radom.");
            logger.info("pronadjeno vise najmladjih studenata", ex);
            System.out.println(ex.getMessage());
            return "prekid";
        }

        return "nastavak";
    }
    public static void podaciZaVeleucilisteJave(VeleucilisteJave obrazovnaUstanova){
        for(Student student: obrazovnaUstanova.getStudents()){
            try{
                BigDecimal prosjecnaOcjena = obrazovnaUstanova.odrediProsjekOcjenaNaIspitima(
                        obrazovnaUstanova.filtrirajIspitePoStudentu(obrazovnaUstanova.getIspits(), student));

                Ocjena ocjenaZavrsnogRada = dodajOcjenuZavrsnogRadaIspita(student, obrazovnaUstanova.getIspits());

                Ocjena obranaZavrsnogRada = dodajOcjenuObraneRadaIspita(student, obrazovnaUstanova.getIspits());


                if(ocjenaZavrsnogRada != null && obranaZavrsnogRada != null){

                    BigDecimal konacnaOcjena = obrazovnaUstanova.izracunajKonacnuOcjenuStudijaZaStudenta(prosjecnaOcjena, ocjenaZavrsnogRada, obranaZavrsnogRada);
                    System.out.print("\nKonačna ocjena studija studenta " + student.getImeIPrezime() +" je: ");
                    System.out.println(konacnaOcjena);
                }

            }catch(NemoguceOdreditiProsjekStudentaException ex){
                logger.info(ex.getMessage(), ex);
                System.out.println(ex.getMessage());
                System.out.println("Zbog toga ima prosjek nedovoljan(1)");
            }
            catch (NemaIspitaZaStudentaException e){
                logger.info(e.getMessage(), e);
                System.out.println();
            }
        }
        Student najStudent = obrazovnaUstanova.odrediNajuspjesnijegStudentaNaGodini(2022);

        if(!najStudent.getIme().equals("_")){
            System.out.print("Najbolji student 2022. godine je: ");
            System.out.println(najStudent.ImeIPrezimeStudenta() + " JMBAG: " + najStudent.getJmbag());
        }

    }

    public static Ocjena dodajOcjenuZavrsnogRadaIspita(Student student, List<Ispit>ispits){
        for(Ispit ispit: ispits){
            if(ispit.getPredmetKojiSePolaze().getSifra().matches("ZV(.*)") &&
                ispit.getStudentKojiPolazeIspit().getId() == student.getId()){
                return ispit.getOcjena();
            }
        }
        return null;
    }
    public static Ocjena dodajOcjenuObraneRadaIspita(Student student, List<Ispit>ispits){
        for(Ispit ispit: ispits){
            if(ispit.getPredmetKojiSePolaze().getSifra().matches("OB(.*)") &&
                    ispit.getStudentKojiPolazeIspit().getId() == student.getId()){
                return ispit.getOcjena();
            }
        }
        return null;
    }
    public static void ispisObrazovnihUstanova(Sveuciliste<ObrazovnaUstanova> sveuciliste){
        System.out.println("\n___________________________ Obrazovne Ustanove ___________________________");
        sveuciliste.getListaUstanova()
                .stream()
                .sorted(new ObrazovneUstanoveSorter())
                .forEach(obrazovnaUstanova1 -> System.out.println(obrazovnaUstanova1.getNaziv() +
                        " -> Broj studenata: "+ obrazovnaUstanova1.getStudents().size()));

    }


    public static void ispisProfesorPredmet(Set<Profesor>profesors, List<Predmet>predmets) {
        for(Profesor profesor: profesors){
            List<Predmet>predmetiKojePredajeProfesor = new ArrayList<>();
            for(Predmet predmet: predmets){
                if(predmet.getNositelj().getId() == profesor.getId() && testIfZavrsni(predmet.getSifra())){
                    predmetiKojePredajeProfesor.add(predmet);
                }
            }
            if(predmetiKojePredajeProfesor.isEmpty()){
                System.out.println("\nProfesor " + profesor.ImeIPrezimeProfesora() + " ne predaje na ovom Sveucilistu.");
            }
            else{
                System.out.println("\nProfesor " + profesor.ImeIPrezimeProfesora() + " predaje predmete: ");
                predmetiKojePredajeProfesor.forEach(p -> System.out.println(p.getNaziv()));
            }
        }
        System.out.println();
    }
    public static void ispisStudenataPoPredmetima(List<Predmet> predmets){
        for (Predmet predmet : predmets)
            if (predmet.getBrojstudenata() == 0)
                System.out.println("Nema studenata upisanih na predmet '" + predmet.getNaziv() + "'.");
            else if (testIfZavrsni(predmet.getSifra())) {
                System.out.println("Studenti upisani na predmet '" + predmet.getNaziv() + "' su:");
                predmet.getStudentiKojiSlusajuPredmet().forEach(student -> System.out.println(student.ImeIPrezimeStudenta()));
                System.out.println();
            }
        System.out.println();
    }
    //popraviti regex
    public static void ispisStudenataKojiSuDobiliOdlican(List<Ispit> ispits){
        System.out.println("____________________________ odlicni ____________________________");
        ispits.forEach(ispit -> {
            if(ispit.getOcjena() == Ocjena.ODLICAN){
                if(testIfZavrsni(ispit.getPredmetKojiSePolaze().getSifra())){
                    System.out.println("Student "+ ispit.getStudentKojiPolazeIspit().ImeIPrezimeStudenta() +
                            " je ostvario ocjenu odlican na predmetu '" + ispit.getPredmetKojiSePolaze().getNaziv() + "'.");
                }
            }

        });
        System.out.println();
    }

    private static void cekajSekundu(){
        try {
            TimeUnit.MILLISECONDS.sleep(400);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static boolean testIfZavrsni(String sifra){
        return !sifra.matches("ZV(.*)") && !sifra.matches("OB(.*)");
    }
}

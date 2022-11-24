package hr.java.vjezbe.entitet;

import hr.java.vjezbe.glavna.Glavna;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static hr.java.vjezbe.Global.Globals.BROJUCILISTA;
import static hr.java.vjezbe.glavna.Glavna.unos;

public class Sveuciliste <T extends ObrazovnaUstanova>{
    private List<T> listaUstanova;

    public Sveuciliste() {
        this(BROJUCILISTA);
    }
    public Sveuciliste(int kapacitet){
        int initKapacitet = kapacitet > 0 ? kapacitet : 10;
        listaUstanova = new ArrayList<T>(initKapacitet);
    }

    /**
     * method for adding object to list, and it is called in constructor
     * @param ustanova contains object of some kind of Institution
     */
    public void dodajObrazovnuUstanovu(T ustanova){
        this.listaUstanova.add(ustanova);
    }

    /**
     * method for getting object from list on specific index
     * @param index contains index of element in list
     * @return object on given index
     */
    public T dohvatiObrazovnuUstanovu(int index){
        boolean provjera = false;
        do{
            try{
                if(provjera){
                    index = unos.nextInt();
                    System.out.println();
                }
                return listaUstanova.get(index);
            }catch (IndexOutOfBoundsException ex){
                provjera = true;
                LoggerFactory.getLogger(Glavna.class).info("Ne postoji Ustanova na indexu: " + index, ex);
                System.out.println("Ne postoji ustanova na tom indexu, pokusajte ponovo. ");
            }
        }while(true);
    }

    /**
     * method for returning list of all Institution
     * @return list of Institutions
     */
    public List<T> getListaUstanova() {
        return listaUstanova;
    }


}

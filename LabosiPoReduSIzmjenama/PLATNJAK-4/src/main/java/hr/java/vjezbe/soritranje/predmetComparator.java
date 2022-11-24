package hr.java.vjezbe.soritranje;

import hr.java.vjezbe.entitet.Predmet;

import java.util.Comparator;

public class predmetComparator implements Comparator<Predmet> {
    @Override
    public int compare(Predmet o1, Predmet o2) {
        return o2.getBrojEctsBodova().compareTo(o1.getBrojEctsBodova());
    }
}

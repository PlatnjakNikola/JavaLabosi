package hr.java.vjezbe.soritranje;

import hr.java.vjezbe.entitet.ObrazovnaUstanova;
import hr.java.vjezbe.entitet.Student;

import java.util.Comparator;

public class ObrazovneUstanoveSorter implements Comparator<ObrazovnaUstanova> {
    @Override
    public int compare(ObrazovnaUstanova o1, ObrazovnaUstanova o2) {
        if(o1.getStudents().size() > o2.getStudents().size()){
            return 1;
        }
        else if(o1.getStudents().size() == o2.getStudents().size()){
            return o1.getNaziv().compareTo(o2.getNaziv());
        }
        else{
            return -1;
        }
    }
}

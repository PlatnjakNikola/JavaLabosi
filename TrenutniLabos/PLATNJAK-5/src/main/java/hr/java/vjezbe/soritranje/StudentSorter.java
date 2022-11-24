package hr.java.vjezbe.soritranje;

import hr.java.vjezbe.entitet.Student;

import java.util.Comparator;

public class StudentSorter implements Comparator<Student> {
    @Override
    public int compare(Student o1, Student o2) {
        if(o1.getPrezime().compareTo(o2.getPrezime()) > 0)
            return 1;
        else if (o1.getPrezime().compareTo(o2.getPrezime()) == 0)
            return o1.getIme().compareTo(o2.getIme());
        else
            return -1;
    }
}

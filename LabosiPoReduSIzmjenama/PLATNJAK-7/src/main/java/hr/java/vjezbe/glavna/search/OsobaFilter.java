package hr.java.vjezbe.glavna.search;


import hr.java.vjezbe.entitet.Osoba;
import hr.java.vjezbe.entitet.Student;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public interface OsobaFilter{
    default <T> List<T> imeFilter(String enteredName, List<T> listaZaFiltriranje){
        if(!enteredName.isEmpty()){
            listaZaFiltriranje = listaZaFiltriranje.stream()
                    .filter(s -> ((Osoba)s).getIme().toLowerCase().contains(enteredName.toLowerCase(Locale.ROOT)))
                    .collect(Collectors.toList());
        }
        return listaZaFiltriranje;
    }

    default <T> List<T> prezimeFilter(String enteredLastName, List<T> listaZaFiltriranje){
        if(!enteredLastName.isEmpty()){
            listaZaFiltriranje = listaZaFiltriranje.stream()
                    .filter(s -> ((Osoba)s).getPrezime().toLowerCase().contains(enteredLastName.toLowerCase(Locale.ROOT)))
                    .collect(Collectors.toList());
        }
        return listaZaFiltriranje;
    }
}

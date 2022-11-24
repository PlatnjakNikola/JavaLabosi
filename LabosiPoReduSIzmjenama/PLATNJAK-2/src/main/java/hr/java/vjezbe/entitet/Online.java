package hr.java.vjezbe.entitet;

public sealed interface Online permits Ispit {
    /**
     * function that givs number of Students for given software for online exam
     * @param nazivSoftvera is name of software
     * @return number of students
     */
    int brojStudenataNaOnlineIspitu(String nazivSoftvera);

}

package hr.java.vjezbe.entitet;

public enum Ocjena {
    NEDOVOLJAN(1),
    DOVOLJAN(2),
    DOBAR(3),
    VRLO_DOBAR(4),
    ODLICAN(5);
    private int ocjena;

    Ocjena(int ocjena){
        this.ocjena = ocjena;
    }

    public int getOcjena() {
        return ocjena;
    }

    public void setOcjena(int ocjena) {
        this.ocjena = ocjena;
    }
}

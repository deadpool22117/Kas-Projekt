package model;



import java.time.LocalDate;
import java.util.ArrayList;



public class Udflugt {
    private String navn;
    private LocalDate dato;
    private double pris;

    //LinkAttributter
    private Konference konference;
    private final ArrayList<Ledsager> ledsagere = new ArrayList<>();

    public Udflugt(String navn, LocalDate dato, double pris, Konference konference) {
        this.navn = navn;
        this.dato = dato;
        this.pris = pris;
        this.konference = konference;
    }

    public void addLedsager(Ledsager ledsager){
        ledsagere.add(ledsager);
    }

    public double getPris() {
        return pris;
    }
}

package model;

import org.jspecify.annotations.NullMarked;

import java.time.LocalDate;
import java.util.ArrayList;

@NullMarked

public class Konference {
    private String navn;
    private LocalDate startDato;
    private LocalDate slutDato;
    private String sted;
    private double prisPrDag;

    //LinkAttributter
    private final ArrayList<Tilmelding> tilmeldinger = new ArrayList<>();
    private final ArrayList<Udflugt> udflugter = new ArrayList<>();
    private final ArrayList<Hotel> hoteller = new ArrayList<>();

    public Konference(String navn, LocalDate startDato, LocalDate slutDato, double prisPrDag) {
        this.navn = navn;
        this.startDato = startDato;
        this.slutDato = slutDato;
        this.prisPrDag = prisPrDag;
    }

    public void addTilmelding(Tilmelding tilmelding) {
        tilmeldinger.add(tilmelding);
    }

    public void addUdflugt(Udflugt udflugt){
        udflugter.add(udflugt);
    }

    public LocalDate getStartDato() {
        return startDato;
    }

    public LocalDate getSlutDato() {
        return slutDato;
    }

    public double getPrisPrDag() {
        return prisPrDag;
    }
}

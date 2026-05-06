package model;

import org.jspecify.annotations.NullMarked;

import java.util.ArrayList;

@NullMarked
public class Ledsager {
    private String navn;
    //LinkAttributter
    private final ArrayList<Udflugt> udflugter = new ArrayList<>();
    private Tilmelding tilmelding;

    public Ledsager(String navn, Tilmelding tilmelding) {
        this.navn = navn;
        this.tilmelding = tilmelding;
    }

    public void addUdflugt(Udflugt udflugt){
        udflugter.add(udflugt);
    }

    public ArrayList<Udflugt> getUdflugter() {
        return udflugter;
    }
}

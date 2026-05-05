package model;

import org.jspecify.annotations.NullMarked;

import java.util.ArrayList;

@NullMarked
public class Tillæg {
    private String navn;
    private double pris;

    public Tillæg(String navn, double pris) {
        this.navn = navn;
        this.pris = pris;
    }
}

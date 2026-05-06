package model;

import org.jspecify.annotations.NullMarked;

import java.util.ArrayList;

@NullMarked
public class Hotel {
    private String hotelNavn;
    private double prisEnkelt;
    private double prisDobbelt;
    //LinkAttributter
    private final ArrayList<Tillaeg> muligeTillaeg = new ArrayList<>();

    public Hotel(String hotelNavn, double prisEnkelt, double prisDobbelt) {
        this.hotelNavn = hotelNavn;
        this.prisEnkelt = prisEnkelt;
        this.prisDobbelt = prisDobbelt;
    }

    public void addTillaeg(Tillaeg tillaeg){
        muligeTillaeg.add(tillaeg);
    }
}

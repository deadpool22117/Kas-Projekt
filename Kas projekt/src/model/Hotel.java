package model;



import org.jspecify.annotations.NullMarked;

import org.jspecify.annotations.NullMarked;

import org.jspecify.annotations.NullMarked;

import java.util.ArrayList;
import java.util.Collection;

@NullMarked
public class Hotel {
    private String hotelNavn;
    private double prisEnkelt;
    private double prisDobbelt;
    //LinkAttributter
    private final ArrayList<Tilmelding> tilmeldinger = new ArrayList<>();
    private final ArrayList<Tillaeg> muligeTillaeg = new ArrayList<>();

    public Hotel(String hotelNavn, double prisEnkelt, double prisDobbelt) {
        this.hotelNavn = hotelNavn;
        this.prisEnkelt = prisEnkelt;
        this.prisDobbelt = prisDobbelt;
    }

    public String getHotelNavn() {
        return hotelNavn;
    }

    public double getPrisEnkelt() {
        return prisEnkelt;
    }

    public double getPrisDobbelt() {
        return prisDobbelt;
    }

    public void addTillaeg(Tillaeg tillaeg){
        muligeTillaeg.add(tillaeg);
    }

    public void addTilmelding(Tilmelding tilmelding) {
        tilmeldinger.add(tilmelding);
    }

    public ArrayList<Tilmelding> getTilmeldinger() {
        return tilmeldinger;
    }

    public ArrayList<Tillaeg> getMuligeTillaeg() {
        return muligeTillaeg;
    }

    @Override
    public String toString() {
        return hotelNavn;
    }
}

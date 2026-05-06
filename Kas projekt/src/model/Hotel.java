package model;

import org.jspecify.annotations.NullMarked;

import java.util.ArrayList;

@NullMarked
public class Hotel {
    private String hotelNavn;
    private double prisEnkelt;
    private double prisDobbelt;
    //LinkAttributter
    private final ArrayList<Tilmelding> tilmeldinger = new ArrayList<>();
    private final ArrayList<Tillaeg> muligeTillæg = new ArrayList<>();

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
        muligeTillæg.add(tillaeg);

    }
}

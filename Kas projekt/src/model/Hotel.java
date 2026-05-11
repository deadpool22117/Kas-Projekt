package model;

import org.jspecify.annotations.NullMarked;

import java.util.ArrayList;

@NullMarked
public class Hotel {
    private String hotelNavn;
    private double prisEnkelt;
    private double prisDobbelt;

    private final ArrayList<Tillaeg> tillaeg = new ArrayList<>();
    private final ArrayList<Tilmelding> tilmeldinger = new ArrayList<>();

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

    public void addTillaeg(Tillaeg tillaeg) {
        this.tillaeg.add(tillaeg);
    }

    public ArrayList<Tillaeg> getTillaeg() {
        return new ArrayList<>(tillaeg);
    }

    public void addTilmelding(Tilmelding tilmelding) {
        tilmeldinger.add(tilmelding);
    }

    public ArrayList<Tilmelding> getTilmeldinger() {
        return new ArrayList<>(tilmeldinger);
    }

    @Override
    public String toString() {
        return hotelNavn;
    }
}
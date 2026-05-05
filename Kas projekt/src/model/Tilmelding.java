package model;

import org.jspecify.annotations.NullMarked;

import java.time.LocalDate;
import java.util.ArrayList;

@NullMarked

public class Tilmelding {
    private int tilmeldingID;
    private LocalDate ankomstDato;
    private LocalDate afrejseDato;
    private boolean erForedragsHolder;

    //LinkAttributter
    private Konference konference;
    private Hotel hotel;
    private Deltager deltager;
    private ArrayList<Tillaeg> valgteTillaeg = new ArrayList<>();

    public Tilmelding(int tilmeldingID, LocalDate ankomstDato, LocalDate afrejseDato, boolean erForedragsHolder, Konference konference, Deltager deltager, Hotel hotel) {
        this.tilmeldingID = tilmeldingID;
        this.ankomstDato = ankomstDato;
        this.afrejseDato = afrejseDato;
        this.erForedragsHolder = erForedragsHolder;
    }

    public Konference getKonference() {
        return konference;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public ArrayList<Tillaeg> getValgteTillaeg() {
        return valgteTillaeg;
    }

}

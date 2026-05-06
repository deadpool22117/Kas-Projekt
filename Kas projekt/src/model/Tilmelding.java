package model;

import org.jspecify.annotations.NullMarked;

import java.time.LocalDate;
import java.util.ArrayList;

@NullMarked

public class Tilmelding {
    private String tilmeldingID;
    private LocalDate ankomstDato;
    private LocalDate afrejseDato;
    private boolean erForedragsHolder;

    //LinkAttributter
    private Konference konference;
    private Hotel hotel;
    private Deltager deltager;
    private Ledsager ledsager;
    private Firma firma;
    private ArrayList<Tillaeg> valgteTillaeg = new ArrayList<>();

    public Tilmelding(String tilmeldingID, LocalDate ankomstDato, LocalDate afrejseDato, boolean erForedragsHolder, Konference konference, Deltager deltager) {
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

    public void setValgteTillaeg(ArrayList<Tillaeg> valgteTillaeg) {
        this.valgteTillaeg = valgteTillaeg;
    }

    public void setLedsager(Ledsager ledsager) {
        this.ledsager = ledsager;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

}

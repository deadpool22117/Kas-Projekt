package controller;

import model.*;

import org.jspecify.annotations.NullMarked;
import storage.Storage;
import java.time.LocalDate;

@NullMarked
public class Controller {

    public static Konference opretKonference(String name, LocalDate startDato, LocalDate slutDato, double prisPrDag) {
        Konference konference = new Konference(name, startDato, slutDato, prisPrDag);
        Storage.storeKonferencer(konference);
        return konference;
    }

    public static Tilmelding opretTilmelding (String tilmeldingID, LocalDate ankomstDato, LocalDate afrejseDato, boolean erForedragsHolder, Konference konference, Deltager deltager) {
        Tilmelding tilmelding = new Tilmelding(tilmeldingID, ankomstDato, afrejseDato, erForedragsHolder, konference, deltager);
        konference.addTilmelding(tilmelding);
        deltager.addTilmelding(tilmelding);
        return tilmelding;
    }

    public static Udflugt opretUdflugt(String navn, LocalDate dato, double pris, Konference konference) {
        Udflugt udflugt = new Udflugt(navn, dato, pris, konference);
        Storage.storeUdflugt(udflugt);
        return udflugt;
    }

    public static Hotel opretHotel(String hotelNavn, double prisEnkelt, double prisDobbelt) {
        Hotel hotel = new Hotel(hotelNavn, prisEnkelt, prisDobbelt);
        Storage.storeHotel(hotel);
        return hotel;
    }

    public static Ledsager opretLedsager(String navn, Tilmelding tilmelding) {
        Ledsager ledsager = new Ledsager(navn, tilmelding);
        Storage.storeLedsager(ledsager);
        return ledsager;
    }

    public static Deltager opretDeltager(String navn, String adresse, String mobil, String byLand) {
        Deltager deltager = new Deltager(navn, adresse, mobil,byLand);
        Storage.storeDeltager(deltager);
        return deltager;
    }

    public static Tillaeg oprettillaeg(String navn, double pris) {
        Tillaeg tillaeg = new Tillaeg(navn, pris);
        Storage.storeTillaeg(tillaeg);
        return tillaeg;
    }












}

package controller;

import model.*;


import org.jspecify.annotations.NullMarked;
import storage.Storage;
import java.time.LocalDate;
import java.util.ArrayList;

@NullMarked
public class Controller {

    public static ArrayList<String> deltagerePåKonference(Konference konference){
        ArrayList<String> liste = new ArrayList<>();

        for(Tilmelding t: konference.getTilmeldinger()){
            Deltager d = t.getDeltager();
            liste.add(d.getNavn() + " - " + d.getByLand());


        }
        return liste;
    }

    public static ArrayList<String> udflugterPåKonference(Konference konference){
        ArrayList<String> liste = new ArrayList<>();

        for(Udflugt u: konference.getUdflugter()){
            liste.add(u.getNavn() + " | " + u.getDato() + " | Pris: " + u.getPris() + " kr.");
        }
        return liste;
    }

    public static ArrayList<String> alleHoteller(Hotel hoteller){
        ArrayList<String> liste = new ArrayList<>();

        for(Hotel h: Storage.getHoteller()){
            liste.add(h.getHotelNavn() + " | Enkelt: " + h.getPrisEnkelt() + " kr." + " | Dobbelt: " + h.getPrisDobbelt()+ " kr.");
        }
        return liste;
    }

    public static ArrayList<String> deltagerKonferenceInfo(Deltager deltager){
        ArrayList<String> liste = new ArrayList<>();

        for(Tilmelding t: deltager.getTilmeldinger()){
            liste.add(t.getKonference().getNavn() + " | " + t.getAnkomstDato() + " -> " + t.getAfrejseDato());

            if (t.getHotel() != null) {
                liste.add("Hotel: " + t.getHotel().getHotelNavn());
            }
            if(t.getLedsager() != null){
                liste.add("Ledsager: " + t.getLedsager().getNavn());

                for(Udflugt u : t.getLedsager().getUdflugter()){
                    liste.add("Udflugt: " + u.getNavn());
                }
            }
            liste.add("Samlet pris: " + t.samletPris());
        }
        return liste;
    }

    /**
     * Create konference.
     * Pre: navn not empty, startDato <= endDato, prisPrDag >=0.
     */
    public static Konference opretKonference(String name, LocalDate startDato, LocalDate slutDato, double prisPrDag) {
        Konference konference = new Konference(name, startDato, slutDato, prisPrDag);
        Storage.storeKonferencer(konference);
        return konference;
    }

    /**
     * Create tilmelding
     *  Pre: ankomstDato <= afrejseDato, konference not null, deltager not null
     */
    public static Tilmelding opretTilmelding (LocalDate ankomstDato, LocalDate afrejseDato, boolean erForedragsHolder, Konference konference, Deltager deltager) {
        String tilmeldingID = "t" + (Storage.getTilmeldinger().size() + 1);
        Tilmelding tilmelding = new Tilmelding(tilmeldingID, ankomstDato, afrejseDato, erForedragsHolder, konference, deltager);
        konference.addTilmelding(tilmelding);
        deltager.addTilmelding(tilmelding);
        Storage.storeTilmeldinger(tilmelding);
        return tilmelding;
    }

    /**
     * Create udflugt
     * Pre: navn not empty, dato not empty, pris >= 0, konference not null
     */
    public static Udflugt opretUdflugt(String navn, LocalDate dato, double pris, Konference konference) {
        Udflugt udflugt = new Udflugt(navn, dato, pris, konference);
        Storage.storeUdflugt(udflugt);
        return udflugt;
    }

    /**
     * Create hotel
     * Pre: hotelNavn not empty, prisEnkelt >= 0, prisDobbelt >= 0
     */
    public static Hotel opretHotel(String hotelNavn, double prisEnkelt, double prisDobbelt) {
        Hotel hotel = new Hotel(hotelNavn, prisEnkelt, prisDobbelt);
        Storage.storeHotel(hotel);
        return hotel;
    }

    /**
     * Create ledsager
     * Pre: navn not empty, tilmelding not null
     */
    public static Ledsager opretLedsager(String navn, Tilmelding tilmelding) {
        Ledsager ledsager = new Ledsager(navn, tilmelding);
        Storage.storeLedsager(ledsager);
        return ledsager;
    }

    /**
     * create deltager
     * Pre: navn not empty, adresse not empty, mobil not empty, byLand not empty
     */
    public static Deltager opretDeltager(String navn, String adresse, String mobil, String byLand) {
        Deltager deltager = new Deltager(navn, adresse, mobil,byLand);
        Storage.storeDeltager(deltager);
        return deltager;
    }

    /**
     * create tillaeg
     * Pre: navn not empty, pris >= 0
     */
    public static Tillaeg opretTillaeg(String navn, double pris) {
        Tillaeg tillaeg = new Tillaeg(navn, pris);
        Storage.storeTillaeg(tillaeg);
        return tillaeg;
    }














}

package controller;

import model.*;


import storage.Storage;
import java.time.LocalDate;
import java.util.ArrayList;


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

    public static Tillaeg opretTillaeg(String navn, double pris) {
        Tillaeg tillaeg = new Tillaeg(navn, pris);
        Storage.storeTillaeg(tillaeg);
        return tillaeg;
    }














}

package storage;

import model.*;

import java.util.ArrayList;

public class Storage {
    private static final ArrayList<Konference> konferencer = new ArrayList<>();
    private static final ArrayList<Deltager> deltagere = new ArrayList<>();
    private static final ArrayList<Tilmelding> tilmeldinger = new ArrayList<>();
    private static final ArrayList<Udflugt> udflugter = new ArrayList<>();
    private static final ArrayList<Hotel> hoteller = new ArrayList<>();
    private static final ArrayList<Ledsager> ledsagere = new ArrayList<>();



    //--------Konferencer-------

    public static ArrayList<Konference> getKonferencer() {
        return new ArrayList<>(konferencer);
    }

    public static void storeKonferencer(Konference konference) {
        konferencer.add(konference);
    }

    //--------Deltager-------

    public static ArrayList<Deltager> getDeltager() {
        return new ArrayList<>(deltagere);
    }

    public static void storeDeltager(Deltager deltager) {
        deltagere.add(deltager);
    }

    //--------Tilmelding-------

    public static ArrayList<Tilmelding> getTilmeldinger() {
        return new ArrayList<>(tilmeldinger);
    }

    public static void storeTilmeldinger(Tilmelding tilmelding) {
        tilmeldinger.add(tilmelding);
    }

    //--------udflugter-------

    public static ArrayList<Udflugt> getUdflugter() {
        return new ArrayList<>(udflugter);
    }

    public static void storeUdflugt(Udflugt udflugt) {
        udflugter.add(udflugt);
    }

    //--------Hotel-------

    public static ArrayList<Hotel> getHoteller() {
        return new ArrayList<>(hoteller);
    }

    public static void storeHotel(Hotel hotel) {
        hoteller.add(hotel);
    }

    //--------Ledsager-------

    public static ArrayList<Ledsager> getLedsagere() {
        return new ArrayList<>(ledsagere);
    }

    public static void storeLedsager(Ledsager ledsager) {
        ledsagere.add(ledsager);
    }
}



package storage;

import model.*;

import java.util.ArrayList;

public class Storage {
    private static final ArrayList<Konference> konferencer = new ArrayList<>();
    private static final ArrayList<Deltager> deltagere = new ArrayList<>();
    private static final ArrayList<Tilmelding> tilmeldinger = new ArrayList<>();



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


}



package controller;

import model.*;
import org.jspecify.annotations.NullMarked;
import storage.Storage;

import java.time.LocalDate;

@NullMarked
public class Controller {

    public static Konference createKonference(String name, LocalDate startDato, LocalDate slutDato, double prisPrDag) {
        Konference konference = new Konference(name, startDato, slutDato, prisPrDag);
        Storage.storeKonferencer(konference);
        return konference;
    }

    public static Tilmelding createTilmelding (int tilmeldingID, LocalDate ankomstDato, LocalDate afrejseDato, boolean erForedragsHolder, Konference konference, Deltager deltager, Hotel hotel) {
        Tilmelding tilmelding = new Tilmelding(tilmeldingID, ankomstDato, afrejseDato, erForedragsHolder, konference, deltager, hotel);
        konference.addTilmelding(tilmelding);
        deltager.addTilmelding(tilmelding);
        return tilmelding;
        //Tilmelding er forbundet til både deltager og konference, men ikke den anden vej.
        // Derfor skaber vi den anden forbindelse ved at skrive .addTilmelding(tilmelding)
    }









}

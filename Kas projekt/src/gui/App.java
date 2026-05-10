package gui;

import controller.Controller;
import model.*;

import java.time.LocalDate;

public class App {
    void main(){
        initStorage();
        Main.launch(Main.class);
    }

    public static void initStorage() {
        Konference havOgHimmel = Controller.opretKonference("Hav og Himmel", LocalDate.of(2026,5, 18), LocalDate.of(2026,5,20),1500);
        Konference LavOgHimmel = Controller.opretKonference("lav og Himmel", LocalDate.of(2027,5, 18), LocalDate.of(2028,5,20),1500);

        Hotel denHvideSvane = Controller.opretHotel("Den hvide svane", 1050, 1250);
        Tillaeg wifi = Controller.opretTillaeg("Wifi", 50);
        denHvideSvane.addTillaeg(wifi);
        havOgHimmel.addHoteller(denHvideSvane);

        Hotel hoetelPhoenix = Controller.opretHotel("Høtel Phønix", 700, 800);
        Tillaeg wifi2 = Controller.opretTillaeg("Wifi", 75);
        hoetelPhoenix.addTillaeg(wifi2);
        Tillaeg bad = Controller.opretTillaeg("Bad", 200);
        hoetelPhoenix.addTillaeg(bad);
        havOgHimmel.addHoteller(hoetelPhoenix);

        Hotel pensionTusindfryd = Controller.opretHotel("Pension Tusindfryd", 500, 600);
        Tillaeg morgenmad = Controller.opretTillaeg("Morgenmad", 100);
        pensionTusindfryd.addTillaeg(morgenmad);
        havOgHimmel.addHoteller(pensionTusindfryd);

        Udflugt egeskov = Controller.opretUdflugt("Egeskov", LocalDate.of(2026,5,19), 75, havOgHimmel);
        Udflugt trapholt = Controller.opretUdflugt("Trapholt", LocalDate.of(2026,5,20), 200, havOgHimmel);
        Udflugt byrundtur = Controller.opretUdflugt("Byrundtur", LocalDate.of(2026,5,18), 125, havOgHimmel);

        //----Finn-----
        Deltager finn = Controller.opretDeltager("Finn Madsen", "Adresse 1", "23357712", "Odense");
        Tilmelding t1 = Controller.opretTilmelding(LocalDate.of(2026,5,18),LocalDate.of(2026,5,20), false, havOgHimmel, finn);

        //----Niels-----
        Deltager niels = Controller.opretDeltager("Niels Petersen", "Adresse 2", "55357712", "Skive");
        Tilmelding t2 = Controller.opretTilmelding( LocalDate.of(2026,5,18), LocalDate.of(2026,5,20), false, havOgHimmel, niels);
        t2.setHotel(denHvideSvane);

        //----Ulla-----
        Deltager ulla = Controller.opretDeltager("Ulla Hansen", "Adresse 3", "95357712", "København");
        Tilmelding t3 = Controller.opretTilmelding( LocalDate.of(2026,5,18), LocalDate.of(2026,5,19), false, havOgHimmel, ulla);
        Ledsager hans = Controller.opretLedsager("Hans Hansen", t3);
        t3.setLedsager(hans);
        hans.addUdflugt(byrundtur);

        //----Peter-----
        Deltager peter = Controller.opretDeltager("Peter Sommer", "Adresse 4", "00357712", "Aarhus");
        Tilmelding t4 = Controller.opretTilmelding( LocalDate.of(2026,5,18),LocalDate.of(2026,5,20), false, havOgHimmel, peter);
        t4.setHotel(denHvideSvane);
        Ledsager mie = Controller.opretLedsager("Mie Sommer", t4);
        t4.setLedsager(mie);
        mie.addUdflugt(egeskov);
        mie.addUdflugt(trapholt);
        t4.getValgteTillaeg().add(wifi);

        //----Lone-----
        Deltager lone = Controller.opretDeltager("Lone Jensen", "Adresse 5", "11357712", "Aarhus");
        Tilmelding t5 = Controller.opretTilmelding( LocalDate.of(2026,5,18),LocalDate.of(2026,5,20), true, havOgHimmel, lone);
        t5.setHotel(denHvideSvane);
        Ledsager jan = Controller.opretLedsager("Jan Madsen", t5);
        t5.setLedsager(jan);
        jan.addUdflugt(egeskov);
        jan.addUdflugt(byrundtur);
        t5.getValgteTillaeg().add(wifi);
    }
}

package test;

import controller.Controller;
import model.*;

import java.time.LocalDate;

public class KasTest {
    public static void main(String[] args) {
        Konference havOgHimmel = Controller.opretKonference("Hav og Himmel", LocalDate.of(2026,5, 18), LocalDate.of(2026,5,20),1500);

        Hotel denHvideSvane = Controller.opretHotel("Den hvide svane", 1050, 1250);
        Tillaeg wifi = Controller.opretTillaeg("Wifi", 50);

        denHvideSvane.addTillaeg(wifi);
        havOgHimmel.addHoteller(denHvideSvane);



        Udflugt egeskov = Controller.opretUdflugt("Egeskov", LocalDate.of(2026,5,19), 75, havOgHimmel);
        Udflugt trapholt = Controller.opretUdflugt("Trapholt", LocalDate.of(2026,5,20), 200, havOgHimmel);
        Udflugt byrundtur = Controller.opretUdflugt("Byrundtur", LocalDate.of(2026,5,18), 125, havOgHimmel);

        //----Finn-----
        Deltager finn = Controller.opretDeltager("Finn Madsen", "Adresse 1", "23357712", "Odense");
        Tilmelding t1 = Controller.opretTilmelding(LocalDate.of(2026,5,18),LocalDate.of(2026,5,20), false, havOgHimmel, finn);
        IO.println("Forventet pris: 4500 kr.");
        IO.println("Finn samlet pris: " + t1.samletPris());

        //----Niels-----
        Deltager niels = Controller.opretDeltager("Niels Petersen", "Adresse 2", "55357712", "Skive");
        Tilmelding t2 = Controller.opretTilmelding( LocalDate.of(2026,5,18), LocalDate.of(2026,5,20), false, havOgHimmel, niels);
        t2.setHotel(denHvideSvane);
        IO.println();
        IO.println("Forventet pris: 6600 kr.");
        IO.println("Niels samlet pris:  " + t2.samletPris());

        //----Ulla-----
        Deltager ulla = Controller.opretDeltager("Ulla Hansen", "Adresse 3", "95357712", "København");
        Tilmelding t3 = Controller.opretTilmelding( LocalDate.of(2026,5,18), LocalDate.of(2026,5,19), false, havOgHimmel, ulla);
        Ledsager hans = Controller.opretLedsager("Hans Hansen", t3);
        t3.setLedsager(hans);
        hans.addUdflugt(byrundtur);
        IO.println();
        IO.println("Forventet pris: 3125 kr.");
        IO.println("Ulla samlet pris: " + t3.samletPris());

        //----Peter-----
        Deltager peter = Controller.opretDeltager("Peter Sommer", "Adresse 4", "00357712", "Aarhus");
        Tilmelding t4 = Controller.opretTilmelding( LocalDate.of(2026,5,18),LocalDate.of(2026,5,20), false, havOgHimmel, peter);
        t4.setHotel(denHvideSvane);
        Ledsager mie = Controller.opretLedsager("Mie Sommer", t4);
        t4.setLedsager(mie);
        mie.addUdflugt(egeskov);
        mie.addUdflugt(trapholt);
        t4.getValgteTillaeg().add(wifi);
        IO.println();
        IO.println("Forventet pris: 7375 kr.");
        IO.println("Peter samlet pris: " + t4.samletPris());

        //----Lone-----
        Deltager lone = Controller.opretDeltager("Lone Jensen", "Adresse 5", "11357712", "Aarhus");
        Tilmelding t5 = Controller.opretTilmelding( LocalDate.of(2026,5,18),LocalDate.of(2026,5,20), true, havOgHimmel, lone);
        t5.setHotel(denHvideSvane);
        Ledsager jan = Controller.opretLedsager("Jan Madsen", t5);
        t5.setLedsager(jan);
        jan.addUdflugt(egeskov);
        jan.addUdflugt(byrundtur);
        t5.getValgteTillaeg().add(wifi);
        IO.println();
        IO.println("Forventet pris: 2800 kr.");
        IO.println("Lone samlet pris: " + t5.samletPris());
    }
}

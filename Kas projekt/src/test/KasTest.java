package test;

import controller.Controller;
import model.*;

import java.time.LocalDate;

public class KasTest {
    public static void main(String[] args) {
        Konference k = Controller.opretKonference("Hav og Himmel", LocalDate.of(2026,5, 18), LocalDate.of(2026,5,20),1500);

        Hotel svane = Controller.opretHotel("Den hvide svane", 1050, 1250);
        Tillaeg tillaeg = Controller.oprettillaeg("Wifi", 50);

        Udflugt egeskov = Controller.opretUdflugt("Egeskov", LocalDate.of(2026,5,19), 300, k);
        Udflugt trapholt = Controller.opretUdflugt("Trapholt", LocalDate.of(2026,5,20), 200, k);
        Udflugt byrundtur = Controller.opretUdflugt("Byrundtur", LocalDate.of(2026,5,18), 125, k);

        Deltager finn = Controller.opretDeltager("Finn Madsen", "H.C andersensvej 9", "23357712", "Odense");
        Tilmelding t1 = Controller.opretTilmelding("t1", LocalDate.of(2026,5,18),LocalDate.of(2026,5,20), false, k, finn, svane);
        IO.println("Finn: " + t1.samletPris());

        Deltager niels = Controller.opretDeltager("Niels Petersen", "Ringvej 13, 5 tv", "55357712", "Skive");
        Tilmelding t2 = Controller.opretTilmelding("t2", LocalDate.of(2026,5,18), LocalDate.of(2026,5,20), false, k, niels, svane);
        IO.println("Niels: " + t2.samletPris());

        Deltager ulla = Controller.opretDeltager("Ulla Hansen", "Åbyhøjgårdvej 50", "95357712", "København");
        Tilmelding t3 = Controller.opretTilmelding("t3", LocalDate.of(2026,5,18), LocalDate.of(2026,5,20), false, k, ulla);
        IO.println("Ulla: " + t3.samletPris());

        Deltager peter = Controller.opretDeltager("Peter Sommer", "Sigridsvej 35", "00357712", "Aarhus");
        Deltager lone = Controller.opretDeltager("Lone Jensen", "Ingridsensvej 22, 3 TH", "11357712", "Aarhus");

        Tilmelding t4 = Controller.opretTilmelding("t4", LocalDate.of(2026,5,18),20-5-2026, false, k, peter, svane);
        Tilmelding t5 = Controller.opretTilmelding("t5", LocalDate.of(2026,5,18),20-5-2026, true, k, lone, svane);

    }
}

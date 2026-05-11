package model;



import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;


@NullMarked
public class Tilmelding {
    private String tilmeldingID;
    private LocalDate ankomstDato;
    private LocalDate afrejseDato;
    private boolean erForedragsHolder;

    //LinkAttributter
    private final Konference konference;
    private @Nullable Hotel hotel;
    private Deltager deltager;
    private @Nullable Ledsager ledsager;
    private @Nullable Firma firma;
    private final ArrayList<Tillaeg> valgteTillaeg = new ArrayList<>();

    public Tilmelding(String tilmeldingID, LocalDate ankomstDato, LocalDate afrejseDato, boolean erForedragsHolder, Konference konference, Deltager deltager) {
        this.tilmeldingID = tilmeldingID;
        this.ankomstDato = ankomstDato;
        this.afrejseDato = afrejseDato;
        this.erForedragsHolder = erForedragsHolder;
        this.konference = konference;
        this.deltager = deltager;
    }

    public LocalDate getAnkomstDato() {
        return ankomstDato;
    }

    public LocalDate getAfrejseDato() {
        return afrejseDato;
    }

    public Konference getKonference() {
        return konference;
    }

    public String getTilmeldingID() {
        return tilmeldingID;
    }

    public Deltager getDeltager() {
        return deltager;
    }
    public void setLedsager(Ledsager ledsager) {
        this.ledsager = ledsager;
    }

    public @Nullable Ledsager getLedsager() {
        return ledsager;
    }
    public @Nullable Hotel getHotel() {
        return hotel;
    }

    public ArrayList<Tillaeg> getValgteTillaeg() {
        return valgteTillaeg;
    }


    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }


    public void setFirma(Firma firma) {
        this.firma = firma;
    }



    public double samletPris() {
        double samletPris = 0;
        int periode = (int) ankomstDato.until(afrejseDato, ChronoUnit.DAYS) + 1;

        if (!erForedragsHolder) {
            samletPris += konference.getPrisPrDag() * periode;
        }

        if (ledsager != null) {
            ArrayList<Udflugt> udflugter = ledsager.getUdflugter();
            if (!udflugter.isEmpty()) {
                for (Udflugt u : udflugter) {
                    samletPris += u.getPris();
                }
            }
        }

        if (hotel != null) {
            int hotelPeriode = periode - 1; // Man betaler pr overnatning, ikke pr dag

            if (ledsager != null) {
                samletPris += hotel.getPrisDobbelt() * hotelPeriode;
            } else {
                samletPris += hotel.getPrisEnkelt() * hotelPeriode;
            }

            if (!valgteTillaeg.isEmpty()) {
                for (Tillaeg t : valgteTillaeg) {
                    samletPris += t.getPris() * hotelPeriode;
                }
            }
        }
        return samletPris;
    }

    @Override
    public String toString() {
        return tilmeldingID + " " + deltager.getNavn() + " (mblnr: " + deltager.getMobil() + ")";
    }
}

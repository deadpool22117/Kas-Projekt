package model;



import java.util.ArrayList;

public class Deltager {
    private String navn;
    private String adresse;
    private String mobil;
    private String byLand;

    //LinkAttributter
    private final ArrayList<Tilmelding> tilmeldinger = new ArrayList<>();

    public Deltager(String navn, String adresse, String mobil, String byLand) {
        this.navn = navn;
        this.adresse = adresse;
        this.mobil = mobil;
        this.byLand = byLand;
    }

    public String getNavn() {
        return navn;
    }

    public String getAdresse() {
        return adresse;
    }

    public String getMobil() {
        return mobil;
    }

    public String getByLand() {
        return byLand;
    }

    public ArrayList<Tilmelding> getTilmeldinger() {
        return tilmeldinger;
    }

    public void addTilmelding(Tilmelding tilmelding) {
        tilmeldinger.add(tilmelding);
    }
}

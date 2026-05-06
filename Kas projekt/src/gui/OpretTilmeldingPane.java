package gui;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import model.*;
import storage.Storage;

import java.time.LocalDate;
import java.util.ArrayList;

public class OpretTilmeldingPane extends GridPane {

    private TextField txfNavn;
    private TextField txfAdresse;
    private TextField txfMobil;
    private TextField txfByLand;

    private DatePicker dpAnkomst;
    private DatePicker dpAfrejse;

    private CheckBox chbForedragsholder;
    private CheckBox chbLedsager;

    private TextField txfLedsagerNavn;

    private ComboBox<Konference> cbKonference;
    private ComboBox<Hotel> cbHotel;

    private ListView<Udflugt> lvwUdflugter;

    private Button btnOpret;
    private Button btnRyd;

    public OpretTilmeldingPane() {
        this.setPadding(new Insets(20));
        this.setHgap(10);
        this.setVgap(10);

        // ---------------------------------------------------
        // Deltagerinformation
        // ---------------------------------------------------

        this.add(new Label("Deltagernavn:"), 0, 0);
        txfNavn = new TextField();
        this.add(txfNavn, 1, 0);

        this.add(new Label("Adresse:"), 0, 1);
        txfAdresse = new TextField();
        this.add(txfAdresse, 1, 1);

        this.add(new Label("Mobil:"), 0, 2);
        txfMobil = new TextField();
        this.add(txfMobil, 1, 2);

        this.add(new Label("By / Land:"), 0, 3);
        txfByLand = new TextField();
        this.add(txfByLand, 1, 3);

        // ---------------------------------------------------
        // Datoer
        // ---------------------------------------------------

        this.add(new Label("Ankomstdato:"), 0, 4);
        dpAnkomst = new DatePicker();
        this.add(dpAnkomst, 1, 4);

        this.add(new Label("Afrejsedato:"), 0, 5);
        dpAfrejse = new DatePicker();
        this.add(dpAfrejse, 1, 5);

        // ---------------------------------------------------
        // Foredragsholder
        // ---------------------------------------------------

        chbForedragsholder = new CheckBox("Foredragsholder");
        this.add(chbForedragsholder, 1, 6);

        // ---------------------------------------------------
        // Konference
        // ---------------------------------------------------

        this.add(new Label("Konference:"), 0, 7);
        cbKonference = new ComboBox<>();
        cbKonference.getItems().addAll(Storage.getKonferencer());
        this.add(cbKonference, 1, 7);

        // ---------------------------------------------------
        // Hotel
        // ---------------------------------------------------

        this.add(new Label("Hotel:"), 0, 8);
        cbHotel = new ComboBox<>();
        cbHotel.getItems().addAll(Storage.getHoteller());
        this.add(cbHotel, 1, 8);

        // ---------------------------------------------------
        // Ledsager
        // ---------------------------------------------------

        chbLedsager = new CheckBox("Har ledsager");
        this.add(chbLedsager, 0, 9);

        txfLedsagerNavn = new TextField();
        txfLedsagerNavn.setPromptText("Ledsagers navn");
        this.add(txfLedsagerNavn, 1, 9);

        // ---------------------------------------------------
        // Udflugter
        // ---------------------------------------------------

        this.add(new Label("Udflugter:"), 0, 10);

        lvwUdflugter = new ListView<>();
        lvwUdflugter.getItems().addAll(Storage.getUdflugter());
        lvwUdflugter.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        lvwUdflugter.setPrefHeight(120);
        lvwUdflugter.setPrefWidth(300);

        this.add(lvwUdflugter, 1, 10);

        // ---------------------------------------------------
        // Knapper
        // ---------------------------------------------------

        btnOpret = new Button("Opret tilmelding");
        this.add(btnOpret, 0, 11);

        btnRyd = new Button("Ryd felter");
        this.add(btnRyd, 1, 11);

        btnOpret.setOnAction(event -> opretTilmeldingAction());
        btnRyd.setOnAction(event -> rydFelter());
    }

    private void opretTilmeldingAction() {

        // ---------------------------------------------------
        // Hent deltagerdata
        // ---------------------------------------------------

        String navn = txfNavn.getText();
        String adresse = txfAdresse.getText();
        String mobil = txfMobil.getText();
        String byLand = txfByLand.getText();

        // ---------------------------------------------------
        // Hent datoer
        // ---------------------------------------------------

        LocalDate ankomstDato = dpAnkomst.getValue();
        LocalDate afrejseDato = dpAfrejse.getValue();

        // ---------------------------------------------------
        // Hent valg
        // ---------------------------------------------------

        boolean erForedragsholder = chbForedragsholder.isSelected();

        Konference konference =
                cbKonference.getSelectionModel().getSelectedItem();

        Hotel hotel =
                cbHotel.getSelectionModel().getSelectedItem();

        // ---------------------------------------------------
        // Validering
        // ---------------------------------------------------

        if (navn.isEmpty() || adresse.isEmpty() || mobil.isEmpty() || byLand.isEmpty()) {
            visFejl("Udfyld alle deltageroplysninger.");
            return;
        }

        if (ankomstDato == null || afrejseDato == null) {
            visFejl("Vælg både ankomstdato og afrejsedato.");
            return;
        }

        if (afrejseDato.isBefore(ankomstDato)) {
            visFejl("Afrejsedato kan ikke være før ankomstdato.");
            return;
        }

        if (konference == null) {
            visFejl("Vælg en konference.");
            return;
        }

        // ---------------------------------------------------
        // Opret deltager
        // ---------------------------------------------------

        Deltager deltager = new Deltager(
                navn,
                adresse,
                mobil,
                byLand
        );

        // ---------------------------------------------------
        // Opret tilmelding
        // ---------------------------------------------------

        String tilmeldingID =
                "T" + (Storage.getTilmeldinger().size() + 1);

        Tilmelding tilmelding = new Tilmelding(
                tilmeldingID,
                ankomstDato,
                afrejseDato,
                erForedragsholder,
                konference,
                deltager
        );

        // ---------------------------------------------------
        // Sæt hotel hvis valgt
        // ---------------------------------------------------

        if (hotel != null) {
            tilmelding.setHotel(hotel);
        }

        // ---------------------------------------------------
        // Opret ledsager hvis valgt
        // ---------------------------------------------------

        if (chbLedsager.isSelected()) {

            String ledsagerNavn = txfLedsagerNavn.getText();

            if (ledsagerNavn.isEmpty()) {
                visFejl("Indtast navn på ledsager.");
                return;
            }

            Ledsager ledsager = new Ledsager(
                    ledsagerNavn,
                    tilmelding
            );

            ArrayList<Udflugt> valgteUdflugter =
                    new ArrayList<>(
                            lvwUdflugter
                                    .getSelectionModel()
                                    .getSelectedItems()
                    );

            for (Udflugt udflugt : valgteUdflugter) {
                ledsager.addUdflugt(udflugt);
            }

            tilmelding.setLedsager(ledsager);
            Storage.storeLedsager(ledsager);
        }

        // ---------------------------------------------------
        // Opret relationer
        // ---------------------------------------------------

        deltager.addTilmelding(tilmelding);
        konference.addTilmelding(tilmelding);

        // ---------------------------------------------------
        // Gem i Storage
        // ---------------------------------------------------

        Storage.storeDeltager(deltager);
        Storage.storeTilmeldinger(tilmelding);

        // ---------------------------------------------------
        // Vis besked
        // ---------------------------------------------------

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Tilmelding oprettet");
        alert.setHeaderText("Tilmelding blev oprettet");
        alert.setContentText(
                "Deltager: " + deltager.getNavn()
                        + "\nTilmelding ID: " + tilmeldingID
                        + "\nSamlet pris: " + tilmelding.samletPris() + " kr."
        );

        alert.showAndWait();

        rydFelter();
    }

    private void rydFelter() {
        txfNavn.clear();
        txfAdresse.clear();
        txfMobil.clear();
        txfByLand.clear();

        dpAnkomst.setValue(null);
        dpAfrejse.setValue(null);

        chbForedragsholder.setSelected(false);
        chbLedsager.setSelected(false);

        txfLedsagerNavn.clear();

        cbKonference.getSelectionModel().clearSelection();
        cbHotel.getSelectionModel().clearSelection();

        lvwUdflugter.getSelectionModel().clearSelection();
    }

    private void visFejl(String besked) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Fejl");
        alert.setHeaderText("Manglende oplysninger");
        alert.setContentText(besked);
        alert.showAndWait();
    }
}
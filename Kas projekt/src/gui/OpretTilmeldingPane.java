package gui;

import controller.Controller;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.time.LocalDate;
import java.util.ArrayList;

import model.*;

public class OpretTilmeldingPane extends GridPane {

    private TextField txfNavn;
    private TextField txfAdresse;
    private TextField txfMobil;
    private TextField txfByLand;

    private DatePicker dpAnkomst;
    private DatePicker dpAfrejse;

    private CheckBox chbOpretNyDeltager;
    private CheckBox chbForedragsholder;
    private CheckBox chbLedsager;

    private TextField txfLedsagerNavn;

    private ListView lvwDeltagere;

    private CheckBox chbFirma;
    private TextField txfFirmaNavn;
    private TextField txfFirmaTlf;

    private ComboBox<Konference> cbKonference;
    private TextField txfKonferenceDatoer;
    private ComboBox<Hotel> cbHotel;

    private ListView<Udflugt> lvwUdflugter;

    private Button btnOpret;
    private Button btnRyd;

    public OpretTilmeldingPane() {
        this.setPadding(new Insets(20));
        this.setHgap(10);
        this.setVgap(10);


        // ---------------------------------------------------
        // Deltagere
        // -------------------------------------------------

        lvwDeltagere = new ListView<>();
        this.add(lvwDeltagere, 1, 0, 1,4);
        lvwDeltagere.getItems().setAll(
                Controller.getDeltager());

        // ---------------------------------------------------
        // Deltagerinformation
        // ---------------------------------------------------

       // this.add(new Label("Deltagernavn:"), 2, 0);
        txfNavn = new TextField();
        txfNavn.setPromptText("Deltagernavn");
        this.add(txfNavn, 2, 0);

        //this.add(new Label("Adresse:"), 2, 1);
        txfAdresse = new TextField();
        txfAdresse.setPromptText("Adresse");
        this.add(txfAdresse, 2, 1);

        //this.add(new Label("Mobil:"), 2, 2);
        txfMobil = new TextField();
        txfMobil.setPromptText("Mobil");
        this.add(txfMobil, 2, 2);

        //this.add(new Label("By / Land:"), 2, 3);
        txfByLand = new TextField();
        txfByLand.setPromptText("By / Land:");
        this.add(txfByLand, 2, 3);

        chbOpretNyDeltager = new CheckBox("Opret ny deltager");
        this.add(chbOpretNyDeltager, 0, 0);
        chbOpretNyDeltager.setOnAction(event -> nyDeltagerChb());
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
        cbKonference.getItems().addAll(Controller.getKonferencer());
        this.add(cbKonference, 1, 7);

        txfKonferenceDatoer = new TextField();
        this.add(txfKonferenceDatoer, 1, 8);
        txfKonferenceDatoer.setEditable(false);


        // ---------------------------------------------------
        // Hotel
        // ---------------------------------------------------

        this.add(new Label("Hotel:"), 0, 9);
        cbHotel = new ComboBox<>();
        cbKonference.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> opdaterDatoerHotellerOgUdflugter());
        this.add(cbHotel, 1, 9);

        // ---------------------------------------------------
        // Ledsager
        // ---------------------------------------------------

        chbLedsager = new CheckBox("Har ledsager");
        this.add(chbLedsager, 0, 10);

        txfLedsagerNavn = new TextField();
        txfLedsagerNavn.setPromptText("Ledsagers navn");
        this.add(txfLedsagerNavn, 1, 10);

        // ---------------------------------------------------
        // Firma
        // ---------------------------------------------------
        chbFirma = new CheckBox("Firma");
        this.add(chbFirma, 0, 11);

        txfFirmaNavn = new TextField();
        txfFirmaNavn.setPromptText("Firma navn");
        this.add(txfFirmaNavn, 1, 11);

        txfFirmaTlf = new TextField();
        txfFirmaTlf.setPromptText("Firma Tlf.");
        this.add(txfFirmaTlf, 2, 11);

        // ---------------------------------------------------
        // Udflugter
        // ---------------------------------------------------

        this.add(new Label("Udflugter:"), 0, 12);

        lvwUdflugter = new ListView<>();
        lvwUdflugter.getItems().addAll(Controller.getUdflugter());
        lvwUdflugter.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        lvwUdflugter.setPrefHeight(120);
        lvwUdflugter.setPrefWidth(300);

        this.add(lvwUdflugter, 1, 12);

        // ---------------------------------------------------
        // Knapper
        // ---------------------------------------------------

        btnOpret = new Button("Opret tilmelding");
        this.add(btnOpret, 0, 13);

        btnRyd = new Button("Ryd felter");
        this.add(btnRyd, 1, 13);

        btnOpret.setOnAction(event -> opretTilmeldingAction());
        btnRyd.setOnAction(event -> rydFelter());
        disableDeltagerOplysninger();

    }
    //---------------------------------------------------
    // opdater tildmeldingPane
    // --------------------------------------------------

    public void opdater() {
        cbKonference.getItems().setAll(Controller.getKonferencer());

        cbKonference.getSelectionModel().clearSelection();

        cbHotel.getItems().clear();
        cbHotel.getSelectionModel().clearSelection();

        lvwUdflugter.getItems().clear();
        lvwUdflugter.getSelectionModel().clearSelection();

    }

    private void opretTilmeldingAction() {
        Deltager deltager;

        if(chbOpretNyDeltager.isSelected()) {
            // ---------------------------------------------------
            // Hent deltagerdata
            // ---------------------------------------------------

            String navn = txfNavn.getText();
            String adresse = txfAdresse.getText();
            String mobil = txfMobil.getText();
            String byLand = txfByLand.getText();


            // ---------------------------------------------------
            // Validering
            // ---------------------------------------------------

            if (navn.isEmpty() || adresse.isEmpty() || mobil.isEmpty() || byLand.isEmpty()) {
                visFejl("Udfyld alle deltageroplysninger.");
                return;
            }



            // ---------------------------------------------------
            // Opret deltager
            // ---------------------------------------------------

            deltager = Controller.opretDeltager(navn, adresse, mobil, byLand);
        } else {
            deltager = (Deltager) lvwDeltagere.getSelectionModel().getSelectedItem();
        }

        Konference konference = cbKonference.getSelectionModel().getSelectedItem();

        Hotel hotel = cbHotel.getSelectionModel().getSelectedItem();

        // ---------------------------------------------------
        // Hent datoer
        // ---------------------------------------------------

        LocalDate ankomstDato = dpAnkomst.getValue();
        LocalDate afrejseDato = dpAfrejse.getValue();

        // ---------------------------------------------------
        // Hent valg
        // ---------------------------------------------------

        boolean erForedragsholder = chbForedragsholder.isSelected();



        // ---------------------------------------------------
        // Validering
        // ---------------------------------------------------

        if (ankomstDato == null || afrejseDato == null) {
            visFejl("Vælg både ankomstdato og afrejsedato.");
            return;
        }

        if (afrejseDato.isBefore(ankomstDato)) {
            visFejl("Afrejsedato kan ikke være før ankomstdato.");
            return;
        }

        if (ankomstDato.isBefore(konference.getStartDato()) || afrejseDato.isAfter(konference.getSlutDato())) {
            visFejl("Ankomst of afrejsedato skal være inden for konferencens tidsperiode.");
            return;
        }

        if (konference == null) {
            visFejl("Vælg en konference.");
            return;
        }

        // ---------------------------------------------------
        // Opret tilmelding
        // ---------------------------------------------------

        String tilmeldingID = "T" + (Controller.getTilmeldinger().size() + 1);

        Tilmelding tilmelding = Controller.opretTilmelding(ankomstDato, afrejseDato, erForedragsholder, konference, deltager);

        // ---------------------------------------------------
        // Sæt hotel hvis valgt
        // ---------------------------------------------------

        if (hotel != null) {
            Controller.setHotel(tilmelding, hotel);
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

            Ledsager ledsager = Controller.opretLedsager(ledsagerNavn, tilmelding);

            ArrayList<Udflugt> valgteUdflugter = new ArrayList<>(lvwUdflugter.getSelectionModel().getSelectedItems());

            for (Udflugt udflugt : valgteUdflugter) {
                Controller.addUdflugtToLedsager(ledsager, udflugt);
            }

        }

        // ---------------------------------------------------
        // Opret firma hvis valgt
        // ---------------------------------------------------

        if (chbFirma.isSelected()) {
            String firmaNavn = txfFirmaNavn.getText();
            String firmaTlf = txfFirmaTlf.getText();

            if (firmaNavn.isEmpty()) {
                visFejl("Indtast navn på firma.");
                return;
            }

            if (firmaTlf.isEmpty()) {
                visFejl("Indtast firma tlf.");
                return;
            }

            Controller.setFirmaToTilmelding(Controller.opretFirma(firmaNavn, firmaTlf), tilmelding);
        }

        // ---------------------------------------------------
        // Vis besked
        // ---------------------------------------------------

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Tilmelding oprettet");
        alert.setHeaderText("Tilmelding blev oprettet");
        alert.setContentText("Deltager: " + deltager.getNavn() + "\nTilmelding ID: " + tilmeldingID + "\nSamlet pris: " + tilmelding.samletPris() + " kr.");

        alert.showAndWait();

        rydFelter();
    }

    private void nyDeltagerChb() {
        if (chbOpretNyDeltager.isSelected()) {
            eneableDeltagerOplysninger();
            lvwDeltagere.setDisable(true);
        } else {
            disableDeltagerOplysninger();
            lvwDeltagere.setDisable(false);
        }
    }

    private void opdaterDatoerHotellerOgUdflugter() {
        Konference konference = cbKonference.getSelectionModel().getSelectedItem();

        cbHotel.getItems().clear();
        lvwUdflugter.getItems().clear();

        if (konference != null) {
            txfKonferenceDatoer.setText("Fra " + konference.getStartDato() + " til " + konference.getSlutDato());
            cbHotel.getItems().setAll(konference.getHoteller());
            lvwUdflugter.getItems().setAll(konference.getUdflugter());
        }
    }

    private void eneableDeltagerOplysninger() {
        txfNavn.setDisable(false);
        txfAdresse.setDisable(false);
        txfMobil.setDisable(false);
        txfByLand.setDisable(false);
    }

    private void disableDeltagerOplysninger() {
        txfNavn.setDisable(true);
        txfAdresse.setDisable(true);
        txfMobil.setDisable(true);
        txfByLand.setDisable(true);
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

        txfFirmaNavn.clear();
        txfFirmaTlf.clear();

        txfKonferenceDatoer.clear();

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
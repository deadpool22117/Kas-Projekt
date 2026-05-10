package gui;

import controller.Controller;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import model.Hotel;
import model.Konference;
import model.Tilmelding;
import storage.Storage;

import javax.swing.*;

public class HotelPane extends GridPane {

    private ListView<Hotel> lvwHoteller;
    private ListView<Tilmelding> lvwTilmeldinger;

    private TextField txfNavn;
    private TextField txfPrisEnkelt;
    private TextField txfPrisDobbelt;

    private ComboBox<Konference> cbKonference;

    private Button btnOpretHotel;
    private Button btnVisDeltagere;
    private Button btnRyd;

    public HotelPane() {
        this.setPadding(new Insets(20));
        this.setHgap(20);
        this.setVgap(10);

        // ---------------------------------------------------
        // Venstre side - liste over hoteller
        // ---------------------------------------------------

        Label lblHoteller = new Label("Hoteller");
        this.add(lblHoteller, 0, 0);

        lvwHoteller = new ListView<>();
        lvwHoteller.setPrefWidth(250);
        lvwHoteller.setPrefHeight(350);
        lvwHoteller.getItems().addAll(Storage.getHoteller());
        this.add(lvwHoteller, 0, 1, 1, 6);

        btnVisDeltagere = new Button("Vis deltagere på hotel");
        this.add(btnVisDeltagere, 0, 7);

        // ---------------------------------------------------
        // Midten - deltagere/tilmeldinger på valgt hotel
        // ---------------------------------------------------

        Label lblTilmeldinger = new Label("Tilmeldinger på valgt hotel");
        this.add(lblTilmeldinger, 1, 0);

        lvwTilmeldinger = new ListView<>();
        lvwTilmeldinger.setPrefWidth(350);
        lvwTilmeldinger.setPrefHeight(350);
        this.add(lvwTilmeldinger, 1, 1, 1, 6);

        // ---------------------------------------------------
        // Højre side - opret hotel
        // ---------------------------------------------------

        Label lblOpret = new Label("Opret nyt hotel");
        this.add(lblOpret, 2, 0);

        this.add(new Label("Hotelnavn:"), 2, 1);
        txfNavn = new TextField();
        this.add(txfNavn, 3, 1);

        this.add(new Label("Pris enkeltværelse:"), 2, 2);
        txfPrisEnkelt = new TextField();
        this.add(txfPrisEnkelt, 3, 2);

        this.add(new Label("Pris dobbeltværelse:"), 2, 3);
        txfPrisDobbelt = new TextField();
        this.add(txfPrisDobbelt, 3, 3);

        this.add(new Label("Konference:"), 2, 4);
        cbKonference = new ComboBox<>();
        cbKonference.getItems().setAll(Storage.getKonferencer());
        this.add(cbKonference, 3, 4);


        btnOpretHotel = new Button("Opret hotel");
        this.add(btnOpretHotel, 2, 5);

        btnRyd = new Button("Ryd felter");
        this.add(btnRyd, 3, 6);

        // ---------------------------------------------------
        // Actions
        // ---------------------------------------------------

        btnOpretHotel.setOnAction(event -> opretHotelAction());

        btnVisDeltagere.setOnAction(event -> visDeltagereAction());

        btnRyd.setOnAction(event -> rydFelter());

        lvwHoteller
                .getSelectionModel()
                .selectedItemProperty()
                .addListener((obs, oldValue, newValue) -> visDeltagereAction());
    }

    private void opretHotelAction() {
        String navn = txfNavn.getText();
        String prisEnkeltTekst = txfPrisEnkelt.getText();
        String prisDobbeltTekst = txfPrisDobbelt.getText();

        Konference konference = cbKonference.getSelectionModel().getSelectedItem();

        if (navn.isEmpty()) {
            visFejl("Indtast hotelnavn.");
            return;
        }

        if (prisEnkeltTekst.isEmpty() || prisDobbeltTekst.isEmpty()) {
            visFejl("Indtast både pris for enkeltværelse og dobbeltværelse.");
            return;
        }

        double prisEnkelt;
        double prisDobbelt;

        try {
            prisEnkelt = Double.parseDouble(prisEnkeltTekst);
            prisDobbelt = Double.parseDouble(prisDobbeltTekst);
        } catch (NumberFormatException e) {
            visFejl("Priser skal være tal.");
            return;
        }


        Controller.addHoteltoKonference(konference, Controller.opretHotel(navn, prisEnkelt, prisDobbelt));

        opdaterHoteller();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Hotel oprettet");
        alert.setHeaderText("Hotellet blev oprettet");
        alert.setContentText(
                "Hotel: " + navn
       //                 + "\nKonference: " + konference.getNavn()
                        + "\nPris enkeltværelse: " + prisEnkelt + " kr."
                        + "\nPris dobbeltværelse: " + prisDobbelt + " kr."

        );
        alert.showAndWait();

        rydFelter();
    }

    private void visDeltagereAction() {
        lvwTilmeldinger.getItems().clear();

        Hotel valgtHotel =
                lvwHoteller
                        .getSelectionModel()
                        .getSelectedItem();

        if (valgtHotel == null) {
            return;
        }

        for (Tilmelding tilmelding : Storage.getTilmeldinger()) {
            if (tilmelding.getHotel() != null
                    && tilmelding.getHotel().equals(valgtHotel)) {

                lvwTilmeldinger.getItems().add(tilmelding);
            }
        }
    }

    private void opdaterHoteller() {
        lvwHoteller.getItems().setAll(
                Controller.getHoteller()
        );
    }

    private void rydFelter() {
        txfNavn.clear();
        txfPrisEnkelt.clear();
        txfPrisDobbelt.clear();

        lvwHoteller.getSelectionModel().clearSelection();
        lvwTilmeldinger.getItems().clear();
    }

    private void visFejl(String besked) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Fejl");
        alert.setHeaderText("Noget mangler");
        alert.setContentText(besked);
        alert.showAndWait();
    }
}
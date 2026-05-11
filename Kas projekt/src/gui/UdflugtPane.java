package gui;

import controller.Controller;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import model.Konference;
import model.Ledsager;
import model.Udflugt;

import java.time.LocalDate;

public class UdflugtPane extends GridPane {

    private ListView<Udflugt> lvwUdflugter;
    private ListView<Ledsager> lvwLedsagere;

    private TextField txfNavn;
    private TextField txfPris;
    private DatePicker dpDato;

    private ComboBox<Konference> cbKonference;

    private Button btnOpretUdflugt;
    private Button btnVisLedsagere;
    private Button btnRyd;

    public UdflugtPane() {
        this.setPadding(new Insets(20));
        this.setHgap(20);
        this.setVgap(10);

        // ---------------------------------------------------
        // Venstre side - liste over udflugter
        // ---------------------------------------------------

        Label lblUdflugter = new Label("Udflugter");
        this.add(lblUdflugter, 0, 0);

        lvwUdflugter = new ListView<>();
        lvwUdflugter.setPrefWidth(250);
        lvwUdflugter.setPrefHeight(350);
        this.add(lvwUdflugter, 0, 1, 1, 6);

        btnVisLedsagere = new Button("Vis ledsagere");
        this.add(btnVisLedsagere, 0, 7);

        // ---------------------------------------------------
        // Midten - ledsagere til valgt udflugt
        // ---------------------------------------------------

        Label lblLedsagere = new Label("Ledsagere på valgt udflugt");
        this.add(lblLedsagere, 1, 0);

        lvwLedsagere = new ListView<>();
        lvwLedsagere.setPrefWidth(250);
        lvwLedsagere.setPrefHeight(350);
        this.add(lvwLedsagere, 1, 1, 1, 6);

        // ---------------------------------------------------
        // Højre side - opret udflugt
        // ---------------------------------------------------

        Label lblOpret = new Label("Opret ny udflugt");
        this.add(lblOpret, 2, 0);

        Label lblNavn = new Label("Navn:");
        this.add(lblNavn, 2, 1);

        txfNavn = new TextField();
        this.add(txfNavn, 3, 1);

        Label lblDato = new Label("Dato:");
        this.add(lblDato, 2, 2);

        dpDato = new DatePicker();
        this.add(dpDato, 3, 2);

        Label lblPris = new Label("Pris:");
        this.add(lblPris, 2, 3);

        txfPris = new TextField();
        this.add(txfPris, 3, 3);

        Label lblKonference = new Label("Konference:");
        this.add(lblKonference, 2, 4);

        cbKonference = new ComboBox<>();
        cbKonference.getItems().addAll(Controller.getKonferencer());
        this.add(cbKonference, 3, 4);

        btnOpretUdflugt = new Button("Opret udflugt");
        this.add(btnOpretUdflugt, 2, 6);

        btnRyd = new Button("Ryd felter");
        this.add(btnRyd, 3, 6);

        // ---------------------------------------------------
        // Actions
        // ---------------------------------------------------

        btnOpretUdflugt.setOnAction(event -> opretUdflugtAction());

        btnVisLedsagere.setOnAction(event -> visLedsagereAction());

        btnRyd.setOnAction(event -> rydFelter());

        lvwUdflugter
                .getSelectionModel()
                .selectedItemProperty()
                .addListener((obs, oldValue, newValue) -> visLedsagereAction());

        opdaterUdflugter();
    }

    private void opretUdflugtAction() {
        String navn = txfNavn.getText();
        LocalDate dato = dpDato.getValue();
        String prisTekst = txfPris.getText();

        Konference konference =
                cbKonference.getSelectionModel().getSelectedItem();

        if (navn.isEmpty()) {
            visFejl("Indtast navn på udflugten.");
            return;
        }

        if (dato == null) {
            visFejl("Vælg dato for udflugten.");
            return;
        }

        if (prisTekst.isEmpty()) {
            visFejl("Indtast pris for udflugten.");
            return;
        }

        if (konference == null) {
            visFejl("Vælg en konference.");
            return;
        }

        double pris;

        try {
            pris = Double.parseDouble(prisTekst);
        } catch (NumberFormatException e) {
            visFejl("Pris skal være et tal.");
            return;
        }

        Udflugt udflugt = Controller.opretUdflugt(navn, dato, pris, konference);


        opdaterUdflugter();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Udflugt oprettet");
        alert.setHeaderText("Udflugten blev oprettet");
        alert.setContentText(
                "Udflugt: " + navn +
                        "\nDato: " + dato +
                        "\nPris: " + pris + " kr."
        );
        alert.showAndWait();

        rydFelter();
    }

    private void visLedsagereAction() {
        lvwLedsagere.getItems().clear();

        Udflugt valgtUdflugt =
                lvwUdflugter
                        .getSelectionModel()
                        .getSelectedItem();

        if (valgtUdflugt == null) {
            return;
        }

        for (Ledsager ledsager : Controller.getLedsagere()) {
            if (ledsager.getUdflugter().contains(valgtUdflugt)) {
                lvwLedsagere.getItems().add(ledsager);
            }
        }
    }

    private void opdaterUdflugter() {
        lvwUdflugter.getItems().setAll(
                Controller.getUdflugter()
        );

        cbKonference.getItems().setAll(Controller.getKonferencer());
        cbKonference.getSelectionModel().clearSelection();
    }

    private void rydFelter() {
        txfNavn.clear();
        txfPris.clear();

        dpDato.setValue(null);

        cbKonference.getSelectionModel().clearSelection();

        lvwUdflugter.getSelectionModel().clearSelection();
        lvwLedsagere.getItems().clear();
    }

    private void visFejl(String besked) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Fejl");
        alert.setHeaderText("Noget mangler");
        alert.setContentText(besked);
        alert.showAndWait();
    }
    public void opdater() {
        lvwUdflugter.getItems().setAll(
                Controller.getUdflugter());
        lvwLedsagere.getItems().clear();
    }
}
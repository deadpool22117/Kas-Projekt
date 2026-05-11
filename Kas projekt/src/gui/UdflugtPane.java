package gui;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import model.Konference;
import model.Ledsager;
import model.Udflugt;
import storage.Storage;

import java.time.LocalDate;

public class UdflugtPane extends GridPane {

    private ListView<Konference> lvwKonferencer;

    private TextArea txaInfo;

    private TextField txfNavn;
    private TextField txfPris;
    private DatePicker dpDato;

    private ComboBox<Konference> cbKonference;

    private Button btnOpretUdflugt;
    private Button btnVisInfo;
    private Button btnRyd;

    public UdflugtPane() {
        this.setPadding(new Insets(20));
        this.setHgap(20);
        this.setVgap(10);

        // ---------------------------------------------------
        // Venstre side - konferencer
        // ---------------------------------------------------

        Label lblKonferencer = new Label("Konferencer");
        this.add(lblKonferencer, 0, 0);

        lvwKonferencer = new ListView<>();
        lvwKonferencer.setPrefWidth(250);
        lvwKonferencer.setPrefHeight(400);
        lvwKonferencer.getItems().setAll(Storage.getKonferencer());
        this.add(lvwKonferencer, 0, 1, 1, 6);

        btnVisInfo = new Button("Vis udflugter");
        this.add(btnVisInfo, 0, 7);

        // ---------------------------------------------------
        // Midten - info om udflugter
        // ---------------------------------------------------

        Label lblInfo = new Label("Udflugter for valgt konference");
        this.add(lblInfo, 1, 0);

        txaInfo = new TextArea();
        txaInfo.setEditable(false);
        txaInfo.setPrefWidth(500);
        txaInfo.setPrefHeight(400);
        this.add(txaInfo, 1, 1, 1, 6);

        // ---------------------------------------------------
        // Højre side - opret ny udflugt
        // ---------------------------------------------------

        Label lblOpret = new Label("Opret ny udflugt");
        this.add(lblOpret, 2, 0);

        this.add(new Label("Navn:"), 2, 1);
        txfNavn = new TextField();
        this.add(txfNavn, 3, 1);

        this.add(new Label("Dato:"), 2, 2);
        dpDato = new DatePicker();
        this.add(dpDato, 3, 2);

        this.add(new Label("Pris:"), 2, 3);
        txfPris = new TextField();
        this.add(txfPris, 3, 3);

        this.add(new Label("Konference:"), 2, 4);
        cbKonference = new ComboBox<>();
        cbKonference.getItems().setAll(Storage.getKonferencer());
        this.add(cbKonference, 3, 4);

        btnOpretUdflugt = new Button("Opret udflugt");
        this.add(btnOpretUdflugt, 2, 6);

        btnRyd = new Button("Ryd felter");
        this.add(btnRyd, 3, 6);

        // ---------------------------------------------------
        // Actions
        // ---------------------------------------------------

        btnOpretUdflugt.setOnAction(event -> opretUdflugtAction());

        btnVisInfo.setOnAction(event -> visUdflugterForKonference());

        btnRyd.setOnAction(event -> rydFelter());

        lvwKonferencer
                .getSelectionModel()
                .selectedItemProperty()
                .addListener((obs, oldValue, newValue) -> visUdflugterForKonference());
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

        Udflugt udflugt = new Udflugt(
                navn,
                dato,
                pris,
                konference
        );

        Storage.storeUdflugt(udflugt);
        konference.addUdflugt(udflugt);

        opdater();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Udflugt oprettet");
        alert.setHeaderText("Udflugten blev oprettet");
        alert.setContentText(
                "Udflugt: " + navn
                        + "\nKonference: " + konference.getNavn()
                        + "\nDato: " + dato
                        + "\nPris: " + pris + " kr."
        );
        alert.showAndWait();

        rydFelter();
    }

    private void visUdflugterForKonference() {
        Konference konference =
                lvwKonferencer
                        .getSelectionModel()
                        .getSelectedItem();

        if (konference == null) {
            txaInfo.clear();
            return;
        }

        StringBuilder sb = new StringBuilder();

        sb.append("Konference: ")
                .append(konference.getNavn())
                .append("\n");

        sb.append("-----------------------------\n\n");

        if (konference.getUdflugter().isEmpty()) {
            sb.append("Der er ingen udflugter til denne konference.");
        } else {
            for (Udflugt udflugt : konference.getUdflugter()) {

                sb.append("Udflugt:\n");
                sb.append(udflugt).append("\n\n");

                sb.append("Ledsagere til denne udflugt:\n");

                boolean fundetLedsager = false;

                for (Ledsager ledsager : Storage.getLedsagere()) {
                    if (ledsager.getUdflugter().contains(udflugt)) {
                        fundetLedsager = true;
                        sb.append(" - ").append(ledsager).append("\n");
                    }
                }

                if (!fundetLedsager) {
                    sb.append("Ingen ledsagere tilmeldt.\n");
                }

                sb.append("\n-----------------------------\n\n");
            }
        }

        txaInfo.setText(sb.toString());
    }

    public void opdater() {
        lvwKonferencer.getItems().setAll(
                Storage.getKonferencer()
        );

        cbKonference.getItems().setAll(
                Storage.getKonferencer()
        );

        txaInfo.clear();
    }

    private void rydFelter() {
        txfNavn.clear();
        txfPris.clear();

        dpDato.setValue(null);

        cbKonference.getSelectionModel().clearSelection();

        lvwKonferencer.getSelectionModel().clearSelection();

        txaInfo.clear();
    }

    private void visFejl(String besked) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Fejl");
        alert.setHeaderText("Noget mangler");
        alert.setContentText(besked);
        alert.showAndWait();
    }
}
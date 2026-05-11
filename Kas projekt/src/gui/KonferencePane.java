package gui;

import controller.Controller;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import model.Konference;
import model.Tilmelding;
import storage.Storage;

import java.time.LocalDate;


public class KonferencePane extends GridPane {

    private ListView<Konference> lvwKonferencer;

    private TableView<Tilmelding> tblTilmeldinger;

    private TableColumn<Tilmelding, String> colID;
    private TableColumn<Tilmelding, String> colDeltager;
    private TableColumn<Tilmelding, Double> colPris;

    private TextField txfKonferenceNavn;
    private TextField txfprisPrDag;

    private DatePicker dpStartDato;
    private DatePicker dpSlutDato;

    private Button btnVis;
    private Button btnOpretKonference;

    //---------------------------------------------------
    // opdater panes
    // --------------------------------------------------

    public void opdater(){
        lvwKonferencer.getItems().setAll(
                Storage.getKonferencer());
        tblTilmeldinger.getItems().clear();
    }


    public KonferencePane() {

        this.setPadding(new Insets(20));
        this.setHgap(20);
        this.setVgap(10);

        // ---------------------------------------------------
        // Konference liste
        // ---------------------------------------------------

        Label lblKonferencer =
                new Label("Konferencer");

        this.add(lblKonferencer, 0, 1);

        lvwKonferencer = new ListView<>();

        lvwKonferencer.getItems().addAll(
                Storage.getKonferencer()
        );

        lvwKonferencer.setPrefWidth(250);
        lvwKonferencer.setPrefHeight(400);

        this.add(lvwKonferencer, 0, 1);

        // ---------------------------------------------------
        // TableView
        // ---------------------------------------------------

        Label lblTilmeldinger =
                new Label("Tilmeldinger");

        this.add(lblTilmeldinger, 1, 0);

        tblTilmeldinger = new TableView<>();

        colID = new TableColumn<>("Tilmelding ID");
        colID.setCellValueFactory(
                new PropertyValueFactory<>("tilmeldingID")
        );

        colID.setPrefWidth(120);

        colDeltager = new TableColumn<>("Deltager");

        colDeltager.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(
                        cellData.getValue()
                                .getDeltager()
                                .getNavn()
                )
        );

        colDeltager.setPrefWidth(200);

        colPris = new TableColumn<>("Pris");

        colPris.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleObjectProperty<>(
                        cellData.getValue().samletPris()
                )
        );

        colPris.setPrefWidth(100);

        tblTilmeldinger.getColumns().addAll(
                colID,
                colDeltager,
                colPris
        );

        tblTilmeldinger.setPrefWidth(450);
        tblTilmeldinger.setPrefHeight(400);

        this.add(tblTilmeldinger, 1, 1, 4, 1);

        // ---------------------------------------------------
        // Create konference
        // ---------------------------------------------------

        Label lblKonferenceNavn = new Label("Navn");
        this.add(lblKonferenceNavn, 1, 2);

        txfKonferenceNavn = new TextField();
        this.add(txfKonferenceNavn, 1, 3);


        Label lblPrisPrDag = new Label("Pris pr dag");
        this.add(lblPrisPrDag, 1, 4);

        txfprisPrDag = new TextField();
        this.add(txfprisPrDag, 1, 5);


        this.add(new Label("Startdato:"), 2, 2);
        dpStartDato = new DatePicker();
        this.add(dpStartDato, 2, 3);

        this.add(new Label("Slutdato:"), 2, 4);
        dpSlutDato = new DatePicker();
        this.add(dpSlutDato, 2, 5);

        // ---------------------------------------------------
        // Buttons
        // ---------------------------------------------------

        btnVis = new Button("Vis tilmeldinger");

        this.add(btnVis, 0, 2);

        btnOpretKonference =
                new Button("Opret konference");

        this.add(btnOpretKonference, 3, 3);

        // ---------------------------------------------------
        // Actions
        // ---------------------------------------------------

        btnVis.setOnAction(event -> visTilmeldinger());
        btnOpretKonference.setOnAction(actionEvent -> opretKonference());

        lvwKonferencer
                .getSelectionModel()
                .selectedItemProperty()
                .addListener((obs, oldValue, newValue) ->
                        visTilmeldinger()
                );
    }



    // ---------------------------------------------------
    // Vis tilmeldinger
    // ---------------------------------------------------

    private void visTilmeldinger() {

        Konference konference =
                lvwKonferencer
                        .getSelectionModel()
                        .getSelectedItem();

        if (konference != null) {

            tblTilmeldinger.getItems().setAll(
                    konference.getTilmeldinger()
            );

        }
    }

    private void opretKonference() {
        String konferenceNavn = txfKonferenceNavn.getText();
        String prisPrDagTekst = txfprisPrDag.getText();

        LocalDate startDato = dpStartDato.getValue();
        LocalDate slutDato = dpSlutDato.getValue();

        if (konferenceNavn.isEmpty()) {
            visFejl("Indtast venligst et navn");
            return;
        }

        double prisPrDag;
        try {
            prisPrDag = Double.parseDouble(prisPrDagTekst);
        } catch (NumberFormatException ex) {
            visFejl("Indtast venligst et tal");
            return;
        }


        if (startDato == null || slutDato == null) {
            visFejl("Vælg både startdato og slutdato.");
            return;
        }

        if (slutDato.isBefore(startDato)) {
            visFejl("Slutdato kan ikke være før startdato.");
            return;
        }

        Controller.opretKonference(konferenceNavn, startDato, slutDato, prisPrDag);

        opdaterKonferencer();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Konference oprettet");
        alert.setHeaderText("Konferencen blev oprettet");
        alert.setContentText(
                "Konference: " + konferenceNavn
                        + "\nPris Pr dag: " + prisPrDag + " kr."
                        + "\nStartdato: " + startDato
                        + "\nSlutdato: " + slutDato

        );
        alert.showAndWait();

        rydFelter();
    }

    private void visFejl(String besked) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Fejl");
        alert.setHeaderText("Noget mangler");
        alert.setContentText(besked);
        alert.showAndWait();
    }

    private void rydFelter() {
        txfKonferenceNavn.clear();
        txfprisPrDag.clear();

        dpStartDato.setValue(null);
        dpSlutDato.setValue(null);
    }

    private void opdaterKonferencer() {
        lvwKonferencer.getItems().setAll(
                Controller.getKonferencer()
        );
    }
}


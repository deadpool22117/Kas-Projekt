package gui;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import model.Konference;
import model.Tilmelding;
import storage.Storage;


public class KonferencePane extends GridPane {

    private ListView<Konference> lvwKonferencer;

    private TableView<Tilmelding> tblTilmeldinger;

    private TableColumn<Tilmelding, String> colID;
    private TableColumn<Tilmelding, String> colDeltager;
    private TableColumn<Tilmelding, Double> colPris;

    private Button btnVis;
    private Button btnOpretKonference;

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

        this.add(tblTilmeldinger, 1, 1);

        // ---------------------------------------------------
        // Buttons
        // ---------------------------------------------------

        btnVis = new Button("Vis tilmeldinger");

        this.add(btnVis, 0, 2);

        btnOpretKonference =
                new Button("Opret konference");

        this.add(btnOpretKonference, 1, 2);

        // ---------------------------------------------------
        // Actions
        // ---------------------------------------------------

        btnVis.setOnAction(event -> visTilmeldinger());

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
                    Konference.getTilmeldinger()
            );

        }
    }
}


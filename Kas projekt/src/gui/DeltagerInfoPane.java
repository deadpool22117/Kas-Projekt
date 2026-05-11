package gui;

import controller.Controller;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import model.Deltager;
import model.Tilmelding;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class DeltagerInfoPane extends GridPane {

    private ListView<Deltager> lvwDeltagere;
    private TextArea txaInfo;
    private Button btnVisInfo;
    private Button btnOpdater;
    private Button btnRyd;
    private Button btnSoeg;
    private TextField txfSoegning;

    public DeltagerInfoPane() {
        this.setPadding(new Insets(20));
        this.setHgap(20);
        this.setVgap(10);

        // ---------------------------------------------------
        // Venstre side - deltagere
        // ---------------------------------------------------

        Label lblDeltagere = new Label("Deltagere");
        this.add(lblDeltagere, 0, 0);

        txfSoegning = new TextField();
        txfSoegning.setPromptText("Indtast navn");
        this.add(txfSoegning,0,1 );

        lvwDeltagere = new ListView<>();
        lvwDeltagere.setPrefWidth(250);
        lvwDeltagere.setPrefHeight(400);
        lvwDeltagere.getItems().addAll(Controller.getDeltager());
        this.add(lvwDeltagere, 0, 2, 2, 6);

        // ---------------------------------------------------
        // Højre side - info
        // ---------------------------------------------------

        Label lblInfo = new Label("Information om deltager");
        this.add(lblInfo, 2, 1);

        txaInfo = new TextArea();
        txaInfo.setPrefWidth(500);
        txaInfo.setPrefHeight(400);
        txaInfo.setEditable(false);
        this.add(txaInfo, 2, 2, 2, 6);

        // ---------------------------------------------------
        // Knapper
        // ---------------------------------------------------

        btnSoeg = new Button("Søg");
        this.add(btnSoeg, 1, 1);

        btnVisInfo = new Button("Vis info");
        this.add(btnVisInfo, 0, 8);

        btnOpdater = new Button("Opdater liste");
        this.add(btnOpdater, 1, 8);

        btnRyd = new Button("Ryd");
        this.add(btnRyd, 2, 8);

        // ---------------------------------------------------
        // Actions
        // ---------------------------------------------------

        btnSoeg.setOnAction(event -> opdaterDeltagere());

        btnVisInfo.setOnAction(event -> visInfoAction());

        btnOpdater.setOnAction(event -> opdaterDeltagere());

        btnRyd.setOnAction(event -> ryd());

        lvwDeltagere
                .getSelectionModel()
                .selectedItemProperty()
                .addListener((obs, oldValue, newValue) -> visInfoAction());
    }

    private void visInfoAction() {
        Deltager deltager =
                lvwDeltagere
                        .getSelectionModel()
                        .getSelectedItem();

        if (deltager == null) {
            txaInfo.clear();
            return;
        }

        StringBuilder sb = new StringBuilder();

        sb.append("Deltagerinformation\n");
        sb.append("-----------------------------\n");
        sb.append("Navn: ").append(deltager.getNavn()).append("\n");
        sb.append("Adresse: ").append(deltager.getAdresse()).append("\n");
        sb.append("Mobil: ").append(deltager.getMobil()).append("\n");
        sb.append("By/Land: ").append(deltager.getByLand()).append("\n\n");

        sb.append("Tilmeldinger\n");
        sb.append("-----------------------------\n");

        if (deltager.getTilmeldinger().isEmpty()) {
            sb.append("Deltageren har ingen tilmeldinger.\n");
        } else {
            for (Tilmelding tilmelding : deltager.getTilmeldinger()) {

                sb.append("Tilmelding ID: ")
                        .append(tilmelding.getTilmeldingID())
                        .append("\n");

                sb.append("Konference: ")
                        .append(tilmelding.getKonference().getNavn())
                        .append("\n");

                if (tilmelding.getHotel() != null) {
                    sb.append("Hotel: ")
                            .append(tilmelding.getHotel())
                            .append("\n");
                } else {
                    sb.append("Hotel: Ikke valgt\n");
                }

                sb.append("Samlet pris: ")
                        .append(tilmelding.samletPris())
                        .append(" kr.\n");

                sb.append("-----------------------------\n");
            }
            sb.append("Samlet pris for alle tilmeldinger: ")
                    .append(deltager.sumAfPrisforTilmeldinger())
                    .append(" kr.\n");
            sb.append("-----------------------------\n");
        }

        txaInfo.setText(sb.toString());
    }

    private void opdaterDeltagere() {
        lvwDeltagere.getItems().clear();

        ArrayList<Deltager> deltagere = Controller.getDeltager();

        if (txfSoegning.getText().isEmpty()) {
            lvwDeltagere.getItems().setAll(deltagere);

        } else {
            Deltager deltager = linearSearchDeltager(deltagere, txfSoegning.getText().toUpperCase());

            if (deltager != null) {
                lvwDeltagere.getItems().add(deltager);

            } else {

                for (Deltager d : deltagere) {
                    if (d.getNavn().toUpperCase().contains(txfSoegning.getText().toUpperCase())) {
                        lvwDeltagere.getItems().add(d);
                    }
                }
            }
        }
    }

    private Deltager linearSearchDeltager(ArrayList<Deltager> deltagere, String name) {
        Deltager deltager = null;
        int i = 0;
        while (deltager == null && i < deltagere.size()) {
            Deltager d = deltagere.get(i);
            if (d.getNavn().toUpperCase().equals(name)) {
                deltager = d;
            } else {
                i++;
            }
        }
        return deltager;
    }

    private void ryd() {
        lvwDeltagere.getSelectionModel().clearSelection();
        txaInfo.clear();
    }
    public void opdater() {
        lvwDeltagere.getItems().setAll(
                Controller.getDeltager()
        );

        lvwDeltagere.getItems().setAll(
                Controller.getDeltager()
        );

        lvwDeltagere.getItems().clear();
    }
}
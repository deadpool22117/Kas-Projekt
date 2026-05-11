package gui;

import controller.Controller;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import model.Deltager;
import model.Tilmelding;
import storage.Storage;

public class DeltagerInfoPane extends GridPane {

    private ListView<Deltager> lvwDeltagere;
    private TextArea txaInfo;
    private Button btnVisInfo;
    private Button btnOpdater;
    private Button btnRyd;

    public DeltagerInfoPane() {
        this.setPadding(new Insets(20));
        this.setHgap(20);
        this.setVgap(10);

        // ---------------------------------------------------
        // Venstre side - deltagere
        // ---------------------------------------------------

        Label lblDeltagere = new Label("Deltagere");
        this.add(lblDeltagere, 0, 0);

        lvwDeltagere = new ListView<>();
        lvwDeltagere.setPrefWidth(250);
        lvwDeltagere.setPrefHeight(400);
        lvwDeltagere.getItems().addAll(Storage.getDeltager());
        this.add(lvwDeltagere, 0, 1, 1, 6);

        // ---------------------------------------------------
        // Højre side - info
        // ---------------------------------------------------

        Label lblInfo = new Label("Information om deltager");
        this.add(lblInfo, 1, 0);

        txaInfo = new TextArea();
        txaInfo.setPrefWidth(500);
        txaInfo.setPrefHeight(400);
        txaInfo.setEditable(false);
        this.add(txaInfo, 1, 1, 2, 6);

        // ---------------------------------------------------
        // Knapper
        // ---------------------------------------------------

        btnVisInfo = new Button("Vis info");
        this.add(btnVisInfo, 0, 7);

        btnOpdater = new Button("Opdater liste");
        this.add(btnOpdater, 1, 7);

        btnRyd = new Button("Ryd");
        this.add(btnRyd, 2, 7);

        // ---------------------------------------------------
        // Actions
        // ---------------------------------------------------

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
        }

        txaInfo.setText(sb.toString());
    }

    private void opdaterDeltagere() {
        lvwDeltagere.getItems().setAll(
                Storage.getDeltager()
        );
    }
    public void opdater(){
        lvwDeltagere.getItems().setAll(
                Storage.getDeltager());
        lvwDeltagere.getItems().clear();

    }


    private void ryd() {
        lvwDeltagere.getSelectionModel().clearSelection();
        txaInfo.clear();
    }
}
package gui;

import controller.Controller;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import model.Hotel;
import model.Konference;
import model.Tillaeg;
import model.Tilmelding;
import model.Deltager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class HotelPane extends GridPane {

    private ListView<Hotel> lvwHoteller;

    private TextArea txaInfo;

    private TextField txfHotelNavn;
    private TextField txfPrisEnkelt;
    private TextField txfPrisDobbelt;

    private ComboBox<Konference> cbKonference;

    private TextField txfTillaegNavn;
    private TextField txfTillaegPris;
    private ListView<String> lvwMidlertidigeTillaeg;

    private Button btnTilfoejTillaeg;
    private Button btnOpretHotel;
    private Button btnVisInfo;
    private Button btnRyd;

    private final ArrayList<TillaegData> midlertidigeTillaeg = new ArrayList<>();

    public HotelPane() {
        this.setPadding(new Insets(20));
        this.setHgap(20);
        this.setVgap(10);

        // ---------------------------------------------------
        // Venstre side - hoteller
        // ---------------------------------------------------

        Label lblHoteller = new Label("Hoteller");
        this.add(lblHoteller, 0, 0);

        lvwHoteller = new ListView<>();
        lvwHoteller.setPrefWidth(250);
        lvwHoteller.setPrefHeight(400);
        lvwHoteller.getItems().setAll(Controller.getHoteller());
        this.add(lvwHoteller, 0, 1, 1, 6);

        btnVisInfo = new Button("Vis hotelinfo");
        this.add(btnVisInfo, 0, 7);

        // ---------------------------------------------------
        // Midten - info om valgt hotel
        // ---------------------------------------------------

        Label lblInfo = new Label("Info om valgt hotel");
        this.add(lblInfo, 1, 0);

        txaInfo = new TextArea();
        txaInfo.setEditable(false);
        txaInfo.setPrefWidth(500);
        txaInfo.setPrefHeight(400);
        this.add(txaInfo, 1, 1, 1, 6);

        // ---------------------------------------------------
        // Højre side - opret hotel
        // ---------------------------------------------------

        Label lblOpret = new Label("Opret nyt hotel");
        this.add(lblOpret, 2, 0);

        this.add(new Label("Hotelnavn:"), 2, 1);
        txfHotelNavn = new TextField();
        this.add(txfHotelNavn, 3, 1);

        this.add(new Label("Pris enkeltværelse:"), 2, 2);
        txfPrisEnkelt = new TextField();
        this.add(txfPrisEnkelt, 3, 2);

        this.add(new Label("Pris dobbeltværelse:"), 2, 3);
        txfPrisDobbelt = new TextField();
        this.add(txfPrisDobbelt, 3, 3);

        this.add(new Label("Konference:"), 2, 4);
        cbKonference = new ComboBox<>();
        cbKonference.getItems().setAll(Controller.getKonferencer());
        this.add(cbKonference, 3, 4);


        // ---------------------------------------------------
        // Tillæg
        // ---------------------------------------------------

        Label lblTillaeg = new Label("Tillæg til hotel");
        this.add(lblTillaeg, 2, 6);

        this.add(new Label("Tillægsnavn:"), 2, 7);
        txfTillaegNavn = new TextField();
        this.add(txfTillaegNavn, 3, 7);

        this.add(new Label("Tillægspris:"), 2, 8);
        txfTillaegPris = new TextField();
        this.add(txfTillaegPris, 3, 8);

        btnTilfoejTillaeg = new Button("Tilføj tillæg");
        this.add(btnTilfoejTillaeg, 2, 9);

        lvwMidlertidigeTillaeg = new ListView<>();
        lvwMidlertidigeTillaeg.setPrefWidth(250);
        lvwMidlertidigeTillaeg.setPrefHeight(100);
        this.add(lvwMidlertidigeTillaeg, 3, 9, 1, 2);

        btnOpretHotel = new Button("Opret hotel");
        this.add(btnOpretHotel, 2, 12);

        btnRyd = new Button("Ryd felter");
        this.add(btnRyd, 3, 12);

        Button btnGemHotelInfo = new Button("Gem hotelinfo");
        this.add(btnGemHotelInfo, 1, 8);



        // ---------------------------------------------------
        // Actions
        // ---------------------------------------------------

        btnGemHotelInfo.setOnAction(event -> gemHotelInfoAction());

        btnVisInfo.setOnAction(event -> visHotelInfo());

        btnTilfoejTillaeg.setOnAction(event -> tilfoejTillaegAction());

        btnOpretHotel.setOnAction(event -> opretHotelAction());

        btnRyd.setOnAction(event -> rydFelter());

        lvwHoteller
                .getSelectionModel()
                .selectedItemProperty()
                .addListener((obs, oldValue, newValue) -> visHotelInfo());
    }

    private void tilfoejTillaegAction() {
        String navn = txfTillaegNavn.getText();
        String prisTekst = txfTillaegPris.getText();

        if (navn.isEmpty()) {
            visFejl("Indtast navn på tillæg.");
            return;
        }

        if (prisTekst.isEmpty()) {
            visFejl("Indtast pris på tillæg.");
            return;
        }

        double pris;

        try {
            pris = Double.parseDouble(prisTekst);
        } catch (NumberFormatException e) {
            visFejl("Tillægspris skal være et tal.");
            return;
        }

        TillaegData data = new TillaegData(navn, pris);
        midlertidigeTillaeg.add(data);

        lvwMidlertidigeTillaeg.getItems().add(
                navn + " | " + pris + " kr."
        );

        txfTillaegNavn.clear();
        txfTillaegPris.clear();
    }

    private void opretHotelAction() {
        String hotelNavn = txfHotelNavn.getText();
        String prisEnkeltTekst = txfPrisEnkelt.getText();
        String prisDobbeltTekst = txfPrisDobbelt.getText();

        Konference konference =
                cbKonference.getSelectionModel().getSelectedItem();

        if (hotelNavn.isEmpty()) {
            visFejl("Indtast hotelnavn.");
            return;
        }

        if (prisEnkeltTekst.isEmpty() || prisDobbeltTekst.isEmpty()) {
            visFejl("Indtast både pris for enkeltværelse og dobbeltværelse.");
            return;
        }

        if (konference == null) {
            visFejl("Vælg hvilken konference hotellet skal knyttes til.");
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

        Hotel hotel = Controller.opretHotel(
                hotelNavn,
                prisEnkelt,
                prisDobbelt
        );

        Controller.addHoteltoKonference(
                konference,
                hotel
        );

        for (TillaegData data : midlertidigeTillaeg) {
            Tillaeg tillaeg = Controller.opretTillaeg(
                    data.navn,
                    data.pris
            );

            Controller.addTillaegToHotel(
                    tillaeg,
                    hotel
            );
        }

        opdater();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Hotel oprettet");
        alert.setHeaderText("Hotellet blev oprettet");
        alert.setContentText(
                "Hotel: " + hotelNavn
                        + "\nKonference: " + konference.getNavn()
                        + "\nPris enkeltværelse: " + prisEnkelt + " kr."
                        + "\nPris dobbeltværelse: " + prisDobbelt + " kr."
                        + "\nAntal tillæg: " + midlertidigeTillaeg.size()
        );
        alert.showAndWait();

        rydFelter();
    }

    private void visHotelInfo() {
        Hotel hotel =
                lvwHoteller
                        .getSelectionModel()
                        .getSelectedItem();

        if (hotel == null) {
            txaInfo.clear();
            return;
        }

        StringBuilder sb = new StringBuilder();

        sb.append("Hotelinformation\n");
        sb.append("-----------------------------\n");

        sb.append("Hotel: ")
                .append(hotel.getHotelNavn())
                .append("\n");

        sb.append("Pris enkeltværelse: ")
                .append(hotel.getPrisEnkelt())
                .append(" kr.\n");

        sb.append("Pris dobbeltværelse: ")
                .append(hotel.getPrisDobbelt())
                .append(" kr.\n\n");

        // ---------------------------------------------------
        // Hotellets mulige tillæg
        // ---------------------------------------------------

        sb.append("Hotellets mulige tillæg\n");
        sb.append("-----------------------------\n");

        if (hotel.getTillaeg().isEmpty()) {
            sb.append("Ingen tillæg til dette hotel.\n");
        } else {
            for (Tillaeg tillaeg : hotel.getTillaeg()) {
                sb.append("- ")
                        .append(tillaeg.getNavn())
                        .append(" | ")
                        .append(tillaeg.getPris())
                        .append(" kr.\n");
            }
        }

        sb.append("\n");

        // ---------------------------------------------------
        // Tilmeldinger på hotellet
        // ---------------------------------------------------

        sb.append("Tilmeldinger på hotellet\n");
        sb.append("-----------------------------\n");

        if (hotel.getTilmeldinger().isEmpty()) {
            sb.append("Ingen deltagere bor på dette hotel.\n");
        } else {
            for (Tilmelding tilmelding : hotel.getTilmeldinger()) {

                Deltager deltager = tilmelding.getDeltager();
                Konference konference = tilmelding.getKonference();

                sb.append("Tilmelding: ")
                        .append(tilmelding.getTilmeldingID())
                        .append("\n");

                sb.append("Deltager: ")
                        .append(deltager.getNavn())
                        .append("\n");

                sb.append("Konference: ")
                        .append(konference.getNavn())
                        .append("\n");

                sb.append("Periode: ")
                        .append(tilmelding.getAnkomstDato())
                        .append(" -> ")
                        .append(tilmelding.getAfrejseDato())
                        .append("\n");

                sb.append("Valgte tillæg på tilmeldingen:\n");

                if (tilmelding.getValgteTillaeg().isEmpty()) {
                    sb.append("Ingen valgte tillæg.\n");
                } else {
                    for (Tillaeg tillaeg : tilmelding.getValgteTillaeg()) {
                        sb.append("- ")
                                .append(tillaeg.getNavn())
                                .append(" | ")
                                .append(tillaeg.getPris())
                                .append(" kr.\n");
                    }
                }

                sb.append("Samlet pris: ")
                        .append(tilmelding.samletPris())
                        .append(" kr.\n");

                sb.append("-----------------------------\n");
            }
        }

        txaInfo.setText(sb.toString());
    }


    public void opdater() {
        lvwHoteller.getItems().setAll(
                Controller.getHoteller()
        );

        cbKonference.getItems().setAll(
                Controller.getKonferencer()
        );

        txaInfo.clear();
    }

    private void rydFelter() {
        txfHotelNavn.clear();
        txfPrisEnkelt.clear();
        txfPrisDobbelt.clear();

        txfTillaegNavn.clear();
        txfTillaegPris.clear();

        cbKonference.getSelectionModel().clearSelection();

        lvwHoteller.getSelectionModel().clearSelection();

        lvwMidlertidigeTillaeg.getItems().clear();
        midlertidigeTillaeg.clear();

        txaInfo.clear();
    }

    private void visFejl(String besked) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Fejl");
        alert.setHeaderText("Noget mangler");
        alert.setContentText(besked);
        alert.showAndWait();
    }

    private static class TillaegData {
        private final String navn;
        private final double pris;

        private TillaegData(String navn, double pris) {
            this.navn = navn;
            this.pris = pris;
        }
    }
    private void gemHotelInfoAction() {
        Hotel hotel = lvwHoteller.getSelectionModel().getSelectedItem();

        if (hotel == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Intet hotel valgt");
            alert.setHeaderText(null);
            alert.setContentText("Vælg et hotel først.");
            alert.showAndWait();
            return;
        }

        // Opdaterer text area med info om det valgte hotel
        visHotelInfo();

        File out = new File("hotelinfo.txt");

        try (PrintWriter writer = new PrintWriter(out)) {
            writer.print(txaInfo.getText());

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Gemt");
            alert.setHeaderText(null);
            alert.setContentText("Hotelinformationen er gemt i hotelinfo.txt");
            alert.showAndWait();

        } catch (FileNotFoundException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Fejl");
            alert.setHeaderText(null);
            alert.setContentText("Filen kunne ikke gemmes.");
            alert.showAndWait();
        }
    }
}
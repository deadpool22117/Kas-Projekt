package gui;

import javafx.application.Application;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("Konference System");

        BorderPane root = new BorderPane();

        TabPane tabPane = new TabPane();
        tabPane.setSide(Side.TOP);

        // ---------------------------------------------------
        // Tab 1 - Opret tilmelding
        // ---------------------------------------------------

        Tab tabOpretTilmelding = new Tab("Opret tilmelding");
        tabOpretTilmelding.setClosable(false);
        tabOpretTilmelding.setContent(new OpretTilmeldingPane());

        // ---------------------------------------------------
        // Tab 2 - Oversigt konferencer
        // ---------------------------------------------------

        Tab tabKonferencer = new Tab("Oversigt konferencer");
        tabKonferencer.setClosable(false);
        tabKonferencer.setContent(new KonferencePane());

        // ---------------------------------------------------
        // Tab 3 - Udflugter
        // ---------------------------------------------------

        Tab tabUdflugter = new Tab("Udflugter");
        tabUdflugter.setClosable(false);
        tabUdflugter.setContent(new UdflugtPane());

        // ---------------------------------------------------
        // Tab 4 - Hoteller
        // ---------------------------------------------------

        Tab tabHoteller = new Tab("Hoteller");
        tabHoteller.setClosable(false);
        tabHoteller.setContent(new HotelPane());


        // ---------------------------------------------------
        // Tab 5 - Info om deltager
        // ---------------------------------------------------

        Tab tabDeltagerInfo = new Tab("Info om deltager");
        tabDeltagerInfo.setClosable(false);
        tabDeltagerInfo.setContent(new DeltagerInfoPane());


        tabPane.getTabs().addAll(
                tabOpretTilmelding,
                tabKonferencer,
                tabUdflugter,
                tabHoteller,
                tabDeltagerInfo
        );

        root.setCenter(tabPane);

        Scene scene = new Scene(root, 1000, 650);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
package main.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import main.controller.GeneralController;

import java.io.IOException;
import java.util.Objects;

public class Main extends Application {
    private static TabPane tabPanelMain;
    private static int numTab = 0;
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("main.css")).toExternalForm());
        scene.addEventHandler(KeyEvent.KEY_PRESSED, (event) -> {
            if (event.isControlDown()) {
                if (event.getCode() == KeyCode.N) {
                    GeneralController.create();
                }
                if (event.getCode() == KeyCode.O) {
                    GeneralController.open();
                }
                if (event.getCode() == KeyCode.S) {
                    GeneralController.save();
                }
                if (event.getCode() == KeyCode.W) {
                    GeneralController.close();
                }
                if (event.getCode() == KeyCode.F) {
                    GeneralController.search();
                }
                if (event.getCode() == KeyCode.J) {
                    GeneralController.change();
                }
            }
            if (event.getCode() == KeyCode.F1) {
                try {
                    GeneralController.help();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        stage.setScene(scene);

        tabPanelMain = (TabPane) scene.lookup("#tabPanelMain");
        GeneralController.create();

        stage.setTitle("Блокнот");
        stage.getIcons().add(new Image("/main/view/img/frame.png"));
        stage.setWidth(1024);
        stage.setHeight(768);
        stage.centerOnScreen();
        stage.show();
    }
    public static TabPane getTabPanelMain() {
        return tabPanelMain;
    }
    public static int getNumTab() {
        return numTab;
    }
    public static void setNumTab(int changer) {
        numTab = numTab + changer;
    }
}

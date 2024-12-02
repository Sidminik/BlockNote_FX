package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.*;

public class FxmlController {
    @FXML
    private CheckMenuItem viewPanelButton;
    @FXML
    private ToolBar toolBar;

    @FXML
    private void create(ActionEvent event) {
        GeneralController.create();
    }
    @FXML
    private void open(ActionEvent event) {
        GeneralController.open();
    }
    @FXML
    private void save(ActionEvent event) {
        GeneralController.save();
    }
    @FXML
    private void close(ActionEvent event) {
        GeneralController.close();
    }
    @FXML
    private void exit(ActionEvent event) {
        GeneralController.exit();
    }
    @FXML
    private void search(ActionEvent event) {
        GeneralController.search();
    }
    @FXML
    private void change(ActionEvent event) {
        GeneralController.change();
    }
    @FXML
    private void viewPanelButton(ActionEvent event) {
        GeneralController.viewPanelButton(viewPanelButton, toolBar);
    }
    @FXML
    private void help(ActionEvent event) throws IOException {
        GeneralController.help();
    }
    @FXML
    private void devInfo(ActionEvent event) {
        GeneralController.devInfo();
    }
}
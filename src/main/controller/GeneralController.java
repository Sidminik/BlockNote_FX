package main.controller;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Pair;

import main.model.TextTabModel;
import main.view.Main;

import java.io.*;
import java.util.Optional;

public class GeneralController {
    private static final SingleSelectionModel<Tab> selectionModel = Main.getTabPanelMain().getSelectionModel();
    private static TextTabModel currentTab;
    public static void create() {
        Main.setNumTab(1);
        TextTabModel newTab = new TextTabModel("Новый_" + Main.getNumTab());
        Main.getTabPanelMain().getTabs().add(newTab);
        selectionModel.select(Main.getTabPanelMain().getTabs().size() - 1);
        Main.getTabPanelMain().requestFocus();
    }
    public static void open() {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialDirectory(new File("C:\\"));
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Текстовые файлы", "*.txt"),
                    new FileChooser.ExtensionFilter("Все файлы", "*.*"));
            File openFile = fileChooser.showOpenDialog(null);
            FileReader reader = new FileReader(openFile);
            BufferedReader tempBuf = new BufferedReader(reader);
            TextTabModel newTab = new TextTabModel(openFile.getName());
            Main.getTabPanelMain().getTabs().add(newTab);
            newTab.setTextArea(tempBuf.readLine());
            selectionModel.select(Main.getTabPanelMain().getTabs().size() - 1);
        } catch (IOException | NullPointerException ignored) {
        }
    }
    public static void save() {
        if (Main.getTabPanelMain().getTabs().size() > 0) {
            try {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setInitialDirectory(new File("C:\\"));
                fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("Текстовые файлы", "*.txt"),
                        new FileChooser.ExtensionFilter("Все файлы", "*.*"));
                fileChooser.setInitialFileName("new" + ".txt");
                File savedFile = fileChooser.showSaveDialog(null);

                currentTab = (TextTabModel) selectionModel.getSelectedItem();
                FileWriter writer = new FileWriter(savedFile);
                writer.write(currentTab.getTextArea());
                currentTab.setText(savedFile.getName());
                writer.close();
            } catch (IOException | IndexOutOfBoundsException | NullPointerException ignored) {
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Внимание!");
            alert.setHeaderText("Ошибка запрашиваемого действия");
            alert.setContentText("Для выполнения операции сохранения текста в виде файла\n" +
                    "откройте имеющийся файл или создайте новую вкладку");
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(GeneralController.class.getResource("frame.png").toString()));
            alert.showAndWait();
        }
    }
    public static void close() {
        if (Main.getTabPanelMain().getTabs().size() > 0) {
            Main.getTabPanelMain().getTabs().remove(Main.getTabPanelMain().getSelectionModel().getSelectedItem());
            selectionModel.select(Main.getTabPanelMain().getTabs().size() - 1);
            if (Main.getTabPanelMain().getTabs().size() > 0) {
                Main.getTabPanelMain().getSelectionModel().getSelectedItem().getContent().requestFocus();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Внимание!");
            alert.setHeaderText("Ошибка запрашиваемого действия");
            alert.setContentText("Все вкладки закрыты.\n" +
                    "Для выполнения операции закрытия вкладки\n" +
                    "откройте имеющийся файл или создайте новую вкладку");
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(GeneralController.class.getResource("frame.png").toString()));
            alert.showAndWait();
        }
    }
    public static void exit() {
        System.exit(0);
    }
    public static void search() {
        if (Main.getTabPanelMain().getTabs().size() > 0) {
            Dialog<String> dialog = new Dialog<>();
            dialog.setTitle("Поиск");

            Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(GeneralController.class.getResource("frame.png").toString()));

            dialog.setHeaderText("Введите символ, слово или выражение для поиска");

            ButtonType changeButton = new ButtonType("Найти", ButtonBar.ButtonData.OK_DONE);
            ButtonType chancelButton = new ButtonType("Отмена", ButtonBar.ButtonData.CANCEL_CLOSE);
            dialog.getDialogPane().getButtonTypes().addAll(changeButton, chancelButton);

            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(10, 10, 10, 10));

            TextField searchString = new TextField();
            searchString.setPrefWidth(300);

            grid.add(new Label("Найти:"), 0, 0);
            grid.add(searchString, 1, 0);

            dialog.getDialogPane().setContent(grid);

            Platform.runLater(() -> searchString.requestFocus());

            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == changeButton) {
                    return searchString.getText();
                }
                return null;
            });

            Optional<String> result = dialog.showAndWait();
            result.ifPresent(input -> {
                currentTab = (TextTabModel) selectionModel.getSelectedItem();
                currentTab.setTemplateSearch(input);
                currentTab.setCount();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Результаты поиска");
                alert.setHeaderText(null);
                alert.setContentText("Найдено совпадений:  " + currentTab.getCount());
                Stage stage_2 = (Stage) alert.getDialogPane().getScene().getWindow();
                stage_2.getIcons().add(new Image(GeneralController.class.getResource("frame.png").toString()));
                alert.showAndWait();
                currentTab.setNullCount();
            });
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Внимание!");
            alert.setHeaderText("Ошибка запрашиваемого действия");
            alert.setContentText("Для выполнения операции поиска текста\n" +
                    "откройте имеющийся файл или создайте новую вкладку");
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(GeneralController.class.getResource("frame.png").toString()));
            alert.showAndWait();
        }
    }
    public static void change() {
        if (Main.getTabPanelMain().getTabs().size() > 0) {
            Dialog<Pair<String, String>> dialog = new Dialog<>();
            dialog.setTitle("Замена");

            Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(GeneralController.class.getResource("frame.png").toString()));

            dialog.setHeaderText("Введите символ, слово или выражение для замены");

            ButtonType changeButton = new ButtonType("Заменить", ButtonBar.ButtonData.OK_DONE);
            ButtonType chancelButton = new ButtonType("Отмена", ButtonBar.ButtonData.CANCEL_CLOSE);
            dialog.getDialogPane().getButtonTypes().addAll(changeButton, chancelButton);

            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(10, 10, 10, 10));

            TextField searchString = new TextField();
            searchString.setPrefWidth(300);
            TextField changeString = new TextField();
            changeString.setPrefWidth(300);

            grid.add(new Label("Найти:"), 0, 0);
            grid.add(searchString, 1, 0);
            grid.add(new Label("Заменить на:"), 0, 1);
            grid.add(changeString, 1, 1);

            dialog.getDialogPane().setContent(grid);

            Platform.runLater(() -> searchString.requestFocus());

            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == changeButton) {
                    return new Pair<>(searchString.getText(), changeString.getText());
                }
                return null;
            });

            Optional<Pair<String, String>> result = dialog.showAndWait();
            result.ifPresent(input -> {
                currentTab = (TextTabModel) selectionModel.getSelectedItem();
                currentTab.setTemplateSearch(input.getKey());
                currentTab.setTemplateChange(input.getValue());
                currentTab.setCount();
                currentTab.setNewMainText();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Результаты замены");
                alert.setHeaderText(null);
                alert.setContentText("Выполнено замен:  " + currentTab.getCount());
                Stage stage_2 = (Stage) alert.getDialogPane().getScene().getWindow();
                stage_2.getIcons().add(new Image(GeneralController.class.getResource("frame.png").toString()));
                alert.showAndWait();
                currentTab.setNullCount();
            });
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Внимание!");
            alert.setHeaderText("Ошибка запрашиваемого действия");
            alert.setContentText("Для выполнения операции замены текста\n" +
                    "откройте имеющийся файл или создайте новую вкладку");
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(GeneralController.class.getResource("frame.png").toString()));
            alert.showAndWait();
        }
    }
    public static void viewPanelButton(CheckMenuItem viewPanelButton, ToolBar toolBar) {
        if (!viewPanelButton.isSelected()) {
            toolBar.setVisible(false);
            toolBar.setLayoutY(-25);
        } else {
            toolBar.setVisible(true);
            toolBar.setLayoutY(25);
        }
    }
    public static void help() throws IOException {
        Stage newStage = new Stage();
        FXMLLoader loader = new FXMLLoader(GeneralController.class.getResource("Help.fxml"));

        Scene scene = new Scene(loader.load());
        newStage.setScene(scene);
        newStage.setTitle("Справка");
        newStage.getIcons().add(new Image("/main/view/img/frame.png"));
        newStage.centerOnScreen();

        newStage.addEventHandler(KeyEvent.KEY_PRESSED, (event) -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                newStage.close();
            }
        });
        newStage.show();
    }
    public static void devInfo() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Сведения о разработчике");
        alert.setHeaderText("ФГБОУ ВО \"ТОМСКИЙ ГОСУДАРСТВЕННЫЙ УНИВЕРСИТЕТ\n" +
                "СИСТЕМ УПРАВЛЕНИЯ И РАДИОЭЛЕКТРОНИКИ» (ТУСУР)\"\n\n" +
                "Факультет дистанционного образования\n\n" +
                "Кафедра автоматизации обработки информации (АОИ)");
        alert.setContentText("Синичев Дмитрий Николаевич (группа з-423П5-5)");
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(GeneralController.class.getResource("frame.png").toString()));
        alert.showAndWait();
    }
}
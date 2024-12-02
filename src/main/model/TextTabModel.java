package main.model;

import javafx.application.Platform;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;

public class TextTabModel extends Tab {
    private TextArea textArea;
    private String mainText;
    private String templateSearch;
    private String templateChange;
    private int count = 0;

    public TextTabModel(String fileName) {
        super(fileName);
        this.textArea = new TextArea();
        this.textArea.setFont(javafx.scene.text.Font.font(20));
        this.setContent(textArea);
        Platform.runLater(() -> textArea.requestFocus());
    }

    public String getTextArea() {
        return textArea.getText();
    }
    public void setTextArea(String text) {
        textArea.setText(text);
    }
    public void setCount() { // searchInText
        mainText = textArea.getText();
        for (int position = 0; position < mainText.length(); ) {
            if (mainText.indexOf(templateSearch, position) >= 0) {
                count++;
                position = mainText.indexOf(templateSearch, position) + 1;
            } else {
                break;
            }
        }
    }
    public void setNullCount() {
        count = 0;
    }
    public int getCount() {
        return count;
    }
    public void setNewMainText() {
        String newMainText = mainText.replaceAll(templateSearch, templateChange);
        textArea.setText(newMainText);
    }
    public void setTemplateSearch(String input) {
        templateSearch = input;
    }
    public void setTemplateChange(String input) {
        templateChange = input;
    }
}
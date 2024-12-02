package tests;

import javafx.application.Application;
import main.model.TextTabModel;
import main.view.Main;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TextTabModelTest {
    @BeforeAll
    public static void setUp() {
        new Thread(() -> Application.launch(Main.class)).start();
    }

    @Test
    void getTextArea() {
        String expected = "getTextAreaTest";
        TextTabModel testTab = new TextTabModel("testTab");
        testTab.setTextArea(expected);
        String actual = testTab.getTextArea();
        assertEquals(expected, actual);
    }

    @Test
    void setTextArea() {
        String expected = "setTextAreaTest";
        TextTabModel testTab = new TextTabModel("testTab");
        testTab.setTextArea(expected);
        String actual = testTab.getTextArea();
        assertEquals(expected, actual);
    }

    @Test
    void setCount() throws NoSuchFieldException {
        TextTabModel testTab = new TextTabModel("testTab");

        int expected = 5;

        testTab.setTextArea("88888");
        Field tMainText = testTab.getClass().getDeclaredField("mainText");
        tMainText.setAccessible(true);
        testTab.setTemplateSearch("8");
        testTab.setCount();

        int actual = testTab.getCount();
        assertEquals(expected, actual);
    }

    @Test
    void setNullCount() {
        int expected = 0;
        TextTabModel testTab = new TextTabModel("testTab");
        testTab.setNullCount();
        int actual = testTab.getCount();
        assertEquals(expected, actual);
    }

    @Test
    void getCount() {
        int expected = 0;
        TextTabModel testTab = new TextTabModel("testTab");
        testTab.setNullCount();
        int actual = testTab.getCount();
        assertEquals(expected, actual);
    }

    @Test
    void setNewMainText() throws NoSuchFieldException, IllegalAccessException {
        TextTabModel testTab = new TextTabModel("testTab");

        String expected = "Change";
        testTab.setTemplateChange(expected);

        testTab.setTemplateSearch("Search");
        Field tMainText = testTab.getClass().getDeclaredField("mainText");
        tMainText.setAccessible(true);
        tMainText.set(testTab, (String) "Change");

        Field tTextArea = testTab.getClass().getDeclaredField("textArea");
        tTextArea.setAccessible(true);

        testTab.setNewMainText();
        String actual = testTab.getTextArea();
        assertEquals(expected, actual);
    }

    @Test
    void setTemplateSearch() throws NoSuchFieldException, IllegalAccessException {
        TextTabModel testTab = new TextTabModel("testTab");

        String expected = "setTemplateSearch";
        testTab.setTemplateSearch(expected);

        Field field = testTab.getClass().getDeclaredField("templateSearch");
        field.setAccessible(true);
        String actual = (String) field.get(testTab);
        assertEquals(expected, actual);
    }

    @Test
    void setTemplateChange() throws NoSuchFieldException, IllegalAccessException {
        TextTabModel testTab = new TextTabModel("testTab");

        String expected = "setTemplateChange";
        testTab.setTemplateChange(expected);

        Field field = testTab.getClass().getDeclaredField("templateChange");
        field.setAccessible(true);
        String actual = (String) field.get(testTab);
        assertEquals(expected, actual);
    }
}
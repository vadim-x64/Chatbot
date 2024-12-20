package com.example.demo.properties;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import java.util.ArrayList;
import java.util.List;

public class Keyboard {
    public static InlineKeyboardMarkup createCloseButtonKeyboard() {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        InlineKeyboardButton closeButton = new InlineKeyboardButton();
        closeButton.setText("Закрити");
        closeButton.setCallbackData("close");
        List<InlineKeyboardButton> row = new ArrayList<>();
        row.add(closeButton);
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        rows.add(row);
        markup.setKeyboard(rows);
        return markup;
    }

    public ReplyKeyboardMarkup buildKeyboard() {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setResizeKeyboard(true);
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow row1 = new KeyboardRow();
        row1.add("Категорії");
        row1.add("Марки");
        KeyboardRow row2 = new KeyboardRow();
        row2.add("Принцип роботи");
        row2.add("Будова");
        keyboard.add(row1);
        keyboard.add(row2);
        keyboardMarkup.setKeyboard(keyboard);
        return keyboardMarkup;
    }

    public InlineKeyboardMarkup buildInlineCategoriesKeyboard() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> row1 = new ArrayList<>();
        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
        inlineKeyboardButton1.setText("Маслкари");
        inlineKeyboardButton1.setCallbackData("muscle");
        row1.add(inlineKeyboardButton1);
        InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
        inlineKeyboardButton2.setText("Спорткари");
        inlineKeyboardButton2.setCallbackData("sport");
        row1.add(inlineKeyboardButton2);
        rowsInline.add(row1);
        List<InlineKeyboardButton> row2 = new ArrayList<>();
        InlineKeyboardButton inlineKeyboardButton3 = new InlineKeyboardButton();
        inlineKeyboardButton3.setText("Вантажні");
        inlineKeyboardButton3.setCallbackData("truck");
        row2.add(inlineKeyboardButton3);
        InlineKeyboardButton inlineKeyboardButton4 = new InlineKeyboardButton();
        inlineKeyboardButton4.setText("Ретро");
        inlineKeyboardButton4.setCallbackData("retro");
        row2.add(inlineKeyboardButton4);
        rowsInline.add(row2);
        List<InlineKeyboardButton> row3 = new ArrayList<>();
        InlineKeyboardButton inlineKeyboardButton5 = new InlineKeyboardButton();
        inlineKeyboardButton5.setText("Закрити");
        inlineKeyboardButton5.setCallbackData("close");
        row3.add(inlineKeyboardButton5);
        rowsInline.add(row3);
        inlineKeyboardMarkup.setKeyboard(rowsInline);
        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup getStructure() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> row1 = new ArrayList<>();
        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
        inlineKeyboardButton1.setText("ДВЗ");
        inlineKeyboardButton1.setCallbackData("engine");
        row1.add(inlineKeyboardButton1);
        InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
        inlineKeyboardButton2.setText("Шасі");
        inlineKeyboardButton2.setCallbackData("chassis");
        row1.add(inlineKeyboardButton2);
        rowsInline.add(row1);
        List<InlineKeyboardButton> row2 = new ArrayList<>();
        InlineKeyboardButton inlineKeyboardButton3 = new InlineKeyboardButton();
        inlineKeyboardButton3.setText("Кузов");
        inlineKeyboardButton3.setCallbackData("body");
        row2.add(inlineKeyboardButton3);
        InlineKeyboardButton inlineKeyboardButton4 = new InlineKeyboardButton();
        inlineKeyboardButton4.setText("Види");
        inlineKeyboardButton4.setCallbackData("species");
        row2.add(inlineKeyboardButton4);
        rowsInline.add(row2);
        List<InlineKeyboardButton> row3 = new ArrayList<>();
        InlineKeyboardButton inlineKeyboardButton5 = new InlineKeyboardButton();
        inlineKeyboardButton5.setText("Основи керування");
        inlineKeyboardButton5.setCallbackData("basics");
        row3.add(inlineKeyboardButton5);
        rowsInline.add(row3);
        List<InlineKeyboardButton> row4 = new ArrayList<>();
        InlineKeyboardButton inlineKeyboardButton6 = new InlineKeyboardButton();
        inlineKeyboardButton6.setText("Техніка управління");
        inlineKeyboardButton6.setCallbackData("tech");
        row4.add(inlineKeyboardButton6);
        rowsInline.add(row4);
        List<InlineKeyboardButton> row5 = new ArrayList<>();
        InlineKeyboardButton inlineKeyboardButton7 = new InlineKeyboardButton();
        inlineKeyboardButton7.setText("Закрити");
        inlineKeyboardButton7.setCallbackData("close");
        row5.add(inlineKeyboardButton7);
        rowsInline.add(row5);
        inlineKeyboardMarkup.setKeyboard(rowsInline);
        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup buildEngineButtons() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> row1 = new ArrayList<>();
        InlineKeyboardButton button1 = new InlineKeyboardButton();
        button1.setText("2 механізми");
        button1.setCallbackData("2_mechanisms");
        row1.add(button1);
        InlineKeyboardButton button2 = new InlineKeyboardButton();
        button2.setText("5 систем");
        button2.setCallbackData("5_systems");
        row1.add(button2);
        rowsInline.add(row1);
        inlineKeyboardMarkup.setKeyboard(rowsInline);
        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup buildChassisButtons() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        InlineKeyboardButton button1 = new InlineKeyboardButton();
        button1.setText("Трансмісія");
        button1.setCallbackData("transmission");
        List<InlineKeyboardButton> row1 = new ArrayList<>();
        row1.add(button1);
        rowsInline.add(row1);
        InlineKeyboardButton button2 = new InlineKeyboardButton();
        button2.setText("Ходова частина");
        button2.setCallbackData("bridges");
        List<InlineKeyboardButton> row2 = new ArrayList<>();
        row2.add(button2);
        rowsInline.add(row2);
        InlineKeyboardButton button3 = new InlineKeyboardButton();
        button3.setText("Механізми керування");
        button3.setCallbackData("control");
        List<InlineKeyboardButton> row3 = new ArrayList<>();
        row3.add(button3);
        rowsInline.add(row3);
        inlineKeyboardMarkup.setKeyboard(rowsInline);
        return inlineKeyboardMarkup;
    }
}
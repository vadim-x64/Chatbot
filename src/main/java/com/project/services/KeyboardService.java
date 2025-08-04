package com.project.services;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class KeyboardService {
    public ReplyKeyboardMarkup getMainKeyboardMarkup() {
        ReplyKeyboardMarkup mainKeyboardMarkup = new ReplyKeyboardMarkup();
        mainKeyboardMarkup.setResizeKeyboard(true);
        mainKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> mainKeyboardRows = new ArrayList<>();
        KeyboardRow mainKeyboardRow = new KeyboardRow();

        mainKeyboardRow.add(new KeyboardButton("Основи"));
        mainKeyboardRow.add(new KeyboardButton("Будова"));
        mainKeyboardRow.add(new KeyboardButton("Історія"));

        mainKeyboardRows.add(mainKeyboardRow);
        mainKeyboardMarkup.setKeyboard(mainKeyboardRows);

        return mainKeyboardMarkup;
    }
}
package com.project.services;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
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

    public ReplyKeyboardMarkup getBasicsKeyboardMarkup() {
        ReplyKeyboardMarkup basicsKeyboardMarkup = new ReplyKeyboardMarkup();
        basicsKeyboardMarkup.setResizeKeyboard(true);
        basicsKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> basicsKeyboardRows = new ArrayList<>();

        KeyboardRow basicsKeyboardRow1 = new KeyboardRow();
        basicsKeyboardRow1.add(new KeyboardButton("Принцип роботи"));
        basicsKeyboardRows.add(basicsKeyboardRow1);

        KeyboardRow basicsKeyboardRow2 = new KeyboardRow();
        basicsKeyboardRow2.add(new KeyboardButton("Основи керування"));
        basicsKeyboardRows.add(basicsKeyboardRow2);

        KeyboardRow basicsKeyboardRow3 = new KeyboardRow();
        basicsKeyboardRow3.add(new KeyboardButton("Техніка управління"));
        basicsKeyboardRows.add(basicsKeyboardRow3);

        KeyboardRow basicsKeyboardRow4 = new KeyboardRow();
        basicsKeyboardRow4.add(new KeyboardButton("Повернутися назад"));
        basicsKeyboardRows.add(basicsKeyboardRow4);

        basicsKeyboardMarkup.setKeyboard(basicsKeyboardRows);
        return basicsKeyboardMarkup;
    }

    public ReplyKeyboardMarkup getHistoryKeyboardMarkup() {
        ReplyKeyboardMarkup basicsKeyboardMarkup = new ReplyKeyboardMarkup();
        basicsKeyboardMarkup.setResizeKeyboard(true);
        basicsKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> basicsKeyboardRows = new ArrayList<>();

        KeyboardRow basicsKeyboardRow1 = new KeyboardRow();
        basicsKeyboardRow1.add(new KeyboardButton("Вступ"));
        basicsKeyboardRow1.add(new KeyboardButton("Марки авто"));
        basicsKeyboardRows.add(basicsKeyboardRow1);

        KeyboardRow basicsKeyboardRow2 = new KeyboardRow();
        basicsKeyboardRow2.add(new KeyboardButton("Цікаві факти"));
        basicsKeyboardRow2.add(new KeyboardButton("Видатні інженери"));
        basicsKeyboardRows.add(basicsKeyboardRow2);

        KeyboardRow basicsKeyboardRow3 = new KeyboardRow();
        basicsKeyboardRow3.add(new KeyboardButton("Повернутися назад"));
        basicsKeyboardRows.add(basicsKeyboardRow3);

        basicsKeyboardMarkup.setKeyboard(basicsKeyboardRows);
        return basicsKeyboardMarkup;
    }

    public InlineKeyboardMarkup getEngineersInlineKeyboard(int currentPage, int totalPages) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();

        List<InlineKeyboardButton> navigationRow = new ArrayList<>();

        if (totalPages > 1) {
            if (currentPage > 0) {
                InlineKeyboardButton prevButton = new InlineKeyboardButton();
                prevButton.setText("Назад");
                prevButton.setCallbackData("previous" + currentPage);
                navigationRow.add(prevButton);
            }

            if (currentPage < totalPages - 1) {
                InlineKeyboardButton nextButton = new InlineKeyboardButton();
                nextButton.setText("Вперед");
                nextButton.setCallbackData("next" + currentPage);
                navigationRow.add(nextButton);
            }

            rows.add(navigationRow);
        }

        inlineKeyboardMarkup.setKeyboard(rows);
        return inlineKeyboardMarkup;
    }
}
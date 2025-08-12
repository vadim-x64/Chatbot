package com.project.services;

import com.fasterxml.jackson.databind.JsonNode;
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

        KeyboardRow mainKeyboardRow1 = new KeyboardRow();
        mainKeyboardRow1.add(new KeyboardButton("Основи"));
        mainKeyboardRow1.add(new KeyboardButton("Будова"));
        mainKeyboardRows.add(mainKeyboardRow1);

        KeyboardRow mainKeyboardRow2 = new KeyboardRow();
        mainKeyboardRow2.add(new KeyboardButton("Історія"));
        mainKeyboardRow2.add(new KeyboardButton("Автомобілі"));
        mainKeyboardRows.add(mainKeyboardRow2);

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

    public InlineKeyboardMarkup getEngineersInlineKeyboard(int currentPage, int totalPages, int currentPhoto, int totalPhotos) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();

        if (totalPhotos > 1) {
            List<InlineKeyboardButton> photoRow = new ArrayList<>();

            if (currentPhoto > 0) {
                InlineKeyboardButton prevPhotoButton = new InlineKeyboardButton();
                prevPhotoButton.setText("⬅️");
                prevPhotoButton.setCallbackData("photo_prev_" + currentPage + "_" + currentPhoto);
                photoRow.add(prevPhotoButton);
            }

            if (currentPhoto < totalPhotos - 1) {
                InlineKeyboardButton nextPhotoButton = new InlineKeyboardButton();
                nextPhotoButton.setText("➡️");
                nextPhotoButton.setCallbackData("photo_next_" + currentPage + "_" + currentPhoto);
                photoRow.add(nextPhotoButton);
            }

            rows.add(photoRow);
        }

        if (totalPages > 1) {
            List<InlineKeyboardButton> navigationRow = new ArrayList<>();

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

    public InlineKeyboardMarkup getBrandsInlineKeyboard() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        JsonNode brands = JsonService.getJsonArray("automobiles", "list");

        if (brands != null) {
            for (int i = 0; i < brands.size(); i += 2) {
                List<InlineKeyboardButton> row = new ArrayList<>();

                for (int j = 0; j < 2 && (i + j) < brands.size(); j++) {
                    JsonNode brand = brands.get(i + j);
                    InlineKeyboardButton button = new InlineKeyboardButton(brand.get("name").asText());
                    button.setCallbackData("auto_brand_" + (i + j));
                    row.add(button);
                }

                rows.add(row);
            }
        }

        inlineKeyboardMarkup.setKeyboard(rows);
        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup getAutomobilesInlineKeyboard(int brandIndex, int currentModel, int totalModels, int currentPhoto, int totalPhotos) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();

        if (totalPhotos > 1) {
            List<InlineKeyboardButton> photoRow = new ArrayList<>();

            if (currentPhoto > 0) {
                InlineKeyboardButton prevPhotoButton = new InlineKeyboardButton();
                prevPhotoButton.setText("⬅️");
                prevPhotoButton.setCallbackData("auto_photo_prev_" + brandIndex + "_" + currentModel + "_" + currentPhoto);
                photoRow.add(prevPhotoButton);
            }

            if (currentPhoto < totalPhotos - 1) {
                InlineKeyboardButton nextPhotoButton = new InlineKeyboardButton();
                nextPhotoButton.setText("➡️");
                nextPhotoButton.setCallbackData("auto_photo_next_" + brandIndex + "_" + currentModel + "_" + currentPhoto);
                photoRow.add(nextPhotoButton);
            }

            rows.add(photoRow);
        }

        if (totalModels > 1) {
            List<InlineKeyboardButton> navigationRow = new ArrayList<>();

            if (currentModel > 0) {
                InlineKeyboardButton prevButton = new InlineKeyboardButton();
                prevButton.setText("Назад");
                prevButton.setCallbackData("auto_model_prev_" + brandIndex + "_" + currentModel);
                navigationRow.add(prevButton);
            }

            if (currentModel < totalModels - 1) {
                InlineKeyboardButton nextButton = new InlineKeyboardButton();
                nextButton.setText("Вперед");
                nextButton.setCallbackData("auto_model_next_" + brandIndex + "_" + currentModel);
                navigationRow.add(nextButton);
            }

            rows.add(navigationRow);
        }

        inlineKeyboardMarkup.setKeyboard(rows);
        return inlineKeyboardMarkup;
    }
}
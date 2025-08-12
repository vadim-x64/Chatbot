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

    public InlineKeyboardMarkup getAutomobilesInlineKeyboard() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();

        try {
            JsonNode carsData = JsonService.getJsonArray("automobiles", "list");

            if (carsData != null) {
                List<InlineKeyboardButton> currentRow = new ArrayList<>();

                for (JsonNode brand : carsData) {
                    String brandName = brand.get("name").asText();

                    InlineKeyboardButton brandButton = new InlineKeyboardButton();
                    brandButton.setText(brandName);
                    brandButton.setCallbackData("car_brand_" + brandName);

                    currentRow.add(brandButton);

                    if (currentRow.size() == 2) {
                        rows.add(new ArrayList<>(currentRow));
                        currentRow.clear();
                    }
                }

                if (!currentRow.isEmpty()) {
                    rows.add(currentRow);
                }
            }
        } catch (Exception ignored) {
        }

        inlineKeyboardMarkup.setKeyboard(rows);
        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup getCarModelsInlineKeyboard(String brandName, int currentModel, int totalModels, int currentPhoto, int totalPhotos) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();

        if (totalPhotos > 1) {
            List<InlineKeyboardButton> photoRow = new ArrayList<>();

            if (currentPhoto > 0) {
                InlineKeyboardButton prevPhotoButton = new InlineKeyboardButton();
                prevPhotoButton.setText("⬅️");
                prevPhotoButton.setCallbackData("car_photo_prev_" + brandName + "_" + currentModel + "_" + currentPhoto);
                photoRow.add(prevPhotoButton);
            }

            if (currentPhoto < totalPhotos - 1) {
                InlineKeyboardButton nextPhotoButton = new InlineKeyboardButton();
                nextPhotoButton.setText("➡️");
                nextPhotoButton.setCallbackData("car_photo_next_" + brandName + "_" + currentModel + "_" + currentPhoto);
                photoRow.add(nextPhotoButton);
            }

            rows.add(photoRow);
        }

        if (totalModels > 1) {
            List<InlineKeyboardButton> modelRow = new ArrayList<>();

            if (currentModel > 0) {
                InlineKeyboardButton prevModelButton = new InlineKeyboardButton();
                prevModelButton.setText("Назад");
                prevModelButton.setCallbackData("car_model_prev_" + brandName + "_" + currentModel);
                modelRow.add(prevModelButton);
            }

            if (currentModel < totalModels - 1) {
                InlineKeyboardButton nextModelButton = new InlineKeyboardButton();
                nextModelButton.setText("Вперед");
                nextModelButton.setCallbackData("car_model_next_" + brandName + "_" + currentModel);
                modelRow.add(nextModelButton);
            }

            rows.add(modelRow);
        }

        List<InlineKeyboardButton> backRow = new ArrayList<>();
        InlineKeyboardButton backButton = new InlineKeyboardButton();
        backButton.setText("Повернутися назад");
        backButton.setCallbackData("back_to_automobiles");
        backRow.add(backButton);
        rows.add(backRow);

        inlineKeyboardMarkup.setKeyboard(rows);
        return inlineKeyboardMarkup;
    }
}
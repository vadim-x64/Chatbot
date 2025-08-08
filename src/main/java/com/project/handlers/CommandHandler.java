package com.project.handlers;

import com.fasterxml.jackson.databind.JsonNode;
import com.project.services.JsonService;
import com.project.services.KeyboardService;
import com.project.services.MessageService;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.Objects;

public class CommandHandler {

    private final MessageService _messageService;
    private final KeyboardService _keyboardService;

    public CommandHandler(MessageService messageService) {
        _messageService = messageService;
        _keyboardService = new KeyboardService();
    }

    public void handleStartCommand(long chatId, User user) {
        String username = user.getUserName() != null ? user.getUserName() : user.getFirstName();
        String responseText = JsonService.getTextWithPlaceholder("basePhrases", "greeting", "username", username);
        _messageService.sendMessageWithMainKeyboard(chatId, responseText, _keyboardService.getMainKeyboardMarkup());
    }

    public void handleUnknownMessage(long chatId) {
        String responseText = JsonService.getText("basePhrases", "unknownMessage");
        _messageService.sendMessage(chatId, responseText);
    }

    public void handleReturnBackButton(long chatId) {
        String responseText = JsonService.getText("basePhrases", "returnToMainMenu");
        _messageService.sendMessageWithMainKeyboard(chatId, responseText, _keyboardService.getMainKeyboardMarkup());
    }

    public void handleCategoryCommand(long chatId, String category) {
        switch (category) {
            case "Основи" -> {
                String responseText = JsonService.getText("generalPhrases", "general");
                _messageService.sendMessageWithMainKeyboard(chatId, responseText, _keyboardService.getBasicsKeyboardMarkup());
            }
            case "Основи керування" -> {
                String responseText = JsonService.getText("basicsManager", "basics");
                _messageService.sendMessage(chatId, responseText);
            }
            case "Техніка управління" -> {
                String responseText = JsonService.getText("basicsManager", "tech");
                _messageService.sendMessage(chatId, responseText);
            }
            case "Принцип роботи" -> {
                String responseText = JsonService.getText("basicsManager", "principle");
                _messageService.sendMessage(chatId, responseText);
            }

            case "Будова" -> {
                String responseText = JsonService.getText("generalPhrases", "structure");
                _messageService.sendMessage(chatId, responseText);
            }

            case "Історія" -> {
                String responseText = JsonService.getText("generalPhrases", "history");
                _messageService.sendMessageWithMainKeyboard(chatId, responseText, _keyboardService.getHistoryKeyboardMarkup());
            }
            case "Вступ" -> {
                String responseText = JsonService.getText("introduction", "intro");

                int splitIndex = responseText.length() > 1000
                        ? responseText.lastIndexOf(" ", 1000)
                        : responseText.length();

                String firstPart = responseText.substring(0, splitIndex).trim();
                String secondPart = responseText.length() > splitIndex
                        ? responseText.substring(splitIndex).trim()
                        : null;

                String imageUrl = JsonService.getText("introduction", "historyImageUrl");
                _messageService.sendPhotoWithCaptionAndKeyboard(chatId, imageUrl, firstPart, _keyboardService.getHistoryKeyboardMarkup());

                if (secondPart != null && !secondPart.isBlank()) {
                    _messageService.sendMessage(chatId, secondPart);
                }
            }
            case "Видатні інженери" -> handleEngineersCommand(chatId, 0, 0);
        }
    }

    public void handleEngineersCommand(long chatId, int page, int photoIndex) {
        try {
            JsonNode engineersData = JsonService.getJsonArray("engineers", "list");
            int totalEngineers = Objects.requireNonNull(engineersData).size();

            if (page < 0) page = totalEngineers - 1;
            if (page >= totalEngineers) page = 0;

            JsonNode currentEngineer = engineersData.get(page);
            String description = currentEngineer.get("description").asText();

            JsonNode imageUrl = currentEngineer.get("imageUrl");
            String imageUri;
            int totalPhotos = 1;

            if (imageUrl.isArray()) {
                totalPhotos = imageUrl.size();
                if (photoIndex < 0) photoIndex = totalPhotos - 1;
                if (photoIndex >= totalPhotos) photoIndex = 0;
                imageUri = imageUrl.get(photoIndex).asText();
            } else {
                photoIndex = 0;
                imageUri = imageUrl.asText();
            }

            String caption = description + "Сторінка " + (page + 1) + " з " + totalEngineers;

            if (totalPhotos > 1) {
                caption += "\nФото " + (photoIndex + 1) + " з " + totalPhotos;
            }

            _messageService.sendPhotoWithInlineKeyboard(chatId, imageUri, caption, _keyboardService.getEngineersInlineKeyboard(page, totalEngineers, photoIndex, totalPhotos));

        } catch (Exception ignored) {
        }
    }

    public void handleEngineersPagination(long chatId, int messageId, int page, int photoIndex) {
        try {
            JsonNode engineersData = JsonService.getJsonArray("engineers", "list");

            int totalEngineers = Objects.requireNonNull(engineersData).size();

            if (page < 0) page = totalEngineers - 1;
            if (page >= totalEngineers) page = 0;

            JsonNode currentEngineer = engineersData.get(page);
            String description = currentEngineer.get("description").asText();

            JsonNode imageUrl = currentEngineer.get("imageUrl");
            String imageUri;
            int totalPhotos = 1;

            if (imageUrl.isArray()) {
                totalPhotos = imageUrl.size();
                if (photoIndex < 0) photoIndex = totalPhotos - 1;
                if (photoIndex >= totalPhotos) photoIndex = 0;
                imageUri = imageUrl.get(photoIndex).asText();
            } else {
                photoIndex = 0;
                imageUri = imageUrl.asText();
            }
            String caption = description + "Сторінка " + (page + 1) + " з " + totalEngineers;

            if (totalPhotos > 1) {
                caption += "\nФото " + (photoIndex + 1) + " з " + totalPhotos;
            }

            _messageService.editMessageMedia(chatId, messageId, imageUri, caption, _keyboardService.getEngineersInlineKeyboard(page, totalEngineers, photoIndex, totalPhotos));

        } catch (Exception ignored) {
        }
    }
}
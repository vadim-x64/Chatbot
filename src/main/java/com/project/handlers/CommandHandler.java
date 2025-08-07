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

                String firstPart = responseText.length() > 1000
                        ? responseText.substring(0, 1000)
                        : responseText;

                String secondPart = responseText.length() > 1000
                        ? responseText.substring(1000)
                        : null;

                String imageUrl = JsonService.getText("introduction", "historyImageUrl");

                _messageService.sendPhotoWithCaptionAndKeyboard(chatId, imageUrl, firstPart, _keyboardService.getHistoryKeyboardMarkup());

                if (secondPart != null && !secondPart.isBlank()) {
                    _messageService.sendMessage(chatId, secondPart.trim());
                }
            }
            case "Видатні інженери" -> handleEngineersCommand(chatId, 0);
        }
    }

    public void handleEngineersCommand(long chatId, int page) {
        try {
            JsonNode engineersData = JsonService.getJsonArray("engineers", "list");

            int totalEngineers = Objects.requireNonNull(engineersData).size();

            if (page < 0) page = totalEngineers - 1;
            if (page >= totalEngineers) page = 0;

            JsonNode currentEngineer = engineersData.get(page);
            String description = currentEngineer.get("description").asText();
            String imageUrl = currentEngineer.get("imageUrl").asText();

            String caption = String.format(description, page + 1, totalEngineers);

            _messageService.sendPhotoWithInlineKeyboard(chatId, imageUrl, caption,
                    _keyboardService.getEngineersInlineKeyboard(page, totalEngineers));

        } catch (Exception ignored) {
        }
    }

    public void handleEngineersPagination(long chatId, int messageId, int page) {
        try {
            JsonNode engineersData = JsonService.getJsonArray("engineers", "list");

            int totalEngineers = Objects.requireNonNull(engineersData).size();

            if (page < 0) page = totalEngineers - 1;
            if (page >= totalEngineers) page = 0;

            JsonNode currentEngineer = engineersData.get(page);

            String description = currentEngineer.get("description").asText();
            String imageUrl = currentEngineer.get("imageUrl").asText();
            String caption = String.format(description, page + 1, totalEngineers);

            _messageService.editMessageMedia(chatId, messageId, imageUrl, caption,
                    _keyboardService.getEngineersInlineKeyboard(page, totalEngineers));

        } catch (Exception ignored) {
        }
    }
}
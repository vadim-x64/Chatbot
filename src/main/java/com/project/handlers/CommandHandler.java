package com.project.handlers;

import com.project.services.JsonService;
import com.project.services.KeyboardService;
import com.project.services.MessageService;
import org.telegram.telegrambots.meta.api.objects.User;

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
            case "Будова" -> {
                String responseText = JsonService.getText("generalPhrases", "structure");
                _messageService.sendMessage(chatId, responseText);
            }
            case "Історія" -> {
                String responseText = JsonService.getText("generalPhrases", "history");

                String firstPart = responseText.length() > 1000
                        ? responseText.substring(0, 1000)
                        : responseText;

                String secondPart = responseText.length() > 1000
                        ? responseText.substring(1000)
                        : null;

                String imageUrl = JsonService.getText("generalPhrases", "historyImageUrl");

                _messageService.sendPhotoWithCaptionAndKeyboard(chatId, imageUrl, firstPart, _keyboardService.getHistoryKeyboardMarkup());

                if (secondPart != null && !secondPart.isBlank()) {
                    _messageService.sendMessage(chatId, secondPart.trim());
                }
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
        }
    }
}
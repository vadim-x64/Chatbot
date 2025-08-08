package com.project;

import com.project.configuration.BotConfig;
import com.project.handlers.CommandHandler;
import com.project.services.MessageService;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class TelegramBot extends TelegramLongPollingBot {

    private final BotConfig _botConfig;
    private final CommandHandler _commandHandler;

    public TelegramBot() {
        _botConfig = new BotConfig();
        MessageService _messageService = new MessageService(this);
        _commandHandler = new CommandHandler(_messageService);
    }

    @Override
    public String getBotUsername() {
        return _botConfig.getBotUsername();
    }

    @Override
    public String getBotToken() {
        return _botConfig.getBotToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            long chatId = update.getMessage().getChatId();
            User user = update.getMessage().getFrom();

            if (update.getMessage().hasText()) {
                String messageText = update.getMessage().getText();

                switch (messageText) {
                    case "/start", "/restart" -> _commandHandler.handleStartCommand(chatId, user);
                    case "Видатні інженери", "Основи", "Будова", "Історія", "Основи керування", "Техніка управління",
                         "Принцип роботи", "Вступ" -> _commandHandler.handleCategoryCommand(chatId, messageText);
                    case "Повернутися назад" -> _commandHandler.handleReturnBackButton(chatId);
                    default -> _commandHandler.handleUnknownMessage(chatId);
                }
            } else {
                _commandHandler.handleUnknownMessage(chatId);
            }
        } else if (update.hasCallbackQuery()) {
            long chatId = update.getCallbackQuery().getMessage().getChatId();
            int messageId = update.getCallbackQuery().getMessage().getMessageId();
            String callbackData = update.getCallbackQuery().getData();

            if (callbackData.startsWith("previous")) {
                int currentPage = Integer.parseInt(callbackData.replace("previous", ""));
                _commandHandler.handleEngineersPagination(chatId, messageId, currentPage - 1, 0);
            } else if (callbackData.startsWith("next")) {
                int currentPage = Integer.parseInt(callbackData.replace("next", ""));
                _commandHandler.handleEngineersPagination(chatId, messageId, currentPage + 1, 0);
            } else if (callbackData.startsWith("photo_prev_")) {
                String[] parts = callbackData.replace("photo_prev_", "").split("_");
                int currentPage = Integer.parseInt(parts[0]);
                int currentPhoto = Integer.parseInt(parts[1]);
                _commandHandler.handleEngineersPagination(chatId, messageId, currentPage, currentPhoto - 1);
            } else if (callbackData.startsWith("photo_next_")) {
                String[] parts = callbackData.replace("photo_next_", "").split("_");
                int currentPage = Integer.parseInt(parts[0]);
                int currentPhoto = Integer.parseInt(parts[1]);
                _commandHandler.handleEngineersPagination(chatId, messageId, currentPage, currentPhoto + 1);
            }
        }
    }
}
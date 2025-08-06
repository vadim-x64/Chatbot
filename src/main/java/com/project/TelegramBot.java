package com.project;

import com.project.configuration.BotConfig;
import com.project.handlers.CommandHandler;
import com.project.services.MessageService;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

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
                    case "Основи", "Будова", "Історія", "Основи керування", "Техніка управління", "Принцип роботи" ->
                            _commandHandler.handleCategoryCommand(chatId, messageText);
                    case "Повернутися назад" -> _commandHandler.handleReturnBackButton(chatId);
                    default -> _commandHandler.handleUnknownMessage(chatId);
                }
            } else {
                _commandHandler.handleUnknownMessage(chatId);
            }
        }
    }
}
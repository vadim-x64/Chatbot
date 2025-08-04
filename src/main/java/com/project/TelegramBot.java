package com.project;

import com.project.configuration.BotConfig;
import com.project.handlers.CommandHandler;
import com.project.services.MessageService;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

public class TelegramBot extends TelegramLongPollingBot {

    private final BotConfig _botConfig;
    private final MessageService _messageService;
    private final CommandHandler _commandHandler;

    public TelegramBot() {
        _botConfig = new BotConfig();
        _messageService = new MessageService(this);
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

                if (messageText.equals("/start") || messageText.equals("/restart")) {
                    _commandHandler.handleStartCommand(chatId, user);
                } else if (messageText.equals("Основи") ||
                        messageText.equals("Будова") ||
                        messageText.equals("Історія")) {
                    _commandHandler.handleCategoryCommand(chatId, messageText);
                } else if (messageText.equals("Повернутися назад")) {
                    _commandHandler.handleReturnBackButton(chatId);
                } else {
                    _commandHandler.handleUnknownMessage(chatId);
                }
            } else {
                _commandHandler.handleUnknownMessage(chatId);
            }
        }
    }
}
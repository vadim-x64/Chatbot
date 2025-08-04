package com.project.services;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class MessageService {

    private final TelegramLongPollingBot _telegramLongPollingBot;

    public MessageService(TelegramLongPollingBot telegramLongPollingBot) {
        _telegramLongPollingBot = telegramLongPollingBot;
    }

    public void sendMessage(long chatId, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(text);

        try {
            _telegramLongPollingBot.execute(sendMessage);
        } catch (TelegramApiException ignored) {
        }
    }
}
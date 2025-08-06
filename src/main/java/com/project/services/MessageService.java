package com.project.services;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
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

    public void sendMessageWithMainKeyboard(long chatId, String text, ReplyKeyboardMarkup mainKeyboardMarkup) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(text);
        sendMessage.setReplyMarkup(mainKeyboardMarkup);

        try {
            _telegramLongPollingBot.execute(sendMessage);
        } catch (TelegramApiException ignored) {
        }
    }

    public void sendPhotoWithCaptionAndKeyboard(long chatId, String photoUrl, String caption, ReplyKeyboardMarkup keyboardMarkup) {
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(String.valueOf(chatId));
        sendPhoto.setPhoto(new InputFile(photoUrl));
        sendPhoto.setCaption(caption);
        sendPhoto.setReplyMarkup(keyboardMarkup);

        try {
            _telegramLongPollingBot.execute(sendPhoto);
        } catch (TelegramApiException ignored) {
        }
    }
}
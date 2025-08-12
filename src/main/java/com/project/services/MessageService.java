package com.project.services;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageMedia;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
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

    public void sendMessageWithInlineKeyboard(long chatId, String text, InlineKeyboardMarkup inlineKeyboardMarkup) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(text);
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);

        try {
            _telegramLongPollingBot.execute(sendMessage);
        } catch (TelegramApiException ignored) {
        }
    }

    public Message sendPhotoWithInlineKeyboard(long chatId, String photoUrl, String caption, InlineKeyboardMarkup inlineKeyboardMarkup) {
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(String.valueOf(chatId));
        sendPhoto.setPhoto(new InputFile(photoUrl));
        sendPhoto.setCaption(caption);
        sendPhoto.setReplyMarkup(inlineKeyboardMarkup);

        try {
            return _telegramLongPollingBot.execute(sendPhoto);
        } catch (TelegramApiException ignored) {
        }

        return null;
    }

    public void editMessageMedia(long chatId, int messageId, String photoUrl, String caption, InlineKeyboardMarkup inlineKeyboardMarkup) {
        EditMessageMedia editMessageMedia = new EditMessageMedia();
        editMessageMedia.setChatId(String.valueOf(chatId));
        editMessageMedia.setMessageId(messageId);

        InputMediaPhoto media = new InputMediaPhoto();
        media.setMedia(photoUrl);
        media.setCaption(caption);

        editMessageMedia.setMedia(media);
        editMessageMedia.setReplyMarkup(inlineKeyboardMarkup);

        try {
            _telegramLongPollingBot.execute(editMessageMedia);
        } catch (TelegramApiException ignored) {
        }
    }
}
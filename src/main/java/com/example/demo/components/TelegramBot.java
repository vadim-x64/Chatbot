package com.example.demo.components;

import com.example.demo.entities.History;
import com.example.demo.interfaces.HistoryRepos;
import com.example.demo.properties.Keyboard;
import com.example.demo.properties.MessageLoader;
import com.example.demo.properties.Properties;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import java.io.File;
import java.io.IOException;
import java.util.List;

@AllArgsConstructor
@Component
public class TelegramBot extends TelegramLongPollingBot {
    private final Properties properties;
    private final MarksButton marksButton;
    private final HistoryRepos historyRepos;
    private final CategoryButton categoryButton;
    private final StructureButton structureButton;
    private final PrincipleButton principleButton;

    @Override
    public String getBotUsername() {
        return properties.getBotName();
    }

    @Override
    public String getBotToken() {
        return properties.getBotToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            String username = update.getMessage().getChat().getFirstName();
            long chatId = update.getMessage().getChatId();
            if (update.getMessage().hasText()) {
                String messageText = update.getMessage().getText();
                if (messageText.startsWith("/")) {
                    handleCommand(messageText, chatId, username, update);
                } else if (messageText.equals("Категорії")) {
                    categoryButton.sendInlineCategoriesKeyboard(chatId);
                } else if (messageText.equals("Будова")) {
                    structureButton.handleTextMessage(chatId);
                } else if (messageText.equals("Принцип роботи")) {
                    principleButton.sendPrinciple(chatId);
                } else if (messageText.equals("Марки")) {
                    marksButton.sendMarks(chatId);
                } else {
                    sendMessage(chatId);
                }
            } else {
                sendMessage(chatId);
            }
        } else if (update.hasCallbackQuery()) {
            String callbackData = update.getCallbackQuery().getData();

            if ("close".equals(callbackData)) {
                try {
                    int messageId = update.getCallbackQuery().getMessage().getMessageId();
                    String chatId = String.valueOf(update.getCallbackQuery().getMessage().getChatId());
                    execute(new DeleteMessage(chatId, messageId));
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else {
                structureButton.onUpdateReceived(update);
            }
        }
    }

    private void handleCommand(String command, long chatId, String username, Update update) {
        switch (command) {
            case "/start":
                sendStartMessage(chatId, username);
                return;
            case "/restart":
                sendRestartMessage(chatId);
                return;
            case "/history":
                sendHistoryMessage(chatId);
                break;
            case "/info":
                sendForumMessage(chatId);
                break;
            case "/references":
                sendReferencesMessage(chatId);
                break;
            default:
                sendMessage(chatId);
        }
        try {
            Thread.sleep(0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            execute(new DeleteMessage(String.valueOf(chatId), update.getMessage().getMessageId()));
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendStartMessage(long chatId, String username) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText("Привіт, " + username + MessageLoader.getMessage("telegram_info1"));
        Keyboard keyboardBuilder = new Keyboard();
        ReplyKeyboardMarkup keyboardMarkup = keyboardBuilder.buildKeyboard();
        keyboardMarkup.setOneTimeKeyboard(false);
        message.setReplyMarkup(keyboardMarkup);
        message.enableMarkdown(true);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendRestartMessage(long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(MessageLoader.getMessage("telegram_info2"));
        Keyboard keyboardBuilder = new Keyboard();
        ReplyKeyboardMarkup keyboardMarkup = keyboardBuilder.buildKeyboard();
        keyboardMarkup.setOneTimeKeyboard(false);
        message.setReplyMarkup(keyboardMarkup);
        message.enableMarkdown(true);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendForumMessage(long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(MessageLoader.getMessage("telegram_info3"));
        message.enableMarkdown(true);
        message.setReplyMarkup(Keyboard.createCloseButtonKeyboard());
        try {
            execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendReferencesMessage(long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(MessageLoader.getMessage("telegram_info4"));
        message.enableMarkdown(true);
        message.disableWebPagePreview();
        message.setReplyMarkup(Keyboard.createCloseButtonKeyboard());
        try {
            execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendHistoryMessage(long chatId) {
        List<History> histories = historyRepos.findAll();
        for (History history : histories) {
            String photo = history.getPhoto();
            String text = history.getText();
            String symbol = "<i>\uD83D\uDCDC На фото 1885 рік. Берта з синами на автомобілі Карла Бенца.\n\n</i>";
            String detailsUrl = "https://surl.li/thawo";
            String caption = symbol + text + " \uD83D\uDC49 <a href='" + detailsUrl + "'>Читати</a> \uD83D\uDC48";
            sendPhotoWithCaption(chatId, photo, caption);
        }
    }

    private void sendPhotoWithCaption(long chatId, String photoPath, String caption) {
        SendPhoto photo = new SendPhoto();
        photo.setChatId(String.valueOf(chatId));
        try {
            File file = new ClassPathResource(photoPath).getFile();
            photo.setPhoto(new InputFile(file));
            photo.setCaption(caption);
            photo.setParseMode("HTML");
            photo.setReplyMarkup(Keyboard.createCloseButtonKeyboard());
            execute(photo);
        } catch (TelegramApiException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendMessage(long chatId) {
        SendMessage message1 = new SendMessage();
        message1.setChatId(String.valueOf(chatId));
        message1.setText("\uD83E\uDD14");
        SendMessage message2 = new SendMessage();
        message2.setChatId(String.valueOf(chatId));
        message2.setText("Упс! Ви ввели невірну команду! Будь ласка, спробуйте ще раз.");
        try {
            execute(message1);
            execute(message2);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
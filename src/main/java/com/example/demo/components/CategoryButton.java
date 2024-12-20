package com.example.demo.components;

import java.io.File;
import java.util.List;
import java.io.IOException;
import java.util.ArrayList;
import lombok.AllArgsConstructor;
import com.example.demo.entities.Car;
import com.example.demo.properties.Keyboard;
import com.example.demo.entities.VideoPaths;
import com.example.demo.interfaces.CarRepos;
import com.example.demo.entities.TypesImages;
import com.example.demo.properties.Properties;
import org.springframework.stereotype.Component;
import org.springframework.core.io.ClassPathResource;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.send.SendVideo;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

@AllArgsConstructor
@Component
public class CategoryButton extends TelegramLongPollingBot {
    private final Properties c;
    private final CarRepos carRepos;

    @Override
    public String getBotUsername() {
        return c.getBotName();
    }

    @Override
    public String getBotToken() {
        return c.getBotToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        CallbackQuery callbackQuery = update.getCallbackQuery();
        String callbackData = callbackQuery.getData();
        long chatId = callbackQuery.getMessage().getChatId();
        if (callbackData.equals("Категорії")) {
            sendInlineCategoriesKeyboard(chatId);
        } else if (!callbackData.equals("Будова")) {
            sendCarsByCategory(chatId, callbackData);
        }
        if (callbackData.equals("close")) {
            deleteMessage(chatId, callbackQuery.getMessage().getMessageId());
        }
    }

    public void deleteMessage(long chatId, int messageId) {
        DeleteMessage deleteMessage = new DeleteMessage();
        deleteMessage.setChatId(String.valueOf(chatId));
        deleteMessage.setMessageId(messageId);
        try {
            execute(deleteMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendInlineCategoriesKeyboard(long chatId) {
        Keyboard keyboardBuilder = new Keyboard();
        InlineKeyboardMarkup inlineKeyboardMarkup = keyboardBuilder.buildInlineCategoriesKeyboard();
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(
                """
                        Що сказати про автомобіль?
                        
                        Це свобода. Це річ, яка відображає Ваш стиль та особистість. \
                        Це предмет життя, який змінює Ваше сприйняття часу. Особливу увагу хочеться приділити \
                        американському автопрому. Саме їхні машини демонструють усе те, що виражається \
                        в цих типах авто. Кожен варіант пропонує свої унікальні переваги та вражає своїм стилем, \
                        дизайном та індивідуальністю. Це справжні монстри на дорогах.\s
                        
                        Дані представники \
                        популярні ще по цей день. Більшість з них можна побачити в кліпах, фільмах, передачах і так далі, \
                        причому, авто зустрічаються і 50-х і 60-х років. Американці вкладають багато часу, багато коштів \
                        та сили в створення, вдосконалення та тюнінг. Тому і виходить, що кожна тачка по-своєму унікальна, \
                        потужна та набирає популярності серед автолюбителів. Задній привід, механічна коробка передач, \
                        під капотом 1000 кінських сил, стиль, потужність, прості форми та чіткі лінії - ось, що означає \
                        класика. Ось що означає справжнє авто. Про ці витвори мистецтва можна говорити і говорити \
                        але, є варіант познайомитися з легендами, ретрокарами та ексклюзивами автоіндустрії. Поїхали 🏁\s\s
                        
                        1️⃣ Натисніть на тип авто
                        
                        2️⃣ Перегляньте інформацію
                        
                        3️⃣ Поділіться з однодумцями
                        
                        https://t.me/HellTiresBot""");
        message.setReplyMarkup(inlineKeyboardMarkup);
        try {
            Message sentMessage = execute(message);
            int sentMessageId = sentMessage.getMessageId();
            deleteMessage(chatId, sentMessageId - 1);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendCarsByCategory(long chatId, String category) {
        List<Car> cars = switch (category) {
            case "muscle" -> carRepos.findByCategoryName("Маслкари");
            case "sport" -> carRepos.findByCategoryName("Спорткари");
            case "truck" -> carRepos.findByCategoryName("Вантажні");
            case "retro" -> carRepos.findByCategoryName("Ретро");
            default -> new ArrayList<>();
        };
        if (!cars.isEmpty()) {
            for (Car car : cars) {
                List<String> photoPaths = new ArrayList<>();
                List<String> videoPaths = new ArrayList<>();
                for (TypesImages image : car.getImages()) {
                    photoPaths.add(image.getPhotoPath());
                }
                for (VideoPaths video : car.getVideos()) {
                    videoPaths.add(video.getVideoPath());
                }
                String description = car.getDescription();
                sendImagesWithCaption(chatId, photoPaths, videoPaths, description);
            }
        }
    }

    private void sendImagesWithCaption(long chatId, List<String> photos, List<String> videos, String caption) {
        for (String photoPath : photos) {
            SendPhoto sendPhoto = new SendPhoto();
            sendPhoto.setChatId(String.valueOf(chatId));
            try {
                File file = new ClassPathResource(photoPath).getFile();
                InputFile inputFile = new InputFile(file);
                sendPhoto.setPhoto(inputFile);
                execute(sendPhoto);
            } catch (IOException | TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }
        for (String videoPath : videos) {
            SendVideo sendVideo = new SendVideo();
            sendVideo.setChatId(String.valueOf(chatId));
            try {
                File videoFile = new ClassPathResource(videoPath).getFile();
                InputFile inputFile = new InputFile(videoFile);
                sendVideo.setVideo(inputFile);
                execute(sendVideo);
            } catch (IOException | TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(caption);
        message.setParseMode("HTML");
        try {
            execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
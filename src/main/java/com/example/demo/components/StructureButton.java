package com.example.demo.components;

import com.example.demo.entities.Types;
import com.example.demo.entities.TypesImages;
import com.example.demo.entities.VideoPaths;
import com.example.demo.interfaces.TypesRepos;
import com.example.demo.properties.Keyboard;
import com.example.demo.properties.MessageLoader;
import com.example.demo.properties.Properties;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.send.SendVideo;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Component
public class StructureButton extends TelegramLongPollingBot {
    private final Properties properties;
    private final TypesRepos typesRepos;
    private final CategoryButton categoryButton;
    private final Map<Long, List<Integer>> sentMessages = new HashMap<>();

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
        CallbackQuery callbackQuery = update.getCallbackQuery();
        String callbackData = callbackQuery.getData();
        long chatId = callbackQuery.getMessage().getChatId();
        if (callbackData.equals("Категорії")) {
            categoryButton.sendInlineCategoriesKeyboard(chatId);
        } else if (!callbackData.equals("Будова")) {
            categoryButton.sendCarsByCategory(chatId, callbackData);
        }
        if (callbackData.startsWith("close")) {
            List<Integer> messageIds = sentMessages.get(chatId);
            if (messageIds != null) {
                for (int messageId : messageIds) {
                    deleteMessage(chatId, messageId);
                }
                sentMessages.remove(chatId);
            }
        } else {
            switch (callbackData) {
                case "engine":
                case "chassis":
                case "body":
                case "species":
                    sendStructureTypes(chatId, callbackData);
                    break;
                case "2_mechanisms":
                    sendMechanismsDescription(chatId);
                    break;
                case "5_systems":
                    sendSystemsDescription(chatId);
                    break;
                case "transmission":
                    sendTransmissionDescription(chatId);
                    break;
                case "bridges":
                    sendBridgesDescription(chatId);
                    break;
                case "control":
                    sendControlDescription(chatId);
                    break;
                case "basics":
                    sendBasicsManagement(chatId);
                    break;
                case "tech":
                    sendTechManagement(chatId);
                    break;
                default:
                    break;
            }
        }
    }

    private void sendTechManagement(long chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(MessageLoader.getMessage("structure_info1"));
        sendMessage.enableMarkdown(true);
        sendMessage.setReplyMarkup(Keyboard.createCloseButtonKeyboard());
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendBasicsManagement(long chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(MessageLoader.getMessage("structure_info2"));
        sendMessage.enableMarkdown(true);
        sendMessage.setReplyMarkup(Keyboard.createCloseButtonKeyboard());
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private void deleteMessage(long chatId, int messageId) {
        DeleteMessage deleteMessage = new DeleteMessage();
        deleteMessage.setChatId(String.valueOf(chatId));
        deleteMessage.setMessageId(messageId);
        try {
            execute(deleteMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendError(long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText("Наразі ця категорія порожня!");
        try {
            execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendMechanismsDescription(long chatId) {
        sendData(chatId, "2_mechanisms");
    }

    private void sendSystemsDescription(long chatId) {
        sendData(chatId, "5_systems");
    }

    private void sendTransmissionDescription(long chatId) {
        sendData(chatId, "transmission");
    }

    private void sendBridgesDescription(long chatId) {
        sendData(chatId, "bridges");
    }

    private void sendControlDescription(long chatId) {
        sendData(chatId, "control");
    }

    private void sendData(long chatId, String callback) {
        List<Types> types = typesRepos.findByName(callback);
        if (types.isEmpty()) {
            sendError(chatId);
            return;
        }
        List<Integer> messageIds = sentMessages.computeIfAbsent(chatId, k -> new ArrayList<>());
        for (Types type : types) {
            List<String> photoPaths = new ArrayList<>();
            List<String> videoPaths = new ArrayList<>();
            for (TypesImages image : type.getImages()) {
                photoPaths.add(image.getPhotoPath());
            }
            for (VideoPaths video : type.getVideos()) {
                videoPaths.add(video.getVideoPath());
            }
            String description = type.getDescription();
            sendStructureInfo(chatId, photoPaths, videoPaths, description, callback.equals("engine"), callback.equals("chassis"), messageIds);
        }
        sendCloseButton(chatId, messageIds);
    }

    public void handleTextMessage(long chatId) {
        Keyboard keyboardBuilder = new Keyboard();
        InlineKeyboardMarkup inlineKeyboardMarkup = keyboardBuilder.getStructure();
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(MessageLoader.getMessage("structure_info3"));
        message.setReplyMarkup(inlineKeyboardMarkup);
        try {
            Message sentMessage = execute(message);
            int sentMessageId = sentMessage.getMessageId();
            deleteMessage(chatId, sentMessageId - 1);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendStructureTypes(long chatId, String callback) {
        List<Types> types;
        switch (callback) {
            case "engine" -> types = typesRepos.findByName("ДВЗ");
            case "chassis" -> types = typesRepos.findByName("Шасі");
            case "body" -> types = typesRepos.findByName("Кузов");
            case "species" -> types = typesRepos.findByName("Види");
            default -> {
                return;
            }
        }
        if (types.isEmpty()) {
            sendError(chatId);
            return;
        }
        List<Integer> messageIds = sentMessages.computeIfAbsent(chatId, k -> new ArrayList<>());
        for (Types type : types) {
            List<String> photoPaths = new ArrayList<>();
            List<String> videoPaths = new ArrayList<>();
            for (TypesImages image : type.getImages()) {
                photoPaths.add(image.getPhotoPath());
            }
            for (VideoPaths video : type.getVideos()) {
                videoPaths.add(video.getVideoPath());
            }
            String description = type.getDescription();
            sendStructureInfo(chatId, photoPaths, videoPaths, description, callback.equals("engine"), callback.equals("chassis"), messageIds);
        }
        sendCloseButton(chatId, messageIds);
    }

    public void sendStructureInfo(long chatId, List<String> photos, List<String> videos, String caption, boolean isEngine, boolean isChassis, List<Integer> messageIds) {
        for (String photoPath : photos) {
            SendPhoto sendPhoto = new SendPhoto();
            sendPhoto.setChatId(String.valueOf(chatId));
            try {
                File photoFile = new ClassPathResource(photoPath).getFile();
                InputFile inputFile = new InputFile(photoFile);
                sendPhoto.setPhoto(inputFile);
                Message sentPhotoMessage = execute(sendPhoto);
                messageIds.add(sentPhotoMessage.getMessageId());
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
                Message sentVideoMessage = execute(sendVideo);
                messageIds.add(sentVideoMessage.getMessageId());
            } catch (IOException | TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(caption);
        message.setParseMode("HTML");
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        if (isEngine) {
            Keyboard keyboardBuilder = new Keyboard();
            InlineKeyboardMarkup engineButtons = keyboardBuilder.buildEngineButtons();
            keyboard.addAll(engineButtons.getKeyboard());
        } else if (isChassis) {
            Keyboard keyboardBuilder = new Keyboard();
            InlineKeyboardMarkup chassisButtons = keyboardBuilder.buildChassisButtons();
            keyboard.addAll(chassisButtons.getKeyboard());
        }
        inlineKeyboardMarkup.setKeyboard(keyboard);
        message.setReplyMarkup(inlineKeyboardMarkup);
        try {
            Message sentMessage = execute(message);
            messageIds.add(sentMessage.getMessageId());
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendCloseButton(long chatId, List<Integer> messageIds) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText("Бажаєте повернутися до головного меню? Натисніть, щоб закрити розділ \uD83D\uDC47");
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton closeButton = new InlineKeyboardButton();
        closeButton.setText("Закрити");
        closeButton.setCallbackData("close_body");
        List<InlineKeyboardButton> closeButtonRow = new ArrayList<>();
        closeButtonRow.add(closeButton);
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        keyboard.add(closeButtonRow);
        inlineKeyboardMarkup.setKeyboard(keyboard);
        message.setReplyMarkup(inlineKeyboardMarkup);
        try {
            Message sentMessage = execute(message);
            messageIds.add(sentMessage.getMessageId());
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
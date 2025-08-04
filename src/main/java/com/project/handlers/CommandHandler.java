package com.project.handlers;

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
        String responseText = "Мої вітання, @" + username + ".";
        _messageService.sendMessageWithMainKeyboard(chatId, responseText, _keyboardService.getMainKeyboardMarkup());
    }

    public void handleUnknownMessage(long chatId) {
        String responseText = "Вибачте, але я не розпізнав вашого запиту! Спробуйте будь ласка ще раз.";
        _messageService.sendMessage(chatId, responseText);
    }

    public void handleCategoryCommand(long chatId, String category) {
        String responseText = "Ви відкрили категорію " + category;
        _messageService.sendMessage(chatId, responseText);
    }
}
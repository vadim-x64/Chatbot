package com.project;

import com.project.initializer.BotInitializer;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Main {
    public static void main(String[] args) {
        try {
            BotInitializer botInitializer = new BotInitializer();
            botInitializer.initializeBot();
        } catch (TelegramApiException ignored) {}
    }
}
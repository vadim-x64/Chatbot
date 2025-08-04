package com.project.initializer;

import com.project.TelegramBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class BotInitializer {
    public void initializeBot() throws TelegramApiException {
        TelegramBotsApi telegramBotsApi =new TelegramBotsApi(DefaultBotSession.class);
        TelegramBot telegramBot = new TelegramBot();
        telegramBotsApi.registerBot(telegramBot);
    }
}
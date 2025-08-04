package com.project.configuration;

import io.github.cdimascio.dotenv.Dotenv;

public class BotConfig {

    private final String _botUsername;
    private final String _botToken;

    public BotConfig() {
        Dotenv dotenv = Dotenv.configure().load();
        _botUsername = dotenv.get("BOT_USERNAME");
        _botToken = dotenv.get("BOT_TOKEN");
    }

    public String getBotUsername() {
        return _botUsername;
    }

    public String getBotToken() {
        return _botToken;
    }
}
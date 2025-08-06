package com.project.configuration;

public class BotConfig {

    private final String _botUsername;
    private final String _botToken;

    public BotConfig() {
        _botUsername = System.getenv("BOT_USERNAME");
        _botToken = System.getenv("BOT_TOKEN");
    }

    public String getBotUsername() {
        return _botUsername;
    }

    public String getBotToken() {
        return _botToken;
    }
}
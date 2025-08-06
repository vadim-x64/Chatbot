package com.project.configuration;

import io.github.cdimascio.dotenv.Dotenv;

public class BotConfig {

    private final String _botUsername;
    private final String _botToken;

    public BotConfig() {
        Dotenv dotenv = null;

        try {
            dotenv = Dotenv.configure().ignoreIfMissing().load();
        } catch (Exception ignored) {
        }

        _botUsername = System.getenv("BOT_USERNAME") != null ?
                System.getenv("BOT_USERNAME") :
                (dotenv != null ? dotenv.get("BOT_USERNAME") : null);

        _botToken = System.getenv("BOT_TOKEN") != null ?
                System.getenv("BOT_TOKEN") :
                (dotenv != null ? dotenv.get("BOT_TOKEN") : null);
    }

    public String getBotUsername() {
        return _botUsername;
    }

    public String getBotToken() {
        return _botToken;
    }
}
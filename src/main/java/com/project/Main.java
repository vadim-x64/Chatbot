package com.project;

import com.project.initializer.BotInitializer;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

public class Main {
    public static void main(String[] args) throws IOException {
        int port = Integer.parseInt(System.getenv().getOrDefault("PORT", "8080"));
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

        server.createContext("/", exchange -> {
            try {
                InputStream is = Main.class.getResourceAsStream("/resources/main/index.html");
                if (is == null) {
                    String fallback = "<html><body><h1>Бот працює!</h1><a href='https://t.me/vodiydrivingbot'>Перейти</a></body></html>";
                    exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
                    exchange.sendResponseHeaders(200, fallback.getBytes(StandardCharsets.UTF_8).length);
                    exchange.getResponseBody().write(fallback.getBytes(StandardCharsets.UTF_8));
                } else {
                    byte[] htmlBytes = is.readAllBytes();
                    exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
                    exchange.sendResponseHeaders(200, htmlBytes.length);
                    exchange.getResponseBody().write(htmlBytes);
                }
            } catch (Exception e) {
                String error = "Помилка сервера";
                exchange.sendResponseHeaders(500, error.length());
                exchange.getResponseBody().write(error.getBytes());
            } finally {
                exchange.close();
            }
        });

        server.setExecutor(null);
        server.start();
        // System.out.println("HTTP server started on port " + port);

        try {
            BotInitializer botInitializer = new BotInitializer();
            botInitializer.initializeBot();
        } catch (TelegramApiException ignored) {
        }
    }
}
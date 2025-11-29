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
                InputStream is = Main.class.getResourceAsStream("/main/index.html");
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

        server.createContext("/index.css", exchange -> {
            try {
                InputStream is = Main.class.getClassLoader().getResourceAsStream("main/index.css");
                if (is != null) {
                    byte[] cssBytes = is.readAllBytes();
                    exchange.getResponseHeaders().set("Content-Type", "text/css; charset=UTF-8");
                    exchange.sendResponseHeaders(200, cssBytes.length);
                    exchange.getResponseBody().write(cssBytes);
                    is.close();
                } else {
                    exchange.sendResponseHeaders(404, 0);
                }
            } catch (Exception e) {
                exchange.sendResponseHeaders(500, 0);
            } finally {
                exchange.close();
            }
        });

        server.createContext("/assets/icon.png", exchange -> {
            try {
                InputStream is = Main.class.getClassLoader().getResourceAsStream("assets/icon.png");
                if (is != null) {
                    byte[] imageBytes = is.readAllBytes();
                    exchange.getResponseHeaders().set("Content-Type", "image/png");
                    exchange.sendResponseHeaders(200, imageBytes.length);
                    exchange.getResponseBody().write(imageBytes);
                    is.close();
                } else {
                    exchange.sendResponseHeaders(404, 0);
                }
            } catch (Exception e) {
                exchange.sendResponseHeaders(500, 0);
            } finally {
                exchange.close();
            }
        });

        server.setExecutor(null);
        server.start();

        try {
            BotInitializer botInitializer = new BotInitializer();
            botInitializer.initializeBot();
        } catch (TelegramApiException ignored) {
        }
    }
}
package com.project;

import com.project.initializer.BotInitializer;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;

public class Main {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/", exchange -> {
            String response = "Bot is running";
            exchange.sendResponseHeaders(200, response.length());
            exchange.getResponseBody().write(response.getBytes());
            exchange.close();
        });
        server.setExecutor(null);
        server.start();
        //System.out.println("HTTP server started on port 8080");
        try {
            BotInitializer botInitializer = new BotInitializer();
            botInitializer.initializeBot();
        } catch (TelegramApiException ignored) {
        }
    }
}
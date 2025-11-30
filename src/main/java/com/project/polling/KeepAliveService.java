package com.project.polling;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class KeepAliveService {
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private final String serverUrl;

    public KeepAliveService(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public void start() {
        scheduler.scheduleAtFixedRate(this::ping, 0, 0, TimeUnit.MINUTES);
        System.out.println("KeepAlive service started");
    }

    private void ping() {
        try {
            URL url = new URL(serverUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(0);
            connection.setReadTimeout(0);

            int responseCode = connection.getResponseCode();
            System.out.println("Keep-alive ping: " + responseCode);
            connection.disconnect();
        } catch (Exception e) {
            System.err.println("Keep-alive ping failed: " + e.getMessage());
        }
    }

    public void stop() {
        scheduler.shutdown();
    }
}
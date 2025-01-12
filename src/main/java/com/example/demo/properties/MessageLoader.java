package com.example.demo.properties;

import java.util.Map;
import java.io.IOException;
import java.io.InputStream;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MessageLoader {
    private static Map<String, Object> messages;
    String categoriesInfo = MessageLoader.getMessage("categories_info");
    String marksInfo = MessageLoader.getMessage("marks_info");
    String principleInfo = MessageLoader.getMessage("principle_info");
    String structureInfo1 = MessageLoader.getMessage("structure_info1");
    String structureInfo2 = MessageLoader.getMessage("structure_info2");
    String structureInfo3 = MessageLoader.getMessage("structure_info3");
    String telegramInfo1 = MessageLoader.getMessage("telegram_info1");
    String telegramInfo2 = MessageLoader.getMessage("telegram_info2");
    String telegramInfo3 = MessageLoader.getMessage("telegram_info3");
    String telegramInfo4 = MessageLoader.getMessage("telegram_info4");

    static {
        try (InputStream inputStream = MessageLoader.class.getResourceAsStream("/messages.json")) {
            ObjectMapper mapper = new ObjectMapper();
            messages = mapper.readValue(inputStream, Map.class);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load.", e);
        }
    }

    public static String getMessage(String key) {
        return (String) messages.get(key);
    }

    @SuppressWarnings("unchecked")
    public static String getNestedMessage(String... keys) {
        Map<String, Object> current = messages;
        for (int i = 0; i < keys.length - 1; i++) {
            current = (Map<String, Object>) current.get(keys[i]);
        }
        return (String) current.get(keys[keys.length - 1]);
    }
}
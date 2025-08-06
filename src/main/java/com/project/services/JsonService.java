package com.project.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class JsonService {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final Map<String, JsonNode> cache = new HashMap<>();

    public static String getText(String fileName, String key) {
        try {
            JsonNode jsonNode = getJsonNode(fileName);
            return jsonNode.get(key).asText();
        } catch (Exception ignored) {
        }
        return null;
    }

    public static String getTextWithPlaceholder(String fileName, String key, String placeholder, String value) {
        return Objects.requireNonNull(getText(fileName, key)).replace("{" + placeholder + "}", value);
    }

    private static JsonNode getJsonNode(String fileName) throws Exception {
        if (!cache.containsKey(fileName)) {
            String path = "/json/" + fileName + "/" + fileName + ".json";
            InputStream inputStream = JsonService.class.getResourceAsStream(path);
            cache.put(fileName, objectMapper.readTree(inputStream));
        }
        return cache.get(fileName);
    }
}
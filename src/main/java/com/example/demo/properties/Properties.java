package com.example.demo.properties;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import java.util.Locale;

@Data
@Setter
@Getter
@Configuration
@PropertySource("/application.properties")

public class Properties {
    private Locale language;

    @Value("${bot.name}")
    String botName;

    @Value("${bot.token}")
    String botToken;
}
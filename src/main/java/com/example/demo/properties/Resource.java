package com.example.demo.properties;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class Resource implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry reg) {
        reg.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }
}
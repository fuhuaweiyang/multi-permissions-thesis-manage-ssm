package com.rabbiter.ol.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost:9252") // 允许前端源
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 必须包含 OPTIONS
                .allowedHeaders("*")
                .allowCredentials(true); // 如果需要凭证
    }
}
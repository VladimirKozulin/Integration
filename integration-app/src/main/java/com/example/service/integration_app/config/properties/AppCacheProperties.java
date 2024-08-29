package com.example.service.integration_app.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@ConfigurationProperties(prefix = "app.cache")
public class AppCacheProperties {

    private final List<String> cacheNames = new ArrayList<>(); //Хранение кешей

    private final Map<String, CacheProperties> cache = new HashMap<>(); // Хранит дополнительные настройки кеша

    private CacheType cacheType;

    @Data
    public static class CacheProperties {
        private Duration expiry = Duration.ZERO;
    }

    public interface cacheNames {
        String DATABASE_ENTITIES = "databaseEntities";

        String DATABASE_ENTITIES_BY_NAME = "databaseEntitiesByName";
    }

    public enum CacheType {
        IN_MEMORY,
    }
}

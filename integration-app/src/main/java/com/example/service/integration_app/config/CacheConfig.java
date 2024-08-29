package com.example.service.integration_app.config;

import com.example.service.integration_app.config.properties.AppCacheProperties;
import com.google.common.cache.CacheBuilder;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
@EnableConfigurationProperties(AppCacheProperties.class)
public class CacheConfig {

    @Bean
    @ConditionalOnExpression("${app.cache.cacheType}.equals('inMemory)")
    public ConcurrentMapCacheManager inMemoryCacheManager(AppCacheProperties appCacheProperties) {
        var cacheManager = new ConcurrentMapCacheManager(){
        @Override
        protected Cache createConcurrentMapCache(String name) {
            return new ConcurrentMapCache(name,
                    CacheBuilder.newBuilder()
                            .expireAfterWrite(appCacheProperties.getCache().get(name).getExpiry())
                            .build()
                            .asMap(), true
            );}
        };

        var cachesNames = appCacheProperties.getCacheNames();

        if(!cachesNames.isEmpty()){
            cacheManager.setCacheNames(cachesNames);
        }
        return cacheManager;
    }
}

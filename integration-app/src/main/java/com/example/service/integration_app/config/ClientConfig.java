package com.example.service.integration_app.config;

import okhttp3.OkHttpClient;
import okhttp3.WebSocket;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ClientConfig {

    @Bean
    public OkHttpClient okHttpClient() {
        return new OkHttpClient();
    }
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
    @Bean
    public WebClient webClient(@Value("${app.integration_app.url}") String baseurl){
return WebClient.builder()
        .baseUrl(baseurl)
        .build();
    }
}

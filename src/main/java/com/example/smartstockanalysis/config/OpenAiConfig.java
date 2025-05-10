package com.example.smartstockanalysis.config;

import org.springframework.ai.model.SimpleApiKey;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.util.LinkedMultiValueMap;

@Configuration
public class OpenAiConfig {

    @Value("${openai.api-key}")
    private String apiKey;


    @Bean
    public OpenAiApi openAiApi() {
        return OpenAiApi.builder()
                .baseUrl("https://api.openai.com")
                .apiKey(new SimpleApiKey(apiKey))
                .headers(new LinkedMultiValueMap<>()) // opzionale
                .restClientBuilder(RestClient.builder())
                .webClientBuilder(WebClient.builder())
                .build();
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
}

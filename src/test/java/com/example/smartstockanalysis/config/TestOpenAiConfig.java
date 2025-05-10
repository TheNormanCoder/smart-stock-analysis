package com.example.smartstockanalysis.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@TestConfiguration
public class TestOpenAiConfig {

    @Bean
    public OpenAiConfig openAiConfig() {
        OpenAiConfig mockConfig = mock(OpenAiConfig.class);
        when(mockConfig.getApiKey()).thenReturn("dummy-api-key");
        return mockConfig;
    }
}

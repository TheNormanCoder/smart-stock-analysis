package com.example.smartstockanalysis.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@TestConfiguration
public class TestAlphaVantageConfig {

    @Bean
    public AlphaVantageConfig alphaVantageConfig() {
        AlphaVantageConfig mockConfig = mock(AlphaVantageConfig.class);
        when(mockConfig.getApiKey()).thenReturn("dummy-api-key");
        return mockConfig;
    }
}

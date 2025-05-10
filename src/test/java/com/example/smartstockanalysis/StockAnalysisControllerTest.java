package com.example.smartstockanalysis;


import com.example.smartstockanalysis.config.TestAlphaVantageConfig;
import com.example.smartstockanalysis.config.TestOpenAiConfig;
import com.example.smartstockanalysis.service.SpringAiService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest(classes = {SmartStockAnalysisApplication.class, TestOpenAiConfig.class, TestAlphaVantageConfig.class},
properties = "spring.profiles.active=test"
)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
@ExtendWith(MockitoExtension.class)
class StockAnalysisControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SpringAiService springAiService; //

    @Test
    void testPredictAlphaVantageWithMockedAI() throws Exception {
        // Definiamo il comportamento mockato
        when(springAiService.analyzeStock(anyString(), anyList(), anyDouble()))
                .thenReturn("Mocked analysis response.");

        mockMvc.perform(get("/predict/alphavantage")
                        .param("ticker", "AAPL"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.analysis").value("Mocked analysis response."));
    }
}

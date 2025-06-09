package com.example.smartstockanalysis;


import com.example.smartstockanalysis.config.TestAlphaVantageConfig;
import com.example.smartstockanalysis.config.TestOpenAiConfig;
import com.example.smartstockanalysis.service.SpringAiService;
import com.example.smartstockanalysis.service.AlphaVantageService;
import com.example.smartstockanalysis.model.StockData;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
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

    @MockitoBean
    private AlphaVantageService alphaVantageService;

    @Test
    void testPredictAlphaVantageWithMockedAI() throws Exception {
        // Mock SpringAiService
        when(springAiService.analyzeStock(anyString(), anyList(), anyDouble()))
                .thenReturn("Mocked analysis response.");

        // Prepare sample stock data
        List<StockData> sampleData = Arrays.asList(
                new StockData(new Date(), 0, 0, 0, 10.0, 0),
                new StockData(new Date(), 0, 0, 0, 20.0, 0),
                new StockData(new Date(), 0, 0, 0, 30.0, 0)
        );
        List<Double> normalized = Arrays.asList(0.0, 0.5, 1.0);

        // Mock AlphaVantageService so no real HTTP call is made
        when(alphaVantageService.getStockData(anyString())).thenReturn(sampleData);
        when(alphaVantageService.preprocessStockData(anyList())).thenReturn(normalized);

        mockMvc.perform(get("/predict/alphavantage")
                        .param("ticker", "AAPL"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.analysis").value("Mocked analysis response."));
    }
}

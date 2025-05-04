package com.example.smartstockanalysis.service;

import com.example.smartstockanalysis.model.StockData;
import com.example.smartstockanalysis.utils.AlphaVantageParser;
import com.example.smartstockanalysis.utils.DataPreprocessingUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class AlphaVantageService {

    @Value("${alphavantage.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();
    private final AlphaVantageParser parser;

    public AlphaVantageService(AlphaVantageParser parser) {
        this.parser = parser;
    }

    public List<StockData> getStockData(String symbol) {
        String url = "https://www.alphavantage.co/query"
                + "?function=TIME_SERIES_DAILY"
                + "&symbol=" + symbol
                + "&apikey=" + apiKey;

        try {
            String response = restTemplate.getForObject(url, String.class);
            return parser.parse(response);
        } catch (Exception e) {
            throw new RuntimeException("Errore nel recupero dati da Alpha Vantage", e);
        }
    }

    public List<Double> preprocessStockData(List<StockData> stockData) {
        return DataPreprocessingUtils.extractNormalizedClosingPrices(stockData);
    }
}

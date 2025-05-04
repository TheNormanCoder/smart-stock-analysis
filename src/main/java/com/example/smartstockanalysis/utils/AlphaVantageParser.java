package com.example.smartstockanalysis.utils;

import com.example.smartstockanalysis.model.StockData;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

@Component
public class AlphaVantageParser {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<StockData> parse(String jsonResponse) throws IOException {
        JsonNode root = objectMapper.readTree(jsonResponse);
        JsonNode timeSeries = root.get("Time Series (Daily)");

        if (timeSeries == null) {
            throw new RuntimeException("Formato JSON non valido o dati mancanti.");
        }

        List<StockData> stockDataList = new ArrayList<>();

        for (Iterator<Map.Entry<String, JsonNode>> it = timeSeries.fields(); it.hasNext(); ) {
            Map.Entry<String, JsonNode> entry = it.next();
            String dateStr = entry.getKey();
            JsonNode values = entry.getValue();

            Date date = java.sql.Date.valueOf(dateStr);
            double open = values.get("1. open").asDouble();
            double high = values.get("2. high").asDouble();
            double low = values.get("3. low").asDouble();
            double close = values.get("4. close").asDouble();
            long volume = values.get("5. volume").asLong();

            stockDataList.add(new StockData(date, open, high, low, close, volume));
        }

        return stockDataList;
    }
}

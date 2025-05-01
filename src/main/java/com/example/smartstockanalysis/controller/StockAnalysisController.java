package com.example.smartstockanalysis.controller;

import com.example.smartstockanalysis.model.PredictionResult;
import com.example.smartstockanalysis.model.StockData;
import com.example.smartstockanalysis.service.PredictionService;
import com.example.smartstockanalysis.service.YahooFinanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class StockAnalysisController {

    @Autowired
    private YahooFinanceService yahooFinanceService;

    @Autowired
    private PredictionService predictionService;


    @GetMapping("/predict")
    public PredictionResult predict(@RequestParam String ticker) {
        List<StockData> stockData = yahooFinanceService.getStockData(ticker);
        List<Double> normalized = yahooFinanceService.preprocessStockData(stockData);

        double prediction = predictionService.predictNextValue(normalized);

        return new PredictionResult(ticker, prediction, normalized.size());
    }
}

package com.example.smartstockanalysis.controller;

import com.example.smartstockanalysis.model.PredictionResult;
import com.example.smartstockanalysis.model.StockData;
import com.example.smartstockanalysis.service.AlphaVantageService;
import com.example.smartstockanalysis.service.prediction.AdvancedPredictionService;
import com.example.smartstockanalysis.service.prediction.PredictionService;
import com.example.smartstockanalysis.service.SpringAiService;
import com.example.smartstockanalysis.service.YahooFinanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StockAnalysisController {

    @Autowired
    private YahooFinanceService yahooFinanceService;

    @Autowired
    private AlphaVantageService alphaVantageService;

    @Autowired
    private PredictionService predictionService;

    @Autowired
    private AdvancedPredictionService advancedPredictionService;


    @Autowired
    private SpringAiService springAiService;


    @GetMapping("/predict/yahoofinace")
    public PredictionResult predictYahooFinace(@RequestParam String ticker) {
        List<StockData> stockData = yahooFinanceService.getStockData(ticker);
        List<Double> normalized = yahooFinanceService.preprocessStockData(stockData);

        double prediction = predictionService.predictNextValue(normalized);

        String analysis = springAiService.analyzeStock(ticker, normalized, prediction);

        return new PredictionResult(ticker, prediction, normalized.size(), analysis);
    }
    @GetMapping("/predict/alphavantage")
    public PredictionResult predictAlphaVantage(@RequestParam String ticker) {
        List<StockData> stockData = alphaVantageService.getStockData(ticker);
        List<Double> normalized = alphaVantageService.preprocessStockData(stockData);

        double prediction = predictionService.predictNextValue(normalized);

        String analysis = springAiService.analyzeStock(ticker, normalized, prediction);

        return new PredictionResult(ticker, prediction, normalized.size(), analysis);
    }
    @GetMapping("/predict/alphavantage/advanced")
    public PredictionResult predictAlphaVantageAdvanced(@RequestParam String ticker) {
        List<StockData> stockData = alphaVantageService.getStockData(ticker);
        List<Double> normalized = alphaVantageService.preprocessStockData(stockData);

        double prediction = advancedPredictionService.predictAndRetrain(ticker, normalized);

        String analysis = springAiService.analyzeStock(ticker, normalized, prediction);
        return new PredictionResult(ticker, prediction, normalized.size(), analysis);
    }
}

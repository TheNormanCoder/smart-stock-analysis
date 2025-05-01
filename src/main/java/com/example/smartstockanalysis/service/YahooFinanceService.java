package com.example.smartstockanalysis.service;

import com.example.smartstockanalysis.model.StockData;
import com.example.smartstockanalysis.utils.DataPreprocessingUtils;
import org.springframework.stereotype.Service;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
public class YahooFinanceService {

    public List<StockData> getStockData(String ticker) {
        try {
            Calendar from = Calendar.getInstance();
            Calendar to = Calendar.getInstance();
            from.add(Calendar.YEAR, -1);

            Stock stock = YahooFinance.get(ticker);
            List<HistoricalQuote> history = stock.getHistory(from, to, Interval.DAILY);

            List<StockData> stockDataList = new ArrayList<>();
            for (HistoricalQuote quote : history) {
                if (quote.getClose() != null && quote.getDate() != null) {
                    stockDataList.add(new StockData(
                            quote.getDate().getTime(),
                            quote.getOpen().doubleValue(),
                            quote.getHigh().doubleValue(),
                            quote.getLow().doubleValue(),
                            quote.getClose().doubleValue(),
                            quote.getVolume()
                    ));
                }
            }

            return stockDataList;

        } catch (IOException e) {
            throw new RuntimeException("Errore durante il download dei dati da Yahoo Finance", e);
        }
    }

    public List<Double> preprocessStockData(List<StockData> stockData) {
        return DataPreprocessingUtils.extractNormalizedClosingPrices(stockData);
    }
}
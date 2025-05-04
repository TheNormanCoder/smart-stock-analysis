package com.example.smartstockanalysis.service;

import com.example.smartstockanalysis.model.StockData;
import com.example.smartstockanalysis.utils.DataPreprocessingUtils;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${stock.default-ticker:AAPL}")
    private String defaultTicker;

    @Value("${stock.days:365}")
    private int days;

    public List<StockData> getStockData(String ticker) {
        Calendar from = Calendar.getInstance();
        Calendar to = Calendar.getInstance();
        from.add(Calendar.DAY_OF_YEAR, -days);

        try {
            return fetchStockHistory(ticker, from, to);
        } catch (IOException e) {
            // Se viene superato il rate limit, attende e riprova una volta
            if (e.getMessage().contains("429")) {
                try {
                    Thread.sleep(2000); // attende 2 secondi
                    return fetchStockHistory(ticker, from, to); // riprova una volta
                } catch (InterruptedException | IOException retryException) {
                    throw new RuntimeException("Errore dopo il retry: " + retryException.getMessage(), retryException);
                }
            }
            throw new RuntimeException("Errore durante il download dei dati da Yahoo Finance", e);
        }
    }

    private List<StockData> fetchStockHistory(String ticker, Calendar from, Calendar to) throws IOException {
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
    }


    public List<StockData> getDefaultStockData() {
        return getStockData(defaultTicker);
    }

    public List<Double> preprocessStockData(List<StockData> stockData) {
        return DataPreprocessingUtils.extractNormalizedClosingPrices(stockData);
    }
}
package com.example.smartstockanalysis.utils;

import com.example.smartstockanalysis.model.StockData;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class DataPreprocessingUtils {

    public static List<Double> extractNormalizedClosingPrices(List<StockData> data) {
        if (data == null || data.isEmpty()) {
            return List.of();
        }

        // Ordina per data crescente (opzionale ma utile per finestre temporali)
        data.sort(Comparator.comparing(StockData::getDate));

        List<Double> closingPrices = new ArrayList<>();
        for (StockData d : data) {
            closingPrices.add(d.getClose());
        }

        double min = closingPrices.stream().min(Double::compareTo).orElse(0.0);
        double max = closingPrices.stream().max(Double::compareTo).orElse(1.0);

        List<Double> normalized = new ArrayList<>();

        // Se tutti i valori sono uguali, restituisce una lista di zeri
        if (Double.compare(max, min) == 0) {
            for (int i = 0; i < closingPrices.size(); i++) {
                normalized.add(0.0);
            }
            return normalized;
        }
        for (double value : closingPrices) {
            double norm = (value - min) / (max - min);
            normalized.add(norm);
        }

        return normalized;
    }
}

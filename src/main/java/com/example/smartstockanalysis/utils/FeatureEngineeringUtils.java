package com.example.smartstockanalysis.utils;

import java.util.ArrayList;
import java.util.List;

public class FeatureEngineeringUtils {

    /**
     * Calcola la Simple Moving Average (SMA).
     * @param prices Lista dei prezzi di chiusura.
     * @param period Numero di giorni della media mobile.
     * @return Lista dei valori SMA.
     */
    public static List<Double> calculateSMA(List<Double> prices, int period) {
        List<Double> sma = new ArrayList<>();
        for (int i = 0; i <= prices.size() - period; i++) {
            double sum = 0.0;
            for (int j = i; j < i + period; j++) {
                sum += prices.get(j);
            }
            sma.add(sum / period);
        }
        return sma;
    }

    /**
     * Calcola il Relative Strength Index (RSI).
     * @param prices Lista dei prezzi di chiusura.
     * @param period Periodo per il calcolo dell'RSI (tipicamente 14).
     * @return Lista dei valori RSI.
     */
    public static List<Double> calculateRSI(List<Double> prices, int period) {
        List<Double> rsi = new ArrayList<>();
        for (int i = period; i < prices.size(); i++) {
            double gain = 0.0;
            double loss = 0.0;
            for (int j = i - period + 1; j <= i; j++) {
                double change = prices.get(j) - prices.get(j - 1);
                if (change > 0) {
                    gain += change;
                } else {
                    loss -= change;
                }
            }
            double avgGain = gain / period;
            double avgLoss = loss / period;
            double rs = avgLoss == 0 ? 100 : avgGain / avgLoss;
            double rsiValue = 100 - (100 / (1 + rs));
            rsi.add(rsiValue);
        }
        return rsi;
    }

    /**
     * Combina prezzi normalizzati e feature calcolate in un unico vettore di feature.
     * @param prices Lista dei prezzi di chiusura.
     * @return Lista completa delle feature.
     */
    public static List<Double> generateFeatureVector(List<Double> prices) {
        List<Double> features = new ArrayList<>(prices);

        // Aggiungi SMA con periodi diversi
        features.addAll(calculateSMA(prices, 5));
        features.addAll(calculateSMA(prices, 10));

        // Aggiungi RSI
        features.addAll(calculateRSI(prices, 14));

        return features;
    }
}

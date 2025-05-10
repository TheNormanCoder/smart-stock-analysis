package com.example.smartstockanalysis.model;

public class PredictionResult {

    private String ticker;
    private double prediction;
    private int dataPoints;
    private String analysis; // 🆕 Nuovo campo per il testo AI

    public PredictionResult(String ticker, double prediction, int dataPoints, String analysis) {
        this.ticker = ticker;
        this.prediction = prediction;
        this.dataPoints = dataPoints;
        this.analysis = analysis;
    }

    public String getTicker() {
        return ticker;
    }

    public double getPrediction() {
        return prediction;
    }

    public int getDataPoints() {
        return dataPoints;
    }

    public String getAnalysis() {
        return analysis;
    }
}

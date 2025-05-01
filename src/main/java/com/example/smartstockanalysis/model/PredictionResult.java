package com.example.smartstockanalysis.model;

public class PredictionResult {

    private String ticker;
    private double prediction;
    private int dataPoints;

    public PredictionResult(String ticker, double prediction, int dataPoints) {
        this.ticker = ticker;
        this.prediction = prediction;
        this.dataPoints = dataPoints;
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
}

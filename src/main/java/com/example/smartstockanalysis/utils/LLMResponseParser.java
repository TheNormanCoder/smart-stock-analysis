package com.example.smartstockanalysis.utils;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LLMResponseParser {

    public static class LLMResponse {
        @JsonProperty("trend")
        private String trend;

        @JsonProperty("buy_signal")
        private boolean buySignal;

        @JsonProperty("confidence_score")
        private double confidenceScore;


        public LLMResponse() {}


        public LLMResponse(String trend, boolean buySignal, double confidenceScore) {
            this.trend = trend;
            this.buySignal = buySignal;
            this.confidenceScore = confidenceScore;
        }


        public String getTrend() {
            return trend;
        }

        public void setTrend(String trend) {
            this.trend = trend;
        }

        public boolean isBuySignal() {
            return buySignal;
        }

        public void setBuySignal(boolean buySignal) {
            this.buySignal = buySignal;
        }

        public double getConfidenceScore() {
            return confidenceScore;
        }

        public void setConfidenceScore(double confidenceScore) {
            this.confidenceScore = confidenceScore;
        }

        @Override
        public String toString() {
            return "LLMResponse{" +
                    "trend='" + trend + '\'' +
                    ", buySignal=" + buySignal +
                    ", confidenceScore=" + confidenceScore +
                    '}';
        }
    }
}

package com.example.smartstockanalysis.model;

public class TrainingSample {
    public float[] features;
    public float target;

    public TrainingSample(float[] features, float target) {
        this.features = features;
        this.target = target;
    }
}

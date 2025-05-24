package com.example.smartstockanalysis.service.training;

import com.example.smartstockanalysis.model.TrainingSample;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class DatasetLoaderUtils {


    public static List<TrainingSample> loadFromCsv(String datasetPath) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(datasetPath));
            List<TrainingSample> samples = new ArrayList<>();

            for (String line : lines) {
                String[] parts = line.split(",");
                int featureSize = parts.length - 3;

                float[] features = new float[featureSize];
                for (int i = 0; i < featureSize; i++) {
                    features[i] = Float.parseFloat(parts[i]);
                }

                float target = Float.parseFloat(parts[parts.length - 1]); // Confidence score
                samples.add(new TrainingSample(features, target));
            }

            return samples;
        } catch (Exception e) {
            throw new RuntimeException("Errore durante il caricamento del CSV: " + e.getMessage(), e);
        }
    }
}

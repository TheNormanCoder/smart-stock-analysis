package com.example.smartstockanalysis.utils;

import com.example.smartstockanalysis.model.TrainingSample;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class DatasetUtils {

    private static final String DATASET_PATH = "datasets/training_data.csv";

    public static void appendTrainingSample(List<Double> features, LLMResponseParser.LLMResponse label) {
        try {
            // Crea la directory se non esiste
            File datasetDir = new File("datasets");
            if (!datasetDir.exists()) {
                datasetDir.mkdirs();
            }

            FileWriter writer = new FileWriter("datasets/training_data.csv", true);
            StringBuilder line = new StringBuilder();

            for (Double feature : features) {
                line.append(feature).append(",");
            }

            line.append(label.getTrend()).append(",");
            line.append(label.isBuySignal()).append(",");
            line.append(label.getConfidenceScore()).append("\n");

            writer.write(line.toString());
            writer.close();

        } catch (IOException e) {
            throw new RuntimeException("Errore scrittura CSV dataset: " + e.getMessage(), e);
        }
    }

}

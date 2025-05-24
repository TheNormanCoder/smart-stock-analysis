package com.example.smartstockanalysis.service;

import ai.djl.Model;
import ai.djl.ndarray.NDManager;
import ai.djl.training.dataset.ArrayDataset;
import com.example.smartstockanalysis.model.TrainingSample;
import com.example.smartstockanalysis.service.training.DataConversionUtils;
import com.example.smartstockanalysis.service.training.DatasetLoaderUtils;
import com.example.smartstockanalysis.service.training.ModelConfigFactory;
import com.example.smartstockanalysis.service.training.ModelTrainer;
import org.springframework.stereotype.Service;

import java.nio.file.Paths;
import java.util.List;

@Service
public class ModelTrainingService {

    public void trainAndSaveModel(String datasetPath, String modelPath) {
        List<TrainingSample> samples = DatasetLoaderUtils.loadFromCsv(datasetPath);

        try (NDManager manager = NDManager.newBaseManager()) {
            ArrayDataset dataset = DataConversionUtils.toArrayDataset(samples, manager);

            try (Model model = ModelConfigFactory.createMLPModel(samples.get(0).features.length)) {
                ModelTrainer.train(model, dataset);
                model.save(Paths.get(modelPath), "mlp-trained");
            }
        } catch (Exception e) {
            throw new RuntimeException("Errore durante l'addestramento del modello: " + e.getMessage(), e);
        }
    }
}

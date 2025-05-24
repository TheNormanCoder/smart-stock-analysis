package com.example.smartstockanalysis.service.training;

import ai.djl.ndarray.NDArray;
import ai.djl.ndarray.NDManager;
import ai.djl.training.dataset.ArrayDataset;
import com.example.smartstockanalysis.model.TrainingSample;

import java.util.List;



public class DataConversionUtils {

    public static ArrayDataset toArrayDataset(List<TrainingSample> samples, NDManager manager) {
        int featureSize = samples.get(0).features.length;
        float[][] featureMatrix = new float[samples.size()][featureSize];
        float[] targets = new float[samples.size()];

        for (int i = 0; i < samples.size(); i++) {
            featureMatrix[i] = samples.get(i).features;
            targets[i] = samples.get(i).target;
        }

        NDArray X = manager.create(featureMatrix);
        NDArray y = manager.create(targets);

        return new ArrayDataset.Builder()
                .setData(X)
                .optLabels(y)
                .setSampling(16, true)
                .build();
    }
}

package com.example.smartstockanalysis.service.prediction;

import ai.djl.Model;
import ai.djl.basicmodelzoo.basic.Mlp;
import ai.djl.inference.Predictor;
import ai.djl.ndarray.NDArray;
import ai.djl.ndarray.NDList;
import ai.djl.ndarray.NDManager;
import ai.djl.ndarray.types.Shape;
import ai.djl.translate.Batchifier;
import ai.djl.translate.Translator;
import ai.djl.translate.TranslatorContext;
import com.example.smartstockanalysis.utils.FeatureEngineeringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdvancedPredictionService {

    public double predictNextValueWithFeatures(List<Double> input) {
        // 🧩 Applichiamo Feature Engineering avanzato
        List<Double> enrichedFeatures = FeatureEngineeringUtils.generateFeatureVector(input);

        try (Model model = Model.newInstance("mlp-advanced")) {
            model.setBlock(new Mlp(enrichedFeatures.size(), 1, new int[]{64, 32}));

            try (NDManager manager = NDManager.newBaseManager()) {
                float[] inputArray = new float[enrichedFeatures.size()];
                for (int i = 0; i < enrichedFeatures.size(); i++) {
                    inputArray[i] = enrichedFeatures.get(i).floatValue();
                }
                NDArray inputND = manager.create(inputArray).reshape(new Shape(1, enrichedFeatures.size()));

                model.getBlock().initialize(manager, ai.djl.ndarray.types.DataType.FLOAT32, new Shape(1, enrichedFeatures.size()));

                Translator<NDList, NDList> translator = new Translator<>() {
                    @Override
                    public NDList processInput(TranslatorContext ctx, NDList input) {
                        return input;
                    }

                    @Override
                    public NDList processOutput(TranslatorContext ctx, NDList output) {
                        return output;
                    }

                    @Override
                    public Batchifier getBatchifier() {
                        return null;
                    }
                };

                try (Predictor<NDList, NDList> predictor = model.newPredictor(translator)) {
                    NDList result = predictor.predict(new NDList(inputND));
                    return result.singletonOrThrow().getFloat(0);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Errore nella predizione avanzata DJL", e);
        }
    }
}

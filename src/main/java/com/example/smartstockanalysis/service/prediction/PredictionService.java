package com.example.smartstockanalysis.service.prediction;

import ai.djl.Model;
import ai.djl.inference.Predictor;
import ai.djl.nn.SequentialBlock;
import ai.djl.nn.core.Linear;
import ai.djl.nn.Activation;
import ai.djl.ndarray.NDArray;
import ai.djl.ndarray.NDList;
import ai.djl.ndarray.NDManager;
import ai.djl.ndarray.types.Shape;
import ai.djl.translate.Batchifier;
import ai.djl.translate.Translator;
import ai.djl.translate.TranslatorContext;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PredictionService {

    public double predictNextValue(List<Double> input) {
        try (Model model = Model.newInstance("mlp", "PyTorch")) {

            SequentialBlock block = new SequentialBlock()
                    .add(Linear.builder().setUnits(64).build())
                    .add(Activation::relu)
                    .add(Linear.builder().setUnits(32).build())
                    .add(Activation::relu)
                    .add(Linear.builder().setUnits(1).build());

            model.setBlock(block);

            try (NDManager manager = NDManager.newBaseManager()) {
                float[] inputArray = new float[input.size()];
                for (int i = 0; i < input.size(); i++) {
                    inputArray[i] = input.get(i).floatValue();
                }
                NDArray inputND = manager.create(inputArray).reshape(new Shape(1, input.size()));

                model.getBlock().initialize(manager, ai.djl.ndarray.types.DataType.FLOAT32, new Shape(1, input.size()));

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
            throw new RuntimeException("Errore nella predizione DJL", e);
        }
    }
}

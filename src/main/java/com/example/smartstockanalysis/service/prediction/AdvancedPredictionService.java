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
import com.example.smartstockanalysis.service.SpringAiService;
import com.example.smartstockanalysis.service.ModelTrainingService;
import com.example.smartstockanalysis.utils.DatasetUtils;
import com.example.smartstockanalysis.utils.FeatureEngineeringUtils;
import com.example.smartstockanalysis.utils.LLMResponseParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class AdvancedPredictionService {

    private static final int FIXED_INPUT_SIZE = 100;

    @Autowired
    private ModelTrainingService modelTrainingService;

    @Autowired
    private SpringAiService springAiService;

    public double predictAndRetrain(String ticker, List<Double> normalizedPrices) {
        List<Double> enriched = FeatureEngineeringUtils.generateFeatureVector(normalizedPrices);
        List<Double> resized = resizeVector(enriched, FIXED_INPUT_SIZE);

        LLMResponseParser.LLMResponse label = springAiService.getLabels(ticker, resized);
        DatasetUtils.appendTrainingSample(resized, label);
        modelTrainingService.trainAndSaveModel("datasets/training_data.csv", "models/mlp-trained");

        return predictNextValueWithFeatures(normalizedPrices);
    }

    public double predictNextValueWithFeatures(List<Double> input) {
        List<Double> enrichedFeatures = FeatureEngineeringUtils.generateFeatureVector(input);
        List<Double> resized = resizeVector(enrichedFeatures, FIXED_INPUT_SIZE);

        try (Model model = Model.newInstance("mlp-trained", "PyTorch")) {

            SequentialBlock block = new SequentialBlock()
                    .add(Linear.builder().setUnits(128).build())
                    .add(Activation::relu)
                    .add(Linear.builder().setUnits(64).build())
                    .add(Activation::relu)
                    .add(Linear.builder().setUnits(1).build());

            model.setBlock(block);

            try (NDManager manager = NDManager.newBaseManager()) {

                model.getBlock().initialize(manager, ai.djl.ndarray.types.DataType.FLOAT32, new Shape(1, FIXED_INPUT_SIZE));
                model.load(Paths.get("models/mlp-trained"));

                float[] inputArray = new float[FIXED_INPUT_SIZE];
                for (int i = 0; i < FIXED_INPUT_SIZE; i++) {
                    inputArray[i] = resized.get(i).floatValue();
                }

                NDArray inputND = manager.create(inputArray).reshape(new Shape(1, FIXED_INPUT_SIZE));

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
            throw new RuntimeException("Errore nella predizione con modello addestrato", e);
        }
    }


    public static List<Double> resizeVector(List<Double> input, int desiredSize) {
        if (input.size() > desiredSize) {
            return input.subList(0, desiredSize);
        } else if (input.size() < desiredSize) {
            List<Double> padded = new ArrayList<>(input);
            while (padded.size() < desiredSize) {
                padded.add(0.0);
            }
            return padded;
        }
        return input;
    }
}

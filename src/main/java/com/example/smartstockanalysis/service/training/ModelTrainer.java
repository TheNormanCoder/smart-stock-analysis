package com.example.smartstockanalysis.service.training;

import ai.djl.Model;
import ai.djl.ndarray.NDArray;
import ai.djl.training.GradientCollector;
import ai.djl.training.Trainer;
import ai.djl.training.dataset.ArrayDataset;
import ai.djl.training.dataset.Batch;
import ai.djl.ndarray.NDManager;
import ai.djl.translate.TranslateException;
import java.io.IOException;

public class ModelTrainer {

    private static final int NUM_EPOCHS = 20;
    private static final int BATCH_SIZE = 16;

    public static void train(Model model, ArrayDataset dataset) {
        try (Trainer trainer = model.newTrainer(ModelConfigFactory.getTrainingConfig())) {

            // Inizializza trainer con la shape del primo batch disponibile
            try (NDManager manager = NDManager.newBaseManager()) {
                Batch firstBatch = trainer.iterateDataset(dataset).iterator().next();
                NDArray firstData = firstBatch.getData().head();
                trainer.initialize(firstData.getShape());
                firstBatch.close();
            } catch (TranslateException | IOException e) {
                throw new RuntimeException("Errore durante l'inizializzazione del trainer", e);
            }

            for (int epoch = 0; epoch < NUM_EPOCHS; epoch++) {
                float epochLoss = 0;
                int batchCount = 0;

                for (Batch batch : trainer.iterateDataset(dataset)) {
                    try (GradientCollector collector = trainer.newGradientCollector()) {
                        NDArray prediction = trainer.forward(batch.getData()).singletonOrThrow();
                        NDArray lossValue = trainer.getLoss().evaluate(
                                batch.getLabels(),
                                new ai.djl.ndarray.NDList(prediction)
                        );
                        epochLoss += lossValue.mean().getFloat(); // Accumula la loss media
                        collector.backward(lossValue);
                    }
                    trainer.step();
                    batch.close();
                    batchCount++;
                }

                float avgLoss = epochLoss / batchCount;
                System.out.printf("Epoch %d - Loss Media: %.5f%n", epoch + 1, avgLoss);
            }

            System.out.println("✅ Training completato correttamente.");

        } catch (TranslateException | IOException e) {
            throw new RuntimeException("Errore durante il training", e);
        }
    }
}
package com.example.smartstockanalysis.service.training;

import ai.djl.Model;
import ai.djl.nn.SequentialBlock;
import ai.djl.nn.core.Linear;
import ai.djl.nn.Activation;
import ai.djl.training.DefaultTrainingConfig;
import ai.djl.training.loss.Loss;
import ai.djl.training.optimizer.Optimizer;
import ai.djl.training.tracker.Tracker;

public class ModelConfigFactory {

    public static Model createMLPModel(int inputSize) {
        Model model = Model.newInstance("mlp-trained", "PyTorch");

        SequentialBlock block = new SequentialBlock()
                .add(Linear.builder().setUnits(64).build())
                .add(Activation::relu)
                .add(Linear.builder().setUnits(32).build())
                .add(Activation::relu)
                .add(Linear.builder().setUnits(1).build());

        model.setBlock(block);
        return model;
    }

    public static DefaultTrainingConfig getTrainingConfig() {
        return new DefaultTrainingConfig(Loss.l2Loss())
                .optOptimizer(Optimizer.sgd()
                        .setLearningRateTracker(Tracker.fixed(0.01f))
                        .build());
    }
}

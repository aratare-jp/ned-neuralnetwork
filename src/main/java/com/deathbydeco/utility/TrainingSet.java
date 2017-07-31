package com.deathbydeco.utility;

import java.util.List;

/**
 * Created by rextruong on 2017/06/01.
 *
 * This class is used to feed into neural networks to train them.
 */
public class TrainingSet {

    private List<Matrix> trainings;
    private Matrix outputs;

    public TrainingSet(List<Matrix> trainings, Matrix outputs) {
        this.trainings = trainings;
        this.outputs = outputs;
    }

    public List<Matrix> getTrainings() {
        return trainings;
    }

    public Matrix getOutputs() {
        return outputs;
    }
}

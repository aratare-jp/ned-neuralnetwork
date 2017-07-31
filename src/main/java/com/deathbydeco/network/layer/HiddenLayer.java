package com.deathbydeco.network.layer;

import com.deathbydeco.network.function.Function;
import com.deathbydeco.utility.Matrix;

/**
 * Created by rex on 2017/04/22.
 * <p>
 * This class is used to represent the hidden layers of the neural network.
 */
public class HiddenLayer extends Layer {
    /**
     * Default constructor for this class.
     *
     * @param numberOfNeurons number of neurons in this layer
     * @param function        activation function of this class
     *
     * @throws IllegalArgumentException if numberOfNeurons is non-positive
     * @throws NullPointerException     if function is null
     * @require numberOfNeurons > 0 && function != null
     * @ensure new instance of this class
     */
    public HiddenLayer(int numberOfNeurons, Function function) {
        super(numberOfNeurons, function);
    }

    /**
     * Calculate the propagation error vector.
     */
    @Override
    public void calculatePropagationErrorVector() {
        Matrix transpose = nextLayer.getWeightMatrix().getTranspose();
        Matrix previousError = nextLayer.calculateAndGetPropagationErrorVector();
        Matrix partOne = Matrix.dotProduct(transpose, previousError);
        Matrix partTwo = getDerivativeOutputVector();
        propagationErrorVector = Matrix.schurProduct(partOne, partTwo);
    }
}

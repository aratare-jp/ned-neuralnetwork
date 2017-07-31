package com.deathbydeco.network.layer;

import com.deathbydeco.network.function.Function;
import com.deathbydeco.network.neuron.FunctionFactory;
import com.deathbydeco.utility.Matrix;

/**
 * Created by rex on 2017/04/22.
 * <p>
 * This class is used to represent the output layer of the neural network.
 */
public class OutputLayer extends Layer {
    /**
     * Expected outputs from this layer.
     */
    private Matrix expectedOutputs;

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
    public OutputLayer(int numberOfNeurons, Function function) {
        super(numberOfNeurons, function);
    }

    /**
     * Set the expected outputs from this layer.
     *
     * @param expectedOutputs the inputs into this layer
     *
     * @throws NullPointerException     if expectedOutputs is null
     * @throws IllegalArgumentException if mismatch between matrix dimension
     *                                  and number of neurons and matrix has
     *                                  more than 1 column
     * @require expectedOutputs != null &&
     * expectedOutputs.getRows() == number of neurons &&
     * expectedOutputs.getColumns() == 1
     * @ensure expected output from this layer will be set
     */
    public void setExpectedOutputs(Matrix expectedOutputs) {
        if (expectedOutputs == null) {
            throw new NullPointerException("Null expected outputs");
        }
        if (expectedOutputs.getRows() != numberOfNeurons ||
                expectedOutputs.getColumns() != 1) {
            throw new IllegalArgumentException("Mismatch between matrix " +
                    "dimension and number of neurons");
        }
        this.expectedOutputs = expectedOutputs;
    }

    /**
     * Overridden method from the base class in order to disable this method.
     * Output layer does not have any layer after it.
     *
     * @param nextLayer the previous layer that is linked to this layer
     */
    @Override
    public void setNextLayer(Layer nextLayer) {
        throw new UnsupportedOperationException("Output layer does not have next layer.");
    }

    /**
     * Calculate the propagation error vector.
     */
    @Override
    public void calculatePropagationErrorVector() {
        Matrix outputs = getOutputVector();
        Matrix partOne = Matrix.subtract(outputs, expectedOutputs);
        Matrix partTwo = getDerivativeOutputVector();
        propagationErrorVector = Matrix.schurProduct(partOne, partTwo);
    }

    /**
     * Return the mean cost of this layer after a single training exercise.
     *
     * @return the mean cost of this layer after a single training exercise
     */
    public double getMeanCost() {
        Matrix outputs = getOutputVector();
        Matrix errorVector = Matrix.subtract(outputs, expectedOutputs);
        errorVector = Matrix.applyFunction(errorVector, FunctionFactory.square());
        return errorVector.sum() / 2;
    }

    public Matrix getCosts() {
        Matrix outputs = getOutputVector();
        return Matrix.subtract(outputs, expectedOutputs);
    }
}

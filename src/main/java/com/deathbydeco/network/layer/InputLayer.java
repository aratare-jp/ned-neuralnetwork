package com.deathbydeco.network.layer;

import com.deathbydeco.network.function.Function;
import com.deathbydeco.network.neuron.InputNeuron;
import com.deathbydeco.utility.Matrix;

/**
 * Created by rex on 2017/04/22.
 * <p>
 * This class is used to represent the input layer of the neural network.
 */
public class InputLayer extends Layer {
    /**
     * Inputs into this layer.
     */
    private Matrix initialInputs;

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
    public InputLayer(int numberOfNeurons, Function function) {
        super(numberOfNeurons, function);
        // Initialise neurons with one input and weight of 1
        for (int i = 0; i < numberOfNeurons; i++) {
            neurons[i] = new InputNeuron();
        }
    }

    /**
     * Set the inputs into this layer.
     *
     * @param initialInputs the inputs into this layer
     *
     * @throws NullPointerException     if initialInputs is null
     * @throws IllegalArgumentException if mismatch between matrix dimension
     *                                  and number of neurons and matrix has
     *                                  more than 1 column
     * @require initialInputs != null && initialInputs.getRows() == number of
     * neurons && initialInputs.getColumns() == 1
     * @ensure inputs into this layer will be set
     */
    public void setInitialInputs(Matrix initialInputs) {
        if (initialInputs == null) {
            throw new NullPointerException("Null inputs");
        }
        if (initialInputs.getRows() != numberOfNeurons ||
                initialInputs.getColumns() != 1) {
            throw new IllegalArgumentException("Mismatch between matrix " +
                    "dimension and number of neurons");
        }
        this.initialInputs = initialInputs;
    }

    /**
     * Set the inputs into this layer.
     *
     * @param initialInputs the inputs into this layer
     */
    public void setInitialInputs(double[][] initialInputs) {
        setInitialInputs(new Matrix(initialInputs).getFlat());
    }

    /**
     * Overridden method from the base class in order to disable this method.
     * Input layer does not have any layer before it.
     */
    @Override
    public void setPreviousLayer(Layer previousLayer) {
        throw new UnsupportedOperationException("Input layer does not have previous layer.");
    }

    /**
     * Overridden method from the base class in order to disable this method.
     * Input layer does not have propagation error.
     */
    @Override
    public Matrix getPropagationErrorVector() {
        throw new UnsupportedOperationException("Input layer does not have propagation error.");
    }

    /**
     * Overridden method from the base class in order to disable this method.
     * Input layer does not have propagation error.
     */
    @Override
    public Matrix calculateAndGetPropagationErrorVector() {
        throw new UnsupportedOperationException("Input layer does not have propagation error.");
    }

    /**
     * Overridden method from the base class in order to disable this method.
     * Input layer does not have propagation error.
     */
    @Override
    public void calculatePropagationErrorVector() {
        throw new UnsupportedOperationException("Input layer does not have propagation error.");
    }

    /**
     * Override method from base class since input layer does not have any
     * layer before it. Simply move the inputs to the outputs without
     * modifying it.
     */
    @Override
    public void calculateOutputVector() {
        // Iterate the neurons in this layer and add the output of them into
        // the vector.
        outputVector = new Matrix(numberOfNeurons);
        for (int i = 0; i < outputVector.getRows(); i++) {
            outputVector.setValueAt(i, initialInputs.getValueAt(i));
        }
    }
}

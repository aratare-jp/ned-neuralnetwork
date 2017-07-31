package com.deathbydeco.network.layer;

import com.deathbydeco.network.NeuralNetwork;
import com.deathbydeco.network.function.Function;
import com.deathbydeco.network.neuron.FunctionFactory;
import com.deathbydeco.network.neuron.Neuron;
import com.deathbydeco.utility.Matrix;

/**
 * Created by rex on 2017/04/22.
 * <p>
 * This class is used to represent an abstract layer inside the neural network.
 */
public abstract class Layer {
    /**
     * Number of neurons in this layer.
     */
    protected int numberOfNeurons;

    /**
     * List of neurons in this layer.
     */
    protected Neuron[] neurons;

    /**
     * Previous layer that is linked to this layer.
     */
    protected Layer previousLayer;

    /**
     * Next layer that is linked to this layer.
     */
    protected Layer nextLayer;

    /**
     * Activation function that is used to calculate outputs of the neurons
     * in this layer.
     */
    protected Function function;

    /**
     * The propagation error vector of this layer.
     */
    protected Matrix propagationErrorVector;

    /**
     * Bias vector of this layer.
     */
    protected Matrix biasVector;

    /**
     * Net input vector of this layer.
     */
    protected Matrix netInputVector;

    /**
     * Output vector of this layer.
     */
    protected Matrix outputVector;

    /**
     * Weight matrix of this layer.
     */
    protected Matrix weightMatrix;

    /**
     * Default constructor for this class.
     *
     * @param numberOfNeurons number of neurons in this layer
     * @param function        activation function of this class
     * @throws IllegalArgumentException if numberOfNeurons is non-positive
     * @throws NullPointerException     if function is null
     * @require numberOfNeurons > 0 && function != null
     * @ensure new instance of this class
     */
    Layer(int numberOfNeurons, Function function) {
        if (numberOfNeurons <= 0) {
            throw new IllegalArgumentException("Non-positive number of " +
                    "neurons");
        }
        if (function == null) {
            throw new NullPointerException("Null function");
        }
        this.numberOfNeurons = numberOfNeurons;
        this.function = function;
        neurons = new Neuron[numberOfNeurons];
    }

    /**
     * Return the number of neurons in this layer.
     *
     * @return the number of neurons in this layer.
     */
    public int getNumberOfNeurons() {
        return numberOfNeurons;
    }

    /**
     * Set the previous layer that is linked to this layer. Also initialise
     * all neurons in this layer.
     *
     * @param previousLayer the previous layer that is linked to this layer
     * @throws NullPointerException if previousLayer is null
     * @require previousLayer != null
     * @ensure the previous layer that is linked to this layer will be set
     */
    public void setPreviousLayer(Layer previousLayer) {
        if (previousLayer == null) {
            throw new NullPointerException("Null argument");
        }
        this.previousLayer = previousLayer;
        for (int i = 0; i < numberOfNeurons; i++) {
            neurons[i] = new Neuron(previousLayer.numberOfNeurons);
        }
    }

    /**
     * Set the next layer that is linked to this layer.
     *
     * @param nextLayer the previous layer that is linked to this layer
     * @throws NullPointerException if nextLayer is null
     * @require nextLayer != null
     * @ensure the next layer that is linked to this layer will be set
     */
    public void setNextLayer(Layer nextLayer) {
        if (nextLayer == null) {
            throw new NullPointerException("Null argument");
        }
        this.nextLayer = nextLayer;
    }

    /**
     * Calculate the propagation error vector.
     */
    public abstract void calculatePropagationErrorVector();

    /**
     * Return a vector of propagation errors in this layer.
     *
     * @return a vector of propagation errors in this layer.
     */
    public Matrix getPropagationErrorVector() {
        return propagationErrorVector;
    }

    /**
     * Calculate and return the propagation error vector of this layer.
     *
     * @return the propagation error vector of this layer
     */
    public Matrix calculateAndGetPropagationErrorVector() {
        calculatePropagationErrorVector();
        return getPropagationErrorVector();
    }

    /**
     * Calculate the weight matrix of this layer.
     */
    public void calculateWeightMatrix() {
        // Matrix that has rows equal to the number of neurons in this layer
        // and columns equal to the number of neurons in the previous layer
        weightMatrix = new Matrix(numberOfNeurons, previousLayer.numberOfNeurons);
        // Iterate through the neurons in this layer
        for (int i = 0; i < weightMatrix.getRows(); i++) {
            // Current neuron in this layer.
            Neuron currentNeuron = neurons[i];
            // Iterate through the weights of the current neuron and add them
            // to the matrix.
            for (int j = 0; j < weightMatrix.getColumns(); j++) {
                double weight = currentNeuron.getWeightAt(j);
                weightMatrix.setValueAt(i, j, weight);
            }
        }
    }

    /**
     * Calculate and get the weight matrix of this layer.
     *
     * @return the weight matrix of this layer
     */
    public Matrix calculateAndGetWeightMatrix() {
        calculateWeightMatrix();
        return getWeightMatrix();
    }

    /**
     * Get the weight matrix of this layer.
     *
     * @return the weight matrix of this layer
     */
    public Matrix getWeightMatrix() {
        return weightMatrix;
    }

    /**
     * Update all weights of all neurons in this layer.
     *
     * @param update delta weight matrix
     * @throws NullPointerException     if update is null
     * @throws IllegalArgumentException if update is of wrong dimension with
     *                                  the weight matrix of this layer
     * @require update != null &&
     * update.getRows() == number of neurons in this layer &&
     * update.getColumns == number of neurons in the previous layer
     * @ensure weights will be updated
     */
    public void updateWeightMatrix(Matrix update) {
        if (update == null) {
            throw new NullPointerException("Null argument");
        }
        if (update.getRows() != numberOfNeurons &&
                update.getColumns() != previousLayer.numberOfNeurons) {
            throw new IllegalArgumentException("Mismatch matrix dimension");
        }
        // Loop through all neurons and their weights and update them.
        for (int i = 0; i < numberOfNeurons; i++) {
            Neuron neuron = neurons[i];
            for (int j = 0; j < neuron.getNumberOfInwardConnection(); j++) {
                double deltaWeight = update.getValueAt(i, j);
                double currentWeight = neuron.getWeightAt(j);
                double newWeight = currentWeight - deltaWeight;
                neuron.setWeightAt(j, newWeight);
            }
        }
    }

    /**
     * Update all biases of all neurons in this layer.
     *
     * @param update delta bias vector
     * @throws NullPointerException     if update is null
     * @throws IllegalArgumentException if update is not a vector and has
     *                                  wrong dimension
     * @require update != null &&
     * update.getRows() == number of neurons in this layer &&
     * update.getColumns == 1
     * @ensure biases will be updated
     */
    public void updateBiasVector(Matrix update) {
        if (update == null) {
            throw new NullPointerException("Null argument");
        }
        if (update.getRows() != numberOfNeurons && update.getColumns() != 1) {
            throw new IllegalArgumentException("Mismatch matrix dimension");
        }
        // Loop through all neurons and their weights and update them.
        for (int i = 0; i < numberOfNeurons; i++) {
            Neuron neuron = neurons[i];
            double delta = update.getValueAt(i);
            double oldvalue = neuron.getBias();
            double newValue = oldvalue - delta;
            neuron.setBias(newValue);
        }
    }

    /**
     * Calculate bias vector of this layer.
     */
    public void calculateBiasVector() {
        biasVector = new Matrix(numberOfNeurons);
        // Iterate through the neurons in this layer to add their bias to the
        // matrix.
        for (int i = 0; i < biasVector.getRows(); i++) {
            Neuron currentNeuron = neurons[i];
            biasVector.setValueAt(i, currentNeuron.getBias());
        }
    }

    /**
     * Calculate and get bias vector of this layer.
     *
     * @return bias vector of this layer
     */
    public Matrix calculateAndGetBiasVector() {
        calculateBiasVector();
        return getBiasVector();
    }

    /**
     * Get the bias vector (a matrix with one column) of this layer.
     *
     * @return the bias vector of this layer
     */
    public Matrix getBiasVector() {
        return biasVector;
    }

    /**
     * Calculate the net input vector of this layer.
     */
    public void calculateNetInputVector() {
        calculateWeightMatrix();
        previousLayer.calculateOutputVector();
        calculateBiasVector();
        // Dot product between the weight matrix of this layer and the
        // output vector of the previous layer.
        Matrix dotProduct = Matrix.dotProduct(weightMatrix,
                previousLayer.getOutputVector());
        netInputVector = Matrix.add(dotProduct, biasVector);
    }

    /**
     * Get the net input vector (a matrix with one column) of this layer.
     *
     * @return the net input vector of this layer
     */
    public Matrix getNetInputVector() {
        return netInputVector;
    }

    /**
     * Get the net input vector of this layer.
     *
     * @return the net input vector of this layer.
     */
    public Matrix calculateAndGetInputVector() {
        calculateNetInputVector();
        return getNetInputVector();
    }

    /**
     * Calculate the output vector of this layer.
     */
    public void calculateOutputVector() {
        Matrix netInputVector = calculateAndGetInputVector();
        outputVector = Matrix.applyFunction(netInputVector, function);
    }

    /**
     * Calculate and get the output vector of this layer.
     *
     * @return the output vector of this layer
     */
    public Matrix calculateAndGetOutputVector() {
        calculateOutputVector();
        return getOutputVector();
    }

    /**
     * Get activation vector of this layer.
     *
     * @return activation vector of this layer
     */
    public Matrix getOutputVector() {
        return outputVector;
    }

    /**
     * Get the derivative activation function vector of this layer.
     *
     * @return derivative activation function vector of this layer
     */
    public Matrix getDerivativeOutputVector() {
        Matrix outputVector = getOutputVector();
        Function function = FunctionFactory.dSigmoid();
        return Matrix.applyFunction(outputVector, function);
    }

    /**
     * Return the vector of partial derivative of cost function with respect
     * to bias.
     *
     * @return the vector of partial derivative of cost function with respect
     * to bias
     */
    public Matrix getCostWRTBiasVector() {
        return getPropagationErrorVector();
    }

    /**
     * Return the matrix of partial derivative of cost function with respect
     * to weight between this layer and its previous layer.
     *
     * @return the matrix of partial derivative of cost function with respect
     * to weight between this layer and its previous layer
     */
    public Matrix getCostWRTWeightMatrix() {
        Matrix partOne = previousLayer.getOutputVector();
        Matrix partTwo = getPropagationErrorVector().getTranspose();
        return Matrix.dotProduct(partOne, partTwo).getTranspose();
    }

    /**
     * Dissolve the layer.
     */
    public void dissolve() {
        for (int i = 0; i < numberOfNeurons; i++) {
            neurons[i] = null;
        }
        neurons = null;
        nextLayer = null;
        previousLayer = null;
        propagationErrorVector = null;
        biasVector = null;
        netInputVector = null;
        outputVector = null;
        weightMatrix = null;
    }

    public Neuron[] getNeurons() {
        return this.neurons;
    }
}

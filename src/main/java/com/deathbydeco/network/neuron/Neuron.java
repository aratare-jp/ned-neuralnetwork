package com.deathbydeco.network.neuron;

import com.deathbydeco.utility.Randomiser;

/**
 * Created by rex on 2017/04/22.
 * <p>
 * This class is used to represent the neurons in the neural network.
 */
public class Neuron {
    /**
     * Number of inputs into this neuron.
     */
    private int numberOfInwardConnection;

    /**
     * List of weights of this neuron. This list also includes the bias of
     * the neuron.
     */
    double[] weights;

    /**
     * Default constructor for the class.
     *
     * @param numberOfInwardConnection number of inputs into this neurons
     * @throws IllegalArgumentException if numberOfInwardConnection is
     *                                  non-positive
     * @require numberOfInwardConnection != null
     * @ensure new instance of this class
     */
    public Neuron(int numberOfInwardConnection) {
        if (numberOfInwardConnection <= 0) {
            throw new IllegalArgumentException("Non-positive number of inputs");
        }
        this.numberOfInwardConnection = numberOfInwardConnection;
        weights = new double[numberOfInwardConnection + 1];
        init();
    }

    /**
     * Initialise the weights of this neuron.
     */
    protected void init() {
        for (int i = 0; i < numberOfInwardConnection + 1; i++) {
            weights[i] = Randomiser.randomise() / 1000;
        }
    }

    /**
     * Get the number of inputs into this neuron.
     *
     * @return the number of inputs into this neuron
     */
    public int getNumberOfInwardConnection() {
        return numberOfInwardConnection;
    }

    /**
     * Get the list of weights of this neuron.
     *
     * @return the list of weights of this neuron
     */
    public double[] getWeights() {
        return weights;
    }

    /**
     * Set the weights of this neuron.
     *
     * @param weights the new list of weights to set to this neuron
     * @throws NullPointerException     if weights is null
     * @throws IllegalArgumentException if the size of the new list of
     *                                  weights mismatch the old one
     * @require weights != null && weights.length == getWeights().length
     * @ensure getWeights() == weights
     */
    public void setWeights(double[] weights) {
        if (weights == null) {
            throw new NullPointerException("Null list of weights");
        }
        if (weights.length != this.weights.length) {
            throw new IllegalArgumentException("Mismatch length");
        }
        this.weights = weights;
    }

    /**
     * Get the weight at specified index.
     *
     * @param index the index of the retrieved weight
     * @return the weight at specified index
     * @throws IllegalArgumentException if index out of bound
     * @require index >= 0 && index < getWeights().length
     * @ensure the weight at specified index
     */
    public double getWeightAt(int index) {
        if (index < 0 || index >= weights.length) {
            throw new IllegalArgumentException("Index out of bound");
        }
        return weights[index];
    }

    /**
     * Set the weight at specified index.
     *
     * @param index the index of the retrieved weight
     * @param value the new value of the weight
     * @throws IllegalArgumentException if index out of bound
     * @require index >= 0 && index < getWeights().length
     * @ensure getWeightAt(index) == value
     */
    public void setWeightAt(int index, double value) {
        if (index < 0 || index >= weights.length) {
            throw new IllegalArgumentException("Index out of bound");
        }
        weights[index] = value;
    }

    /**
     * Get the bias of this neuron.
     *
     * @return the bias of this neuron
     */
    public double getBias() {
        return getWeightAt(numberOfInwardConnection);
    }

    /**
     * Update the bias of the neuron.
     *
     * @param newBias new value of bias
     */
    public void setBias(double newBias) {
        weights[numberOfInwardConnection] = newBias;
    }
}

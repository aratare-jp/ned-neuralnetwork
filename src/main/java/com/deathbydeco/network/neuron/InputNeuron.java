package com.deathbydeco.network.neuron;

/**
 * Created by rex on 2017/04/22.
 * <p>
 * This class is used to represent the neurons in the input layer.
 */
public class InputNeuron extends Neuron {
    /**
     * Default constructor for the class. Input neuron only has one input.
     */
    public InputNeuron() {
        super(1);
    }

    /**
     * Overridden initialisation the weights of this neuron.
     */
    @Override
    protected void init() {
        weights[0] = 1;
        weights[1] = 1;
    }
}

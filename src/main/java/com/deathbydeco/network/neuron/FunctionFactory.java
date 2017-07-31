package com.deathbydeco.network.neuron;

import com.deathbydeco.network.function.Function;

/**
 * Created by rex on 2017/04/22.
 * <p>
 * This class is used as a factory that produces lambda function that will be
 * used in the neural network.
 */
public class FunctionFactory {
    /**
     * Return the sigmoid function.
     *
     * @return the sigmoid function
     */
    public static Function sigmoid() {
        return (x -> 1 / (1 + Math.exp(-x)));
    }

    /**
     * Return the derivative of the sigmoid function.
     *
     * @return the derivative of the sigmoid function
     */
    public static Function dSigmoid() {
        return (x -> x * (1 - x));
    }

    /**
     * Return a simple function that does not affect the input.
     *
     * @return a simple function that does not affect the input
     */
    public static Function simple() {
        return (x -> x);
    }

    /**
     * Return a function that raises the input to the power of 2.
     *
     * @return a function that raises the input to the power of 2
     */
    public static Function square() {
        return (x -> Math.pow(x, 2));
    }
}

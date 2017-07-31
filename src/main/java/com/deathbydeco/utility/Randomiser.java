package com.deathbydeco.utility;

import java.util.Random;

/**
 * Created by rex on 2017/04/05.
 * <p>
 * Utility class used to generate random doubles.
 */
public class Randomiser {
    // Underlying randomiser that generates doubles.
    private final static Random RANDOMISER = new Random(0);

    /**
     * Return the next random double.
     *
     * @return the next random double
     */
    public static double randomise() {
        return RANDOMISER.nextDouble();
    }
}

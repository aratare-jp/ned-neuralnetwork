package com.deathbydeco.network;

import com.deathbydeco.utility.Matrix;

/**
 * Created by rex on 24/04/2017.
 * <p>
 * This class serves as a factory that produces expected output from the
 * neural network. All expected output vector will be ordered from bulleyes,
 * central island, smiley face.
 */
public class ExpectedOutputFactory {
    /**
     * Return the expected output vector for bulleyes.
     *
     * @return the expected output vector for bulleyes
     */
    public static Matrix getBulleyes() {
        Matrix exOutBulleyes = new Matrix(3);
        exOutBulleyes.setValueAt(0, 1);
        exOutBulleyes.setValueAt(1, 0);
        exOutBulleyes.setValueAt(2, 0);
        return exOutBulleyes;
    }

    /**
     * Return the expected output vector for island.
     *
     * @return the expected output vector for island
     */
    public static Matrix getIsland() {
        Matrix exOutIsland = new Matrix(3);
        exOutIsland.setValueAt(0, 0);
        exOutIsland.setValueAt(1, 1);
        exOutIsland.setValueAt(2, 0);
        return exOutIsland;
    }
    /**
     * Return the expected output vector for island.
     *
     * @return the expected output vector for island
     */
    public static Matrix getSmiley() {
        Matrix exOutSmiley = new Matrix(3);
        exOutSmiley.setValueAt(0, 0);
        exOutSmiley.setValueAt(1, 0);
        exOutSmiley.setValueAt(2, 1);
        return exOutSmiley;
    }
}

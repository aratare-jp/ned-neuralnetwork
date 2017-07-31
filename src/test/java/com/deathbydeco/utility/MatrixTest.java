package com.deathbydeco.utility;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;

import static org.junit.Assert.*;

/**
 * Created by rex on 2017/04/13.
 * <p>
 * Test suites for Matrix class.
 */
public class MatrixTest {

    private static Logger LOGGER = LoggerFactory.getLogger(MatrixTest.class);

    /**
     * Test dot product of two matrices with the first matrix having one row.
     */
    @Test
    public void testDotProductWithOneRow() {
        // Matrix one
        Matrix matrixOne = new Matrix(1, 3);
        matrixOne.setValueAt(0, 0, 3);
        matrixOne.setValueAt(0, 1, 4);
        matrixOne.setValueAt(0, 2, 2);

        // Matrix two
        Matrix matrixTwo = new Matrix(3, 4);
        matrixTwo.setValueAt(0, 0, 13);
        matrixTwo.setValueAt(0, 1, 9);
        matrixTwo.setValueAt(0, 2, 7);
        matrixTwo.setValueAt(0, 3, 15);
        matrixTwo.setValueAt(1, 0, 8);
        matrixTwo.setValueAt(1, 1, 7);
        matrixTwo.setValueAt(1, 2, 4);
        matrixTwo.setValueAt(1, 3, 6);
        matrixTwo.setValueAt(2, 0, 6);
        matrixTwo.setValueAt(2, 1, 4);
        matrixTwo.setValueAt(2, 2, 0);
        matrixTwo.setValueAt(2, 3, 3);

        // Dot product
        Matrix dotProduct = Matrix.dotProduct(matrixOne, matrixTwo);
        assertEquals(83, dotProduct.getValueAt(0, 0), 0);
        assertEquals(63, dotProduct.getValueAt(0, 1), 0);
        assertEquals(37, dotProduct.getValueAt(0, 2), 0);
        assertEquals(75, dotProduct.getValueAt(0, 3), 0);
    }

    /**
     * Test dot product of two matrices with them having multiple rows and
     * columns.
     */
    @Test
    public void testDotProductWithMultipleRows() {
        // Matrix one
        Matrix matrixOne = new Matrix(2, 3);
        matrixOne.setValueAt(0, 0, 1);
        matrixOne.setValueAt(0, 1, 2);
        matrixOne.setValueAt(0, 2, 3);
        matrixOne.setValueAt(1, 0, 4);
        matrixOne.setValueAt(1, 1, 5);
        matrixOne.setValueAt(1, 2, 6);

        // Matrix two
        Matrix matrixTwo = new Matrix(3, 2);
        matrixTwo.setValueAt(0, 0, 7);
        matrixTwo.setValueAt(0, 1, 8);
        matrixTwo.setValueAt(1, 0, 9);
        matrixTwo.setValueAt(1, 1, 10);
        matrixTwo.setValueAt(2, 0, 11);
        matrixTwo.setValueAt(2, 1, 12);

        // Dot product
        Matrix dotProduct = Matrix.dotProduct(matrixOne, matrixTwo);
        assertEquals(58, dotProduct.getValueAt(0, 0), 0);
        assertEquals(64, dotProduct.getValueAt(0, 1), 0);
        assertEquals(139, dotProduct.getValueAt(1, 0), 0);
        assertEquals(154, dotProduct.getValueAt(1, 1), 0);
    }

    /**
     * Test transpose of a matrix.
     */
    @Test
    public void testTranspose() {
        int rows = 9;
        int columns = 3;
        // Original matrix
        Matrix originalMatrix = new Matrix(rows, columns);
        // Set up values
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                originalMatrix.setValueAt(i, j, Randomiser.randomise());
            }
        }
        // Transpose
        Matrix transpose = originalMatrix.getTranspose();
        // Testing
        for (int i = 0; i < originalMatrix.getRows(); i++) {
            for (int j = 0; j < originalMatrix.getColumns(); j++) {
                double originalValue = originalMatrix.getValueAt(i, j);
                double transposeValue = transpose.getValueAt(j, i);
                assertEquals(originalValue, transposeValue, 0);
            }
        }
    }

    /**
     * Testing flattening a matrix
     */
    @Test
    public void testFlat() {
        // Matrix one
        Matrix matrixOne = new Matrix(2, 3);
        matrixOne.setValueAt(0, 0, 1);
        matrixOne.setValueAt(0, 1, 2);
        matrixOne.setValueAt(0, 2, 3);
        matrixOne.setValueAt(1, 0, 4);
        matrixOne.setValueAt(1, 1, 5);
        matrixOne.setValueAt(1, 2, 6);
        // Get flat matrix from matrix one
        Matrix flatMatrix = matrixOne.getFlat();
        // Test dimension
        assertEquals(6, flatMatrix.getRows());
        assertEquals(1, flatMatrix.getColumns());
        // Test elements
        for (int i = 0; i < flatMatrix.getRows(); i++) {
            assertEquals(i + 1, flatMatrix.getValueAt(i), 0);
        }
    }

    /**
     * Test for grand sum of a matrix
     */
    @Test
    public void testSum() {
        // Matrix one
        Matrix matrixOne = new Matrix(2, 3);
        matrixOne.setValueAt(0, 0, 1);
        matrixOne.setValueAt(0, 1, 2);
        matrixOne.setValueAt(0, 2, 3);
        matrixOne.setValueAt(1, 0, 4);
        matrixOne.setValueAt(1, 1, 5);
        matrixOne.setValueAt(1, 2, 6);
        // Get grand sum
        double sum = matrixOne.sum();
        // Check
        assertEquals(21, sum, 0);
    }

    /**
     * Test out normalisation of a matrix.
     */
    @Test
    public void testNormalise() {
        // Matrix one
        Matrix matrixOne = new Matrix(2, 3);
        matrixOne.setValueAt(0, 0, 1);
        matrixOne.setValueAt(0, 1, 2);
        matrixOne.setValueAt(0, 2, 3);
        matrixOne.setValueAt(1, 0, 4);
        matrixOne.setValueAt(1, 1, 5);
        matrixOne.setValueAt(1, 2, 6);

        Matrix normalised = matrixOne.normalise();

        for (int i = 0; i < normalised.getRows(); i++) {
            for (int j = 0; j < normalised.getColumns(); j++) {
                double value = normalised.getValueAt(i, j);
                if (value < 0 || value > 1) {
                    fail("Normalisation failed.");
                }
            }
        }
    }
}
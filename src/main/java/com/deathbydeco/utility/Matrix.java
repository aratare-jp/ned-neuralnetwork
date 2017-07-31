package com.deathbydeco.utility;

import com.deathbydeco.network.function.Function;

import java.util.Collections;
import java.util.List;

/**
 * Created by rex on 2017/03/23.
 * <p>
 * This class is used to represent a simple matrix.
 */
public class Matrix {
    /**
     * Array to store data under the hood.
     */
    private double[][] array;

    /**
     * Number of rows.
     */
    private int rows;

    /**
     * Number of columns.
     */
    private int columns;

    /**
     * Maximum x in the data set.
     */
    private final int xMax = 6;

    /**
     * Minimum x in the data set.
     */
    private final int xMin = -6;

    /**
     * Return the dot product of two matrices.
     *
     * @param matrixOne first matrix
     * @param matrixTwo two matrix
     * @return dot product of two matrices
     * @throws NullPointerException     if either matrix is null
     * @throws IllegalArgumentException if the first matrix's columns does
     *                                  not match the second matrix's rows
     * @require matrixOne != null && matrixTwo != null &&
     * matrixOne.getColumns() == matrixTwo.getRows()
     * @ensure dot product of two matrices
     */
    public static Matrix dotProduct(Matrix matrixOne, Matrix matrixTwo) {
        if (matrixOne == null || matrixTwo == null) {
            throw new NullPointerException("Arguments cannot be null.");
        }
        if (matrixOne.columns != matrixTwo.rows) {
            throw new IllegalArgumentException("Matrices are not of the same " +
                    "dimension");
        }
        Matrix dotProduct = new Matrix(matrixOne.rows, matrixTwo.columns);
        int counter = 0;
        for (int i = 0; i < matrixOne.rows; i++) {
            for (int j = 0; j < matrixTwo.columns; j++) {
                double rowResult = 0;
                for (int k = 0; k < matrixOne.columns; k++) {
                    double valueOne = matrixOne.getValueAt(i, k);
                    double valueTwo = matrixTwo.getValueAt(k, counter);
                    rowResult += valueOne * valueTwo;
                }
                dotProduct.setValueAt(i, j, rowResult);
                counter++;
            }
            counter = 0;
        }
        return dotProduct;
    }

    /**
     * Return the Schur product of two matrices.
     *
     * @param matrixOne first matrix
     * @param matrixTwo two matrix
     * @return Schur product of two matrices
     * @throws NullPointerException     if either matrix is null
     * @throws IllegalArgumentException if two matrices are not of the same
     *                                  dimension
     * @require matrixOne != null && matrixTwo != null &&
     * matrixOne.getColumns() == matrixTwo.getColumns() &&
     * matrixOne.getRows() == matrixTwo.getRows()
     * @ensure Schur product of two matrices
     */
    public static Matrix schurProduct(Matrix matrixOne, Matrix matrixTwo) {
        if (matrixOne == null || matrixTwo == null) {
            throw new NullPointerException("Arguments cannot be null.");
        }
        if (matrixOne.columns != matrixTwo.columns ||
                matrixOne.rows != matrixTwo.rows) {
            throw new IllegalArgumentException("Matrices are not of the same " +
                    "dimension");
        }
        Matrix result = new Matrix(matrixOne.rows, matrixOne.columns);
        for (int i = 0; i < matrixOne.rows; i++) {
            for (int j = 0; j < matrixOne.columns; j++) {
                double valueOne = matrixOne.getValueAt(i, j);
                double valueTwo = matrixTwo.getValueAt(i, j);
                result.setValueAt(i, j, valueOne * valueTwo);
            }
        }
        return result;
    }

    /**
     * Return the addition of two matrices.
     *
     * @param matrixOne first matrix
     * @param matrixTwo two matrix
     * @return addition of two matrices
     * @throws NullPointerException     if either matrix is null
     * @throws IllegalArgumentException if two matrices are not of the same
     *                                  dimension
     * @require matrixOne != null && matrixTwo != null &&
     * matrixOne.getColumns() == matrixTwo.getColumns() &&
     * matrixOne.getRows() == matrixTwo.getRows()
     * @ensure addition of two matrices
     */
    public static Matrix add(Matrix matrixOne, Matrix matrixTwo) {
        if (matrixOne == null || matrixTwo == null) {
            throw new NullPointerException("Arguments cannot be null.");
        }
        if (matrixOne.columns != matrixTwo.columns ||
                matrixOne.rows != matrixTwo.rows) {
            throw new IllegalArgumentException("Matrices are not of the same " +
                    "dimension");
        }
        Matrix result = new Matrix(matrixOne.rows, matrixOne.columns);
        for (int i = 0; i < matrixOne.rows; i++) {
            for (int j = 0; j < matrixOne.columns; j++) {
                double valueOne = matrixOne.getValueAt(i, j);
                double valueTwo = matrixTwo.getValueAt(i, j);
                result.setValueAt(i, j, valueOne + valueTwo);
            }
        }
        return result;
    }

    /**
     * Return the difference of two matrices.
     *
     * @param matrixOne first matrix
     * @param matrixTwo two matrix
     * @return difference of two matrices
     * @throws NullPointerException     if either matrix is null
     * @throws IllegalArgumentException if two matrices are not of the same
     *                                  dimension
     * @require matrixOne != null && matrixTwo != null &&
     * matrixOne.getColumns() == matrixTwo.getColumns() &&
     * matrixOne.getRows() == matrixTwo.getRows()
     * @ensure difference of two matrices
     */
    public static Matrix subtract(Matrix matrixOne, Matrix matrixTwo) {
        if (matrixOne == null || matrixTwo == null) {
            throw new NullPointerException("Arguments cannot be null.");
        }
        if (matrixOne.columns != matrixTwo.columns ||
                matrixOne.rows != matrixTwo.rows) {
            throw new IllegalArgumentException("Matrices are not of the same " +
                    "dimension");
        }
        Matrix result = new Matrix(matrixOne.rows, matrixOne.columns);
        for (int i = 0; i < matrixOne.rows; i++) {
            for (int j = 0; j < matrixOne.columns; j++) {
                double valueOne = matrixOne.getValueAt(i, j);
                double valueTwo = matrixTwo.getValueAt(i, j);
                result.setValueAt(i, j, valueOne - valueTwo);
            }
        }
        return result;
    }

    /**
     * Apply a function to a matrix.
     *
     * @param matrix   the matrix to be applied with the function
     * @param function the function used to apply to the matrix
     * @return a new matrix whose elements are the output of the matrix being
     * applied with the function
     * @throws NullPointerException if matrix or function is null
     * @require matrix != null && function != null
     * @ensure a new matrix whose elements are the output of the matrix being
     * applied with the function
     */
    public static Matrix applyFunction(Matrix matrix, Function function) {
        Matrix resultMatrix = new Matrix(matrix.rows, matrix.columns);
        // Loop through the matrix and simply apply the function to all
        // elements in the original matrix.
        for (int i = 0; i < resultMatrix.rows; i++) {
            for (int j = 0; j < resultMatrix.columns; j++) {
                double input = matrix.getValueAt(i, j);
                resultMatrix.setValueAt(i, j, function.calculate(input));
            }
        }
        return resultMatrix;
    }

    /**
     * Default constructor for this class.
     *
     * @param rows    number of rows.
     * @param columns number of columns.
     * @throws IllegalArgumentException if rows or columns less than one
     * @require rows >= 1 && columns >= 1
     * @ensure new instance of this class
     */
    public Matrix(int rows, int columns) {
        if (rows < 1 || columns < 1) {
            throw new IllegalArgumentException("Row and column numbers invalid.");
        }
        array = new double[rows][columns];
        this.rows = rows;
        this.columns = columns;
    }

    /**
     * Constructor that takes an array of arrays of doubles.
     *
     * @param array an array of arrays of doubles
     * @throws NullPointerException if array is null
     * @require array != null
     * @ensure new instance of this class
     */
    public Matrix(double[][] array) {
        if (array == null) {
            throw new NullPointerException("Null array");
        }
        this.array = array;
        rows = array.length;
        columns = array[0].length;
    }

    /**
     * Constructor that takes a list of lists of doubles.
     *
     * @param lists a list of lists of doubles
     * @throws NullPointerException if lists is null and element of lists is
     *                              null
     * @require lists != null && element of lists != null
     * @ensure new instance of this class
     */
    public Matrix(List<Double> lists) {
        this(lists.size());
        for (int i = 0; i < rows; i++) {
            setValueAt(i, lists.get(i));
        }
    }

    /**
     * Convenient constructor with one column each row.
     *
     * @param rows number of rows
     */
    public Matrix(int rows) {
        this(rows, 1);
    }

    /**
     * Return the data array.
     *
     * @return the data array.
     */
    public double[][] getArray() {
        return array;
    }

    /**
     * Return the value at specified index.
     *
     * @param row    row index
     * @param column column index
     * @return the value at specified index.
     * @throws IllegalArgumentException if row or column out of bound.
     * @require row >= 0 && row < rows && column >= 0 && column <= columns
     * @ensure the value at specified index.
     */
    public double getValueAt(int row, int column) {
        if (row < 0 || row >= rows) {
            throw new IllegalArgumentException("Row index out of bound.");
        }
        if (column < 0 || column >= columns) {
            throw new IllegalArgumentException("Column index out of bound.");
        }
        return array[row][column];
    }

    /**
     * Convenient function to return the value of the first column.
     *
     * @param row row index
     * @return the value of the first column.
     */
    public double getValueAt(int row) {
        return getValueAt(row, 0);
    }

    /**
     * Set the value at specified index.
     *
     * @param row    row index
     * @param column column index
     * @param value  value to set
     * @throws IllegalArgumentException if row or column out of bound.
     * @require row >= 0 && row < rows && column >= 0 && column <= columns
     * @ensure the value at specified index is set.
     */
    public void setValueAt(int row, int column, double value) {
        if (row < 0 || row >= rows) {
            throw new IllegalArgumentException("Row index out of bound.");
        }
        if (column < 0 || column >= columns) {
            throw new IllegalArgumentException("Column index out of bound.");
        }
        array[row][column] = value;
    }

    /**
     * Convenient function to set value at column 1.
     *
     * @param row   row index
     * @param value value to set
     */
    public void setValueAt(int row, double value) {
        setValueAt(row, 0, value);
    }

    /**
     * Return the number of rows in this matrix.
     *
     * @return the number of rows in this matrix.
     */
    public int getRows() {
        return rows;
    }

    /**
     * Return the number of columns in this matrix.
     *
     * @return the number of columns in this matrix.
     */
    public int getColumns() {
        return columns;
    }

    /**
     * Return transpose of a matrix.
     *
     * @return transpose of a matrix.
     */
    public Matrix getTranspose() {
        Matrix newMatrix = new Matrix(columns, rows);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                newMatrix.setValueAt(j, i, getValueAt(i, j));
            }
        }
        return newMatrix;
    }

    /**
     * Flatten out a matrix into a vector.
     *
     * @return a vector containing elements in the source matrix
     */
    public Matrix getFlat() {
        Matrix newMatrix = new Matrix(rows * columns);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                int index = i * columns + j;
                newMatrix.setValueAt(index, getValueAt(i, j));
            }
        }
        return newMatrix;
    }

    /**
     * Return the grand sum of all the elements in the matrix.
     *
     * @return the grand sum of all the elements in the matrix
     */
    public double sum() {
        double sum = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                sum += array[i][j];
            }
        }
        return sum;
    }

    /**
     * Multiply all elements in this matrix with given argument.
     *
     * @param x number to be multiplied with
     */
    public void multiplyAllElementsWith(double x) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                array[i][j] = array[i][j] * x;
            }
        }
    }

    /**
     * Normalise the input value into values between 0 and 1.
     *
     * @return a new matrix with normalised elements
     */
    public Matrix normalise() {
        Matrix normalised = new Matrix(rows, columns);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                double value = getValueAt(i, j);
                double normalisedValue;
                if (value < -6 || value > 6) {
                    normalisedValue = 0;
                } else {
                    normalisedValue = (value - xMin) / (xMax - xMin);
                }
                normalised.setValueAt(i, j, normalisedValue);
            }
        }
        return normalised;
    }

    /**
     * Check if given obj is equal to this matrix.
     *
     * @param obj the obj to be checked.
     * @return a boolean indicating if the two are equal to each other.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Matrix)) {
            return false;
        }
        Matrix converted = (Matrix) obj;
        // False if these don't have the same dimensions.
        if (rows != converted.rows || columns != converted.columns) {
            return false;
        }
        // Check each value in the matrix for equality.
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (array[i][j] != converted.array[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Hash code of the matrix.
     *
     * @return hash code of the matrix.
     */
    @Override
    public int hashCode() {
        int result = 199;
        // Simply add all of the values
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                result += (int) array[i][j];
            }
        }
        return result;
    }

    /**
     * Return the string representation of this matrix.
     *
     * @return the string representation of this matrix.
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Number of rows: ").append(rows).append("\n");
        stringBuilder.append("Number of columns: ").append(columns).append("\n");
        // Loop through the matrix to add the value to the string builder.
        for (int i = 0; i < rows; i++) {
            stringBuilder.append("Row ").append(i).append(": ");
            for (int j = 0; j < columns; j++) {
                stringBuilder.append(array[i][j]).append("\t");
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }
}

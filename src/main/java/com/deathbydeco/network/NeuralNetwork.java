package com.deathbydeco.network;

import com.deathbydeco.network.layer.HiddenLayer;
import com.deathbydeco.network.layer.InputLayer;
import com.deathbydeco.network.layer.Layer;
import com.deathbydeco.network.layer.OutputLayer;
import com.deathbydeco.network.neuron.FunctionFactory;
import com.deathbydeco.utility.DataSet;
import com.deathbydeco.utility.Matrix;
import com.deathbydeco.utility.TrainingSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rex on 2017/04/22.
 * <p>
 * This class is used to represent a neural network.
 */
public class NeuralNetwork {

    /**
     * Enumeration representing different types of myopia file.
     */
    public enum FileType {
        BULLEYES(ExpectedOutputFactory.getBulleyes()),
        ISLAND(ExpectedOutputFactory.getIsland()),
        SMILEY(ExpectedOutputFactory.getSmiley()),
        UNKNOWN(null);

        private Matrix expectedOutput;

        /**
         * Default constructor for this enumeration.
         *
         * @param expectedOutput the expected output of this type
         */
        FileType(Matrix expectedOutput) {
            this.expectedOutput = expectedOutput;
        }

        /**
         * Return the expected output of this type.
         *
         * @return the expected output of this type
         */
        public Matrix getExpectedOutput() {
            return expectedOutput;
        }
    }

    /**
     * Logger for the class.
     */
    private Logger LOGGER = LoggerFactory.getLogger(NeuralNetwork.class);

    /**
     * Error threshold for the neural network.
     */
    public static final double ERROR_THRESHOLD = 0.1;

    /**
     * Learning rate of the neural network.
     */
    public static final double LEARNING_RATE = 0.1;

    /**
     * Input layer
     */
    private InputLayer inputLayer;

    /**
     * List of hidden layers.
     */
    private HiddenLayer[] hiddenLayers;

    /**
     * Output layer.
     */
    private OutputLayer outputLayer;

    /**
     * Iteration of the neural network.
     */
    private int maxEpoch;

    /**
     * Error rate of the neural network.
     */
    private double errorRate = 10;

    /**
     * Array of all hidden layers' delta weight matrices after backpropagated.
     */
    private Matrix[] hiddenWeightUpdate;

    /**
     * Array of all hidden layers' delta biases matrices after backpropagated.
     */
    private Matrix[] hiddenBiasUpdate;

    /**
     * Array of mean costs for each iteration of this neural network.
     */
    private List<Double> meanCosts;

    /**
     * Time taken to finish the training
     */
    private double time;

    /**
     * Default constructor for this class.
     *
     * @param numberOfInputNeurons  number of input neurons
     * @param numberOfHiddenNeurons list of number of hidden neurons
     * @param numberOfOutputNeurons number of output neurons
     * @param maxEpoch              maximum epoch
     * @throws IllegalArgumentException if non-positive number of neurons or
     *                                  mismatch number of hidden neurons or
     *                                  non-positive epoch
     * @throws NullPointerException     if null list of number of hidden
     *                                  neurons
     * @require numberOfInputNeurons > 0 && numberOfHiddenLayers >= 0 &&
     * numberOfOutputNeurons > 0 &&
     * numberOfHiddenNeurons.length == numberOfHiddenLayers && maxEpoch > 0
     * @ensure new instance of this class
     */
    public NeuralNetwork(int numberOfInputNeurons,
                         int[] numberOfHiddenNeurons,
                         int numberOfOutputNeurons,
                         int maxEpoch) {
        checkArgument(numberOfInputNeurons,
                numberOfHiddenNeurons,
                numberOfOutputNeurons,
                maxEpoch);
        this.maxEpoch = maxEpoch;
        hiddenWeightUpdate = new Matrix[numberOfHiddenNeurons.length];
        hiddenBiasUpdate = new Matrix[numberOfHiddenNeurons.length];
        meanCosts = new ArrayList<>();
        // Initialise all layers before connecting them
        inputLayer = new InputLayer(numberOfInputNeurons,
                FunctionFactory.simple());
        hiddenLayers = new HiddenLayer[numberOfHiddenNeurons.length];
        for (int i = 0; i < numberOfHiddenNeurons.length; i++) {
            hiddenLayers[i] = new HiddenLayer(numberOfHiddenNeurons[i],
                    FunctionFactory.sigmoid());
        }
        outputLayer = new OutputLayer(numberOfOutputNeurons,
                FunctionFactory.sigmoid());
        // Link all layers together
        linkingLayers();
    }

    /**
     * Helper method that checks all the arguments.
     *
     * @param numberOfInputNeurons  number of input neurons
     * @param numberOfHiddenNeurons list of number of hidden neurons
     * @param numberOfOutputNeurons number of output neurons
     * @param maxEpoch              maximum epoch
     * @throws IllegalArgumentException if non-positive number of neurons or
     *                                  mismatch number of hidden neurons or
     *                                  non-positive epoch
     * @throws NullPointerException     if null list of number of hidden
     *                                  neurons
     * @require numberOfInputNeurons > 0 && numberOfHiddenLayers >= 0 &&
     * numberOfOutputNeurons > 0 &&
     * numberOfHiddenNeurons.length == numberOfHiddenLayers && maxEpoch > 0
     */
    private void checkArgument(int numberOfInputNeurons,
                               int[] numberOfHiddenNeurons,
                               int numberOfOutputNeurons,
                               int maxEpoch) {
        if (numberOfHiddenNeurons == null) {
            throw new NullPointerException("Null list of number of hidden " +
                    "neurons");
        }
        if (numberOfInputNeurons <= 0 || numberOfHiddenNeurons.length < 0 ||
                numberOfOutputNeurons <= 0) {
            throw new IllegalArgumentException("Non-positive number of " +
                    "neurons");
        }
        if (maxEpoch <= 0) {
            throw new IllegalArgumentException("Non-positive epoch");
        }
    }

    /**
     * Helper method that links all the layers together.
     */
    private void linkingLayers() {
        // If there is no hidden layer, connect the input to the output neurons.
        if (hiddenLayers.length == 0) {
            inputLayer.setNextLayer(outputLayer);
            outputLayer.setPreviousLayer(inputLayer);
        } else if (hiddenLayers.length == 1) {
            inputLayer.setNextLayer(hiddenLayers[0]);
            hiddenLayers[0].setPreviousLayer(inputLayer);
            hiddenLayers[0].setNextLayer(outputLayer);
            outputLayer.setPreviousLayer(hiddenLayers[0]);
        } else {
            // Connecting layers together
            for (int i = 0; i < hiddenLayers.length; i++) {
                Layer currentLayer = hiddenLayers[i];
                if (i == 0) {
                    inputLayer.setNextLayer(currentLayer);
                    currentLayer.setPreviousLayer(inputLayer);
                    currentLayer.setNextLayer(hiddenLayers[i + 1]);
                } else if (i == hiddenLayers.length - 1) {
                    outputLayer.setPreviousLayer(currentLayer);
                    currentLayer.setPreviousLayer(hiddenLayers[i - 1]);
                    currentLayer.setNextLayer(outputLayer);
                } else {
                    currentLayer.setPreviousLayer(hiddenLayers[i - 1]);
                    currentLayer.setNextLayer(hiddenLayers[i + 1]);
                }
            }
        }
    }

    /**
     * Train the neural network first before starting the server.
     */
    public void training() {
        LOGGER.info("Start training the neural network...");
        // Time before the training starts.
        long timeBeforeTraining = System.currentTimeMillis();
        try {
            LOGGER.info("Retrieving training set...");
            File directoryBulleyes = new File("src/resources/datasets/bulleyes");
            File directoryIsland = new File("src/resources/datasets/Central Island");
            File directorySmiley = new File("src/resources/datasets/Smiley Face");
            // Retrieve all child files
            File[] bulleyes = directoryBulleyes.listFiles();
            File[] island = directoryIsland.listFiles();
            File[] smiley = directorySmiley.listFiles();
            if (bulleyes == null || island == null || smiley == null) {
                throw new NullPointerException("Cannot find files in directory.");
            }
            Map<FileType, List<Matrix>> map = new HashMap<>();
            map.put(FileType.BULLEYES, trainingHelper(bulleyes));
            LOGGER.info("Processing " + map.get(FileType.BULLEYES).size() + " bulleyes files...");
            map.put(FileType.ISLAND, trainingHelper(island));
            LOGGER.info("Processing " + map.get(FileType.ISLAND).size() + " island files...");
            map.put(FileType.SMILEY, trainingHelper(smiley));
            LOGGER.info("Processing " + map.get(FileType.SMILEY).size() + " smiley files...");
            run(map);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // Time to train the neural network.
        long timeAfterTraining = System.currentTimeMillis();
        time = (timeAfterTraining - timeBeforeTraining) / 1000.0;
        LOGGER.info("Number of hidden layers: " + hiddenLayers.length);
        for (int i = 0; i < hiddenLayers.length; i++) {
            LOGGER.info("Number of hidden neurons in layer " + i + " " +
                    hiddenLayers[i].getNumberOfNeurons());
        }
        LOGGER.info("Mean cost after training:\t\t" + errorRate);
        LOGGER.info("Time taken to train:\t\t\t" + time + " seconds");
    }

    /**
     * Simple method to help with the training of the neural network.
     */
    private List<Matrix> trainingHelper(File[] files) throws FileNotFoundException {
        List<Matrix> processedMatrices = new ArrayList<>();
        // Train smiley
        for (File file : files) {
            InputStream inputStream = new FileInputStream(file);
            DataSet dataSet = new DataSet(inputStream);
            ArrayList<ArrayList<Double>> acInputs = dataSet.getAxialCurvature();
            Matrix before = new Matrix(acInputs.get(0));
            Matrix after = new Matrix(acInputs.get(1));
            processedMatrices.add(Matrix.subtract(after, before).normalise());
        }
        return processedMatrices;
    }

    public void training(TrainingSet... trainingSets) {
        
    }

    /**
     * Run the neural network. It takes a hash map of file types (bulleyes,
     * island, smiley, etc.) with their corresponding actual data (in forms
     * of matrices) extracted from the physical files. All matrices in the
     * map are normalised.
     *
     * @param map single data set for the neural network
     * @throws NullPointerException     if map is null
     * @throws IllegalArgumentException if the matrix's dimension is wrong
     * @require rows of each matrix == number of neurons in the output layer &&
     * columns of each matrix != 1 && input is normalised
     * @ensure neural network will be trained
     */
    public void run(Map<FileType, List<Matrix>> map) {
        for (FileType fileType : FileType.values()) {
            if (fileType == FileType.UNKNOWN) {
                continue;
            }
            for (Matrix matrix : map.get(fileType)) {
                if (matrix == null) {
                    throw new NullPointerException("Null found");
                }
                if (matrix.getRows() != inputLayer.getNumberOfNeurons() ||
                        matrix.getColumns() != 1) {
                    throw new IllegalArgumentException("Mismatch matrix dimension");
                }
            }
        }
        List<Matrix> bulleyes = map.get(FileType.BULLEYES);
        List<Matrix> islands = map.get(FileType.ISLAND);
        List<Matrix> smileys = map.get(FileType.SMILEY);
        LOGGER.info("Start training the neural network...");
        // Counter for current iteration of the neural network.
        int currentEpoch = 0;
        // Continue to learn until either epoch is reached or error rate
        // drops below the acceptable threshold.
        while (currentEpoch++ < maxEpoch && errorRate > ERROR_THRESHOLD) {
            double bulleyesError = 1;
            double islandError = 1;
            double smileyError = 1;
            for (int i = 0; i < bulleyes.size(); i++) {
                Matrix bulleye = bulleyes.get(i);
                Matrix island = islands.get(i);
                Matrix smiley = smileys.get(i);
                // Bulleyes
                outputLayer.setExpectedOutputs(FileType.BULLEYES.expectedOutput);
                inputLayer.setInitialInputs(bulleye);
                forward();
                backpropagate();
                bulleyesError = outputLayer.getMeanCost();
                // Island
                outputLayer.setExpectedOutputs(FileType.ISLAND.expectedOutput);
                inputLayer.setInitialInputs(island);
                forward();
                backpropagate();
                islandError = outputLayer.getMeanCost();
                // Smiley
                outputLayer.setExpectedOutputs(FileType.SMILEY.expectedOutput);
                inputLayer.setInitialInputs(smiley);
                forward();
                backpropagate();
                smileyError = outputLayer.getMeanCost();
            }
            // Overall mean error
            errorRate = (bulleyesError + islandError + smileyError) / 3;
            meanCosts.add(errorRate);
            LOGGER.info("Epoch " + currentEpoch + ":\t" + errorRate);
        }
    }

    /**
     * Private helper that calculate the overall mean error of the neural
     * network. This error represents the error of all data sets given to the
     * neural network.
     *
     * @param errors an array of doubles representing the errors of the file
     *               types
     * @return the overall mean error of the neural network
     * @throws NullPointerException if errors is null
     * @require errors != null
     * @ensure the overall mean error of the neural network
     */
    private double calculateOverallMeanError(double... errors) {
        if (errors == null) {
            throw new NullPointerException("Null errors");
        }
        double result = 0;
        for (double error : errors) {
            result += Math.pow(error, 2);
        }
        return result / errors.length;
    }

    /**
     * Querying for output from the neural network.
     *
     * @param source the input into the neural network
     * @return the output from the neural network
     * @throws NullPointerException  if source is null
     * @throws FileNotFoundException if source is invalid
     * @require source != null && source.exists()
     * @ensure the output from the neural network
     */
    public synchronized Matrix query(File source) throws FileNotFoundException {
        if (source == null) {
            throw new NullPointerException("Null input");
        }
        if (!source.exists()) {
            throw new FileNotFoundException("Non-existing file");
        }
        return query(new FileInputStream(source));
    }

    /**
     * Querying for output from the neural network.
     *
     * @param source the input into the neural network
     * @return the output from the neural network
     * @throws NullPointerException if source is null
     * @require source != null
     * @ensure the output from the neural network
     */
    public synchronized Matrix query(InputStream source) {
        if (source == null) {
            throw new NullPointerException("Null input");
        }
        DataSet dataSet = new DataSet(source);
        Matrix before = new Matrix(dataSet.getAxialCurvature().get(0));
        Matrix after = new Matrix(dataSet.getAxialCurvature().get(1));
        inputLayer.setInitialInputs(Matrix.subtract(after, before).normalise());
        return outputLayer.calculateAndGetOutputVector();
    }

    /**
     * Querying for output from the neural network.
     *
     * @param source the input into the neural network
     * @return the file type of the input
     * @throws NullPointerException  if source is null
     * @throws FileNotFoundException if source is invalid
     * @require source != null && source.exists()
     * @ensure the file type of the input
     */
    public synchronized FileType queryType(File source) throws FileNotFoundException {
        if (source == null) {
            throw new NullPointerException("Null input");
        }
        if (!source.exists()) {
            throw new FileNotFoundException("Non-existing file");
        }
        return queryType(new FileInputStream(source));
    }

    /**
     * Query for output from the neural network.
     *
     * @param source the input into the neural network
     * @return the file type of the input
     * @throws NullPointerException if source is null
     * @require source != null
     * @ensure the file type of the input
     */
    public synchronized FileType queryType(InputStream source) {
        if (source == null) {
            throw new NullPointerException("Null input");
        }
        Matrix result = query(source);

        double a = result.getValueAt(0);
        double b = result.getValueAt(1);
        double c = result.getValueAt(2);
        if (a >= 0.5 && b < 0.5 && c < 0.5) {
            return FileType.BULLEYES;
        } else if (a < 0.5 && b >= 0.5 && c < 0.5) {
            return FileType.ISLAND;
        } else if (a < 0.5 && b < 0.5 && c >= 0.5) {
            return FileType.SMILEY;
        } else {
            return FileType.UNKNOWN;
        }
    }

    /**
     * Forward through the neural network.
     */
    private void forward() {
        // Output layer will produce output vector that is based on previous
        // layer's input, which in turns is based on the next previous layer's
        // input, and so on.
        outputLayer.calculateOutputVector();
    }

    /**
     * Backpropagate the errors back through the neural network.
     */
    private void backpropagate() {
        if (hiddenLayers.length > 0) {
            // Starting from the first hidden layer
            hiddenLayers[0].calculatePropagationErrorVector();
        } else {
            outputLayer.calculatePropagationErrorVector();
        }
        // Add all weight update matrices to the array
        for (int i = 0; i < hiddenLayers.length; i++) {
            Matrix updateWeightChange = hiddenLayers[i].getCostWRTWeightMatrix();
            updateWeightChange.multiplyAllElementsWith(LEARNING_RATE);
            hiddenWeightUpdate[i] = updateWeightChange;
        }
        // Add all bias update matrices to the array
        for (int i = 0; i < hiddenLayers.length; i++) {
            Matrix updateBiasChange = hiddenLayers[i].getCostWRTBiasVector();
            updateBiasChange.multiplyAllElementsWith(LEARNING_RATE);
            hiddenBiasUpdate[i] = updateBiasChange;
        }
        // Retrieve the weight update matrix from the output layer
        Matrix updateWeightChange = outputLayer.getCostWRTWeightMatrix();
        updateWeightChange.multiplyAllElementsWith(LEARNING_RATE);
        // Retrieve biases
        Matrix updateBiasChange = outputLayer.getCostWRTBiasVector();
        updateBiasChange.multiplyAllElementsWith(LEARNING_RATE);
        // Update weights and biases
        for (int i = 0; i < hiddenLayers.length; i++) {
            hiddenLayers[i].updateWeightMatrix(hiddenWeightUpdate[i]);
            hiddenLayers[i].updateBiasVector(hiddenBiasUpdate[i]);
        }
        outputLayer.updateWeightMatrix(updateWeightChange);
        outputLayer.updateBiasVector(updateBiasChange);
    }

    /**
     * Return the max epoch of this neural network.
     *
     * @return the max epoch of this neural network.
     */
    public int getMaxEpoch() {
        return maxEpoch;
    }

    /**
     * Get the number of hidden layer in this neural network.
     *
     * @return the number of hidden layer in this neural network.
     */
    public int getNumberOfHiddenLayers() {
        return hiddenLayers.length;
    }

    /**
     * Return the array of mean costs over the training of the neural net.
     *
     * @return the array of mean costs over the training of the neural net
     */
    public List<Double> getMeanErrors() {
        return meanCosts;
    }

    /**
     * Get time in seconds taken to train this neural network.
     *
     * @return time in seconds taken to train this neural network
     */
    public double getTime() {
        return time;
    }

    /**
     * Get error rate of the neural network.
     *
     * @return error rate of the neural network.
     */
    public double getErrorRate() {
        return errorRate;
    }

    /**
     * Return a list of weight matrices of all layers in this neural network.
     *
     * @return a list of weight matrices of all layers in this neural network.
     */
    public List<Matrix> getWeightMatrices() {
        List<Matrix> matrices = new ArrayList<>();
        for (Layer hiddenLayer : hiddenLayers) {
            matrices.add(hiddenLayer.calculateAndGetWeightMatrix());
        }
        matrices.add(outputLayer.calculateAndGetWeightMatrix());
        return matrices;
    }

    /**
     * Dissolve the neural network.
     */
    public void dissolve() {
        inputLayer.dissolve();
        for (HiddenLayer hiddenLayer : hiddenLayers) {
            hiddenLayer.dissolve();
        }
        outputLayer.dissolve();
        inputLayer = null;
        for (HiddenLayer hiddenLayer : hiddenLayers) {
            hiddenLayer = null;
        }
        hiddenLayers = null;
        outputLayer = null;
        hiddenWeightUpdate = null;
        meanCosts = null;
    }

    public HiddenLayer[] getHiddenLayers() {
        return this.hiddenLayers;
    }

    public OutputLayer getOutputLayer() {
        return this.outputLayer;
    }

    public InputLayer getInputLayer() {
        return this.inputLayer;
    }
}

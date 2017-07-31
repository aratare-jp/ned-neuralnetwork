package com.deathbydeco.network;

import com.deathbydeco.utility.DataSet;
import com.deathbydeco.utility.Matrix;
import com.deathbydeco.utility.TrainingSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rextruong on 2017/06/01.
 * <p>
 * This class is used to manage two neural networks. One for the bulleyes and the other for the other two types of
 * image.
 */
public class NeuralNetworkController {

    private static Logger LOGGER = LoggerFactory.getLogger(NeuralNetworkController.class);

    private NeuralNetwork bulleyesNN;
    private NeuralNetwork otherNN;

    public NeuralNetworkController(int inA, int[] hiddenA, int outA, int epochA,
                                   int inB, int[] hiddenB, int outB, int epochB) {
        bulleyesNN = new NeuralNetwork(inA, hiddenA, outA, epochA);
        otherNN = new NeuralNetwork(inB, hiddenB, outB, epochB);
    }

    public void training() {
        LOGGER.info("Start training the bulleye neural network...");
        // Time before the training starts.
        double timeBeforeTraining = System.currentTimeMillis();
        try {
            LOGGER.info("Retrieving training set...");
            File directoryBulleyes = new File("src/resources/datasets/bulleyes/left");
            File directoryIsland = new File("src/resources/datasets/Central Island/left");
            File directorySmiley = new File("src/resources/datasets/Smiley Face/left");
            // Retrieve all child files
            File[] bulleyes = directoryBulleyes.listFiles();
            File[] island = directoryIsland.listFiles();
            File[] smiley = directorySmiley.listFiles();
            if (bulleyes == null || island == null || smiley == null) {
                throw new NullPointerException("Cannot find files in directory.");
            }
            List<Matrix> bulleyesList = trainingHelper(bulleyes);
            List<Matrix> islandList = trainingHelper(island);
            List<Matrix> smileyList = trainingHelper(smiley);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // Time to train the neural network.
        double timeAfterTraining = System.currentTimeMillis();
        double time = (timeAfterTraining - timeBeforeTraining) / 1000.0;
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
}

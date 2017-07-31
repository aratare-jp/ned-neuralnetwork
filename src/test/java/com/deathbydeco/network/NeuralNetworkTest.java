package com.deathbydeco.network;

import com.deathbydeco.network.neuron.Neuron;
import com.deathbydeco.utility.DataSet;
import com.deathbydeco.utility.Matrix;
import com.deathbydeco.utility.Randomiser;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.AssertTrue;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static com.deathbydeco.network.NeuralNetwork.FileType;

/**
 * Created by rex on 2017/04/23.
 * <p>
 * This class is used to test out functionality of the neural network.
 */
public class NeuralNetworkTest {

    private static Logger LOGGER = LoggerFactory.getLogger(NeuralNetworkTest.class);

//    /**
//     * Test a neural network with no hidden layer.
//     */
//    @Test
//    public void testSimpleNeuralNetWithNoHiddenLayer() {
//        int numberOfInputNeurons = 2;
//        int numberOfOutputNeurons = 1;
//        int[] numberOfHiddenNeurons = {};
//        int numberOfHiddenLayers = 0;
//        int maxEpoch = 500;
//
//        // Create a simple neural net with 2 input and 1 output neurons.
//        NeuralNetwork neuralNet = new NeuralNetwork(numberOfInputNeurons,
//                numberOfHiddenNeurons,
//                numberOfHiddenLayers,
//                numberOfOutputNeurons,
//                maxEpoch);
//
//        // Input into the neural net
//        Matrix input = new Matrix(2);
//        input.setValueAt(0, 0.95);
//        input.setValueAt(1, 0.11);
//        List<Matrix> inputs = new ArrayList<>();
//        inputs.add(input);
//
//        // Expected outputs from the neural net
//        Matrix expectedOutput = new Matrix(1);
//        expectedOutput.setValueAt(0, 0.5);
//
//        // Train the neural net
//        neuralNet.run(inputs, expectedOutput);
//    }
//
//    /**
//     * Test a neural network with one hidden layer.
//     */
//    @Test
//    public void testNeuralNetworkWithOneHiddenLayer() {
//        int numberOfInputNeurons = 3;
//        int numberOfOutputNeurons = 4;
//        int[] numberOfHiddenNeurons = {2};
//        int numberOfHiddenLayers = 1;
//        int maxEpoch = 1000;
//
//        // Create a simple neural net with 2 input and 1 output neurons.
//        NeuralNetwork neuralNet = new NeuralNetwork(numberOfInputNeurons,
//                numberOfHiddenNeurons,
//                numberOfHiddenLayers,
//                numberOfOutputNeurons,
//                maxEpoch);
//
//        // Input into the neural net
//        Matrix input = new Matrix(numberOfInputNeurons);
//        for (int i = 0; i < input.getRows(); i++) {
//            input.setValueAt(i, Randomiser.randomise());
//        }
//        List<Matrix> inputs = new ArrayList<>();
//        inputs.add(input);
//
//        // Expected outputs from the neural net
//        Matrix expectedOutput = new Matrix(numberOfOutputNeurons);
//        for (int i = 0; i < expectedOutput.getRows(); i++) {
//            expectedOutput.setValueAt(i, Randomiser.randomise());
//        }
//
//        // Train the neural net
//        neuralNet.run(inputs, expectedOutput);
//
//        // Check if cost rose during the training
//        double[] meanCosts = neuralNet.getMeanErrors();
//        for (int i = 0; i < meanCosts.length - 1; i++) {
//            if (meanCosts[i] < meanCosts[i + 1]) {
//                fail("Mean cost has risen during the training phase");
//            }
//        }
//    }
//
//    @Test
//    public void testingMultipleHiddenLayersWithRealDataSet() {
//        int numberOfInputNeurons = 2500;
//        int numberOfHiddenLayers = 2;
//        int[] numberOfHiddenNeurons = {500, 500};
//        int numberOfOutputNeurons = 6;
//        int maxEpoch = 500;
//
//        // Create a simple neural net with 2 input and 1 output neurons.
//        NeuralNetwork neuralNet = new NeuralNetwork(numberOfInputNeurons,
//                numberOfHiddenNeurons,
//                numberOfHiddenLayers,
//                numberOfOutputNeurons,
//                maxEpoch);
//
//        URL mockInputURL = this.getClass().getResource("/datasets/bulleyes/Bullseye1.muf");
//        try {
//            // Get file
//            File mockInput = new File(mockInputURL.toURI());
//            assertTrue("File does not exist", mockInput.exists());
//
//            // Processing file
//            InputStream inputStream = new FileInputStream(mockInput);
//            DataSet dataSet = new DataSet(inputStream);
//
//            // First 50x50 image
//            double[][] firstCornealHeight = dataSet.getCornealHeight().get(0);
//            Matrix input = new Matrix(firstCornealHeight).getFlat().normalise();
//            List<Matrix> inputs = new ArrayList<>();
//            inputs.add(input);
//
//            // Expected outputs from the neural net
//            Matrix expectedOutput = ExpectedOutputFactory.getBulleyes();
//
//            // Train the neural net
//            neuralNet.run(inputs, expectedOutput);
//
//            // Check if cost rose during the training
//            double[] meanCosts = neuralNet.getMeanErrors();
//            for (int i = 0; i < meanCosts.length - 1; i++) {
//                if (meanCosts[i] < meanCosts[i + 1]) {
//                    fail("Mean cost has risen during the training phase");
//                }
//            }
//        } catch (URISyntaxException | FileNotFoundException e) {
//            fail(e.getMessage());
//        }
//    }

//    @Test
//    public void trainAndTestBulleyes() {
//        int numberOfInputNeurons = 2500;
//        int numberOfHiddenLayers = 2;
//        int[] numberOfHiddenNeurons = {500, 500};
//        int numberOfOutputNeurons = 6;
//        int maxEpoch = 500;
//
//        // Create a simple neural net with 2 input and 1 output neurons.
//        NeuralNetwork neuralNet = new NeuralNetwork(numberOfInputNeurons,
//                numberOfHiddenNeurons,
//                numberOfHiddenLayers,
//                numberOfOutputNeurons,
//                maxEpoch);
//
//        // Train the neural network first
//        neuralNet.training();
//
//        URL mockInputURL = this.getClass().getResource("/notfortraining/Bullseye3.muf");
//        try {
//            // Get file
//            File mockInput = new File(mockInputURL.toURI());
//            assertTrue("File does not exist", mockInput.exists());
//
//            // Train the neural net
//            Matrix output = neuralNet.query(mockInput);
//
//            System.out.println(output);
//
//            if (output.getValueAt(0) < 0.4) {
//                fail("Right class less than 40% certainty");
//            }
//            for (int i = 1; i < output.getRows(); i++) {
//                if (output.getValueAt(i) > 0.6) {
//                    fail("Wrong class more than 40% certainty");
//                }
//            }
//
//            // Check if cost rose during the training
//            double[] meanCosts = neuralNet.getMeanErrors();
//            for (int i = 0; i < meanCosts.length - 1; i++) {
//                if (meanCosts[i] < meanCosts[i + 1]) {
//                    fail("Mean cost has risen during the training phase");
//                }
//            }
//        } catch (URISyntaxException | FileNotFoundException e) {
//            fail(e.getMessage());
//        }
//    }
//
    
    public void trainAndTestIsland() {
        int numberOfInputNeurons = 128;
        int numberOfHiddenLayers = 1;
        int[] numberOfHiddenNeurons = {10};
        int numberOfOutputNeurons = 3;
        int maxEpoch = 50000;

        // Create a simple neural net with 2 input and 1 output neurons.
        NeuralNetwork neuralNet = new NeuralNetwork(numberOfInputNeurons,
                numberOfHiddenNeurons,
                numberOfOutputNeurons,
                maxEpoch);

        // Train the neural network first
        neuralNet.training();

        URL bel5 = this.getClass().getResource("/notfortraining/BE_L5.muf");
        URL bel6 = this.getClass().getResource("/notfortraining/BE_L6.muf");
        URL cil5 = this.getClass().getResource("/notfortraining/CI_L5.muf");
        URL cil6 = this.getClass().getResource("/notfortraining/CI_L6.muf");
        URL sfl5 = this.getClass().getResource("/notfortraining/SF_L5.muf");
        URL sfl6 = this.getClass().getResource("/notfortraining/SF_L6.muf");
        URL be3 = this.getClass().getResource("/notfortraining/Bullseye3.muf");
        URL ci3 = this.getClass().getResource("/notfortraining/CentralIsland3.muf");
        URL sf3 = this.getClass().getResource("/notfortraining/Smiley3.muf");
        try {
            // Get file
            File bel5File = new File(bel5.toURI());
            File bel6File = new File(bel6.toURI());
            File cil5File = new File(cil5.toURI());
            File cil6File = new File(cil6.toURI());
            File sfl5File = new File(sfl5.toURI());
            File sfl6File = new File(sfl6.toURI());
            File be3File = new File(be3.toURI());
            File ci3File = new File(ci3.toURI());
            File sf3File = new File(sf3.toURI());
            assertTrue("File does not exist", bel5File.exists());
            assertTrue("File does not exist", bel6File.exists());
            assertTrue("File does not exist", cil5File.exists());
            assertTrue("File does not exist", cil6File.exists());
            assertTrue("File does not exist", sfl5File.exists());
            assertTrue("File does not exist", sfl6File.exists());
            assertTrue("File does not exist", be3File.exists());
            assertTrue("File does not exist", ci3File.exists());
            assertTrue("File does not exist", sf3File.exists());

            // Train the neural net
//            Matrix output1 = neuralNet.query(bel5File);
//            Matrix output2 = neuralNet.query(bel6File);
//            Matrix output3 = neuralNet.query(cil5File);
//            Matrix output4 = neuralNet.query(cil6File);
//            Matrix output5 = neuralNet.query(sfl5File);
//            Matrix output6 = neuralNet.query(sfl6File);
//            Matrix output7 = neuralNet.query(be3File);
//            Matrix output8 = neuralNet.query(ci3File);
//            Matrix output9 = neuralNet.query(sf3File);

            FileType output1 = neuralNet.queryType(bel5File);
            FileType output2 = neuralNet.queryType(bel6File);
            FileType output3 = neuralNet.queryType(cil5File);
            FileType output4 = neuralNet.queryType(cil6File);
            FileType output5 = neuralNet.queryType(sfl5File);
            FileType output6 = neuralNet.queryType(sfl6File);
            FileType output7 = neuralNet.queryType(be3File);
            FileType output8 = neuralNet.queryType(ci3File);
            FileType output9 = neuralNet.queryType(sf3File);

            System.out.println(output1);
            System.out.println(output2);
            System.out.println(output3);
            System.out.println(output4);
            System.out.println(output5);
            System.out.println(output6);
            System.out.println(output7);
            System.out.println(output8);
            System.out.println(output9);

        } catch (URISyntaxException | FileNotFoundException e) {
            fail(e.getMessage());
        } finally {
            neuralNet.dissolve();
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void setBadNeuronTest() {
        Neuron neuron1 = new Neuron(0);
    }

    @Test(expected = NullPointerException.class)
    public void setNullWeightTest() {
        Neuron neuron1 = new Neuron(1);
        double[] error = null;
        neuron1.setWeights(error);
        System.out.println(neuron1.getWeights()[0]);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setIllegalWeightTest() {
        Neuron neuron1 = new Neuron(1);
        double[] length;
        length = new double[]{0.4};
        neuron1.setWeights(length);
        System.out.println(neuron1.getWeights()[0]);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setIllegalWeightTest2() {
        Neuron neuron1 = new Neuron(4);
        double[] length;
        length = new double[]{0.4, 0.3, 0.8, 0.5, 0.7, 0.3};
        neuron1.setWeights(length);
        System.out.println(neuron1.getWeights()[0]);
    }

    @Test
    public void setCorrectWeightTest() {
        Neuron neuron1 = new Neuron(1);
        double[] length;
        length = new double[]{0.4, 0.2};
        neuron1.setWeights(length);
        assertTrue(neuron1.getWeights() == length);
    }

    @Test
    public void setCorrectWeightTest2() {
        Neuron neuron1 = new Neuron(2);
        double[] length;
        length = new double[]{0.4, 0.3, 0.8};
        neuron1.setWeights(length);
        assertTrue(neuron1.getWeights() == length);
    }
}

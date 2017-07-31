package com.deathbydeco;

import be.tomcools.dropwizard.websocket.WebsocketBundle;
import com.deathbydeco.network.ExpectedOutputFactory;
import com.deathbydeco.network.NeuralNetwork;
import com.deathbydeco.network.layer.HiddenLayer;
import com.deathbydeco.network.layer.InputLayer;
import com.deathbydeco.network.layer.OutputLayer;
import com.deathbydeco.server.MainConfiguration;
import com.deathbydeco.server.realtime.RealTimeWebSocket;
import com.deathbydeco.server.resources.DatasetResource;
import com.deathbydeco.utility.DataSet;
import com.deathbydeco.utility.Matrix;
import com.google.common.graph.Network;
import io.dropwizard.Application;
import io.dropwizard.forms.MultiPartBundle;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.apache.log4j.Logger;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.skife.jdbi.v2.DBI;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;

import com.deathbydeco.server.dao.MyDAO;
import org.skife.jdbi.v2.Handle;


/**
 * Created by rex on 2017/03/16.
 * <p>
 * This class is the entrance to the program.
 */
public class MainClass extends Application<MainConfiguration> {
    static final private int numberOfInputNeurons = 128;
    static final private int[] numberOfHiddenNeurons = new int[]{10};
    static final private int numberOfHiddenLayers = 1;
    static final private int numberOfOutputNeurons = 3;
    static final private int maxEpoch = 20000;

    /**
     * Use singleton to have only one instance of the neural network running
     * for the life time of the server.
     */
    public static final NeuralNetwork NEURAL_NETWORK = new NeuralNetwork(numberOfInputNeurons,
            numberOfHiddenNeurons, numberOfOutputNeurons, maxEpoch);


    /**
     * Relative path to the datasets folder, where all uploaded file will be
     * saved.
     */
    public static final String DATASETS_BASE_PATH = "src" +
            File.separator + "resources" + File.separator + "datasets";

    /**
     * Logger for the class.
     */
    private static Logger LOGGER = Logger.getLogger(MainClass.class);

    /**
     * Web websocketBundle bundle
     */
    private WebsocketBundle<MainConfiguration> websocketBundle = new WebsocketBundle<>();

    /**
     * Entry point for the program.
     *
     * @param args arguments passed from the command line.
     * @throws Exception throw if error.
     */
    public static void main(String[] args) throws Exception {

        //get from database the layers and neuron counts

        //check if the same as what as set

        // Train the neural network
        NEURAL_NETWORK.training();
        //
        MainClass mainClass = new MainClass();
        // Create logging file
        mainClass.createLogFile();
        // Start the server
        mainClass.run(args);
    }

    /**
     * Simple helper function to create a logging file.
     *
     * @throws IOException if logging file cannot be created
     */
    private void createLogFile() throws IOException {
        // Create new log file.
        File loggingFile = new File("server.log");
        // Clear the content.
        PrintWriter writer = new PrintWriter(loggingFile);
        writer.print("");
        writer.close();
    }

    /**
     * Return the name of the application.
     *
     * @return the name of the application.
     */
    @Override
    public String getName() {
        return "DeathByDeco Myopia";
    }

    @Override
    public void initialize(Bootstrap<MainConfiguration> bootstrap) {
        super.initialize(bootstrap);
        bootstrap.addBundle(new MultiPartBundle());
        bootstrap.addBundle(websocketBundle);
    }

    /**
     * Start the server.
     *
     * @param configuration server configuration.
     * @param environment   server environment.
     * @throws Exception throw if error.
     */
    @Override
    public void run(MainConfiguration configuration, Environment environment) throws Exception {
        // Web socket
        websocketBundle.addEndpoint(RealTimeWebSocket.class);

        // Allow cross origin
        FilterRegistration.Dynamic corsFilter = environment.servlets().addFilter("CORS",
                CrossOriginFilter.class);
        corsFilter.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM,
                "GET,PUT,POST,DELETE,OPTIONS");
        corsFilter.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "*");
        corsFilter.setInitParameter(CrossOriginFilter.ALLOWED_HEADERS_PARAM,
                "Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin");
        corsFilter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");

        // Database
        DBIFactory dbiFactory = new DBIFactory();
        DBI dbi = dbiFactory.build(environment,
                configuration.getDataSourceFactory(),
                "mysql");

        // Put in dao here.
        final MyDAO database = dbi.onDemand(MyDAO.class);

        //Get from database
        List<Matrix> network = NEURAL_NETWORK.getWeightMatrices();
        //Clear the database
        database.clearLink();
        database.clearNeuron();
        database.clearLayer();


        //Get layer loop
        int layers = NEURAL_NETWORK.getNumberOfHiddenLayers();
        //System.out.println("Hidden Layers: " + layers);
        //Get the layers here in a list\
        //+2 for input and output layer
        layers += 2;
        //System.out.println("Layers: " + layers);
        for (int i = 0; i < (layers); i++) {
            //System.out.println("Loop:" + i);
            //for input layer
            if (i == 0) {
                InputLayer layer = NEURAL_NETWORK.getInputLayer();
                int neuronCount = layer.getNumberOfNeurons();
                database.insertLayer(0, neuronCount);
                //System.out.println("Input Layer,Neuron Count:" + neuronCount);
                //create neurons
                for (int j = 0; j < neuronCount; j++) {

                    double bias = layer.getNeurons()[j].getBias();
                    database.insertNeuron(j, 0, bias);
                }

            } else if (i == (layers - 1)) {
                OutputLayer layer = NEURAL_NETWORK.getOutputLayer();
                int neuronCount = layer.getNumberOfNeurons();
                //System.out.println("Output Layer,Neuron Count:" + neuronCount);
                database.insertLayer(layers - 1, neuronCount);
                for (int j = 0; j < neuronCount; j++) {
                    double bias = layer.getNeurons()[j].getBias();
                    database.insertNeuron(j, layers - 1, bias);
                }
                //System.out.println("Inserted Neurons");
                //Create links
                //rows is neurons of this layer
                int rows = network.get(i - 1).getRows();
                //comlums is neurons of the previous layer
                int columns = network.get(i - 1).getColumns();
                //insert link
                for (int j = 0; j < rows; j++) {
                    for (int k = 0; k < columns; k++) {
                        double value = network.get(i - 1).getValueAt(j, k);
                        //System.out.println("Weight:" + value);
                        database.insertLink(value, k, j, i);
                    }
                }
            } else {
                HiddenLayer layer = NEURAL_NETWORK.getHiddenLayers()[i - 1];
                int neuronCount = layer.getNumberOfNeurons();
                //System.out.println("Hidden Layer,Neuron Count:" + neuronCount);
                database.insertLayer(i, neuronCount);
                for (int j = 0; j < neuronCount; j++) {
                    double bias = layer.getNeurons()[j].getBias();
                    database.insertNeuron(j, i, bias);
                }
                //Create links
                //rows is neurons of this layer
                int rows = network.get(i - 1).getRows();
                //comlums is neurons of the previous layer
                int columns = network.get(i - 1).getColumns();
                //insert link
                for (int j = 0; j < rows; j++) {
                    for (int k = 0; k < columns; k++) {
                        double value = network.get(i - 1).getValueAt(j, k);
                        //System.out.println("Weight:" + value);
                        database.insertLink(value, k, j, i);
                    }

                }
            }


        }


        // Resources


        // Heath checks

        // Add resources and health-checks to the server
        environment.jersey().register(new DatasetResource());
    }
    /**
     *
     * Checks if the structure of the neural network has been changed
     *
     * Clears the database if it has been changed
     *
     */
    /*
    private void structureChanged () {
        if (numberOfHiddenLayers != dataBaseHiddenLayers) ||
        (numberOfHiddenNeurons != dataBaseHiddenNeurons) ||
                (numberOfInputNeurons != dataBaseInputNeurons) ||
                (numberOfOutputNeurons != dataBaseOutputNeurons) {
            MyDAO.clearLayer();
        }
        return false;


    }
    */
}

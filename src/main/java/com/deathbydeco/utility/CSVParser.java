package com.deathbydeco.utility;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Tony on 27/04/2017.
 */
public class CSVParser {



    public static ArrayList<ArrayList<double[]>> run() throws URISyntaxException {

        //Allocate variable names for parsing and pushing too
        ArrayList<double[]> firstDataSet = new ArrayList<>();
        ArrayList<double[]> secondDataSet = new ArrayList<>();
        ArrayList<double[]> thirdDataSet = new ArrayList<>();
        ArrayList<double[]> fourthDataSet = new ArrayList<>();
        ArrayList<double[]> fifthDataSet = new ArrayList<>();
        ArrayList<ArrayList<double[]>> finalSet = new ArrayList<>();
        double[] inputSet = new double[2500];
        double[] insertSet = new double[2500];

        URL url = CSVParser.class.getResource("/datasets/file.csv");
        File file = new File(url.toURI());
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        scanner.useDelimiter(",|\n");
        int i = 0;
        int j = 0;
        while (scanner.hasNext()){

            while (i < 2500){
                inputSet[i] = Double.parseDouble(scanner.next());
                i++;
            }
            if (i >= 2500) {
                String token = scanner.next();
                //Allocate the 2500 long data set based on the final 5 data cells of the CSV row
                if (Double.parseDouble(token) == 1) {
                    switch (i - 2500) {
                        case 0:
                            insertSet = inputSet;
                            firstDataSet.add(insertSet);
                            i++;
                            break;
                        case 1:
                            insertSet = inputSet;
                            secondDataSet.add(insertSet);
                            i++;
                            break;
                        case 2:
                            insertSet = inputSet;
                            thirdDataSet.add(insertSet);
                            i++;
                            break;
                        case 3:
                            insertSet = inputSet;
                            fourthDataSet.add(insertSet);
                            i++;
                            break;
                        case 4:
                            insertSet = inputSet;
                            fifthDataSet.add(insertSet);
                            i = 0;
                            break;
                        default:
                            i = 0;
                            break;
                    }
                //Reset i counter since the last cell on the row has been processed
                } else if (i == 2504) {
                    i = 0;
                //Last i counter in the row has not been encountered, iterate further
                } else {
                    i++;
                }
            }
        }
        scanner.close();

        //compile the final stack of data sets and return
        finalSet.add(firstDataSet);
        finalSet.add(secondDataSet);
        finalSet.add(thirdDataSet);
        finalSet.add(fourthDataSet);
        finalSet.add(fifthDataSet);
        return finalSet;
    }



}

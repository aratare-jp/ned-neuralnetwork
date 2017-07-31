package com.deathbydeco.utility;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Tony on 16/03/2017.
 */
public class XMLParser {

    public static ArrayList<double[][]> run(InputStream file) {
        ArrayList<String> dataTypes = new ArrayList<>();
        dataTypes.add("CornealHeight");
        dataTypes.add("TangentialCurvature");
        dataTypes.add("AxialCurvature");


        ArrayList<double[][]> finalDataSet = new ArrayList<>();
        try {

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();

            //Devise a node list based on the specific datasets needed
            for (String dataType: dataTypes) {
                NodeList nodeList = doc.getElementsByTagName(dataType);


                // Here we iterate through both instances of the chosen tag in the XML doc
                for (int temp = 0; temp < nodeList.getLength(); temp++) {

                    Node cornNode = nodeList.item(temp);
                    NodeList childNodes = cornNode.getChildNodes();
                    Node chosenNode;
                    for (int y = 0; y < childNodes.getLength(); y++) {
                        if (childNodes.item(y).getNodeName() == "Data") {
                            chosenNode = childNodes.item(y);
                            //System.out.println("Here's the data: " + chosenNode.getTextContent());
                            String dataFull = chosenNode.getTextContent().replace("\t", "");
                            String[] dataLines = dataFull.split("\n");
                            //System.out.println(dataLines.length);

                            //System.out.println(dataLines[50]);
                            double[][] dataMap = new double[50][50];
                            int stripCounter = 0;
                            for (String dataStrip : dataLines) {

                                String[] dataLine = dataStrip.split(" ");

                                int pointCounter = 0;

                                for (String dataPoint : dataLine) {
                                    if (!(dataPoint.isEmpty())) {
                                        dataMap[stripCounter][pointCounter] = Double.parseDouble(dataPoint);
                                    } else {
                                        pointCounter--;
                                        stripCounter--;
                                    }

                                    pointCounter++;
                                }
                                stripCounter++;
                            }
                            finalDataSet.add(dataMap);
                        }

                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return finalDataSet;
    }
    public static double[][] reduceSize(double[][] fullSet){
        double[][] leanSet = new double[34][34];
        int xCounter = 0;
        int yCounter = 0;
        for (int y=8; y<42; y++){
            for (int x=8; x<42; x++){
                leanSet[yCounter][xCounter] = fullSet[y][x];
                xCounter++;
            }
            xCounter = 0;
            yCounter++;
        }
        return leanSet;
    }

    public static double[][] averageReduce(double[][] largeSet){
        double[][] downsizedSet = new double[17][17];
        int xCounter = 0;
        int yCounter = 0;
        for (int y = 0; y < 34; y+=2){
            for (int x = 0; x < 34; x+= 2){
                double topAverage = (largeSet[y][x] + largeSet[y][x+1])/2;
                double botAverage = (largeSet[y+1][x] + largeSet[y+1][x+1])/2;

                downsizedSet[yCounter][xCounter] = (topAverage + botAverage)/2;
                xCounter ++;
            }
            yCounter++;
            xCounter = 0;
        }
        return downsizedSet;
    }

    public static ArrayList<ArrayList<Double>> downSizedRun(InputStream file){
        //Shaves off the outer third of every 2D array extracted by run()

        ArrayList<ArrayList<Double>> reducedDataSet = new ArrayList<>();
        ArrayList<double[][]> fullDataSet = run(file);
        for (double[][] fullSet: fullDataSet){
            reducedDataSet.add(radialDownSize(fullSet));
        }

        return reducedDataSet;
    }

    public static ArrayList<Double> radialDownSize(double[][] fullSet){

        ArrayList<Double> reducedSet = new ArrayList<>();
        double subSubSum = 0;
        int r = 0;
        ArrayList<ArrayList<ArrayList<Double>>> octalList = new ArrayList<>();
        while (r < 16){
            octalList.add(new ArrayList<ArrayList<Double>>());
            octalList.get(r).add(new ArrayList<Double>());
            octalList.get(r).add(new ArrayList<Double>());
            octalList.get(r).add(new ArrayList<Double>());
            octalList.get(r).add(new ArrayList<Double>());
            octalList.get(r).add(new ArrayList<Double>());
            octalList.get(r).add(new ArrayList<Double>());
            octalList.get(r).add(new ArrayList<Double>());
            octalList.get(r).add(new ArrayList<Double>());

            for (int i = 0; i<50;i++){
                for (int j = 0; j<50; j++){

                    if (Math.pow((Math.pow((24.5-i),2)+Math.pow((24.5-j),2)), 0.5) >=r &&
                            (Math.pow((Math.pow((24.5-i),2)+Math.pow((24.5-j),2)),0.5))<r+1){
                        if (fullSet[i][j] != -5e+20){
                            if (i < 24.5 && j< 24.5){
                                if (Math.pow(i-24.5, 2) < Math.pow(j-24.5, 2)) {
                                    octalList.get(r).get(0).add(fullSet[i][j]);
                                }

                                else if (Math.pow(i-24.5, 2) > (Math.pow(j-24.5, 2))){
                                    octalList.get(r).get(1).add(fullSet[i][j]);
                                } else {
                                    octalList.get(r).get(0).add(fullSet[i][j]);
                                    octalList.get(r).get(1).add(fullSet[i][j]);
                                }
                            }
                            if (i>= 24.5 && j< 24.5){
                                if (Math.pow(i-24.5, 2) < Math.pow(j-24.5, 2)){
                                    octalList.get(r).get(2).add(fullSet[i][j]);
                                }
                                else if (Math.pow(i-24.5, 2) > Math.pow(j-24.5, 2)){
                                    octalList.get(r).get(3).add(fullSet[i][j]);
                                } else{
                                    octalList.get(r).get(2).add(fullSet[i][j]);
                                    octalList.get(r).get(3).add(fullSet[i][j]);
                                }
                            }
                            if (i >= 24.5 && j>= 24.5){
                                if (Math.pow(i-24.5, 2) < Math.pow(j-24.5, 2)){
                                    octalList.get(r).get(4).add(fullSet[i][j]);
                                }

                                else if (Math.pow(i-24.5, 2) > Math.pow(j-24.5, 2)){
                                    octalList.get(r).get(5).add(fullSet[i][j]);
                                } else{
                                    octalList.get(r).get(4).add(fullSet[i][j]);
                                    octalList.get(r).get(5).add(fullSet[i][j]);
                                }
                            }

                            if (i < 24.5 && j>= 24.5){
                                if (Math.pow(i-24.5, 2) < Math.pow(j-24.5, 2)){
                                    octalList.get(r).get(6).add(fullSet[i][j]);
                                }
                                else if ( Math.pow(i-24.5, 2) > Math.pow(j-24.5, 2)){
                                    octalList.get(r).get(7).add(fullSet[i][j]);
                                } else{
                                    octalList.get(r).get(6).add(fullSet[i][j]);
                                    octalList.get(r).get(7).add(fullSet[i][j]);
                                }
                            }
                        }





                    }
                }
            }
            r++;
        }
        for (ArrayList<ArrayList<Double>> subList: octalList){
            for (ArrayList<Double> subSubList : subList){
                for (double value: subSubList){
                    subSubSum += value;
                }
                subSubSum = subSubSum / subSubList.size();
                reducedSet.add(subSubSum);
                subSubSum = 0;
            }

        }

        return reducedSet;
    }

}

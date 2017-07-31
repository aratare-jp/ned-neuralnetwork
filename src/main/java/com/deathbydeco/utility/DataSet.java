package com.deathbydeco.utility;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

// Created by Harry 23/03/17
public class DataSet {

    private String name;
    private String folder;
    private ArrayList<ArrayList<Double>> CornealHeight = new ArrayList<>();
    private ArrayList<ArrayList<Double>> TangentialCurvature = new ArrayList<>();
    private ArrayList<ArrayList<Double>> AxialCurvature = new ArrayList<>();

    // Doesn't matter what you enter in atm will just use path
    public DataSet(InputStream file){
        ArrayList<ArrayList<Double>> allHeightData = new ArrayList<>();
        allHeightData = XMLParser.downSizedRun(file);
        this.CornealHeight.add(allHeightData.get(0));
        this.CornealHeight.add(allHeightData.get(1));
        this.TangentialCurvature.add(allHeightData.get(2));
        this.TangentialCurvature.add(allHeightData.get(3));
        this.AxialCurvature.add(allHeightData.get(4));
        this.AxialCurvature.add(allHeightData.get(5));
    }


    public ArrayList<ArrayList<Double>> getCornealHeight (){
        return this.CornealHeight;
    }

    public ArrayList<ArrayList<Double>> getTangentialCurvature (){
        return this.TangentialCurvature;
    }

    public ArrayList<ArrayList<Double>> getAxialCurvature (){
        return this.AxialCurvature;
    }



}
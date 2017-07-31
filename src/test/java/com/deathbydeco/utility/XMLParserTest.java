package com.deathbydeco.utility;

import com.deathbydeco.utility.XMLParser;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.AssertTrue;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by Tony on 13/04/2017.
 */
public class XMLParserTest {

    private static Logger LOGGER = LoggerFactory.getLogger(DataSetTest.class);
    private ArrayList<double[][]> parseResult;
    private ArrayList<ArrayList<Double>> parsedResult = new ArrayList<>();

    @Test
    public void TestCorrectParse() {

        URL url = this.getClass().getResource("/datasets/Smiley3.muf");
        File file = null;
        try {
            file = new File(url.toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        InputStream testStream = null;
        try {
            testStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            System.out.println("InputStream not created");
            e.printStackTrace();
        }

        parseResult = XMLParser.run(testStream);
        assertTrue(parseResult.size() == 6);
        for (int x = 0; x < 6; x++) {
            assertTrue(parseResult.get(x).length == 50);
        }
        //double[][] testReducer;
        //testReducer = XMLParser.reduceSize(parseResult.get(0));

    }

    @Test
    public void TestReducedParse() {

        URL url = this.getClass().getResource("/datasets/Smiley3.muf");
        File file = null;
        try {
            file = new File(url.toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        InputStream testStream = null;
        try {
            testStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            System.out.println("InputStream not created");
            e.printStackTrace();
        }



        ArrayList<ArrayList<Double>> parseResult1 = XMLParser.downSizedRun(testStream);
        assertTrue(parseResult1.size() == 6);

    }

    @Test
    public void TestIncorrectParse1() {
        // Test reading an XML file missing the appropriate
        // CornealHeight|TangentialCurvature|AxialCurvature tags

        URL url = this.getClass().getResource("/datasets/InvalidTypeTags.muf");
        File file = null;
        try {
            file = new File(url.toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        InputStream testStream = null;
        try {
            testStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            System.out.println("InputStream not created");
            e.printStackTrace();
        }
        //parseResult.clear();
        parseResult = XMLParser.run(testStream);
        LOGGER.debug(parseResult.toString());
        assertEquals(0, parseResult.size());
    }

    @Test
    public void TestIncorrectParse2() {
        //Test reading an XML file missing the require Data Tags but contains
        //the correct CornealHeight|TangentialCurvature|AxialCurvature tags

        URL url = this.getClass().getResource("/datasets/InvalidDataTags.muf");
        File file = null;
        try {
            file = new File(url.toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        InputStream testStream = null;
        try {
            testStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            System.out.println("InputStream not created");
            e.printStackTrace();
        }

        parseResult = XMLParser.run(testStream);
        assertEquals(0, parseResult.size());
    }

    @Test
    public void TestIncorrectParse3() {
        //Test reading an XML file with invalid Data e.g. Strings instead of Doubles

        URL url = this.getClass().getResource("/datasets/InvalidDataContents.muf");
        File file = null;
        try {
            file = new File(url.toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        InputStream testStream = null;
        try {
            testStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            System.out.println("InputStream not created");
            e.printStackTrace();
        }

        try {
            parseResult = XMLParser.run(testStream);
            fail("Exception wasn't thrown");
        } catch (AssertionError e) {
            e.printStackTrace();
        }

    }

    @Test
    public void TestCircularDownSize() {
        URL url = this.getClass().getResource("/datasets/Smiley3.muf");
        File file = null;
        try {
            file = new File(url.toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        InputStream testStream = null;
        try {
            testStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            System.out.println("InputStream not created");
            e.printStackTrace();
        }

        parseResult = XMLParser.run(testStream);
        for (double[][] subList : parseResult) {
            parsedResult.add(XMLParser.radialDownSize(subList));
        }
        int t = 1;
    }


    @Test
    public void testCorrectReduceSize() {

        URL url = this.getClass().getResource("/datasets/Smiley3.muf");
        File file = null;
        try {
            file = new File(url.toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        InputStream testStream = null;
        try {
            testStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            System.out.println("InputStream not created");
            e.printStackTrace();
        }

        double[][] reducedResult;
        parseResult = XMLParser.run(testStream);
        reducedResult = XMLParser.reduceSize(parseResult.get(0));
        assertTrue(reducedResult.length == 34);

    }


}

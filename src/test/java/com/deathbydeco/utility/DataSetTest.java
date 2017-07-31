package com.deathbydeco.utility;

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

import static org.junit.Assert.assertTrue;

/**
 * Created by Tony on 10/04/2017.
 */
public class DataSetTest {

    private static Logger LOGGER = LoggerFactory.getLogger(DataSetTest.class);

    @Test
    public void TestErrors() {

        URL url = this.getClass().getResource("/datasets/Smiley3.muf");
        File file = null;
        try {
            file = new File(url.toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        LOGGER.debug("FILE FOUND: " + file.exists());
        InputStream testStream = null;
        try {
            testStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            System.out.println("InputStream not created");
            e.printStackTrace();
        }

        DataSet testData1 = new DataSet(testStream);
        assertTrue(testData1.getCornealHeight().size() == 2);
        assertTrue(testData1.getAxialCurvature().size() == 2);
        assertTrue(testData1.getTangentialCurvature().size() == 2);

    }


}

package com.deathbydeco.utility;

import com.deathbydeco.utility.CSVParser;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.junit.Assert.*;

import java.net.URISyntaxException;
import java.util.ArrayList;

/**
 * Created by Tony on 27/04/2017.
 */
public class CSVParserTest {

    private ArrayList<ArrayList<double[]>> parseResult;

    @Test
    public void testCSVParse(){
        try {
            parseResult = CSVParser.run();
        } catch (Exception e) {
            fail("URI syntax is wrong");
        }
        System.out.println(parseResult);
    }
}

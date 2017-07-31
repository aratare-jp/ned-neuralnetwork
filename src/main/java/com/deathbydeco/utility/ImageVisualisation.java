package com.deathbydeco.utility;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * Created by rextruong on 2017/05/14.
 */
public class ImageVisualisation {

    private static final double gcMax = 6;
    private static final double gcMin = -6;
    private static final double cMax = 255;
    private static final double cMin = 0;

//    public static void saveImage(File source) throws IOException {
//        if (source == null) {
//            throw new NullPointerException("Null file");
//        }
//        if (!source.exists()) {
//            throw new IllegalArgumentException("File not existing");
//        }
//
//        DataSet dataSet = new DataSet(new FileInputStream(source));
//        List<double[][]> acs = dataSet.getAxialCurvature();
//
//        double[][] acBefore = acs.get(0);
//
//        BufferedImage image = new BufferedImage(50, 50, BufferedImage.TYPE_BYTE_GRAY);
//        for (int i = 0; i < 50; i++) {
//            for (int j = 0; j < 50; j++) {
//                int converted = convert(acBefore[i][j]);
//                Color color = new Color(converted, converted, converted);
//                image.setRGB(j, i, color.getRGB());
//            }
//        }
//
//        File saveFile = new File("src/resources/visualisation/data1.jpg");
//        if (saveFile.exists()) {
//            saveFile.delete();
//        }
//        saveFile.createNewFile();
//        ImageIO.write(image, "jpg", saveFile);
//
//    }
//
//    private static int convert(double greyscale) {
//        return (int) ((255 * (greyscale)) / 12);
//    }

}

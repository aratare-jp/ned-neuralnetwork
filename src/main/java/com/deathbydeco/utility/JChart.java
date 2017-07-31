package com.deathbydeco.utility;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Harry on 4/3/2017.
 */


public class JChart {
    private JButton button1;
    private JPanel Main;
    private JPanel panel1;

    public static void chartSetup() {

        JFrame frame = new JFrame("JChart");
        frame.setContentPane(new JChart().Main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }


    public JChart() {
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultCategoryDataset barchartdata = new DefaultCategoryDataset();
                barchartdata.setValue(6, "Error", "10000");
                barchartdata.setValue(5, "Error", "20000");
                barchartdata.setValue(4, "Error", "30000");
                barchartdata.setValue(2, "Error", "40000");
                barchartdata.setValue(1, "Error", "50000");
                barchartdata.setValue(0.5, "Error", "75000");
                barchartdata.setValue(0.2, "Error", "100000");

                JFreeChart barchart = ChartFactory.createBarChart("Error Rate", "Iterations", "Error", barchartdata, PlotOrientation.VERTICAL,false,false,false);
                CategoryPlot plot = barchart.getCategoryPlot();
                plot.setRangeGridlinePaint(Color.black);


                ChartPanel chartPanel = new ChartPanel(barchart);
                panel1.removeAll();
                panel1.add(chartPanel, BorderLayout.CENTER);
                panel1.validate();




            }
        });
    }



}

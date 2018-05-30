package com.company;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class PlotFrame extends JFrame {
    HashMap<Integer, Double> hashMap;

    PlotFrame (HashMap<Integer, Double> hashMap) {
        this.hashMap = hashMap;
        setTitle("Plot");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(700, 700);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        PlotPanel plotPanel = new PlotPanel(hashMap);
        add(plotPanel);

        setVisible(true);
    }

}

package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class PlotPanel extends JPanel implements ActionListener {

    private final int VALUE = 13;
    private final int MAX_LENGTH = 650;
    private final int STEP_SIZE = 50;
    private final int VOLUME_SCALE = 50;

    Timer timer = new Timer(100, this);
    double press;
    HashMap<Integer, Double> hashMap;
    HashMap<Integer, Integer> idealMap = new HashMap<Integer, Integer>();
    private int pressScalePlot;
    private int pressScaleReal;
    private int constNumber;

    PlotPanel(HashMap<Integer, Double> hashMap) {
        int num;
        this.hashMap = hashMap;
        press = hashMap.get(650);
        num = (int) (Math.pow(10, calcValue((int) press)));
        pressScalePlot = num / VALUE;
        pressScaleReal = (int) (MAX_LENGTH * 100 / num);
        timer.start();
    }

    private int calcValue(int val) {
        int num = 1;
        int checkNum = 10;
        while (val / checkNum != 0) {
            checkNum *= 10;
            num++;
        }
        return (num + 2);
    }

    private void changePlotScalePressure() {
        this.pressScalePlot = pressScalePlot * 10;
        this.pressScaleReal = pressScaleReal / 10;
    }

    @Override
    public void paint(Graphics g2) {
        int yReal;
        int yTheor;

        super.paintComponent(g2);
        Graphics2D gr2d = (Graphics2D) g2;

        //рисуем фон
        gr2d.setColor(Color.WHITE);
        gr2d.fillRect(10, 10, 650, 650);

        gr2d.setColor(Color.BLACK);
        gr2d.setStroke(new BasicStroke(5.0f));
        // OY
        gr2d.drawLine(5, 10, 5, MAX_LENGTH + 10);
        // OX
        gr2d.drawLine(5, MAX_LENGTH + 15, MAX_LENGTH + 5, MAX_LENGTH + 15);
        gr2d.setStroke(new BasicStroke(1.0f));

        for (int iter = 0; iter <= VALUE; iter++) {

            //по x
            gr2d.setColor(Color.LIGHT_GRAY);
            gr2d.drawLine(10 + iter * STEP_SIZE, 10, 10 + iter * STEP_SIZE, MAX_LENGTH + 10);
            gr2d.setColor(Color.BLUE);
            gr2d.fillOval(10 + iter * STEP_SIZE, MAX_LENGTH + 7, 4, 4);
            gr2d.drawString(Integer.toString(iter * VOLUME_SCALE), 13 + iter * STEP_SIZE, MAX_LENGTH + 7);

            //по y
            if (iter != 0) {
                gr2d.setColor(Color.LIGHT_GRAY);
                gr2d.drawLine(10, 10 + iter * STEP_SIZE, MAX_LENGTH + 10, 10 + iter * STEP_SIZE);
                gr2d.setColor(Color.BLUE);
                gr2d.fillOval(10, 10 + iter * STEP_SIZE, 4, 4);
                gr2d.drawString(Integer.toString(iter * pressScalePlot), 13, MAX_LENGTH - iter * STEP_SIZE - 3);
            }
        }

        Set<Map.Entry<Integer, Double>> set = hashMap.entrySet();
        for (Map.Entry<Integer, Double> volPress : set) {
            yReal = (int) (MAX_LENGTH - volPress.getValue() * pressScaleReal - 2);
            yTheor = (int) (MAX_LENGTH - press * (Math.pow(MAX_LENGTH, (5.0 / 3)) / Math.pow(volPress.getKey(), (5.0 / 3))) * pressScaleReal - 2);
            if ((yReal - 12 > 0) && (yTheor - 12 > 0)) {
                gr2d.setColor(Color.GREEN);
                gr2d.fillOval(volPress.getKey() + 5, yReal, 4, 4);
                gr2d.setColor(Color.PINK);
                gr2d.fillOval(volPress.getKey() + 5, yTheor, 4, 4);
            } else {
                changePlotScalePressure();
            }
        }

    }

    public void update() {
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        update();
    }
}

package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class Panel extends JPanel implements ActionListener {
    public int x = 700;
    public int y = 51;

    boolean isInit = false;

    int speed = 10;
    int numberOfMolecules = 50;

    JLabel labelP, labelV;

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setNumberOfMolecules(int numberOfMolecules) {
        this.numberOfMolecules = numberOfMolecules;
    }

    Timer timer;
    MovePompThread pompThread;
    volatile MoleculesList moleculesList;
    public volatile Map<Integer, Double> valuePress = new HashMap<Integer, Double>();

    Panel(JLabel labelP, JLabel labelV) {
        pompThread = new MovePompThread(x, y);
        timer = new Timer (10, this);
        this.labelP = labelP;
        this.labelV = labelV;
    }

    void start() {
        if (!timer.isRunning()) {
            moleculesList = new MoleculesList(numberOfMolecules, x, y, speed);
            isInit = true;
            timer.start();
        }
        pompThread = new MovePompThread(x, y);
        pompThread.start();
        if (!moleculesList.isAlive()) {
            moleculesList.start();
        }
    }

    void stop () throws InterruptedException {
        x = pompThread.getX();
        pompThread.flag = false;
        pompThread.interrupt();
    }

    @Override
    public void paint(Graphics g) {
        super.paintComponent(g);
        Graphics2D gr2d = (Graphics2D) g;

        gr2d.setColor(Color.BLACK);
        gr2d.drawLine(50, 50, 700, 50);
        gr2d.drawLine(50, 50, 50, 550);
        gr2d.drawLine(50, 550, 700, 550);

        gr2d.drawLine(50, 570, 700, 570);
        for (int j = 0; j < 700; j = j + 50) {
            gr2d.setColor(Color.cyan);
            gr2d.fillOval(47 + j, 567, 6, 6);
            gr2d.setColor(Color.BLACK);
            gr2d.drawString(Integer.toString(j), 50 + j, 590);
        }

        gr2d.setColor(Color.BLUE);
        x = pompThread.getX();
        gr2d.fillRect(x, 51, 50, 499);

        gr2d.setColor(Color.RED);
        if (isInit){
            if (!moleculesList.molecules.isEmpty()) {
                if (pompThread.isAlive()) {
                    for (Molecule m : moleculesList.molecules) {
                        gr2d.fillOval(m.getX(), m.getY(), 10, 10);
                    }
                }
            }
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (pompThread.isAlive()) {
            moleculesList.setxBorder(x - 10);
            moleculesList.setyBorder(y);
        }
        valuePress.put(x - 50, getPressure());
        labelP.setText ("Pressure: " + (int) (getPressure()*100));
        labelV.setText ("Volume: " + (x - 50));
        repaint();
     }

     public HashMap<Integer, Double> getVolPress () {
        return (HashMap<Integer, Double>) valuePress;
     }

    public double getPressure () {
        return moleculesList.calcPressure();
    }
}
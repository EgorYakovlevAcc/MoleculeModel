package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class PlotPanel extends JPanel implements ActionListener {
    Timer timer = new Timer(100, this);
    HashMap<Integer, Double> hashMap;
    int k = 1;
    double press = 100;

    PlotPanel (HashMap<Integer, Double> hashMap) {
        this.hashMap = hashMap;
        timer.start();
    }

    @Override
    public void paint(Graphics g2) {
        int j = 0;
        int t = 0;
        super.paintComponent(g2);
        Graphics2D gr2d = (Graphics2D) g2;

        gr2d.setColor(Color.WHITE);
        gr2d.fillRect(0, 0, 550, 550);

        gr2d.setColor(Color.LIGHT_GRAY);
        for (int i = 0; i < 532; i = i + 38) {
            gr2d.drawLine(i + 38, 0, i + 38, 550);
            gr2d.drawLine(0, 550 - (i + 38), 550, 550 - (i + 38));
        }

        for (int i = 0; i < 550; i = i + 38) {
            if (j != 0) {
                gr2d.setColor (Color.BLUE);
                gr2d.drawString(Integer.toString(50*j), i, 548);
                gr2d.setColor (Color.WHITE);
                gr2d.drawOval(i, 548, 4, 4);
            }
            gr2d.setColor(Color.BLUE);
            gr2d.drawString(Integer.toString(50 * j), 10, 550 - i);
            gr2d.setColor (Color.WHITE);
            gr2d.drawOval(3, 548 - i, 4, 4);
            j++;
        }

        gr2d.setColor(Color.BLACK);
        gr2d.setStroke(new BasicStroke(5.0f));
        gr2d.drawLine(5, 0, 5, 550);
        gr2d.drawLine(5, 550, 550, 550);
        gr2d.setStroke(new BasicStroke(1.0f));

        Set<Map.Entry<Integer, Double>> set = hashMap.entrySet();
        gr2d.setColor(Color.GREEN);
        press = hashMap.get(700);
        for (Map.Entry<Integer, Double> volPress: set) {
            if ((550 - (int) (((double)(volPress.getValue())*100*50/70) - 2)) > 0)
                gr2d.fillOval(((int)(((double)volPress.getKey())*532/700) - 2), (550 - (int) (((double)(volPress.getValue())*100*50/70) - 2)), 4, 4);
        }
    }

    public void update () {
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        update();
    }
}

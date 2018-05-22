package com.company;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class  UIframe extends JFrame {
    boolean isStopped = false;
    Panel panel;
    JButton showGraphButton;
    JButton startButton;
    JLabel pressureLabel, volumeLabel;

    public UIframe(String title) throws HeadlessException {

        super(title);
        setSize(900, 700);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null);
        setFocusable(true);

        pressureLabel = new JLabel("Pressure: ");
        pressureLabel.setBounds(760, 50, 120, 30);
        add (pressureLabel);

        volumeLabel = new JLabel("Volume: ");
        volumeLabel.setBounds(760, 100, 120, 30);
        add (volumeLabel);


        panel = new Panel(pressureLabel, volumeLabel);
        panel.setBounds(0,0, 750, 600);
        add (panel);


        JTextField numberOfMolecules = new JTextField(3);
        numberOfMolecules.setText("50");
        numberOfMolecules.setBounds (760, 200, 30, 30);
        add (numberOfMolecules);
        JLabel numberOfMoleculesLabel = new JLabel("molecules" );
        numberOfMoleculesLabel.setBounds(800, 200, 900, 30);
        add (numberOfMoleculesLabel);


        JTextField speedOfMolecules = new JTextField(3);
        speedOfMolecules.setText("10");
        speedOfMolecules.setBounds (760, 240, 30, 30);
        add (speedOfMolecules);
        JLabel speedOfMoleculesLabel = new JLabel("px" );
        speedOfMoleculesLabel.setBounds(800, 240, 90, 30);
        add (speedOfMoleculesLabel);

        JButton enterMoleculesData = new JButton("Enter");
        enterMoleculesData.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.setNumberOfMolecules(Integer.parseInt(numberOfMolecules.getText()));
                panel.setSpeed(Integer.parseInt(speedOfMolecules.getText()));
                startButton.setEnabled(true);
                speedOfMolecules.setEnabled(false);
                numberOfMolecules.setEnabled(false);
                enterMoleculesData.setEnabled(false);
            }
        });
        enterMoleculesData.setBounds (760, 280, 100, 30);
        add (enterMoleculesData);

        startButton = new JButton("Start");
        startButton.setVisible(true);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isStopped) {
                    startButton.setText("Stop");
                    isStopped = true;
                    panel.start();
                } else {
                    startButton.setText("Start");
                    isStopped = false;
                    try {
                        panel.stop();
                    } catch (InterruptedException e1) {
                    }
                }
                showGraphButton.setEnabled(true);
            }
        });
        startButton.setBounds(50, 630, 150, 30);
        startButton.setEnabled(false);
        add(startButton);

        showGraphButton = new JButton("Show graph");
        showGraphButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new PlotFrameThread(panel.getVolPress()).start();
            }
        });
        showGraphButton.setBounds(250, 630, 150, 30);
        showGraphButton.setEnabled(false);
        add(showGraphButton);

        setVisible(true);
    }

 }

class PlotFrameThread extends Thread {
    HashMap<Integer, Double> hashMap;
    PlotFrameThread (HashMap <Integer, Double> hashMap) {
        this.hashMap = hashMap;
    }

    @Override
    public void run () {
        new PlotFrame (hashMap);
    }
}


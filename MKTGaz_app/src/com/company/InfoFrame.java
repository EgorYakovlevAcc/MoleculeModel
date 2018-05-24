package com.company;

import javax.swing.*;
import java.io.*;

public class InfoFrame extends JFrame {
    JTextArea infoText;

    InfoFrame () {
        super("Information");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(null);
        setSize(500, 600);
        setResizable(false);
        setVisible(true);

        infoText = new JTextArea(400, 400);
        infoText.setBounds(50, 50, 400, 400);
        add(infoText);
        infoText.setEnabled(false);
        readFromFile ("src/InfoText.txt");
    }

    private void readFromFile (String dir) {
        try {
            FileInputStream input = new FileInputStream(dir);
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            String oneLine;
            while ((oneLine = reader.readLine()) != null) {
                infoText.append(oneLine + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

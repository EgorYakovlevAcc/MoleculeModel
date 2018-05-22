package com.company;

import java.util.ArrayList;
import java.util.Random;

public class MoleculesList extends Thread {
    ArrayList<Molecule> molecules;
    MoleculesThread moleculesThread;
    int xBorder = 700;
    int yBorder;
    int ieter = 0;
    int pomp = 0;
    private double cof = 1;

    public int getxBorder() {
        return xBorder;
    }

    public void setxBorder(int xBorder) {
        this.xBorder = xBorder;
    }

    public int getyBorder() {
        return yBorder;
    }

    public void setyBorder(int yBorder) {
        this.yBorder = yBorder;
    }

    public ArrayList<Molecule> getMoleculesList () {
        return molecules;
    }
    public void setMoleculesList(ArrayList<Molecule> moleculesList) {
        molecules = moleculesList;
    }

    public double calcCof () {
        int v = 700;
        double p = calcPressure();

        return (p*Math.pow(p, 1.5));
    }

    MoleculesList(int length, int xBorder, int yBorder) {
        int x = 0;
        int y = 0;

        int xDir = 0;
        int yDir = 0;

        this.xBorder = xBorder;
        this.yBorder = yBorder;

        molecules = new ArrayList<Molecule>();
        for (int i = 0; i < length; i++) {
            x = new Random().nextInt(450) + 50;
            y = new Random().nextInt(450) + 50;
            xDir = new Random().nextInt(450) + 50;
            yDir = new Random().nextInt(450) + 50;

            molecules.add(new Molecule(10, x, y, xDir, yDir));
        }
    }

    @Override
    public void run() {
        while (true) {
            move();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {}
        }
    }

    private void move() {

        //столкновения частиц между собой
        collisionBetweenMolecules();

        //столкновения частиц со стенкой и поршнем
        checkCollisionWithSubjects();
    }
    private void checkCollisionWithSubjects () {
        int x;
        int y;
        //столкновения частиц со стенкой и поршнем
        for (Molecule m : molecules) {
            x = m.getX();
            y = m.getY();
            x = (int) (x + m.getxSpeedDir());
            y = (int) (y + m.getySpeedDir());

            if ((x > 50) && (x < xBorder)) {
                m.setxCoord(x);
            }
            else {
                if (xBorder - m.getX() < m.getX() - 50)
                    m.setyCoord(xBorder);
                else m.setyCoord(50);
                m.setxSpeedDir((-1)*m.getxSpeedDir());
            }
            if ((y > 50) && (y < 540)){
                m.setyCoord(y);
            }
            else {
                if (540 - m.getY() < m.getY() - 50)
                    m.setyCoord(540);
                else m.setyCoord(50);
                m.setySpeedDir((-1)*m.getySpeedDir());
            }
        }
    }
    private void collisionBetweenMolecules () {
        double x1 = 0;
        double y1 = 0;
        double x2 = 0;
        double y2 = 0;
        double r;
        for (Molecule j : molecules) {
            for (Molecule i : molecules) {
                if (!i.equals(j)) {
                    x1 = j.getX() + j.getxSpeedDir();
                    y1 = j.getY() + j.getySpeedDir();
                    x2 = i.getX() + i.getxSpeedDir();
                    y2 = i.getY() + i.getySpeedDir();
                    r = Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2);
                    if (r > 100) {
                    } else {
                        j.setySpeedDir((-1) * j.getySpeedDir());
                        j.setxSpeedDir((-1) * j.getxSpeedDir());
                    }
                }
            }
        }
    }
    public double middleSpeed() {
        double mid = 0;
        for (Molecule M: molecules) {
            mid = mid + M.getxSpeedDir()*M.getxSpeedDir() + M.getySpeedDir()*M.getySpeedDir();
        }
        return (mid/molecules.size());
    }

    public double calcPressure () {
        return (((molecules.size()/3)*middleSpeed())/xBorder);
    }
}


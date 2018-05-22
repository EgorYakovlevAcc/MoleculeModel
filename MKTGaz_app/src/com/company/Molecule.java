package com.company;

public class  Molecule {
    private int speed;
    public double speedDir[] = new double [2];
    private int xCoord;
    private int yCoord;
    private int xDir;
    private int yDir;
    public final int MOLECULE_SIZE = 10;

    public void setxCoord(int xCoord) {
        this.xCoord = xCoord;
    }

    public void setyCoord(int yCoord) {
        this.yCoord = yCoord;
    }

    public Molecule(int speed, int xCoord, int yCoord, int xDir, int yDir) {
        this.speed = speed;
        this.xDir = xDir;
        this.yDir = yDir;
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        speedDir[0] = sign()*(xDir/Math.sqrt(xDir*xDir + yDir*yDir))*speed;
        speedDir[1] = sign()*(yDir/Math.sqrt(xDir*xDir + yDir*yDir))*speed;
    }
    public int getX () {
        return xCoord;
    }
    public int getY () {
        return yCoord;
    }
    public double getxSpeedDir() {
        return speedDir[0];
    }
    public double getySpeedDir() {
        return speedDir[1];
    }
    public void setxSpeedDir(double speedx) {
        speedDir[0] = speedx;
    }
    public void setySpeedDir(double speedy) {
        speedDir[1] = speedy;
    }

    private int sign() {
        int result = 1;
        if (xCoord > xDir) {
            result = result * (-1);
        }
        if (yCoord > yDir) {
            result = result * (-1);
        }
        return result;
    }

}
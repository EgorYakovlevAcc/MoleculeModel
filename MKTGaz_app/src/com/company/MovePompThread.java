package com.company;

public class MovePompThread extends Thread {
   private int x;
   private int y;
   public boolean flag = true;
   int iter = 0;

    MovePompThread (int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void run () {
        while (x > 150) {
            if (flag) {
                x = x - 1;
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                }
            }
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public  void setX(int xB) {
        x = xB;
    }
}

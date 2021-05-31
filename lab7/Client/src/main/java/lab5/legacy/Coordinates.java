package lab5.legacy;

import java.io.Serializable;

public class Coordinates implements Serializable {
    private double x;
    private int y;
    public Coordinates(double x, int y) {
        this.x = Math.round(x*1000d)/1000d;
        this.y = y;
    }

    public void setX(double x) {
        this.x = Math.round(x*1000d)/1000d;
    }
    public void setY(int y) {
        this.y = y;
    }
    public double getX() {
        return x;
    }
    public int getY() {
        return y;
    }
}
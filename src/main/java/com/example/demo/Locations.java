package com.example.demo;

public class Locations {
    int x,y;
    public Locations(int xx, int yy){
        x = xx;
        y = yy;
    }
    public void setLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}

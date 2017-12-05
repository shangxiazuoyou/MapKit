package com.robbu.mapkit.wrapper;

public class IPoint {
    public int x, y;

    public IPoint() {
    }

    public IPoint(int ax, int ay) {
        x = ax;
        y = ay;
    }

    @Override
    public Object clone() {
        IPoint point = null;
        try {
            point = (IPoint) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return point;
    }
}
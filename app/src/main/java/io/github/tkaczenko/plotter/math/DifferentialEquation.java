package io.github.tkaczenko.plotter.math;

/**
 * Created by tkaczenko on 10.03.17.
 */

public abstract class DifferentialEquation implements Function {
    protected double a, b, h;
    private boolean drawable = true;

    public double getA() {
        return a;
    }

    public void setA(double a) {
        this.a = a;
    }

    public double getB() {
        return b;
    }

    public void setB(double b) {
        this.b = b;
    }

    public double getH() {
        return h;
    }

    public void setH(double h) {
        this.h = h;
    }

    public boolean isDrawable() {
        return drawable;
    }

    public void setDrawable(boolean drawable) {
        this.drawable = drawable;
    }
}

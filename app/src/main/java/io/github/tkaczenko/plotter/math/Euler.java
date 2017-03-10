package io.github.tkaczenko.plotter.math;

/**
 * Created by tkaczenko on 09.03.17.
 */

public class Euler implements Function {
    private double a, b, h, previous = 0;

    @Override
    public double f(double x) {
        return previous = (a * b - b * previous) * h + previous;
    }

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
}

package io.github.tkaczenko.plotter.math;

/**
 * Created by tkaczenko on 09.03.17.
 */

public class RungeKutta implements Function {
    private double a, b, h;
    private double k1, k2, k3, k4, delta, previous = 0;

    @Override
    public double f(double x) {
        calculateK1();
        calculateK2();
        calculateK3();
        calculateK4();
        calculateDelta();
        return previous += delta;
    }

    private double calculateK1() {
        return k1 = a * b - b * previous;
    }

    private double calculateK2() {
        return k2 = a * b - b * (previous + k1 * h / 2);
    }

    private double calculateK3() {
        return k3 = a * b - b * (previous + k2 * h / 2);
    }

    private double calculateK4() {
        return k4 = a * b - b * (previous + k3 * h);
    }

    private double calculateDelta() {
        return delta = h / 6 * (k1 + 2 * k2 + 2 * k3 + k4);
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

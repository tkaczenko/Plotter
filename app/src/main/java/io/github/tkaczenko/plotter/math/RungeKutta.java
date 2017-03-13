package io.github.tkaczenko.plotter.math;

/**
 * Created by tkaczenko on 09.03.17.
 */

public class RungeKutta extends DifferentialEquation {
    private double k1;
    private double k2;
    private double k3;
    private double k4;
    private double delta;
    private double previous = 0;

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

    public void clear() {
        this.previous = 0;
    }
}

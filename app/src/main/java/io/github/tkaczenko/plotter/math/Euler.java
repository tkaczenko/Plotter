package io.github.tkaczenko.plotter.math;

/**
 * Created by tkaczenko on 09.03.17.
 */

public class Euler extends DifferentialEquation {
    private double previous = 0;

    @Override
    public double f(double x) {
        return previous = (a * b - b * previous) * h + previous;
    }

    public void clear() {
        this.previous = 0;
    }
}

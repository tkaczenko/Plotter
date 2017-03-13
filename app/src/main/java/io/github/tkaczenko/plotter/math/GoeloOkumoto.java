package io.github.tkaczenko.plotter.math;

/**
 * Created by tkaczenko on 09.03.17.
 */

public class GoeloOkumoto extends DifferentialEquation {
    @Override
    public double f(double x) {
        return a * (1 - Math.exp(-b * x));
    }
}

package io.github.tkaczenko.plotter.math;

/**
 * Created by tkaczenko on 09.03.17.
 */

public class GoeloOkumoto implements Function {
    private double a, b;

    @Override
    public double f(double x) {
        return a * (1 - Math.exp(-b * x));
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

}

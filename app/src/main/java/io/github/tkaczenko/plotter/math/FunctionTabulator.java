package io.github.tkaczenko.plotter.math;

import java.util.ArrayList;
import java.util.List;

import io.github.tkaczenko.plotter.graphics.Point;

/**
 * Class for tabulating functions
 *
 * @author tkaczenko
 */
public class FunctionTabulator {
    private Function function;
    private int stepCount;
    private double from;
    private double to;

    public FunctionTabulator() {

    }

    public FunctionTabulator(Function function) {
        this.function = function;
    }

    public List<Point<Double>> tabulate() {
        if (function == null) {
            return null;
        }
        List<Point<Double>> points = new ArrayList<>();
        double delta = (to - from) / stepCount;
        for (int i = 0; i <= stepCount; i++) {
            double x = from + delta * i;
            points.add(new Point(x, function.f(x)));
        }
        return points;
    }

    public Function getFunction() {
        return function;
    }

    public void setFunction(Function function) {
        this.function = function;
    }

    public int getStepCount() {
        return stepCount;
    }

    public void setStepCount(int stepCount) {
        this.stepCount = stepCount;
    }

    public double getFrom() {
        return from;
    }

    public void setFrom(double from) {
        this.from = from;
    }

    public double getTo() {
        return to;
    }

    public void setTo(double to) {
        this.to = to;
    }

}

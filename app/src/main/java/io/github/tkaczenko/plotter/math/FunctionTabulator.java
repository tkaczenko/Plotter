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
    private double from;
    private double to;
    private double step;

    public List<Point<Double>> tabulate() {
        if (function == null) {
            return null;
        }
        int stepCount = calculateSteps();
        List<Point<Double>> points = new ArrayList<>();
        double x = from;
        points.add(new Point<>(x, 0D));
        for (int i = 1; i <= stepCount; i++) {
            x += step;
            points.add(new Point<>(x, function.f(x)));
        }
        return points;
    }

    private int calculateSteps() {
        return (int) ((to - from) / step + 1);
    }

    public Function getFunction() {
        return function;
    }

    public void setFunction(Function function) {
        this.function = function;
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

    public void setStep(double step) {
        this.step = step;
    }

    public double getStep() {
        return step;
    }
}

package io.github.tkaczenko.plotter.graphics;

/**
 * Class for convenient access to screen and world coordinate of points
 *
 * @param <T> Point maybe Integer or Double. For this realization, using Double.
 * @author tkaczenko
 */
public class Point<T> {
    private final T x;
    private final T y;

    public Point(T x, T y) {
        this.x = x;
        this.y = y;
    }

    public T getX() {
        return x;
    }

    public T getY() {
        return y;
    }
}

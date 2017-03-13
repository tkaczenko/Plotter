package io.github.tkaczenko.plotter.graphics;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Class for convenient access to screen and world coordinate of points
 *
 * @param <T> Point maybe Integer or Double. For this realization, using Double.
 * @author tkaczenko
 */
public class Point<T extends Number> implements Parcelable {
    private final T x;
    private final T y;

    public Point(T x, T y) {
        this.x = x;
        this.y = y;
    }

    @SuppressWarnings("unchecked")
    protected Point(Parcel in) {
        x = (T) Double.valueOf(in.readDouble());
        y = (T) Double.valueOf(in.readDouble());
    }

    public static final Creator<Point> CREATOR = new Creator<Point>() {
        @Override
        public Point createFromParcel(Parcel in) {
            return new Point(in);
        }

        @Override
        public Point[] newArray(int size) {
            return new Point[size];
        }
    };

    public T getX() {
        return x;
    }

    public T getY() {
        return y;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble((Double) x);
        dest.writeDouble((Double) y);
    }
}

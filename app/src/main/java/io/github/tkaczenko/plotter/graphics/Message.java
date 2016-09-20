package io.github.tkaczenko.plotter.graphics;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Parcelable class for passing data between Activities
 *
 * @author tkaczenko
 */
public class Message implements Parcelable {
    private double minX;
    private double maxX;
    private double minY;
    private double maxY;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(minX);
        dest.writeDouble(maxX);
        dest.writeDouble(minY);
        dest.writeDouble(maxY);
    }

    public static final Parcelable.Creator<Message> CREATOR = new Parcelable.Creator<Message>() {
        @Override
        public Message createFromParcel(Parcel source) {
            return new Message(source);
        }

        @Override
        public Message[] newArray(int size) {
            return new Message[size];
        }
    };

    public Message() {

    }

    private Message(Parcel in) {
        minX = in.readDouble();
        maxX = in.readDouble();
        minY = in.readDouble();
        maxY = in.readDouble();
    }

    public double getMinX() {
        return minX;
    }

    public void setMinX(double minX) {
        this.minX = minX;
    }

    public double getMaxX() {
        return maxX;
    }

    public void setMaxX(double maxX) {
        this.maxX = maxX;
    }

    public double getMinY() {
        return minY;
    }

    public void setMinY(double minY) {
        this.minY = minY;
    }

    public double getMaxY() {
        return maxY;
    }

    public void setMaxY(double maxY) {
        this.maxY = maxY;
    }
}

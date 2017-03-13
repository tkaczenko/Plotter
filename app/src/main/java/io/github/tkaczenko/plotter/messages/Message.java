package io.github.tkaczenko.plotter.messages;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.github.tkaczenko.plotter.graphics.Point;

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
    private Map<String, List<Point<Double>>> functionListMap;

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
        writeParcelableMap(dest, flags, functionListMap);
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
        functionListMap = readParcelableMap(in);
    }

    public void writeParcelableMap(Parcel parcel, int flags, Map<String, List<Point<Double>>> map) {
        if (map == null) {
            parcel.writeInt(0);
            return;
        } else {
            parcel.writeInt(map.size());
        }
        for(Map.Entry<String, List<Point<Double>>> e : map.entrySet()){
            parcel.writeString(e.getKey());
            parcel.writeList(e.getValue());
        }
    }

    // For reading from a Parcel
    public Map<String, List<Point<Double>>> readParcelableMap(Parcel parcel) {
        int size = parcel.readInt();
        Map<String, List<Point<Double>>> map = new HashMap<>(size);
        for(int i = 0; i < size; i++){
            String name = parcel.readString();
            List<Point<Double>> points = new ArrayList<>();
            parcel.readList(points, null);
            map.put(name, points);
        }
        return map;
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

    public Map<String, List<Point<Double>>> getFunctionListMap() {
        return functionListMap;
    }

    public void setFunctionListMap(Map<String, List<Point<Double>>> functionListMap) {
        this.functionListMap = functionListMap;
    }
}

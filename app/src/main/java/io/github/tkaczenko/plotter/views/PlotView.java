package io.github.tkaczenko.plotter.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.List;

import io.github.tkaczenko.plotter.graphics.Point;

/**
 * Custom SurfaceView for drawing axes, grid and plot for math function
 *
 * @author tkaczenko
 */
public class PlotView extends SurfaceView implements SurfaceHolder.Callback {
    private DrawThread drawThread;

    private List<Point<Double>> points;

    public PlotView(Context context) {
        super(context);
        getHolder().addCallback(this);
    }

    public PlotView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getHolder().addCallback(this);
    }

    public PlotView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        setWillNotDraw(false);
        if (drawThread == null) {
            drawThread = new DrawThread(holder);
            drawThread.setRunning(true);
            drawThread.setPoints(points);
            drawThread.start();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (drawThread != null) {
            boolean retry = true;
            drawThread.setRunning(false);
            while (retry) {
                try {
                    drawThread.join();
                    retry = false;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void setPoints(List<Point<Double>> points) {
        this.points = points;
    }

    public void setMinX(double minX) {
        drawThread.setMinX(minX);
    }

    public double getMinX() {
        return drawThread.getMinX();
    }

    public void setMaxX(double maxX) {
        drawThread.setMaxX(maxX);
    }

    public double getMaxX() {
        return drawThread.getMaxX();
    }

    public void setMinY(double minY) {
        drawThread.setMinY(minY);
    }

    public double getMinY() {
        return drawThread.getMinY();
    }

    public void setMaxY(double maxY) {
        drawThread.setMaxY(maxY);
    }

    public double getMaxY() {
        return drawThread.getMaxY();
    }

}

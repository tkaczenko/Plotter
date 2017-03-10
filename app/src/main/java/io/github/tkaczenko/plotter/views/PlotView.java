package io.github.tkaczenko.plotter.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.List;
import java.util.Map;

import io.github.tkaczenko.plotter.graphics.Point;
import io.github.tkaczenko.plotter.graphics.ScreenConverter;

/**
 * Custom SurfaceView for drawing axes, grid and plot for math function
 *
 * @author tkaczenko
 */
public class PlotView extends SurfaceView implements SurfaceHolder.Callback {
    private DrawThread drawThread;

    private ScreenConverter screenConverter;

    private List<Point<Double>> points;
    private Map<String, List<Point<Double>>> functions;

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
        if (drawThread == null) {
            return;
        }
        drawThread.setSurfaceHolder(holder);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        setWillNotDraw(false);
        if (drawThread == null) {
            drawThread = new DrawThread(holder);
            drawThread.setScreenConverter(screenConverter);
            drawThread.setRunning(true);
            drawThread.setFunctions(functions);
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
            drawThread = null;
        }
    }

    public void setPoints(List<Point<Double>> points) {
        this.points = points;
    }

    public void setMinX(double minX) {
        screenConverter.setMinX(minX);
    }

    public double getMinX() {
        return screenConverter.getMinX();
    }

    public void setMaxX(double maxX) {
        screenConverter.setMaxX(maxX);
    }

    public double getMaxX() {
        return screenConverter.getMaxX();
    }

    public void setMinY(double minY) {
        screenConverter.setMinY(minY);
    }

    public double getMinY() {
        return screenConverter.getMinY();
    }

    public void setMaxY(double maxY) {
        screenConverter.setMaxY(maxY);
    }

    public double getMaxY() {
        return screenConverter.getMaxY();
    }

    public void setScreenConverter(ScreenConverter screenConverter) {
        this.screenConverter = screenConverter;
    }

    public Map<String, List<Point<Double>>> getFunctions() {
        return functions;
    }

    public void setFunctions(Map<String, List<Point<Double>>> functions) {
        this.functions = functions;
    }
}

package io.github.tkaczenko.plotter.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.List;

import io.github.tkaczenko.plotter.graphics.Point;
import io.github.tkaczenko.plotter.graphics.ScreenConverter;

/**
 * Custom SurfaceView for drawing axes, grid and plot for math function
 *
 * @author tkaczenko
 */
public class PlotView extends SurfaceView implements SurfaceHolder.Callback {
    private DrawThread drawThread;

    private ScreenConverter screenConverter = new ScreenConverter();

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
        System.out.println("Changed");
        if (drawThread == null) {
            return;
        }
        drawThread.setSurfaceHolder(holder);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        System.out.println ("Created");
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
        System.out.println("Destroyed");
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
        if (drawThread == null) {
            return;
        }
        drawThread.setMinY(minY);
    }

    public double getMinY() {
        if (drawThread == null) {
            return 0;
        }
        return drawThread.getMinY();
    }

    public void setMaxY(double maxY) {
        if (drawThread == null) {
            return;
        }
        drawThread.setMaxY(maxY);
    }

    public double getMaxY() {
        if (drawThread == null) {
            return 0;
        }
        return drawThread.getMaxY();
    }

}

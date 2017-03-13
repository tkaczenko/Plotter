package io.github.tkaczenko.plotter.views;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import io.github.tkaczenko.plotter.graphics.NiceScale;
import io.github.tkaczenko.plotter.graphics.Point;
import io.github.tkaczenko.plotter.graphics.ScreenConverter;

/**
 * New thread for drawing in PlotView
 *
 * @author tkaczenko
 */
public class DrawThread extends Thread {
    public static final double DEFAULT_MIN_X = -10;
    public static final double DEFAULT_MAX_X = 80;
    public static final double DEFAULT_MIN_Y = -10;
    public static final double DEFAULT_MAX_Y = 80;

    private boolean running = false;
    private SurfaceHolder surfaceHolder;

    private ScreenConverter screenConverter;
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private List<Point<Double>> points;
    private Map<String, List<Point<Double>>> functions;
    private List<Integer> colors;

    private int backgroundColor = Color.WHITE;

    //Axes
    private int axesColor = Color.BLACK;
    private float axesWidth = 1.5F;
    private float arrowSize = 20.0F;
    private float tickSize = 10.0F;
    private float textSize = 20.0F;

    //Plot
    private int plotColor = Color.BLUE;
    private float plotWidth = 1.5F;

    //Grid
    private int gridColor = Color.LTGRAY;
    private float gridWidth = 1.0F;

    public DrawThread(SurfaceHolder surfaceHolder) {
        this.surfaceHolder = surfaceHolder;
    }

    @Override
    public void run() {
        Canvas canvas;
        while (running) {
            synchronized (this) {
                canvas = null;
                try {
                    canvas = surfaceHolder.lockCanvas(null);
                    if (canvas == null) {
                        continue;
                    }
                    init(canvas);
                    fillBackground(canvas);
                    drawAxes(canvas);
                    drawPlot(canvas);
                } finally {
                    if (canvas != null) {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                }
            }
        }
    }

    public synchronized void setSurfaceHolder(SurfaceHolder surfaceHolder) {
        this.surfaceHolder = surfaceHolder;
    }

    private void init(Canvas canvas) {
        paint.setStyle(Paint.Style.STROKE);
        initConverter(canvas);
    }

    private void drawAxes(Canvas canvas) {
        drawXGrid(canvas);
        drawXAxis(canvas);
        drawYGrid(canvas);
        drawYAxis(canvas);
    }

    private void initConverter(Canvas canvas) {
        screenConverter.setWidth(canvas.getWidth());
        screenConverter.setHeight(canvas.getHeight());
    }

    private void fillBackground(Canvas canvas) {
        canvas.drawColor(backgroundColor);
    }

    private void drawXAxis(Canvas canvas) {
        paint.setColor(axesColor);
        paint.setStrokeWidth(axesWidth);

        //Drawing axis
        int yOfAxisX = screenConverter.toScreenY(0);
        canvas.drawLine(0.0F, yOfAxisX,
                canvas.getWidth(), yOfAxisX, paint);

        //Drawing arrow
        canvas.drawLine(canvas.getWidth(), yOfAxisX,
                canvas.getWidth() - arrowSize / 2.0F, yOfAxisX - arrowSize / 2.0F,
                paint);
        canvas.drawLine(canvas.getWidth(), yOfAxisX,
                canvas.getWidth() - arrowSize / 2.0F, yOfAxisX + arrowSize / 2.0F,
                paint);

        //Drawing ticks and signs
        NiceScale niceScale = new NiceScale(
                Math.floor(screenConverter.toWorldX(0)),
                Math.floor(screenConverter.toWorldX(canvas.getWidth()))
        );
        int ticksStart = (int) Math.floor(niceScale.getNiceMin());
        int ticksStop = (int) Math.ceil(niceScale.getNiceMax());

        for (int tick = ticksStart; tick < ticksStop; tick+=niceScale.getTickSpacing()) {
            if (tick == 0 || Math.abs(tick) == ticksStop) {
                continue;
            }
            float tickLeft = (float) screenConverter.toScreenX(tick);
            canvas.drawLine(tickLeft, yOfAxisX - tickSize / 2.0F,
                    tickLeft, yOfAxisX + tickSize / 2.0F,
                    paint);
            canvas.drawText(Integer.toString(tick),
                    tickLeft, yOfAxisX + textSize * 2.0F,
                    paint);
        }

        //Drawing sign
        paint.setTextSize(textSize);
        canvas.drawText("X",
                canvas.getWidth() - textSize * 2.0F, yOfAxisX + textSize * 2.0F,
                paint);
    }

    private void drawXGrid(Canvas canvas) {
        paint.setColor(gridColor);
        paint.setStrokeWidth(gridWidth);

        NiceScale niceScale = new NiceScale(
                Math.floor(screenConverter.toWorldX(0)),
                Math.floor(screenConverter.toWorldX(canvas.getWidth()))
        );

        int gridStart = (int) Math.floor(niceScale.getNiceMin());
        int gridStop = (int) Math.ceil(niceScale.getNiceMax());

        for (int grid = gridStart; grid < gridStop; grid += niceScale.getTickSpacing()) {
            if (grid == 0) {
                continue;
            }
            float gridLeft = (float) screenConverter.toScreenX(grid);
            canvas.drawLine(gridLeft, 0.0F, gridLeft, canvas.getHeight(), paint);
        }

    }

    private void drawYAxis(Canvas canvas) {
        paint.setColor(axesColor);
        paint.setStrokeWidth(axesWidth);

        //Drawing axis
        int xOfAxisY = screenConverter.toScreenX(0);
        canvas.drawLine(xOfAxisY, canvas.getHeight(),
                xOfAxisY, 0.0F, paint);

        //Drawing arrow
        canvas.drawLine(xOfAxisY, 0.0F,
                xOfAxisY - arrowSize / 2.0F, arrowSize / 2.0F,
                paint);
        canvas.drawLine(xOfAxisY, 0.0F,
                xOfAxisY + arrowSize / 2.0F, arrowSize / 2.0F,
                paint);

        //Drawing ticks and signs
        NiceScale niceScale = new NiceScale(
                Math.floor(screenConverter.toWorldY(canvas.getHeight())),
                Math.floor(screenConverter.toWorldY(0))
        );
        int ticksStart = (int) Math.floor(niceScale.getNiceMin());
        int ticksStop = (int) Math.ceil(niceScale.getNiceMax());;

        for (int tick = ticksStart; tick < ticksStop; tick += niceScale.getTickSpacing()) {
            if (tick == 0 || Math.abs(tick) == ticksStop) {
                continue;
            }
            float tickTop = (float) screenConverter.toScreenY(tick);
            canvas.drawLine(xOfAxisY - tickSize / 2.0F, tickTop,
                    xOfAxisY + tickSize / 2.0F, tickTop,
                    paint);
            canvas.drawText(Integer.toString(tick),
                    xOfAxisY - textSize * 2.0F, tickTop,
                    paint);
        }

        //Drawing sign
        paint.setTextSize(textSize);
        canvas.drawText("Y",
                xOfAxisY - textSize * 2.0F, textSize * 2.0F,
                paint);
    }

    private void drawYGrid(Canvas canvas) {
        paint.setColor(gridColor);
        paint.setStrokeWidth(gridWidth);

        NiceScale niceScale = new NiceScale(
                Math.floor(screenConverter.toWorldY(canvas.getHeight())),
                Math.floor(screenConverter.toWorldY(0))
        );

        int gridStart = (int) Math.floor(niceScale.getNiceMin());
        int gridStop = (int) Math.ceil(niceScale.getNiceMax());

        for (int grid = gridStart; grid < gridStop; grid += niceScale.getTickSpacing()) {
            if (grid == 0) {
                continue;
            }
            float gridTop = (float) screenConverter.toScreenY(grid);
            canvas.drawLine(0.0F, gridTop, canvas.getWidth(), gridTop, paint);
        }
    }

    private void drawPlot(Canvas canvas) {
        if (functions == null || functions.size() == 0) {
            return;
        }
        int colorCount = 0;
        for (Map.Entry<String, List<Point<Double>>> entry : functions.entrySet()) {
            paint.setColor(colors.get(colorCount));
            paint.setStrokeWidth(plotWidth);

            int startX = screenConverter.toScreenX(entry.getValue().get(0).getX());
            int startY = screenConverter.toScreenY(entry.getValue().get(0).getY());

            for (int i = 1; i < entry.getValue().size(); i++) {
                int stopX = screenConverter.toScreenX(entry.getValue().get(i).getX());
                int stopY = screenConverter.toScreenY(entry.getValue().get(i).getY());
                canvas.drawLine(startX, startY, stopX, stopY, paint);
                startX = stopX;
                startY = stopY;
            }
            colorCount++;
        }
    }

    private int generateColor(Random random) {
        int red = random.nextInt(256);
        int green = random.nextInt(256);
        int blue = random.nextInt(256);

        return Color.rgb(red, green, blue);
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public void setPoints(List<Point<Double>> points) {
        this.points = points;
    }

    public void setScreenConverter(ScreenConverter screenConverter) {
        this.screenConverter = screenConverter;
    }

    public Map<String, List<Point<Double>>> getFunctions() {
        return functions;
    }

    public void setFunctions(Map<String, List<Point<Double>>> functions) {
        if (functions == null) {
            return;
        }
        this.functions = functions;
        this.colors = new ArrayList<>(functions.size());
        Random random = new Random(System.currentTimeMillis());
        for (int i = 0; i < functions.size(); i++) {
            int color = generateColor(random);
            while (color == Color.WHITE || color == Color.BLACK || color == Color.GRAY
                    || color == Color.LTGRAY || color == Color.DKGRAY || color == Color.TRANSPARENT) {
                color = generateColor(random);
            }
            colors.add(color);
        }
    }
}

package io.github.tkaczenko.plotter.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import io.github.tkaczenko.plotter.R;
import io.github.tkaczenko.plotter.graphics.Point;
import io.github.tkaczenko.plotter.graphics.ScreenConverter;
import io.github.tkaczenko.plotter.math.DifferentialEquation;
import io.github.tkaczenko.plotter.math.Euler;
import io.github.tkaczenko.plotter.math.FunctionTabulator;
import io.github.tkaczenko.plotter.math.GoeloOkumoto;
import io.github.tkaczenko.plotter.math.RungeKutta;
import io.github.tkaczenko.plotter.messages.Message;
import io.github.tkaczenko.plotter.views.DrawThread;
import io.github.tkaczenko.plotter.views.PlotView;

/**
 * Main activity that draw PlotView
 *
 * @author tkaczenko
 */
public class PlotActivity extends AppCompatActivity implements View.OnClickListener {
    private static final double DEFAULT_A = 76;
    private static final double DEFAULT_B = 0.076;
    private static final double DEFAULT_H = 2;

    public static final String EXTRA_MESSAGE = "coordinate_settings";

    private ScreenConverter screenConverter = new ScreenConverter();

    private FunctionTabulator functionTabulator = new FunctionTabulator();
    private Euler euler = new Euler();
    private GoeloOkumoto goeloOkumoto = new GoeloOkumoto();
    private RungeKutta rungeKutta = new RungeKutta();
    private List<DifferentialEquation> functions = new ArrayList<>();

    private PlotView plotView;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Message message = new Message();
        message.setMinX(screenConverter.getMinX());
        message.setMaxX(screenConverter.getMaxX());
        message.setMinY(screenConverter.getMinY());
        message.setMaxY(screenConverter.getMaxY());
        message.setFunctionListMap(plotView.getFunctions());
        outState.putParcelable(EXTRA_MESSAGE, message);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plot);

        plotView = (PlotView) findViewById(R.id.plot_view);

        Button btnDraw = (Button) findViewById(R.id.btnDraw);
        Button btnEdit = (Button) findViewById(R.id.edit_button);

        CheckBox cbPlot1 = (CheckBox) findViewById(R.id.cbPlot1);
        CheckBox cbPlot2 = (CheckBox) findViewById(R.id.cbPlot2);
        CheckBox cbPlot3 = (CheckBox) findViewById(R.id.cbPlot3);
        cbPlot1.setChecked(true);
        cbPlot2.setChecked(true);
        cbPlot3.setChecked(true);

        cbPlot1.setOnClickListener(this);
        cbPlot2.setOnClickListener(this);
        cbPlot3.setOnClickListener(this);
        btnDraw.setOnClickListener(this);
        btnEdit.setOnClickListener(this);

        if (savedInstanceState != null) {
            Message message = savedInstanceState.getParcelable(EXTRA_MESSAGE);
            screenConverter.setMinX(message.getMinX());
            screenConverter.setMaxX(message.getMaxX());
            screenConverter.setMinY(message.getMinY());
            screenConverter.setMaxY(message.getMaxY());
            plotView.setFunctions(message.getFunctionListMap());
        } else {
            screenConverter.setMinX(DrawThread.DEFAULT_MIN_X);
            screenConverter.setMaxX(DrawThread.DEFAULT_MAX_X);
            screenConverter.setMinY(DrawThread.DEFAULT_MIN_Y);
            screenConverter.setMaxY(DrawThread.DEFAULT_MAX_Y);
        }

        initFunctions();
        initTabulator();

        plotView.setScreenConverter(screenConverter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edit_button: {
                Message message = new Message();
                message.setMinX(screenConverter.getMinX());
                message.setMaxX(screenConverter.getMaxX());
                message.setMinY(screenConverter.getMinY());
                message.setMaxY(screenConverter.getMaxY());

                Intent intent = new Intent(this, PreferencesActivity.class);
                intent.putExtra(EXTRA_MESSAGE, message);
                startActivityForResult(intent, 1);
                break;
            }
            case R.id.cbPlot1: {
                goeloOkumoto.setDrawable(!goeloOkumoto.isDrawable());
                break;
            }
            case R.id.cbPlot2: {
                euler.setDrawable(!euler.isDrawable());
                break;
            }
            case R.id.cbPlot3: {
                rungeKutta.setDrawable(!rungeKutta.isDrawable());
                break;
            }
            case R.id.btnDraw: {
                Map<String, List<Point<Double>>> functionListMap = new LinkedHashMap<>();
                for (int i = 0; i < functions.size(); i++) {
                    DifferentialEquation function = functions.get(i);
                    if (function.isDrawable()) {
                        refreshFunctions();
                        functionTabulator.setFunction(function);
                        functionListMap.put(String.valueOf(i), functionTabulator.tabulate());
                    }
                }
                plotView.setFunctions(functionListMap);
                refreshPlotView();
                break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                if (data == null) {
                    Toast.makeText(this, getString(R.string.data_error), Toast.LENGTH_SHORT)
                            .show();
                    return;
                }
                Message message = data.getParcelableExtra(EXTRA_MESSAGE);
                plotView.setMinX(message.getMinX());
                plotView.setMaxX(message.getMaxX());
                plotView.setMinY(message.getMinY());
                plotView.setMaxY(message.getMaxY());
            } else {
                Toast.makeText(this, R.string.data_error, Toast.LENGTH_SHORT)
                        .show();
                return;
            }
        }
    }

    private void refreshPlotView() {
        plotView.setVisibility(View.GONE);
        plotView.setVisibility(View.VISIBLE);
    }

    private void refreshFunctions() {
        euler.clear();
        rungeKutta.clear();
    }

    private void initFunctions() {
        euler.setA(DEFAULT_A);
        euler.setB(DEFAULT_B);
        euler.setH(DEFAULT_H);
        goeloOkumoto.setA(DEFAULT_A);
        goeloOkumoto.setB(DEFAULT_B);
        rungeKutta.setA(DEFAULT_A);
        rungeKutta.setB(DEFAULT_B);
        rungeKutta.setH(DEFAULT_H);
        functions.add(goeloOkumoto);
        functions.add(euler);
        functions.add(rungeKutta);
    }

    private void initTabulator() {
        functionTabulator.setFrom(0);
        functionTabulator.setTo(DEFAULT_A);
        functionTabulator.setStep(DEFAULT_H);
    }
}
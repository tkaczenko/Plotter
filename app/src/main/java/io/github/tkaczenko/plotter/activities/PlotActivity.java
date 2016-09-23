package io.github.tkaczenko.plotter.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import io.github.tkaczenko.plotter.R;
import io.github.tkaczenko.plotter.graphics.ScreenConverter;
import io.github.tkaczenko.plotter.math.Function;
import io.github.tkaczenko.plotter.math.FunctionTabulator;
import io.github.tkaczenko.plotter.messages.Message;
import io.github.tkaczenko.plotter.views.DrawThread;
import io.github.tkaczenko.plotter.views.PlotView;

/**
 * Main activity that draw PlotView
 *
 * @author tkaczenko
 */
public class PlotActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "coordinate_settings";

    private ScreenConverter screenConverter = new ScreenConverter();

    private Function function = new Function() {
        @Override
        public double f(double x) {
            if (x != 0.0) {
                return Math.sin(x);
            } else {
                return 0.0;
            }
        }
    };

    private PlotView plotView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plot);

        plotView = (PlotView) findViewById(R.id.plot_view);

        screenConverter.setMinX(DrawThread.DEFAULT_MIN_X);
        screenConverter.setMaxX(DrawThread.DEFAULT_MAX_X);
        screenConverter.setMinY(DrawThread.DEFAULT_MIN_Y);
        screenConverter.setMaxY(DrawThread.DEFAULT_MAX_Y);

        FunctionTabulator functionTabulator = new FunctionTabulator(function);
        functionTabulator.setFrom(1.0);
        functionTabulator.setTo(4.5);
        functionTabulator.setStepCount(1000);

        plotView.setPoints(functionTabulator.tabulate());
        plotView.setScreenConverter(screenConverter);
    }

    public void onClick(View v) {
        Message message = new Message();
        message.setMinX(screenConverter.getMinX());
        message.setMaxX(screenConverter.getMaxX());
        message.setMinY(screenConverter.getMinY());
        message.setMaxY(screenConverter.getMaxY());

        Intent intent = new Intent(this, PreferencesActivity.class);
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivityForResult(intent, 1);
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
}
package io.github.tkaczenko.plotter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import io.github.tkaczenko.plotter.graphics.Message;
import io.github.tkaczenko.plotter.math.Function;
import io.github.tkaczenko.plotter.math.FunctionTabulator;
import io.github.tkaczenko.plotter.views.PlotView;

/**
 * Main activity that draw PlotView
 *
 * @author tkaczenko
 */
public class PlotActivity extends AppCompatActivity {
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

        FunctionTabulator functionTabulator = new FunctionTabulator(function);
        functionTabulator.setFrom(1.0);
        functionTabulator.setTo(4.5);
        functionTabulator.setStepCount(1000);

        plotView.setPoints(functionTabulator.tabulate());
    }

    public void onClick(View v) {
        Message message = new Message();
        message.setMinX(plotView.getMinX());
        message.setMaxX(plotView.getMaxX());
        message.setMinY(plotView.getMinY());
        message.setMaxY(plotView.getMaxY());

        Intent intent = new Intent(this, PreferencesActivity.class);
        intent.putExtra("coordinate_settings", message);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Message message = data.getParcelableExtra("coordinate_setting");
        plotView.setMinX(message.getMinX());
        plotView.setMaxX(message.getMaxX());
        plotView.setMinY(message.getMinY());
        plotView.setMaxY(message.getMaxY());
    }
}
package io.github.tkaczenko.plotter.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import io.github.tkaczenko.plotter.R;
import io.github.tkaczenko.plotter.messages.Message;

/**
 * Activity for setting settings for PlotView
 *
 * @author tkaczenko
 */
public class PreferencesActivity extends AppCompatActivity {
    EditText editMinX;
    EditText editMaxX;
    EditText editMinY;
    EditText editMaxY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        Message message = getIntent().getParcelableExtra("coordinate_settings");

        editMinX = (EditText) findViewById(R.id.editMinX);
        editMinX.setText(Double.toString(message.getMinX()));

        editMaxX = (EditText) findViewById(R.id.editMaxX);
        editMaxX.setText(Double.toString(message.getMaxX()));

        editMinY = (EditText) findViewById(R.id.editMinY);
        editMinY.setText(Double.toString(message.getMinY()));

        editMaxY = (EditText) findViewById(R.id.editMaxY);
        editMaxY.setText(Double.toString(message.getMaxY()));
    }

    public void onClick(View v) {
        Message message = new Message();
        message.setMinX(Double.parseDouble(editMinX.getText().toString()));
        message.setMaxX(Double.parseDouble(editMaxX.getText().toString()));
        message.setMinY(Double.parseDouble(editMinY.getText().toString()));
        message.setMaxY(Double.parseDouble(editMaxY.getText().toString()));

        Intent intent = new Intent();
        intent.putExtra("coordinate_settings", message);
        setResult(RESULT_OK, intent);
        finish();
    }
}
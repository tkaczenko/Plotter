package io.github.tkaczenko.plotter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import io.github.tkaczenko.plotter.graphics.Message;

/**
 * Activity for setting settings for PlotView
 *
 * @author tkaczenko
 */
public class PreferencesActivity extends AppCompatActivity {
    EditText editText;
    EditText editText2;
    EditText editText3;
    EditText editText4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        Message message = getIntent().getParcelableExtra("coordinate_settings");

        editText = (EditText) findViewById(R.id.editText);
        editText.setText(Double.toString(message.getMinX()));

        editText2 = (EditText) findViewById(R.id.editText2);
        editText2.setText(Double.toString(message.getMaxX()));

        editText3 = (EditText) findViewById(R.id.editText3);
        editText3.setText(Double.toString(message.getMinY()));

        editText4 = (EditText) findViewById(R.id.editText4);
        editText4.setText(Double.toString(message.getMaxY()));
    }

    public void onClick(View v) {
        Message message = new Message();
        message.setMinX(Double.parseDouble(editText.getText().toString()));
        message.setMaxX(Double.parseDouble(editText2.getText().toString()));
        message.setMinY(Double.parseDouble(editText3.getText().toString()));
        message.setMaxY(Double.parseDouble(editText4.getText().toString()));

        Intent intent = new Intent();
        intent.putExtra("coordinate_setting", message);
        setResult(RESULT_OK, intent);
        finish();
    }
}
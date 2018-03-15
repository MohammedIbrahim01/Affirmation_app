package com.example.android.affirmation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    //**** 1
    private EditText titleEditText;
    private String title;
    //**** 2
    private EditText affirmationEditText;
    private String affirmation;
    //**** 4
    private Spinner notifyRateSpinner;
    private String notifyRate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //**** 1
        titleEditText = (EditText) findViewById(R.id.title_text_view);
        //**** 2
        affirmationEditText = (EditText) findViewById(R.id.affirmation_text_view);
        //**** 4
        notifyRateSpinner = (Spinner) findViewById(R.id.notify_rate_spinner);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.notify_rate_array, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        notifyRateSpinner.setAdapter(spinnerAdapter);

    }

    //**** 3
    private String getText(EditText editText){
        return editText.getText().toString();
    }
}

package com.example.android.affirmation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    //**** 1
    private EditText titleEditText;
    private String title;
    //**** 2
    private EditText affirmationEditText;
    private String affirmation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //**** 1
        titleEditText = (EditText) findViewById(R.id.title_text_view);
        //**** 2
        affirmationEditText = (EditText) findViewById(R.id.affirmation_text_view);

    }

    //**** 3
    private String getText(EditText editText){
        return editText.getText().toString();
    }
}

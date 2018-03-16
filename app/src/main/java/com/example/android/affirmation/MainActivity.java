package com.example.android.affirmation;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
    private int notifyRate;                    //String'
    //**** 5
    private Button saveButton;
    //**** 6
    private Button startNotifyButton;
    //**** 10
    private AlarmManager alarmManager;
    private PendingIntent sendToPublisher;
    //**** 12
    private Button stopButton;

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
        //**** 5
        saveButton = (Button) findViewById(R.id.save_button);
        //**** 6
        startNotifyButton = (Button) findViewById(R.id.start_notify_button);
        //**** 12
        stopButton = (Button) findViewById(R.id.stop_notify_button);


        //**** 5
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveInfo();
            }
        });
        //**** 6
        startNotifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scheduleNotification(title, affirmation, notifyRate);
            }
        });
        //**** 12
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopNotification();
            }
        });

    }

    //**** 3
    private String getText(EditText editText){
        return editText.getText().toString();
    }

    //**** 7
    private void saveInfo(){
        title = getText(titleEditText);
        affirmation = getText(affirmationEditText);
        notifyRate = getNotifyRate(notifyRateSpinner.getSelectedItem().toString());
    }

    //**** 7
    private int getNotifyRate(String string){
        if (string.equals("1 hour"))
            return 1;
        else if (string.equals("2 hour"))
            return 2;
        else if (string.equals("3 hour"))
            return 3;
        else if (string.equals("4 hour"))
            return 4;
        else return 0;
    }

    //**** 8 (deleted buildNotification)

    //**** 9
    private void scheduleNotification(String title, String text, int notifyRate){
        Intent intent = new Intent(this, NotificationPublisher.class);
        intent.putExtra("title", title);
        intent.putExtra("text", text);
        sendToPublisher = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + notifyRate * 1000, notifyRate * 1000, sendToPublisher);
    }

    //**** 10
    private void stopNotification(){
        if(alarmManager != null)
            alarmManager.cancel(sendToPublisher);
    }

}

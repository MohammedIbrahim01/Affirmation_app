package com.example.android.affirmation;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    /*declaration*/

    //**** title
    private EditText titleEditText;
    private String title;
    //**** affirmation
    private EditText affirmationEditText;
    private String affirmation;
    //**** notifyRate
    private Spinner notifyRateSpinner;
    private int notifyRate;
    //**** audio
    private Spinner audioSpinner;
    private int audioId;
    //**** startNotify
    private Button startNotifyButton;
    //**** stopNotify
    private Button stopNotifyButton;
    //**** alarmManager
    private AlarmManager alarmManager;
    private PendingIntent sendToPublisher;
    //**** image
    private ImageView imagePreviewImageView;
    public Bitmap bitmap;                       //bitmap to send to publisher (custom bitmap or default bitmap if user didn't choos)
    //**** pickImage
    private final int PICK_IMAGE_REQUEST = 1;
    private Button pickImageFromGallaryButton;
    //**** imagePreview textview
    private TextView imagePreviewTextView;
    //**** developer state
    private Switch developerSwich;
    private TextView developerTextView;
    private boolean isDeveloper = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*initialization*/

        //**** title
        titleEditText = (EditText) findViewById(R.id.title_text_view);
        //**** affirmation
        affirmationEditText = (EditText) findViewById(R.id.affirmation_text_view);
        //**** notifyRate
        notifyRateSpinner = (Spinner) findViewById(R.id.notify_rate_spinner);
        ArrayAdapter<CharSequence> notifyRateSpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.notify_rate_array, android.R.layout.simple_spinner_item);
        notifyRateSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        notifyRateSpinner.setAdapter(notifyRateSpinnerAdapter);
        //**** audio
        audioSpinner = (Spinner) findViewById(R.id.audio_spinner);
        ArrayAdapter<CharSequence> audioSpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.audio_array, android.R.layout.simple_spinner_item);
        audioSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        audioSpinner.setAdapter(audioSpinnerAdapter);
        //**** startNotify
        startNotifyButton = (Button) findViewById(R.id.start_notify_button);
        //**** stopNotify
        stopNotifyButton = (Button) findViewById(R.id.stop_notify_button);
        //**** image
        imagePreviewImageView = (ImageView) findViewById(R.id.image_preview_imageview);
        //**** pickImage
        pickImageFromGallaryButton = (Button) findViewById(R.id.pick_image_from_gallary_button);
        //**** imagePreview TextView
        imagePreviewTextView = (TextView) findViewById(R.id.image_preciew_imageview);
        //**** developer state
        developerSwich = (Switch) findViewById(R.id.developer_switch);
        developerTextView = (TextView) findViewById(R.id.developer_textview);
        developerTextView.setVisibility(View.INVISIBLE);


        /*set*/

        //**** imagePreview TextView
        imagePreviewTextView.setText("Image Preview : \n\n\t\t\t'no image selected'");

        //**** startNotify
        startNotifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveInfo();                                             //save info
                prepareInformation();                                   //putExtra the info to send to publisher
                startNotify();                                          //start repeating notification
            }
        });
        //**** stopNotify
        stopNotifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopNotification();
            }
        });
        //**** pickImage
        pickImageFromGallaryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickImageFromGallary();
            }
        });
        //**** developer state
        developerSwich.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    isDeveloper = false;
                    developerTextView.setVisibility(View.INVISIBLE);
                }
                else {
                    isDeveloper = true;
                    developerTextView.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    /*method*/

    //**** after user pick an image
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                imagePreviewImageView.setImageBitmap(bitmap);
                imagePreviewTextView.setText("Image Preview :");
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    //**** get text from EditText
    private String getText(EditText editText) {
        if(editText.getText().toString().trim().length() != 0)
            return  editText.getText().toString();
        else
            return editText.getHint().toString();       //return default text if it is empty
    }

    //**** get notify rate choice
    private int getNotifyRate(String string) {
        if (string.equals("1 hour"))
            return isDeveloper? 1000 : (1 * 60 * 60 *1000); //if developer make notify rate 1 sec to see the notification fast
        else if (string.equals("2 hour"))
            return isDeveloper? 2000 : (1 * 60 * 60 *1000);//if developer make notify rate 2 sec to see the notification fast
        else if (string.equals("3 hour"))
            return isDeveloper? 3000 : (1 * 60 * 60 *1000);//if developer make notify rate 3 sec to see the notification fast
        else if (string.equals("4 hour"))
            return isDeveloper? 4000 : (1 * 60 * 60 *1000);//if developer make notify rate 4 sec to see the notification fast
        else
            return 0;
    }

    //**** get audio choice
    private int getAudioId(String string) {
        if (string.matches("Self Esteem")) {
            return R.raw.self_esteem;
        } else if (string.matches("Confidence")) {
            return R.raw.confidence;
        } else {
            return 0;
        }
    }

    //**** save notification information
    private void saveInfo() {
        title = getText(titleEditText);
        affirmation = getText(affirmationEditText);
        notifyRate = getNotifyRate(notifyRateSpinner.getSelectedItem().toString());
        audioId = getAudioId(audioSpinner.getSelectedItem().toString());
    }

    //**** prepare information to send to publisher
    private void prepareInformation() {
        Intent intent = new Intent(getApplicationContext(), NotificationPublisher.class);
        intent.putExtra("title", title);
        intent.putExtra("text", affirmation);
        intent.putExtra("bitmap", bitmap);
        intent.putExtra("audioId", audioId);
        sendToPublisher = PendingIntent.getBroadcast(getBaseContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    //**** start sending notifications
    private void startNotify() {
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + notifyRate, notifyRate, sendToPublisher);
    }

    //**** stop sending notifications
    private void stopNotification() {
        if (alarmManager != null)
            alarmManager.cancel(sendToPublisher);
    }

    //**** pick image from gallary
    private void pickImageFromGallary() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);

        //start chooser if there is multiple choices
        startActivityForResult(Intent.createChooser(intent, "Pick Affirmation Image"), PICK_IMAGE_REQUEST);
    }

}

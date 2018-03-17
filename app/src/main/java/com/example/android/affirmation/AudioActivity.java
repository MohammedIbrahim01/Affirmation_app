package com.example.android.affirmation;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class AudioActivity extends AppCompatActivity {

    private int audioId;
    private ImageView imageView;
    private Button enoughButton;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio1);

        imageView = (ImageView) findViewById(R.id.imageview);
        enoughButton = (Button) findViewById(R.id.enough_button);

        Intent intent = getIntent();
        audioId = intent.getExtras().getInt("audioId");

        //Play audio
        mediaPlayer = MediaPlayer.create(getApplicationContext(), audioId);
        mediaPlayer.start();

        enoughButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                releaseMediaPlayer();
            }
        });
    }

    private void releaseMediaPlayer(){
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}

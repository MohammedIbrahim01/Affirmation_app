package com.example.android.affirmation;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class AudioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio1);

        final ImageView imageView = (ImageView) findViewById(R.id.imageview);
        final Button enoughButton = (Button) findViewById(R.id.enough_button);

        Intent intent = getIntent();
        int audioId = intent.getIntExtra("audioId", R.raw.self_esteem); //self esteem is the default audio
        Bitmap bitmap = intent.getParcelableExtra("bitmap");

        imageView.setImageBitmap(bitmap);

        //Play audio
        final MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), audioId);
        mediaPlayer.start();

        enoughButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.stop();
                mediaPlayer.release();
            }
        });


    }
}

package com.example.android.affirmation;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;

import java.io.ByteArrayOutputStream;

/**
 * Created by x on 15/03/18.
 */

public class NotificationPublisher extends BroadcastReceiver{

    private String title;
    private String text;
    private Bitmap defaultPicture;
    private Notification notification;
    private Bitmap bitmap;
    private int audioId;

    @Override
    public void onReceive(Context context, Intent intent) {
        //**** 13
        title = intent.getExtras().getString("title");
        text = intent.getExtras().getString("text");
        audioId = intent.getExtras().getInt("audioId");
        //**** 15
        bitmap = intent.getParcelableExtra("bitmap");
        //****
        defaultPicture = BitmapFactory.decodeResource(context.getResources(), R.drawable.default_image);

        Intent AudioIntent = new Intent(context, AudioActivity.class);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        AudioIntent.putExtra("bitmap", byteArray);
        AudioIntent.putExtra("audioId", audioId);

        notification = buildNotification(context, AudioIntent);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);
    }

    private Notification buildNotification(Context context, Intent intent){

        Notification notification = new NotificationCompat.Builder(context, "id0")
                .setSmallIcon(R.drawable.ic_stat_attach_money)
                .setContentTitle(title)
                .setContentText(text)
                .setLargeIcon(bitmap == null? defaultPicture : bitmap) //set default picture if no image picked from gallary
                .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(bitmap == null? defaultPicture : bitmap).setSummaryText(text))
                .setContentIntent(PendingIntent.getActivity(context, 0, intent, 0))
                .setAutoCancel(true)
                .build();
        return notification;
    }
}

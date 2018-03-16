package com.example.android.affirmation;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;

/**
 * Created by x on 15/03/18.
 */

public class NotificationPublisher extends BroadcastReceiver{

    private String title;
    private String text;
    private Bitmap picture;
    private Notification notification;
    private Context senderContext;
    private Bitmap bitmap;

    @Override
    public void onReceive(Context context, Intent intent) {
        //**** 13
        senderContext = context;
        title = intent.getExtras().getString("title");
        text = intent.getExtras().getString("text");
        //**** 15
        bitmap = intent.getParcelableExtra("bitmap");
        //****
        picture = BitmapFactory.decodeResource(context.getResources(), R.drawable.picture1);
        notification = buildNotification();

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);
    }

    private Notification buildNotification(){
        Notification notification = new NotificationCompat.Builder(senderContext, "id")
                .setSmallIcon(R.drawable.ic_stat_attach_money)
                .setContentTitle(title)
                .setContentText(text)
                .setLargeIcon(bitmap)
                .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(bitmap).setSummaryText(text))
                .build();
        return notification;
    }
}

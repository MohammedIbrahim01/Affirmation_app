package com.example.android.affirmation;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by x on 15/03/18.
 */

public class NotificationPublisher extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        //**** 11
        Notification notification = intent.getParcelableExtra("notifiction");
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);
    }
}

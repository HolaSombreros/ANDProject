package com.group2.foodie.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.group2.foodie.R;

public class NotificationPublisher extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        //if (intent.getExtras().getString("hasExpired").equals("true")) {
            Notification notification = new Notification.Builder(context, "42")
                    .setSmallIcon(R.mipmap.ic_app_launcher)
                    .setContentTitle("Your ingredient is about to expire")
                    .setContentText("text")
                    .build();
            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            manager.notify(42, notification);
        //}
    }
}


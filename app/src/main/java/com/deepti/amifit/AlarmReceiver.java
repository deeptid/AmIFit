package com.deepti.amifit;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Icon;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import com.deepti.amifit.model.Steps;
import com.deepti.amifit.util.CalendarUtility;
import com.deepti.amifit.view.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by deepti on 03/02/17.
 */

public class AlarmReceiver extends BroadcastReceiver {
    String title = "AmIfit";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("AlarmReceiver", "In onReceive");
        String desc = intent.getExtras().getString("desc");
        if(desc == null){
            desc = "";
        }
        long time = new Date().getTime();
        String tmpStr = String.valueOf(time);
        String last4Str = tmpStr.substring(tmpStr.length() - 5);
        int notificationId = Integer.valueOf(last4Str);

        String reminderType = intent.getExtras().getString("reminderType");

//        switch (reminderType){
//            case "steps":
//            case "workout":
//            case "weight":
//                break;
//            case "walk":
//                break;
//            case "water":
//                break;
//        }

        long when = System.currentTimeMillis();
        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        Intent notificationIntent = new Intent(context, com.deepti.amifit.view.MainActivity.class);

        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Date today = CalendarUtility.getCurrentDate();

        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        String todaysDate = sdf1.format(today);
            Notification notification = new Notification.Builder(context)
                    .setSmallIcon(R.mipmap.icon_amifit_white)
                    .setContentTitle(title)
                    .setSound(alarmSound)
                    .setAutoCancel(true)
                    .setContentText(desc)
                    .setColor(context.getResources().getColor(R.color.colorPrimary))
                    .setContentIntent(pendingIntent)
                    .setWhen(when).build();
            notificationManager.notify(notificationId, notification);
    }


}
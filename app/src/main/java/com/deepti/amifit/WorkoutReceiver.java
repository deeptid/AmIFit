package com.deepti.amifit;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.deepti.amifit.model.Steps;
import com.deepti.amifit.util.CalendarUtility;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by deepti on 07/02/17.
 */

public class WorkoutReceiver extends BroadcastReceiver {

    private int NOTIFICATION_ID = 1;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("AlarmReceiver", "In onReceive");
        long when = System.currentTimeMillis();
        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        Intent notificationIntent = new Intent(context, MainActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Date today = CalendarUtility.getCurrentDate();

        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        String todaysDate = sdf1.format(today);

        ArrayList<Steps> steps = (ArrayList<Steps>) Steps.findWithQuery(Steps.class, "select * from Steps where time = ?", todaysDate);
        if (steps.size() == 0) {
            NotificationCompat.Builder mNotifyBuilder = new NotificationCompat.Builder(
                    context).setSmallIcon(R.mipmap.icon)
                    .setContentTitle("AmIFit Steps")
                    .setContentText("You have not entered your steps yet!").setSound(alarmSound)
                    .setAutoCancel(true).setWhen(when)
                    .setContentIntent(pendingIntent)
                    .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});
            mNotifyBuilder.setColor(Color.WHITE);
            notificationManager.notify(NOTIFICATION_ID, mNotifyBuilder.build());
            NOTIFICATION_ID++;
        }
    }
}

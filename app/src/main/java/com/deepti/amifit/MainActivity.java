package com.deepti.amifit;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.util.Log;

import com.bumptech.glide.util.Util;
import com.deepti.amifit.Settings.ReminderSettings;
import com.deepti.amifit.model.Exercise;
import com.deepti.amifit.model.MyWorkoutProgram;
import com.deepti.amifit.model.Steps;
import com.deepti.amifit.model.TodaysWorkout;
import com.deepti.amifit.model.Weight;
import com.deepti.amifit.model.Workout;
import com.deepti.amifit.util.CalendarUtility;
import com.deepti.amifit.util.DPExample;
import com.deepti.amifit.view.Workout.Programs.CardioProgram;
import com.orm.SugarContext;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

/**
 * Created by deepti on 06/01/17.
 */
public class MainActivity extends Application {
    public static String TAG = MainActivity.class.getSimpleName();
    int ALARM_STEP_REQUEST = 0;
    int ALARM_WORKOUT_REQUEST = 1;
    int ALARM_WEIGHT_REQUEST = 2;
    int ALARM_WALK_REQUEST = 3;
    int ALARM_WATER_REQUEST = 4;


    @Override
    public void onCreate() {
        SugarContext.init(getApplicationContext());
        ArrayList<Workout> workouts = (ArrayList<Workout>) Workout.listAll(Workout.class);
        if (workouts.size() > 0)
            for (Workout w : workouts) {
                Workout.delete(w);
            }
        Resources res = getResources();
        String[] titles = res.getStringArray(R.array.workout_title);
        String[] descs = res.getStringArray(R.array.workout_desc);
        String[] types = res.getStringArray(R.array.workout_type);

        for (int i = 0; i < titles.length; i++) {
            Workout workout = new Workout(titles[i], descs[i], types[i]);
            workout.save();
        }

        int arr[] = {10, 22, 9, 33, 21, 50, 40};
        int n = arr.length;
        Log.d(TAG,"Length of LIS is " + DPExample.lis( arr, n ));

        ArrayList<Exercise> exercises = (ArrayList<Exercise>) Exercise.listAll(Exercise.class);
        if (exercises.size() > 0)
            for (Exercise e : exercises) {
                Exercise.delete(e);
            }

        String[] cardioPrograms = getResources().getStringArray(R.array.cardio_program);
        String[] cardioProgramTimes = getResources().getStringArray(R.array.cardio_program_time);

        for (int i = 0; i < cardioPrograms.length; i++) {
            Exercise exercise = new Exercise(cardioPrograms[i], cardioProgramTimes[i], "cardio");
            exercise.save();
        }

        String[] homePrograms = getResources().getStringArray(R.array.home_program);
        String[] homeProgramTimes = getResources().getStringArray(R.array.home_program_time);

        for (int i = 0; i < homePrograms.length; i++) {
            Exercise exercise = new Exercise(homePrograms[i], homeProgramTimes[i], "home");
            exercise.save();
        }

        String[] absPrograms = getResources().getStringArray(R.array.abs_program);
        String[] absProgramTimes = getResources().getStringArray(R.array.abs_program_time);

        for (int i = 0; i < absPrograms.length; i++) {
            Exercise exercise = new Exercise(absPrograms[i], absProgramTimes[i], "abs");
            exercise.save();
        }

        String[] fullbodyPrograms = getResources().getStringArray(R.array.fullbody_program);
        String[] fullbodyProgramTimes = getResources().getStringArray(R.array.fullbody_program_time);

        for (int i = 0; i < fullbodyPrograms.length; i++) {
            Exercise exercise = new Exercise(fullbodyPrograms[i], fullbodyProgramTimes[i], "fullbody");
            exercise.save();
        }

        String[] ballPrograms = getResources().getStringArray(R.array.ball_program);
        String[] ballProgramTimes = getResources().getStringArray(R.array.ball_program_time);

        for (int i = 0; i < ballPrograms.length; i++) {
            Exercise exercise = new Exercise(ballPrograms[i], ballProgramTimes[i], "ball");
            exercise.save();
        }

        String[] gymPrograms = getResources().getStringArray(R.array.gym_program);
        String[] gymProgramTimes = getResources().getStringArray(R.array.gym_program_time);

        for (int i = 0; i < gymPrograms.length; i++) {
            Exercise exercise = new Exercise(gymPrograms[i], gymProgramTimes[i], "gym");
            exercise.save();
        }


        String todaysDay = CalendarUtility.getTodaysDay();

        ArrayList<MyWorkoutProgram> wps = (ArrayList<MyWorkoutProgram>) MyWorkoutProgram.listAll(MyWorkoutProgram.class);
        ArrayList<TodaysWorkout> twps = (ArrayList<TodaysWorkout>) TodaysWorkout.listAll(TodaysWorkout.class);
        List<Long> wIds = new ArrayList<Long>();
        for (TodaysWorkout t : twps) {
            wIds.add(t.getMyWorkoutProgramId());
        }
        int total = 0;
        if (wps.size() > 0)
            for (MyWorkoutProgram w : wps) {
                if (w.getDayOfTheWeek().equals(todaysDay)) {
                    if (!wIds.contains(w.getId())) {
                        TodaysWorkout tw = new TodaysWorkout(w.getId(), "not_done");
                        tw.save();
                    }
                }
            }
//        ArrayList<MyWorkoutProgram> programs = (ArrayList<MyWorkoutProgram>) MyWorkoutProgram.listAll(MyWorkoutProgram.class);
//        if (programs.size() > 0)
//            for (MyWorkoutProgram e : programs) {
//                MyWorkoutProgram.delete(e);
//            }
        SharedPreferences prefs = getApplicationContext().getSharedPreferences("reminder_preferences", getApplicationContext().MODE_PRIVATE);
        ReminderSettings reminderSettings = new ReminderSettings(getApplicationContext(), prefs);

        if (reminderSettings.isStepsReminderEnabled()) {
            String alarmTime = reminderSettings.getStepsReminderTime();
            String alarmText = reminderSettings.getStepsReminderText();
            setReminder("steps", alarmText, alarmTime, ALARM_STEP_REQUEST);
        }

        if (reminderSettings.isWorkoutReminderEnabled()) {
            String alarmTime = reminderSettings.getWorkoutReminderTime();
            String alarmText = reminderSettings.getWorkoutReminderText();
            setReminder("workout", alarmText, alarmTime, ALARM_WORKOUT_REQUEST);

        }
        if (reminderSettings.isWeightReminderEnabled()) {
            String alarmTime = reminderSettings.getWeightReminderTime();
            String alarmText = reminderSettings.getWeightReminderText();
            setReminder("weight", alarmText, alarmTime, ALARM_WEIGHT_REQUEST);

        }
        if (reminderSettings.isWalkReminderEnabled()) {
            String alarmTime = reminderSettings.getWalkReminderTime();
            String alarmText = reminderSettings.getWalkReminderText();
            setRepeatReminder("walk", alarmText, alarmTime);

        }

        if (reminderSettings.isWaterReminderEnabled()) {
            String alarmTime = reminderSettings.getWalkReminderTime();
            String alarmText = reminderSettings.getWalkReminderText();
            setRepeatReminder("water", alarmText, alarmTime);
        }


    }

    private void setReminder(String reminderType, String reminderDesc, String alarmTime, int requestCode) {
        String[] s = alarmTime.split(":");

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent alarmIntent = new Intent(getApplicationContext(), AlarmReceiver.class); // AlarmReceiver1 = broadcast receiver
        Bundle bundle = new Bundle();
        bundle.putString("desc", reminderDesc);
        bundle.putString("reminderType", reminderType);
        alarmIntent.putExtras(bundle);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), requestCode, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        alarmIntent.setData((Uri.parse("custom://" + System.currentTimeMillis())));
        alarmManager.cancel(pendingIntent);
        Calendar alarmStartTime = Calendar.getInstance();
        Calendar now = Calendar.getInstance();
        alarmStartTime.set(Calendar.HOUR_OF_DAY, Integer.parseInt(s[0]));
        alarmStartTime.set(Calendar.MINUTE, Integer.parseInt(s[1]));
        alarmStartTime.set(Calendar.SECOND, 0);
        if (now.after(alarmStartTime)) {
            Log.d("Hey", "Added a day");
            alarmStartTime.add(Calendar.DATE, 1);
        }
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, alarmStartTime.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        Log.d("Alarm", "Alarm is set for everyday:" + Integer.parseInt(s[0]) +  ":" + s[1]);
    }

    private void setRepeatReminder(String reminderType, String reminderDesc, String alarmTime) {
        int addFactor = 0;
        if(reminderType.equals("water")){
            addFactor = 100;
        }
        if(reminderType.equals("walk")){
            addFactor = 200;
        }
        Intent alarmIntent = new Intent(getApplicationContext(), AlarmReceiver.class);
        AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(ALARM_SERVICE);
        ArrayList<PendingIntent> intentArray = new ArrayList<PendingIntent>();
        Calendar now = Calendar.getInstance();

        int alarmInterval = 1;
        switch (alarmTime){
            case "Every 1 hour":
                alarmInterval = 1;
                break;
            case "Every 2 hour":
                alarmInterval = 2;
                break;
            case "Every 3 hour":
                alarmInterval = 3;
                break;
            case "Every 4 hour":
                alarmInterval = 4;
                break;
        }

        for(int i = 9 ; i < 23 ; i = i + alarmInterval)
        {
            Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
            Bundle bundle = new Bundle();
            bundle.putString("desc", reminderDesc);
            bundle.putString("reminderType", reminderType);
            intent.putExtras(bundle);
            // Loop counter `i` is used as a `requestCode`
            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), i + addFactor, intent, 0);
            alarmManager.cancel(pendingIntent);
            Calendar alarmStartTime = Calendar.getInstance();
            alarmStartTime.set(Calendar.HOUR_OF_DAY, i);
            alarmStartTime.set(Calendar.MINUTE, 0);
            alarmStartTime.set(Calendar.SECOND, 0);
            if (now.after(alarmStartTime)) {
                Log.d("Hey", "Added a day");
                alarmStartTime.add(Calendar.DATE, 1);
            }
//            alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
//                    alarmStartTime.getTimeInMillis(),
//                    pendingIntent);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, alarmStartTime.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
            Log.d("Alarm", "Alarm is set for everyday:" + i + ":0");

            intentArray.add(pendingIntent);
        }
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        SugarContext.terminate();
    }
}

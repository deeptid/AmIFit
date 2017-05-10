package com.deepti.amifit.Settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.util.Log;

import com.deepti.amifit.R;

/**
 * Created by deepti on 14/02/17.
 */

public class ReminderSettings {
    public static String TAG = ReminderSettings.class.getSimpleName();

    SharedPreferences mSettings;
    Context context;

    public ReminderSettings(Context ctx, SharedPreferences settings) {
        mSettings = settings;
        context = ctx;
    }

    // Steps

    public String getStepsReminderTime() {
        String stepsReminderAt = mSettings.getString("time_steps_key", "10:00");
        Log.d(TAG,"stepsReminderAt : " + stepsReminderAt);
        return stepsReminderAt;
    }

    public String getStepsReminderText() {
        String stepsReminderText = mSettings.getString("steps_reminder_title", context.getString(R.string.text_steps_reminder));
        Log.d(TAG,"stepsReminderText : " + stepsReminderText);
        return stepsReminderText;
    }

    public boolean isStepsReminderEnabled() {
        boolean stepsReminderEnabled = mSettings.getBoolean("pref_steps_enable", true);
        Log.d(TAG,"stepsReminderEnabled : " + stepsReminderEnabled);
        return stepsReminderEnabled;
    }

    //Workout

    public String getWorkoutReminderTime() {
        String workoutReminderAt = mSettings.getString("time_workout_key", "10:00");
        Log.d(TAG,"workoutReminderAt : " + workoutReminderAt);
        return workoutReminderAt;
    }

    public String getWorkoutReminderText() {
        String workoutReminderText = mSettings.getString("workout_reminder_title", context.getString(R.string.text_workout_reminder));
        Log.d(TAG,"workoutReminderText : " + workoutReminderText);
        return workoutReminderText;
    }

    public boolean isWorkoutReminderEnabled() {
        boolean workoutReminderEnabled = mSettings.getBoolean("pref_workout_enable", false);
        Log.d(TAG,"workoutReminderEnabled : " + workoutReminderEnabled);
        return workoutReminderEnabled;
    }

    //Weight

    public String getWeightReminderTime() {
        String weightReminderAt = mSettings.getString("time_weight_key", "10:00");
        Log.d(TAG,"weightReminderAt : " + weightReminderAt);
        return weightReminderAt;
    }

    public String getWeightReminderText() {
        String weightReminderText = mSettings.getString("weight_reminder_title", context.getString(R.string.text_weight_reminder));
        Log.d(TAG,"weightReminderText : " + weightReminderText);
        return weightReminderText;
    }

    public boolean isWeightReminderEnabled() {
        boolean weightReminderEnabled = mSettings.getBoolean("pref_weight_enable", false);
        Log.d(TAG,"weightReminderEnabled : " + weightReminderEnabled);
        return weightReminderEnabled;
    }

    //Walk

    public String getWalkReminderTime() {
        String walktReminderAt = mSettings.getString("time_walk_key", "Every 2 hour");
        Log.d(TAG,"walktReminderAt : " + walktReminderAt);
        return walktReminderAt;
    }

    public String getWalkReminderText() {
        String walkReminderText = mSettings.getString("walk_reminder_title", context.getString(R.string.text_walk_reminder));
        Log.d(TAG,"walkReminderText : " + walkReminderText);
        return walkReminderText;
    }

    public boolean isWalkReminderEnabled() {
        boolean walkReminderEnabled = mSettings.getBoolean("pref_walk_enable", false);
        Log.d(TAG,"walkReminderEnabled : " + walkReminderEnabled);
        return walkReminderEnabled;
    }

    //water

    public String getWaterReminderTime() {
        String waterReminderAt = mSettings.getString("time_water_key", "Every 2 hour");
        Log.d(TAG,"waterReminderAt : " + waterReminderAt);
        return waterReminderAt;
    }

    public String getWaterReminderText() {
        String waterReminderText = mSettings.getString("water_reminder_title", context.getString(R.string.text_water_reminder));
        Log.d(TAG,"waterReminderText : " + waterReminderText);
        return waterReminderText;
    }

    public boolean isWaterReminderEnabled() {
        boolean waterReminderEnabled = mSettings.getBoolean("pref_water_enable", false);
        Log.d(TAG,"waterReminderEnabled : " + waterReminderEnabled);
        return waterReminderEnabled;
    }

}

package com.deepti.amifit.Settings;

import android.app.AlarmManager;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.deepti.amifit.AlarmReceiver;
import com.deepti.amifit.MainActivity;
import com.deepti.amifit.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import static android.content.Context.ALARM_SERVICE;

/**
 * Created by deepti on 12/02/17.
 */

public class ReminderSettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {
    public static String TAG = ReminderSettingsFragment.class.getSimpleName();
    public Context context;
    SharedPreferences sharedPreferences;
    ReminderSettings reminderSettings;
    int ALARM_STEP_REQUEST = 0;
    int ALARM_WORKOUT_REQUEST = 1;
    int ALARM_WEIGHT_REQUEST = 2;
    int ALARM_WALK_REQUEST = 3;
    int ALARM_WATER_REQUEST = 4;

    public ReminderSettingsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.reminder_preferences);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        SharedPreferences rprefs = getActivity().getSharedPreferences("reminder_preferences", getActivity().MODE_PRIVATE);
        reminderSettings = new ReminderSettings(getActivity(), rprefs);
        View view = super.onCreateView(inflater, container, savedInstanceState);
        view.setBackgroundColor(getResources().getColor(android.R.color.white));
        final TimePreference timePreference = (TimePreference) findPreference("time_steps_key");
        final PreferenceScreen stepsPref = (PreferenceScreen) findPreference("steps_settings");
        Context hostActivity = getActivity();
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(hostActivity);
        String time = getFormattedTime(prefs.getString("time_steps_key", "disabled"));
        timePreference.setSummary(time);
        stepsPref.setSummary(time);

        final TimePreference timePreference2 = (TimePreference) findPreference("time_workout_key");
        final PreferenceScreen workoutPref = (PreferenceScreen) findPreference("workout_settings");
        String time1 = getFormattedTime(prefs.getString("time_workout_key", "disabled"));
        timePreference2.setSummary(time1);
        workoutPref.setSummary(time1);


        final TimePreference timePreference3 = (TimePreference) findPreference("time_weight_key");
        final PreferenceScreen weightPref = (PreferenceScreen) findPreference("weight_settings");
        String time2 = getFormattedTime(prefs.getString("time_weight_key", "disabled"));
        timePreference3.setSummary(time2);
        weightPref.setSummary(time2);


        final ListPreference timePreference4 = (ListPreference) findPreference("time_walk_key");
        final PreferenceScreen walkPref = (PreferenceScreen) findPreference("walk_settings");
        String time3 = (prefs.getString("time_walk_key", "disabled"));
        timePreference4.setSummary(time3);
        walkPref.setSummary(time3);


        final ListPreference timePreference5 = (ListPreference) findPreference("time_water_key");
        final PreferenceScreen waterPref = (PreferenceScreen) findPreference("water_settings");
        String time4 = (prefs.getString("time_water_key", "disabled"));
        timePreference5.setSummary(time4);
        waterPref.setSummary(time4);
        return view;
    }

    private String getFormattedTime(String s) {
        String time = s;
        String result = null;
        // fix: not correctly formatting 12:00 - 12:59 pm
        DateFormat outputFormat = new SimpleDateFormat("hh:mm aa");
        SimpleDateFormat parseFormat = new SimpleDateFormat("HH:mm");


        try {
            Date dt = parseFormat.parse(time);
            result = outputFormat.format(dt);
        } catch (ParseException exc) {
            exc.printStackTrace();
        }
        return result;
    }

    @Override
    public void onResume() {
        super.onResume();

        sharedPreferences = getPreferenceManager().getSharedPreferences();

        // we want to watch the preference values' changes
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);

        final TimePreference timePreference = (TimePreference) findPreference("time_steps_key");
        final PreferenceScreen stepsPref = (PreferenceScreen) findPreference("steps_settings");
        Context hostActivity = getActivity();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(hostActivity);
        String time = getFormattedTime(prefs.getString("time_steps_key", ""));
        timePreference.setSummary(time);
        stepsPref.setSummary(time);
//        }
    }

    @Override
    public void onPause() {
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
        super.onPause();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        SharedPreferences.Editor prefs = getActivity().getSharedPreferences("reminder_preferences", getActivity().MODE_PRIVATE).edit();
        String prefValue;
        switch (key) {
            case "time_steps_key":
                prefValue = sharedPreferences.getString(key, "");
                // Set summary to be the user-description for the selected value
                final TimePreference timePreference = (TimePreference) findPreference("time_steps_key");
                final PreferenceScreen stepsPref = (PreferenceScreen) findPreference("steps_settings");

                //set summary
                String time = getFormattedTime(sharedPreferences.getString(key, ""));
                stepsPref.setSummary(time);
                timePreference.setSummary(time);
                ((BaseAdapter) getPreferenceScreen().getRootAdapter()).notifyDataSetChanged();

                //commit in shared preference
                prefs.putString("steps_settings", prefValue);
                prefs.putString("time_steps_key", prefValue);
                prefs.commit();

                // set alarm
                setReminder("steps", reminderSettings.getStepsReminderText(), prefValue, ALARM_STEP_REQUEST);
                break;
            case "steps_reminder_title":
                prefValue = sharedPreferences.getString(key, "");
                //commit in shared preference
                prefs.putString("steps_reminder_title", prefValue);
                prefs.commit();
                final EditTextPreference edt = (EditTextPreference) findPreference("steps_reminder_title");
                edt.setSummary(prefValue);
                break;
            case "pref_steps_enable":
                Boolean prefStepsEnable = sharedPreferences.getBoolean(key, false);
                if (!prefStepsEnable) {
                    //cancel related pendingintent
                    final TimePreference tp2 = (TimePreference) findPreference("time_steps_key");
                    final PreferenceScreen sp2 = (PreferenceScreen) findPreference("steps_settings");

                    //set summary
                    tp2.setSummary("");
                    sp2.setSummary("");
                    ((BaseAdapter) getPreferenceScreen().getRootAdapter()).notifyDataSetChanged();
                    cancelReminder(ALARM_STEP_REQUEST);
                } else {
                    //set Alarm
                    setReminder("steps", reminderSettings.getWeightReminderText(), "10:00", ALARM_STEP_REQUEST);
                }
                //commit in shared preference
                prefs.putBoolean("pref_steps_enable", prefStepsEnable);
                prefs.commit();
                break;
            //workout
            case "time_workout_key":
                prefValue = sharedPreferences.getString(key, "");
                final TimePreference timePreference1 = (TimePreference) findPreference("time_workout_key");
                final PreferenceScreen workoutPref = (PreferenceScreen) findPreference("workout_settings");

                //set summary
                String time1 = getFormattedTime(sharedPreferences.getString(key, ""));
                workoutPref.setSummary(time1);
                timePreference1.setSummary(time1);
                ((BaseAdapter) getPreferenceScreen().getRootAdapter()).notifyDataSetChanged();

                //commit in shared preference
                prefs.putString("workout_settings", prefValue);
                prefs.putString("time_workout_key", prefValue);
                prefs.commit();
                // set alarm
                setReminder("workout", reminderSettings.getWorkoutReminderText(), prefValue, ALARM_WORKOUT_REQUEST);
                break;
            case "workout_reminder_title":
                prefValue = sharedPreferences.getString(key, "");
                prefs.putString("workout_reminder_title", prefValue);
                prefs.commit();
                final EditTextPreference edt1 = (EditTextPreference) findPreference("workout_reminder_title");
                edt1.setSummary(prefValue);
                break;
            case "pref_workout_enable":
                Boolean prefWorkoutEnable = sharedPreferences.getBoolean(key, false);
                prefs.putBoolean("pref_workout_enable", prefWorkoutEnable);
                prefs.commit();
                if (!prefWorkoutEnable) {
                    //cancel related pendingintent
                    final TimePreference tp3 = (TimePreference) findPreference("time_workout_key");
                    final PreferenceScreen wp3 = (PreferenceScreen) findPreference("workout_settings");

                    //set summary
                    wp3.setSummary("");
                    tp3.setSummary("");
                    ((BaseAdapter) getPreferenceScreen().getRootAdapter()).notifyDataSetChanged();
                    cancelReminder(ALARM_WORKOUT_REQUEST);
                } else {
                    //set Alarm
                    setReminder("workout", reminderSettings.getWeightReminderText(), "10:00", ALARM_WORKOUT_REQUEST);
                }
                break;

            //weight
            case "time_weight_key":
                prefValue = sharedPreferences.getString(key, "");
                final TimePreference timePreference2 = (TimePreference) findPreference("time_weight_key");
                final PreferenceScreen weightPref = (PreferenceScreen) findPreference("weight_settings");

                //set summary
                String time2 = getFormattedTime(sharedPreferences.getString(key, ""));
                weightPref.setSummary(time2);
                timePreference2.setSummary(time2);
                ((BaseAdapter) getPreferenceScreen().getRootAdapter()).notifyDataSetChanged();

                //commit in shared preference
                prefs.putString("weight_settings", prefValue);
                prefs.putString("time_weight_key", prefValue);
                prefs.commit();

                // set alarm
                setReminder("weight", reminderSettings.getWeightReminderText(), prefValue, ALARM_WEIGHT_REQUEST);
                break;
            case "weight_reminder_title":
                prefValue = sharedPreferences.getString(key, "");
                prefs.putString("weight_reminder_title", prefValue);
                prefs.commit();
                final EditTextPreference edt2 = (EditTextPreference) findPreference("weight_reminder_title");
                edt2.setSummary(prefValue);
                break;
            case "pref_weight_enable":
                Boolean prefWeightEnable = sharedPreferences.getBoolean(key, false);
                if (!prefWeightEnable) {
                    //cancel related pendingintent
                    final TimePreference tp4 = (TimePreference) findPreference("time_weight_key");
                    final PreferenceScreen wp4 = (PreferenceScreen) findPreference("weight_settings");

                    //set summary
                    wp4.setSummary("");
                    tp4.setSummary("");
                    ((BaseAdapter) getPreferenceScreen().getRootAdapter()).notifyDataSetChanged();
                    cancelReminder(ALARM_WEIGHT_REQUEST);
                } else {
                    //set Alarm
                    setReminder("weight", reminderSettings.getWeightReminderText(), "10:00", ALARM_WEIGHT_REQUEST);
                }

                prefs.putBoolean("  pref_weight_enable", prefWeightEnable);
                prefs.commit();
                break;


            //workout
            case "time_walk_key":
                prefValue = sharedPreferences.getString(key, "");
                final ListPreference timePreference3 = (ListPreference) findPreference("time_walk_key");
                final PreferenceScreen walkPref = (PreferenceScreen) findPreference("walk_settings");

                //set summary
                walkPref.setSummary(prefValue);
                timePreference3.setSummary(prefValue);
                ((BaseAdapter) getPreferenceScreen().getRootAdapter()).notifyDataSetChanged();

                //commit in shared preference
                prefs.putString("walk_settings", prefValue);
                prefs.putString("time_walk_key", prefValue);
                prefs.commit();

                // set alarm
                setRepeatReminder("walk", reminderSettings.getWalkReminderText(), prefValue);
                break;
            case "walk_reminder_title":
                prefValue = sharedPreferences.getString(key, "");
                prefs.putString("walk_reminder_title", prefValue);
                prefs.commit();
                final EditTextPreference edt3 = (EditTextPreference) findPreference("walk_reminder_title");
                edt3.setSummary(prefValue);
                break;
            case "pref_walk_enable":
                Boolean prefWalkEnable = sharedPreferences.getBoolean(key, false);
                if (!prefWalkEnable) {
                    final ListPreference tp = (ListPreference) findPreference("time_walk_key");
                    final PreferenceScreen wp = (PreferenceScreen) findPreference("walk_settings");
                    wp.setSummary("");
                    tp.setSummary("");
                    ((BaseAdapter) getPreferenceScreen().getRootAdapter()).notifyDataSetChanged();
                    cancelRepeatReminder("walk");
                } else {
                    //set Alarm
                    setRepeatReminder("water", reminderSettings.getWaterReminderText(), "Every 1 hour");
                }
                prefs.putBoolean("pref_walk_enable", prefWalkEnable);
                prefs.commit();
                break;

            //water
            case "time_water_key":
                prefValue = sharedPreferences.getString(key, "");
                final ListPreference timePreference4 = (ListPreference) findPreference("time_water_key");
                final PreferenceScreen waterPref = (PreferenceScreen) findPreference("water_settings");

                //set summary
                waterPref.setSummary(prefValue);
                timePreference4.setSummary(prefValue);
                ((BaseAdapter) getPreferenceScreen().getRootAdapter()).notifyDataSetChanged();

                //commit in shared preference
                prefs.putString("water_settings", prefValue);
                prefs.putString("time_water_key", prefValue);
                prefs.commit();

                // set alarm
                setRepeatReminder("water", reminderSettings.getWaterReminderText(), prefValue);
                break;
            case "water_reminder_title":
                prefValue = sharedPreferences.getString(key, "");
                prefs.putString("water_reminder_title", prefValue);
                prefs.commit();
                final EditTextPreference edt4 = (EditTextPreference) findPreference("water_reminder_title");
                edt4.setSummary(prefValue);
                break;
            case "pref_water_enable":
                Boolean prefWaterEnable = sharedPreferences.getBoolean(key, false);
                // if prefWaterEnable is false cancel related pendingintent
                // if prefWaterEnable is true set the alarm for the default time
                if (!prefWaterEnable) {
                    //cancel related pendingintent
                    final ListPreference tp1 = (ListPreference) findPreference("time_water_key");
                    final PreferenceScreen wp1 = (PreferenceScreen) findPreference("water_settings");

                    //set summary
                    tp1.setSummary("");
                    wp1.setSummary("");
                    ((BaseAdapter) getPreferenceScreen().getRootAdapter()).notifyDataSetChanged();
                    cancelRepeatReminder("water");
                } else {
                    //set Alarm
                    setRepeatReminder("water", reminderSettings.getWaterReminderText(), "Every 1 hour");
                }

                prefs.putBoolean("pref_water_enable", prefWaterEnable);
                prefs.commit();
                break;
        }
    }

    private void setReminder(String reminderType, String reminderDesc, String alarmTime, int requestCode) {
        String[] s = alarmTime.split(":");

        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(ALARM_SERVICE);
        Intent alarmIntent = new Intent(getActivity(), AlarmReceiver.class); // AlarmReceiver1 = broadcast receiver
        Bundle bundle = new Bundle();
        bundle.putString("desc", reminderDesc);
        bundle.putString("reminderType", reminderType);
        alarmIntent.putExtras(bundle);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), requestCode, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);

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
        Log.d("Alarm", "Alarm is set for everyday:" + Integer.parseInt(s[0]) + ":" + s[1]);
    }

    private void cancelReminder(int requestCode) {
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(ALARM_SERVICE);
        Intent alarmIntent = new Intent(getActivity(), AlarmReceiver.class); // AlarmReceiver1 = broadcast receiver
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), requestCode, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(pendingIntent);
    }

    private void setRepeatReminder(String reminderType, String reminderDesc, String alarmTime) {
        Log.d(TAG, "reminderDesc: " + reminderDesc);
        int addFactor = 0;
        if (reminderType.equals("water")) {
            addFactor = 100;
        }
        if (reminderType.equals("walk")) {
            addFactor = 200;
        }
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(ALARM_SERVICE);
        ArrayList<PendingIntent> intentArray = new ArrayList<PendingIntent>();
        Calendar now = Calendar.getInstance();

        int alarmInterval = 1;
        switch (alarmTime) {
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

        for (int i = 9; i < 23; i = i + alarmInterval) {
            Intent intent = new Intent(getActivity(), AlarmReceiver.class);
            Bundle bundle = new Bundle();
            bundle.putString("desc", reminderDesc);
            bundle.putString("reminderType", reminderType);
            intent.putExtras(bundle);

            // Loop counter `i` is used as a `requestCode`
            PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), i + addFactor, intent, 0);
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

    private void cancelRepeatReminder(String reminderType) {
        int addFactor = 0;
        if (reminderType.equals("water")) {
            addFactor = 100;
        }
        if (reminderType.equals("walk")) {
            addFactor = 200;
        }
        int alarmInterval = 1;
        for (int j = 1; j < 5; j++)
            for (int i = 9; i < 23; i = i + j) {
                Intent intent = new Intent(getActivity(), AlarmReceiver.class);
                AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(ALARM_SERVICE);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), i + addFactor, intent, 0);
                alarmManager.cancel(pendingIntent);
            }
    }


    private void updateSummary(EditTextPreference preference) {
        preference.setSummary(preference.getText());
    }
}

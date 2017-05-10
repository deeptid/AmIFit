package com.deepti.amifit.view.Workout;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.deepti.amifit.R;
import com.deepti.amifit.model.TodaysWorkout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * Created by deepti on 23/01/17.
 */

public class TodaysWorkoutViewAdapter extends BaseAdapter {
    public static String TAG = TodaysWorkoutViewAdapter.class.getSimpleName();

    private Activity activity;
    private ArrayList<HashMap<String, String>> data;
    public LayoutInflater inflater = null;
    static final String KEY_TITLE = "title";
    static final String KEY_DESC = "desc";
    static final String KEY_TIME = "time";
    static final String KEY_ID = "id";
    Button start, stop, restart;
    MyCountDownTimer myCountDownTimer;


    public TodaysWorkoutViewAdapter(Activity activity, ArrayList<HashMap<String, String>> d) {
        this.data = d;
        this.activity = activity;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        Typeface customFont = Typeface.createFromAsset(activity.getAssets(), "fonts/helvetica-neue-ultra-light.ttf");

        if (convertView == null)
            vi = inflater.inflate(R.layout.todays_workout_list_row, null);
        TextView title = (TextView) vi.findViewById(R.id.todays_title); // title
        TextView desc = (TextView) vi.findViewById(R.id.todays_description); // title
        final TextView time = (TextView) vi.findViewById(R.id.time_workout); // title

        title.setTypeface(customFont, Typeface.BOLD);
        desc.setTypeface(customFont, Typeface.BOLD);

        HashMap<String, String> map = new HashMap<String, String>();
        map = data.get(position);

        title.setText(map.get(KEY_TITLE));
        desc.setText(map.get(KEY_DESC));

        final String workoutTime = map.get(KEY_TIME);
        final HashMap<String, String> finalMap = map;
        final String myWorkoutId = map.get(KEY_ID);
        Log.d(TAG, "workoutTime:" + workoutTime);
        time.setText(workoutTime + " min");

        start = (Button) vi.findViewById(R.id.startButton);
        stop = (Button) vi.findViewById(R.id.stopButton);
        restart = (Button) vi.findViewById(R.id.restartButton);

//        final Chronometer simpleChronometer = (Chronometer) vi.findViewById(R.id.jackChronometer);

//        simpleChronometer.setTypeface(customFont, Typeface.BOLD);
        start.setTypeface(customFont, Typeface.BOLD);
        stop.setTypeface(customFont, Typeface.BOLD);
        restart.setTypeface(customFont, Typeface.BOLD);
        final ProgressBar progressBar = (ProgressBar) vi.findViewById(R.id.simpleProgressBar);


        final ImageView iv = (ImageView) vi.findViewById(R.id.marker);
        String status = null;
        ArrayList<TodaysWorkout> todaysWorkoutTables = (ArrayList<TodaysWorkout>) TodaysWorkout.listAll(TodaysWorkout.class);
        for (TodaysWorkout table : todaysWorkoutTables) {
            if (table.getMyWorkoutProgramId().toString().equals(myWorkoutId))
                status = table.getStatus();
        }

        if (status.equals("done")) {
            iv.setImageResource(R.drawable.markertick1);
            notifyDataSetChanged();
        }

//
//        simpleChronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
//
//            public void onChronometerTick(Chronometer arg0) {
//                // TODO Auto-generated method stub
//                long elapsedTime = SystemClock.elapsedRealtime();
//
//                long minutes = ((elapsedTime - simpleChronometer.getBase()) / 1000) / 60;
//                long seconds = ((elapsedTime - simpleChronometer.getBase()) / 1000) % 60;
//                String currentTime = minutes + ":" + seconds;
//                Log.d(TAG, "current minutes: " + minutes);
//                Log.d(TAG, "workoutTime minutes: " + workoutTime);
//                if (workoutTime.equals(String.valueOf(minutes))) {
//                    iv.setImageResource(R.drawable.markertick1);
//                    ArrayList<TodaysWorkout> todaysWorkoutTables = (ArrayList<TodaysWorkout>) TodaysWorkout.listAll(TodaysWorkout.class);
//                    for (TodaysWorkout table : todaysWorkoutTables) {
//                        if (table.getMyWorkoutProgramId().toString().equals(myWorkoutId)){
//                            table.status = "done";
//                            table.save();
//                        }
//
//                    }
//                    simpleChronometer.stop();
//                    notifyDataSetChanged();
//
//
//                }
////                arg0.setText(currentTime);
//                elapsedTime = elapsedTime + 1000;
//
//            }
//
//        });

//        start.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d(TAG, " Clicked start");
//                simpleChronometer.start();
//            }
//        });
//
//        stop.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d(TAG, " Clicked Onstop");
//                simpleChronometer.stop();
//            }
//        });
//
//        restart.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                Log.d(TAG, " Clicked restart");
//                simpleChronometer.setBase(SystemClock.elapsedRealtime());
//            }
//        });
        final int oneMin = 1 * 60 * 1000;
        final int inputMin = Integer.parseInt(workoutTime) * 60 * 1000;
        int interval = 100;
        if (Integer.parseInt(workoutTime) == 2) {
            interval = 200;
        } else {
            interval = 500;
        }
        final int finalInterval = interval;
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myCountDownTimer = new MyCountDownTimer(inputMin, finalInterval, progressBar, iv, time,
                        Integer.parseInt(workoutTime) * 6, myWorkoutId);
                myCountDownTimer.start();
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, " Clicked stop");
                myCountDownTimer.cancel();

            }
        });

        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myCountDownTimer = new MyCountDownTimer(inputMin, finalInterval, progressBar, iv, time,
                        Integer.parseInt(workoutTime) * 6, myWorkoutId);
                myCountDownTimer.start();
            }
        });

        return vi;
    }

    public class MyCountDownTimer extends CountDownTimer {
        ProgressBar progressBar;
        ImageView iv;
        TextView time;
        int factor;
        String workoutId;

        public MyCountDownTimer(long millisInFuture, long countDownInterval,
                                final ProgressBar progressBar,
                                final ImageView iv,
                                final TextView time, int n, String myWorkoutId) {
            super(millisInFuture, countDownInterval);
            this.progressBar = progressBar;
            this.time = time;
            this.iv = iv;
            this.factor = n;
            this.workoutId = myWorkoutId;
        }

        @Override
        public void onTick(long millisUntilFinished) {
            String timeText = String.format("%d min, %d sec",
                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                    TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))
            );
            time.setText(timeText);
            int progress = ((int) (millisUntilFinished / 100))/factor;
            progressBar.setProgress(progress);
            Log.d(TAG, "progress:" + progress);
        }

        @Override
        public void onFinish() {
            time.setText("Done");
            iv.setImageResource(R.drawable.markertick1);
            progressBar.setProgress(0);
            ArrayList<TodaysWorkout> todaysWorkoutTables = (ArrayList<TodaysWorkout>) TodaysWorkout.listAll(TodaysWorkout.class);
                    for (TodaysWorkout table : todaysWorkoutTables) {
                        if (table.getMyWorkoutProgramId().toString().equals(workoutId)){
                            table.status = "done";
                            table.save();
                        }

                    }
        }
    }

}


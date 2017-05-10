package com.deepti.amifit.view;

import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
//import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.deepti.amifit.R;
import com.deepti.amifit.model.Steps;
import com.deepti.amifit.model.Weight;
import com.deepti.amifit.util.CalendarUtility;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by deepti on 26/12/16.
 */

public class DaySummary extends Fragment implements View.OnTouchListener {
    RelativeLayout layout;
    View v;
    private GestureDetector gestureDetector;
    public static String TAG = DaySummary.class.getSimpleName();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
    }

    @Override
    public View onCreateView(final LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        layout = (RelativeLayout) inflater.inflate(R.layout.day_summary, container, false);
        RelativeLayout touchLayout = (RelativeLayout) layout.findViewById(R.id.touch_layout);
        gestureDetector = new GestureDetector(getActivity(),
                new GestureDetector.SimpleOnGestureListener() {

                    @Override
                    public boolean onDown(MotionEvent e) {
                        return true;
                    }

                    @Override
                    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                                           float velocityY) {
                        final int SWIPE_MIN_DISTANCE = 120;
                        final int SWIPE_MAX_OFF_PATH = 250;
                        final int SWIPE_THRESHOLD_VELOCITY = 200;
                        try {
                            if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
                                return false;
                            if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE
                                    && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                                Log.d(TAG, "right to left");
                            } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE
                                    && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                                Log.d(TAG, "left to right");
                                getActivity().onBackPressed();
                            }
                        } catch (Exception e) {
                            // nothing
                        }
                        return super.onFling(e1, e2, velocityX, velocityY);
                    }
                });

//        layout.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                Log.d(TAG, "In onTouch");
//                return gestureDetector.onTouchEvent(event);
//            }
//        });
        touchLayout.setOnTouchListener(this);

        Typeface customFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/android_7.ttf");
        TextView summaryTitle = (TextView) layout.findViewById(R.id.todays_title);
        TextView weight = (TextView) layout.findViewById(R.id.todays_weight);
        TextView steps = (TextView) layout.findViewById(R.id.no_of_steps);
        TextView remaingTitle = (TextView) layout.findViewById(R.id.remaining_steps_title);
        TextView prefixTitle = (TextView) layout.findViewById(R.id.s1);
        TextView rSteps = (TextView) layout.findViewById(R.id.remainsteps);

        summaryTitle.setTypeface(customFont);
        weight.setTypeface(customFont);
        remaingTitle.setTypeface(customFont);
        rSteps.setTypeface(customFont);
        steps.setTypeface(customFont);
        prefixTitle.setTypeface(customFont);
        Date today = CalendarUtility.getCurrentDate();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        String todaysDate = sdf1.format(today);

        ArrayList<Steps> l = (ArrayList<Steps>) Steps.findWithQuery(Steps.class, "select * from Steps where time = ? ", todaysDate);
        int todaySteps = 0, total = 0, remainingsteps = 0;
        float totalWeight = 0;
        if (l.size() > 0)
            for (Steps step : l) {
                total += step.getNumberOfSteps();
            }
        else {
            l = (ArrayList<Steps>) Steps.listAll(Steps.class);
            if (l.size() == 1)
                total += l.get(0).getNumberOfSteps();
        }

        ArrayList<Weight> w = (ArrayList<Weight>) Weight.findWithQuery(Weight.class, "select * from Weight where time = ? ", todaysDate);
        if (w.size() > 0)
            for (Weight ww : w) {
                totalWeight += ww.getCurrentWeight();
            }
        else {

            ArrayList<Weight> wLatest = (ArrayList<Weight>) Weight.findWithQuery(Weight.class, "select * from Weight limit 1");
            if (wLatest.size() == 1) {
                for (Weight ww : wLatest) {
                    totalWeight = ww.getCurrentWeight();
                }
            }


        }

        if (total > 0)
            todaySteps = total / l.size();

        remainingsteps = 10000 - todaySteps;
        steps.setText("" + todaySteps + "");
        weight.setText("" + totalWeight + " kg");

        if (remainingsteps < 0) {
            remainingsteps = Math.abs(remainingsteps);
            prefixTitle.setText("You are");
            rSteps.setText("" + remainingsteps + "");
            remaingTitle.setText("steps ahead of your target");
        } else {
            rSteps.setText("" + remainingsteps + "");
        }

        return layout;

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

//    @Override
//    public boolean onTouch(View v, MotionEvent event) {
//        Log.d(TAG, "in OnTouch");
//        return true;
//    }
}

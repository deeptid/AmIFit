package com.deepti.amifit.view;

import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by deepti on 10/01/17.
 */

public class SwipeGestureDetector extends GestureDetector.SimpleOnGestureListener {
    // Swipe properties, you can change it to make the swipe
    // longer or shorter and speed
    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_MAX_OFF_PATH = 200;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
    MainActivity activity;
    DrawerLayout drawerLayout;


    public SwipeGestureDetector(MainActivity mainActivity) {
        this.activity = mainActivity;
    }

    public SwipeGestureDetector(MainActivity mainActivity, DrawerLayout drawerLayout) {
        this.activity = mainActivity;
        this.drawerLayout = drawerLayout;
    }



    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2,
                           float velocityX, float velocityY) {
        try {
            float diffAbs = Math.abs(e1.getY() - e2.getY());
            float diff = e1.getX() - e2.getX();
            float diffY = e2.getY() - e1.getY();
            float sensitvity = 50;
            final float yDistance = Math.abs(e1.getY() - e2.getY());
            if (diffAbs > SWIPE_MAX_OFF_PATH)
                return false;
            Log.d("MAIN","Swipe: " + (e1.getY() - e2.getY()));

            if((e1.getY() - e2.getY()) > sensitvity){
                Log.d("MAIN","Swipe Up" + (e1.getY() - e2.getY()));
            }else if((e2.getY() - e1.getY()) > sensitvity){
                Log.d("MAIN","Swipe Down" + (e1.getY() - e2.getY()));
                activity.onDownSwipe();
            }

            // Left swipe
            if (diff > SWIPE_MIN_DISTANCE
                    && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                activity.onLeftSwipe();
                // Right swipe
            } else if (-diff > SWIPE_MIN_DISTANCE
                    && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                activity.onRightSwipe();
            }
// else if (diffY > SWIPE_MIN_DISTANCE
//                    && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
//                Log.d("MAIN", "Down Swipe");
//                activity.onDownSwipe();
//            }
        } catch (Exception e) {
            Log.e("YourActivity", "Error on gestures");
        }
        return false;
    }
}
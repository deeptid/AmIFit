package com.deepti.amifit.view.Workout;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;

import com.deepti.amifit.R;
import com.deepti.amifit.model.Workout;
import com.deepti.amifit.util.DeviceCapabilities;
import com.deepti.amifit.view.DaySummary;
import com.deepti.amifit.view.Workout.Programs.AbsWorkout;
import com.deepti.amifit.view.Workout.Programs.CardioProgram;
import com.deepti.amifit.view.Workout.Programs.ExerciseBallWorkout;
import com.deepti.amifit.view.Workout.Programs.FullBodyWorkout;
import com.deepti.amifit.view.Workout.Programs.GymWorkout;
import com.deepti.amifit.view.Workout.Programs.HomeWorkout;

import java.util.ArrayList;

/**
 * Created by deepti on 10/01/17.
 */

public class WorkoutListFragment extends Fragment implements AdapterView.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = WorkoutListFragment.class.getSimpleName();
    WorkoutListAdapter workoutListAdapter;
    GridView workoutlistView;
    RelativeLayout layout;
    ArrayList<Workout> workoutsArrayListData = new ArrayList<Workout>();
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private GestureDetector gestureDetector;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        layout = (RelativeLayout) inflater.inflate(R.layout.workout_list_fragment, container, false);
        workoutlistView = (GridView) layout.findViewById(R.id.fav_workout_list);
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
                        final int SWIPE_MAX_OFF_PATH = 120;
                        final int SWIPE_THRESHOLD_VELOCITY = 200;
                        try {
                            if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
                                getActivity().onBackPressed();
                            if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE
                                    && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                                Log.d(TAG, "right to left");
                            } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE
                                    && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                                Log.d(TAG, "left to right");
//                                getActivity().onBackPressed();
                            }
                        } catch (Exception e) {
                            // nothing
                        }
                        return super.onFling(e1, e2, velocityX, velocityY);
                    }
                });

        workoutlistView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        setColumnsAndWidth();
        workoutsArrayListData = (ArrayList<Workout>) Workout.listAll(Workout.class);
        if (workoutsArrayListData.size() > 0)
            for (Workout w : workoutsArrayListData) {
                Log.d(TAG, "Workout Title: " + w.getTitle());
            }

        workoutListAdapter = new WorkoutListAdapter(this.getActivity());
        workoutlistView.setAdapter((ListAdapter) workoutListAdapter);

        mSwipeRefreshLayout = (SwipeRefreshLayout) layout.findViewById(R.id.swipe_workout_lists);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        if (workoutsArrayListData != null) {
            refreshWorkoutList(workoutsArrayListData);
        }

        workoutlistView.setOnItemClickListener(this);
        return layout;
    }

    private void setColumnsAndWidth() {
        int num;
        int cell_width = DeviceCapabilities.getScreenSizeInDP(getActivity().getBaseContext()).x / 2; // minimum width expected for cell
        int gap = 0;
        int width = DeviceCapabilities.getScreenSizeInDP(getActivity().getBaseContext()).x;
        num = width / cell_width;
        cell_width = (width - (gap * num - 1)) / num;
        DisplayMetrics dm = getResources().getDisplayMetrics();
        float dpInPx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, cell_width, dm);
        workoutlistView.setNumColumns(num);
        workoutlistView.setColumnWidth(Math.round(dpInPx));
        workoutlistView.setMinimumHeight(Math.round(dpInPx));

        Log.d(TAG, "Cell size = " + dpInPx);

        // channellistView.setMinimumHeight(cell_width);

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch(position){
            case 0:
                CardioProgram newFragment = new CardioProgram();
                String tag = CardioProgram.class.getSimpleName();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.full_screen_workout, newFragment, tag);
                ft.addToBackStack(tag);
                ft.commit();
                break;
            case 1:
                HomeWorkout newFragment1 = new HomeWorkout();
                String tag1 = HomeWorkout.class.getSimpleName();
                FragmentManager fm1 = getFragmentManager();
                FragmentTransaction ft1 = fm1.beginTransaction();
                ft1.replace(R.id.full_screen_workout, newFragment1, tag1);
                ft1.addToBackStack(tag1);
                ft1.commit();
                break;

            case 2:
                AbsWorkout newFragment2 = new AbsWorkout();
                String tag2 = HomeWorkout.class.getSimpleName();
                FragmentManager fm2 = getFragmentManager();
                FragmentTransaction ft2 = fm2.beginTransaction();
                ft2.replace(R.id.full_screen_workout, newFragment2, tag2);
                ft2.addToBackStack(tag2);
                ft2.commit();
                break;
            case 3:
                ExerciseBallWorkout newFragment3 = new ExerciseBallWorkout();
                String tag3 = ExerciseBallWorkout.class.getSimpleName();
                FragmentManager fm3 = getFragmentManager();
                FragmentTransaction ft3 = fm3.beginTransaction();
                ft3.replace(R.id.full_screen_workout, newFragment3, tag3);
                ft3.addToBackStack(tag3);
                ft3.commit();
                break;
            case 4:
                FullBodyWorkout newFragment4 = new FullBodyWorkout();
                String tag4 = HomeWorkout.class.getSimpleName();
                FragmentManager fm4 = getFragmentManager();
                FragmentTransaction ft4 = fm4.beginTransaction();
                ft4.replace(R.id.full_screen_workout, newFragment4, tag4);
                ft4.addToBackStack(tag4);
                ft4.commit();
                break;
            case 5:
                GymWorkout newFragment5 = new GymWorkout();
                String tag5 = GymWorkout.class.getSimpleName();
                FragmentManager fm5 = getFragmentManager();
                FragmentTransaction ft5 = fm5.beginTransaction();
                ft5.replace(R.id.full_screen_workout, newFragment5, tag5);
                ft5.addToBackStack(tag5);
                ft5.commit();
                break;
        }

    }

    public void refreshWorkoutList(ArrayList<Workout> workoutsArrayList) {
        workoutListAdapter.resetData();
        workoutListAdapter.updateData(workoutsArrayList);
        workoutListAdapter.notifyDataSetChanged();

        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }

        workoutlistView.invalidate();
    }

    @Override
    public void onRefresh() {
        refreshWorkoutList(workoutsArrayListData);
    }
}
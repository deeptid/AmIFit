package com.deepti.amifit.view.Workout;

import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.deepti.amifit.R;
import com.deepti.amifit.model.MyWorkoutProgram;
import com.deepti.amifit.model.TodaysWorkout;
import com.deepti.amifit.util.CalendarUtility;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by deepti on 23/01/17.
 */

public class TodaysWorkoutPlan  extends Fragment {

    public static String TAG = TodaysWorkoutPlan.class.getSimpleName();
    static final String KEY_TITLE = "title";
    static final String KEY_DESC = "desc";
    static final String KEY_TIME = "time";
    static final String KEY_ID = "id";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        ScrollView layout = (ScrollView) inflater.inflate(R.layout.todays_workout, container, false);
        Typeface customFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/helvetica-neue-ultra-light.ttf");
        TextView title = (TextView) layout.findViewById(R.id.todays_workout_title);
        ListView list = (ListView) layout.findViewById(R.id.list);
        ArrayList<HashMap<String, String>> mapList = new ArrayList<HashMap<String, String>>();
        title.setTypeface(customFont, Typeface.BOLD);

        String todaysDay = CalendarUtility.getTodaysDay();

        ArrayList<MyWorkoutProgram> wps1 = (ArrayList<MyWorkoutProgram>) MyWorkoutProgram.listAll(MyWorkoutProgram.class);
        ArrayList<TodaysWorkout> twps1 = (ArrayList<TodaysWorkout>) TodaysWorkout.listAll(TodaysWorkout.class);
        List<Long> wIds =  new ArrayList<Long>();
        for (TodaysWorkout t : twps1) {
            wIds.add(t.getMyWorkoutProgramId());
        }
        if (wps1.size() > 0)
            for (MyWorkoutProgram w : wps1) {
                if (w.getDayOfTheWeek().equals(todaysDay)) {
                    if(! wIds.contains(w.getId())){
                        TodaysWorkout tw =  new TodaysWorkout(w.getId(), "not_done");
                        tw.save();
                    }
                }
            }

        Log.d(TAG, "todaysDay: " + todaysDay);

        ArrayList<MyWorkoutProgram> wps = (ArrayList<MyWorkoutProgram>) MyWorkoutProgram.listAll(MyWorkoutProgram.class);
        int total = 0;
        if (wps.size() > 0)
            for (MyWorkoutProgram w : wps) {
                if (w.getDayOfTheWeek().equals(todaysDay)) {
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put(KEY_TITLE, w.getName());
                    map.put(KEY_DESC, w.getType());
                    map.put(KEY_TIME, w.getTime());
                    map.put(KEY_ID, String.valueOf(w.getId()));
                    mapList.add(map);
                }
            }
        if (mapList.size() > 0) {
            TodaysWorkoutViewAdapter adapter = new TodaysWorkoutViewAdapter(getActivity(), mapList);
            list.setAdapter(adapter);
        }
        ImageView iv = (ImageView) layout.findViewById(R.id.congrats);
        iv.setVisibility(View.GONE);
        boolean allDone = false;

        ArrayList<TodaysWorkout> twps = (ArrayList<TodaysWorkout>) TodaysWorkout.listAll(TodaysWorkout.class);
//        List<Long> wIds =  new ArrayList<Long>();
        for (TodaysWorkout t : twps) {
            if(t.getStatus().equals("done")){
                allDone = true;
            } else{
                allDone = false;
            }

//            wIds.add(t.getMyWorkoutProgramId());
        }
        if(allDone){
            iv.setVisibility(View.VISIBLE);
        }

        return layout;
    }


    }

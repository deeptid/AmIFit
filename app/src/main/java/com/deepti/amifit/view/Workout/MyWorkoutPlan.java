package com.deepti.amifit.view.Workout;

import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.deepti.amifit.R;
import com.deepti.amifit.model.Exercise;
import com.deepti.amifit.model.MyWorkoutProgram;
import com.deepti.amifit.model.Weight;
import com.deepti.amifit.view.Workout.Programs.CardioProgram;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by deepti on 18/01/17.
 */

public class MyWorkoutPlan extends Fragment {

    public static String TAG = MyWorkoutPlan.class.getSimpleName();
    static final String KEY_TITLE = "title";
    static final String KEY_DESC = "desc";
    static final String KEY_TIME = "time";
    static final String KEY_ID = "id";
    LazyAdapter adapterMonday;
    LazyAdapter adapterTuesday;
    LazyAdapter adapterWednesday;
    LazyAdapter adapterThursday;
    LazyAdapter adapterFriday;
    LazyAdapter adapterSaturday;
    LazyAdapter adapterSunday;
    ListView listMonday;
    ListView listTuesday;
    ListView listWednesday;
    ListView listThursday;
    ListView listFriday;
    ListView listSaturday;
    ListView listSunday;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        ScrollView layout = (ScrollView) inflater.inflate(R.layout.my_workout_plan, container, false);
        Typeface customFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/helvetica-neue-ultra-light.ttf");
        TextView title = (TextView) layout.findViewById(R.id.my_plan_title);

//Monday
        TextView title_monday = (TextView) layout.findViewById(R.id.monday);
        TextView time = (TextView) layout.findViewById(R.id.time);
        TextView totalTitle = (TextView) layout.findViewById(R.id.total_title);

        title.setTypeface(customFont, Typeface.BOLD);
        title_monday.setTypeface(customFont, Typeface.BOLD);
        time.setTypeface(customFont, Typeface.BOLD_ITALIC);
        totalTitle.setTypeface(customFont, Typeface.BOLD_ITALIC);

        listMonday = (ListView) layout.findViewById(R.id.list_monday);
        ArrayList<HashMap<String, String>> mapListMonday = new ArrayList<HashMap<String, String>>();

        ArrayList<MyWorkoutProgram> wps = (ArrayList<MyWorkoutProgram>) MyWorkoutProgram.listAll(MyWorkoutProgram.class);
        if (wps.size() > 0)
            for (MyWorkoutProgram w : wps) {
                Log.d(TAG, "MyWorkoutProgram w:" + w.getDayOfTheWeek());
                Log.d(TAG, "MyWorkoutProgram w id:" + String.valueOf(w.getId()));
            }
        int total = 0;
        if (wps.size() > 0)
            for (MyWorkoutProgram w : wps) {
                if (w.getDayOfTheWeek().equals("Monday")) {
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put(KEY_TITLE, w.getName());
                    map.put(KEY_DESC, w.getType());
                    map.put(KEY_TIME, w.getTime());
                    map.put(KEY_ID, String.valueOf(w.getId()));
                    mapListMonday.add(map);
                    total += Integer.parseInt(w.getTime());
                }
            }

        if (total != 0) {
            time.setText(total + " min");
        } else {
            RelativeLayout totalLayout = (RelativeLayout) layout.findViewById(R.id.total_layout);
            totalLayout.setVisibility(View.GONE);
        }


        if (mapListMonday.size() > 0) {
            Log.d(TAG, "mapList:" + mapListMonday);
            adapterMonday = new LazyAdapter(getActivity(), mapListMonday);
            listMonday.setAdapter(adapterMonday);
        }


//Tuesday
        TextView titleTues = (TextView) layout.findViewById(R.id.tuesday);
        TextView timeTues = (TextView) layout.findViewById(R.id.time_tuesday);
        TextView totalTitleTues = (TextView) layout.findViewById(R.id.total_title_tuesday);

        titleTues.setTypeface(customFont, Typeface.BOLD);
        timeTues.setTypeface(customFont, Typeface.BOLD_ITALIC);
        totalTitleTues.setTypeface(customFont, Typeface.BOLD_ITALIC);

        listTuesday = (ListView) layout.findViewById(R.id.list_tuesday);
        ArrayList<HashMap<String, String>> mapListTuesday = new ArrayList<HashMap<String, String>>();

        int totalTues = 0;
        if (wps.size() > 0)
            for (MyWorkoutProgram w : wps) {
                if (w.getDayOfTheWeek().equals("Tuesday")) {
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put(KEY_TITLE, w.getName());
                    map.put(KEY_DESC, w.getType());
                    map.put(KEY_TIME, w.getTime());
                    map.put(KEY_ID, String.valueOf(w.getId()));
                    mapListTuesday.add(map);
                    totalTues += Integer.parseInt(w.getTime());
                }
            }

        if (totalTues != 0) {
            timeTues.setText(totalTues + " min");
        } else {
            RelativeLayout totalLayout = (RelativeLayout) layout.findViewById(R.id.total_layout_tuesday);
            totalLayout.setVisibility(View.GONE);
        }


        if (mapListTuesday.size() > 0) {
            Log.d(TAG, "mapListTuesday:" + mapListTuesday);
            adapterTuesday = new LazyAdapter(getActivity(), mapListTuesday);
            listTuesday.setAdapter(adapterTuesday);
        }

//Wednesday

        TextView titleWednesday = (TextView) layout.findViewById(R.id.wednesday);
        TextView timeWednesday = (TextView) layout.findViewById(R.id.time_wednesday);
        TextView totalTitleWednesday = (TextView) layout.findViewById(R.id.total_title_wednesday);

        titleWednesday.setTypeface(customFont, Typeface.BOLD);
        timeWednesday.setTypeface(customFont, Typeface.BOLD_ITALIC);
        totalTitleWednesday.setTypeface(customFont, Typeface.BOLD_ITALIC);

        listWednesday = (ListView) layout.findViewById(R.id.list_wednesday);
        ArrayList<HashMap<String, String>> mapListWednesday = new ArrayList<HashMap<String, String>>();

        int totalWednesday = 0;
        if (wps.size() > 0)
            for (MyWorkoutProgram w : wps) {
                Log.d(TAG, " listWednesday w.getDayOfTheWeek" + w.getDayOfTheWeek());
                if (w.getDayOfTheWeek().equals("Wednesday")) {
                    Log.d(TAG, " After Adding: w.getDayOfTheWeek" + w.getName() + " : "+  w.getTime() + " : " + w.getType());
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put(KEY_TITLE, w.getName());
                    map.put(KEY_DESC, w.getType());
                    map.put(KEY_TIME, w.getTime());
                    map.put(KEY_ID, String.valueOf(w.getId()));
                    mapListWednesday.add(map);
                    totalWednesday += Integer.parseInt(w.getTime());
                }
            }

        if (totalWednesday != 0) {
            timeWednesday.setText(totalWednesday + " min");
        } else {
            RelativeLayout totalLayout = (RelativeLayout) layout.findViewById(R.id.total_layout_wednesday);
            totalLayout.setVisibility(View.GONE);
        }


        if (mapListWednesday.size() > 0) {
            Log.d(TAG, "mapListWednesday:" + mapListWednesday);
            adapterWednesday = new LazyAdapter(getActivity(), mapListWednesday);
            listWednesday.setAdapter(adapterWednesday);
        }


//Thursday
        TextView titleThursday = (TextView) layout.findViewById(R.id.thursday);
        TextView timeThursday = (TextView) layout.findViewById(R.id.time_thursday);
        TextView totalTitleThursday = (TextView) layout.findViewById(R.id.total_title_thursday);

        titleThursday.setTypeface(customFont, Typeface.BOLD);
        timeThursday.setTypeface(customFont, Typeface.BOLD_ITALIC);
        totalTitleThursday.setTypeface(customFont, Typeface.BOLD_ITALIC);

        listThursday = (ListView) layout.findViewById(R.id.list_thursday);
        ArrayList<HashMap<String, String>> mapListThursday = new ArrayList<HashMap<String, String>>();

        int totalThursday = 0;
        if (wps.size() > 0)
            for (MyWorkoutProgram w : wps) {
                if (w.getDayOfTheWeek().equals("Thursday")) {
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put(KEY_TITLE, w.getName());
                    map.put(KEY_DESC, w.getType());
                    map.put(KEY_TIME, w.getTime());
                    map.put(KEY_ID, String.valueOf(w.getId()));
                    mapListThursday.add(map);
                    totalThursday += Integer.parseInt(w.getTime());
                }
            }

        if (totalThursday != 0) {
            timeThursday.setText(totalThursday + " min");
        } else {
            RelativeLayout totalLayout = (RelativeLayout) layout.findViewById(R.id.total_layout_thursday);
            totalLayout.setVisibility(View.GONE);
        }


        if (mapListThursday.size() > 0) {
            Log.d(TAG, "mapListThursday:" + mapListThursday);
            adapterThursday = new LazyAdapter(getActivity(), mapListThursday);
            listThursday.setAdapter(adapterThursday);
        }

//Friday

        TextView titleFriday = (TextView) layout.findViewById(R.id.friday);
        TextView timeFriday = (TextView) layout.findViewById(R.id.time_friday);
        TextView totalTitleFriday = (TextView) layout.findViewById(R.id.total_title_friday);

        titleFriday.setTypeface(customFont, Typeface.BOLD);
        timeFriday.setTypeface(customFont, Typeface.BOLD_ITALIC);
        totalTitleFriday.setTypeface(customFont, Typeface.BOLD_ITALIC);

        listFriday = (ListView) layout.findViewById(R.id.list_friday);
        ArrayList<HashMap<String, String>> mapListFriday = new ArrayList<HashMap<String, String>>();

        int totalFriday = 0;
        if (wps.size() > 0)
            for (MyWorkoutProgram w : wps) {
                if (w.getDayOfTheWeek().equals("Friday")) {
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put(KEY_TITLE, w.getName());
                    map.put(KEY_DESC, w.getType());
                    map.put(KEY_TIME, w.getTime());
                    map.put(KEY_ID, String.valueOf(w.getId()));
                    mapListFriday.add(map);
                    totalFriday += Integer.parseInt(w.getTime());
                }
            }

        if (totalFriday != 0) {
            timeFriday.setText(totalFriday + " min");
        } else {
            RelativeLayout totalLayout = (RelativeLayout) layout.findViewById(R.id.total_layout_friday);
            totalLayout.setVisibility(View.GONE);
        }


        if (mapListFriday.size() > 0) {
            Log.d(TAG, "mapListFriday:" + mapListFriday);
            adapterFriday = new LazyAdapter(getActivity(), mapListFriday);
            listFriday.setAdapter(adapterFriday);
        }


//Saturday


        TextView titleSaturday = (TextView) layout.findViewById(R.id.saturday);
        TextView timeSaturday = (TextView) layout.findViewById(R.id.time_saturday);
        TextView totalTitleSaturday = (TextView) layout.findViewById(R.id.total_title_saturday);

        titleSaturday.setTypeface(customFont, Typeface.BOLD);
        timeSaturday.setTypeface(customFont, Typeface.BOLD_ITALIC);
        totalTitleSaturday.setTypeface(customFont, Typeface.BOLD_ITALIC);

        listSaturday = (ListView) layout.findViewById(R.id.list_saturday);
        ArrayList<HashMap<String, String>> mapListSaturday = new ArrayList<HashMap<String, String>>();

        int totalSaturday = 0;
        if (wps.size() > 0)
            for (MyWorkoutProgram w : wps) {
                if (w.getDayOfTheWeek().equals("Saturday")) {
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put(KEY_TITLE, w.getName());
                    map.put(KEY_DESC, w.getType());
                    map.put(KEY_TIME, w.getTime());
                    map.put(KEY_ID, String.valueOf(w.getId()));
                    mapListSaturday.add(map);
                    totalSaturday += Integer.parseInt(w.getTime());
                }
            }

        if (totalSaturday != 0) {
            timeSaturday.setText(totalSaturday + " min");
        } else {
            RelativeLayout totalLayout = (RelativeLayout) layout.findViewById(R.id.total_layout_saturday);
            totalLayout.setVisibility(View.GONE);
        }


        if (mapListSaturday.size() > 0) {
            Log.d(TAG, "mapListSaturday:" + mapListSaturday);
            adapterSaturday = new LazyAdapter(getActivity(), mapListSaturday);
            listSaturday.setAdapter(adapterSaturday);
        }

//Sunday


        TextView titleSunday = (TextView) layout.findViewById(R.id.sunday);
        TextView timeSunday = (TextView) layout.findViewById(R.id.time_sunday);
        TextView totalTitleSunday = (TextView) layout.findViewById(R.id.total_title_sunday);

        titleSunday.setTypeface(customFont, Typeface.BOLD);
        timeSunday.setTypeface(customFont, Typeface.BOLD_ITALIC);
        totalTitleSunday.setTypeface(customFont, Typeface.BOLD_ITALIC);

        listSunday = (ListView) layout.findViewById(R.id.list_sunday);
        ArrayList<HashMap<String, String>> mapListSunday = new ArrayList<HashMap<String, String>>();

        int totalSunday = 0;
        if (wps.size() > 0)
            for (MyWorkoutProgram w : wps) {
                if (w.getDayOfTheWeek().equals("Sunday")) {
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put(KEY_TITLE, w.getName());
                    map.put(KEY_DESC, w.getType());
                    map.put(KEY_TIME, w.getTime());
                    map.put(KEY_ID, String.valueOf(w.getId()));
                    mapListSunday.add(map);
                    totalSunday += Integer.parseInt(w.getTime());
                }
            }

        if (totalSunday != 0) {
            timeSunday.setText(totalSunday + " min");
        } else {
            RelativeLayout totalLayout = (RelativeLayout) layout.findViewById(R.id.total_layout_sunday);
            totalLayout.setVisibility(View.GONE);
        }


        if (mapListSunday.size() > 0) {
            Log.d(TAG, "mapListSunday:" + mapListSunday);
            adapterSunday = new LazyAdapter(getActivity(), mapListSunday);
            listSunday.setAdapter(adapterSunday);
        } else {
            TextView tv = new TextView(getActivity());
            tv.setHeight(20);
            tv.setTextSize(17);
            tv.setText("Rest Day!");
            tv.setTypeface(customFont, Typeface.BOLD);
            RelativeLayout.LayoutParams relativeParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 50);
            relativeParams.addRule(RelativeLayout.BELOW, R.id.l7);
            relativeParams.setMargins(100, 10, 0, 30);
//            tv.setPadding(10,10,10,10);
            tv.setLayoutParams(relativeParams);
            RelativeLayout containerLayout = (RelativeLayout) layout.findViewById(R.id.container_layout);
            containerLayout.addView(tv);
        }
        return layout;
    }

//    @Override
//    public void onResume() {
//        Log.d(TAG, "On Resume");
//        ArrayList<MyWorkoutProgram> wps = (ArrayList<MyWorkoutProgram>) MyWorkoutProgram.listAll(MyWorkoutProgram.class);
//        ArrayList<HashMap<String, String>> mapListMonday = new ArrayList<HashMap<String, String>>();
//        ArrayList<HashMap<String, String>> mapListTuesday= new ArrayList<HashMap<String, String>>();
//        ArrayList<HashMap<String, String>> mapListWednesday = new ArrayList<HashMap<String, String>>();
//        ArrayList<HashMap<String, String>> mapListThursday = new ArrayList<HashMap<String, String>>();
//        ArrayList<HashMap<String, String>> mapListFriday = new ArrayList<HashMap<String, String>>();
//        ArrayList<HashMap<String, String>> mapListSaturday = new ArrayList<HashMap<String, String>>();
//        ArrayList<HashMap<String, String>> mapListSunday = new ArrayList<HashMap<String, String>>();
//
//        if (adapterMonday != null) {
//            if (wps.size() > 0)
//                for (MyWorkoutProgram w : wps) {
//                    if (w.getDayOfTheWeek().equals("Monday")) {
//                        HashMap<String, String> map = new HashMap<String, String>();
//                        map.put(KEY_TITLE, w.getName());
//                        map.put(KEY_DESC, w.getType());
//                        map.put(KEY_TIME, w.getTime());
//                        map.put(KEY_ID, String.valueOf(w.getId()));
//                        mapListMonday.add(map);
//                    }
//                    adapterMonday.swapItems(mapListMonday);
//                }
//        }
//        if (adapterTuesday != null) {
//            if (wps.size() > 0)
//                for (MyWorkoutProgram w : wps) {
//                    if (w.getDayOfTheWeek().equals("Tuesday")) {
//                        HashMap<String, String> map = new HashMap<String, String>();
//                        map.put(KEY_TITLE, w.getName());
//                        map.put(KEY_DESC, w.getType());
//                        map.put(KEY_TIME, w.getTime());
//                        map.put(KEY_ID, String.valueOf(w.getId()));
//                        mapListTuesday.add(map);
//                    }
//                    adapterTuesday.swapItems(mapListTuesday);
//                }
//        }
//        if (adapterWednesday != null) {
//            if (wps.size() > 0)
//                for (MyWorkoutProgram w : wps) {
//                    if (w.getDayOfTheWeek().equals("Wednesday")) {
//                        HashMap<String, String> map = new HashMap<String, String>();
//                        map.put(KEY_TITLE, w.getName());
//                        map.put(KEY_DESC, w.getType());
//                        map.put(KEY_TIME, w.getTime());
//                        map.put(KEY_ID, String.valueOf(w.getId()));
//                        mapListWednesday.add(map);
//                    }
//                    adapterWednesday.swapItems(mapListWednesday);
//                }
//        }
//        if (adapterThursday != null) {
//            if (wps.size() > 0)
//                for (MyWorkoutProgram w : wps) {
//                    if (w.getDayOfTheWeek().equals("Thursday")) {
//                        HashMap<String, String> map = new HashMap<String, String>();
//                        map.put(KEY_TITLE, w.getName());
//                        map.put(KEY_DESC, w.getType());
//                        map.put(KEY_TIME, w.getTime());
//                        map.put(KEY_ID, String.valueOf(w.getId()));
//                        mapListThursday.add(map);
//                    }
//                    adapterThursday.swapItems(mapListThursday);
//                }
//        }
//        if (adapterFriday != null) {
//            if (wps.size() > 0)
//                for (MyWorkoutProgram w : wps) {
//                    if (w.getDayOfTheWeek().equals("Friday")) {
//                        HashMap<String, String> map = new HashMap<String, String>();
//                        map.put(KEY_TITLE, w.getName());
//                        map.put(KEY_DESC, w.getType());
//                        map.put(KEY_TIME, w.getTime());
//                        map.put(KEY_ID, String.valueOf(w.getId()));
//                        mapListFriday.add(map);
//                    }
//                    adapterFriday.swapItems(mapListFriday);
//                }
//        }
//        if (adapterSaturday != null) {
//            if (wps.size() > 0)
//                for (MyWorkoutProgram w : wps) {
//                    if (w.getDayOfTheWeek().equals("Saturday")) {
//                        HashMap<String, String> map = new HashMap<String, String>();
//                        map.put(KEY_TITLE, w.getName());
//                        map.put(KEY_DESC, w.getType());
//                        map.put(KEY_TIME, w.getTime());
//                        map.put(KEY_ID, String.valueOf(w.getId()));
//                        mapListSaturday.add(map);
//                    }
//                    adapterSaturday.swapItems(mapListSaturday);
//                }
//        }
//        if (adapterSunday != null) {
//            if (wps.size() > 0)
//                for (MyWorkoutProgram w : wps) {
//                    if (w.getDayOfTheWeek().equals("Sunday")) {
//                        HashMap<String, String> map = new HashMap<String, String>();
//                        map.put(KEY_TITLE, w.getName());
//                        map.put(KEY_DESC, w.getType());
//                        map.put(KEY_TIME, w.getTime());
//                        map.put(KEY_ID, String.valueOf(w.getId()));
//                        mapListSunday.add(map);
//                    }
//                    adapterSunday.swapItems(mapListSunday);
//                }
//        }
//
//        super.onResume();
//    }
}

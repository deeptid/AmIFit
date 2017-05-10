package com.deepti.amifit.view.Workout.Programs;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.deepti.amifit.R;
import com.deepti.amifit.Widgets.MultiSelectionSpinner;
import com.deepti.amifit.model.Exercise;
import com.deepti.amifit.model.MyWorkoutProgram;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by deepti on 26/02/17.
 */

public class ExerciseBallWorkout extends Fragment {

    public static String TAG = ExerciseBallWorkout.class.getSimpleName();
    public ArrayList<Exercise> myCardioExercises = new ArrayList<Exercise>();
    ArrayList<MyWorkoutProgram> programs = new ArrayList<MyWorkoutProgram>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();

    }

    @Override
    public View onCreateView(final LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        ScrollView layout = (ScrollView) inflater.inflate(R.layout.exercise_ball_program, container, false);
        Typeface customFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/helvetica-neue-ultra-light.ttf");
        TextView full_body_title = (TextView) layout.findViewById(R.id.full_body_title);
        TextView level1_title = (TextView) layout.findViewById(R.id.level1_title);
        TextView level2_title = (TextView) layout.findViewById(R.id.level2_title);
        TextView level3_title = (TextView) layout.findViewById(R.id.level3_title);

        full_body_title.setTypeface(customFont, Typeface.BOLD);

        level1_title.setTypeface(customFont, Typeface.BOLD);
        level2_title.setTypeface(customFont, Typeface.BOLD);
        level3_title.setTypeface(customFont, Typeface.BOLD);

        TextView add_title1 = (TextView) layout.findViewById(R.id.add_title1);
        add_title1.setTypeface(customFont, Typeface.BOLD);

        final String[] fullBodyPrograms = getResources().getStringArray(R.array.ball_program);

        final String[] day_options = getResources().getStringArray(R.array.add_to_my_program);

        ImageView iv1  = (ImageView) layout.findViewById(R.id.level1_gif_placer);
        ImageView iv2  = (ImageView) layout.findViewById(R.id.level2_gif_placer);
        ImageView iv3  = (ImageView) layout.findViewById(R.id.level3_gif_placer);

        iv1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://www.youtube.com/watch?v=qiVpPaGb430"));
                startActivity(intent);
            }
        });

        iv2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://www.youtube.com/watch?v=oXXQNbxUCd0"));
                startActivity(intent);
            }
        });

        iv3.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://www.youtube.com/watch?v=hFP1qfZEhUg"));
                startActivity(intent);
            }
        });

        MultiSelectionSpinner level1Spinner = (MultiSelectionSpinner) layout.findViewById(R.id.level1_spinner);
        level1Spinner.setItems(day_options);
        level1Spinner.setSelection("none");
        level1Spinner.setListener(new MultiSelectionSpinner.OnMultipleItemsSelectedListener() {
            @Override
            public void selectedIndices(List<Integer> indices) {
                for (int i : indices) {
                    ArrayList<Exercise> exercises = (ArrayList<Exercise>) Exercise.findWithQuery(Exercise.class, "select * from Exercise where name = ? ", fullBodyPrograms[0]);
                    for (Exercise e : exercises) {
                        MyWorkoutProgram program = new MyWorkoutProgram(e.getName(), e.getTime(), e.getType(), day_options[i]);
                        programs.add(program);
                    }
                }
            }

            @Override
            public void selectedStrings(List<String> strings) {
                Log.d(TAG, "strings: " + strings);
            }
        });

        TextView add_title2 = (TextView) layout.findViewById(R.id.add_title2);
        add_title2.setTypeface(customFont, Typeface.BOLD);


        MultiSelectionSpinner level2Spinner = (MultiSelectionSpinner) layout.findViewById(R.id.level2_spinner);
        level2Spinner.setItems(day_options);
        level2Spinner.setSelection("none");
        level2Spinner.setListener(new MultiSelectionSpinner.OnMultipleItemsSelectedListener() {
            @Override
            public void selectedIndices(List<Integer> indices) {
                for (int i : indices) {
                    ArrayList<Exercise> exercises = (ArrayList<Exercise>) Exercise.findWithQuery(Exercise.class, "select * from Exercise where name = ? ", fullBodyPrograms[1]);
                    for (Exercise e : exercises) {
                        MyWorkoutProgram program = new MyWorkoutProgram(e.getName(), e.getTime(), e.getType(), day_options[i]);
                        programs.add(program);
                    }
                }

            }

            @Override
            public void selectedStrings(List<String> strings) {

            }
        });


        TextView add_title3 = (TextView) layout.findViewById(R.id.add_title3);
        add_title3.setTypeface(customFont, Typeface.BOLD);

        MultiSelectionSpinner level3Spinner = (MultiSelectionSpinner) layout.findViewById(R.id.level3_spinner);
        level3Spinner.setItems(day_options);
        level3Spinner.setSelection("none");
        level3Spinner.setListener(new MultiSelectionSpinner.OnMultipleItemsSelectedListener() {
            @Override
            public void selectedIndices(List<Integer> indices) {
                for (int i : indices) {
                    ArrayList<Exercise> exercises = (ArrayList<Exercise>) Exercise.findWithQuery(Exercise.class, "select * from Exercise where name = ? ", fullBodyPrograms[2]);
                    for (Exercise e : exercises) {
                        MyWorkoutProgram program = new MyWorkoutProgram(e.getName(), e.getTime(), e.getType(), day_options[i]);
                        programs.add(program);
                    }
                }
            }

            @Override
            public void selectedStrings(List<String> strings) {

            }
        });

        Button submit = (Button) layout.findViewById(R.id.submit);
        Button cancel = (Button) layout.findViewById(R.id.cancel);

        submit.setTypeface(customFont, Typeface.BOLD);
        cancel.setTypeface(customFont, Typeface.BOLD);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (MyWorkoutProgram p : programs) {
                    p.save();
                }
//                ArrayList<Weight> w = (ArrayList<Weight>) Weight.findWithQuery(Weight.class, "select * from Weight where time = ?", onlyDate);
                ArrayList<MyWorkoutProgram> wps = (ArrayList<MyWorkoutProgram>) MyWorkoutProgram.listAll(MyWorkoutProgram.class);
                if (wps.size() > 0)
                    for (MyWorkoutProgram w : wps) {
                        Log.d(TAG, "MyWorkoutProgram w:" + w.getDayOfTheWeek() + " and time :" + w.getTime() + " min");
                    }
                getActivity().onBackPressed();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });


        return layout;
    }

}



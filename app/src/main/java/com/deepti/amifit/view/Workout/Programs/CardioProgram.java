package com.deepti.amifit.view.Workout.Programs;

import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.deepti.amifit.Widgets.MultiSelectionSpinner;
import com.deepti.amifit.R;
import com.deepti.amifit.model.Exercise;
import com.deepti.amifit.model.MyWorkoutProgram;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by deepti on 17/01/17.
 */

public class CardioProgram extends Fragment {
    public static String TAG = CardioProgram.class.getSimpleName();
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
        ScrollView layout = (ScrollView) inflater.inflate(R.layout.cardio_program, container, false);
        Typeface customFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/helvetica-neue-ultra-light.ttf");
        TextView cardio_title = (TextView) layout.findViewById(R.id.cardio_title);
        TextView jumping_jack_title = (TextView) layout.findViewById(R.id.jumping_jack);
        TextView jumping_rope_title = (TextView) layout.findViewById(R.id.jumping_rope);
        TextView running_title = (TextView) layout.findViewById(R.id.running);
        TextView stairmaster_title = (TextView) layout.findViewById(R.id.stairmaster);
        cardio_title.setTypeface(customFont, Typeface.BOLD);
        jumping_rope_title.setTypeface(customFont, Typeface.BOLD);
        running_title.setTypeface(customFont, Typeface.BOLD);
        stairmaster_title.setTypeface(customFont, Typeface.BOLD);
        jumping_jack_title.setTypeface(customFont, Typeface.BOLD);

        ImageView imageView1 = (ImageView) layout.findViewById(R.id.jumping_jack_gif_placer);
        GlideDrawableImageViewTarget imageViewTarget1 = new GlideDrawableImageViewTarget(imageView1);
        Glide.with(this).load(R.drawable.old_jumping_jacks).into(imageViewTarget1);

        ImageView imageView2 = (ImageView) layout.findViewById(R.id.jumping_rope_gif_placer);
        GlideDrawableImageViewTarget imageViewTarget2 = new GlideDrawableImageViewTarget(imageView2);
        Glide.with(this).load(R.drawable.jumping_rope).into(imageViewTarget2);

        ImageView imageView3 = (ImageView) layout.findViewById(R.id.running_gif_placer);
        GlideDrawableImageViewTarget imageViewTarget3 = new GlideDrawableImageViewTarget(imageView3);
        Glide.with(this).load(R.drawable.treadmill).into(imageViewTarget3);

        ImageView imageView4 = (ImageView) layout.findViewById(R.id.stairmaster_gif_placer);
        GlideDrawableImageViewTarget imageViewTarget4 = new GlideDrawableImageViewTarget(imageView4);
        Glide.with(this).load(R.drawable.stairmaster).into(imageViewTarget4);

        TextView add_title1 = (TextView) layout.findViewById(R.id.add_title1);
        add_title1.setTypeface(customFont, Typeface.BOLD);

        final String[] cardioPrograms = getResources().getStringArray(R.array.cardio_program);

        final String[] day_options = getResources().getStringArray(R.array.add_to_my_program);


        MultiSelectionSpinner jumpingJackSpinner = (MultiSelectionSpinner) layout.findViewById(R.id.jack_spinner);
        jumpingJackSpinner.setItems(day_options);
        jumpingJackSpinner.setSelection("none");
        jumpingJackSpinner.setListener(new MultiSelectionSpinner.OnMultipleItemsSelectedListener() {
            @Override
            public void selectedIndices(List<Integer> indices) {
                for (int i : indices) {
                    ArrayList<Exercise> exercises = (ArrayList<Exercise>) Exercise.findWithQuery(Exercise.class, "select * from Exercise where name = ? ", cardioPrograms[0]);
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


        MultiSelectionSpinner ropeSpinner = (MultiSelectionSpinner) layout.findViewById(R.id.rope_spinner);
        ropeSpinner.setItems(day_options);
        ropeSpinner.setSelection("none");
        ropeSpinner.setListener(new MultiSelectionSpinner.OnMultipleItemsSelectedListener() {
            @Override
            public void selectedIndices(List<Integer> indices) {
                for (int i : indices) {
                    ArrayList<Exercise> exercises = (ArrayList<Exercise>) Exercise.findWithQuery(Exercise.class, "select * from Exercise where name = ? ", cardioPrograms[1]);
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

        MultiSelectionSpinner runSpinner = (MultiSelectionSpinner) layout.findViewById(R.id.run_spinner);
        runSpinner.setItems(day_options);
        runSpinner.setSelection("none");
        runSpinner.setListener(new MultiSelectionSpinner.OnMultipleItemsSelectedListener() {
            @Override
            public void selectedIndices(List<Integer> indices) {
                for (int i : indices) {
                    ArrayList<Exercise> exercises = (ArrayList<Exercise>) Exercise.findWithQuery(Exercise.class, "select * from Exercise where name = ? ", cardioPrograms[2]);
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

        TextView add_title4 = (TextView) layout.findViewById(R.id.add_title4);
        add_title4.setTypeface(customFont, Typeface.BOLD);


        MultiSelectionSpinner stairSpinner = (MultiSelectionSpinner) layout.findViewById(R.id.stair_spinner);
        stairSpinner.setItems(day_options);
        stairSpinner.setSelection("none");
        stairSpinner.setListener(new MultiSelectionSpinner.OnMultipleItemsSelectedListener() {
            @Override
            public void selectedIndices(List<Integer> indices) {
                for (int i : indices) {
                    ArrayList<Exercise> exercises = (ArrayList<Exercise>) Exercise.findWithQuery(Exercise.class, "select * from Exercise where name = ? ", cardioPrograms[3]);
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
                for(MyWorkoutProgram p: programs){
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

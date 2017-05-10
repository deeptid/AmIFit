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
import com.deepti.amifit.R;
import com.deepti.amifit.Widgets.MultiSelectionSpinner;
import com.deepti.amifit.model.Exercise;
import com.deepti.amifit.model.MyWorkoutProgram;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by deepti on 26/02/17.
 */

public class HomeWorkout extends Fragment {
    public static String TAG = HomeWorkout.class.getSimpleName();
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
        ScrollView layout = (ScrollView) inflater.inflate(R.layout.home_program, container, false);
        Typeface customFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/helvetica-neue-ultra-light.ttf");
        TextView home_title = (TextView) layout.findViewById(R.id.home_title);

        TextView diveBombers_title = (TextView) layout.findViewById(R.id.divebombers);
        TextView lunges_title = (TextView) layout.findViewById(R.id.lunges);
        TextView burpees_title = (TextView) layout.findViewById(R.id.burpees);
        TextView mountain_climbers_title = (TextView) layout.findViewById(R.id.mountain_climbers);
        TextView jumping_jacks_home_title = (TextView) layout.findViewById(R.id.jumping_jacks_home);
        TextView squats_title = (TextView) layout.findViewById(R.id.squats);
        TextView chair_dips_title = (TextView) layout.findViewById(R.id.chair_dips);


        home_title.setTypeface(customFont, Typeface.BOLD);

        diveBombers_title.setTypeface(customFont, Typeface.BOLD);
        lunges_title.setTypeface(customFont, Typeface.BOLD);
        burpees_title.setTypeface(customFont, Typeface.BOLD);
        mountain_climbers_title.setTypeface(customFont, Typeface.BOLD);
        jumping_jacks_home_title.setTypeface(customFont, Typeface.BOLD);
        squats_title.setTypeface(customFont, Typeface.BOLD);
        chair_dips_title.setTypeface(customFont, Typeface.BOLD);

        ImageView imageView1 = (ImageView) layout.findViewById(R.id.divebombers_2_gif_placer);
        GlideDrawableImageViewTarget imageViewTarget1 = new GlideDrawableImageViewTarget(imageView1);
        Glide.with(this).load(R.drawable.divebombers_2).into(imageViewTarget1);

        ImageView imageView2 = (ImageView) layout.findViewById(R.id.lunges_gif_placer);
        GlideDrawableImageViewTarget imageViewTarget2 = new GlideDrawableImageViewTarget(imageView2);
        Glide.with(this).load(R.drawable.lunges_small).into(imageViewTarget2);

        ImageView imageView3 = (ImageView) layout.findViewById(R.id.burpees_gif_placer);
        GlideDrawableImageViewTarget imageViewTarget3 = new GlideDrawableImageViewTarget(imageView3);
        Glide.with(this).load(R.drawable.burpees_small).into(imageViewTarget3);

        ImageView imageView4 = (ImageView) layout.findViewById(R.id.mountain_climbers_gif_placer);
        GlideDrawableImageViewTarget imageViewTarget4 = new GlideDrawableImageViewTarget(imageView4);
        Glide.with(this).load(R.drawable.mountain_climbers).into(imageViewTarget4);

        ImageView imageView5 = (ImageView) layout.findViewById(R.id.jumping_jacks_home_gif_placer);
        GlideDrawableImageViewTarget imageViewTarget5 = new GlideDrawableImageViewTarget(imageView5);
        Glide.with(this).load(R.drawable.jumpingjacks_1).into(imageViewTarget5);

        ImageView imageView6 = (ImageView) layout.findViewById(R.id.squats_gif_placer);
        GlideDrawableImageViewTarget imageViewTarget6 = new GlideDrawableImageViewTarget(imageView6);
        Glide.with(this).load(R.drawable.regular_squat).into(imageViewTarget6);

        ImageView imageView7 = (ImageView) layout.findViewById(R.id.chair_dips_gif_placer);
        GlideDrawableImageViewTarget imageViewTarget7 = new GlideDrawableImageViewTarget(imageView7);
        Glide.with(this).load(R.drawable.chair_dip).into(imageViewTarget7);

        TextView add_title1 = (TextView) layout.findViewById(R.id.add_title1);
        add_title1.setTypeface(customFont, Typeface.BOLD);

        final String[] homePrograms = getResources().getStringArray(R.array.home_program);

        final String[] day_options = getResources().getStringArray(R.array.add_to_my_program);


        MultiSelectionSpinner diveBomberSpinner = (MultiSelectionSpinner) layout.findViewById(R.id.divebombers_spinner);
        diveBomberSpinner.setItems(day_options);
        diveBomberSpinner.setSelection("none");
        diveBomberSpinner.setListener(new MultiSelectionSpinner.OnMultipleItemsSelectedListener() {
            @Override
            public void selectedIndices(List<Integer> indices) {
                for (int i : indices) {
                    ArrayList<Exercise> exercises = (ArrayList<Exercise>) Exercise.findWithQuery(Exercise.class, "select * from Exercise where name = ? ", homePrograms[0]);
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


        MultiSelectionSpinner lungesSpinner = (MultiSelectionSpinner) layout.findViewById(R.id.lunges_spinner);
        lungesSpinner.setItems(day_options);
        lungesSpinner.setSelection("none");
        lungesSpinner.setListener(new MultiSelectionSpinner.OnMultipleItemsSelectedListener() {
            @Override
            public void selectedIndices(List<Integer> indices) {
                for (int i : indices) {
                    ArrayList<Exercise> exercises = (ArrayList<Exercise>) Exercise.findWithQuery(Exercise.class, "select * from Exercise where name = ? ", homePrograms[1]);
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

        MultiSelectionSpinner burpeesSpinner = (MultiSelectionSpinner) layout.findViewById(R.id.burpees_spinner);
        burpeesSpinner.setItems(day_options);
        burpeesSpinner.setSelection("none");
        burpeesSpinner.setListener(new MultiSelectionSpinner.OnMultipleItemsSelectedListener() {
            @Override
            public void selectedIndices(List<Integer> indices) {
                for (int i : indices) {
                    ArrayList<Exercise> exercises = (ArrayList<Exercise>) Exercise.findWithQuery(Exercise.class, "select * from Exercise where name = ? ", homePrograms[2]);
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


        MultiSelectionSpinner mountainClimbersSpinner = (MultiSelectionSpinner) layout.findViewById(R.id.mountain_climbers_spinner);
        mountainClimbersSpinner.setItems(day_options);
        mountainClimbersSpinner.setSelection("none");
        mountainClimbersSpinner.setListener(new MultiSelectionSpinner.OnMultipleItemsSelectedListener() {
            @Override
            public void selectedIndices(List<Integer> indices) {
                for (int i : indices) {
                    ArrayList<Exercise> exercises = (ArrayList<Exercise>) Exercise.findWithQuery(Exercise.class, "select * from Exercise where name = ? ", homePrograms[3]);
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


        TextView add_title5 = (TextView) layout.findViewById(R.id.add_title5);
        add_title5.setTypeface(customFont, Typeface.BOLD);


        MultiSelectionSpinner jumpingJacksSpinner = (MultiSelectionSpinner) layout.findViewById(R.id.jumping_jacks_home_spinner);
        jumpingJacksSpinner.setItems(day_options);
        jumpingJacksSpinner.setSelection("none");
        jumpingJacksSpinner.setListener(new MultiSelectionSpinner.OnMultipleItemsSelectedListener() {
            @Override
            public void selectedIndices(List<Integer> indices) {
                for (int i : indices) {
                    ArrayList<Exercise> exercises = (ArrayList<Exercise>) Exercise.findWithQuery(Exercise.class, "select * from Exercise where name = ? ", homePrograms[4]);
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

        TextView add_title6 = (TextView) layout.findViewById(R.id.add_title6);
        add_title6.setTypeface(customFont, Typeface.BOLD);


        MultiSelectionSpinner squatsSpinner = (MultiSelectionSpinner) layout.findViewById(R.id.squats_spinner);
        squatsSpinner.setItems(day_options);
        squatsSpinner.setSelection("none");
        squatsSpinner.setListener(new MultiSelectionSpinner.OnMultipleItemsSelectedListener() {
            @Override
            public void selectedIndices(List<Integer> indices) {
                for (int i : indices) {
                    ArrayList<Exercise> exercises = (ArrayList<Exercise>) Exercise.findWithQuery(Exercise.class, "select * from Exercise where name = ? ", homePrograms[5]);
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


        TextView add_title7 = (TextView) layout.findViewById(R.id.add_title7);
        add_title7.setTypeface(customFont, Typeface.BOLD);


        MultiSelectionSpinner chairDipsSpinner = (MultiSelectionSpinner) layout.findViewById(R.id.chair_dips_spinner);
        chairDipsSpinner.setItems(day_options);
        chairDipsSpinner.setSelection("none");
        chairDipsSpinner.setListener(new MultiSelectionSpinner.OnMultipleItemsSelectedListener() {
            @Override
            public void selectedIndices(List<Integer> indices) {
                for (int i : indices) {
                    ArrayList<Exercise> exercises = (ArrayList<Exercise>) Exercise.findWithQuery(Exercise.class, "select * from Exercise where name = ? ", homePrograms[6]);
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

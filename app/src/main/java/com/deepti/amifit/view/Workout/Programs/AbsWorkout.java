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

public class AbsWorkout extends Fragment {
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
        ScrollView layout = (ScrollView) inflater.inflate(R.layout.abs_program, container, false);
        Typeface customFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/helvetica-neue-ultra-light.ttf");
        TextView home_title = (TextView) layout.findViewById(R.id.abs_title);

        TextView plank_title = (TextView) layout.findViewById(R.id.plank);
        TextView spiderman_pushup_title = (TextView) layout.findViewById(R.id.spiderman_pushup);
        TextView leg_raise_title = (TextView) layout.findViewById(R.id.leg_raise);
        TextView plank_jack_title = (TextView) layout.findViewById(R.id.plank_jack);
        TextView crunches_title = (TextView) layout.findViewById(R.id.crunches);
        TextView pilates100_title = (TextView) layout.findViewById(R.id.pilates100);
        TextView mc_twist_title = (TextView) layout.findViewById(R.id.mc_twist);
        TextView pilates_crossover_title = (TextView) layout.findViewById(R.id.pilates_crossover);


        home_title.setTypeface(customFont, Typeface.BOLD);

        plank_title.setTypeface(customFont, Typeface.BOLD);
        spiderman_pushup_title.setTypeface(customFont, Typeface.BOLD);
        leg_raise_title.setTypeface(customFont, Typeface.BOLD);
        plank_jack_title.setTypeface(customFont, Typeface.BOLD);
        crunches_title.setTypeface(customFont, Typeface.BOLD);
        pilates100_title.setTypeface(customFont, Typeface.BOLD);
        mc_twist_title.setTypeface(customFont, Typeface.BOLD);
        pilates_crossover_title.setTypeface(customFont, Typeface.BOLD);

        ImageView imageView0 = (ImageView) layout.findViewById(R.id.plank_gif_placer);
        GlideDrawableImageViewTarget imageViewTarget0 = new GlideDrawableImageViewTarget(imageView0);
        Glide.with(this).load(R.drawable.plank).into(imageViewTarget0);

        ImageView imageView1 = (ImageView) layout.findViewById(R.id.spiderman_pushup_gif_placer);
        GlideDrawableImageViewTarget imageViewTarget1 = new GlideDrawableImageViewTarget(imageView1);
        Glide.with(this).load(R.drawable.spiderman_pushup).into(imageViewTarget1);

        ImageView imageView2 = (ImageView) layout.findViewById(R.id.leg_raise_gif_placer);
        GlideDrawableImageViewTarget imageViewTarget2 = new GlideDrawableImageViewTarget(imageView2);
        Glide.with(this).load(R.drawable.leg_raises).into(imageViewTarget2);

        ImageView imageView3 = (ImageView) layout.findViewById(R.id.plank_jack_gif_placer);
        GlideDrawableImageViewTarget imageViewTarget3 = new GlideDrawableImageViewTarget(imageView3);
        Glide.with(this).load(R.drawable.plank_jack).into(imageViewTarget3);

        ImageView imageView4 = (ImageView) layout.findViewById(R.id.crunches_gif_placer);
        GlideDrawableImageViewTarget imageViewTarget4 = new GlideDrawableImageViewTarget(imageView4);
        Glide.with(this).load(R.drawable.crunches_1).into(imageViewTarget4);

        ImageView imageView5 = (ImageView) layout.findViewById(R.id.pilates100_gif_placer);
        GlideDrawableImageViewTarget imageViewTarget5 = new GlideDrawableImageViewTarget(imageView5);
        Glide.with(this).load(R.drawable.pilates100).into(imageViewTarget5);

        ImageView imageView6 = (ImageView) layout.findViewById(R.id.mc_twist_gif_placer);
        GlideDrawableImageViewTarget imageViewTarget6 = new GlideDrawableImageViewTarget(imageView6);
        Glide.with(this).load(R.drawable.mountain_climber_twist).into(imageViewTarget6);

        ImageView imageView7 = (ImageView) layout.findViewById(R.id.pilates_crossover_gif_placer);
        GlideDrawableImageViewTarget imageViewTarget7 = new GlideDrawableImageViewTarget(imageView7);
        Glide.with(this).load(R.drawable.crunch_crossover).into(imageViewTarget7);

        TextView add_title1 = (TextView) layout.findViewById(R.id.add_title1);
        add_title1.setTypeface(customFont, Typeface.BOLD);

        final String[] homePrograms = getResources().getStringArray(R.array.abs_program);

        final String[] day_options = getResources().getStringArray(R.array.add_to_my_program);


        MultiSelectionSpinner plankSpinner = (MultiSelectionSpinner) layout.findViewById(R.id.plank_spinner);
        plankSpinner.setItems(day_options);
        plankSpinner.setSelection("none");
        plankSpinner.setListener(new MultiSelectionSpinner.OnMultipleItemsSelectedListener() {
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


        MultiSelectionSpinner spiderManSpinner = (MultiSelectionSpinner) layout.findViewById(R.id.spiderman_pushup_spinner);
        spiderManSpinner.setItems(day_options);
        spiderManSpinner.setSelection("none");
        spiderManSpinner.setListener(new MultiSelectionSpinner.OnMultipleItemsSelectedListener() {
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

        MultiSelectionSpinner legRaiseSpinner = (MultiSelectionSpinner) layout.findViewById(R.id.leg_raise_spinner);
        legRaiseSpinner.setItems(day_options);
        legRaiseSpinner.setSelection("none");
        legRaiseSpinner.setListener(new MultiSelectionSpinner.OnMultipleItemsSelectedListener() {
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


        MultiSelectionSpinner plankJackSpinner = (MultiSelectionSpinner) layout.findViewById(R.id.plank_jack_spinner);
        plankJackSpinner.setItems(day_options);
        plankJackSpinner.setSelection("none");
        plankJackSpinner.setListener(new MultiSelectionSpinner.OnMultipleItemsSelectedListener() {
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


        MultiSelectionSpinner crunchesSpinner = (MultiSelectionSpinner) layout.findViewById(R.id.crunches_spinner);
        crunchesSpinner.setItems(day_options);
        crunchesSpinner.setSelection("none");
        crunchesSpinner.setListener(new MultiSelectionSpinner.OnMultipleItemsSelectedListener() {
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


        MultiSelectionSpinner pilates100Spinner = (MultiSelectionSpinner) layout.findViewById(R.id.pilates100_spinner);
        pilates100Spinner.setItems(day_options);
        pilates100Spinner.setSelection("none");
        pilates100Spinner.setListener(new MultiSelectionSpinner.OnMultipleItemsSelectedListener() {
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


        MultiSelectionSpinner mcSpinner = (MultiSelectionSpinner) layout.findViewById(R.id.mc_twist_spinner);
        mcSpinner.setItems(day_options);
        mcSpinner.setSelection("none");
        mcSpinner.setListener(new MultiSelectionSpinner.OnMultipleItemsSelectedListener() {
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

        TextView add_title8 = (TextView) layout.findViewById(R.id.add_title8);
        add_title8.setTypeface(customFont, Typeface.BOLD);


        MultiSelectionSpinner pilatesCrossOverSpinner = (MultiSelectionSpinner) layout.findViewById(R.id.pilates_crossover_spinner);
        pilatesCrossOverSpinner.setItems(day_options);
        pilatesCrossOverSpinner.setSelection("none");
        pilatesCrossOverSpinner.setListener(new MultiSelectionSpinner.OnMultipleItemsSelectedListener() {
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

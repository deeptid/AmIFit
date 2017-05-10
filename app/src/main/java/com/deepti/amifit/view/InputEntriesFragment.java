package com.deepti.amifit.view;

import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
//import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.deepti.amifit.*;
import com.deepti.amifit.model.Steps;
import com.deepti.amifit.model.Weight;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by deepti on 20/12/16.
 */
public class InputEntriesFragment extends Fragment {
    ScrollView layout;
    public static String TAG = InputEntriesFragment.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
//        email = bundle.getString(AppConfig.EMAIL);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        layout = (ScrollView) inflater.inflate(R.layout.enter_detail, container, false);
        Typeface customFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/android_7.ttf");
        TextView title = (TextView) layout.findViewById(R.id.detail_title);
        title.setTypeface(customFont);

        final EditText stepsInput = (EditText) layout.findViewById(R.id.steps);
        final EditText inputWeight = (EditText) layout.findViewById(R.id.body_weight);

        Button btn = (Button) layout.findViewById(R.id.submit);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                Log.d(TAG, "Current time => " + c.getTime());
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String onlyDate = dateFormat.format(c.getTime());
                Editable currentWeight = inputWeight.getText();
                Editable noOfSteps = stepsInput.getText();

                ArrayList<Steps> l = (ArrayList<Steps>) Steps.findWithQuery(Steps.class, "select * from Steps where time = ? ", onlyDate);
                if (l.size() > 0)
                    if (!noOfSteps.toString().equals(""))
                        for (Steps step : l) {
                            Steps.delete(step);
                        }

                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                String formattedDate = df.format(c.getTime());

                if (stepsInput != null && !stepsInput.getText().equals("")) {
                    if (!noOfSteps.toString().equals("")) {
                        Steps steps = new Steps(Integer.parseInt(noOfSteps.toString()), formattedDate);
                        steps.save();
                    }
                }

                ArrayList<Weight> w = (ArrayList<Weight>) Weight.findWithQuery(Weight.class, "select * from Weight where time = ?", onlyDate);
                if (w.size() > 0)
                    for (Weight weight : w) {
                        Weight.delete(weight);
                    }

                if (inputWeight != null && !inputWeight.getText().equals("")) {
                    if (!currentWeight.toString().equals("")) {
                        Weight weight = new Weight(Float.parseFloat(currentWeight.toString()), formattedDate);
                        weight.save();
                    }
                }

                List<Steps> allInputSteps = Steps.listAll(Steps.class);
                List<Weight> allInputWeights = Weight.listAll(Weight.class);

                Log.d(TAG, "allInputSteps : " + allInputSteps.toString());
                Log.d(TAG, "allInputWeights : " + allInputWeights.toString());
                getActivity().onBackPressed();
            }
        });
        return layout;
    }
}

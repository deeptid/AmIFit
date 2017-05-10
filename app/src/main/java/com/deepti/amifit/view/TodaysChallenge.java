package com.deepti.amifit.view;

import android.app.Fragment;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.deepti.amifit.R;
import com.deepti.amifit.util.CalendarUtility;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by deepti on 23/02/17.
 */

public class TodaysChallenge extends Fragment {
    RelativeLayout layout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
    }

    @Override
    public View onCreateView(final LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        layout = (RelativeLayout) inflater.inflate(R.layout.todays_challenge, container, false);
        TextView challengeText = (TextView) layout.findViewById(R.id.challenge_text);
        Date today = CalendarUtility.getCurrentDate();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        String todaysDate = sdf1.format(today);

        String[] todayDate = todaysDate.split("-");

        Resources res = getResources();
        String[] challengeTextArray = res.getStringArray(R.array.challenge_text);

        challengeText.setText(challengeTextArray[Integer.parseInt(todayDate[2])]);
        challengeText.setGravity(View.TEXT_ALIGNMENT_CENTER);



        return layout;
    }
}

package com.deepti.amifit.view;

import android.app.Fragment;
import android.content.res.Resources;
import android.graphics.Typeface;
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
 * Created by deepti on 28/02/17.
 */

public class AboutFragment extends Fragment {
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
        layout = (RelativeLayout) inflater.inflate(R.layout.about, container, false);
        TextView aboutText = (TextView) layout.findViewById(R.id.about_text);
        TextView versionText = (TextView) layout.findViewById(R.id.version_text);
        TextView descText = (TextView) layout.findViewById(R.id.description_text);
        TextView disclaimerText = (TextView) layout.findViewById(R.id.disclaimer_text);
        TextView disclaimerTitle = (TextView) layout.findViewById(R.id.disclaimer_title);
//        Typeface customFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/helvetica-neue-ultra-light.ttf");
//
//        aboutText.setTypeface(customFont, Typeface.BOLD);
//        versionText.setTypeface(customFont, Typeface.BOLD);
//        descText.setTypeface(customFont, Typeface.BOLD);
//        disclaimerText.setTypeface(customFont, Typeface.BOLD);
//        disclaimerTitle.setTypeface(customFont, Typeface.BOLD);

        disclaimerTitle.setTypeface(Typeface.DEFAULT_BOLD);
        aboutText.setTypeface(Typeface.DEFAULT_BOLD);
        versionText.setTypeface(Typeface.DEFAULT_BOLD);

        return layout;
    }
}


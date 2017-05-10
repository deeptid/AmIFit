package com.deepti.amifit.view;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.deepti.amifit.R;
import com.deepti.amifit.util.CalendarUtility;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by deepti on 27/02/17.
 */

public class MoreOptionsFragment extends Fragment {
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
        layout = (RelativeLayout) inflater.inflate(R.layout.more_options, container, false);
        TextView profileEditText = (TextView) layout.findViewById(R.id.profile_text);
        TextView aboutText = (TextView) layout.findViewById(R.id.about_text);
        Typeface customFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/helvetica-neue-ultra-light.ttf");

        profileEditText.setTypeface(customFont, Typeface.BOLD);
        aboutText.setTypeface(customFont, Typeface.BOLD);

        LinearLayout profileLayout = (LinearLayout)layout.findViewById(R.id.profile_header);
        LinearLayout aboutLayout = (LinearLayout)layout.findViewById(R.id.about_header);

        profileLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 EditProfileFragment editProfileFragment = new EditProfileFragment();
                String tag = EditProfileFragment.class.getSimpleName();
                FragmentManager fm5 = getFragmentManager();
                FragmentTransaction ft5 = fm5.beginTransaction();
                ft5.replace(R.id.full_screen_more, editProfileFragment, tag);
                ft5.addToBackStack(tag);
                ft5.commit();
            }
        });

        aboutLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AboutFragment aboutFragment = new AboutFragment();
                String tag = EditProfileFragment.class.getSimpleName();
                FragmentManager fm5 = getFragmentManager();
                FragmentTransaction ft5 = fm5.beginTransaction();
                ft5.replace(R.id.full_screen_more, aboutFragment, tag);
                ft5.addToBackStack(tag);
                ft5.commit();
            }
        });





        return layout;
    }
}
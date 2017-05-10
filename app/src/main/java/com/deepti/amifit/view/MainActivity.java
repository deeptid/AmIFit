package com.deepti.amifit.view;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentTransaction;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.annotation.BoolRes;
import android.support.annotation.NonNull;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.deepti.amifit.R;
import com.deepti.amifit.Service.PedometerSettings;
import com.deepti.amifit.Service.StepService;
import com.deepti.amifit.Settings.ReminderSettingsFragment;
import com.deepti.amifit.model.MyWorkoutProgram;
import com.deepti.amifit.model.Steps;
import com.deepti.amifit.util.CalendarUtility;
import com.deepti.amifit.util.TextToSpeechUtil;
import com.deepti.amifit.view.Workout.MyWorkoutPlan;
import com.deepti.amifit.view.Workout.TodaysWorkoutPlan;
import com.deepti.amifit.view.Workout.WorkoutListFragment;
import com.orm.SugarContext;

import android.support.design.widget.NavigationView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static String TAG = MainActivity.class.getSimpleName();
    private GestureDetector gestureDetector;
    private TextView mStepValueView;
    private Button enterStepsbtn;
    private int mStepValue;
    private SharedPreferences mSettings;
    private PedometerSettings mPedometerSettings;
    private static final int STEPS_MSG = 1;
    boolean usePedometer = false;
    int averageSteps = 0;

    private NavigationView navigationView;
    private DrawerLayout drawerLayout;

    private boolean mIsRunning;
    private TextToSpeechUtil mUtils;

    int finalAvg = 0;
    ActionBarDrawerToggle drawerToggle;
    RelativeLayout mainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setItemIconTintList(null);


//        gestureDetector = new GestureDetector(
//                new SwipeGestureDetector(this, drawerLayout));
//        drawerLayout.setOnTouchListener(new View.OnTouchListener() {
//            public boolean onTouch(View v, MotionEvent event) {
//                if (gestureDetector.onTouchEvent(event)) {
//                    return true;
//                }
//                return false;
//            }
//        });
        Typeface customFont = Typeface.createFromAsset(getAssets(), "fonts/android_7.ttf");
        mUtils = TextToSpeechUtil.getInstance();

        ImageView imageView = (ImageView) findViewById(R.id.gif_placer);
        GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(imageView);
        Glide.with(this).load(R.drawable.rungif).into(imageViewTarget);


        TextView title = (TextView) findViewById(R.id.title);
        title.setTypeface(customFont);
        mStepValueView = (TextView) findViewById(R.id.msg);
        mStepValueView.setTypeface(customFont);
        mSettings = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);


        SharedPreferences.Editor editor = mSettings.edit();
        editor.putBoolean("service_running", false);
        editor.putBoolean("tell_steps", true);
        editor.putBoolean("speak", true);
        editor.putString("sensitivity", "30");
        editor.putString("speaking_interval", "10");


        editor.putLong("last_seen", 0);
        editor.commit();

        //get avg
        int avg = 0;
        if (averageSteps == 0)
            avg = getAverageSteps();
        else
            avg = averageSteps;

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        usePedometer = sharedPrefs.getBoolean("usePedometer", false);
        Log.d(TAG, "usePedometer onCreate: " + usePedometer);
        enterStepsbtn = (Button) findViewById(R.id.btnSteps);
        if (usePedometer) {
            enterStepsbtn.setText(R.string.btn_change_manual);
            mStepValueView.setText("0");

            finalAvg = avg;
            enterStepsbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = sharedPrefs.edit();
                    editor.remove("usePedometer");
                    editor.putBoolean("usePedometer", false);
                    editor.commit();
                    usePedometer = false;
                    mStepValueView.setText("" + finalAvg + "");
                    enterStepsbtn.setText(R.string.enter_steps);
                    finish();
                    startActivity(getIntent());
                }
            });
        } else {
            enterStepsbtn.setText(R.string.enter_steps);
            mStepValueView.setText("" + avg + "");
            enterStepsbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    InputEntriesFragment newFragment = new InputEntriesFragment();
                    String tag = InputEntriesFragment.class.getSimpleName();
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.add(R.id.full_screen, newFragment, tag);
                    ft.addToBackStack(tag);
                    ft.commit();
                }
            });
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.nav_icon);
        toolbar.setContentInsetStartWithNavigation(0);

        drawerToggle = setupDrawerToggle(toolbar);
        drawerLayout.setDrawerListener(drawerToggle);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                drawerLayout.openDrawer(GravityCompat.START);
                Log.d("Navigation", "button button button..................");
//                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
//                drawerLayout.openDrawer(GravityCompat.START);
                if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
                    drawerLayout.closeDrawers();
                    Log.d(TAG, "Closing the drawers in click listener");

                    // unreachable area, somehow code here is not executing, so moved the arrangement logic in to OnDrawerClosed method
                } else {
                    drawerLayout.openDrawer(Gravity.LEFT);
                    drawerLayout.bringToFront();
                    Log.d(TAG, "opening the drawer");
                }
            }
        });

        setupDrawerContent(navigationView);


        // Find our drawer view
    }


    private ActionBarDrawerToggle setupDrawerToggle(Toolbar toolbar) {
        return new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                Log.d("SLIDING", "" + drawerView.getX());

            }

            @Override
            public void onDrawerClosed(View v) {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                super.onDrawerClosed(v);
                Log.d(TAG, "Closing the drawer");
            }

            @Override
            public void onDrawerOpened(View v) {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                super.onDrawerOpened(v);
            }
        };
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
        View view = navigationView.getHeaderView(0);
        TextView profileName = (TextView) view.findViewById(R.id.profile_status);
        profileName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Settings");
                drawerLayout.closeDrawers();
                EditProfileFragment editProfileFragment = new EditProfileFragment();
                String tag2 = WorkoutListFragment.class.getSimpleName();
                FragmentManager fm2 = getFragmentManager();
                FragmentTransaction ft2 = fm2.beginTransaction();
                ft2.replace(R.id.full_screen, editProfileFragment, tag2);
                ft2.addToBackStack(tag2);
                ft2.commit();
            }
        });
        ImageView profile_picture = (ImageView) view.findViewById(R.id.img_profile);


        SharedPreferences shre = PreferenceManager.getDefaultSharedPreferences(this);
        String filepath = shre.getString("profile_imagepath", "");
        String status = shre.getString("status", "not set");
        if(status != null && !status.equals("not set")){
            profileName.setText(status);
        } else {
            profileName.setText("You are awesome!");
        }
        File imgFile = new  File(filepath);
        if(imgFile.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            profile_picture.setImageBitmap(myBitmap);
        }
    }

    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked
        switch (menuItem.getItemId()) {
            case R.id.home:
                drawerLayout.closeDrawers();
//                android.app.FragmentManager fm1 = getFragmentManager();
                android.support.v4.app.FragmentManager fmHome = getSupportFragmentManager();
//                fmHome.popBackStackImmediate("ChannelLauncher", android.support.v4.app.FragmentManager.POP_BACK_STACK_INCLUSIVE);
//                fmHome.popBackStackImmediate("TabbedCardFragment", android.support.v4.app.FragmentManager.POP_BACK_STACK_INCLUSIVE);
                fmHome.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                FrameLayout fl = (FrameLayout) findViewById(R.id.full_screen);
                fl.removeAllViewsInLayout();
                drawerLayout.closeDrawers();
                break;

            case R.id.days_summary:
                drawerLayout.closeDrawers();
                DaySummary newFragment = new DaySummary();
                String tag = DaySummary.class.getSimpleName();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.full_screen, newFragment, tag);
                ft.addToBackStack(tag);
                ft.commit();
                break;

            case R.id.monthly_summary:
                drawerLayout.closeDrawers();
                MonthlySummary monthlyFragment = new MonthlySummary();
                String tag1 = MonthlySummary.class.getSimpleName();
                FragmentManager fm1 = getFragmentManager();
                FragmentTransaction ft1 = fm1.beginTransaction();
                ft1.replace(R.id.full_screen, monthlyFragment, tag1);
                ft1.addToBackStack(tag1);
                ft1.commit();
                break;
//            case R.id.overall_summary:
//                // User chose the "Favorite" action, mark the current item
//                // as a favorite...
//                return true;
            case R.id.workouts:
                drawerLayout.closeDrawers();
                WorkoutListFragment workoutFragment = new WorkoutListFragment();
                String tag2 = WorkoutListFragment.class.getSimpleName();
                FragmentManager fm2 = getFragmentManager();
                FragmentTransaction ft2 = fm2.beginTransaction();
                ft2.replace(R.id.full_screen, workoutFragment, tag2);
                ft2.addToBackStack(tag2);
                ft2.commit();
                break;

            case R.id.workout_plan:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                drawerLayout.closeDrawers();
                MyWorkoutPlan workoutplan = new MyWorkoutPlan();
                String tag3 = MyWorkoutPlan.class.getSimpleName();
                FragmentManager fm3 = getFragmentManager();
                FragmentTransaction ft3 = fm3.beginTransaction();
                ft3.replace(R.id.full_screen, workoutplan, tag3);
                ft3.addToBackStack(tag3);
                ft3.commit();
                break;
            case R.id.todays_workout:
                drawerLayout.closeDrawers();
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                TodaysWorkoutPlan todaysWorkoutPlan = new TodaysWorkoutPlan();
                String tag4 = TodaysWorkoutPlan.class.getSimpleName();
                FragmentManager fm4 = getFragmentManager();
                FragmentTransaction ft4 = fm4.beginTransaction();
                ft4.replace(R.id.full_screen, todaysWorkoutPlan, tag4);
                ft4.addToBackStack(tag4);
                ft4.commit();
                break;
            case R.id.todays_challenge:
                TodaysChallenge todaysChallenge = new TodaysChallenge();
                String tag6 = TodaysChallenge.class.getSimpleName();
                FragmentManager fm6 = getFragmentManager();
                FragmentTransaction ft6 = fm6.beginTransaction();
                ft6.replace(R.id.full_screen, todaysChallenge, tag6);
                ft6.addToBackStack(tag6);
                ft6.commit();
                drawerLayout.closeDrawers();
                break;
            case R.id.pedometer_setting:
                drawerLayout.closeDrawers();
                showPedometerSettingsDialog();
                break;
            case R.id.reminder:
                setTitle("Reminder Settings");
                ReminderSettingsFragment reminderSettingsFragment = new ReminderSettingsFragment();
                String tag5 = ReminderSettingsFragment.class.getSimpleName();
                FragmentManager fm5 = getFragmentManager();
                FragmentTransaction ft5 = fm5.beginTransaction();
                ft5.replace(R.id.full_screen, reminderSettingsFragment, tag5);
                ft5.addToBackStack(tag5);
                ft5.commit();
                drawerLayout.closeDrawers();
                break;
            case R.id.more:
                setTitle("More");
                MoreOptionsFragment moreOptionsFragment = new MoreOptionsFragment();
                String tag7 = ReminderSettingsFragment.class.getSimpleName();
                FragmentManager fm7 = getFragmentManager();
                FragmentTransaction ft7 = fm7.beginTransaction();
                ft7.replace(R.id.full_screen, moreOptionsFragment, tag7);
                ft7.addToBackStack(tag7);
                ft7.commit();
                drawerLayout.closeDrawers();
                break;
            default:
                break;
        }


        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);
        // Set action bar title
        setTitle(menuItem.getTitle());
        // Close the navigation drawer
        drawerLayout.closeDrawers();
        menuItem.setChecked(false);
    }


    private StepService.ICallback mCallback = new StepService.ICallback() {
        public void stepsChanged(int value) {
            mHandler.sendMessage(mHandler.obtainMessage(STEPS_MSG, value, 0));
        }
    };

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case STEPS_MSG:
                    Log.d(TAG, "usePedometer inside Handle: " + usePedometer);
                    if (usePedometer) {
                        mStepValue = (int) msg.arg1;
                        mStepValueView.setText("" + mStepValue);
                    }
                    break;
            }
        }
    };

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (gestureDetector != null && gestureDetector.onTouchEvent(event)) {
            return true;
        }
        return super.onTouchEvent(event);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.stats_menu, menu);
//        return true;
//    }

    public void onLeftSwipe() {
        DaySummary newFragment = new DaySummary();
        String tag = DaySummary.class.getSimpleName();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.full_screen, newFragment, tag);
        ft.addToBackStack(tag);
        ft.commit();
    }

    public void onRightSwipe() {
        MonthlySummary monthlyFragment = new MonthlySummary();
        String tag1 = MonthlySummary.class.getSimpleName();
        FragmentManager fm1 = getFragmentManager();
        FragmentTransaction ft1 = fm1.beginTransaction();
        ft1.replace(R.id.full_screen, monthlyFragment, tag1);
        ft1.addToBackStack(tag1);
        ft1.commit();
    }


//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.days_summary:
//                DaySummary newFragment = new DaySummary();
//                String tag = DaySummary.class.getSimpleName();
//                FragmentManager fm = getFragmentManager();
//                FragmentTransaction ft = fm.beginTransaction();
//                ft.replace(R.id.full_screen, newFragment, tag);
//                ft.addToBackStack(tag);
//                ft.commit();
//                return true;
//
//            case R.id.monthly_summary:
//                MonthlySummary monthlyFragment = new MonthlySummary();
//                String tag1 = MonthlySummary.class.getSimpleName();
//                FragmentManager fm1 = getFragmentManager();
//                FragmentTransaction ft1 = fm1.beginTransaction();
//                ft1.replace(R.id.full_screen, monthlyFragment, tag1);
//                ft1.addToBackStack(tag1);
//                ft1.commit();
//                return true;
////            case R.id.overall_summary:
////                // User chose the "Favorite" action, mark the current item
////                // as a favorite...
////                return true;
//            case R.id.workouts:
//                WorkoutListFragment workoutFragment = new WorkoutListFragment();
//                String tag2 = WorkoutListFragment.class.getSimpleName();
//                FragmentManager fm2 = getFragmentManager();
//                FragmentTransaction ft2 = fm2.beginTransaction();
//                ft2.replace(R.id.full_screen, workoutFragment, tag2);
//                ft2.addToBackStack(tag2);
//                ft2.commit();
//                return true;
//
//            case R.id.workout_plan:
//                // User chose the "Favorite" action, mark the current item
//                // as a favorite...
//                MyWorkoutPlan workoutplan = new MyWorkoutPlan();
//                String tag3 = MyWorkoutPlan.class.getSimpleName();
//                FragmentManager fm3 = getFragmentManager();
//                FragmentTransaction ft3 = fm3.beginTransaction();
//                ft3.replace(R.id.full_screen, workoutplan, tag3);
//                ft3.addToBackStack(tag3);
//                ft3.commit();
//                return true;
//            case R.id.todays_workout:
//                // User chose the "Favorite" action, mark the current item
//                // as a favorite...
//                TodaysWorkoutPlan todaysWorkoutPlan = new TodaysWorkoutPlan();
//                String tag4 = TodaysWorkoutPlan.class.getSimpleName();
//                FragmentManager fm4 = getFragmentManager();
//                FragmentTransaction ft4 = fm4.beginTransaction();
//                ft4.replace(R.id.full_screen, todaysWorkoutPlan, tag4);
//                ft4.addToBackStack(tag4);
//                ft4.commit();
//                return true;
//            case R.id.pedometer_setting:
//                // User chose the "Favorite" action, mark the current item
//                // as a favorite...
//                showPedometerSettingsDialog();
//                return true;
//            default:
//                // If we got here, the user's action was not recognized.
//                // Invoke the superclass to handle it.
//                return super.onOptionsItemSelected(item);
//
//        }
//    }

    private void showPedometerSettingsDialog() {
        Typeface customFont = Typeface.createFromAsset(getAssets(), "fonts/helvetica-neue-ultra-light.ttf");
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.pedometer_settings_dialog);
        TextView dialogTitle = (TextView) dialog.findViewById(R.id.dialog_title);
        TextView dialogMsg = (TextView) dialog.findViewById(R.id.dialog_msg);
        dialogTitle.setTypeface(customFont, Typeface.BOLD);
        dialogMsg.setTypeface(customFont, Typeface.BOLD);

        Button btn = (Button) dialog.findViewById(R.id.ok_button);
        btn.setTypeface(customFont, Typeface.BOLD);
        if (!usePedometer) {
            dialogTitle.setText(R.string.change_to_pedometer_title);
            dialogMsg.setText(R.string.change_to_pedometer);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = sharedPrefs.edit();
                    editor.remove("usePedometer");
                    editor.putBoolean("usePedometer", true);
                    editor.commit();
                    usePedometer = true;
                    mStepValueView.setText("0");
                    enterStepsbtn.setText(R.string.btn_change_manual);
                    mSettings = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                    mPedometerSettings = new PedometerSettings(mSettings);

                    mUtils.setSpeak(mSettings.getBoolean("speak", false));

                    // Read from reminder_preferences if the service was running on the last onPause
                    mIsRunning = mPedometerSettings.isServiceRunning();
                    Log.d(TAG, "mIsRunning:" + mIsRunning);

                    // Start the service if this is considered to be an application start (last onPause was long ago)
                    if (!mIsRunning && mPedometerSettings.isNewStart()) {
                        startStepService();
                        bindStepService();
                    } else if (mIsRunning) {
                        bindStepService();
                    }
                    mPedometerSettings.clearServiceRunning();
                    finish();
                    startActivity(getIntent());
                    dialog.dismiss();
                }
            });
        } else {
            dialogTitle.setText(R.string.change_to_manual_title);
            dialogMsg.setText(R.string.change_to_manual);

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = sharedPrefs.edit();
                    editor.remove("usePedometer");
                    editor.putBoolean("usePedometer", false);
                    editor.commit();
                    usePedometer = false;
                    mStepValueView.setText("" + finalAvg + "");
                    enterStepsbtn.setText(R.string.enter_steps);
                    finish();
                    startActivity(getIntent());

                    dialog.dismiss();
                }
            });
        }


        dialog.show();

    }

    @Override
    protected void onResume() {
        super.onResume();
        getAverageSteps();
        Log.d(TAG, "usePedometer inside Resume :" + usePedometer);
        if (usePedometer) {
            mSettings = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
            mPedometerSettings = new PedometerSettings(mSettings);

            mUtils.setSpeak(mSettings.getBoolean("speak", false));

            // Read from reminder_preferences if the service was running on the last onPause
            mIsRunning = mPedometerSettings.isServiceRunning();
            Log.d(TAG, "mIsRunning inside Resume :" + mIsRunning);

//            // Start the service if this is considered to be an application start (last onPause was long ago)
//            bindStepService();
            if (!mIsRunning && mPedometerSettings.isNewStart()) {
                startStepService();
                bindStepService();
            } else if (mIsRunning) {
                bindStepService();
            }

            mPedometerSettings.clearServiceRunning();
        }
    }

    @Override
    protected void onPause() {
        Log.i(TAG, "[ACTIVITY] onPause");
        if (usePedometer) {
            if (mIsRunning) {
                unbindStepService();
            }
//            if (mQuitting) {
//                mPedometerSettings.saveServiceRunningWithNullTimestamp(mIsRunning);
//            } else {
            mPedometerSettings.saveServiceRunningWithTimestamp(mIsRunning);
//            }
        }
        super.onPause();
    }

    public int getAverageSteps() {
        Date today = CalendarUtility.getCurrentDate();
        String firstDateOfMonth = null;
        String lastDateOfMonth = null;
        try {
            firstDateOfMonth = CalendarUtility.getFirstDayofMonth(today);
            lastDateOfMonth = CalendarUtility.getLastDayofMonth(today);
        } catch (Exception e) {
            e.printStackTrace();
        }
        int total = 0;
        List<Steps> allSteps = Steps.listAll(Steps.class);
        for (Steps step : allSteps) {
            Log.d(TAG, "allSteps step : " + step.getNumberOfSteps());
            Log.d(TAG, "allSteps time : " + step.getTime());
        }

        Log.d(TAG, "firstDateOfMonth : " + firstDateOfMonth);
        Log.d(TAG, "lastDateOfMonth : " + lastDateOfMonth);

        if (allSteps.size() > 0) {
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
            String todaysDate = sdf1.format(today);

            ArrayList<Steps> steps = (ArrayList<Steps>) Steps.findWithQuery(Steps.class, "select * from Steps where time = ?", todaysDate);
            if (steps.size() > 0)
                for (Steps step : steps) {
                    Log.d(TAG, "steps: " + step.getNumberOfSteps());
                    total += step.getNumberOfSteps();
                }
            else {
                steps = (ArrayList<Steps>) Steps.listAll(Steps.class);
                if (steps.size() == 1)
                    total += steps.get(0).getNumberOfSteps();
            }
            Log.d(TAG, "total: " + total);
            if (total > 0)
                averageSteps = total / steps.size();
        }
        return averageSteps;
    }

    public void setAverageSteps(int averageSteps) {
        this.averageSteps = averageSteps;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.d("MAIN", "onBackpress");
        int avg = getAverageSteps();

        TextView msg = (TextView) findViewById(R.id.msg);
        msg.setText("" + avg + "");
        setTitle("AmIFit");
    }

    public void onDownSwipe() {
        WorkoutListFragment workoutFragment = new WorkoutListFragment();
        String tag2 = WorkoutListFragment.class.getSimpleName();
        FragmentManager fm2 = getFragmentManager();
        FragmentTransaction ft2 = fm2.beginTransaction();
        ft2.replace(R.id.full_screen, workoutFragment, tag2);
        ft2.addToBackStack(tag2);
        ft2.commit();
    }

    private StepService mService;

    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            Log.d(TAG, "Connection done!");
            mService = ((StepService.StepBinder) service).getService();
            mService.registerCallback(mCallback);
            mService.reloadSettings();
        }

        public void onServiceDisconnected(ComponentName className) {
            mService = null;
        }
    };

    private void startStepService() {
        if (!mIsRunning) {
            Log.i(TAG, "[SERVICE] Start");
            mIsRunning = true;
            startService(new Intent(MainActivity.this,
                    StepService.class));
        }
    }

    private void bindStepService() {
        Log.i(TAG, "[SERVICE] Bind");
        bindService(new Intent(this,
                StepService.class), mConnection, Context.BIND_AUTO_CREATE + Context.BIND_DEBUG_UNBIND);
    }

    private void unbindStepService() {
        Log.i(TAG, "[SERVICE] Unbind");
        unbindService(mConnection);
    }

    private void stopStepService() {
        Log.i(TAG, "[SERVICE] Stop");
        if (mService != null) {
            Log.i(TAG, "[SERVICE] stopService");
            stopService(new Intent(MainActivity.this,
                    StepService.class));
        }
        mIsRunning = false;
    }

    private void resetValues(boolean updateDisplay) {
        if (mService != null && mIsRunning) {
            mService.resetValues();
        } else {
            mStepValueView.setText("0");

            SharedPreferences state = getSharedPreferences("state", 0);
            SharedPreferences.Editor stateEditor = state.edit();
            if (updateDisplay) {
                stateEditor.putInt("steps", 0);
                stateEditor.commit();
            }
        }
    }

}

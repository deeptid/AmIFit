package com.deepti.amifit.view.Workout;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.support.v7.view.menu.MenuBuilder;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.deepti.amifit.R;
import com.deepti.amifit.model.MyWorkoutProgram;
import com.deepti.amifit.view.MainActivity;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by deepti on 18/01/17.
 */
public class LazyAdapter extends BaseAdapter {
    public static String TAG = LazyAdapter.class.getSimpleName();


    private Activity activity;
    private ArrayList<HashMap<String, String>> data;
    private LayoutInflater inflater = null;
    static final String KEY_TITLE = "title";
    static final String KEY_DESC = "desc";
    static final String KEY_TIME = "time";
    static final String KEY_ID = "id";


    public LazyAdapter(Activity activity, ArrayList<HashMap<String, String>> d) {
        this.data = d;
        this.activity = activity;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        Typeface customFont = Typeface.createFromAsset(activity.getAssets(), "fonts/helvetica-neue-ultra-light.ttf");

        if (convertView == null)
            vi = inflater.inflate(R.layout.list_row, null);
        TextView title = (TextView) vi.findViewById(R.id.title); // title
        TextView desc = (TextView) vi.findViewById(R.id.description); // title
        TextView time = (TextView) vi.findViewById(R.id.time); // title

        title.setTypeface(customFont, Typeface.BOLD);
        desc.setTypeface(customFont, Typeface.BOLD);
        time.setTypeface(customFont, Typeface.BOLD);

        HashMap<String, String> map = new HashMap<String, String>();
        map = data.get(position);

        // Setting all values in listview
        title.setText(map.get(KEY_TITLE));
        desc.setText(map.get(KEY_DESC));
        time.setText(map.get(KEY_TIME) + " min");

        final View overflow = vi.findViewById(R.id.menu_overflow);

        final HashMap<String, String> finalMap = map;
        overflow.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(activity, v);
                popup.getMenuInflater().inflate(R.menu.workout_plan_menu, popup.getMenu());

                Object menuHelper;
                Class[] argTypes;
                try {
                    Field fMenuHelper = PopupMenu.class.getDeclaredField("mPopup");
                    fMenuHelper.setAccessible(true);
                    menuHelper = fMenuHelper.get(popup);
                    argTypes = new Class[]{boolean.class};
                    menuHelper.getClass().getDeclaredMethod("setForceShowIcon", argTypes).invoke(menuHelper, true);
                } catch (Exception e) {
                    // Possible exceptions are NoSuchMethodError and NoSuchFieldError
                    //
                    // In either case, an exception indicates something is wrong with the reflection code, or the
                    // structure of the PopupMenu class or its dependencies has changed.
                    //
                    // These exceptions should never happen since we're shipping the AppCompat library in our own apk,
                    // but in the case that they do, we simply can't force icons to display, so log the error and
                    // show the menu normally.

                    Log.w(TAG, "error forcing menu icons to show", e);
                    popup.show();
                    return;
                }
                popup.show();
                popup.setOnMenuItemClickListener(new android.widget.PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Toast.makeText(
                                activity,
                                "You Clicked : " + item.getTitle(), Toast.LENGTH_SHORT
                        ).show();
                        performAction(item.getTitle().toString(), finalMap, position);
                        return true;
                    }

                });

            }
        });
        return vi;
    }

    private void performAction(String s, HashMap<String, String> finalMap, int position) {
        String id = finalMap.get(KEY_ID);
        switch (s) {
            case "Delete":
                List<MyWorkoutProgram> workoutPlans = new ArrayList<>();
                long count = MyWorkoutProgram.count(MyWorkoutProgram.class);
                if (count > 0) {
                    workoutPlans = MyWorkoutProgram.find(MyWorkoutProgram.class, "id=?", id);
                    if (workoutPlans == null) {
                    } else {
                        MyWorkoutProgram workoutPlanTable = MyWorkoutProgram.findById(MyWorkoutProgram.class, Long.parseLong(id));
                        workoutPlanTable.delete();
                        data.remove(data.get(position));
                        notifyDataSetChanged();
                    }
                }

                break;
            case "Change time":
                showDialog(finalMap, position);
                break;

        }

    }

    public void swapItems(ArrayList<HashMap<String, String>> d) {
        Log.d(TAG,"In Swap Items");
        this.data = d;
        notifyDataSetChanged();
    }

    private void showDialog(HashMap<String, String> finalMap, final int position) {
        final String id = finalMap.get(KEY_ID);
        final String title = finalMap.get(KEY_TITLE);
        Typeface customFont = Typeface.createFromAsset(activity.getAssets(), "fonts/helvetica-neue-ultra-light.ttf");
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.edit_workout_time_dialog);
        TextView dialogTitle = (TextView) dialog.findViewById(R.id.dialog_title);
        final EditText time = (EditText) dialog.findViewById(R.id.edit_time);
        TextView timeTitle = (TextView) dialog.findViewById(R.id.time);
        final Editable inputTime = time.getText();

        time.setTypeface(customFont, Typeface.BOLD);
        dialogTitle.setTypeface(customFont, Typeface.BOLD);
        timeTitle.setTypeface(customFont, Typeface.BOLD);
        dialogTitle.setText("Change Time for " + title);
        Button btn = (Button) dialog.findViewById(R.id.ok_button);
        btn.setTypeface(customFont, Typeface.BOLD);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inputTime != null && !inputTime.toString().equals("")) {
                    MyWorkoutProgram workoutPlanTable = MyWorkoutProgram.findById(MyWorkoutProgram.class, Long.parseLong(id));
                    workoutPlanTable.time = inputTime.toString();
                    workoutPlanTable.save();
                    Log.d(TAG, "time changed to : " + inputTime);
                    data.get(position).put(KEY_TIME, inputTime.toString());
                    notifyDataSetChanged();
                    dialog.dismiss();
                }
            }
        });
        dialog.show();
    }


}

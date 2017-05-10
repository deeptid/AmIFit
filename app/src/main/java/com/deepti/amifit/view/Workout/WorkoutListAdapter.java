package com.deepti.amifit.view.Workout;

import android.app.Activity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.deepti.amifit.R;
import com.deepti.amifit.model.Workout;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by deepti on 10/01/17.
 */

public class WorkoutListAdapter extends BaseAdapter {
    private static final String TAG = WorkoutListAdapter.class.getSimpleName();
    private static LayoutInflater inflater;
    private Activity activity;
    private static int colorIndex = 0;
    private ArrayList<Workout> workouts = new ArrayList<Workout>();

    public WorkoutListAdapter(Activity activity) {
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return workouts.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d(TAG, "In getView: position" + position);
        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.dashboard_grid_cell, null);

        final View cView;
        cView = convertView;

        cView.post(new Runnable() {

            @Override
            public void run() {
                AbsListView.LayoutParams mParams;
                mParams = (AbsListView.LayoutParams) cView.getLayoutParams();
                mParams.height = cView.getWidth();
                cView.setLayoutParams(mParams);
                cView.postInvalidate();
            }
        });

        final View bg = convertView.findViewById(R.id.bg);
        final View viewColor = convertView.findViewById(R.id.view_for_color);

        int[] colors = new int[]{
                R.color.grad1, R.color.grad2, R.color.grad3, R.color.grad4, R.color.grad5,
                R.color.grad6, R.color.grad7, R.color.grad8, R.color.grad9, R.color.grad10,
                R.color.grad11, R.color.grad12, R.color.grad13, R.color.grad14, R.color.grad15,};

//        viewColor.setBackgroundResource(R.mipmap.gym);
        final TypedArray testArrayIcon = activity.getResources().obtainTypedArray(R.array.workout_icons);
        viewColor.setBackgroundResource(testArrayIcon.getResourceId(position, -1));
//        viewColor.setBackgroundColor(activity.getResources().getColor(colors[colorIndex]));
        colorIndex++;
        if (colorIndex >= 15) {
            colorIndex = 0;
        }

        Typeface customFont = Typeface.createFromAsset(activity.getAssets(), "fonts/helvetica-neue-ultra-light.ttf");
        TextView title = (TextView) convertView.findViewById(R.id.title); // title
        TextView desc = (TextView) convertView.findViewById(R.id.workout_desc); // title

        title.setTypeface(customFont, Typeface.BOLD);
        desc.setTypeface(customFont, Typeface.BOLD);
        ImageView thumbNail = (ImageView) convertView.findViewById(R.id.grid_image);

        //bgView = (View) convertView.findViewById(R.id.bg);

        GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(thumbNail);
//        Glide.with(activity).load(R.drawable.run).into(imageViewTarget);
        thumbNail.setVisibility(View.GONE);
        title.setText(workouts.get(position).getTitle());
        desc.setText(workouts.get(position).getDescription());

        return convertView;
    }

    public void resetData() {
        workouts.clear();
    }

    public void updateData(ArrayList<Workout> workoutsArrayList) {
        workouts.clear();
        workouts.addAll(workoutsArrayList);

    }
}

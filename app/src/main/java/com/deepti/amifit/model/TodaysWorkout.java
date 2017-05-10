package com.deepti.amifit.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.orm.SugarRecord;

/**
 * Created by deepti on 24/01/17.
 */

public class TodaysWorkout extends SugarRecord implements Parcelable {

    public Long myWorkoutProgramId;
    public String status;

    public Long getMyWorkoutProgramId() {
        return myWorkoutProgramId;
    }

    public void setMyWorkoutProgramId(Long myWorkoutProgramId) {
        this.myWorkoutProgramId = myWorkoutProgramId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public TodaysWorkout(Long myWorkoutProgramId, String status) {
        this.myWorkoutProgramId = myWorkoutProgramId;
        this.status = status;
    }

    public TodaysWorkout() {
    }

    protected TodaysWorkout(Parcel in) {
    }

    public static final Creator<TodaysWorkout> CREATOR = new Creator<TodaysWorkout>() {
        @Override
        public TodaysWorkout createFromParcel(Parcel in) {
            return new TodaysWorkout(in);
        }

        @Override
        public TodaysWorkout[] newArray(int size) {
            return new TodaysWorkout[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}

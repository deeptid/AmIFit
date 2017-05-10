package com.deepti.amifit.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.orm.SugarRecord;

/**
 * Created by deepti on 19/12/16.
 */
public class Steps  extends SugarRecord implements Parcelable {

    int numberOfSteps;
    String time;

    public int getNumberOfSteps() {
        return numberOfSteps;
    }

    public void setNumberOfSteps(int numberOfSteps) {
        this.numberOfSteps = numberOfSteps;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    protected Steps(Parcel in) {
        numberOfSteps = in.readInt();
        time = in.readString();
    }

    public Steps() {
    }

    public Steps(int numberOfSteps, String time){
        this.numberOfSteps = numberOfSteps;
        this.time = time;
    }

    public static final Creator<Steps> CREATOR = new Creator<Steps>() {
        @Override
        public Steps createFromParcel(Parcel in) {
            return new Steps(in);
        }

        @Override
        public Steps[] newArray(int size) {
            return new Steps[size];
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

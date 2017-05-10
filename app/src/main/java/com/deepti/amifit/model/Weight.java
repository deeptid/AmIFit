package com.deepti.amifit.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.orm.SugarRecord;

/**
 * Created by deepti on 03/01/17.
 */

public class Weight extends SugarRecord implements Parcelable {
    float currentWeight;
    String time;

    public float getCurrentWeight() {
        return currentWeight;
    }

    public void setCurrentWeight(int currentWeight) {
        this.currentWeight = currentWeight;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    protected Weight(Parcel in) {
    }

    public Weight() {
    }

    public Weight(float currentWeight, String time){
        this.currentWeight = currentWeight;
        this.time = time;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Weight> CREATOR = new Creator<Weight>() {
        @Override
        public Weight createFromParcel(Parcel in) {
            return new Weight(in);
        }

        @Override
        public Weight[] newArray(int size) {
            return new Weight[size];
        }
    };
}

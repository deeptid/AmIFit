package com.deepti.amifit.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.orm.SugarRecord;

/**
 * Created by deepti on 18/01/17.
 */

public class MyWorkoutProgram extends SugarRecord implements Parcelable {

    public String name;
    public String type;
    public String time;
    public String dayOfTheWeek;

//    public Exercise getExercise() {
//        return exercise;
//    }
//
//    public void setExercise(Exercise exercise) {
//        this.exercise = exercise;
//    }

    public String getDayOfTheWeek() {
        return dayOfTheWeek;
    }

    public void setDayOfTheWeek(String dayOfTheWeek) {
        this.dayOfTheWeek = dayOfTheWeek;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public MyWorkoutProgram() {
    }

    public MyWorkoutProgram(String name, String time, String type, String dayOfTheWeek) {
//        this.exercise = exercise;
        this.name = name;
        this.type = type;
        this.time = time;
        this.dayOfTheWeek = dayOfTheWeek;
    }

    protected MyWorkoutProgram(Parcel in) {
//        exercise = (Exercise) in.readValue(Exercise.class.getClassLoader());
        name = in.readString();
        type = in.readString();
        time = in.readString();
        dayOfTheWeek = in.readString();
    }

    public static final Creator<MyWorkoutProgram> CREATOR = new Creator<MyWorkoutProgram>() {
        @Override
        public MyWorkoutProgram createFromParcel(Parcel in) {
            return new MyWorkoutProgram(in);
        }

        @Override
        public MyWorkoutProgram[] newArray(int size) {
            return new MyWorkoutProgram[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeValue(exercise);
        dest.writeString(dayOfTheWeek);
    }

    @Override
    public int describeContents() {
        return 0;
    }


}

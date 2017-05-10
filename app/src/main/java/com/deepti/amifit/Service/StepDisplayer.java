package com.deepti.amifit.Service;

import com.deepti.amifit.util.TextToSpeechUtil;

import java.util.ArrayList;

/**
 * Created by deepti on 01/02/17.
 */
public class StepDisplayer implements StepListener, SpeakingTimer.Listener {

    private int mCount = 0;
    PedometerSettings mSettings;
    TextToSpeechUtil mUtils;

    public StepDisplayer(PedometerSettings settings, TextToSpeechUtil utils) {
        mUtils = utils;
        mSettings = settings;
        notifyListener();
    }
    public void setUtils(TextToSpeechUtil utils) {
        mUtils = utils;
    }

    public void setSteps(int steps) {
        mCount = steps;
        notifyListener();
    }
    public void onStep() {
        mCount ++;
        notifyListener();
    }
    public void reloadSettings() {
        notifyListener();
    }
    public void passValue() {
    }



    //-----------------------------------------------------
    // Listener

    public interface Listener {
        public void stepsChanged(int value);
        public void passValue();
    }
    private ArrayList<Listener> mListeners = new ArrayList<Listener>();

    public void addListener(Listener l) {
        mListeners.add(l);
    }
    public void notifyListener() {
        for (Listener listener : mListeners) {
            listener.stepsChanged((int)mCount);
        }
    }

    //-----------------------------------------------------
    // Speaking

    public void speak() {
        if (mSettings.shouldTellSteps()) {
            if (mCount > 0) {
                mUtils.say("" + mCount + " steps");
            }
        }
    }


}


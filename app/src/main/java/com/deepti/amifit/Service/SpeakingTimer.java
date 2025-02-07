package com.deepti.amifit.Service;

import com.deepti.amifit.util.TextToSpeechUtil;

import java.util.ArrayList;

/**
 * Created by deepti on 01/02/17.
 */
public class SpeakingTimer implements StepListener {

    PedometerSettings mSettings;
    TextToSpeechUtil mUtils;
    boolean mShouldSpeak;
    float mInterval;
    long mLastSpeakTime;

    public SpeakingTimer(PedometerSettings settings, TextToSpeechUtil utils) {
        mLastSpeakTime = System.currentTimeMillis();
        mSettings = settings;
        mUtils = utils;
        reloadSettings();
    }
    public void reloadSettings() {
        mShouldSpeak = mSettings.shouldSpeak();
        mInterval = mSettings.getSpeakingInterval();
    }

    public void onStep() {
        long now = System.currentTimeMillis();
        long delta = now - mLastSpeakTime;

        if (delta / 60000.0 >= mInterval) {
            mLastSpeakTime = now;
            notifyListeners();
        }
    }

    public void passValue() {
        // not used
    }


    //-----------------------------------------------------
    // Listener

    public interface Listener {
        public void speak();
    }
    private ArrayList<Listener> mListeners = new ArrayList<Listener>();

    public void addListener(Listener l) {
        mListeners.add(l);
    }
    public void notifyListeners() {
        mUtils.ding();
        for (Listener listener : mListeners) {
            listener.speak();
        }
    }

    //-----------------------------------------------------
    // Speaking

    public boolean isSpeaking() {
        return mUtils.isSpeakingNow();
    }
}

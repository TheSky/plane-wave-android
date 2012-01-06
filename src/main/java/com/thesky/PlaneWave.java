package com.thesky;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import android.widget.TextView;
import com.thesky.PlaneWaveView.PlaneWaveThread;

public class PlaneWave extends Activity {

    private static String TAG = "plane-wave-android";

    private PlaneWaveView mPlaneWaveView;
    private PlaneWaveThread mPlaneWaveThread;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate");
        setContentView(R.layout.main);

        mPlaneWaveView = (PlaneWaveView) findViewById(R.id.planewave);
        mPlaneWaveThread = mPlaneWaveView.getPlaneWaveThread();

        // give the LunarView a handle to the TextView used for messages
         mPlaneWaveView.setTextView((TextView) findViewById(R.id.text));

        /*  if (savedInstanceState == null) {
        // we were just launched: set up a new wave
        mPlaneWaveThread.setState(PlaneWaveThread.STATE_READY);
        Log.w(this.getClass().getName(), "savedInstanceState is null");
    } else {
        // we are being restored: resume a previous game
        mLunarThread.restoreState(savedInstanceState);
        Log.w(this.getClass().getName(), "SIS is nonnull");
    }    */


    }

}


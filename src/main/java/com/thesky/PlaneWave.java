package com.thesky;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import com.thesky.PlaneWaveView.PlaneWaveThread;

public class PlaneWave extends Activity {

    private static String TAG = "plane-wave-android";

    private PlaneWaveView mPlaneWaveView;
    private PlaneWaveThread mPlaneWaveThread;

    private static final int MENU_SMALL_WAVE = 1;
    private static final int MENU_LARGE_WAVE = 2;
    private static final int MENU_ONE_WAVE = 3;
    private static final int MENU_TWO_WAVES = 4;

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case MENU_ONE_WAVE: {
                //   mPlaneWaveView.setSecondWaveActive(false);
                try {
                    mPlaneWaveView.getPlaneWaveThread2().interrupt();
                } catch (Exception e) {

                }
                return true;
            }
            case MENU_TWO_WAVES: {
                //  mPlaneWaveView.setSecondWaveActive(true);
                try {
                    mPlaneWaveView.getPlaneWaveThread2().init_params(mPlaneWaveView.getWidth(), mPlaneWaveView.getHeight(), 150);
                    mPlaneWaveView.getPlaneWaveThread2().setRunning(true);
                    mPlaneWaveView.getPlaneWaveThread2().start();

                } catch (Exception e) {

                }
                return true;
            }
            case MENU_SMALL_WAVE: {
                mPlaneWaveView.getPlaneWaveThread().setWaveLength(12);

                if (mPlaneWaveView.getPlaneWaveThread2() != null) {
                    mPlaneWaveView.getPlaneWaveThread2().setWaveLength(12);
                }
                return true;
            }
            case MENU_LARGE_WAVE: {
                mPlaneWaveView.getPlaneWaveThread().setWaveLength(40);
                if (mPlaneWaveView.getPlaneWaveThread2() != null) {
                    mPlaneWaveView.getPlaneWaveThread2().setWaveLength(40);
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0, MENU_SMALL_WAVE, 0, R.string.menu_smallwave);
        menu.add(0, MENU_LARGE_WAVE, 0, R.string.menu_largewave);
        menu.add(0, MENU_ONE_WAVE, 0, R.string.menu_onewave);
        menu.add(0, MENU_TWO_WAVES, 0, R.string.menu_twowaves);
        return true;
    }
}


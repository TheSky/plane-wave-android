package com.thesky;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class PlaneWave extends Activity {

    private PlaneWaveView mPlaneWaveView;

    private static final int MENU_SMALL_WAVE = 1;
    private static final int MENU_LARGE_WAVE = 2;
    private static final int MENU_ONE_WAVE = 3;
    private static final int MENU_TWO_WAVES = 4;
    private static final int MENU_LOW_DIST = 5;
    private static final int MENU_HIGH_DIST = 6;
    private static final int MENU_LOW_SPEED = 7;
    private static final int MENU_HIGH_SPEED = 8;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mPlaneWaveView = (PlaneWaveView) findViewById(R.id.planewave);

        mPlaneWaveView.setTextView1((TextView) findViewById(R.id.text));
        mPlaneWaveView.setTextView2((TextView) findViewById(R.id.text2));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case MENU_ONE_WAVE: {
                try {
                    mPlaneWaveView.getPlaneWaveThread2().setRunning(false);
                } catch (NullPointerException e) {
                }
                return true;
            }
            case MENU_TWO_WAVES: {
                try {
                    mPlaneWaveView.getPlaneWaveThread2().init_params(mPlaneWaveView.getWidth(), mPlaneWaveView.getHeight(), 150);
                    mPlaneWaveView.getPlaneWaveThread2().setRunning(true);
                    mPlaneWaveView.getPlaneWaveThread2().start();
                } catch (NullPointerException e) {
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

            case MENU_LOW_DIST: {
                mPlaneWaveView.getPlaneWaveThread().setVariance(600);
                if (mPlaneWaveView.getPlaneWaveThread2() != null) {
                    mPlaneWaveView.getPlaneWaveThread2().setVariance(600);
                }
                return true;
            }

            case MENU_HIGH_DIST: {
                mPlaneWaveView.getPlaneWaveThread().setVariance(200);
                if (mPlaneWaveView.getPlaneWaveThread2() != null) {
                    mPlaneWaveView.getPlaneWaveThread2().setVariance(200);
                }
                return true;
            }
            case MENU_LOW_SPEED: {
                mPlaneWaveView.getPlaneWaveThread().setSpeed(10);
                if (mPlaneWaveView.getPlaneWaveThread2() != null) {
                    mPlaneWaveView.getPlaneWaveThread2().setSpeed(10);
                }
                return true;
            }

            case MENU_HIGH_SPEED: {
                mPlaneWaveView.getPlaneWaveThread().setSpeed(80);
                if (mPlaneWaveView.getPlaneWaveThread2() != null) {
                    mPlaneWaveView.getPlaneWaveThread2().setSpeed(80);
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0, MENU_LOW_SPEED, 0, R.string.menu_lowspeed);
        menu.add(0, MENU_HIGH_SPEED, 0, R.string.menu_highspeed);
        menu.add(0, MENU_SMALL_WAVE, 0, R.string.menu_smallwave);
        menu.add(0, MENU_LARGE_WAVE, 0, R.string.menu_largewave);
        menu.add(0, MENU_LOW_DIST, 0, R.string.menu_lowdist);
        menu.add(0, MENU_HIGH_DIST, 0, R.string.menu_highdist);
        menu.add(0, MENU_ONE_WAVE, 0, R.string.menu_onewave);
        menu.add(0, MENU_TWO_WAVES, 0, R.string.menu_twowaves);
        return true;
    }
}


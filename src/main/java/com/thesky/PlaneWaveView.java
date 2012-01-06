package com.thesky;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;

public class PlaneWaveView extends SurfaceView implements SurfaceHolder.Callback {

    private final PlaneWaveThread planeWaveThread;
    private final PlaneWaveThread planeWaveThread2;
    private Context mContext;
    private TextView mStatusText1;
    private TextView mStatusText2;

    private final PerformanceAnalyzer PA = new PerformanceAnalyzer();

    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        planeWaveThread.init_params(getWidth(), getHeight(), 45);
        planeWaveThread.setRunning(true);
        planeWaveThread.start();

        //Start performance analyzer
        PA.startPA();
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        boolean retry = true;
        planeWaveThread.setRunning(false);
        try {
            planeWaveThread2.setRunning(false);
        } catch (Exception e) {
        }
        while (retry) {
            try {
                planeWaveThread.join();
                retry = false;
            } catch (InterruptedException e) {
            }
        }
        retry = true;
        while (retry) {
            try {
                planeWaveThread2.join();
                retry = false;
            } catch (InterruptedException e) {
            }
        }


    }

    public PlaneWaveView(Context context, AttributeSet attrs) {
        super(context, attrs);

        SurfaceHolder holder = getHolder();
        holder.addCallback(this);

        planeWaveThread = new PlaneWaveThread(holder, context, "text", new Handler() {
            @Override
            public void handleMessage(Message m) {
                mStatusText1.setText("Thread-1 Time-Performance Factor: " + m.getData().getString("text"));
            }
        });

        planeWaveThread2 = new PlaneWaveThread(holder, context, "text2", new Handler() {
            @Override
            public void handleMessage(Message m) {
                mStatusText2.setText("Thread-2 Time-Performance Factor: " + m.getData().getString("text2"));
            }
        });
    }


    class PlaneWaveThread extends Thread {

        private final SurfaceHolder mSurfaceHolder;
        private Bitmap image_background;
        private Bitmap image_overlay;
        private final Handler mHandler;
        private final String receiver;

        private long currentPerformanceFactor = 0;

        private boolean mRun = false;

        private Wave wave;
        private int length = 12;

        public void init_params(int width, int height, int angle) {
            wave = new Wave(height, width, angle);
            Bitmap source_image = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.sky);

            //Scaling Filter REDUCES INITIALIZATION TIME DRAMATICALLY!
            //image_background = Bitmap.createScaledBitmap(source_image, width, height, false);
            image_background = Bitmap.createBitmap(source_image);
            image_overlay = Bitmap.createBitmap(image_background);
        }

        public PlaneWaveThread(SurfaceHolder surfaceHolder, Context context, String receiver, Handler handler) {
            mSurfaceHolder = surfaceHolder;
            mHandler = handler;
            mContext = context;
            this.receiver = receiver;
        }

        @Override
        public void run() {
            while (mRun) {
                Canvas c = null;
                try {
                    c = mSurfaceHolder.lockCanvas();
                    synchronized (mSurfaceHolder) {
                        try {
                            doDraw(c);
                            sleep(15);
                            long start_time = System.currentTimeMillis();
                            wave.randomize_params();
                            wave.propagate();
                            image_overlay = Bitmap.createBitmap(image_background);
                            wave.stretch(image_background, image_overlay);
                            wave.setLength(length);
                            long end_time = System.currentTimeMillis();

                            currentPerformanceFactor = end_time - start_time;

                        } catch (Exception e) {
                        }
                    }
                } finally {
                    if (c != null) {
                        mSurfaceHolder.unlockCanvasAndPost(c);
                    }
                }
            }
            currentPerformanceFactor = 0;
        }

        private void doDraw(Canvas c) {
            c.drawBitmap(image_overlay, 0, 0, null);
        }


        public void setRunning(boolean b) {
            mRun = b;
        }


        public void setWaveLength(int length) {
            this.length = length;
        }

        public synchronized long getCurrentPerformanceFactor() {
            return currentPerformanceFactor;
        }
    }

    public class PerformanceAnalyzer extends Thread {

        public void startPA() {
            this.start();
        }

        @Override
        public void run() {
            while (true) {
                try {
                    sleep(1000);
                    sendMessage(getPlaneWaveThread().receiver,
                            String.valueOf(getPlaneWaveThread().getCurrentPerformanceFactor()),
                            getPlaneWaveThread().mHandler);
                    sendMessage(getPlaneWaveThread2().receiver,
                            String.valueOf(getPlaneWaveThread2().getCurrentPerformanceFactor()),
                            getPlaneWaveThread2().mHandler);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        public void sendMessage(String receiver, String str, Handler h) {
            Message msg = h.obtainMessage();
            Bundle b = new Bundle();
            b.putString(receiver, str);
            msg.setData(b);
            h.sendMessage(msg);
        }
    }


    public PlaneWaveThread getPlaneWaveThread() {
        return planeWaveThread;
    }

    public PlaneWaveThread getPlaneWaveThread2() {
        return planeWaveThread2;
    }

    public void setTextView1(TextView textView) {
        mStatusText1 = textView;
    }

    public void setTextView2(TextView textView) {
        mStatusText2 = textView;
    }


}

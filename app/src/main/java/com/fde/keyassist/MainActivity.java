package com.fde.keyassist;

import static com.fde.keyassist.EventUtils.injectMotionEvent;
import static com.fde.keyassist.EventUtils.lerp;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.InputDevice;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

import com.genymobile.scrcpy.Device;

public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";
    private Instrumentation instrumentation = new Instrumentation();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);



//        try {
//            Class clazz = Settings.class;
//            Field field = null;
//            field = clazz.getDeclaredField("ACTION_MANAGE_OVERLAY_PERMISSION");
//            Intent intent = new Intent(field.get(null).toString());
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            intent.setData(Uri.parse("package:" + getPackageName()));
//            startActivity(intent);
//        } catch (NoSuchFieldException e) {
//            throw new RuntimeException(e);
//        } catch (IllegalAccessException e) {
//            throw new RuntimeException(e);
//        }


        getWindow().getDecorView().postDelayed(new Runnable() {
            @Override
            public void run() {
//                tapclick(null);
            }
        },5000);

    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d(TAG, "dispatchTouchEvent: ev:" + ev + "");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        Log.d(TAG, "onKeyUp: keyCode:" + keyCode + ", event:" + event + "");
        return super.onKeyUp(keyCode, event);
    }

    @Override
    public boolean onGenericMotionEvent(MotionEvent event) {
        Log.d(TAG, "onGenericMotionEvent: event:" + event + "");
        return super.onGenericMotionEvent(event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_A){
            leftclick(null);
        } else if(keyCode == KeyEvent.KEYCODE_D) {
            rightclick(null);
        } else if(keyCode == KeyEvent.KEYCODE_F) {
            try {
                swipe(null);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "onTouchEvent: event:" + event + "");
        if(event.getPointerCount() == 2){
            Log.d(TAG, "pointerId 0: " + event.getPointerId(0) +
                    "pointerId 1: " + event.getPointerId(1));
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        Log.d(TAG, "dispatchKeyEvent: event:" + event + "");
        return super.dispatchKeyEvent(event);
    }

    public void swipe(View view) {
//        final float x1 = 100f;
//        final float y1 = 100f;
//        final float x2 = 200f;
//        final float y2 = 200f;
//        int duration = 1000;
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                swipeLine(x1, y1, x2, y2, duration, InputDevice.SOURCE_MOUSE);
//            }
//        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                MultiTouchSimulator.simulateSingleFingerTouch(instrumentation);
            }
        }).start();
    }

    public static void swipeLine(float x1, float y1, float x2, float y2, int duration, int source ) {
        long down = SystemClock.uptimeMillis();
        injectMotionEvent(source, MotionEvent.ACTION_DOWN, down, down, x1, y1, 1.0f,
                0);
        long now = SystemClock.uptimeMillis();
        final long endTime = down + duration;
        while (now < endTime) {
            final long elapsedTime = now - down;
            final float alpha = (float) elapsedTime / duration;
            injectMotionEvent(source, MotionEvent.ACTION_MOVE, down, now,
                    lerp(x1, x2, alpha), lerp(y1, y2, alpha), 1.0f, 0);
            now = SystemClock.uptimeMillis();
        }
        injectMotionEvent(source, MotionEvent.ACTION_UP, down, now, x2, y2, 0.0f,
                0);
    }

    public void leftclick(View view) {
        EventUtils.swipeLeft();
    }

    public void doubleswipe(View view){
        new Thread(new Runnable() {
            @Override
            public void run() {
                MultiTouchSimulator.simulateTwoFingerTouch(instrumentation);
//                try {
//                    new EventTest().handleTouch(new String[]{"twotouch", "100", "200", "0"});
//                } catch (Exception e) {
//                    throw new RuntimeException(e);
//                }
            }
        }).start();
    }

    public void rightclick(View view) {
        final float x1 = 330f;
        final float y1 = 740f;
        final float x2 = 430f;
        final float y2 = 740f;
        int duration = 1000;
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
        swipeLine(x1, y1, x2, y2, duration, InputDevice.SOURCE_TOUCHSCREEN);
//            }
//        }).start();
    }

    public void tapclick(View view){
        final float x = 1120f;
        final float y = 720f;
        final long now = SystemClock.uptimeMillis();
        EventUtils.injectMotionEvent_1(InputDevice.SOURCE_TOUCHSCREEN, MotionEvent.ACTION_DOWN, now, now, x, y, 1.0f,
                0);
        EventUtils.injectMotionEvent_1(InputDevice.SOURCE_TOUCHSCREEN, MotionEvent.ACTION_UP, now, now, x, y, 0.0f, 0);
    }

    public void swipeDown(View view){
        final float x1 = 280f;
        final float y1 = 675f;
        final float x2 = 280f;
        final float y2 = 750f;
        int duration = 500;
        new Thread(new Runnable() {
            @Override
            public void run() {
                swipeLine(x1, y1, x2, y2, duration, 0xd002);
            }
        }).start();
    }

    public void startFloat(View view){
        startService(new Intent(MainActivity.this, FloatingService.class));
    }
}

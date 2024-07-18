package com.fde.keyassist;

import static com.fde.keyassist.EventUtils.injectMotionEvent;
import static com.fde.keyassist.EventUtils.lerp;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.Instrumentation;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.InputDevice;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.fde.keyassist.controller.DoubleClickKeyMappingController;
import com.fde.keyassist.controller.SwipeKeyMappingController;
import com.fde.keyassist.controller.TapClickKeyMappingController;
import com.fde.keyassist.dialog.BaseServiceDialog;
import com.fde.keyassist.dialog.ClickDialog;
import com.fde.keyassist.dialog.SwipeDialog;
import com.fde.keyassist.event.MappingEventType;
import com.fde.keyassist.fragment.AddEventFragment;
import com.fde.keyassist.fragment.ConfigManageFragment;
import com.fde.keyassist.fragment.UserProfileFragment;
import com.fde.keyassist.sql.MyDatabaseHelper;
import com.fde.keyassist.util.Constant;

public class MainActivity extends Activity implements View.OnClickListener{

    private static final String TAG = "MainActivity";
    private Instrumentation instrumentation = new Instrumentation();

    private BaseServiceDialog serviceDialog;

    private MyDatabaseHelper dbHelper;

    private MappingEventType mappingEventType;

    private ImageView configManageImage;
    private ImageView userProfileImage;
    private ImageView addEventImage;
    private LinearLayout baseFragment;


    public void init(){
        dbHelper = new MyDatabaseHelper(this,"keyAssist.db",null,3);
        mappingEventType = new MappingEventType(this);
        configManageImage = findViewById(R.id.config_manage_image);
        userProfileImage = findViewById(R.id.user_profile_image);
        addEventImage = findViewById(R.id.add_event_image);
        baseFragment = findViewById(R.id.base_fragment);
        configManageImage.setOnClickListener(this);
        userProfileImage.setOnClickListener(this);
        addEventImage.setOnClickListener(this);
        replace(new ConfigManageFragment());
        Intent intent = new Intent(this, FloatingService.class);
        startService(intent);
    }

    public void replace(Fragment fragment){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.base_fragment,fragment);
        fragmentTransaction.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);
        init();
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        Log.d(TAG, "onKeyUp: keyCode:" + keyCode + ", event:" + event + "");
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onGenericMotionEvent(MotionEvent event) {
        Log.d(TAG, "onGenericMotionEvent: event:" + event + "");
        return true;
//        return super.onGenericMotionEvent(event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        Integer eventType = mappingEventType.getEventType(keyCode);
//        if(eventType == Constant.TAP_CLICK_EVENT){
//            TapClickKeyMappingController controller = new TapClickKeyMappingController(this);
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    controller.executeEvent(keyCode);
//                }
//            }).start();
//
//        } else if (eventType == Constant.DOUBLE_CLICK_EVENT) {
//            DoubleClickKeyMappingController controller = new DoubleClickKeyMappingController(this);
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    controller.executeEvent(keyCode);
//                }
//            }).start();
//
//        }else if(eventType == Constant.SWIPE){
//            SwipeKeyMappingController controller = new SwipeKeyMappingController(this);
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    controller.executeEvent(keyCode);
//                }
//            }).start();
//
//        }
//        return super.onKeyDown(keyCode, event);
        return false;
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



    public void doubleclick(View view){
        serviceDialog = new ClickDialog(this,Constant.DOUBLE_CLICK_EVENT);
        serviceDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
//                MenuDialog.this.show();
            }
        });
        serviceDialog.show();
    }

    public void tapclick(View view){

        serviceDialog = new ClickDialog(this,Constant.TAP_CLICK_EVENT);
        serviceDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
//                MenuDialog.this.show();
            }
        });
        serviceDialog.show();
//        dismiss();

//        final float x = 1120f;
//        final float y = 720f;
//        final long now = SystemClock.uptimeMillis();
//        EventUtils.injectMotionEvent_1(InputDevice.SOURCE_TOUCHSCREEN, MotionEvent.ACTION_DOWN, now, now, x, y, 1.0f,
//                0);
//        EventUtils.injectMotionEvent_1(InputDevice.SOURCE_TOUCHSCREEN, MotionEvent.ACTION_UP, now, now, x, y, 0.0f, 0);
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

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.add_event_image:
                replace(new AddEventFragment());
                break;
            case R.id.config_manage_image:
                replace(new ConfigManageFragment());
                configManageImage.setImageResource(R.drawable.config_manage_true);
                userProfileImage.setImageResource(R.drawable.user_profile_false);
                break;
            case R.id.user_profile_image:
                replace(new UserProfileFragment());
                configManageImage.setImageResource(R.drawable.config_manage_false);
                userProfileImage.setImageResource(R.drawable.user_profile_true);
                break;
        }
    }
}

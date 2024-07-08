package com.fde.keyassist;

import static android.view.Display.DEFAULT_DISPLAY;
import static android.view.Display.INVALID_DISPLAY;

import android.os.SystemClock;
import android.util.Log;
import android.view.InputDevice;
import android.view.MotionEvent;

import com.genymobile.scrcpy.Device;
import com.genymobile.scrcpy.wrappers.InputManager;

public class EventUtils {


    private static String TAG  = "EventUtils";

    public static void injectMotionEvent(int inputSource, int action, long downTime, long when,
                                         float x, float y, float pressure, int displayId) {
        final float DEFAULT_SIZE = 1.0f;
        final int DEFAULT_META_STATE = 0;
        final float DEFAULT_PRECISION_X = 1.0f;
        final float DEFAULT_PRECISION_Y = 1.0f;
        final int DEFAULT_EDGE_FLAGS = 0;
        MotionEvent event = MotionEvent.obtain(downTime, when, action, x, y, pressure, DEFAULT_SIZE,
                DEFAULT_META_STATE, DEFAULT_PRECISION_X, DEFAULT_PRECISION_Y,
                getInputDeviceId(inputSource)
                , DEFAULT_EDGE_FLAGS);
        event.setSource(inputSource);
        if (displayId == -1 && (inputSource & InputDevice.SOURCE_CLASS_POINTER) != 0) {
            displayId = 0;
        }
        Log.d(TAG, "injectMotionEvent: event:" + event);
        try {
            Device.injectEvent(event, 0,
                    2);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public static void injectMotionEvent_1(int inputSource, int action, long downTime, long when,
                                          float x, float y, float pressure, int displayId) {
        final float DEFAULT_SIZE = 1.0f;
        final int DEFAULT_META_STATE = 0;
        final float DEFAULT_PRECISION_X = 1.0f;
        final float DEFAULT_PRECISION_Y = 1.0f;
        final int DEFAULT_EDGE_FLAGS = 0;
        Log.d(TAG, "injectMotionEvent_1: inputSource:" + inputSource + ", action:" + action + ", downTime:" + downTime + ", when:" + when + ", x:" + x + ", y:" + y + ", pressure:" + pressure + ", displayId:" + displayId + "");
        MotionEvent event = MotionEvent.obtain(downTime, when, action, x, y, pressure, DEFAULT_SIZE,
                DEFAULT_META_STATE, DEFAULT_PRECISION_X, DEFAULT_PRECISION_Y,
                4, DEFAULT_EDGE_FLAGS);
        event.setSource(0xd002);
        if (displayId == INVALID_DISPLAY && (inputSource & InputDevice.SOURCE_CLASS_POINTER) != 0) {
            displayId = DEFAULT_DISPLAY;
        }
        Log.d(TAG, "injectMotionEvent_1: event:" + event);
//        event.setDisplayId(displayId);
        Device.injectEvent(event, 0,
                2);
    }

    public static void injectMotionEventWithPointerId() {
        long downTime = SystemClock.uptimeMillis();
        long eventTime = SystemClock.uptimeMillis();
        final long endTime = eventTime + 1000;
        int action = MotionEvent.ACTION_POINTER_DOWN;  // 多指按下操作
        int pointerCount = 2;  // 两个手指

// 创建指针属性数组
        MotionEvent.PointerProperties[] pointerProperties = new MotionEvent.PointerProperties[pointerCount];
        pointerProperties[0] = new MotionEvent.PointerProperties();
        pointerProperties[0].id = 0;  // 第一个手指的ID
        pointerProperties[0].toolType = MotionEvent.TOOL_TYPE_FINGER;

        pointerProperties[1] = new MotionEvent.PointerProperties();
        pointerProperties[1].id = 1;  // 第二个手指的ID
        pointerProperties[1].toolType = MotionEvent.TOOL_TYPE_FINGER;

// 创建指针坐标数组
        MotionEvent.PointerCoords[] pointerCoords = new MotionEvent.PointerCoords[pointerCount];
        pointerCoords[0] = new MotionEvent.PointerCoords();
        pointerCoords[0].x = 100;
        pointerCoords[0].y = 200;
        pointerCoords[0].pressure = 1;
        pointerCoords[0].size = 1;

        pointerCoords[1] = new MotionEvent.PointerCoords();
        pointerCoords[1].x = 300;
        pointerCoords[1].y = 400;
        pointerCoords[1].pressure = 1;
        pointerCoords[1].size = 1;

        int metaState = 0;
        int buttonState = 0;
        float xPrecision = 1.0f;
        float yPrecision = 1.0f;
        int deviceId = 0;
        int edgeFlags = 0;
        int source = InputDevice.SOURCE_TOUCHSCREEN;
        int flags = 0;
        long now = SystemClock.uptimeMillis();
        MotionEvent event = MotionEvent.obtain(downTime, eventTime, action, pointerCount, pointerProperties,
                pointerCoords, metaState, buttonState, xPrecision, yPrecision,
                deviceId, edgeFlags, source, flags);
        Device.injectEvent(event, 0,
                2);
        while (now < endTime){
            pointerCoords[0].x = pointerCoords[0].x + 1;
            pointerCoords[0].y = pointerCoords[0].y + 1;

            pointerCoords[1].x = pointerCoords[1].x + 1;
            pointerCoords[1].y = pointerCoords[1].y + 1;

            action = MotionEvent.ACTION_HOVER_MOVE;
            event = MotionEvent.obtain(downTime, eventTime, action, pointerCount, pointerProperties,
                    pointerCoords, metaState, buttonState, xPrecision, yPrecision,
                    deviceId, edgeFlags, source, flags);
            Device.injectEvent(event, 0,
                    2);
            now = SystemClock.uptimeMillis();
        }
        action = MotionEvent.ACTION_POINTER_UP;
        event = MotionEvent.obtain(downTime, eventTime, action, pointerCount, pointerProperties,
                pointerCoords, metaState, buttonState, xPrecision, yPrecision,
                deviceId, edgeFlags, source, flags);
        Device.injectEvent(event, 0,
                2);


    }

    public static void injectSingleEvent(){
        long downTime = SystemClock.uptimeMillis();
        long eventTime = SystemClock.uptimeMillis();
        final long endTime = eventTime + 1000;
        int pointerCount = 1;
        MotionEvent.PointerProperties[] pointerProperties = new MotionEvent.PointerProperties[pointerCount];
        pointerProperties[0] = new MotionEvent.PointerProperties();
        pointerProperties[0].id = 0;
        pointerProperties[0].toolType = MotionEvent.TOOL_TYPE_MOUSE;
        MotionEvent.PointerCoords[] pointerCoords = new MotionEvent.PointerCoords[pointerCount];
        pointerCoords[0] = new MotionEvent.PointerCoords();
        pointerCoords[0].x = 300;
        pointerCoords[0].y = 300;
        int metaState = 0;
        int buttonState = 0;
        float xPrecision = 1.0f;
        float yPrecision = 1.0f;
        int deviceId = 0;
        int edgeFlags = 0;
        int source = InputDevice.SOURCE_MOUSE;
        int flags = 0;

        MotionEvent event = MotionEvent.obtain(downTime, eventTime, MotionEvent.ACTION_DOWN, pointerCount, pointerProperties,
                pointerCoords, metaState, buttonState, xPrecision, yPrecision,
                deviceId, edgeFlags, source, flags);
        Device.injectEvent(event, 0,
                2);
//        long now = SystemClock.uptimeMillis();
//        while (now < endTime){
//            pointerCoords[0].x = pointerCoords[0].x + 1;
//            pointerCoords[0].y = pointerCoords[0].y + 1;
//            event = MotionEvent.obtain(downTime, eventTime, MotionEvent.ACTION_HOVER_MOVE, pointerCount, pointerProperties,
//                    pointerCoords, metaState, buttonState, xPrecision, yPrecision,
//                    deviceId, edgeFlags, source, flags);
//            Device.injectEvent(event, 0,
//                    2);
//            now = SystemClock.uptimeMillis();
//        }
//        event = MotionEvent.obtain(downTime, eventTime, MotionEvent.ACTION_POINTER_UP, pointerCount, pointerProperties,
//                pointerCoords, metaState, buttonState, xPrecision, yPrecision,
//                deviceId, edgeFlags, source, flags);
//        Device.injectEvent(event, 0,
//                2);

    }

    public static int getInputDeviceId(int inputSource) {
        final int DEFAULT_DEVICE_ID = 0;
        int[] devIds = InputDevice.getDeviceIds();
        for (int devId : devIds) {
            InputDevice inputDev = InputDevice.getDevice(devId);
            if (inputDev.supportsSource(inputSource)) {
                return devId;
            }
        }
        return DEFAULT_DEVICE_ID;
    }


    public static float lerp(float a, float b, float alpha) {
        return (b - a) * alpha + a;
    }


    private static float directX1 = 280f, directY1 = 675f;
    private static float swipeLength = 75f;
    private static int swipeDuration = 500;
    private static int swipeSource = 0xd002;


    public static void swipeRight() {
        final float x1 = directX1;
        final float y1 = directY1;
        final float x2 = directX1 + swipeLength;
        final float y2 = directY1;
        new Thread(new Runnable() {
            @Override
            public void run() {
                swipeLine(x1, y1, x2, y2, swipeDuration, swipeSource);
            }
        }).start();
    }

    public static void swipeDown() {
        final float x1 = directX1;
        final float y1 = directY1;
        final float x2 = directX1;
        final float y2 = directY1 + swipeLength;
        new Thread(new Runnable() {
            @Override
            public void run() {
                swipeLine(x1, y1, x2, y2, swipeDuration, swipeSource);
            }
        }).start();
    }

    public static void swipeLeft() {
        final float x1 = directX1;
        final float y1 = directY1;
        final float x2 = directX1 - swipeLength;
        final float y2 = directY1;
        new Thread(new Runnable() {
            @Override
            public void run() {
                swipeLine(x1, y1, x2, y2, swipeDuration, swipeSource);
            }
        }).start();
    }

    public static void swipeUp() {
        final float x1 = directX1;
        final float y1 = directY1;
        final float x2 = directX1;
        final float y2 = directY1 - swipeLength;
        new Thread(new Runnable() {
            @Override
            public void run() {
                swipeLine(x1, y1, x2, y2, swipeDuration, swipeSource);
            }
        }).start();
    }

    private static void swipeLine(float x1, float y1, float x2, float y2, int duration, int source ) {
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
//        injectMotionEvent(source, MotionEvent.ACTION_UP, down, now, x2, y2, 0.0f,
//                0);
    }
}

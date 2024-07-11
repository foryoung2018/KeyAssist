package com.fde.keyassist.event;

import android.os.SystemClock;
import android.view.InputDevice;
import android.view.MotionEvent;

public class DoubleClickEvent implements MappingEvent{

    private final float x;
    private final float y;

    public DoubleClickEvent(float x,float y){
        this.x = x;
        this.y = y;
    }

    @Override
    public void execute() {
        final long first = SystemClock.uptimeMillis();
        injectMotionEvent(InputDevice.SOURCE_TOUCHSCREEN, MotionEvent.ACTION_DOWN, first, first, x, y, 1.0f,
                0);
        injectMotionEvent(InputDevice.SOURCE_TOUCHSCREEN, MotionEvent.ACTION_UP, first, first, x, y, 0.0f, 0);

        final long second = SystemClock.uptimeMillis();
        injectMotionEvent(InputDevice.SOURCE_TOUCHSCREEN, MotionEvent.ACTION_DOWN, second, second, x, y, 1.0f,
                0);
        injectMotionEvent(InputDevice.SOURCE_TOUCHSCREEN, MotionEvent.ACTION_UP, second, second, x, y, 0.0f, 0);
    }
}

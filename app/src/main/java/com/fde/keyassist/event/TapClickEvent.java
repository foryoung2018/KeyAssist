package com.fde.keyassist.event;

import android.content.Context;
import android.os.SystemClock;
import android.view.InputDevice;
import android.view.MotionEvent;

// 单击事件
public class TapClickEvent implements MappingEvent{

    private final float x;
    private final float y;
    public TapClickEvent(float x, float y){
        this.x = x;
        this.y = y;
    }

    @Override
    public void execute() {
        final long now = SystemClock.uptimeMillis();
        injectMotionEvent(InputDevice.SOURCE_TOUCHSCREEN, MotionEvent.ACTION_DOWN, now, now, x, y, 1.0f,
                0);
        injectMotionEvent(InputDevice.SOURCE_TOUCHSCREEN, MotionEvent.ACTION_UP, now, now, x, y, 0.0f, 0);
    }

}

package com.fde.keyassist.event;

import android.os.SystemClock;
import android.view.InputDevice;
import android.view.MotionEvent;

// 单击事件
public class SwipeEvent implements MappingEvent{

    private final float startX;
    private final float startY;
    private final float endX;
    private final float endY;
    private Integer swipeDuration; // 持续时间
    private Integer swipeSource;
    public SwipeEvent(float startX, float startY,float endX,float endY,Integer swipeDuration,Integer swipeSource){
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.swipeDuration = swipeDuration;
        this.swipeSource = swipeSource;
    }

    @Override
    public void execute() {
        swipeLine(startX, startY, endX, endY, swipeDuration, swipeSource);
    }

}

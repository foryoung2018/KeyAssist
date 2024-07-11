package com.fde.keyassist.event;

import static android.view.Display.DEFAULT_DISPLAY;
import static android.view.Display.INVALID_DISPLAY;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.SystemClock;
import android.util.Log;
import android.view.InputDevice;
import android.view.MotionEvent;

import com.genymobile.scrcpy.Device;


public interface MappingEvent {
     void execute();

     default void injectMotionEvent(int inputSource, int action, long downTime, long when,
                                                        float x, float y, float pressure, int displayId) {
          final float DEFAULT_SIZE = 1.0f;
          final int DEFAULT_META_STATE = 0;
          final float DEFAULT_PRECISION_X = 1.0f;
          final float DEFAULT_PRECISION_Y = 1.0f;
          final int DEFAULT_EDGE_FLAGS = 0;
          MotionEvent event = MotionEvent.obtain(downTime, when, action, x, y, pressure, DEFAULT_SIZE,
                  DEFAULT_META_STATE, DEFAULT_PRECISION_X, DEFAULT_PRECISION_Y,
                  4, DEFAULT_EDGE_FLAGS);
          event.setSource(0xd002);
          if (displayId == INVALID_DISPLAY && (inputSource & InputDevice.SOURCE_CLASS_POINTER) != 0) {
               displayId = DEFAULT_DISPLAY;
          }
//        event.setDisplayId(displayId);
          Device.injectEvent(event, 0,
                  2);
     }

     default  void swipeLine(float x1, float y1, float x2, float y2, int duration, int source ) {
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
     }

     default float lerp(float a, float b, float alpha) {
          return (b - a) * alpha + a;
     }
}

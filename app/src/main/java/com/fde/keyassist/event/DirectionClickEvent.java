package com.fde.keyassist.event;

import static android.view.KeyEvent.ACTION_DOWN;

import android.os.SystemClock;
import android.util.Log;
import android.view.InputDevice;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

import com.fde.keyassist.EventUtils;
import com.fde.keyassist.FloatingService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// 方向键事件
public class DirectionClickEvent implements MappingEvent{

    private final float x;
    private final float y;
    private Integer keyCode;
    private KeyEvent event;
    private View view;
    public DirectionClickEvent(float x, float y, Integer keyCode, KeyEvent event, View view){
        this.x = x;
        this.y = y;
        this.keyCode = keyCode;
        this.event = event;
        this.view = view;
    }

    @Override
    public void execute() {
        DirectionController.getInstance(x,y).process(keyCode, event);
    }


    public static class DirectionController {

        ExecutorService executor = Executors.newSingleThreadExecutor();
        private static float directX1 = 0f, directY1 = 0f;
        private static float swipeLength = 75f;
        private static int swipeDuration = 500;
        private static int swipeSource = 0xd002;

        private int mDirection;  // 0x1111 ADWS
        private int mSpeed;
        private int mDuration;
        private DirectionController.Pointer center,current;

        private boolean isMoving;
        private static final int DIRECTION_DOWN = 0x1;           // S
        private static final int DIRECTION_UP = 0x2;             // W
        private static final int DIRECTION_RIGHT = 0x4;          // D
        private static final int DIRECTION_LEFT = 0x8;           // A

        private static final int DIRECTION_UP_LEFT = DIRECTION_UP | DIRECTION_LEFT;        //WA
        private static final int DIRECTION_DOWN_LEFT = DIRECTION_DOWN | DIRECTION_LEFT;      //SA
        private static final int DIRECTION_UP_RIGHT = DIRECTION_RIGHT | DIRECTION_UP;       //WD
        private static final int DIRECTION_DOWN_RIGHT = DIRECTION_DOWN | DIRECTION_RIGHT;     //SD

        private volatile boolean batchDroped;

        public static DirectionClickEvent.DirectionController getInstance(Float x, Float y){
            directX1 = x;
            directY1 = y;
            return DirectionClickEvent.DirectionController.SingletonHolder.INSTANCE;
        }

        public synchronized void process(int keyCode, KeyEvent event) {
//            leftPressed = rightPressed = upPressed = downPressed = false;
            int action = event.getAction();
            int directBit = -1;
            switch (keyCode) {
                case KeyEvent.KEYCODE_A:
                    directBit = 3;
                    break;
                case KeyEvent.KEYCODE_D:
                    directBit = 2;
                    break;
                case KeyEvent.KEYCODE_W:
                    directBit = 1;
                    break;
                case KeyEvent.KEYCODE_S:
                    directBit = 0;
                    break;
                default:
                    break;
            }
            int direct = updateDirection(action, directBit);

            if(direct == 0) {
                batchDroped = true;
                EventUtils.injectMotionEvent(swipeSource, MotionEvent.ACTION_UP,
                        event.getDownTime(), event.getDownTime(),
                        center.x, center.y, 1.0f,
                        0);
                isMoving = false;
                this.mDirection = direct;
            } else {
                DirectionController.Pointer pointer = computeOffset(direct);
                executor.execute(()->processInnerOnce(direct, pointer, event.getDownTime(), false));
            }
        }

        private int updateDirection(int action, int directBit) {
            int direction = mDirection;
            switch (directBit){
                case 0: //S
                    direction = action == ACTION_DOWN ? mDirection | (1 << 0) : mDirection & (~(1 << 0));
                    int mask = ~(1 << 1);
                    direction = action == ACTION_DOWN ? direction & mask : direction;
                    break;
                case 1: //W
                    direction = action == ACTION_DOWN ? mDirection | (1 << 1) : mDirection & (~(1 << 1));
                    direction = action == ACTION_DOWN ? direction & (~(1 << 0)): direction;
                    break;
                case 2: //D
                    direction = action == ACTION_DOWN ? mDirection | (1 << 2) : mDirection & (~(1 << 2));
                    direction = action == ACTION_DOWN ? direction & (~(1 << 3)) : direction;
                    break;
                case 3: //A
                    direction = action == ACTION_DOWN ? mDirection | (1 << 3) : mDirection & (~(1 << 3));
                    direction = action == ACTION_DOWN ? direction & (~(1 << 2)) : direction;
                    break;
                default:
                    break;
            }

            return direction;
        }

        public void setDirection(int direction) {
            if (direction == DIRECTION_UP || direction == DIRECTION_DOWN ||
                    direction == DIRECTION_LEFT || direction == DIRECTION_RIGHT) {
                mDirection = direction;
            } else {
                throw new IllegalArgumentException("Invalid direction");
            }
        }

        private DirectionController.Pointer computeOffset(int direct) {
            float horizental  =  (direct & DIRECTION_RIGHT) != 0 ? swipeLength :
                    (direct & DIRECTION_LEFT) != 0 ? - swipeLength : 0;
            float vertical  = (direct & DIRECTION_DOWN) != 0 ? swipeLength :
                    (direct & DIRECTION_UP) != 0 ? - swipeLength : 0;
            return new DirectionController.Pointer(horizental, vertical);
        }

        private void processInnerOnce(int direct, DirectionController.Pointer pointer, long down, boolean once) {
            String format = String.format(" direct:%x, pointer:%s", direct, pointer);
            long now = SystemClock.uptimeMillis();
            if(mDirection == 0 &&  direct != 0){
                batchDroped = false;
                EventUtils.injectMotionEvent(swipeSource, MotionEvent.ACTION_DOWN, down, down,
                        center.x, center.y, 1.0f,
                        0);
                isMoving = false;
                moveOnce(pointer.x, pointer.y);
            } else if ( !isMoving  || mDirection != direct || once ){
                moveOnce(pointer.x, pointer.y);
            }
            this.mDirection = direct;
            long duration = SystemClock.uptimeMillis() - now;


        }

        private void moveOnce(float horizental, float vertical) {
            if(batchDroped) {
                return;
            }

            long now = SystemClock.uptimeMillis();
            EventUtils.injectMotionEvent(swipeSource, MotionEvent.ACTION_MOVE, now, now,
                    center.x + horizental, center.y + vertical, 1.0f,
                    0);
            isMoving = true;
        }

        private static class SingletonHolder {
            private static final DirectionController INSTANCE = new DirectionController();
        }

        private DirectionController(){
            setCenter(new DirectionController.Pointer(directX1, directY1));
        }

        private void setCenter(DirectionController.Pointer center){
            this.center = center;
        }

        public static class Pointer {
            public Pointer(float x, float y) {
                this.x = x;
                this.y = y;
            }
            public Pointer(){

            }
            public float x , y;

            @Override
            public String toString() {
                return "Pointer{" +
                        "x=" + x +
                        ", y=" + y +
                        '}';
            }
        }

    }

}

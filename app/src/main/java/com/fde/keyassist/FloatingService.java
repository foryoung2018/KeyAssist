package com.fde.keyassist;

import static android.view.KeyEvent.ACTION_DOWN;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;
import android.view.Gravity;
import android.view.InputDevice;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.fde.keyassist.controller.DirectionKeyMappingController;
import com.fde.keyassist.controller.DoubleClickKeyMappingController;
import com.fde.keyassist.controller.SwipeKeyMappingController;
import com.fde.keyassist.controller.TapClickKeyMappingController;
import com.fde.keyassist.event.DirectionClickEvent;
import com.fde.keyassist.event.MappingEventType;
import com.fde.keyassist.event.TapClickEvent;
import com.fde.keyassist.util.Constant;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FloatingService extends Service {

    private static final String TAG = "FloatingService";
    private View mFloatingView;
    private WindowManager.LayoutParams floatLayoutParams;
    private WindowManager mWindowManager;
    private MappingEventType mappingEventType;
    Thread direction = new Thread();
    private TapClickKeyMappingController tapClickKeyMappingController;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        showFloatView();
        mappingEventType = new MappingEventType(getApplicationContext());
        tapClickKeyMappingController = new TapClickKeyMappingController(this);
    }
//
    private void showFloatView() {
        mFloatingView = LayoutInflater.from(this).inflate(R.layout.layout_window, null);
        //设置WindowManger布局参数以及相关属性
        floatLayoutParams = new WindowManager.LayoutParams();
        floatLayoutParams.flags =
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL |
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE |
//                        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                        WindowManager.LayoutParams.FLAG_LAYOUT_INSET_DECOR |
                        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        ;
        if (Build.VERSION.SDK_INT >= 26) {
            floatLayoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            floatLayoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        }
        floatLayoutParams.width = 50;
        floatLayoutParams.height = 50;
        floatLayoutParams.format = PixelFormat.TRANSLUCENT;
        //初始化位置
        floatLayoutParams.gravity = Gravity.RIGHT | Gravity.BOTTOM;
        //获取WindowManager对象
        mWindowManager = (WindowManager) this.getSystemService(WINDOW_SERVICE);
        mWindowManager.addView(mFloatingView, floatLayoutParams);
        mFloatingView.setClickable(true);
        mFloatingView.setFocusableInTouchMode(true);
        mFloatingView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                Log.d(TAG, "onKey: " +", keyCode:" + keyCode + ", event:" + event + "");
//                if(keyCode == KeyEvent.KEYCODE_A ||
//                        keyCode == KeyEvent.KEYCODE_D ||
//                        keyCode == KeyEvent.KEYCODE_S ||
//                        keyCode == KeyEvent.KEYCODE_W
//                ) {
//                    mFloatingView.post(()-> DirectionController.getInstance().process(keyCode, event));
//                } else {
//                    if(event.getAction() == ACTION_DOWN){
//                        switch (keyCode){
//                            case KeyEvent.KEYCODE_K:
//                                final float x = 1120f;
//                                final float y = 720f;
//                                final long now = SystemClock.uptimeMillis();
//                                EventUtils.injectMotionEvent_1(InputDevice.SOURCE_TOUCHSCREEN, MotionEvent.ACTION_DOWN, now, now, x, y, 1.0f,
//                                        0);
//                                EventUtils.injectMotionEvent_1(InputDevice.SOURCE_TOUCHSCREEN, MotionEvent.ACTION_UP, now, now, x, y, 0.0f, 0);
//                                break;
//
//                            default:
//                                break;
//                        }
//                    }
//                }
//                return false;
                Integer eventType = mappingEventType.getEventType(keyCode);
                if(eventType == Constant.TAP_CLICK_EVENT){
                    TapClickKeyMappingController controller = new TapClickKeyMappingController(getApplicationContext());
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            controller.executeEvent(keyCode);
                        }
                    }).start();

                } else if (eventType == Constant.DOUBLE_CLICK_EVENT) {
                    DoubleClickKeyMappingController controller = new DoubleClickKeyMappingController(getApplicationContext());
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            controller.executeEvent(keyCode);
                        }
                    }).start();

                }else if(eventType == Constant.SWIPE){
                    SwipeKeyMappingController controller = new SwipeKeyMappingController(getApplicationContext());
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            controller.executeEvent(keyCode);
                        }
                    }).start();
                }else if (eventType == Constant.DIRECTION_KEY){
//                    Runnable runnable = new Runnable() {
//                        @Override
//                        public void run() {
//                            DirectionKeyMappingController controller = new DirectionKeyMappingController(getApplicationContext());
//                            controller.executeEvent(keyCode,event,mFloatingView);
//                        }
//                    };
//                    direction = new Thread(runnable);
//                    if(!direction.isAlive()){
//                        direction.start();
//                    }

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            DirectionKeyMappingController controller = new DirectionKeyMappingController(getApplicationContext());
                            controller.executeEvent(keyCode,event,mFloatingView);
                        }
                    }).start();

                }

                return true;
            }
        });


//        mFloatingView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                Intent intent = new Intent(FloatingService.this,MainActivity.class);
//                startActivity(intent);
//                return true;
//            }
//        });

//        mFloatingView.setOnTouchListener(new View.OnTouchListener() {
//            private int x;
//            private int y;
//            //是否在移动
//            private boolean isMoving;
//            @SuppressLint("ClickableViewAccessibility")
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                        x = (int) event.getRawX();
//                        y = (int) event.getRawY();
//                        isMoving = false;
//                        return true;
//                    case MotionEvent.ACTION_MOVE:
//                        int nowX = (int) event.getRawX();
//                        int nowY = (int) event.getRawY();
//                        int moveX = nowX - x;
//                        int moveY = nowY - y;
//                        if (Math.abs(moveX) > 0 || Math.abs(moveY) > 0) {
//                            isMoving = true;
//                            floatLayoutParams.x += moveX;
//                            floatLayoutParams.y += moveY;
//                            //更新View的位置
//                            mWindowManager.updateViewLayout(mFloatingView, floatLayoutParams);
//                            x = nowX;
//                            y = nowY;
//                            return true;
//                        }
//                        break;
//                    case MotionEvent.ACTION_UP:
//                        if (!isMoving) {
////                            onShowSelectDialog();
//                            return true;
//                        }
//                        break;
//                }
//                return false;
//            }
//        });
    }



//    public static class DirectionController {
//
//        ExecutorService executor = Executors.newSingleThreadExecutor();
//        private static float directX1 = 280f, directY1 = 675f;
//        private static float swipeLength = 75f;
//        private static int swipeDuration = 500;
//        private static int swipeSource = 0xd002;
//
//        private int mDirection;  // 0x1111 ADWS
//        private int mSpeed;
//        private int mDuration;
//        private Pointer center,current;
//
//        private boolean isMoving;
//        private static final int DIRECTION_DOWN = 0x1;           // S
//        private static final int DIRECTION_UP = 0x2;             // W
//        private static final int DIRECTION_RIGHT = 0x4;          // D
//        private static final int DIRECTION_LEFT = 0x8;           // A
//
//        private static final int DIRECTION_UP_LEFT = DIRECTION_UP | DIRECTION_LEFT;        //WA
//        private static final int DIRECTION_DOWN_LEFT = DIRECTION_DOWN | DIRECTION_LEFT;      //SA
//        private static final int DIRECTION_UP_RIGHT = DIRECTION_RIGHT | DIRECTION_UP;       //WD
//        private static final int DIRECTION_DOWN_RIGHT = DIRECTION_DOWN | DIRECTION_RIGHT;     //SD
//
//        private volatile boolean batchDroped;
//
//        public static DirectionController getInstance(){
//            return SingletonHolder.INSTANCE;
//        }
//
//        public synchronized void process(int keyCode, KeyEvent event) {
////            leftPressed = rightPressed = upPressed = downPressed = false;
//            int action = event.getAction();
//            int directBit = -1;
//            switch (keyCode) {
//                case KeyEvent.KEYCODE_A:
//                    directBit = 3;
//                    break;
//                case KeyEvent.KEYCODE_D:
//                    directBit = 2;
//                    break;
//                case KeyEvent.KEYCODE_W:
//                    directBit = 1;
//                    break;
//                case KeyEvent.KEYCODE_S:
//                    directBit = 0;
//                    break;
//                default:
//                    break;
//            }
//            int direct = updateDirection(action, directBit);
//            Log.d(TAG, "process: direct:" + direct);
//            if(direct == 0) {
//                batchDroped = true;
//                EventUtils.injectMotionEvent(swipeSource, MotionEvent.ACTION_UP,
//                        event.getDownTime(), event.getDownTime(),
//                        center.x, center.y, 1.0f,
//                        0);
//                isMoving = false;
//                this.mDirection = direct;
//            } else {
//                Pointer pointer = computeOffset(direct);
//                executor.execute(()->processInnerOnce(direct, pointer, event.getDownTime(), false));
//            }
//        }
//
//        private int updateDirection(int action, int directBit) {
//            int direction = mDirection;
//            switch (directBit){
//                case 0: //S
//                    direction = action == ACTION_DOWN ? mDirection | (1 << 0) : mDirection & (~(1 << 0));
//                    int mask = ~(1 << 1);
//                    direction = action == ACTION_DOWN ? direction & mask : direction;
//                    break;
//                case 1: //W
//                    direction = action == ACTION_DOWN ? mDirection | (1 << 1) : mDirection & (~(1 << 1));
//                    direction = action == ACTION_DOWN ? direction & (~(1 << 0)): direction;
//                    break;
//                case 2: //D
//                    direction = action == ACTION_DOWN ? mDirection | (1 << 2) : mDirection & (~(1 << 2));
//                    direction = action == ACTION_DOWN ? direction & (~(1 << 3)) : direction;
//                    break;
//                case 3: //A
//                    direction = action == ACTION_DOWN ? mDirection | (1 << 3) : mDirection & (~(1 << 3));
//                    direction = action == ACTION_DOWN ? direction & (~(1 << 2)) : direction;
//                    break;
//                default:
//                    break;
//            }
//            Log.d(TAG, "updateDirection() returned: " + direction);
//            return direction;
//        }
//
//        public void setDirection(int direction) {
//            if (direction == DIRECTION_UP || direction == DIRECTION_DOWN ||
//                    direction == DIRECTION_LEFT || direction == DIRECTION_RIGHT) {
//                mDirection = direction;
//            } else {
//                throw new IllegalArgumentException("Invalid direction");
//            }
//        }
//
//        private Pointer computeOffset(int direct) {
//            float horizental  =  (direct & DIRECTION_RIGHT) != 0 ? swipeLength :
//                    (direct & DIRECTION_LEFT) != 0 ? - swipeLength : 0;
//            float vertical  = (direct & DIRECTION_DOWN) != 0 ? swipeLength :
//                    (direct & DIRECTION_UP) != 0 ? - swipeLength : 0;
//            return new Pointer(horizental, vertical);
//        }
//
//        private void processInnerOnce(int direct, Pointer pointer, long down, boolean once) {
//            String format = String.format(" direct:%x, pointer:%s", direct, pointer);
//            long now = SystemClock.uptimeMillis();
//            if(mDirection == 0 &&  direct != 0){
//                batchDroped = false;
//                EventUtils.injectMotionEvent(swipeSource, MotionEvent.ACTION_DOWN, down, down,
//                        center.x, center.y, 1.0f,
//                        0);
//                isMoving = false;
//                moveOnce(pointer.x, pointer.y);
//            } else if ( !isMoving  || mDirection != direct || once ){
//                moveOnce(pointer.x, pointer.y);
//            }
//            this.mDirection = direct;
//            long duration = SystemClock.uptimeMillis() - now;
//            Log.d(TAG, "processInnerOnce: duration:" + duration + " move:" + format);
//
//        }
//
//        private void moveOnce(float horizental, float vertical) {
//            if(batchDroped) {
//                return;
//            }
//            Log.d(TAG, "moveOnce: horizental:" + horizental + ", vertical:" + vertical + "");
//            long now = SystemClock.uptimeMillis();
//            EventUtils.injectMotionEvent(swipeSource, MotionEvent.ACTION_MOVE, now, now,
//                    center.x + horizental, center.y + vertical, 1.0f,
//                    0);
//            isMoving = true;
//        }
//
//        private static class SingletonHolder {
//            private static final DirectionController INSTANCE = new DirectionController();
//        }
//
//        private DirectionController(){
//            setCenter(new Pointer(280f, 675f));
//        }
//
//        private void setCenter(Pointer center){
//            this.center = center;
//        }
//
//        public static class Pointer {
//            public Pointer(float x, float y) {
//                this.x = x;
//                this.y = y;
//            }
//            public Pointer(){
//
//            }
//            public float x , y;
//
//            @Override
//            public String toString() {
//                return "Pointer{" +
//                        "x=" + x +
//                        ", y=" + y +
//                        '}';
//            }
//        }
//
//    }




}

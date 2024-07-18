package com.fde.keyassist.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fde.keyassist.FloatingService;
import com.fde.keyassist.R;
import com.fde.keyassist.adapter.AllConfigAdapter;
import com.fde.keyassist.controller.DoubleClickKeyMappingController;
import com.fde.keyassist.controller.SwipeKeyMappingController;
import com.fde.keyassist.controller.TapClickKeyMappingController;
import com.fde.keyassist.dialog.DirectionKeyDialog;
import com.fde.keyassist.dialog.HornorOfKingsDialog;
import com.fde.keyassist.entity.ConfigManage;
import com.fde.keyassist.event.DoubleClickEvent;
import com.fde.keyassist.event.SwipeEvent;
import com.fde.keyassist.event.TapClickEvent;
import com.fde.keyassist.util.Constant;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class HonorOfKingsActivity extends Activity {

    private TapClickKeyMappingController tapClickKeyMappingController;
    private LinearLayout honorOfKings;
    private TextView honorConfig;
    private TextView hornor_use_config;

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置布局
        setContentView(R.layout.activity_honor_of_kings);
        hornor_use_config = findViewById(R.id.hornor_use_config);
        honorOfKings = findViewById(R.id.honor_of_kings);
        honorConfig = findViewById(R.id.honor_config);
        tapClickKeyMappingController = new TapClickKeyMappingController(this);
        honorConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HornorOfKingsDialog serviceDialog = new HornorOfKingsDialog(getApplication());
                serviceDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                    }
                });
                serviceDialog.show();

            }
        });

        // 使用布局
        hornor_use_config.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLayout(0);
            }
        });

    }


    void showLayout(Integer configType){
        if(configType == 0){
            showHornorKings();
        }
    }

    //显示王者荣耀布局
    void showHornorKings(){

        Map<Integer, TapClickEvent> query = tapClickKeyMappingController.query();

        createText(KeyEvent.KEYCODE_J,query,"J");
        createText(KeyEvent.KEYCODE_K,query,"K");
        createText(KeyEvent.KEYCODE_L,query,"L");
        createText(KeyEvent.KEYCODE_SPACE,query,"SPACE");
        createText(KeyEvent.KEYCODE_U,query,"U");
        createText(KeyEvent.KEYCODE_O,query,"O");
        createText(KeyEvent.KEYCODE_I,query,"I");
        createText(KeyEvent.KEYCODE_N,query,"N");
        createText(KeyEvent.KEYCODE_B,query,"B");
        createText(KeyEvent.KEYCODE_M,query,"M");
        createText(KeyEvent.KEYCODE_E,query,"E");
        createText(KeyEvent.KEYCODE_R,query,"R");


    }

    void createText(int keycode,Map<Integer, TapClickEvent> query,String text){
        TapClickEvent tapClickEvent = query.get(keycode);
        if(tapClickEvent != null){
            TextView textView = new TextView(this);
            textView.setBackgroundResource(R.drawable.circle_background);
            textView.setText(text);
            textView.setGravity(Gravity.CENTER);
            textView.setTextColor(Color.WHITE);
            showView((int)tapClickEvent.getX(),(int)tapClickEvent.getY(),textView);
        }

    }

    void showView(int x, int y,View view){
        WindowManager windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY, // 或者使用 TYPE_PHONE
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE|WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                PixelFormat.TRANSLUCENT);


        params.gravity = Gravity.TOP | Gravity.LEFT;

        int width = view.getWidth();
        int height = view.getHeight();
        // 中心点的x坐标是左上角x坐标加上宽度的一半
        x = x - 15;
        // 中心点的y坐标是左上角y坐标加上高度的一半
        y = y - 15;

        params.x = x;
        params.y = y;
        windowManager.addView(view, params);


        // 设置触摸事件监听器
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // 获取 WindowManager
                WindowManager windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

                // 获取根视图
                View rootView = getWindow().getDecorView().findViewById(android.R.id.content);

                // 获取点击位置
                float rawX = event.getRawX();
                float rawY = event.getRawY();

                // 创建新的 MotionEvent
                MotionEvent newEvent = MotionEvent.obtain(
                        event.getDownTime(),
                        event.getEventTime(),
                        event.getAction(),
                        rawX,
                        rawY,
                        event.getMetaState()
                );

                // 分发点击事件到根视图
                rootView.dispatchTouchEvent(newEvent);

                // 回收 MotionEvent 对象
                newEvent.recycle();

                // 返回 false 以继续处理事件
                return false;
            }
        });
    }


}

package com.fde.keyassist.dialog;

import android.content.Context;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.fde.keyassist.R;
import com.fde.keyassist.controller.SwipeKeyMappingController;
import com.fde.keyassist.entity.SwipeKeyMapping;
import com.fde.keyassist.event.MappingEventType;
import com.fde.keyassist.util.Constant;


// 滑动dialog
public class SwipeDialog extends BaseServiceDialog implements View.OnClickListener {


    private TextView tvHint1;
    private TextView dialogAddStartX;
    private TextView dialogAddStartY;
    private TextView dialogAddEndX;
    private TextView dialogAddEndY;
    private TextView dialogAddPointKeycode;
    private SwipeKeyMapping swipeKeyMapping;
    private boolean flag = true;
    private LinearLayout glInput;
    private SwipeKeyMappingController swipeKeyMappingController;
    private MappingEventType mappingEventType;
    private Button swipeBtCommit;
    private Button swipeBtCancel;
    private EditText dialogSwipDuration;

    public SwipeDialog(@NonNull Context context, Integer eventType) {
        super(context);
        tvHint1 = findViewById(R.id.tv_hint1);
        swipeBtCommit = findViewById(R.id.swipe_bt_commit);
        swipeBtCancel = findViewById(R.id.swipe_bt_cancel);
        dialogAddStartX = findViewById(R.id.dialog_add_start_x);
        dialogAddStartY = findViewById(R.id.dialog_add_start_y);
        dialogAddEndX = findViewById(R.id.dialog_add_end_x);
        dialogAddEndY = findViewById(R.id.dialog_add_end_y);
        swipeKeyMapping = new SwipeKeyMapping();
        glInput = findViewById(R.id.gl_input);
        dialogAddPointKeycode = findViewById(R.id.dialog_add_point_keycode);
        swipeKeyMappingController = new SwipeKeyMappingController(this.getContext());
        mappingEventType = new MappingEventType(this.getContext());
        swipeBtCommit.setOnClickListener(this);
        swipeBtCancel.setOnClickListener(this);
        dialogSwipDuration = findViewById(R.id.dialog_swip_duration);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_swipe;
    }

    @Override
    protected int getWidth() {
        return WindowManager.LayoutParams.MATCH_PARENT;
    }

    @Override
    protected int getHeight() {
        return WindowManager.LayoutParams.MATCH_PARENT;
    }

    @Override
    protected void onInited() {

    }

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            return super.onTouchEvent(event);
        }
        if (event.getAction() == MotionEvent.ACTION_UP && flag) {
            tvHint1.setText("请点击屏幕确定结束位置");
            swipeKeyMapping.setStartX((int)event.getRawX());
            swipeKeyMapping.setStartY((int)event.getRawY());
            flag = false;
        }else{
            tvHint1.setText("请点击按键进行映射");
            swipeKeyMapping.setEndX((int)event.getRawX());
            swipeKeyMapping.setEndY((int)event.getRawY());
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onKeyDown(int keyCode, @NonNull KeyEvent event) {
        swipeKeyMapping.setKeycode(keyCode);
        dialogAddStartX.setText("x1坐标的值为："+swipeKeyMapping.getStartX());
        dialogAddStartY.setText("y1坐标的值为："+swipeKeyMapping.getStartY());
        dialogAddEndX.setText("x2坐标的值为："+swipeKeyMapping.getEndX());
        dialogAddEndY.setText("y2坐标的值为："+swipeKeyMapping.getEndY());
        dialogAddPointKeycode.setText("键值为："+swipeKeyMapping.getKeycode());
        glInput.setVisibility(View.VISIBLE);
        swipeBtCommit.setVisibility(View.VISIBLE);
        swipeBtCancel.setVisibility(View.VISIBLE);
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // 保存按键映射
            case R.id.swipe_bt_commit:
                swipeKeyMapping.setSwipeDuration(Integer.valueOf(dialogSwipDuration.getText().toString()));
                swipeKeyMappingController.insert(swipeKeyMapping);
                mappingEventType.save(swipeKeyMapping.getKeycode(), Constant.SWIPE);
                dismiss();
                break;
            case R.id.swipe_bt_cancel:
                dismiss();
                break;
        }
    }
}

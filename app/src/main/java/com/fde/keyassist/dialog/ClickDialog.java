package com.fde.keyassist.dialog;

import android.content.Context;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.fde.keyassist.R;
import com.fde.keyassist.controller.DoubleClickKeyMappingController;
import com.fde.keyassist.controller.TapClickKeyMappingController;
import com.fde.keyassist.entity.TapClickKeyMapping;
import com.fde.keyassist.event.MappingEventType;
import com.fde.keyassist.util.Constant;
import com.fde.keyassist.view.DotView;


public class ClickDialog extends BaseServiceDialog implements View.OnClickListener {


    private TextView tvHint;
    private TextView dialogAddPointX;
    private TextView dialogAddPointY;
    private int x;
    private int y;
    private int keyCode ;
    private TextView dialogAddPointKeycode;
    private TapClickKeyMappingController tapClickKeyMappingController;
    private DoubleClickKeyMappingController doubleClickKeyMappingController;
    private MappingEventType mappingEventType;
    private Integer eventType;
    private boolean isTouchDisabled = false;
    private Button btCommit;
    private Button btCancel;
    private LinearLayout layoutContent;

    @Override
    public boolean dispatchTouchEvent(@NonNull MotionEvent ev) {
        if(isTouchDisabled){
            return true;
        }
        return super.dispatchTouchEvent(ev);
    }

    public ClickDialog(@NonNull Context context, Integer eventType) {
        super(context);
        this.eventType = eventType;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_add_point;
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
        tapClickKeyMappingController = new TapClickKeyMappingController(this.getContext());
        doubleClickKeyMappingController = new DoubleClickKeyMappingController(this.getContext());
        tvHint = findViewById(R.id.tv_hint);
        dialogAddPointX = findViewById(R.id.dialog_add_point_x);
        dialogAddPointY = findViewById(R.id.dialog_add_point_y);
        dialogAddPointKeycode = findViewById(R.id.dialog_add_point_keycode);
        mappingEventType = new MappingEventType(this.getContext());
        btCommit = findViewById(R.id.bt_commit);
        btCancel = findViewById(R.id.bt_cancel);
        btCommit.setOnClickListener(this);
        btCancel.setOnClickListener(this);
        layoutContent = findViewById(R.id.layout_content);
    }

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            x = (int) event.getRawX();
            y = (int) event.getRawY();
//            DotView dotView = new DotView(this.getContext(),x,y);
//            layoutContent.addView(dotView);
            dialogAddPointX.setText("x的坐标值为：" + x);
            dialogAddPointY.setText("y的坐标值为：" + y);
            dialogAddPointX.setVisibility(View.VISIBLE);
            dialogAddPointY.setVisibility(View.VISIBLE);
            tvHint.setText("请点击按键进行映射");
            isTouchDisabled = true;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onKeyDown(int keyCode, @NonNull KeyEvent event) {
        this.keyCode = keyCode;
        dialogAddPointKeycode.setText("按键的值为："+ this.keyCode);
        tvHint.setVisibility(View.GONE);
        isTouchDisabled = false;
        btCommit.setVisibility(View.VISIBLE);
        btCancel.setVisibility(View.VISIBLE);
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // 保存按键映射
            case R.id.bt_commit:
                if(eventType == Constant.TAP_CLICK_EVENT){
                    tapClickKeyMappingController.insert(new TapClickKeyMapping(x,y,keyCode));
                    mappingEventType.save(keyCode, Constant.TAP_CLICK_EVENT);
                }else if(eventType == Constant.DOUBLE_CLICK_EVENT){
                    doubleClickKeyMappingController.insert(new TapClickKeyMapping(x,y,keyCode));
                    mappingEventType.save(keyCode, Constant.DOUBLE_CLICK_EVENT);
                }

                dismiss();
                break;
            case R.id.bt_cancel:
                dismiss();
                break;
        }
    }
}

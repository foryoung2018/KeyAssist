package com.fde.keyassist.dialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;

import com.fde.keyassist.R;
import com.fde.keyassist.controller.DirectionKeyMappingController;
import com.fde.keyassist.controller.DoubleClickKeyMappingController;
import com.fde.keyassist.controller.TapClickKeyMappingController;
import com.fde.keyassist.entity.TapClickKeyMapping;
import com.fde.keyassist.event.MappingEventType;
import com.fde.keyassist.util.Constant;


public class HornorOfKingsDialog extends BaseServiceDialog  {

    private TapClickKeyMappingController tapClickKeyMappingController;
    private DirectionKeyMappingController directionKeyMappingController;
    private MappingEventType mappingEventType;
    private TextView dialog_hornor_kings_moderate; // 减速
    private TextView dialog_hornor_kings_strengthen; // 强化
    private TextView dialog_hornor_kings_damage; // 伤害
    private TextView dialog_hornor_kings_attack; // 攻击
    private TextView dialog_hornor_kings_add_moderate; // 增加减速
    private TextView dialog_hornor_kings_add_damage; // 增加伤害
    private TextView dialog_hornor_kings_add_strengthen;// 增加强化
    private TextView dialog_hornor_kings_Rage; // 狂暴
    private TextView dialog_hornor_kings_home; // 回城
    private TextView dialog_hornor_kings_restore; // 恢复
    private TextView dialog_hornor_kings_weapon1;
    private TextView dialog_hornor_kings_weapon2;
    private TextView dialog_hornor_kings_direction;

    public HornorOfKingsDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_hornor_kings;
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
        directionKeyMappingController = new DirectionKeyMappingController(this.getContext());
        mappingEventType = new MappingEventType(this.getContext());
        dialog_hornor_kings_moderate = findViewById(R.id.dialog_hornor_kings_moderate);
        dialog_hornor_kings_strengthen = findViewById(R.id.dialog_hornor_kings_strengthen);
        dialog_hornor_kings_damage = findViewById(R.id.dialog_hornor_kings_damage);
        dialog_hornor_kings_attack = findViewById(R.id.dialog_hornor_kings_attack);
        dialog_hornor_kings_add_moderate = findViewById(R.id.dialog_hornor_kings_add_moderate);
        dialog_hornor_kings_add_damage = findViewById(R.id.dialog_hornor_kings_add_damage);
        dialog_hornor_kings_add_strengthen = findViewById(R.id.dialog_hornor_kings_add_strengthen);
        dialog_hornor_kings_Rage = findViewById(R.id.dialog_hornor_kings_Rage);
        dialog_hornor_kings_home = findViewById(R.id.dialog_hornor_kings_home);
        dialog_hornor_kings_restore = findViewById(R.id.dialog_hornor_kings_restore);
        dialog_hornor_kings_weapon1 = findViewById(R.id.dialog_hornor_kings_weapon1);
        dialog_hornor_kings_weapon2 = findViewById(R.id.dialog_hornor_kings_weapon2);
        dialog_hornor_kings_direction = findViewById(R.id.dialog_hornor_kings_direction);
        initDraggable(dialog_hornor_kings_moderate);
        initDraggable(dialog_hornor_kings_strengthen);
        initDraggable(dialog_hornor_kings_damage);
        initDraggable(dialog_hornor_kings_attack);
        initDraggable(dialog_hornor_kings_add_moderate);
        initDraggable(dialog_hornor_kings_add_damage);
        initDraggable(dialog_hornor_kings_add_strengthen);
        initDraggable(dialog_hornor_kings_Rage);
        initDraggable(dialog_hornor_kings_home);
        initDraggable(dialog_hornor_kings_restore);
        initDraggable(dialog_hornor_kings_weapon1);
        initDraggable(dialog_hornor_kings_weapon2);
        initDraggable(dialog_hornor_kings_direction);
    }
    
    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {

        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onKeyDown(int keyCode, @NonNull KeyEvent event) {
        // 保存布局
        // 创建一个整型数组来存储坐标
        // 调用getLocationInWindow方法获取坐标
        if(keyCode == KeyEvent.KEYCODE_CTRL_LEFT){
            getLocationAndSave(dialog_hornor_kings_moderate,KeyEvent.KEYCODE_J);
            getLocationAndSave(dialog_hornor_kings_strengthen,KeyEvent.KEYCODE_K);
            getLocationAndSave(dialog_hornor_kings_damage,KeyEvent.KEYCODE_L);
            getLocationAndSave(dialog_hornor_kings_attack,KeyEvent.KEYCODE_SPACE);
            getLocationAndSave(dialog_hornor_kings_add_moderate,KeyEvent.KEYCODE_U);
            getLocationAndSave(dialog_hornor_kings_add_damage,KeyEvent.KEYCODE_O);
            getLocationAndSave(dialog_hornor_kings_add_strengthen,KeyEvent.KEYCODE_I);
            getLocationAndSave(dialog_hornor_kings_Rage,KeyEvent.KEYCODE_N);
            getLocationAndSave(dialog_hornor_kings_home,KeyEvent.KEYCODE_B);
            getLocationAndSave(dialog_hornor_kings_restore,KeyEvent.KEYCODE_M);
            getLocationAndSave(dialog_hornor_kings_weapon1,KeyEvent.KEYCODE_E);
            getLocationAndSave(dialog_hornor_kings_weapon2,KeyEvent.KEYCODE_R);
            saveDirection(dialog_hornor_kings_direction);
        }
        dismiss();
        return super.onKeyDown(keyCode, event);
    }

    void saveDirection(View view){
        int[] location = new int[2];
//        view.getLocationInWindow(location);
        // 获取组件左上角的坐标
        view.getLocationInWindow(location); // 或者使用 getLocationInParent(location) 取决于你的需求
        // 计算中心点坐标
        // 组件的宽度和高度
        int width = view.getWidth();
        int height = view.getHeight();
        // 中心点的x坐标是左上角x坐标加上宽度的一半
        location[0] = location[0] + width / 2;
        // 中心点的y坐标是左上角y坐标加上高度的一半
        location[1] = location[1] + height / 2;
        directionKeyMappingController.insert(new TapClickKeyMapping(location[0],location[1],KeyEvent.KEYCODE_A));
        directionKeyMappingController.insert(new TapClickKeyMapping(location[0],location[1],KeyEvent.KEYCODE_W));
        directionKeyMappingController.insert(new TapClickKeyMapping(location[0],location[1],KeyEvent.KEYCODE_S));
        directionKeyMappingController.insert(new TapClickKeyMapping(location[0],location[1],KeyEvent.KEYCODE_D));
        mappingEventType.save(KeyEvent.KEYCODE_A, Constant.DIRECTION_KEY);
        mappingEventType.save(KeyEvent.KEYCODE_W, Constant.DIRECTION_KEY);
        mappingEventType.save(KeyEvent.KEYCODE_S, Constant.DIRECTION_KEY);
        mappingEventType.save(KeyEvent.KEYCODE_D, Constant.DIRECTION_KEY);
    }

    int[] getLocationAndSave(View view,Integer keycode){
        int[] location = new int[2];
//        view.getLocationInWindow(location);
        // 获取组件左上角的坐标
        view.getLocationInWindow(location); // 或者使用 getLocationInParent(location) 取决于你的需求
        // 计算中心点坐标
        // 组件的宽度和高度
        int width = view.getWidth();
        int height = view.getHeight();
        // 中心点的x坐标是左上角x坐标加上宽度的一半
        location[0] = location[0] + width / 2;
        // 中心点的y坐标是左上角y坐标加上高度的一半
        location[1] = location[1] + height / 2;
        tapClickKeyMappingController.insert(new TapClickKeyMapping(location[0],location[1],keycode));
        mappingEventType.save(keycode, Constant.TAP_CLICK_EVENT);
        return location;
    }

//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            // 保存按键映射
//            case R.id.bt_commit:
//                dismiss();
//                break;
//            case R.id.bt_cancel:
//                dismiss();
//                break;
//        }
//    }


    private float dX = 0, dY = 0;
    public void initDraggable(final View view) {
        // 记录手指按下时的坐标与视图左上角的坐标差值

        // 为视图设置触摸监听器
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // 记录手指按下时的坐标
                        dX = event.getRawX() - v.getX();
                        dY = event.getRawY() - v.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        // 计算移动后的新位置
                        float newX = event.getRawX() - dX;
                        float newY = event.getRawY() - dY;

                        // 可以在这里添加边界检查，以避免视图移出屏幕

                        // 更新视图的位置
                        view.animate()
                                .x(newX)
                                .y(newY)
                                .setDuration(0) // 立即移动，如果需要动画效果可以设置非零值
                                .start();
                        break;
                    default:
                        return false;
                }
                // 表明我们消费了这个触摸事件
                return true;
            }
        });
    }

}

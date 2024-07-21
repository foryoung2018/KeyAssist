package com.fde.keyassist;



import android.annotation.SuppressLint;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.Nullable;

import com.fde.keyassist.adapter.PlaySpinnerAdapter;
import com.fde.keyassist.dialog.ApplyDialog;
import com.fde.keyassist.dialog.ModifyDialog;
import com.fde.keyassist.entity.KeyMappingEntity;
import com.fde.keyassist.event.EventUtils;
import com.fde.keyassist.util.Constant;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class FloatingService extends Service implements View.OnClickListener{

    private boolean isMainWindow = false; // 是否显示了主界面
    private View mainView;
    private WindowManager.LayoutParams mainParams;
    private WindowManager mainWindow;
    private boolean isChange = false; // 是否正在修改
    private ModifyDialog modifyDialog;
    private Spinner key_mapping_spinner;
    private Button key_mapping_save;
    private Button key_mapping_cancel;
    private ImageView key_mapping_tap_click;

    private ApplyDialog applyDialog;
    private Button key_mapping_apply;
    private Boolean isApply = false;

    private Boolean editAndCancal = true;

    private List<KeyMappingEntity> keyMappingEntities;

    private View floatView;

    private WindowManager.LayoutParams floatParams;

    private WindowManager floatWindow;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }



    @Override
    public void onCreate() {
        super.onCreate();
        showFloatView();
        applyDialog = new ApplyDialog("方案一",this);
    }




    @SuppressLint("ClickableViewAccessibility")
    private void showFloatView() {
        floatView = LayoutInflater.from(this).inflate(R.layout.background_window,null,false);
        floatParams = createLayoutParams();
        floatWindow = createWindow(50, 50, floatView, floatParams);
        dragView(floatView,floatWindow,floatParams,"showKeyMapping");
        onkey();

    }

    public void startListenerKey(){
        floatParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
//                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_SCALED
                | WindowManager.LayoutParams.FLAG_LAYOUT_INSET_DECOR
                | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
        floatWindow.updateViewLayout(floatView,floatParams);
    }
    public void endListenerKey(){
        floatParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_SCALED
                | WindowManager.LayoutParams.FLAG_LAYOUT_INSET_DECOR
                | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
        floatWindow.updateViewLayout(floatView,floatParams);
    }



    public void onkey(){
        floatView.setFocusableInTouchMode(true);
        // 按键事件
        floatView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                int[] pos = getPosition(i);
                if(pos[0] != -1 && pos[1]!=-1){
                    EventUtils.tapClick(pos[0],pos[1]);
                }
                return true;
            }
        });
    }


    // 显示按键映射
    @SuppressLint({"ClickableViewAccessibility", "MissingInflatedId"})
    public void showKeyMapping(){
        if(isMainWindow){
            return;
        }
        mainView = LayoutInflater.from(this).inflate(R.layout.key_mapping,null,false);
        mainParams = createLayoutParams();
        mainWindow = createWindow(300, 450, mainView, mainParams);
        dragView(mainView,mainWindow,mainParams,"");
//        Button key_mapping_click = mainView.findViewById(R.id.key_mapping_tap_click);
//        key_mapping_save = mainView.findViewById(R.id.key_mapping_save);
        key_mapping_spinner = mainView.findViewById(R.id.key_mapping_spinner);
        key_mapping_save = mainView.findViewById(R.id.key_mapping_save);
        key_mapping_save.setOnClickListener(this);
        key_mapping_cancel = mainView.findViewById(R.id.key_mapping_cancel);
        key_mapping_cancel.setOnClickListener(this);
        key_mapping_tap_click = mainView.findViewById(R.id.key_mapping_tap_click);
        key_mapping_tap_click.setOnClickListener(this);
        key_mapping_apply = mainView.findViewById(R.id.key_mapping_apply);
        key_mapping_apply.setOnClickListener(this);
        getAllPlan(key_mapping_spinner);
        isMainWindow = true;
        if(isApply){
            key_mapping_apply.setText("取消");
        }
    }





    // 创建窗口参数
    public WindowManager.LayoutParams createLayoutParams() {
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_SCALED
                | WindowManager.LayoutParams.FLAG_LAYOUT_INSET_DECOR
                | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;


        if (Build.VERSION.SDK_INT >= 26) {
            params.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        }
        params.format = PixelFormat.TRANSLUCENT;
        return params;
    }

    // 创建窗口
    public WindowManager createWindow(int width, int height, View view, WindowManager.LayoutParams params){
//        WindowManager windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        WindowManager windowManager = (WindowManager) this.getSystemService(WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        params.width = width;
        params.height = height;
        params.x = displayMetrics.widthPixels;
        params.y = displayMetrics.heightPixels;
        params.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        params.gravity = Gravity.TOP | Gravity.START;
        windowManager.addView(view,params);
        return windowManager;
    }

    public void dragView(View view,WindowManager windowManager,WindowManager.LayoutParams params,String methodName){
        view.setClickable(true);
        // 拖拽事件
        view.setOnTouchListener(new View.OnTouchListener() {
            private int x;
            private int y;
            //是否在移动
            private boolean isMoving;
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        x = (int) event.getRawX();
                        y = (int) event.getRawY();
                        isMoving = false;
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        int nowX = (int) event.getRawX();
                        int nowY = (int) event.getRawY();
                        int moveX = nowX - x;
                        int moveY = nowY - y;
                        if (Math.abs(moveX) > 0 || Math.abs(moveY) > 0) {
                            isMoving = true;
                            params.x += moveX;
                            params.y += moveY;
                            //更新View的位置
                            windowManager.updateViewLayout(view, params);
                            x = nowX;
                            y = nowY;
                            return true;
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        if (!isMoving) {
                            if(methodName.equals("showKeyMapping")){
                                showKeyMapping();
                            }
                            return true;
                        }
                        break;
                }
                return false;
            }
        });

    }

    public int[] getPosition(int keycode){
        int x = -1;
        int y=-1;
        if(keyMappingEntities != null && !keyMappingEntities.isEmpty()){
            Iterator iterator = keyMappingEntities.iterator();
            while(iterator.hasNext()){
                KeyMappingEntity keyMapping = (KeyMappingEntity) iterator.next();
                if(keyMapping.getKeycode() == keycode){
                    x = keyMapping.getX();
                    y = keyMapping.getY();
                }
            }
            return new int[]{x,y};
        }
        return new int[]{-1,-1};
    }


    public void startModify(String planName){
        if(!isChange){
            modifyDialog = new ModifyDialog(getApplication(),planName);
            modifyDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                }
            });
            modifyDialog.show();
            mainWindow.removeView(mainView);
            mainWindow.addView(mainView,mainParams);
            isChange = true;
        }
    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
         switch (view.getId()){
             case R.id.key_mapping_apply:
                 if(!isApply && editAndCancal){
                     startListenerKey();
                     if(applyDialog != null){
                         applyDialog.cancal();
                     }
                     applyDialog = new ApplyDialog("方案一",this);
                     keyMappingEntities = applyDialog.apply();
                     isApply = true;
                     isMainWindow = false;
                     key_mapping_apply.setText("取消");
                     mainWindow.removeView(mainView);
                 }else{
                     if(applyDialog!=null) {
                         endListenerKey();
                         applyDialog.cancal();
                         isApply = false;
                         key_mapping_apply.setText("应用");
                     }
                 }
                 break;
             case R.id.key_mapping_tap_click:
                 if(!editAndCancal){
                     startModify("方案一");
                     modifyDialog.setEventType(Constant.TAP_CLICK_EVENT); //单击事件
                 }

                 break;
             case R.id.key_mapping_save:
                 if(!editAndCancal) {
                     modifyDialog.save();
                     key_mapping_cancel.setText("编辑");
                     isChange = false;
                     editAndCancal = true;
                 }
                 break;
             case R.id.key_mapping_cancel:
                 // 编辑
                     if(editAndCancal){
                         applyDialog.cancal();
                         isApply = false;
                         editAndCancal = false;
                         key_mapping_apply.setText("应用");
                         endListenerKey();
                         key_mapping_cancel.setText("取消");
                         startModify("方案一");
                         modifyDialog.showView(); //单击事件
                     }else{
                         editAndCancal = true;
                         key_mapping_cancel.setText("编辑");
                         modifyDialog.cancel();
                         isChange = false;
                     }
                     break;
                 }

         }


    public void getAllPlan(Spinner spinner){
        List<String> items = new ArrayList<>();
        items.add("选项一");
        items.add("选项二");
        items.add("选项三");
        PlaySpinnerAdapter adapter = new PlaySpinnerAdapter(this, R.layout.key_mapping_spinner_item,R.id.key_mapping_spinner_item_text, items);
        spinner.setAdapter(adapter);
    }
}
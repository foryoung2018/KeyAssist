package com.fde.keyassist.event;

import android.content.Context;
import android.content.SharedPreferences;

// 映射事件类型
public class MappingEventType {

    private Context context;
    private SharedPreferences sharedPreferences;

    public MappingEventType(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences("mappingEventType",Context.MODE_PRIVATE);
    }

    /*
    * 1->TapClickEvent
    * 2->DoubleClickEvent
    * */
    // 保存事件
    public void save(int keycode,int eventType){
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putInt(String.valueOf(keycode),eventType);
        edit.apply();

    }
    // 得到事件类型
    public Integer getEventType(int keycode){
        return sharedPreferences.getInt(String.valueOf(keycode),-1);
    }
}

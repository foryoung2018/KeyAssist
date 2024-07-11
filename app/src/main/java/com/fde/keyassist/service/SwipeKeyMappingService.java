package com.fde.keyassist.service;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fde.keyassist.entity.SwipeKeyMapping;
import com.fde.keyassist.entity.TapClickKeyMapping;
import com.fde.keyassist.event.DoubleClickEvent;
import com.fde.keyassist.event.SwipeEvent;
import com.fde.keyassist.sql.MyDatabaseHelper;

import java.util.HashMap;
import java.util.Map;

public class SwipeKeyMappingService {

    private MyDatabaseHelper dbHelper;
    private SQLiteDatabase writableDatabase;
    public SwipeKeyMappingService(Context context){
        dbHelper = new MyDatabaseHelper(context,"keyAssist.db",null,1);
        writableDatabase = dbHelper.getWritableDatabase();
    }

    // 插入数据
    public void insert(SwipeKeyMapping swipeKeyMapping){
        ContentValues contentValues = new ContentValues();
        contentValues.put("start_x",swipeKeyMapping.getStartX());
        contentValues.put("start_y",swipeKeyMapping.getStartY());
        contentValues.put("end_x",swipeKeyMapping.getEndX());
        contentValues.put("end_y",swipeKeyMapping.getEndY());
        contentValues.put("keycode",swipeKeyMapping.getKeycode());
        contentValues.put("swipe_duration",swipeKeyMapping.getSwipeDuration());
        contentValues.put("swipe_source",swipeKeyMapping.getSwipeSource());
        writableDatabase.insert("SwipeKeyMapping",null,contentValues);
    }
    // 查询数据
    @SuppressLint("Range")
    public Map<Integer, SwipeEvent> query(){
        Map<Integer, SwipeEvent> map = new HashMap<>();
        Cursor cursor = writableDatabase.query("SwipeKeyMapping", null, null, null, null, null, null);
        if(cursor.moveToFirst()){
            do{
                float startX = cursor.getInt(cursor.getColumnIndex("start_x"));
                float startY = cursor.getInt(cursor.getColumnIndex("start_y"));
                float endX = cursor.getInt(cursor.getColumnIndex("end_x"));
                float endY = cursor.getInt(cursor.getColumnIndex("end_y"));
                int keycode = cursor.getInt(cursor.getColumnIndex("keycode"));
                int swipeDuration = cursor.getInt(cursor.getColumnIndex("swipe_duration"));
//                int swipeSource = cursor.getInt(cursor.getColumnIndex("swipe_source"));
//                int swipeDuration = 5000;
                int swipeSource = 0xd002;
                map.put(keycode,new SwipeEvent(startX,startY,endX,endY,swipeDuration,swipeSource));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return map;
    }
}

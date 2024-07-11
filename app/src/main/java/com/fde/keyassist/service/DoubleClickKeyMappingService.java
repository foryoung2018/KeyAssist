package com.fde.keyassist.service;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fde.keyassist.entity.TapClickKeyMapping;
import com.fde.keyassist.event.DoubleClickEvent;
import com.fde.keyassist.event.TapClickEvent;
import com.fde.keyassist.sql.MyDatabaseHelper;

import java.util.HashMap;
import java.util.Map;

public class DoubleClickKeyMappingService {
    private MyDatabaseHelper dbHelper;
    private SQLiteDatabase writableDatabase;
    public DoubleClickKeyMappingService(Context context){
        dbHelper = new MyDatabaseHelper(context,"keyAssist.db",null,1);
        writableDatabase = dbHelper.getWritableDatabase();
    }
    // 插入数据
    public void insert(TapClickKeyMapping tapClickKeyMapping){
        ContentValues contentValues = new ContentValues();
        contentValues.put("x",tapClickKeyMapping.getX());
        contentValues.put("y",tapClickKeyMapping.getY());
        contentValues.put("keycode",tapClickKeyMapping.getKeycode());
        writableDatabase.insert("DoubleClickKeyMapping",null,contentValues);
    }
    // 查询数据
    @SuppressLint("Range")
    public Map<Integer, DoubleClickEvent> query(){
        Map<Integer, DoubleClickEvent> map = new HashMap();
        Cursor cursor = writableDatabase.query("DoubleClickKeyMapping", null, null, null, null, null, null);
        if(cursor.moveToFirst()){
            do{
                int x = cursor.getInt(cursor.getColumnIndex("x"));
                int y = cursor.getInt(cursor.getColumnIndex("y"));
                int keycode = cursor.getInt(cursor.getColumnIndex("keycode"));
                map.put(keycode,new DoubleClickEvent(x,y));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return map;
    }

}

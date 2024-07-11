package com.fde.keyassist.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;
// 数据库文件
public class MyDatabaseHelper extends SQLiteOpenHelper {


//        private Integer x;  //坐标x
//    private Integer y;  //坐标y
//    private Integer keycode; // 按键码
//    private Integer eventType; // 事件类型
    private String TapClickKeyMapping = "create table TapClickKeyMapping ("+
            "id integer primary key autoincrement,"+
            "x integer,"+
            "y integer,"+
            "keycode integer)";

    private String DoubleClickKeyMapping = "create table DoubleClickKeyMapping ("+
            "id integer primary key autoincrement,"+
            "x integer,"+
            "y integer,"+
            "keycode integer)";

    /*
    *
    *     private Integer swipeDuration; // 持续时间
    private Integer swipeSource;
    * */

    private String SwipeKeyMapping = "create table SwipeKeyMapping ("+
            "id integer primary key autoincrement,"+
            "start_x float,"+
            "start_y float,"+
            "end_x float,"+
            "end_y float,"+
            "keycode integer,"+
            "swipe_duration integer,"+
            "swipe_source integer)";

    public MyDatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // 创建数据表
        sqLiteDatabase.execSQL(TapClickKeyMapping);
        sqLiteDatabase.execSQL(DoubleClickKeyMapping);
        sqLiteDatabase.execSQL(SwipeKeyMapping);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }
}

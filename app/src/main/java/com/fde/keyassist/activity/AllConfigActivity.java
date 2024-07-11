package com.fde.keyassist.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fde.keyassist.R;
import com.fde.keyassist.adapter.AllConfigAdapter;
import com.fde.keyassist.controller.DoubleClickKeyMappingController;
import com.fde.keyassist.controller.SwipeKeyMappingController;
import com.fde.keyassist.controller.TapClickKeyMappingController;
import com.fde.keyassist.entity.ConfigManage;
import com.fde.keyassist.event.DoubleClickEvent;
import com.fde.keyassist.event.SwipeEvent;
import com.fde.keyassist.event.TapClickEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class AllConfigActivity extends Activity {

    private RecyclerView allConfigRecyclerview;
    private List<ConfigManage> allKeyMapping;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_all_config);
        init();
    }

    // 查询所有按键映射
    public void listTapClickEvent(){
        TapClickKeyMappingController controller = new TapClickKeyMappingController(this);
        Map<Integer, TapClickEvent> tapQuery = controller.query();
        Iterator<Map.Entry<Integer, TapClickEvent>> iterator = tapQuery.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry<Integer, TapClickEvent> next = iterator.next();
            allKeyMapping.add(new ConfigManage(next.getKey(),"单击"));
        }
    }

    public void listDoubleClickEvent(){
        DoubleClickKeyMappingController controller = new DoubleClickKeyMappingController(this);
        Map<Integer, DoubleClickEvent> tapQuery = controller.query();
        Iterator<Map.Entry<Integer, DoubleClickEvent>> iterator = tapQuery.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry<Integer, DoubleClickEvent> next = iterator.next();
            allKeyMapping.add(new ConfigManage(next.getKey(),"双击事件"));
        }
    }

    public void listSwipeEvent(){
        SwipeKeyMappingController controller = new SwipeKeyMappingController(this);
        Map<Integer, SwipeEvent> tapQuery = controller.query();
        Iterator<Map.Entry<Integer, SwipeEvent>> iterator = tapQuery.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry<Integer, SwipeEvent> next = iterator.next();
            allKeyMapping.add(new ConfigManage(next.getKey(),"滑动事件"));
        }
    }

    public void init(){
        allKeyMapping = new ArrayList<>();
        listTapClickEvent(); // 查询所有单击映射
        listDoubleClickEvent();
        listSwipeEvent();
        allConfigRecyclerview = findViewById(R.id.all_config_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        allConfigRecyclerview.setLayoutManager(linearLayoutManager);
        AllConfigAdapter allConfigAdapter = new AllConfigAdapter(allKeyMapping);
        allConfigRecyclerview.setAdapter(allConfigAdapter);

    }

}

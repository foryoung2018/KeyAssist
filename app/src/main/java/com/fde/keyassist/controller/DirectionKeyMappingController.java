package com.fde.keyassist.controller;

import android.content.Context;
import android.view.KeyEvent;
import android.view.View;

import com.fde.keyassist.entity.TapClickKeyMapping;
import com.fde.keyassist.event.DirectionClickEvent;
import com.fde.keyassist.event.TapClickEvent;
import com.fde.keyassist.service.DirectionKeyMappingService;
import com.fde.keyassist.service.TapClickKeyMappingService;

import java.util.Map;

public class DirectionKeyMappingController {
    private DirectionKeyMappingService service;

    public DirectionKeyMappingController(Context context){
        service = new DirectionKeyMappingService(context);
    }

    public void executeEvent(int keycode, KeyEvent keyEvent, View view){
        service.query(keyEvent,view).get(keycode).execute();
    }

    public void insert(TapClickKeyMapping tapClickKeyMapping){
        service.insert(tapClickKeyMapping);
    }

    public Map<Integer, DirectionClickEvent> query(){
//        Map<Integer, DirectionClickEvent> query = service.query();
//        return query;
        return null;
    }

}

package com.fde.keyassist.controller;

import android.content.Context;

import com.fde.keyassist.entity.TapClickKeyMapping;
import com.fde.keyassist.event.DoubleClickEvent;
import com.fde.keyassist.service.DoubleClickKeyMappingService;
import com.fde.keyassist.service.TapClickKeyMappingService;

import java.util.Map;

public class DoubleClickKeyMappingController {

    private DoubleClickKeyMappingService service;

    public DoubleClickKeyMappingController(Context context){
        service = new DoubleClickKeyMappingService(context);
    }

    public void executeEvent(int keycode){
        service.query().get(keycode).execute();
    }

    public void insert(TapClickKeyMapping tapClickKeyMapping){
        service.insert(tapClickKeyMapping);
    }

    public Map<Integer, DoubleClickEvent> query(){
        return service.query();
    }


}

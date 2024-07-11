package com.fde.keyassist.controller;

import android.content.Context;

import com.fde.keyassist.entity.SwipeKeyMapping;
import com.fde.keyassist.entity.TapClickKeyMapping;
import com.fde.keyassist.event.SwipeEvent;
import com.fde.keyassist.service.SwipeKeyMappingService;

import java.util.Map;

public class SwipeKeyMappingController {
    private SwipeKeyMappingService service;

    public SwipeKeyMappingController(Context context){
        this.service = new SwipeKeyMappingService(context);
    }

    public void executeEvent(int keycode){
        service.query().get(keycode).execute();
    }

    public void insert(SwipeKeyMapping swipeKeyMapping){
        service.insert(swipeKeyMapping);
    }

    public Map<Integer, SwipeEvent> query(){
        return service.query();
    }

}

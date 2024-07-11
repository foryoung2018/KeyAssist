package com.fde.keyassist.controller;

import android.content.Context;

import com.fde.keyassist.entity.TapClickKeyMapping;
import com.fde.keyassist.event.MappingEvent;
import com.fde.keyassist.event.TapClickEvent;
import com.fde.keyassist.service.TapClickKeyMappingService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TapClickKeyMappingController {

    private TapClickKeyMappingService service;

    public TapClickKeyMappingController(Context context){
        service = new TapClickKeyMappingService(context);
    }

    public void executeEvent(int keycode){
        service.query().get(keycode).execute();
    }

    public void insert(TapClickKeyMapping tapClickKeyMapping){
        service.insert(tapClickKeyMapping);
    }

    public Map<Integer, TapClickEvent> query(){
        Map<Integer, TapClickEvent> query = service.query();
        return query;
    }


}

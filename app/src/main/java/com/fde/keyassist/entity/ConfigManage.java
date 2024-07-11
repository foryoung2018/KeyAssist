package com.fde.keyassist.entity;

public class ConfigManage {
    private Integer keycode;
    private String eventType;

    public Integer getKeycode() {
        return keycode;
    }

    public void setKeycode(Integer keycode) {
        this.keycode = keycode;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public ConfigManage() {
    }

    public ConfigManage(Integer keycode, String eventType) {
        this.keycode = keycode;
        this.eventType = eventType;
    }
}

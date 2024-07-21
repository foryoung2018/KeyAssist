package com.fde.keyassist.entity;


import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

public class KeyMappingEntity extends LitePalSupport {
    private Integer id;

    private Integer x; //坐标x

    private Integer y; // 坐标y

    private Integer keycode; // 按键值

    private String keyValue; // 按键名字

    private Boolean combination; //是否是组合键

    private Integer eventType;

    private String planName;

    @Column(defaultValue = "-1")
    private Integer combinationKeyCode; // 组合键的keycode

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public Integer getEventType() {
        return eventType;
    }

    public void setEventType(Integer eventType) {
        this.eventType = eventType;
    }



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public Integer getKeycode() {
        return keycode;
    }

    public void setKeycode(Integer keycode) {
        this.keycode = keycode;
    }

    public String getKeyValue() {
        return keyValue;
    }

    public void setKeyValue(String keyValue) {
        this.keyValue = keyValue;
    }

    public Boolean getCombination() {
        return combination;
    }

    public void setCombination(Boolean combination) {
        this.combination = combination;
    }

    public Integer getCombinationKeyCode() {
        return combinationKeyCode;
    }

    public void setCombinationKeyCode(Integer combinationKeyCode) {
        this.combinationKeyCode = combinationKeyCode;
    }

    public KeyMappingEntity(Integer x, Integer y, Integer keycode, String keyValue, Boolean combination, Integer eventType, String planName, Integer combinationKeyCode) {
        this.x = x;
        this.y = y;
        this.keycode = keycode;
        this.keyValue = keyValue;
        this.combination = combination;
        this.eventType = eventType;
        this.planName = planName;
        this.combinationKeyCode = combinationKeyCode;
    }

    public KeyMappingEntity() {
    }
}

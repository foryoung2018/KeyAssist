package com.fde.keyassist.entity;

// 单击事件映射
public class TapClickKeyMapping {
    private Integer id;
    private Integer x;  //坐标x
    private Integer y;  //坐标y
    private Integer keycode; // 按键码

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



    public TapClickKeyMapping(Integer x, Integer y, Integer keycode) {
        this.x = x;
        this.y = y;
        this.keycode = keycode;
    }
}

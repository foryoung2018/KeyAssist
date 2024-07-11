package com.fde.keyassist.entity;

public class SwipeKeyMapping {
    private Integer id;
    private Integer startX;
    private Integer startY;
    private Integer endX;
    private Integer endY;
    private Integer keycode;
    private Integer swipeDuration; // 持续时间
    private Integer swipeSource;

    public Integer getSwipeDuration() {
        return swipeDuration;
    }

    public void setSwipeDuration(Integer swipeDuration) {
        this.swipeDuration = swipeDuration;
    }

    public Integer getSwipeSource() {
        return swipeSource;
    }

    public void setSwipeSource(Integer swipeSource) {
        this.swipeSource = swipeSource;
    }

    public Integer getStartX() {
        return startX;
    }

    public void setStartX(Integer startX) {
        this.startX = startX;
    }

    public Integer getStartY() {
        return startY;
    }

    public void setStartY(Integer startY) {
        this.startY = startY;
    }

    public Integer getEndX() {
        return endX;
    }

    public void setEndX(Integer endX) {
        this.endX = endX;
    }

    public Integer getEndY() {
        return endY;
    }

    public void setEndY(Integer endY) {
        this.endY = endY;
    }

    public Integer getKeycode() {
        return keycode;
    }

    public void setKeycode(Integer keycode) {
        this.keycode = keycode;
    }

    public SwipeKeyMapping(Integer startX, Integer startY, Integer endX, Integer endY, Integer keycode, Integer swipeDuration, Integer swipeSource) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.keycode = keycode;
        this.swipeDuration = swipeDuration;
        this.swipeSource = swipeSource;
    }

    public SwipeKeyMapping() {
    }
}

package com.fde.keyassist.entity;

public class KeyMapping {
    private Integer imageId;
    private String title;
    private String context;
    private Integer eventType;

    public Integer getImageId() {
        return imageId;
    }

    public void setImageId(Integer imageId) {
        this.imageId = imageId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public Integer getEventType() {
        return eventType;
    }

    public void setEventType(Integer eventType) {
        this.eventType = eventType;
    }

    public KeyMapping(Integer imageId, String title, String context) {
        this.imageId = imageId;
        this.title = title;
        this.context = context;
    }

    public KeyMapping() {
    }

    public KeyMapping(Integer imageId, String title, String context, Integer eventType) {
        this.imageId = imageId;
        this.title = title;
        this.context = context;
        this.eventType = eventType;
    }
}

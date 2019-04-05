package com.example.stackfarm.myapplication.bean;

public class PluginBean {
    String name;
    int imageId;

    public PluginBean(String name, int imageId) {
        this.name = name;
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public int getImageId() {
        return imageId;
    }
}

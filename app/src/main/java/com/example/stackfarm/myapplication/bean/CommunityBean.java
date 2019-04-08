package com.example.stackfarm.myapplication.bean;

public class CommunityBean {
    int imageID;
    String name;
    String time;
    String loc;
    String content;

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public CommunityBean(int imageID, String name, String time, String loc, String content) {
        this.imageID = imageID;
        this.name = name;
        this.time = time;
        this.loc = loc;
        this.content = content;
    }
}

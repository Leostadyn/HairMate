package com.example.stackfarm.myapplication.bean;

public class BarberBean {
    String name;
    int imageID;
    int orderNums;
    int commentNums;
    String money1;
    String money2;
    String money3;
    String tag;

    public BarberBean(String name, int imageID, String money1, String money2, String money3) {
        this.name = name;
        this.imageID = imageID;
        this.money1 = money1;
        this.money2 = money2;
        this.money3 = money3;
        this.tag="这个人很懒什么都没有留下";
        this.orderNums=99;
        this.commentNums=99;
    }

    public BarberBean(String name, int imageID, int orderNums, int commentNums, String money1, String money2, String money3, String tag) {
        this.name = name;
        this.imageID = imageID;
        this.orderNums = orderNums;
        this.commentNums = commentNums;
        this.money1 = money1;
        this.money2 = money2;
        this.money3 = money3;
        this.tag = tag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

    public int getOrderNums() {
        return orderNums;
    }

    public void setOrderNums(int orderNums) {
        this.orderNums = orderNums;
    }

    public int getCommentNums() {
        return commentNums;
    }

    public void setCommentNums(int commentNums) {
        this.commentNums = commentNums;
    }

    public String getMoney1() {
        return money1;
    }

    public void setMoney1(String money1) {
        this.money1 = money1;
    }

    public String getMoney2() {
        return money2;
    }

    public void setMoney2(String money2) {
        this.money2 = money2;
    }

    public String getMoney3() {
        return money3;
    }

    public void setMoney3(String money3) {
        this.money3 = money3;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}

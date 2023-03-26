package com.example.bar.chat;

import cn.bmob.v3.BmobObject;

public class ChatRoom extends BmobObject {
    private String id;
    private String name;
    private String introduction;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }




}

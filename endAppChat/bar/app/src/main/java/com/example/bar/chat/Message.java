package com.example.bar.chat;

import cn.bmob.v3.BmobObject;

public class Message extends BmobObject {
    private String user_id;
    private String chatroom_id;
    private String message_content;
    private String time;
    public Message(String userId, String messageContent, String time1) {
        user_id = userId;
        message_content = messageContent;
        time = time1;
    }
    public Message() {

    }
    public String getChatroom_id() {
        return chatroom_id;
    }

    public void setChatroom_id(String chatroom_id) {
        this.chatroom_id = chatroom_id;
    }


    public String getMessage_content() {
        return message_content;
    }

    public void setMessage_content(String message_content) {
        this.message_content = message_content;
    }


    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }



    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }



}

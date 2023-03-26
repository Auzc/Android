package com.example.bar.database;

import cn.bmob.v3.BmobObject;

public class Post extends BmobObject {
    private String id; // 文章ID，自动生成
    private String title;
    private String content;
    private String author;
    private String time;

    public Post() {}

    public Post(String title, String content, String author, String time) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
package com.ltdd.cringempone.ui.person;

public class User {
    public User(int resouceId, String name) {
        this.resouceId = resouceId;
        this.name = name;
    }

    public int getResouceId() {
        return resouceId;
    }

    public String getName() {
        return name;
    }

    public void setResouceId(int resouceId) {
        this.resouceId = resouceId;
    }

    public void setName(String name) {
        this.name = name;
    }

    private int resouceId;
    private String name;
}

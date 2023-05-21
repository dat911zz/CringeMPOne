package com.ltdd.cringempone.ui.person;

public class PersonItem {
    private int resouceId;
    private String title;

    public void setResouceId(int resouceId) {
        this.resouceId = resouceId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getResouceId() {
        return resouceId;
    }

    public String getTitle() {
        return title;
    }

    public PersonItem(int resouceId, String title) {
        this.resouceId = resouceId;
        this.title = title;
    }
}

package com.ltdd.cringempone.ui.musicplayer.model;

public class ChildItem {
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String title;
    private String img;

    public String getImg() {
        return img;
    }

    public void setImg(String childItemImg) {
        this.img = childItemImg;
    }

    public ChildItem(String id, String title, String img) {
        this.id = id;
        this.title = title;
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String childItemTitle) {
        this.title = childItemTitle;
    }
}

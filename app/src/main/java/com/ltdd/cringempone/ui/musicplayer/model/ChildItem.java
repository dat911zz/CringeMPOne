package com.ltdd.cringempone.ui.musicplayer.model;

import android.graphics.drawable.Drawable;

import com.android.volley.toolbox.NetworkImageView;

public class ChildItem {
    private String childItemTitle;
    private String childItemImg;

    public String getChildItemImg() {
        return childItemImg;
    }

    public void setChildItemImg(String childItemImg) {
        this.childItemImg = childItemImg;
    }

    public ChildItem(String childItemTitle, String childItemImg) {
        this.childItemTitle = childItemTitle;
        this.childItemImg = childItemImg;
    }

    public String getChildItemTitle() {
        return childItemTitle;
    }

    public void setChildItemTitle(String childItemTitle) {
        this.childItemTitle = childItemTitle;
    }
}

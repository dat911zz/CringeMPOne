package com.ltdd.cringempone.ui.musicplayer;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.exoplayer2.ExoPlayer;
import com.ltdd.cringempone.R;
import com.ltdd.cringempone.service.MediaAction;
import com.ltdd.cringempone.service.MediaControlReceiver;

public class PlayerPagerAdapter extends FragmentPagerAdapter {
    private final static int PLAYER_INFO = 0;
    private final static int PLAYER_MAIN = 1;
    public PlayerPagerAdapter(FragmentManager fragmentManager){
        super(fragmentManager);
    }
    @Override
    public int getCount() {
        return 2;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case PLAYER_INFO:
                return InfoPlayerFragment.newInstance("123", "123");
            case PLAYER_MAIN:
                MediaControlReceiver mediaControl = MediaControlReceiver.getInstance();
                return MainPlayerFragment.newInstance(
                        mediaControl.getCurrentSong().title != null ?
                                mediaControl.getCurrentSong().title : "---",
                        mediaControl.getCurrentSong().artists.get(0).name != null ?
                                mediaControl.getCurrentSong().artists.get(0).name : "---"
                );
        }
        return null;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case PLAYER_INFO:
                return  "Thông tin";
            case PLAYER_MAIN:
                return "Phát nhạc";
        }
        return "";
    }

}

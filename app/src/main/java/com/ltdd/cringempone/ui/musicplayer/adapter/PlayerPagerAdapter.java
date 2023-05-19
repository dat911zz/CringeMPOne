package com.ltdd.cringempone.ui.musicplayer.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.ltdd.cringempone.service.MediaControlReceiver;
import com.ltdd.cringempone.ui.musicplayer.fragment.InfoPlayerFragment;
import com.ltdd.cringempone.ui.musicplayer.fragment.LyricPlayerFragment;
import com.ltdd.cringempone.ui.musicplayer.fragment.MainPlayerFragment;

public class PlayerPagerAdapter extends FragmentPagerAdapter {
    private final static int PLAYER_INFO = 0;
    private final static int PLAYER_MAIN = 1;
    private final static int PLAYER_LYRIC = 2;
    public PlayerPagerAdapter(FragmentManager fragmentManager){
        super(fragmentManager);
    }
    @Override
    public int getCount() {
        return 3;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case PLAYER_INFO:
                return InfoPlayerFragment.getInstance();
            case PLAYER_MAIN:
                return MainPlayerFragment.newInstance();
            case PLAYER_LYRIC:
                return LyricPlayerFragment.newInstance();
        }
        return null;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case PLAYER_INFO:
                return "Thông tin";
            case PLAYER_MAIN:
                return "Phát nhạc";
            case PLAYER_LYRIC:
                return "Lời bài hát";
        }
        return "";
    }

}

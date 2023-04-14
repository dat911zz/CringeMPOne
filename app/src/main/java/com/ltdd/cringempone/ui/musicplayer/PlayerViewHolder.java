package com.ltdd.cringempone.ui.musicplayer;

import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;

public class PlayerViewHolder {
    private SeekBar seekBar;
    private AppCompatButton play, skipNext, skipPrev, shuffle, loop;
    private TextView start;
    private TextView end;

    public PlayerViewHolder(SeekBar seekBar, AppCompatButton play, AppCompatButton skipNext, AppCompatButton skipPrev, AppCompatButton shuffle, AppCompatButton loop, TextView start, TextView end) {
        this.seekBar = seekBar;
        this.play = play;
        this.skipNext = skipNext;
        this.skipPrev = skipPrev;
        this.shuffle = shuffle;
        this.loop = loop;
        this.start = start;
        this.end = end;
    }


    public SeekBar getSeekBar() {
        return seekBar;
    }

    public void setSeekBar(SeekBar seekBar) {
        this.seekBar = seekBar;
    }

    public AppCompatButton getPlay() {
        return play;
    }

    public void setPlay(AppCompatButton play) {
        this.play = play;
    }

    public AppCompatButton getSkipNext() {
        return skipNext;
    }

    public void setSkipNext(AppCompatButton skipNext) {
        this.skipNext = skipNext;
    }

    public AppCompatButton getSkipPrev() {
        return skipPrev;
    }

    public void setSkipPrev(AppCompatButton skipPrev) {
        this.skipPrev = skipPrev;
    }

    public AppCompatButton getShuffle() {
        return shuffle;
    }

    public void setShuffle(AppCompatButton shuffle) {
        this.shuffle = shuffle;
    }

    public AppCompatButton getLoop() {
        return loop;
    }

    public void setLoop(AppCompatButton loop) {
        this.loop = loop;
    }

    public TextView getStart() {
        return start;
    }

    public void setStart(TextView start) {
        this.start = start;
    }

    public TextView getEnd() {
        return end;
    }

    public void setEnd(TextView end) {
        this.end = end;
    }
}

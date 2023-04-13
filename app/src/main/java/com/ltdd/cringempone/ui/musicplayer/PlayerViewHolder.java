package com.ltdd.cringempone.ui.musicplayer;

import android.widget.SeekBar;
import androidx.appcompat.widget.AppCompatButton;

public class PlayerViewHolder {
    private SeekBar seekBar;
    private AppCompatButton play;
    private AppCompatButton skipNext;
    private AppCompatButton skipPrev;
    private AppCompatButton shuffle;
    private AppCompatButton loop;

    public PlayerViewHolder(SeekBar seekBar, AppCompatButton play, AppCompatButton skipNext, AppCompatButton skipPrev, AppCompatButton shuffle, AppCompatButton loop) {
        this.setSeekBar(seekBar);
        this.setPlay(play);
        this.setSkipNext(skipNext);
        this.setSkipPrev(skipPrev);
        this.setShuffle(shuffle);
        this.setLoop(loop);
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
}

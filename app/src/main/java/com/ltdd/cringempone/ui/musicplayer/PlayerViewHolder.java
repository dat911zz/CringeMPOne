package com.ltdd.cringempone.ui.musicplayer;

import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class PlayerViewHolder {
    private SeekBar seekBar;
    private Button play, skipNext, skipPrev, shuffle, loop;
    private TextView start;
    private TextView end;

    public PlayerViewHolder(SeekBar seekBar, Button play, Button skipNext, Button skipPrev, Button shuffle, Button loop, TextView start, TextView end) {
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

    public Button getPlay() {
        return play;
    }

    public void setPlay(Button play) {
        this.play = play;
    }

    public Button getSkipNext() {
        return skipNext;
    }

    public void setSkipNext(Button skipNext) {
        this.skipNext = skipNext;
    }

    public Button getSkipPrev() {
        return skipPrev;
    }

    public void setSkipPrev(Button skipPrev) {
        this.skipPrev = skipPrev;
    }

    public Button getShuffle() {
        return shuffle;
    }

    public void setShuffle(Button shuffle) {
        this.shuffle = shuffle;
    }

    public Button getLoop() {
        return loop;
    }

    public void setLoop(Button loop) {
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

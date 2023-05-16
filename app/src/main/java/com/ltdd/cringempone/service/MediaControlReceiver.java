package com.ltdd.cringempone.service;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.media.Image;
import android.media.browse.MediaBrowser;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.SeekBar;

import androidx.appcompat.widget.AppCompatButton;

import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.ltdd.cringempone.R;
import com.ltdd.cringempone.api.BaseAPIService;
import com.ltdd.cringempone.data.dto.ItemDTO;
import com.ltdd.cringempone.data.dto.SongInfoDTO;
import com.ltdd.cringempone.ui.musicplayer.PlayerViewHolder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MediaControlReceiver extends BroadcastReceiver {
    private static MediaControlReceiver instance; // Singleton instance
    private ExoPlayer exoPlayer;
    private ArrayList<ItemDTO> playList = new ArrayList<>();
    private int currentPos;
    public boolean isRegister;
    private Boolean isShuffle = false
            , isLoop = false;

    private MediaControlReceiver(){ }
    public static MediaControlReceiver getInstance(){
        if (instance == null){
            instance = new MediaControlReceiver();
        }
        return instance;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null && intent.getAction() != null){
            String act = intent.getAction();
            // Get the current playback state of ExoPlayer
            int playbackState = exoPlayer.getPlaybackState();
            boolean isPause = playbackState == Player.STATE_READY && !exoPlayer.isPlaying();
            switch (act){
                case MediaAction.ACTION_PLAY:
                    exoPlayer.setPlayWhenReady(true);
                    if (currentPos >= 0 && !isPause){
                        MediaItem mediaItem = MediaItem.fromUri(BaseAPIService
                                        .getInstance()
                                        .getStreaming(playList.get(currentPos).encodeId)
                                ._128
                        );
                        exoPlayer.setMediaItem(mediaItem);
                        exoPlayer.prepare();
                    }
                    exoPlayer.play();
                    break;
                case MediaAction.ACTION_PAUSE:
                    exoPlayer.setPlayWhenReady(false);
                    exoPlayer.pause();
                    break;
                case MediaAction.ACTION_STOP:
                    exoPlayer.setPlayWhenReady(false);
                    exoPlayer.stop();
                    exoPlayer.release();
                    setExoPlayer(null);
                    break;
            }
        }
    }
    public void registerReceiver(Context context) {
        isRegister = true;
        //Setup media control receiver
        exoPlayer = new ExoPlayer.Builder(context).build();
        setExoPlayer(exoPlayer);

        //Register the broadcast receiver
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MediaAction.ACTION_PLAY);
        intentFilter.addAction(MediaAction.ACTION_PAUSE);
        intentFilter.addAction(MediaAction.ACTION_STOP);
        context.registerReceiver(MediaControlReceiver.getInstance(), intentFilter);
    }
    public void unregisterReceiver(Context context){
        //Unregister broadcast and release exoplayer instance
        this.unregisterReceiver(context);
        exoPlayer.release();
        exoPlayer = null;
    }
    public void addPlaylist(ArrayList<ItemDTO> items){
        if (exoPlayer != null){
            exoPlayer.clearMediaItems();
            playList.clear();
        }
        playList.addAll(items);
    }
    public void addControl(Context context, PlayerViewHolder viewHolder){
        viewHolder.getPlay().setOnClickListener(v -> {
            if(!exoPlayer.isPlaying()) {
                viewHolder.getPlay().setBackgroundResource(R.drawable.baseline_pause_24);
                context.sendBroadcast(new Intent(MediaAction.ACTION_PLAY));
            }
            else{
                viewHolder.getPlay().setBackgroundResource(R.drawable.baseline_play_arrow_24);
                context.sendBroadcast(new Intent(MediaAction.ACTION_PAUSE));
            }
        });

        viewHolder.getSkipNext().setOnClickListener(v -> {
            if (currentPos + 1 <= playList.size() - 1){
                currentPos++;
            }
            else{
                exoPlayer.seekTo(0);
            }
            context.sendBroadcast(new Intent(MediaAction.ACTION_PLAY));
        });
        viewHolder.getSkipPrev().setOnClickListener(v -> {
            if (currentPos - 1 >= 0){
                currentPos--;
            }
            else{
                exoPlayer.seekTo(0);
            }
            context.sendBroadcast(new Intent(MediaAction.ACTION_PLAY));
        });
        viewHolder.getLoop().setOnClickListener(v -> {

        });
        viewHolder.getShuffle().setOnClickListener(v -> {
            if (!isShuffle){
                exoPlayer.setShuffleModeEnabled(true);
                isShuffle = true;
                viewHolder.getShuffle().setTextColor(v.getResources().getColor(R.color.btn_off));
            }
            else{
                exoPlayer.setShuffleModeEnabled(false);
                isShuffle = false;
                viewHolder.getShuffle().setTextColor(v.getResources().getColor(R.color.btn_on));
            }
        });
        //Jump to current pos of current playing song
        int duration = ((int) exoPlayer.getDuration());
        viewHolder.getSeekBar().setMax(duration);
        viewHolder.getEnd().setText(createTimeText(duration));

        exoPlayer.addListener(new Player.Listener() {
            @Override
            public void onPlaybackStateChanged(int playbackState) {
                // Handle playback state change event
                // Example: Update UI based on playback state
                if (playbackState == Player.STATE_BUFFERING) {
                    // Show buffering indicator

                } else if (playbackState == Player.STATE_READY) {
                    // Playback is ready, update UI accordingly
                    if (exoPlayer.getPlayWhenReady()) {
                        // Player is in play state
                        // Example: Handle play event with a Handler
                        int duration = ((int) exoPlayer.getDuration());
                        viewHolder.getSeekBar().setMax(duration);
                        viewHolder.getEnd().setText(createTimeText(duration));
                        viewHolder.getPlay().setBackgroundResource(R.drawable.baseline_pause_24);
                    } else {
                        // Player is in pause state
                        // Example: Handle pause event with a Handler
                        viewHolder.getPlay().setBackgroundResource(R.drawable.baseline_play_arrow_24);
                    }
                } else if (playbackState == Player.STATE_ENDED) {
                    // Playback ended, update UI accordingly
                }
            }
        });
        viewHolder.getSeekBar().setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser){
                    exoPlayer.seekTo(progress);
                    viewHolder.getSeekBar().setProgress(progress);
                    Log.i("On progress", "onProgressChanged: " + seekBar.getMax());
                }
            }
            //#region Các hàm không xài tới
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
            //#endregion
        });
        //Lấy tọa độ từ thời gian nhạc đang phát
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (exoPlayer != null) {
                    int currentPosition = (int) exoPlayer.getCurrentPosition();
                    viewHolder.getSeekBar().setProgress(currentPosition);
                    viewHolder.getStart().setText(createTimeText(currentPosition));
                }
                handler.postDelayed(this, 10);
            }
        }, 0);

    }
    private String createTimeText(int time){
        String timeText;
        int min = time / 1000 / 60;
        int sec = time / 1000 % 60;
        timeText = min + ":";
        if (sec < 10){
            timeText += "0";
        }
        timeText += sec;
        return timeText;
    }
    public void executeDisc(ImageView disc) {
        Picasso.get().load(getCurrentSong().thumbnailM).into(disc);
    }
    public void setExoPlayer(ExoPlayer exoPlayer){
        this.exoPlayer = exoPlayer;
    }
    public ExoPlayer getExoPlayer() { return exoPlayer; }
    public ItemDTO getCurrentSong() {
        if (exoPlayer.getCurrentMediaItem() == null){
            if (playList.size() != 0){
                return playList.get(getCurrentPos());
            }
            return null;
        }
        return playList.get(currentPos);
    }
    public int getCurrentPos() {
        return currentPos;
    }

    public void setCurrentPos(int currentPos) {
        this.currentPos = currentPos;
    }
}
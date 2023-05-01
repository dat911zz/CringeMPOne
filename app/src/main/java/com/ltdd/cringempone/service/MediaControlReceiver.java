package com.ltdd.cringempone.service;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.media.Image;
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
import com.ltdd.cringempone.data.dto.SongInfoDTO;
import com.ltdd.cringempone.ui.musicplayer.PlayerViewHolder;
import com.ltdd.cringempone.utils.Helper;

public class MediaControlReceiver extends BroadcastReceiver {
    private static MediaControlReceiver instance; // Singleton instance
    private ExoPlayer exoPlayer;
    private SongInfoDTO currentSong;
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
                    String mediaUrl = intent.getStringExtra("url");
                    Log.i("APP", "onStartCommand: ");
                    if (mediaUrl != null && !isPause){
                        MediaItem mediaItem = MediaItem.fromUri(mediaUrl);
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
        ((Activity)context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
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
        });

    }
    public void unregisterReceiver(Context context){
        //Unregister broadcast and release exoplayer instance
        this.unregisterReceiver(context);
        exoPlayer.release();
        exoPlayer = null;
    }
    public void addControl(Context context, PlayerViewHolder viewHolder){
        viewHolder.getPlay().setOnClickListener(v -> {
            if(!MediaControlReceiver.getInstance().getExoPlayer().isPlaying()) {
                Log.i("APP", "play song");
                viewHolder.getPlay().setBackgroundResource(R.drawable.baseline_pause_24);
                //Send play command to the MediaControlService

                String songUrl = getCurrentSong().streaming._128;
                Intent playIntent = new Intent(MediaAction.ACTION_PLAY);
                playIntent.putExtra("url", songUrl);
                context.sendBroadcast(playIntent);
            }
            else{
                viewHolder.getPlay().setBackgroundResource(R.drawable.baseline_play_arrow_24);
                Intent pauseIntent = new Intent(MediaAction.ACTION_PAUSE);
                context.sendBroadcast(pauseIntent);
            }
        });
        viewHolder.getSkipNext().setOnClickListener(v -> {
            if (exoPlayer.hasNextMediaItem()){
                exoPlayer.seekToNextMediaItem();
            }
            else{
                exoPlayer.seekTo(0);
            }
        });
        viewHolder.getSkipPrev().setOnClickListener(v -> {
            if (exoPlayer.hasPreviousMediaItem()){
                exoPlayer.seekToPreviousMediaItem();
            }
            else{
                exoPlayer.seekTo(0);
            }
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
                    } else {
                        // Player is in pause state
                        // Example: Handle pause event with a Handler
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

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
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
    public void executeDisc(Context context, ImageView disc) {
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.rotate);
        Handler handler = new Handler();
        disc.setImageDrawable(Helper.ImageUtil.LoadImageFromWebOperations(getCurrentSong().thumbnailM));
    }
    public void setExoPlayer(ExoPlayer exoPlayer){
        this.exoPlayer = exoPlayer;
    }
    public ExoPlayer getExoPlayer() { return exoPlayer; }
    public SongInfoDTO getCurrentSong() { return currentSong; }
    public void setCurrentSong(SongInfoDTO currentSong) { this.currentSong = currentSong; }
}
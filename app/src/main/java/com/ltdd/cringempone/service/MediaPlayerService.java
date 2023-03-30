package com.ltdd.cringempone.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.Player;

public class MediaPlayerService extends Service implements Player.Listener {
    ExoPlayer exoPlayer = new ExoPlayer.Builder(this).build();
    public MediaPlayerService() {
    }
    public static MediaPlayerService getInstance(){
        return new MediaPlayerService();
    }
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }

    //#region ExoPlayer EventListener

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        Player.Listener.super.onPlayerStateChanged(playWhenReady, playbackState);
    }

    @Override
    public void onPositionDiscontinuity(int reason) {
        Player.Listener.super.onPositionDiscontinuity(reason);
    }

    @Override
    public void onIsPlayingChanged(boolean isPlaying) {
        Player.Listener.super.onIsPlayingChanged(isPlaying);
    }

    //#endregion
}
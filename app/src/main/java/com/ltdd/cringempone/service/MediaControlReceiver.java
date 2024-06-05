package com.ltdd.cringempone.service;

import static android.content.Context.NOTIFICATION_SERVICE;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadata;
import android.os.Build;
import android.os.Handler;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.graphics.drawable.IconCompat;
import androidx.media.session.MediaButtonReceiver;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.ltdd.cringempone.MainActivity;
import com.ltdd.cringempone.R;
import com.ltdd.cringempone.api.BaseAPIService;
import com.ltdd.cringempone.data.dto.ItemDTO;
import com.ltdd.cringempone.data.dto.SongDTO;
import com.ltdd.cringempone.data.dto.Streaming;
import com.ltdd.cringempone.ui.musicplayer.PlayerActivity;
import com.ltdd.cringempone.ui.musicplayer.PlayerViewHolder;
import com.ltdd.cringempone.ui.musicplayer.ViewPagerPlayerController;
import com.ltdd.cringempone.utils.CoreHelper;
import com.ltdd.cringempone.utils.CustomsDialog;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class MediaControlReceiver extends BroadcastReceiver {
    private PlayerViewHolder viewHolder;
    private final static String TAG = "BroadcastReceiver";
    private Context context;
    private static MediaControlReceiver instance; // Singleton instance
    private ExoPlayer exoPlayer;
    private ArrayList<ItemDTO> playList = new ArrayList<>();
    private int currentPos;
    private boolean isSkip = false;
    public void setSkip(boolean skip) {
        isSkip = skip;
    }
    public boolean isRegister;
    private Boolean isShuffle = false
            , isLoop = false;
    private static final String CHANNEL_ID = "media_playback_channel";

    private PlaybackStateCompat.Builder mStateBuilder;
    private NotificationManager mNotificationManager;
    private static MediaSessionCompat mMediaSession;
    private MediaSessionCompat.Token token;
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
            try{
                switch (act){
                    case MediaAction.ACTION_PLAY:
                        new Handler().postDelayed(() -> {
                            Log.i(TAG, "onReceive: play");
                            //Load data into viewpager
                            if (getCurrentSong() != null){
                                ViewPagerPlayerController.getInstance().loadDataIntoFragments(getCurrentSong().encodeId);
                            }
                            //Play song
                            exoPlayer.setPlayWhenReady(true);
                            String songLink = LocalStorageService.getInstance().getString("m_link" + playList.get(currentPos).encodeId);
                            String playingSongId = exoPlayer.getCurrentMediaItem() == null ? "" : exoPlayer.getCurrentMediaItem().mediaId;
                            if (!isPause && !playingSongId.equals(getCurrentSong().encodeId) || isSkip){
                                try {
                                    if (isSkip){
                                        isSkip = false;
                                    }
                                    if (songLink.contains("error") || songLink.equals("") || BaseAPIService.getInstance().isDown(songLink)) {
                                        Streaming streamingDTO = BaseAPIService.getInstance().getStreaming(playList.get(currentPos).encodeId);
                                        if (streamingDTO.err.equals("-1150")){
                                            LocalStorageService.getInstance().putString("err:-1150", playList.get(currentPos).encodeId);
                                            exoPlayer.setPlayWhenReady(false);
                                            exoPlayer.pause();
                                            return;
                                        }
                                        songLink = streamingDTO.data._128;
                                        LocalStorageService.getInstance().putString("m_link" + playList.get(currentPos).encodeId, songLink);
                                    }
                                    LocalStorageService.getInstance().putString("currentPlaying", String.valueOf(currentPos));
                                    MediaItem mediaItem = new MediaItem.Builder()
                                            .setUri(songLink)
                                            .setMediaId(getCurrentSong().encodeId)
                                            .build();
                                    exoPlayer.removeMediaItem(0);
                                    exoPlayer.setMediaItem(mediaItem);
                                    exoPlayer.prepare();
                                }catch (Exception ex) {
                                    Log.e(TAG, "onReceive: " + ex.getMessage());
                                }
                            }
                            exoPlayer.play();
                            controlNotification();
                            if (viewHolder != null){
                                viewHolder.getPlay().setBackgroundResource(R.drawable.baseline_pause_24);
                            }
                        }, 0);
                        break;
                    case MediaAction.ACTION_PAUSE:
                        exoPlayer.setPlayWhenReady(false);
                        exoPlayer.pause();
                        controlNotification();
                        if (viewHolder != null){
                            viewHolder.getPlay().setBackgroundResource(R.drawable.baseline_play_arrow_24);
                        }
                        break;
                    case MediaAction.ACTION_STOP:
                        exoPlayer.setPlayWhenReady(false);
                        exoPlayer.stop();
//                        exoPlayer.release();
//                        setExoPlayer(null);
                        break;
                }

            }catch(Exception ex){
                Log.e("Reciver", "onReceive: " + ex.getMessage());
            }
        }
    }
    public void registerReceiver(Context context) {
        this.context = context;
        isRegister = true;
        //Setup media control receiver
        exoPlayer = new ExoPlayer.Builder(context).build();
        setExoPlayer(exoPlayer);
        //Setup view
        setupView();
        //Register the broadcast receiver
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MediaAction.ACTION_PLAY);
        intentFilter.addAction(MediaAction.ACTION_PAUSE);
        intentFilter.addAction(MediaAction.ACTION_STOP);
        context.registerReceiver(MediaControlReceiver.getInstance(), intentFilter);
    }
    public void unregisterReceiver(Context context){
        //Unregister broadcast and release exoplayer instance
        context.unregisterReceiver(this);
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
    public static long lastClickTime = 0;
    public static final long DOUBLE_CLICK_TIME_DELTA = 500;

    public static boolean isDoubleClick(){
        long clickTime = System.currentTimeMillis();
        if(clickTime - lastClickTime < DOUBLE_CLICK_TIME_DELTA){
            lastClickTime = clickTime;
            return true;
        }
        lastClickTime = clickTime;
        return false;
    }
    public void addControl(Context context, PlayerViewHolder viewHolder){
        this.viewHolder = viewHolder;
        viewHolder.getPlay().setOnClickListener(v -> {
            if (!isDoubleClick()){
                if(!exoPlayer.isPlaying()) {
                    viewHolder.getPlay().setBackgroundResource(R.drawable.baseline_pause_24);
                    context.sendBroadcast(new Intent(MediaAction.ACTION_PLAY));
                }
                else{
                    viewHolder.getPlay().setBackgroundResource(R.drawable.baseline_play_arrow_24);
                    context.sendBroadcast(new Intent(MediaAction.ACTION_PAUSE));
                }
            }
        });
        viewHolder.getSkipNext().setOnClickListener(v -> {
            if (!isDoubleClick()){
                isSkip = true;
                seekToNextSong(context);
            }
        });
        viewHolder.getSkipPrev().setOnClickListener(v -> {
            if (!isDoubleClick()){
                isSkip = true;
                seekToPrevSong(context);
            }
        });
        viewHolder.getLoop().setOnClickListener(v -> {
            if (!isDoubleClick()){
                if (!isLoop){
                    isLoop = true;
                    viewHolder.getLoop().setBackground(v.getResources().getDrawable(R.drawable.baseline_loop_24_on));
                    exoPlayer.setRepeatMode(Player.REPEAT_MODE_ALL);
                }
                else{
                    isLoop = false;
                    viewHolder.getLoop().setBackground(v.getResources().getDrawable(R.drawable.baseline_loop_24));
                    exoPlayer.setRepeatMode(Player.REPEAT_MODE_OFF);
                }
            }
        });
        viewHolder.getShuffle().setOnClickListener(v -> {
            if (!isDoubleClick()){
                if (!isShuffle){
                    isShuffle = true;
                    viewHolder.getShuffle().setBackground(v.getResources().getDrawable(R.drawable.baseline_shuffle_24_on));
                }
                else{
                    isShuffle = false;
                    viewHolder.getShuffle().setBackground(v.getResources().getDrawable(R.drawable.baseline_shuffle_24));
                }
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
                    Log.i(TAG, "onPlaybackStateChanged: buffering...");
                    viewHolder.getPlay().setBackgroundResource(R.drawable.baseline_pause_24);
                } else if (playbackState == Player.STATE_READY) {
                    Log.i(TAG, "onPlaybackStateChanged: play");
                    // Playback is ready, update UI accordingly
                    if (exoPlayer.getPlayWhenReady()) {
                        // Player is in play state
                        // Example: Handle play event with a Handler
                        int duration = ((int) exoPlayer.getDuration());
                        viewHolder.getSeekBar().setMax(duration);
                        viewHolder.getEnd().setText(createTimeText(duration));
                        viewHolder.getPlay().setBackgroundResource(R.drawable.baseline_pause_24);
                        controlNotification();
                    } else {
                        // Player is in pause state
                        // Example: Handle pause event with a Handler
                        Log.i(TAG, "onPlaybackStateChanged: pause");
                        viewHolder.getPlay().setBackgroundResource(R.drawable.baseline_play_arrow_24);
                        controlNotification();
                    }
                } else if (playbackState == Player.STATE_ENDED) {
                    // Playback ended, update UI accordingly
                    Log.i(TAG, "onPlaybackStateChanged: ended");
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
                if (progress == seekBar.getMax() && !isLoop){
                    if (isShuffle){
                        final int random = currentPos + 2;
                        setCurrentPos(random);
                    }
                    seekToNextSong(context);
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
    public void seekToNextSong(Context context){
        if (currentPos + 1 <= playList.size() - 1){
            currentPos++;
        }
        else{
            exoPlayer.seekTo(0);
        }
        context.sendBroadcast(new Intent(MediaAction.ACTION_PLAY));
    }
    public void seekToPrevSong(Context context){
        if (currentPos - 1 >= 0){
            currentPos--;
        }
        else{
            exoPlayer.seekTo(0);
        }
        context.sendBroadcast(new Intent(MediaAction.ACTION_PLAY));
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

    private void setupView() {
        mMediaSession = new MediaSessionCompat(context, this.getClass().getSimpleName());
        mMediaSession.setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS | MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);

        // Do not let MediaButtons restart the player when the app is not visible.
        mMediaSession.setMediaButtonReceiver(null);

        mStateBuilder = new PlaybackStateCompat.Builder().setActions(
                PlaybackStateCompat.ACTION_PLAY |
                        PlaybackStateCompat.ACTION_PAUSE |
                        PlaybackStateCompat.ACTION_PLAY_PAUSE |
                        PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS |
                        PlaybackStateCompat.ACTION_SKIP_TO_NEXT |
                        PlaybackStateCompat.ACTION_STOP);

        mMediaSession.setPlaybackState(mStateBuilder.build());
        mMediaSession.setCallback(new MySessionCallback());
        mMediaSession.setActive(true);
    }
    public void controlNotification(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mMediaSession.setPlaybackState(mStateBuilder.build());
                showNotification(mStateBuilder.build());
            }
        }, 600);
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private void createChannel() {
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        // The id of the channel.
        String id = CHANNEL_ID;
        // The user-visible name of the channel.
        CharSequence name = "Media playback";
        // The user-visible description of the channel.
        String description = "Media playback controls";
        int importance = NotificationManager.IMPORTANCE_LOW;
        NotificationChannel mChannel = new NotificationChannel(id, name, importance);
        // Configure the notification channel.
        mChannel.setDescription(description);
        mChannel.setShowBadge(false);
        mChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
        mNotificationManager.createNotificationChannel(mChannel);
    }
    //Session
    private class MySessionCallback extends MediaSessionCompat.Callback {
        @Override
        public void onPlay() {
            mMediaSession.setPlaybackState(mStateBuilder.build());
            showNotification(mStateBuilder.build());
            if(!exoPlayer.isPlaying()) {
                context.sendBroadcast(new Intent(MediaAction.ACTION_PLAY));
            }
            else{
                context.sendBroadcast(new Intent(MediaAction.ACTION_PAUSE));
            }
        }
        @Override
        public void onSkipToPrevious() {
            isSkip = true;
            seekToPrevSong(context);
        }

        @Override
        public void onSkipToNext() {
            isSkip = true;
            seekToNextSong(context);
        }

        @Override
        public void onStop() {
            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            manager.cancel(0);
            context.sendBroadcast(new Intent(MediaAction.ACTION_STOP));
        }
    }
    public static class MediaReceiver extends BroadcastReceiver {

        public MediaReceiver() {
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            MediaButtonReceiver.handleIntent(mMediaSession, intent);
        }
    }
    private void showNotification(PlaybackStateCompat state) {

        // You only need to create the channel on API 26+ devices
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel();
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID);

        int icon;
        String play_pause;
        if (exoPlayer.isPlaying()) {
            icon = R.drawable.baseline_pause_24;
            play_pause = "Dừng";
        } else {
            icon = R.drawable.baseline_play_arrow_24;
            play_pause = "Phát";
        }

        NotificationCompat.Action playPauseAction = new NotificationCompat.Action(
                icon, play_pause,
                MediaButtonReceiver.buildMediaButtonPendingIntent(context,
                        PlaybackStateCompat.ACTION_PLAY_PAUSE));
        NotificationCompat.Action prevAction = new NotificationCompat.Action(
                R.drawable.baseline_skip_previous_24, "Bài Trước",
                MediaButtonReceiver.buildMediaButtonPendingIntent(context,
                        PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS));
        NotificationCompat.Action nextAction = new NotificationCompat.Action(
                R.drawable.baseline_skip_next_24, "Bài Sau",
                MediaButtonReceiver.buildMediaButtonPendingIntent(context,
                        PlaybackStateCompat.ACTION_SKIP_TO_NEXT));
        NotificationCompat.Action exitAction = new NotificationCompat.Action(
                android.R.drawable.ic_menu_close_clear_cancel, "Thoát",
                MediaButtonReceiver.buildMediaButtonPendingIntent(context,
                        PlaybackStateCompat.ACTION_STOP)
        );
        PendingIntent contentPendingIntent = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            contentPendingIntent = PendingIntent.getActivity
                    (context, 0, new Intent(context, PlayerActivity.class), PendingIntent.FLAG_MUTABLE);
        } else {
            contentPendingIntent = PendingIntent.getActivity
                    (context, 0, new Intent(context, PlayerActivity.class), PendingIntent.FLAG_ONE_SHOT);
        }
        mMediaSession.setMetadata(
                new MediaMetadataCompat.Builder()
                        // Title.
                        .putString(MediaMetadata.METADATA_KEY_TITLE, getCurrentSong().title)
                        // Artist.
                        // Could also be the channel name or TV series.
                        .putString(MediaMetadata.METADATA_KEY_ARTIST, getCurrentSong().artistsNames)
                        // Album art.
                        // Could also be a screenshot or hero image for video content
                        // The URI scheme needs to be "content", "file", or "android.resource".
                        .putBitmap(
                                MediaMetadata.METADATA_KEY_ALBUM_ART, CoreHelper.ImageUtil.getBitmapFromURL(getCurrentSong().thumbnailM))
                        // Duration.
                        // If duration isn't set, such as for live broadcasts, then the progress
                        // indicator won't be shown on the seekbar.
                        .putLong(MediaMetadata.METADATA_KEY_DURATION, exoPlayer.getDuration()) // 4
                        .build()
        );
        token = mMediaSession.getSessionToken();

        builder.setContentIntent(contentPendingIntent)
                .setSmallIcon(R.drawable.ic_play_circle)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .addAction(prevAction)
                .addAction(playPauseAction)
                .addAction(nextAction)
                .addAction(exitAction)
                .setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
                        .setMediaSession(token)
                        .setShowActionsInCompactView(0, 1, 2));

        mNotificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        mNotificationManager.notify(0, builder.build());


    }
}
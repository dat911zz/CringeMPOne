package com.ltdd.cringempone.ui;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ltdd.cringempone.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MusicPlayer extends AppCompatActivity {
    private static final int CHOOSE_FILE_REQUESTCODE = 8777;
    private static final int PICKFILE_RESULT_CODE = 8778;
    private Player player = new Player();
    private MediaPlayer mediaPlayer;
    private Handler handler = new Handler();
    private ViewHolder viewHolder;
    private Song current;
    private Animation rotate;
    private ImageView discImg;
    List<Song> playList = new ArrayList<Song>(Arrays.asList(
            new Song("Một bước yêu vạn dặm đau", "mbyvdd"),
            new Song("Maze (The King: Eternal Monarch OST Part 4) Instrumental", "maze"),
            new Song("A Thousand Years - Christina Perri", "athousandyear")
    ));
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);
        viewHolder = new ViewHolder(
                findViewById(R.id.btnPrev),
                findViewById(R.id.btnNext),
                findViewById(R.id.btnPlay),
                findViewById(R.id.btnPause),
                findViewById(R.id.txtMediaName),
                findViewById(R.id.txtS),
                findViewById(R.id.txtE),
                findViewById(R.id.seekBar)
        );
        current = playList.get(0);
        bindEventForPlayList();
        bindEventForMediaPlayer();
        rotate = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);
        discImg = findViewById(R.id.discImg);
        discImg.setImageResource(R.drawable.disc);
    }
    public void bindEventForPlayList(){
        ListView playListView = findViewById(R.id.lstSong);
        playListView.setAdapter(new ArrayAdapter<String>(this, com.google.android.material.R.layout.support_simple_spinner_dropdown_item ,Arrays.asList(
                playList.get(0).name,
                playList.get(1).name,
                playList.get(2).name
        )));

        playListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    player.oTime = 0;
                    viewHolder.pause.setEnabled(false);
                    viewHolder.play.setEnabled(true);
                }
                current = playList.get(i);
                mediaPlayer = MediaPlayer.create(view.getContext(), getMediaIdByName(current.getfName()));
                viewHolder.name.setText(current.getName());
            }
        });
    }
    public void bindEventForMediaPlayer() {
        mediaPlayer = MediaPlayer.create(this, getMediaIdByName(current.getfName()));
        viewHolder.name.setText(current.getName());
        viewHolder.prog.setClickable(false);
        viewHolder.pause.setEnabled(false);
        viewHolder.play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Playing Audio", Toast.LENGTH_SHORT).show();
                mediaPlayer.start();
                discImg.startAnimation(rotate);

                player.eTime = mediaPlayer.getDuration();
                player.sTime = mediaPlayer.getCurrentPosition();

                if (player.oTime == 0) {
                    viewHolder.prog.setMax(player.eTime);
                    player.oTime = 1;
                }
                viewHolder.start.setText(String.format("%d min, %d sec",
                        TimeUnit.MILLISECONDS.toMinutes(player.sTime),
                        TimeUnit.MILLISECONDS.toSeconds(player.sTime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(player.sTime))
                ));
                viewHolder.end.setText(String.format("%d min, %d sec",
                        TimeUnit.MILLISECONDS.toMinutes(player.eTime),
                        TimeUnit.MILLISECONDS.toSeconds(player.eTime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(player.eTime))
                ));
                viewHolder.prog.setProgress(player.sTime);
                handler.postDelayed(UpdateSongTime, 100);
                viewHolder.pause.setEnabled(true);
                viewHolder.play.setEnabled(false);
            }
        });
        viewHolder.pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.pause();
                discImg.clearAnimation();
                viewHolder.pause.setEnabled(false);
                viewHolder.play.setEnabled(true);
                Toast.makeText(getApplicationContext(), "Pausing Audio",
                        Toast.LENGTH_SHORT).show();
            }
        });
        viewHolder.next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((player.sTime + player.fTime) <= player.eTime) {
                    player.sTime = player.sTime + player.fTime;
                    mediaPlayer.seekTo(player.sTime);
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Cannot jump forward 5 second",
                            Toast.LENGTH_SHORT
                    ).show();
                }
                if (!viewHolder.play.isEnabled()) {
                    viewHolder.play.setEnabled(true);
                }
            }
        });

        viewHolder.prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((player.sTime - player.bTime) > 0) {
                    player.sTime = player.sTime - player.bTime;
                    mediaPlayer.seekTo(player.sTime);
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Cannot jump backward 5 seconds",
                            Toast.LENGTH_SHORT
                    ).show();
                }
                if (!viewHolder.play.isEnabled()) {
                    viewHolder.play.setEnabled(true);
                }
            }
        });
//        }catch (Exception ex){
//            Log.e("System", ex.getMessage());
//        }

    }
    class Player {
        int oTime, sTime, eTime, fTime, bTime;
        public Player(){
            oTime = sTime = eTime = 0;
            fTime = bTime = 5000;
        }
    }
    static class ViewHolder{
        ImageButton prev, next, play, pause;
        TextView name, start, end;
        SeekBar prog;

        public ViewHolder(ImageButton prev, ImageButton next, ImageButton play, ImageButton pause, TextView name, TextView start, TextView end, SeekBar prog) {
            this.prev = prev;
            this.next = next;
            this.play = play;
            this.pause = pause;
            this.name = name;
            this.start = start;
            this.end = end;
            this.prog = prog;
        }
    }
    class Song{
        String name, fName;
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getfName() {
            return fName;
        }

        public void setfName(String fName) {
            this.fName = fName;
        }

        public Song(String name, String fName) {
            this.name = name;
            this.fName = fName;
        }
    }
    public int getMediaIdByName(String resName){
        String pkgName = getPackageName();
        int resID = getResources().getIdentifier(resName, "raw", pkgName);
        Log.i("CustomGridView", "Res Name: " + resName + "==> Res ID = " + resID);
        return resID;
    }
    private Runnable UpdateSongTime = new Runnable() {
        @Override
        public void run() {
            player.sTime = mediaPlayer.getCurrentPosition();
            viewHolder.start.setText(String.format("%d min, %d sec",
                    TimeUnit.MILLISECONDS.toMinutes(player.sTime),
                    TimeUnit.MILLISECONDS.toSeconds(player.sTime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(player.sTime))
            ));
            viewHolder.prog.setProgress(player.sTime);
            handler.postDelayed(this, 100);
        }
    };
}
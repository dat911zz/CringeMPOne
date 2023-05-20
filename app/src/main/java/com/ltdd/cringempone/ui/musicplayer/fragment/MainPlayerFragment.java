package com.ltdd.cringempone.ui.musicplayer.fragment;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.exoplayer2.Player;
import com.ltdd.cringempone.R;
import com.ltdd.cringempone.databinding.FragmentMainPlayerBinding;
import com.ltdd.cringempone.service.MediaControlReceiver;
import com.ltdd.cringempone.ui.musicplayer.ViewPagerPlayerController;
import com.ltdd.cringempone.utils.CoreHelper;
public class MainPlayerFragment extends Fragment{
    private String songName;
    private String artist;
    private View view;
    private FragmentMainPlayerBinding binding;
    public MainPlayerFragment() {
        // Required empty public constructor
    }
    public static MainPlayerFragment newInstance() {
        return new MainPlayerFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentMainPlayerBinding.inflate(inflater, container, false);
        ViewPagerPlayerController.getInstance().setFragmentMainPlayerBinding(binding);
        MediaControlReceiver mediaControlReceiver = MediaControlReceiver.getInstance();
        mediaControlReceiver.executeDisc(binding.imgDisc);
        addControl();
        return binding.getRoot();
    }
    @Override
    public void onResume() {
        super.onResume();
    }

    public void addControl(){
        MediaControlReceiver.getInstance().getExoPlayer().addListener(new Player.Listener() {
            @Override
            public void onPlaybackStateChanged(int playbackState) {
                Player.Listener.super.onPlaybackStateChanged(playbackState);
            }
        });
        if (MediaControlReceiver.getInstance().getCurrentSong() != null){
            songName = MediaControlReceiver.getInstance().getCurrentSong().title;
            artist = MediaControlReceiver.getInstance().getCurrentSong().artistsNames;
        }
        else {
            CoreHelper.CustomsDialog.showAlertDialog(
                    this.getContext(),
                    "Lỗi",
                    "Đã xảy ra lỗi, vui lòng kiểm tra lại kết nối mạng!",
                    R.drawable.baseline_error_24
            );
        }
        MediaControlReceiver.getInstance().getExoPlayer().addListener(new Player.Listener() {
            @Override
            public void onPlaybackStateChanged(int playbackState) {
                Player.Listener.super.onPlaybackStateChanged(playbackState);
            }
        });
        binding.txtSongName.setText(songName);
        binding.txtTitle.setText(artist);
    }
}
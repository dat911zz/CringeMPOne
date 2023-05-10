package com.ltdd.cringempone.ui.musicplayer.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainPlayerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainPlayerFragment extends Fragment {

    private static final String SONG_NAME = "songName";
    private static final String ARTIST = "artist";
    private String songName;
    private String artist;
    private View view;
    private FragmentMainPlayerBinding binding;
    public MainPlayerFragment() {
        // Required empty public constructor
    }
    public static MainPlayerFragment newInstance(String songName, String artist) {
        MainPlayerFragment fragment = new MainPlayerFragment();
        Bundle args = new Bundle();
        args.putString(SONG_NAME, songName);
        args.putString(ARTIST, artist);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null){
            songName = getArguments().getString(SONG_NAME);
            artist = getArguments().getString(ARTIST);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentMainPlayerBinding.inflate(inflater, container, false);
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
        binding.txtSongName.setText(songName);
        binding.txtTitle.setText(artist);
    }
}
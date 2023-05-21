package com.ltdd.cringempone.ui.musicplayer.fragment;

import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.exoplayer2.Player;
import com.ltdd.cringempone.R;
import com.ltdd.cringempone.data.firebase.DBUser;
import com.ltdd.cringempone.data.firebase.User;
import com.ltdd.cringempone.databinding.FragmentMainPlayerBinding;
import com.ltdd.cringempone.service.MediaControlReceiver;
import com.ltdd.cringempone.ui.musicplayer.ViewPagerPlayerController;
import com.ltdd.cringempone.utils.CoreHelper;
import com.ltdd.cringempone.utils.CustomsDialog;

import java.util.ArrayList;

public class MainPlayerFragment extends Fragment{
    private String songName;
    private String artist;
    private String idSong;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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
            idSong = MediaControlReceiver.getInstance().getCurrentSong().encodeId;
        } else {
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
        checkFSong(idSong);
        binding.favoriteSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBUser dbUser = new DBUser();

                String idUser = dbUser.getIdUser();
                if (idUser == null) {
                    Toast.makeText(binding.getRoot().getContext(), "Vui lòng đăng nhập để sử dụng", Toast.LENGTH_SHORT).show();
                    return;
                }
                dbUser.getInfoUser(idUser, new DBUser.UserCallback() {
                    @Override
                    public void onSuccess(User user) {
                        if (user != null) {
                            ArrayList<String> fSong = user.getFavoriteSong();
                            if (fSong.contains(idSong)) {
                                fSong.remove(idSong);
                                dbUser.updateFavoriteSong(idUser, fSong);
                                v.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#B5B5B5")));
                                Log.e("favorite song", user.getFavoriteSong().toString());
                            } else {
                                fSong.add(idSong);
                                dbUser.updateFavoriteSong(idUser, fSong);
                                v.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#cb54ec")));
                                Log.e("favorite song", user.getFavoriteSong().toString());
                            }

                        } else return;
                    }

                    @Override
                    public void onFailure(String errorMessage) {
                        Log.e("Loi", errorMessage);
                    }
                });

            }
        });

    }

    public void checkFSong(String idSong) {
        DBUser dbUser = new DBUser();
        String idUser = dbUser.getIdUser();
        if (idUser == null)
            return;
        else {
            dbUser.getInfoUser(idUser, new DBUser.UserCallback() {
                @Override
                public void onSuccess(User user) {
                    if (user != null) {
                        ArrayList<String> fSong = user.getFavoriteSong();
                        if (fSong.contains(idSong)) {
                            binding.favoriteSong.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#cb54ec")));
                        }
                    }
                    return;
                }

                @Override
                public void onFailure(String errorMessage) {
                    Log.e("Loi", errorMessage);
                }
            });
        }
    }
}
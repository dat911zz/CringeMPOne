package com.ltdd.cringempone.ui.musicplayer.fragment;

import com.ltdd.cringempone.api.BaseAPIService;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.ltdd.cringempone.R;
import com.ltdd.cringempone.api.*;
import com.ltdd.cringempone.data.dto.LyricDTO;
import com.ltdd.cringempone.databinding.FragmentLyricPlayerBinding;
import com.ltdd.cringempone.service.LocalStorageService;
import com.ltdd.cringempone.service.MediaControlReceiver;
import com.ltdd.cringempone.ui.musicplayer.ViewPagerPlayerController;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class LyricPlayerFragment extends Fragment {
    FragmentLyricPlayerBinding binding;
    public LyricPlayerFragment() {}
    public static LyricPlayerFragment newInstance() {
        return new LyricPlayerFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLyricPlayerBinding.inflate(inflater, container, false);
        ViewPagerPlayerController.getInstance().setFragmentLyricPlayerBinding(binding);
        return binding.getRoot();
    }
}
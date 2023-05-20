package com.ltdd.cringempone.ui.musicplayer.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ltdd.cringempone.R;
import com.ltdd.cringempone.databinding.FragmentInfoPlayerBinding;
import com.ltdd.cringempone.ui.musicplayer.ViewPagerPlayerController;

public class InfoPlayerFragment extends Fragment {
    FragmentInfoPlayerBinding binding;
    private static InfoPlayerFragment instance;

    private InfoPlayerFragment() {
    }

    public static InfoPlayerFragment getInstance(){
        if (instance == null){
            instance = new InfoPlayerFragment();
        }
        return instance;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentInfoPlayerBinding.inflate(inflater);
        ViewPagerPlayerController.getInstance().setFragmentInfoPlayerBinding(binding);
        return binding.getRoot();
    }
}
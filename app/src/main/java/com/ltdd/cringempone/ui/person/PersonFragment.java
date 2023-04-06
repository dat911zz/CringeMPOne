package com.ltdd.cringempone.ui.person;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.ltdd.cringempone.R;


public class PersonFragment extends Fragment {
    View view;
    ViewPager viewPager;
    Runnable runnable;
    Handler handler;
    int currentItem;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_person, container, false);
        bindingView();
        return view;
    }

    private void bindingView() {
        viewPager = view.findViewById(R.id.viewPager);
    }
}

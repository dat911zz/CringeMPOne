package com.ltdd.cringempone.utils;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.ltdd.cringempone.R;
import com.ltdd.cringempone.ui.fragment.MainPlayerFragment;

import java.io.InputStream;
import java.net.URL;

public class Helper {
    public static Drawable LoadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        } catch (Exception e) {
            Log.e("Helper", "LoadImageFromWebOperations: " + e.getMessage());
            return null;
        }
    }
    public static class FragmentUtil{

        public static FragmentTransaction ft;
        public static void addFragment(Activity activity, Fragment fragment, int frameLayout, String name){
            FragmentManager fragmentManager = ((AppCompatActivity)activity).getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(frameLayout, fragment)
                    .setReorderingAllowed(true)
                    .addToBackStack(name) // name can be null
                    .commit();
        }
    }

}

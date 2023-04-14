package com.ltdd.cringempone.utils;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import org.jetbrains.annotations.Nullable;

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
        public static void addFragment(Activity activity, Fragment fragment, int frameLayout,@Nullable String name){
            FragmentManager fragmentManager = ((AppCompatActivity)activity).getSupportFragmentManager();
            FragmentTransaction fr = fragmentManager.beginTransaction();
                    fr.replace(frameLayout, fragment)
                    .setReorderingAllowed(true);
            if (name != null) {
                fr.addToBackStack(name); // name can be null
            }
            fr.commit();
        }
    }

}

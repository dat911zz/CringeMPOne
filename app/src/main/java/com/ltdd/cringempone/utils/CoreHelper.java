package com.ltdd.cringempone.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.ltdd.cringempone.data.dto.ItemDTO;
import com.ltdd.cringempone.data.dto.SongInfoDTO;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.atomic.AtomicReference;

public class CoreHelper {
    public static boolean isConnected(Context context){
        ConnectivityManager cm = (ConnectivityManager)context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnected()) {
            try {
                HttpURLConnection connection = null;
                URL url = new URL("https://www.google.com/");
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setReadTimeout(1000);
                connection.setConnectTimeout(1000);
                connection.connect();
                if (connection.getResponseCode() == 200) {
                    return true;
                } else {
                    return false;
                }
            } catch (IOException e) {
                Log.i("warning", "Error checking internet connection", e);
                return false;
            }
        }

        return false;
    }
    public static class ImageUtil{
        public static Drawable LoadImageFromWebOperations(String url) {
            try {
                InputStream is = (InputStream) new URL(url).getContent();
                Drawable d = Drawable.createFromStream(is, "src name");
                return d;
            } catch (Exception e) {
                Log.e("CoreHelper", "LoadImageFromWebOperations: " + e.getMessage());
                return null;
            }
        }
        public static Bitmap getBitmapFromURL(String url) {
            final AtomicReference<Bitmap> bitmapRef = new AtomicReference<>();

            Target target = new Target() {
                @Override
                public void onBitmapLoaded(Bitmap loadedBitmap, Picasso.LoadedFrom from) {
                    // Bitmap is ready, assign it to the atomic reference
                    bitmapRef.set(loadedBitmap);
                }

                @Override
                public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                    // Handle any errors that occurred while loading the bitmap
                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {
                    // Optional: You can perform some action while the bitmap is being loaded
                }
            };

            Picasso.get().load(url).into(target);

            // Wait until the bitmap is loaded
            while (bitmapRef.get() == null) {
                try {
                    Thread.sleep(100); // Sleep for a short while
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            // Return the downloaded bitmap
            return bitmapRef.get();
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
    public static class Converter{
        public static ItemDTO songInfoDTO2ItemDTO(SongInfoDTO songInfoDTO){
            return new ItemDTO(
                    songInfoDTO.encodeId,
                    songInfoDTO.title,
                    songInfoDTO.thumbnail,
                    songInfoDTO.isOffical,
                    songInfoDTO.link,
                    songInfoDTO.isIndie,
                    "",
                    "",
                    0,
                    songInfoDTO.genreIds,
                    songInfoDTO.preRelease,
                    songInfoDTO.artists,
                    songInfoDTO.artistsNames,
                    0,
                    0,
                    songInfoDTO.userid,
                    songInfoDTO.thumbnailM,
                    false,
                    songInfoDTO.isPrivate,
                    songInfoDTO.username,
                    false,
                    "",
                    false,
                    songInfoDTO.distributor,
                    songInfoDTO.hasLyric,
                    songInfoDTO.mvlink
            );
        }
    }

}

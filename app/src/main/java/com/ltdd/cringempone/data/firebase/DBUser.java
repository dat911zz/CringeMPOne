package com.ltdd.cringempone.data.firebase;

import android.util.Log;

import com.google.android.gms.common.api.internal.TaskUtil;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public class DBUser {
    String uid,email,password,nameUser;
    Database DB = new Database();
    DatabaseReference userRef = DB.database.getReference("Users");
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    public String getUid() {
        return uid;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getNameUser() {
        return nameUser;
    }

    public DBUser() {
    }

    public void createUser(String uid, String nameUser, String email, String password)
    {
        try {
            User user = new User(password,email, nameUser, uid);
            userRef.child(uid).setValue(user);
        }catch (Exception e)
        {
            Log.e("Error",e.getMessage());
        }
    }

    public void createUser(String uid, String nameUser, String email)
    {
        try {
            User user = new User(password, email, nameUser, uid);
            userRef.child(uid).setValue(user);
        }catch (Exception e)
        {
            Log.e("Error",e.getMessage());
        }
    }

    public String getIdUser()
    {
        String idUser;
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null)
        {
            return idUser = firebaseUser.getUid();
        }
        return null;
    }

    public void  getInfoUser(String id, final UserCallback callback)
    {
        Task<DataSnapshot> task =  userRef.child(id).get();
        task.addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    User user = dataSnapshot.getValue(User.class);
                    callback.onSuccess(user);

                }
                else callback.onFailure(task.getException().getMessage());
            }
        });
    }
    public void updateFavoriteSong(String idUser,ArrayList<String> fSong)
    {
        userRef.child(idUser).child("favoriteSong" ).setValue(fSong);
    }

    public interface UserCallback {
        void onSuccess(User user);
        void onFailure(String errorMessage);
    }

}

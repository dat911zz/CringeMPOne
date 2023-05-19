package com.ltdd.cringempone.firebase;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;

public class DBUser {
    String uid,email,password,nameUser;
    Database DB = new Database();
    DatabaseReference userRef = DB.database.getReference("Users");

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

}

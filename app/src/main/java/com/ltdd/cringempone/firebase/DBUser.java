package com.ltdd.cringempone.firebase;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;

public class DBUser {
    String email,password,nameUser;
    Database DB = new Database();
    DatabaseReference userRef = DB.database.getReference("Users");

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void createUser(String email, String password)
    {
        try {
            User user = new User(password,email);
            userRef.child(email).setValue(user);
        }catch (Exception e)
        {
            Log.e("Error",e.getMessage());
        }
    }

}

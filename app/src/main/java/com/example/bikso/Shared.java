package com.example.bikso;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

class Shared {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private int mode = 0;
    private String filename = "biksospfile";
    private Context context;
    private int uid;

    Shared(Context context, int uid) {
        this.context = context;
        this.uid = uid;
        sharedPreferences = context.getSharedPreferences(filename, mode);
        editor = sharedPreferences.edit();
    }

    void secondTime(){
        editor.putBoolean("b", true);
        editor.putInt("uid", uid);
        editor.commit();
    }

    void firstTime(){
        if (!this.login()){
            Intent intent = new Intent(context, Login.class);
            context.startActivity(intent);
        }
    }

    boolean login(){
        return sharedPreferences.getBoolean("b", false);
    }

    int getUid(){
        return sharedPreferences.getInt("uid", 0);
    }

    void logout(){
        editor.putBoolean("b", false);
        editor.putInt("uid", uid);
        editor.commit();
    }
}

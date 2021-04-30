package com.example.bikso;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class Welcome_logo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_logo);

        //hiding the action bar
        Objects.requireNonNull(getSupportActionBar()).hide();
        //hiding the action bar

        //getting shared preference value and checking if logged in or not and redirecting to respective activity according to condition
        Shared shared = new Shared(getApplicationContext(), 0);
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            if (shared.login()){
                int uid = shared.getUid();
                Intent intent = new Intent(Welcome_logo.this, Homepage.class);
                intent.putExtra("uid", uid);
                startActivity(intent);
            }
            else{
                shared.firstTime();
            }
            finish();
        }, 2500);
        //getting shared preference value and checking if logged in or not and redirecting to respective activity according to condition
    }
}

package com.example.bikso;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    EditText username, password;
    Button loginbtn, signupbtn;
    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        loginbtn = findViewById(R.id.loginbtn);
        signupbtn = findViewById(R.id.signupbtn);

        //hiding the action bar
        Objects.requireNonNull(getSupportActionBar()).hide();
        //hiding the action bar

        //login button onclicklistener
        loginbtn.setOnClickListener(v -> {
            if (username.getText().toString().equals("") || password.getText().toString().equals("")){
                Toast.makeText(getApplicationContext(), "Require all fields", Toast.LENGTH_SHORT).show();
            }
            else{
                Call<String> call = RetrofitInstance.getApiInstance().checkUser(username.getText().toString(), password.getText().toString());
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (Integer.parseInt(response.body()) == -2){
                            Toast.makeText(getApplicationContext(), "Wrong password", Toast.LENGTH_SHORT).show();
                        }
                        else if (Integer.parseInt(response.body()) == 0){
                            Toast.makeText(getApplicationContext(), "Not registered. Please register", Toast.LENGTH_SHORT).show();
                        }
                        else if (Integer.parseInt(response.body()) == -1){
                            Toast.makeText(getApplicationContext(), "Some error occured. Try again", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Login successfull", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Login.this, Homepage.class);
                            intent.putExtra("uid", Integer.parseInt(response.body()));
                            startActivity(intent);
                            Shared shared = new Shared(getApplicationContext(), Integer.parseInt(response.body()));
                            shared.secondTime();
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        //login button onclicklistener

        //signup button onclicklistener
        signupbtn.setOnClickListener(v -> {
            Intent intent = new Intent(getBaseContext(), SignUp.class);
            startActivity(intent);
        });
        //signup button onclicklistener
    }

    @Override
    public void onBackPressed() {
        //press twice back button to exit the app
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            finish();
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(() -> doubleBackToExitPressedOnce = false, 2000);
        //press twice back button to exit the app
    }
}

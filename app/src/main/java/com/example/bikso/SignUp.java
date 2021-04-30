package com.example.bikso;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUp extends AppCompatActivity {

    EditText sname, semail, smob, spass, sconpass;
    Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        sname = findViewById(R.id.sname);
        semail = findViewById(R.id.semail);
        smob = findViewById(R.id.smob);
        spass = findViewById(R.id.spass);
        sconpass = findViewById(R.id.sconpass);
        register = findViewById(R.id.register);

        //hiding the action bar
        Objects.requireNonNull(getSupportActionBar()).hide();
        //hiding the action bar

        //register button onclicklistener
        register.setOnClickListener(v -> {
            if (sname.getText().toString().equals("") || semail.getText().toString().equals("") || smob.getText().toString().equals("") || spass.getText().toString().equals("") || sconpass.getText().toString().equals("")){
                Toast.makeText(getApplicationContext(), "Require all fields", Toast.LENGTH_SHORT).show();
            }
            else if (!(spass.getText().toString().equals(sconpass.getText().toString()))){
                Toast.makeText(getApplicationContext(), "Password must match", Toast.LENGTH_SHORT).show();
            }
            else{
                Call<String> call = RetrofitInstance.getApiInstance().insertUser(sname.getText().toString(), semail.getText().toString(), smob.getText().toString(), spass.getText().toString());
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if(Integer.parseInt(response.body()) == 0){
                            Toast.makeText(getApplicationContext(), "Already registered. Please login", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SignUp.this, Login.class);
                            startActivity(intent);
                            finish();
                        }
                        else if(Integer.parseInt(response.body()) == -1 || Integer.parseInt(response.body()) == -2){
                            Toast.makeText(getApplicationContext(), "Some error occured. Try again", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Registered successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SignUp.this, Homepage.class);
                            intent.putExtra("uid", Integer.parseInt(response.body()));
                            startActivity(intent);
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
        //register button onclicklistener
    }
}

package com.example.bikso;

import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServiceBooked extends AppCompatActivity {

    String services, time, name, output;
    int position, sid;
    ImageView success;
    TextView tv, tv1, tv2, tv3, tv4, tv5, tv6;
    LinearLayout ll1;
    Button btn, btn1, btn2;
    ProgressBar pb;
    int uid;
    //get bid here

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_booked);
        sid = getIntent().getIntExtra("sid", -1);
        services = getIntent().getStringExtra("services");
        time = getIntent().getStringExtra("time");
        position = getIntent().getIntExtra("position", -1);
        name = getIntent().getStringExtra("name");
        uid = getIntent().getIntExtra("uid", 0);
        success = findViewById(R.id.success);
        pb = findViewById(R.id.pb);
        tv = findViewById(R.id.tv);
        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
        tv3 = findViewById(R.id.tv3);
        tv4 = findViewById(R.id.tv4);
        tv5 = findViewById(R.id.tv5);
        tv6 = findViewById(R.id.tv6);
        ll1 = findViewById(R.id.ll1);
        btn = findViewById(R.id.btn);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        String no = time.substring(30);
        int n = Integer.parseInt(no) - 1;
        String newno = String.valueOf(n);
        time = time.substring(0, 30) + newno;

        //function call to book service
        insertBooking();
        //function call to book service

        //home button click listener
        btn2.setOnClickListener(v -> {
            Intent intent = new Intent(ServiceBooked.this, Homepage.class);
            intent.putExtra("uid", uid);
            startActivity(intent);
            finishAffinity();
        });
        //home button click listener

        //history button click listener
        btn.setOnClickListener(v -> {
            Intent intent = new Intent(ServiceBooked.this, BookingHistory.class);
            intent.putExtra("uid", uid);
            startActivity(intent);
        });
        //history button click listener
    }

    //function to insert booking in db using retrofit and show progress bar while loading and then hide it
    void insertBooking(){
        Call<String> call = RetrofitInstance.getApiInstance().insertBooking(String.valueOf(uid), String.valueOf(sid), "1", services, time);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                output = response.body();
                assert output != null;
                if (output.equals("Successfully booked")){
                    success.setVisibility(View.VISIBLE);
                    pb.setVisibility(View.GONE);
                    tv.setVisibility(View.VISIBLE);
                    tv1.setVisibility(View.VISIBLE);
                    tv2.setVisibility(View.VISIBLE);
                    tv2.setText(name);
                    tv3.setVisibility(View.VISIBLE);
                    tv4.setVisibility(View.VISIBLE);
                    tv4.setText(time.substring(0, 19));
                    tv5.setVisibility(View.VISIBLE);
                    tv6.setVisibility(View.VISIBLE);
                    tv6.setText(services);
                    ll1.setVisibility(View.VISIBLE);
                    btn2.setVisibility(View.VISIBLE);
                    ((Animatable) success.getDrawable()).start();
                }
                Toast.makeText(getApplicationContext(), output, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    //function to insert booking in db using retrofit and show progress bar while loading and then hide it

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //go to home on back press
        Intent intent = new Intent(ServiceBooked.this, Homepage.class);
        intent.putExtra("uid", uid);
        startActivity(intent);
        //go to home on back press
        finishAffinity();
    }
}

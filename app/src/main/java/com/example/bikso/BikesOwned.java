package com.example.bikso;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BikesOwned extends AppCompatActivity {

    ImageView nobikeimg;
    TextView nobiketext;
    RecyclerView rvbike;
    Button add_bike;
    List<Bikes> bike;
    BikesOwnedAdapter adapter;
    int uid;

    //onclicklistener for recyclerview
    View.OnClickListener onClickListener = v -> {
        RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) v.getTag();
        int position = viewHolder.getAdapterPosition();
        Intent intent = new Intent(getBaseContext(), BikePage.class);
        intent.putExtra("name", bike.get(position).getName());
        intent.putExtra("make", bike.get(position).getMake());
        intent.putExtra("model", bike.get(position).getModel());
        intent.putExtra("year", bike.get(position).getYear_of_mfg());
        intent.putExtra("photo", bike.get(position).getPhoto1());
        intent.putExtra("bid", bike.get(position).getBid());
        intent.putExtra("uid", uid);
        startActivity(intent);
    };
    //onclicklistener for recyclerview

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bikes_owned);
        nobikeimg = findViewById(R.id.nobikeimg);
        nobiketext = findViewById(R.id.nobiketext);
        rvbike = findViewById(R.id.rvbike);
        add_bike = findViewById(R.id.add_bike);
        uid = getIntent().getIntExtra("uid", 0);

        //recyclerview layout and adapter set
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        rvbike.setLayoutManager(linearLayoutManager);
        rvbike.setItemAnimator(new DefaultItemAnimator());
        adapter = new BikesOwnedAdapter(bike);
        rvbike.setAdapter(adapter);
        //recyclerview layout and adapter set

        //on itemclicklistener recycler view call
        adapter.setOnItemClickListener(onClickListener);
        //on itemclicklistener recycler view call

        //add bike click listener
        add_bike.setOnClickListener(v -> {
            Intent intent = new Intent(BikesOwned.this, BikePage.class);
            intent.putExtra("add", 1);
            intent.putExtra("uid", uid);
            startActivity(intent);
        });
        //add bike click listener
    }

    @Override
    protected void onResume() {
        super.onResume();
        //getting bikes from db using retrofit
        Call<List<Bikes>> call = RetrofitInstance.getApiInstance().getBikes(String.valueOf(uid));
        call.enqueue(new Callback<List<Bikes>>() {
            @Override
            public void onResponse(Call<List<Bikes>> call, Response<List<Bikes>> response) {
                bike = response.body();
                Log.e("bike", bike+"");
                adapter.setItems(bike);
                adapter.notifyDataSetChanged();
                if(bike == null){
                    rvbike.setVisibility(View.GONE);
                    nobikeimg.setVisibility(View.VISIBLE);
                    nobiketext.setVisibility(View.VISIBLE);
                }
                else{
                    rvbike.setVisibility(View.VISIBLE);
                    nobikeimg.setVisibility(View.GONE);
                    nobiketext.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<Bikes>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        //getting bikes from db using retrofit
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(BikesOwned.this, Homepage.class);
        intent.putExtra("uid", uid);
        startActivity(intent);
        finishAffinity();
    }
}

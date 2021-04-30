package com.example.bikso;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingHistory extends AppCompatActivity {

    RadioGroup rgsort;
    RecyclerView rvhis;
    List<ServiceHistory> history;
    BookingHistoryAdapter adapter;
    ProgressBar pb1;
    int uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_history);
        rgsort = findViewById(R.id.rgsort);
        rvhis = findViewById(R.id.rvhis);
        pb1 = findViewById(R.id.pb1);
        uid = getIntent().getIntExtra("uid", 0);

        //recyclerview layout set and adapter set
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        rvhis.setLayoutManager(linearLayoutManager);
        rvhis.setItemAnimator(new DefaultItemAnimator());
        adapter = new BookingHistoryAdapter(history);
        rvhis.setAdapter(adapter);
        //recyclerview layout set and adapter set

        //getting history using retrofit
        Call<List<ServiceHistory>> call = RetrofitInstance.getApiInstance().getHistory(String.valueOf(uid));
        call.enqueue(new Callback<List<ServiceHistory>>() {
            @Override
            public void onResponse(Call<List<ServiceHistory>> call, Response<List<ServiceHistory>> response) {
                history = response.body();
                adapter.setItems(history);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<ServiceHistory>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        //getting history using retrofit

        //getting history according to timerange using retrofit and displaying progress bar
        rgsort.setOnCheckedChangeListener((group, checkedId) -> {
            pb1.setVisibility(View.VISIBLE);
            rvhis.setVisibility(View.GONE);
            RadioButton rb = findViewById(checkedId);
            Call<List<ServiceHistory>> call1 = RetrofitInstance.getApiInstance().getHistoryTimeRange(rb.getText().toString(), String.valueOf(uid));
            call1.enqueue(new Callback<List<ServiceHistory>>() {
                @Override
                public void onResponse(Call<List<ServiceHistory>> call2, Response<List<ServiceHistory>> response) {
                    history = response.body();
                    assert history != null;
                    if (history.get(0).getStatus() == 1) {
                        adapter.setItems(history);
                        adapter.notifyDataSetChanged();
                        rvhis.setVisibility(View.VISIBLE);
                        pb1.setVisibility(View.GONE);
                    }
                    else{
                        pb1.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), history.get(0).getNotfound(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<ServiceHistory>> call2, Throwable t) {
                    pb1.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
        //getting history according to timerange using retrofit and displaying progress bar
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(BookingHistory.this, Homepage.class);
        intent.putExtra("uid", uid);
        startActivity(intent);
        finishAffinity();
    }
}

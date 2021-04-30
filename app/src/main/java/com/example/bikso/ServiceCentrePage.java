package com.example.bikso;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServiceCentrePage extends AppCompatActivity {

    int position;
    List<ServiceCentre> centre;
    ArrayList<String> imagesurl;
    ArrayList<Reviews> review;
    ServiceCentrePageReviewsAdapter adapter1;
    ListView lv1;
    ViewPager images;
    LinearLayout dots;
    TextView name1, about;
    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 500;
    final long PERIOD_MS = 3000;
    private int dotscount;
    private ImageView[] dots1;
    Button proceed;
    int uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_centre_page);
        position = getIntent().getIntExtra("position", -1);
        uid = getIntent().getIntExtra("uid", 0);
        images = findViewById(R.id.images);
        dots = findViewById(R.id.dots);
        lv1 = findViewById(R.id.lv1);
        proceed = findViewById(R.id.proceed);

        //getting data from database using retrofit library by including "implementation 'com.squareup.retrofit2:converter-gson:2.8.1'" and "implementation 'com.squareup.picasso:picasso:2.71828'" in build.gradle(:app) dependencies
        Call<List<ServiceCentre>> call = RetrofitInstance.getApiInstance().getServiceCentre();
        call.enqueue(new Callback<List<ServiceCentre>>() {
            @Override
            public void onResponse(Call<List<ServiceCentre>> call, Response<List<ServiceCentre>> response) {
                centre = response.body();
                execute();
            }

            @Override
            public void onFailure(Call<List<ServiceCentre>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        //getting data from database using retrofit library by including "implementation 'com.squareup.retrofit2:converter-gson:2.8.1'" and "implementation 'com.squareup.picasso:picasso:2.71828'" in build.gradle(:app) dependencies

    }

    public void execute(){
        //setting header layout to listview
        View header = getLayoutInflater().inflate(R.layout.service_centre_page_listview_header, lv1, false);
        lv1.addHeaderView(header);
        name1 = header.findViewById(R.id.name1);
        about = header.findViewById(R.id.about);
        //setting header layout to listview

        name1.setText(centre.get(position).getName());
        about.setText(centre.get(position).getAbout());
        review = centre.get(position).getReviews();

        //for reviews listview adapter
        adapter1 = new ServiceCentrePageReviewsAdapter(getApplicationContext(), review);
        lv1.setAdapter(adapter1);
        //for reviews listview adapter

        //for checking number of photos in database
        imagesurl = new ArrayList<>();
        if (centre.get(position).getPhoto5() != null) {
            imagesurl.add(centre.get(position).getPhoto1());
            imagesurl.add(centre.get(position).getPhoto2());
            imagesurl.add(centre.get(position).getPhoto3());
            imagesurl.add(centre.get(position).getPhoto4());
            imagesurl.add(centre.get(position).getPhoto5());
        }
        else if (centre.get(position).getPhoto4() != null){
            imagesurl.add(centre.get(position).getPhoto1());
            imagesurl.add(centre.get(position).getPhoto2());
            imagesurl.add(centre.get(position).getPhoto3());
            imagesurl.add(centre.get(position).getPhoto4());
        }
        else if (centre.get(position).getPhoto3() != null){
            imagesurl.add(centre.get(position).getPhoto1());
            imagesurl.add(centre.get(position).getPhoto2());
            imagesurl.add(centre.get(position).getPhoto3());
        }
        else if (centre.get(position).getPhoto2() != null){
            imagesurl.add(centre.get(position).getPhoto1());
            imagesurl.add(centre.get(position).getPhoto2());
        }
        else{
            imagesurl.add(centre.get(position).getPhoto1());
        }
        //for checking number of photos in database

        //for horizontal image scrolling adapter
        PagerAdapter adapter = new ServiceCentrePageViewPagerAdapter(ServiceCentrePage.this, imagesurl);
        images.setAdapter(adapter);
        //for horizontal image scrolling adapter

        //proceed button onclicklistener
        proceed.setOnClickListener(v -> {
            Intent intent = new Intent(ServiceCentrePage.this, ServicesSelection.class);
            intent.putExtra("sid", centre.get(position).getSid());
            intent.putExtra("timeslots", centre.get(position).getTimeslots());
            intent.putExtra("position", position);
            intent.putExtra("name", centre.get(position).getName());
            intent.putExtra("uid", uid);
            startActivity(intent);
        });
        //proceed button onclicklistener

        //Setting number of dots according to number of images and changing colour of dots according to the image which is selected
        dotscount = adapter.getCount();
        dots1 = new ImageView[dotscount];

        for(int i = 0; i < dotscount; i++){
            dots1[i] = new ImageView(this);
            dots1[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(8, 0, 8, 0);
            dots.addView(dots1[i], params);
        }

        dots1[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));

        images.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for(int i = 0; i< dotscount; i++){
                    dots1[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));
                }
                dots1[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //Setting number of dots according to number of images and changing colour of dots according to the image which is selected

        //timer for image scrolling in viewpager
        final Handler handler = new Handler();
        final Runnable Update = () -> {
            if (currentPage == 4) {
                currentPage = 0;
            }
            images.setCurrentItem(currentPage++, true);
        };
        timer = new Timer();
        timer.schedule(new TimerTask() { // task to be scheduled
            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY_MS, PERIOD_MS);
        //timer for image scrolling in viewpager
    }
}

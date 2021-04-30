package com.example.bikso;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Homepage extends AppCompatActivity implements LocationListener{

    RecyclerView rv;
    List<ServiceCentre> centre;
    HomepageAdapter adapter;
    Double clat, clon, nclat, nclon;
    int PERMISSION_ID = 44;
    boolean doubleBackToExitPressedOnce = false;
    LocationManager locationManager;
    DrawerLayout dl;
    NavigationView nv;
    ActionBarDrawerToggle actionBarDrawerToggle;
    int uid;

    //onclicklistener of recycler view
    View.OnClickListener onClickListener = v -> {
        RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) v.getTag();
        int position = viewHolder.getAdapterPosition();
        Intent intent = new Intent(Homepage.this, ServiceCentrePage.class);
        intent.putExtra("position", position);
        intent.putExtra("uid", uid);
        startActivity(intent);
    };
    //onclicklistener of recycler view

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        rv = findViewById(R.id.rv);
        dl = findViewById(R.id.dl);
        nv = findViewById(R.id.nv);
        uid = getIntent().getIntExtra("uid", 0);

        //setting layout and adapter to recycler view
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        rv.setLayoutManager(linearLayoutManager);
        rv.setItemAnimator(new DefaultItemAnimator());
        adapter = new HomepageAdapter(centre);
        rv.setAdapter(adapter);
        adapter.setOnItemClickListener(onClickListener);
        //setting layout and adapter to recycler view

        //for navigation menu
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, dl, 0, 0);
        dl.addDrawerListener(actionBarDrawerToggle);
        //for navigation menu

        //getting current latitude and longitude value
        if (checkPermission()) {
            if (isLocationEnabled()) {
                locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                assert locationManager != null;
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 5, this);
            }
            else {
                Toast.makeText(getApplicationContext(), "Turn on location", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        }
        else {
            requestPermissions();
        }
        //getting current latiitude and longitude value

        //navigation menu onitemclicklistener
        nv.setNavigationItemSelectedListener(menuItem -> {
            int id = menuItem.getItemId();
            if (id == R.id.item){
                Intent intent = new Intent(Homepage.this, BookingHistory.class);
                intent.putExtra("uid", uid);
                startActivity(intent);
            }
            else if (id == R.id.item1){
                Intent intent = new Intent(Homepage.this, BikesOwned.class);
                intent.putExtra("uid", uid);
                startActivity(intent);
            }
            else if (id == R.id.item2){
                Intent intent = new Intent(Homepage.this, Profile.class);
                startActivity(intent);
            }
            else if (id == R.id.item3){

            }
            else if (id == R.id.item4){

            }
            else{
                Intent intent = new Intent(Homepage.this, Login.class);
                Shared shared = new Shared(getApplicationContext(), 0);
                shared.logout();
                startActivity(intent);
                finishAffinity();
            }
            dl.closeDrawer(GravityCompat.START);
            return true;
        });
        //navigation menu onitemclicklistener
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        //for navigation menu
        actionBarDrawerToggle.syncState();
        //for navigation menu
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //for navigation menu
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
        //for navigation menu
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //for navigation menu
        if (actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        //for navigation menu
        return super.onOptionsItemSelected(item);
    }

    //getting current latitude and longitude methods
    @Override
    public void onLocationChanged(Location location) {
        clat = location.getLatitude();
        clon = location.getLongitude();
        BigDecimal bd = new BigDecimal(clat).setScale(6, RoundingMode.HALF_UP);
        nclat = bd.doubleValue();
        BigDecimal bd1 = new BigDecimal(clon).setScale(6,RoundingMode.HALF_UP);
        nclon = bd1.doubleValue();
    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }
    //getting current latitude and longitude methods

    //checking location permission requirements methods
    private boolean checkPermission() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ID);
    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        assert locationManager != null;
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                assert locationManager != null;
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 5, this);
            }
            else {
                Toast.makeText(getApplicationContext(), "Permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }
    //checking location permission requirements methods

    @Override
    public void onResume(){
        super.onResume();
        //checking location permission requirements methods
        if (checkPermission()) {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            assert locationManager != null;
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 5, this);
        }
        //checking location permission requirements methods

        //getting data from database using retrofit library by including "implementation 'com.squareup.retrofit2:converter-gson:2.8.1'" and "implementation 'com.squareup.picasso:picasso:2.71828'" in build.gradle(:app) dependencies
        Call<List<ServiceCentre>> call = RetrofitInstance.getApiInstance().getServiceCentre();
        call.enqueue(new Callback<List<ServiceCentre>>() {
            @Override
            public void onResponse(Call<List<ServiceCentre>> call, Response<List<ServiceCentre>> response) {
                centre = response.body();
                adapter.setItems(centre);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<ServiceCentre>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        //getting data from database using retrofit library by including "implementation 'com.squareup.retrofit2:converter-gson:2.8.1'" and "implementation 'com.squareup.picasso:picasso:2.71828'" in build.gradle(:app) dependencies
    }

    @Override
    public void onBackPressed() {
        //for navigation menu
        if (dl.isDrawerOpen(GravityCompat.START)){
            dl.closeDrawer(GravityCompat.START);
        }
        //for navigation menu
        else {
            //for double back press exit
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
            }
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(() -> doubleBackToExitPressedOnce = false, 2000);
            //for double back press exit
        }
    }
}

package com.example.bikso;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ServiceCentrePageViewPagerAdapter extends PagerAdapter {

    private Activity activity;
    private ArrayList<String> imagesurl;

    ServiceCentrePageViewPagerAdapter(Activity activity, ArrayList<String> imagesurl) {
        this.activity = activity;
        this.imagesurl = imagesurl;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = (activity).getLayoutInflater();
        View viewItem = inflater.inflate(R.layout.service_centre_page_imageview, container, false);
        ImageView img = viewItem.findViewById(R.id.img);
        Picasso.get().load(RetrofitInstance.url + "Images/" + imagesurl.get(position)).into(img);
        container.addView(viewItem);
        return viewItem;
    }

    @Override
    public int getCount() {
        return imagesurl.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}

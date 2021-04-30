package com.example.bikso;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ServiceCentrePageReviewsAdapter extends BaseAdapter {

    private ArrayList<Reviews> review;
    private LayoutInflater inflater;

    ServiceCentrePageReviewsAdapter(Context context, ArrayList<Reviews> review) {
        this.review = review;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return review.size();
    }

    @Override
    public Object getItem(int position) {
        return review.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.service_centre_page_listview, parent, false);
        }
        TextView name2 = convertView.findViewById(R.id.name2);
        name2.setText(review.get(position).getName());
        TextView time = convertView.findViewById(R.id.time);
        //getting time ago by adding "implementation 'org.ocpsoft.prettytime:prettytime:4.0.4.Final'" to build.gradle(:app) dependencies
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss", Locale.getDefault());
        Date convertedDate = new Date();
        try {
            convertedDate = dateFormat.parse(review.get(position).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        PrettyTime p  = new PrettyTime();
        //getting time ago by adding "implementation 'org.ocpsoft.prettytime:prettytime:4.0.4.Final'" to build.gradle(:app) dependencies
        time.setText(p.format(convertedDate));
        RatingBar stars1 = convertView.findViewById(R.id.stars1);
        stars1.setRating(Float.parseFloat(review.get(position).getStars()));
        TextView description = convertView.findViewById(R.id.description);
        description.setText(review.get(position).getReview());
        return convertView;
    }
}

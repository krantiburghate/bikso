package com.example.bikso;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class HomepageAdapter extends RecyclerView.Adapter<HomepageAdapter.ViewHolder> {

    private List<ServiceCentre> centres;
    private View.OnClickListener onClickListener;

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView name, authorisation, location;
        ImageView photo;
        RatingBar stars;
        String author;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            author = itemView.getResources().getString(R.string.homepage_recyclerview_relativelayout_linearlayout_relativelayout_textview2_text);
            name = itemView.findViewById(R.id.name);
            authorisation = itemView.findViewById(R.id.authorisation);
            location = itemView.findViewById(R.id.location);
            photo = itemView.findViewById(R.id.photo);
            stars = itemView.findViewById(R.id.stars);
            itemView.setTag(this);
            itemView.setOnClickListener(onClickListener);
        }
    }

    HomepageAdapter(List<ServiceCentre> centres) {
        this.centres = centres;
    }

    void setItems(List<ServiceCentre> centres){
        this.centres = centres;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.homepage_recyclerview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(centres.get(position).getName());
        if (centres.get(position).getAuthorisation().equals("1")){
            holder.authorisation.setVisibility(View.VISIBLE);
            holder.authorisation.setText(holder.author);
        }
        Picasso.get().load(RetrofitInstance.url + "Images/" + centres.get(position).getPhoto1()).into(holder.photo);
        String tempstars = centres.get(position).getStars();
        if (tempstars == null){
            holder.stars.setRating(0);
        }
        else{
            double dtempstars = Double.parseDouble(tempstars);
            BigDecimal bd = new BigDecimal(dtempstars).setScale(1, RoundingMode.HALF_UP);
            double fstars = bd.doubleValue();
            holder.stars.setRating((float) fstars);
        }
        holder.location.setText("Location Name (Distance)");
    }

    @Override
    public int getItemCount() {
        if (centres == null){
            return 0;
        }
        else{
            return centres.size();
        }
    }

    void setOnItemClickListener(View.OnClickListener itemClickListener){
        onClickListener = itemClickListener;
    }
}

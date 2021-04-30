package com.example.bikso;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BikesOwnedAdapter extends RecyclerView.Adapter<BikesOwnedAdapter.ViewHolder> {

    private List<Bikes> bike;
    private View.OnClickListener onItemClickListener;

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView bname;
        ImageView bimage;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            bname = itemView.findViewById(R.id.bname);
            bimage = itemView.findViewById(R.id.bimage);
            itemView.setTag(this);
            itemView.setOnClickListener(onItemClickListener);
        }
    }

    BikesOwnedAdapter(List<Bikes> bike) {
        this.bike = bike;
    }

    void setItems(List<Bikes> bike){
        this.bike = bike;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bikes_owned_recyclerview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bname.setText(bike.get(position).getName());
        Picasso.get().load(RetrofitInstance.url + "Images/" + bike.get(position).getPhoto1()).memoryPolicy(MemoryPolicy.NO_CACHE).networkPolicy(NetworkPolicy.NO_CACHE).into(holder.bimage);
    }

    @Override
    public int getItemCount() {
        if (bike == null){
            return 0;
        }
        else{
            return bike.size();
        }
    }

    void setOnItemClickListener(View.OnClickListener itemClickListener){
        onItemClickListener = itemClickListener;
    }
}

package com.example.bikso;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class BookingHistoryAdapter extends RecyclerView.Adapter<BookingHistoryAdapter.ViewHolder> {

    private List<ServiceHistory> history;

    static class ViewHolder extends RecyclerView.ViewHolder{

        TextView name3, time1, amount;
        String rupeesymbol;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            name3 = itemView.findViewById(R.id.name3);
            time1 = itemView.findViewById(R.id.time1);
            amount = itemView.findViewById(R.id.amount);
            rupeesymbol = itemView.getResources().getString(R.string.Rs);
        }
    }

    BookingHistoryAdapter(List<ServiceHistory> history) {
        this.history = history;
    }

    void setItems(List<ServiceHistory> history){
        this.history = history;
    }

    @NonNull
    @Override
    public BookingHistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.booking_history_recyclerview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingHistoryAdapter.ViewHolder holder, int position) {
        //checking if service done or only booked
        if (history.get(position).getCurr_status().equals("BOOKED")){
            String time = "Today between " + history.get(position).getTime_selected();
            holder.name3.setText(history.get(position).getSname());
            holder.time1.setText(time);
        }
        else{
            holder.amount.setVisibility(View.VISIBLE);
            //pretty time implementation for showing months ago
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss", Locale.getDefault());
            Date convertedDate = new Date();
            try {
                convertedDate = dateFormat.parse(history.get(position).getTime_done());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            PrettyTime p  = new PrettyTime();
            //pretty time implementation for showing months ago
            holder.name3.setText(history.get(position).getSname());
            holder.time1.setText(p.format(convertedDate));
            holder.amount.setText(String.format("%s%s", holder.rupeesymbol, history.get(position).getAmount()));
        }
        //checking if service done or only booked
    }

    @Override
    public int getItemCount() {
        if (history == null){
            return 0;
        }
        else{
            return history.size();
        }
    }
}

package com.example.bikso;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class TimeSlotSelection extends AppCompatActivity {

    String services, name;
    int position, sid;
    String[] timeslots;
    RadioGroup rg;
    Button book;
    int uid;
    //get bid here by using fragment here

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_slot_selection);
        services = getIntent().getStringExtra("services");
        position = getIntent().getIntExtra("position", -1);
        sid = getIntent().getIntExtra("sid", -1);
        timeslots = getIntent().getStringArrayExtra("timeslots");
        name = getIntent().getStringExtra("name");
        uid = getIntent().getIntExtra("uid", 0);
        rg = findViewById(R.id.rg);
        book = findViewById(R.id.book);

        //assigning the available time slots as radio button
        for (String timeslot : timeslots) {
            String newtimeslot = timeslot.replace(timeslot.substring(19), "");
            RadioButton rb = new RadioButton(this);
            rb.setText(newtimeslot);
            rg.addView(rb);
        }
        //assigning the available time slots as radio button

        //proceed button onclicklistener
        book.setOnClickListener(v -> execute());
        //proceed button onclicklistener
    }

    //function to get selected time slot and check if an item is selected or not
    void execute(){
        if (rg.getCheckedRadioButtonId() != -1){
            int selectedid = rg.getCheckedRadioButtonId();
            RadioButton rb1 = findViewById(selectedid);
            int index = rg.indexOfChild(rb1);
            Intent intent = new Intent(TimeSlotSelection.this, ServiceBooked.class);
            intent.putExtra("sid", sid);
            intent.putExtra("services", services);
            intent.putExtra("time", timeslots[index]);
            intent.putExtra("position", position);
            intent.putExtra("name", name);
            intent.putExtra("uid", uid);
            startActivity(intent);
        }
        else{
            Toast.makeText(this, "Please select a time slot", Toast.LENGTH_SHORT).show();
        }
    }
    //function to get selected time slot and check if an item is selected or not
}

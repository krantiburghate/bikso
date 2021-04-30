package com.example.bikso;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ServicesSelection extends AppCompatActivity {

    String services, name;
    int position, sid;
    String[] timeslots;
    CheckBox cb, cb1, cb2, cb3;
    EditText others;
    Button book_time;
    int uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services_selection);
        sid = getIntent().getIntExtra("sid", -1);
        timeslots = getIntent().getStringArrayExtra("timeslots");
        position = getIntent().getIntExtra("position", -1);
        name = getIntent().getStringExtra("name");
        uid = getIntent().getIntExtra("uid", 0);
        cb = findViewById(R.id.cb);
        cb1 = findViewById(R.id.cb1);
        cb2 = findViewById(R.id.cb2);
        cb3 = findViewById(R.id.cb3);
        others = findViewById(R.id.others);
        book_time = findViewById(R.id.book_time);
        services = "";

        //proceed button onclicklistener
        book_time.setOnClickListener(v -> sendData());
        //proceed button onclicklistener

        //making others edittext visible according to others checkbox
        cb3.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked){
                others.setVisibility(View.VISIBLE);
            }
            else{
                others.setVisibility(View.GONE);
                others.setText("");
            }
        });
        //making others edittext visible according to others checkbox
    }

    //function to check whether any field is selected or not and store the selected services in a string
    void sendData(){
        if (cb.isChecked()){
            services = services + cb.getText();
        }
        if (cb1.isChecked()){
            if (services.equals("")){
                services = services + cb1.getText();
            }
            else{
                services = services + "\n" + cb1.getText();
            }
        }
        if (cb2.isChecked()){
            if (services.equals("")){
                services = services + cb2.getText();
            }
            else{
                services = services + "\n" + cb2.getText();
            }
        }
        if (cb3.isChecked()){
            if (!("").contentEquals(others.getText())) {
                if (services.equals("")) {
                    services = services + others.getText();
                } else {
                    services = services + "\n" + others.getText();
                }
            }
            else{
                Toast.makeText(this, "Specify other services", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        if (services.equals("")){
            Toast.makeText(this, "Please select a service", Toast.LENGTH_SHORT).show();
        }
        else {
            Intent intent = new Intent(ServicesSelection.this, TimeSlotSelection.class);
            intent.putExtra("sid", sid);
            intent.putExtra("services", services);
            intent.putExtra("timeslots", timeslots);
            intent.putExtra("position", position);
            intent.putExtra("name", name);
            intent.putExtra("uid", uid);
            startActivity(intent);
        }
    }
    //function to check whether any field is selected or not and store the selected services in a string

    @Override
    protected void onResume() {
        super.onResume();
        //setting services stored string null and all checkbox uncheck
        services = "";
        cb.setChecked(false);
        cb1.setChecked(false);
        cb2.setChecked(false);
        cb3.setChecked(false);
        others.setText("");
        //setting services stored string null and all checkbox uncheck
    }
}

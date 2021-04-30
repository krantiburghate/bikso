package com.example.bikso;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BikePage extends AppCompatActivity {

    ImageView bimg;
    EditText bnameedit, byearedit;
    Spinner bmakeedit, bmodeledit;
    MaterialButton modcon, chngimg;
    String name, make, model, year, photo, todo = "update";
    String[] company, models;
    int bid, addstat, gallery = 1;
    Uri imguri;
    int uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bike_page);
        bimg = findViewById(R.id.bimg);
        chngimg = findViewById(R.id.chngimg);
        bnameedit = findViewById(R.id.bnameedit);
        byearedit = findViewById(R.id.byearedit);
        bmakeedit = findViewById(R.id.bmakeedit);
        bmodeledit = findViewById(R.id.bmodeledit);
        modcon = findViewById(R.id.modcon);
        name = getIntent().getStringExtra("name");
        make = getIntent().getStringExtra("make");
        model = getIntent().getStringExtra("model");
        year = getIntent().getStringExtra("year");
        photo = getIntent().getStringExtra("photo");
        bid = getIntent().getIntExtra("bid", 0);
        addstat = getIntent().getIntExtra("add", 0);
        uid = getIntent().getIntExtra("uid", 0);
        getMakeModel();

        //image change button onclicklistener
        chngimg.setOnClickListener(v -> {
            Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(galleryIntent, gallery);
        });
        //image change button onclicklistener

        //set modify button on click listener and check whether on view screen or edit screen and set views accordingly
        modcon.setOnClickListener(v -> {
            if (bimg.getDrawable() == getResources().getDrawable(R.drawable.ic_bike_logo) || bnameedit.getText().toString().equals("") || bmakeedit.getSelectedItem().toString().equals("Select make") || bmodeledit.getSelectedItem().toString().equals("Select model") || byearedit.getText().toString().equals("")){
                Toast.makeText(getApplicationContext(), "Require all fields", Toast.LENGTH_SHORT).show();
            }
            else if (addstat == 0){
                if (imguri == null){
                    Call<String> call = RetrofitInstance.getApiInstance().updateBike(String.valueOf(bid), bnameedit.getText().toString(), bmakeedit.getSelectedItem().toString(), bmodeledit.getSelectedItem().toString(), byearedit.getText().toString());
                    call.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            Toast.makeText(getApplicationContext(), response.body(), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(BikePage.this, BikesOwned.class);
                            intent.putExtra("uid", uid);
                            startActivity(intent);
                            finish();
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else{
                    File file = UriToFilePath.getFile(this, imguri);
                    RequestBody requestBody = RequestBody.create(MediaType.parse(Objects.requireNonNull(getContentResolver().getType(imguri))), file);MultipartBody.Part bike = MultipartBody.Part.createFormData("bike", file.getName(), requestBody);
                    RequestBody rb_bid = RequestBody.create(MultipartBody.FORM, String.valueOf(bid));
                    RequestBody rb_name = RequestBody.create(MultipartBody.FORM, bnameedit.getText().toString());
                    RequestBody rb_make = RequestBody.create(MultipartBody.FORM, bmakeedit.getSelectedItem().toString());
                    RequestBody rb_model = RequestBody.create(MultipartBody.FORM, bmodeledit.getSelectedItem().toString());
                    RequestBody rb_year = RequestBody.create(MultipartBody.FORM, byearedit.getText().toString());
                    RequestBody rb_photo = RequestBody.create(MultipartBody.FORM, photo);
                    Call<String> call = RetrofitInstance.getApiInstance().updateBikePhoto(bike, rb_bid, rb_name, rb_make, rb_model, rb_year, rb_photo);
                    call.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            Toast.makeText(getApplicationContext(), response.body(), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(BikePage.this, BikesOwned.class);
                            intent.putExtra("uid", uid);
                            startActivity(intent);
                            finish();
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
            else if (addstat == 1){
                File file = UriToFilePath.getFile(this, imguri);
                RequestBody requestBody = RequestBody.create(MediaType.parse(Objects.requireNonNull(getContentResolver().getType(imguri))), file);MultipartBody.Part bike = MultipartBody.Part.createFormData("bike", file.getName(), requestBody);
                RequestBody rb_uid = RequestBody.create(MultipartBody.FORM, String.valueOf(uid));
                RequestBody rb_name = RequestBody.create(MultipartBody.FORM, bnameedit.getText().toString());
                RequestBody rb_make = RequestBody.create(MultipartBody.FORM, bmakeedit.getSelectedItem().toString());
                RequestBody rb_model = RequestBody.create(MultipartBody.FORM, bmodeledit.getSelectedItem().toString());
                RequestBody rb_year = RequestBody.create(MultipartBody.FORM, byearedit.getText().toString());
                Call<String> call = RetrofitInstance.getApiInstance().insertBike(bike, rb_uid, rb_name, rb_make, rb_model, rb_year);
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Toast.makeText(getApplicationContext(), response.body(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(BikePage.this, BikesOwned.class);
                        intent.putExtra("uid", uid);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        //set modify button on click listener and check whether on view screen or edit screen and set views accordingly
    }

    //function to edit views on edit screen and set text items in them
    void getMakeModel(){
        //get all companies in dropdown
        Call<String[]> call = RetrofitInstance.getApiInstance().getBikeCompanies();
        call.enqueue(new Callback<String[]>() {
            @Override
            public void onResponse(Call<String[]> call, Response<String[]> response) {
                company = response.body();
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, company);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                bmakeedit.setAdapter(arrayAdapter);
                //get all companies in dropdown
                if (addstat == 0){
                    Picasso.get().load(RetrofitInstance.url + "Images/" + photo).memoryPolicy(MemoryPolicy.NO_CACHE).networkPolicy(NetworkPolicy.NO_CACHE).into(bimg);
                    chngimg.setText(getResources().getString(R.string.activity_bike_page_relativelayout_materialbutton_text));
                    bnameedit.setText(name);
                    int position = arrayAdapter.getPosition(make);
                    bmakeedit.setSelection(position);
                    byearedit.setText(year);
                }
                else{
                    bimg.setImageDrawable(getDrawable(R.drawable.ic_bike_logo));
                    chngimg.setText(getResources().getString(R.string.activity_bike_page_relativelayout_materialbutton_text2));
                }
            }

            @Override
            public void onFailure(Call<String[]> call, Throwable t) {

            }
        });
        //get model according to selected company using retrofit
        bmakeedit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Call<String[]> call1 = RetrofitInstance.getApiInstance().getBikeModels(company[position]);
                call1.enqueue(new Callback<String[]>() {
                    @Override
                    public void onResponse(Call<String[]> call, Response<String[]> response) {
                        models = response.body();
                        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, models);
                        arrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        bmodeledit.setAdapter(arrayAdapter1);
                        int position = arrayAdapter1.getPosition(model);
                        bmodeledit.setSelection(position);
                    }

                    @Override
                    public void onFailure(Call<String[]> call, Throwable t) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //get model according to selected company using retrofit
    }
    //function to edit views on edit screen and set text items in them

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //getting image from gallery and showing in imageview when activity opens
        if (resultCode == RESULT_CANCELED){
            return;
        }
        if (requestCode == gallery){
            if (data != null){
                imguri = data.getData();
                bimg.setImageURI(imguri);
                chngimg.setText(getResources().getString(R.string.activity_bike_page_relativelayout_materialbutton_text));
            }
        }
        //getting image from gallery and showing in imageview when activity opens
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(BikePage.this);
        builder.setMessage("Discard changes?");
        builder.setTitle("Cancel !");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", (dialog, which) -> {
            Intent intent = new Intent(BikePage.this, BikesOwned.class);
            intent.putExtra("uid", uid);
            startActivity(intent);
            finish();
        });
        builder.setNegativeButton("No", (dialog, which) -> dialog.cancel());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}

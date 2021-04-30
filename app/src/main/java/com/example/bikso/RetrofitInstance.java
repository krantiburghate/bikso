package com.example.bikso;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

class RetrofitInstance {

    static String url = "http://192.168.43.161:8080/Bikso/";

    //method for getting api
    static Api getApiInstance(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build();
        return retrofit.create(Api.class);
    }
    //method for getting api
}

package com.example.bikso;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface Api {

    //getting all service centres in homepage
    @GET("get_all_service_centres.php")
    Call<List<ServiceCentre>> getServiceCentre();

    //booking a service
    @FormUrlEncoded
    @POST("insert_service_history.php")
    Call<String> insertBooking(@Field("uid") String uid, @Field("sid") String sid, @Field("bid") String bid, @Field("services_selected") String services_selected, @Field("time_selected") String time_selected);

    //getting service history in history
    @FormUrlEncoded
    @POST("get_service_history.php")
    Call<List<ServiceHistory>> getHistory(@Field("uid") String uid);

    //getting service history by time range in history
    @FormUrlEncoded
    @POST("get_service_history_time_range.php")
    Call<List<ServiceHistory>> getHistoryTimeRange(@Field("time") String time, @Field("uid") String uid);

    //getting all bikes in my bikes
    @FormUrlEncoded
    @POST("get_bikes.php")
    Call<List<Bikes>> getBikes(@Field("uid") String uid);

    //getting all the companies of bikes for spinner in bikepage
    @GET("get_bike_company_model.php")
    Call<String[]> getBikeCompanies();

    //getting all the models of a particular company for spinner in bikepage
    @FormUrlEncoded
    @POST("get_bike_company_model.php")
    Call<String[]> getBikeModels(@Field("cname") String cname);

    //update bike without photo
    @FormUrlEncoded
    @POST("update_bike_leave_photo.php")
    Call<String> updateBike(@Field("bid") String bid, @Field("name") String name, @Field("make") String make, @Field("model") String model, @Field("year_of_mfg") String year);

    //update bike with photo
    @Multipart
    @POST("update_bike_photo.php")
    Call<String> updateBikePhoto(@Part MultipartBody.Part bike, @Part("bid") RequestBody bid, @Part("name") RequestBody name, @Part("make") RequestBody make, @Part("model") RequestBody model, @Part("year_of_mfg") RequestBody year_of_mfg, @Part("photo1") RequestBody photo1);

    //insert bike
    @Multipart
    @POST("insert_bike.php")
    Call<String> insertBike(@Part MultipartBody.Part bike, @Part("uid") RequestBody uid, @Part("name") RequestBody name, @Part("make") RequestBody make, @Part("model") RequestBody model, @Part("year_of_mfg") RequestBody year_of_mfg);

    //adding a user
    @FormUrlEncoded
    @POST("insert_user.php")
    Call<String> insertUser(@Field("sname") String sname, @Field("semail") String semail, @Field("smob") String smob, @Field("spass") String spass);

    //login user
    @FormUrlEncoded
    @POST("check_user_login.php")
    Call<String> checkUser(@Field("username") String username, @Field("password") String password);
}

package com.example.herokuapp.retrofit;

import com.example.herokuapp.model.DataProfile;
import com.example.herokuapp.model.DataReports;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface Services {

    @GET("reports")
    Call<List<DataReports>> getReports();

    @Multipart
    @POST("reports")
    Call<DataProfile> uploadProfile(
            @Part("description") RequestBody description,
            @Part MultipartBody.Part image);

}

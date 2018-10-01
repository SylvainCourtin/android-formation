package com.example.courtin.myapplication.service;

import com.example.courtin.myapplication.models.User;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Service {

    @GET("/login/{username}/{password}")
    public Call<ResponseBody> login(@Path("username") String username, @Path("password") String password);

    @POST("/signin")
    public Call<ResponseBody> signin(@Body User user);

    @POST("/api/messages")
    public Call<ResponseBody> sendMessage(@Header("Authorization")String authKey, @Body String message);
}

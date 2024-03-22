package com.example.less3.retrofit;

import com.example.less3.model.Sinhvien;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {

    @GET("/api")
    Call<List<Sinhvien>> getSinhviens();
    @POST("/add_sv")
    Call<Void> createSinhViens(@Body Sinhvien sv);

    @PUT("/update/{id}")
    Call<Sinhvien> updateSinhViens(@Path("id") String id, @Body Sinhvien sv);

    @DELETE("/delete/{id}")
    Call<Void> deleteSinhViens(@Path("id") String id);

}

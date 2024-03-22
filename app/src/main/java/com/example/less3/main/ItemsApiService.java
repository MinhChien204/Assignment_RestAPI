package com.example.less3.main;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ItemsApiService {
    @GET("photos")
    Call<List<Items>> getItems();

    @GET("todos")
    Call<List<ItemTodo>> getItemsTodo();


}

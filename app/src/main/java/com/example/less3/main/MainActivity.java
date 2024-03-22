package com.example.less3.main;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.example.less3.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private ListView mListView;
    private List<Items> mList=new ArrayList<>();
    private CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListView = findViewById(R.id.lvCustomListView);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        ItemsApiService apiService = retrofit.create(ItemsApiService.class);
        Call<List<Items>> call = apiService.getItems();
        call.enqueue(new Callback<List<Items>>() {

            @Override
            public void onResponse(Call<List<Items>> call, Response<List<Items>> response) {
                if (response.isSuccessful()) {
                    // thu vien gson
                    mList = response.body();
                    Log.i("Sync","Sucess");
                    Log.i("Sync",String.valueOf(mList.size()));
                    customAdapter = new CustomAdapter(MainActivity.this,mList);
                    mListView.setAdapter(customAdapter);
                } else {
                    Log.i("Sync","Fail");
                }

            }

            @Override
            public void onFailure(Call<List<Items>> call, Throwable t) {

            }
        });

        Log.i("INT", String.valueOf(mList.size()));



    }

    private void syncData() {


    }
}
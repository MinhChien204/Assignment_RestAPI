package com.example.less3;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.less3.adapter.SinhvienAdapter;
import com.example.less3.model.Sinhvien;
import com.example.less3.retrofit.ApiService;
import com.example.less3.retrofit.Config;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TrangChu extends AppCompatActivity {
    Dialog dialog;
    ListView listView;

    List<Sinhvien> list;

    SinhvienAdapter sinhvienAdapter;
    FloatingActionButton fab;
    ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trang_chu);

        listView = findViewById(R.id.lvTrangChu);
        fab = findViewById(R.id.fabAddsv);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Config.ip)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);

        loadData();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TrangChu.this,AddStudent.class);
                startActivity(intent);
            }
        });

    }

    void  loadData(){
        Call<List<Sinhvien>> call = apiService.getSinhviens();

        call.enqueue(new Callback<List<Sinhvien>>() {
            @Override
            public void onResponse(Call<List<Sinhvien>> call, Response<List<Sinhvien>> response) {
                if (response.isSuccessful()) {
                    list = response.body();

                    sinhvienAdapter = new SinhvienAdapter(TrangChu.this, list,TrangChu.this);

                    listView.setAdapter(sinhvienAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Sinhvien>> call, Throwable t) {

            }
        });
    }

    public void xoa(String id){
        Call<Void> call = apiService.deleteSinhViens(id);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    loadData();
                    Toast.makeText(TrangChu.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(TrangChu.this, "Xóa fail", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Xảy ra lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
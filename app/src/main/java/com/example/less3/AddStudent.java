package com.example.less3;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.less3.model.Sinhvien;
import com.example.less3.retrofit.ApiService;
import com.example.less3.retrofit.Config;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddStudent extends AppCompatActivity {

    EditText edtten, edttuoi, edtmssv;
    Button btnLuu, btncancel;
    Sinhvien item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);
        edtten = findViewById(R.id.addName);
        edttuoi = findViewById(R.id.addAge);
        edtmssv = findViewById(R.id.addMSSV);
        btnLuu = findViewById(R.id.btnSaveSV);
        btncancel = findViewById(R.id.btnCancelSV);
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAddStudent();
            }
        });
        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddStudent.this, TrangChu.class);
                startActivity(intent);
            }
        });
    }

    private void onAddStudent() {
        String svten = edtten.getText().toString().trim();
        Integer svtuoi = Integer.parseInt(edttuoi.getText().toString().trim());
        String masv = edtmssv.getText().toString().trim();

        if (!svten.isEmpty() && !masv.isEmpty()) {
            item = new Sinhvien();
            item.setName(svten);
            item.setTuoi(svtuoi);
            item.setMssv(masv);

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Config.ip)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            ApiService apiService = retrofit.create(ApiService.class);

            Call<Void> call = apiService.createSinhViens(item);  // Sử dụng item để truyền dữ liệu sinh viên cần thêm
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        // Thêm sinh viên thành công
                        Intent mainIntent = new Intent(AddStudent.this, TrangChu.class);
//                        mainIntent.putExtra("userRole", "admin");
                        startActivity(mainIntent);
                        // Đóng màn hình sau khi thêm xong
                        finish();
                    } else {
                        Toast.makeText(AddStudent.this, "Có lỗi khi thêm sinh viên", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(AddStudent.this, "Lỗi kết nối", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
        }
    }


}
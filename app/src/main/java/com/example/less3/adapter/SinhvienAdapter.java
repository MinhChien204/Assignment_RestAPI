package com.example.less3.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.less3.R;
import com.example.less3.TrangChu;
import com.example.less3.model.Sinhvien;
import com.example.less3.retrofit.ApiService;
import com.example.less3.retrofit.Config;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SinhvienAdapter extends BaseAdapter {


    Context context;
    List<Sinhvien> mList = new ArrayList<>();
    Dialog dialog;
    Sinhvien item;

    TrangChu trangChu;

    public SinhvienAdapter(Activity activity, List<Sinhvien> mList, TrangChu trangChu) {
        this.context = activity;
        this.mList = mList;
        this.trangChu = trangChu;
    }

    @Override
    public int getCount() {
        return mList.size();
    }


    @Override
    public Object getItem(int position) {
        return position;
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.sv_list_item, parent, false);

        TextView tvID = (TextView) rowView.findViewById(R.id.tvId);
        ImageView imgAvatar = (ImageView) rowView.findViewById(R.id.imgAvatatr);
        TextView tvName = (TextView) rowView.findViewById(R.id.tvName);

        TextView tvTuoi = (TextView) rowView.findViewById(R.id.tvTuoi);

        TextView tvMssv = (TextView) rowView.findViewById(R.id.tvMssv);

        ImageButton edit = rowView.findViewById(R.id.btn_EditSv);
        ImageButton delete = rowView.findViewById(R.id.btnDeleteSv);

//        String imageUrl = mList.get(position).getThumbnailUrl();
//        Picasso.get().load(imageUrl).into(imgAvatar);
////        imgAvatar.setImageResource(imageId[position]);
        tvID.setText(String.valueOf(mList.get(position).get_id()));
        tvName.setText(String.valueOf(mList.get(position).getName()));

        tvTuoi.setText(String.valueOf(mList.get(position).getTuoi()));

        tvMssv.setText(String.valueOf(mList.get(position).getMssv()));


        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEditDialog(position);
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                trangChu.xoa(mList.get(position).get_id());
            }
        });

        return rowView;
    }

    public void showEditDialog(final int position) {
        final Sinhvien sinhvien = mList.get(position);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_updatesv, null);
        builder.setView(dialogView);

        EditText edtName = dialogView.findViewById(R.id.upName);
        EditText edtAge = dialogView.findViewById(R.id.upAge);
        EditText edtmasv = dialogView.findViewById(R.id.upMSSV);

        edtName.setText(sinhvien.getName());
        edtAge.setText(String.valueOf(sinhvien.getTuoi()));
        edtmasv.setText(sinhvien.getMssv());

        builder.setPositiveButton("Lưu", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String name = edtName.getText().toString().trim();
                Integer tuoi = Integer.parseInt(edtAge.getText().toString().trim());
                String mssv = edtmasv.getText().toString().trim();

                if (!name.isEmpty() && !mssv.isEmpty()) {
//                    item = new Sinhvien();
//                    item.setName(name);
//                    item.setTuoi(tuoi);
//                    item.setMssv(mssv);

                    sinhvien.setName(name);
                    sinhvien.setTuoi(tuoi);
                    sinhvien.setMssv(mssv);

                    notifyDataSetChanged();

                    updateStudentOnApi(sinhvien);

                    dialogInterface.dismiss();
                }
            }
        });
        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog dialog1 = builder.create();
        dialog1.show();
    }

    public void updateStudentOnApi(Sinhvien sv) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Config.ip)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        Call<Sinhvien> call = apiService.updateSinhViens(sv.get_id(), sv);  // Sử dụng item để truyền dữ liệu sinh viên cần thêm

        call.enqueue(new Callback<Sinhvien>() {
            @Override
            public void onResponse(Call<Sinhvien> call, Response<Sinhvien> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "update info student success", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(context, "Lỗi khi sửa thông tin sinh viên", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Sinhvien> call, Throwable t) {
                // Xử lý lỗi
                Toast.makeText(context, "Lỗi khi sửa thông tin sinh viên", Toast.LENGTH_SHORT).show();

            }
        });

    }

}

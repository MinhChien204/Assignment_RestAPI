package com.example.less3.main;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.less3.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class CustomAdapter extends BaseAdapter {


    Context context;
    List<Items> mList = new ArrayList<>();


    public CustomAdapter(Context context, List<Items> mList) {

        this.context = context;
        this.mList = mList;
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
        View rowView = inflater.inflate(R.layout.custom_list_item, parent, false);

        TextView tvID = (TextView) rowView.findViewById(R.id.tvId);
        ImageView imgAvatar = (ImageView) rowView.findViewById(R.id.imgAvatatr);
        TextView tvTitle = (TextView) rowView.findViewById(R.id.tvTittle);
        String imageUrl = mList.get(position).getThumbnailUrl();
        Picasso.get().load(imageUrl).into(imgAvatar);
//        imgAvatar.setImageResource(imageId[position]);
       tvID.setText(String.valueOf(mList.get(position).getId()));
        tvTitle.setText(String.valueOf(mList.get(position).getTitle()));



        return rowView;
    }
}

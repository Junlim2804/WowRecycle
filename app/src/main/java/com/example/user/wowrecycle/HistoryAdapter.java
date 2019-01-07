package com.example.user.wowrecycle;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder> {

    Context mContext;
    List<History> mData;


    public HistoryAdapter(Context mContext, List<History> data) {
        this.mContext = mContext;
        this.mData = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.item_records,parent,false);
        MyViewHolder vHolder = new MyViewHolder(v);
        return vHolder;
    }

    public static final String FILE_NAME="com.example.user.wowrecycle";
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.tv_location.setText(mData.get(position).getLocation());
        holder.tv_date.setText(mData.get(position).getDate());
        holder.tv_weight.setText(mData.get(position).getWeight()+"");
        holder.tv_remarks.setText(mData.get(position).getRemarks());
        //holder.iv_item.setImageResource(mData.get(position).getPhoto());
        byte[] decodedString = Base64.decode(mData.get(position).getPhoto(),Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString,
                0, decodedString.length);
        if (decodedByte != null) {
            holder.iv_item.setImageBitmap(decodedByte);
        }

        holder.tv_type.setText(mData.get(position).getType());
    }

    @Override
    public int getItemCount() {

        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView tv_location;
        private TextView tv_date;
        private TextView tv_weight;
        private TextView tv_remarks;
        private ImageView iv_item;
        private TextView tv_type;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_location = (TextView)itemView.findViewById(R.id.record_location);
            tv_date = (TextView)itemView.findViewById(R.id.record_date);
            tv_weight = (TextView)itemView.findViewById(R.id.record_weight);
            tv_remarks = (TextView)itemView.findViewById(R.id.record_remarks);
            iv_item = (ImageView)itemView.findViewById(R.id.record_image);
            tv_type = (TextView)itemView.findViewById(R.id.record_type);
        }
    }
}

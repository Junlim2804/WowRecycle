package com.example.user.wowrecycle;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.wowrecycle.Entity.Redeem;

import java.util.List;

public class RedeemAdapter extends RecyclerView.Adapter<RedeemAdapter.MyViewHolder> {

    Context mContext;
    List<Redeem> mData;


    public RedeemAdapter(Context mContext, List<Redeem> data) {
        this.mContext = mContext;
        this.mData = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.item_myreward,parent,false);
        MyViewHolder vHolder = new MyViewHolder(v);
        return vHolder;
    }


    public static final String FILE_NAME="com.example.user.wowrecycle";

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {


        holder.dateDesc.setText(mData.get(position).getDate());
        byte[] decodedString = Base64.decode(mData.get(position).getImage(),Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString,
                0, decodedString.length);
        if (decodedByte != null) {
            holder.rdImage.setImageBitmap(decodedByte);
        }
        holder.pointDesc.setText(mData.get(position).getPoint()+ " points");

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView dateDesc, pointDesc;
        private ImageView rdImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            dateDesc = (TextView)itemView.findViewById(R.id.displayRdDate);
            rdImage = (ImageView)itemView.findViewById(R.id.rdImage);
            pointDesc = (TextView) itemView.findViewById(R.id.pointUsed);
        }
    }
}

package com.example.user.wowrecycle;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.user.wowrecycle.Entity.User;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder> {

    Context mContext;
    List<History> mData;
    private HistoryAdapter adapter;



    public HistoryAdapter(Context mContext, List<History> data) {
        this.mContext = mContext;
        this.mData = data;
        this.adapter = this;
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
        final String status;
        holder.tv_location.setText(mData.get(position).getLocation());
        holder.tv_date.setText(mData.get(position).getDate());
        holder.tv_type.setText(mData.get(position).getType());
        holder.tv_remarks.setText(mData.get(position).getRemarks());

        if(mData.get(position).getDone())
        {
            status="Completed";

        }
        else {
            status="Incomplete";

        }
        holder.tv_status.setText(status);
        //holder.btn_cancel.setText(mData.get(position).getType());
        holder.btn_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences pref = v.getContext().getSharedPreferences(FILE_NAME,0);
                SharedPreferences.Editor editor=pref.edit();
                editor.putString("location",mData.get(position).getLocation());
                editor.putString("date",mData.get(position).getDate());
                editor.putString("time",mData.get(position).getTime());
                editor.putString("weight",mData.get(position).getWeight()+"");
                editor.putString("remarks",mData.get(position).getRemarks());
                editor.putString("type",mData.get(position).getType());
                editor.putString("image",mData.get(position).getPhoto());
                editor.putString("status",status);
                editor.putString("uname", mData.get(position).getUname());
                editor.commit();
                Intent intent = new Intent(v.getContext(), HistoryDetails.class);
                v.getContext().startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {

        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView tv_location;
        private TextView tv_date;
        private TextView tv_remarks;
        private TextView tv_type;
        private TextView tv_status;

        private TextView btn_view;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_location = (TextView)itemView.findViewById(R.id.record_location);
            tv_remarks = (TextView)itemView.findViewById(R.id.record_remarks);
            tv_date = (TextView)itemView.findViewById(R.id.record_date);
            tv_type = (TextView)itemView.findViewById(R.id.record_type);
            tv_status=(TextView)itemView.findViewById(R.id.record_status);
            btn_view = (TextView)itemView.findViewById(R.id.record_view);


        }
    }
}

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

import com.example.user.wowrecycle.Entity.Event;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.MyViewHolder> {

    Context mContext;
    List<Event> mData;


    public EventAdapter(Context mContext, List<Event> data) {
        this.mContext = mContext;
        this.mData = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.item_event,parent,false);
        MyViewHolder vHolder = new MyViewHolder(v);
        return vHolder;
    }


    public static final String FILE_NAME="com.example.user.wowrecycle";

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {



       // holder.iv_event.setImageResource(mData.get(position).getBackground());

        byte[] decodedString = Base64.decode(mData.get(position).getBackground(),Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString,
                0, decodedString.length);
        if (decodedByte != null) {
            holder.iv_event.setImageBitmap(decodedByte);
        }
        holder.tv_desc.setText(mData.get(position).getEventDesc());
        //holder.btn_view.setText(mData.get(position).getView());

        holder.btn_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse(mData.get(position).getUrl()));
            /*    SharedPreferences pref = v.getContext().getSharedPreferences(FILE_NAME,0);
                SharedPreferences.Editor editor=pref.edit();
                editor.putString("url",mData.get(position).getUrl());
                editor.commit();*/
                v.getContext().startActivity(intent);



            }
        });


    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView tv_desc;
        private Button btn_view;
        private ImageView iv_event;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_desc = (TextView)itemView.findViewById(R.id.event_desc);
            btn_view = (Button)itemView.findViewById(R.id.event_view);
            iv_event = (ImageView)itemView.findViewById(R.id.event_image);


        }
    }
}

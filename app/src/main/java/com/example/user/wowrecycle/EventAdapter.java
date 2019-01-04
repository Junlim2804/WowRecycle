package com.example.user.wowrecycle;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.MyViewHolder> {

    Context mContext;
    List<event> mData;


    public EventAdapter(Context mContext, List<event> data) {
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

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.iv_event.setImageResource(mData.get(position).getBackground());
        holder.tv_desc.setText(mData.get(position).getEventDesc());
        holder.btn_view.setText(mData.get(position).getView());




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

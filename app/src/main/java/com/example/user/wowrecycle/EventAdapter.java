package com.example.user.wowrecycle;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.net.URL;
import java.util.List;

import static com.example.user.wowrecycle.RecyclerViewAdapter.FILE_NAME;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.MyViewHolder> {

    Context mContext;
    List<event> mData;

    public void browser1(View view){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
        view.getContext().startActivity(browserIntent);
    }


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


    public static final String FILE_NAME="com.example.user.wowrecycle";

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.iv_event.setImageResource(mData.get(position).getBackground());
        holder.tv_desc.setText(mData.get(position).getEventDesc());
        holder.btn_view.setText(mData.get(position).getView());

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

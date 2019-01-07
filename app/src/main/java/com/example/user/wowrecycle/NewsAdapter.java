package com.example.user.wowrecycle;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyViewHolder> {

    Context mContext;
    List<News> mData;


    public NewsAdapter(Context mContext, List<News> data) {
        this.mContext = mContext;
        this.mData = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.item_news,parent,false);
        MyViewHolder vHolder = new MyViewHolder(v);
        return vHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.iv_news.setImageResource(mData.get(position).getNewsPicture());
        holder.tv_desc.setText(mData.get(position).getNewsDesc());

        holder.iv_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse(mData.get(position).getNewsUrl()));
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
        private ImageView iv_news;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_desc = (TextView)itemView.findViewById(R.id.news_desc);
            iv_news = (ImageView)itemView.findViewById(R.id.news_image);
        }
    }
}
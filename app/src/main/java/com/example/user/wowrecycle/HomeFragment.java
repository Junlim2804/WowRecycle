package com.example.user.wowrecycle;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    View view;
    private RecyclerView myRecyclerView;
    private List<News> listNews;


    public HomeFragment(){

    }



    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container,false);
        myRecyclerView = (RecyclerView)view.findViewById(R.id.news_rv);
       NewsAdapter recyclerAdapter = new NewsAdapter(getContext(),listNews);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        myRecyclerView.setLayoutManager(linearLayoutManager);

        myRecyclerView.setAdapter(recyclerAdapter);

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        listNews = new ArrayList<>();
        listNews.add(new News(R.drawable.news1, "Ecosurety & Recycling Technologies Strike New Partnership", "https://ciwm-journal.co.uk/ecosurety-recycling-technologies-strike-new-partnership/"));
        listNews.add(new News(R.drawable.news2, "NZ's rule in the Mlaysian plastics dumping ground","https://www.nzgeo.com/audio/nzs-role-in-the-malaysian-plastics-dumping-ground/"));
        listNews.add(new News(R.drawable.news3, "Plastic waste: Govt forms committee to look into recycling industry", "https://www.cnbc.com/2018/04/22/plastic-pollution-firms-and-governments-fight-waste.html"));
        listNews.add(new News(R.drawable.news4, "Proper programme needed to recycle plastics, glass", "https://www.nst.com.my/opinion/letters/2017/06/244621/proper-programme-needed-recycle-plastics-glass"));
        listNews.add(new News(R.drawable.news5, "Where do old clothes go?", "https://www.thestar.com.my/lifestyle/features/2015/02/16/where-do-old-clothes-go/"));




    }
}
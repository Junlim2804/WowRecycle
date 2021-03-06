package com.example.user.wowrecycle;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.user.wowrecycle.Entity.News;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    View view;
    private RecyclerView myRecyclerView;
    private List<News> listNews;
    private Button button;


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
        Button button = (Button)view.findViewById(R.id.btn_book);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Fragment fr=new BookingFragment();
                ((FragmentActivity) v.getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.content,fr).commit();

            }
        });

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        listNews = new ArrayList<>();
        //listNews.add(new News(R.drawable.news1, "Ecosurety & Recycling Technologies Strike New Partnership", "https://ciwm-journal.co.uk/ecosurety-recycling-technologies-strike-new-partnership/"));
        listNews.add(new News(R.drawable.news2, "NZ's rule in the Malaysian plastics dumping ground","https://www.nzgeo.com/audio/nzs-role-in-the-malaysian-plastics-dumping-ground/"));
        listNews.add(new News(R.drawable.news3, "Plastic waste: Govt forms committee to look into recycling industry", "https://www.cnbc.com/2018/04/22/plastic-pollution-firms-and-governments-fight-waste.html"));
        listNews.add(new News(R.drawable.news4, "Proper programme needed to recycle plastics, glass", "https://www.nst.com.my/opinion/letters/2017/06/244621/proper-programme-needed-recycle-plastics-glass"));
        listNews.add(new News(R.drawable.news5, "Where do old clothes go?", "https://www.thestar.com.my/lifestyle/features/2015/02/16/where-do-old-clothes-go/"));





    }
}
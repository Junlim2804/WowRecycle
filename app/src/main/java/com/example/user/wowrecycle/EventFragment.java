package com.example.user.wowrecycle;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class EventFragment extends Fragment {
    View view;
    private RecyclerView myRecyclerView;
    private List<event> listEvent;
    private TextView txturl;


    public EventFragment(){

    }

    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_event, container,false);
        myRecyclerView = (RecyclerView)view.findViewById(R.id.event_rv);
        EventAdapter recyclerAdapter = new EventAdapter(getContext(),listEvent);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        myRecyclerView.setLayoutManager(linearLayoutManager);

        myRecyclerView.setAdapter(recyclerAdapter);



        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        listEvent = new ArrayList<>();
        listEvent.add(new event(R.drawable.event1, "RECYCLE RELOVE RECONNECT", "View", "http://trendingtrain.net/2018/08/23/recycle-relove-reconnect-bandar-rimbayu/"));
        listEvent.add(new event(R.drawable.event2, "Save the Earth, avoid dumping E-Waste", "View", "https://www.google.com/"));
        listEvent.add(new event(R.drawable.event3, "BROS 321 Trade-In Program", "View", "https://www.bros.com.my/v2/bros-321-trade-in-program/"));
        listEvent.add(new event(R.drawable.event1, "Plasticity event in Malaysia", "View", "https://www.recyclingtoday.com/article/plasticity-malaysia-october-25-2018-recycling/"));


    }
}

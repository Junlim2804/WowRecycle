package com.example.user.wowrecycle;

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

public class EventFragment extends Fragment {
    View view;
    private RecyclerView myRecyclerView;
    private List<event> listEvent;


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
        listEvent.add(new event(R.drawable.event1, "Nuggets", "View1"));
        listEvent.add(new event(R.drawable.event2, "Subway", "View2"));
        listEvent.add(new event(R.drawable.event3, "KFC", "View3"));
        listEvent.add(new event(R.drawable.event1, "Nuggets", "View4"));
        listEvent.add(new event(R.drawable.event2, "Subway", "View5"));
        listEvent.add(new event(R.drawable.event3, "KFC", "View6"));

    }
}

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

public class FragmentBrowse extends Fragment {
    View view;
    private RecyclerView myRecyclerView;
    private List<Reward> listReward;


    public FragmentBrowse(){

    }

    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.browse_fragment, container,false);
        myRecyclerView = (RecyclerView)view.findViewById(R.id.reward_rv);
        RecyclerViewAdapter recyclerAdapter = new RecyclerViewAdapter(getContext(),listReward);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        myRecyclerView.setLayoutManager(linearLayoutManager);

        myRecyclerView.setAdapter(recyclerAdapter);

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        listReward = new ArrayList<>();
        listReward.add(new Reward(R.drawable.reward1, "Nuggets", "View1"));
        listReward.add(new Reward(R.drawable.reward2, "Subway", "View2"));
        listReward.add(new Reward(R.drawable.reward3, "KFC", "View3"));
        listReward.add(new Reward(R.drawable.reward1, "Nuggets", "View4"));
        listReward.add(new Reward(R.drawable.reward2, "Subway", "View5"));
        listReward.add(new Reward(R.drawable.reward3, "KFC", "View6"));

    }
}

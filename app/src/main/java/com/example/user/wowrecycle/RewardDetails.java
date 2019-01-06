package com.example.user.wowrecycle;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;


public class RewardDetails extends Fragment {
    private TextView txttnc;
    private TextView txtdetail;


    public RewardDetails() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_reward_details, container, false);
        txttnc=(TextView)v.findViewById(R.id.tv_tnc);
        txtdetail=(TextView)v.findViewById(R.id.tv_details);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(RecyclerViewAdapter.FILE_NAME, 0);
        String tnc=sharedPreferences.getString("tnc","no detail");
        String detail=sharedPreferences.getString("detail", "no detail");
        txttnc.setText(tnc);
        txtdetail.setText(detail);
        return v;
    }








}

package com.example.user.wowrecycle;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;


public class RewardDetails extends Fragment {
    private TextView txttnc;
    private TextView txtdetail;
    private TextView txtPoints;


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
        txtPoints=(TextView)v.findViewById(R.id.tv_points);

        Button button = (Button) v.findViewById(R.id.btn_redeem);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pointHave = Integer.parseInt(getArguments().getString("currentPoint"));
                int pointNeed = Integer.parseInt(txtPoints.getText().toString());
                String message = "";
                if(pointHave >= pointNeed)
                {
                    pointHave = pointHave - pointNeed;
                    message = String.format("Reward claimed. You have %d left", pointHave);
                    //update point left to database

                }
                else
                {
                    message = "Reward failed to claim. Please check your points.";

                }
                Toast.makeText(getActivity(),
                        message, Toast.LENGTH_LONG).show();
            }
        });

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(RecyclerViewAdapter.FILE_NAME, 0);
        String tnc=sharedPreferences.getString("tnc","no detail");
        String detail=sharedPreferences.getString("detail", "no detail");
        String points=sharedPreferences.getString("points", "no detail");
        txttnc.setText(tnc);
        txtdetail.setText(detail);
        txtPoints.setText(points);
        return v;
    }


}

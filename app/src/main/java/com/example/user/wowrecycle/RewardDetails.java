package com.example.user.wowrecycle;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.List;

import static com.example.user.wowrecycle.AppController.TAG;


public class RewardDetails extends Fragment {
    private TextView txttnc;
    private TextView txtdetail;
    private TextView txtPoints;
    private ImageView imgPhoto;
    public static final String FILE_NAME = "com.example.user.wowrecycle";

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
        imgPhoto=(ImageView) v.findViewById(R.id.iv_reward);

        Button button = (Button) v.findViewById(R.id.btn_redeem);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences sharedPref = getActivity().getSharedPreferences(FILE_NAME, 0);
                int rewardIndex = sharedPref.getInt("rewardIndex",0);
                int pointHave = sharedPref.getInt("pointAvailable",0);
                int pointNeed = Integer.valueOf(txtPoints.getText().toString());
                String message = "";
                if(pointHave >= pointNeed)
                {
                    pointHave = pointHave - pointNeed;
                    message = String.format("Reward claimed. You have %d left", pointHave);
                    //update point left to database
                    //redirect to my rewards page
                    //if redeem rewards table exists then insert table (reward id)
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
        String photo =sharedPreferences.getString("photo", "no detail");
        txttnc.setText(tnc);
        txtdetail.setText(detail);
        txtPoints.setText(points);

        byte[] decodedString = Base64.decode(photo,Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString,
                0, decodedString.length);
        if (decodedByte != null) {
            imgPhoto.setImageBitmap(decodedByte);
        }
        imgPhoto.setImageBitmap(decodedByte);
        return v;
    }



}

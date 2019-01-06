package com.example.user.wowrecycle;

import android.app.ProgressDialog;
import android.content.Context;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FragmentBrowse extends Fragment {
    View view;
    private List<Reward> listReward;
    private RecyclerView myRecyclerView;
    private static int currentPoint=0;
    ProgressDialog progressDialog;


    public FragmentBrowse(){

    }

    @Nullable

    private TextView textView;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.browse_fragment, container,false);
        myRecyclerView = (RecyclerView)view.findViewById(R.id.reward_rv);
        RecyclerViewAdapter recyclerAdapter = new RecyclerViewAdapter(getContext(),listReward);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        myRecyclerView.setLayoutManager(linearLayoutManager);

        textView=(TextView)view.findViewById(R.id.tmp_point);
        getBonus(getActivity(),AppConfig.URL_GETPOINTS);
        textView.setText(currentPoint+"");
        myRecyclerView.setAdapter(recyclerAdapter);

        return view;
    }

    private void getBonus(Context context, String url)
    {
        progressDialog = ProgressDialog.show(getActivity(), "Loading...", "Please wait...", true);
        SQLiteHandler db = new SQLiteHandler(getActivity());
        HashMap<String, String> user=db.getUserDetails();
        //String uname=user.get("name");
        String uname="BK";
        RequestQueue queue = Volley.newRequestQueue(context);
        url=url+"?name="+uname;
         if (!progressDialog.isShowing())
             progressDialog.setMessage("Syn with server...");
          progressDialog.show();

        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {


                        try{
                            //Clear list


                           if(response.length()>0){
                                JSONObject res = (JSONObject) response.get(0);
                                currentPoint = res.getInt("points");
                                Toast.makeText(getActivity(), currentPoint+"", Toast.LENGTH_SHORT).show();
                            }


                               if (progressDialog.isShowing())
                                    progressDialog.dismiss();

                        }catch (Exception e){
                            Toast.makeText(getActivity(), "Error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(getActivity(), "Error:" + volleyError.getMessage(), Toast.LENGTH_SHORT).show();
                         if (progressDialog.isShowing())
                            progressDialog.dismiss();
                    }
                });

        queue.add(jsonObjectRequest);
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        listReward = new ArrayList<>();
        listReward.add(new Reward(R.drawable.reward1, "Nuggets", "View","Get 20pcs McNuggets at McDonals for 200 points", "Limited to 20 redemptions per day."));
        listReward.add(new Reward(R.drawable.reward2, "Subway", "View","blah","a"));
        listReward.add(new Reward(R.drawable.reward3, "KFC", "View","blah","b"));
        listReward.add(new Reward(R.drawable.reward1, "Nuggets", "View","blah","c"));
        listReward.add(new Reward(R.drawable.reward2, "Subway", "View","blah","d"));
        listReward.add(new Reward(R.drawable.reward3, "KFC", "View","blah","e"));

    }
}

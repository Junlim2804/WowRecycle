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

import static com.example.user.wowrecycle.AppController.TAG;

public class FragmentBrowse extends Fragment {
    View view;
    private List<Reward> listReward;
    private RecyclerView myRecyclerView;
    private static int currentPoint=0;
    ProgressDialog progressDialog;
    RecyclerViewAdapter recyclerAdapter;
    private static String GET_URL = "https://wwwwowrecyclecom.000webhostapp.com/select_reward.php";
    RequestQueue queue;


    public FragmentBrowse(){

    }

    @Nullable

    private TextView textView;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.browse_fragment, container,false);
        myRecyclerView = (RecyclerView)view.findViewById(R.id.reward_rv);
        listReward = new ArrayList<>();

        downloadReward(getActivity(), AppConfig.URL_REWARD);




        textView=(TextView)view.findViewById(R.id.tmp_point);
        getBonus(getActivity(),AppConfig.URL_GETPOINTS);
        textView.setText(currentPoint+"");

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

    private void downloadReward(Context context, String url) {
        // Instantiate the RequestQueue
        queue = Volley.newRequestQueue(context);
        //progressDialog = ProgressDialog.show(getActivity(), "Loading...", "Please wait...", true);
        //if (!progressDialog.isShowing())
         //   progressDialog.setMessage("Syn with server...");
        //progressDialog.show();

        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(
                url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            listReward.clear();
                            Toast.makeText(getContext(), response.length()+"", Toast.LENGTH_LONG).show();
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject rewardResponse = (JSONObject) response.get(i);
                                String photo = rewardResponse.getString("photo");
                                int points = rewardResponse.getInt("points");
                                String detail = rewardResponse.getString("detail");
                                String tnc = rewardResponse.getString("tnc");
                                Reward reward = new Reward(photo , points, detail, tnc);
                                listReward.add(reward);

                            }
                            loadReward(listReward);
                            if (progressDialog.isShowing())
                                progressDialog.dismiss();
                        } catch (Exception e) {
                            Toast.makeText(getContext(), "Error:" + e.getMessage(), Toast.LENGTH_LONG).show();
                            if (progressDialog.isShowing())
                                progressDialog.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(getContext(), "Error" + volleyError.getMessage(), Toast.LENGTH_LONG).show();
                        if (progressDialog.isShowing())
                            progressDialog.dismiss();
                    }
                });

        // Set the tag on the request.
        jsonObjectRequest.setTag(TAG);

        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest);
    }

    public void loadReward(List<Reward> r)
    {
        recyclerAdapter = new RecyclerViewAdapter(getContext(),r);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        myRecyclerView.setLayoutManager(linearLayoutManager);
        myRecyclerView.setAdapter(recyclerAdapter);


    }
/*
    private void loadReward() {
        final RewardAdapter adapter = new RewardAdapter(this, R.layout.content_main, listReward);
        listViewCourse.setAdapter(adapter);
        Toast.makeText(getApplicationContext(), "Count :" + caList.size(), Toast.LENGTH_LONG).show();
    }*/
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }
}

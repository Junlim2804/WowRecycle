package com.example.user.wowrecycle;

import android.app.ProgressDialog;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.user.wowrecycle.DataSource.AppDatabase;
import com.example.user.wowrecycle.Entity.Redeem;
import com.example.user.wowrecycle.Entity.Reward;
import com.example.user.wowrecycle.Entity.User;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.example.user.wowrecycle.AppController.TAG;

public class FragmentMyRewards extends Fragment {
    View view;
    RequestQueue queue;
    private AppDatabase wowDatabase;
    SharedPreferences sharedPref;
    private String curUser;
    private TextView rdDate;
    private ImageView rdImage;
    private int rdIndex;
    private String rdImageString;
    private RecyclerView myRecyclerView;
    private List<Redeem> listRedeem;
    RedeemAdapter recyclerAdapter;
    ProgressDialog progressDialog;

    public FragmentMyRewards() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.myreward_fragment, container, false);

        rdDate = view.findViewById(R.id.displayRdDate);
        rdImage = view.findViewById(R.id.rdImage);
        myRecyclerView = (RecyclerView)view.findViewById(R.id.rv_myredeem);
        listRedeem = new ArrayList<>();
        wowDatabase = Room.databaseBuilder(view.getContext(),
                AppDatabase.class, getString(R.string.DATABASENAME)).build();

        try {
            new UserAsyncTask().execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        downloadReward(getActivity(),AppConfig.URL_GETREWARD);
        return view;
    }

    private void downloadReward(Context context, String url) {
        // Instantiate the RequestQueue
        queue = Volley.newRequestQueue(context);
        progressDialog = ProgressDialog.show(getActivity(), "Checking Reward", "Please wait...", true);
        url=url+"?name="+curUser;
        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(
                url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            listRedeem.clear();
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject redeemResponse = (JSONObject) response.get(i);
                                int rid=redeemResponse.getInt("rid");
                                String image = redeemResponse.getString("image");
                                int point = redeemResponse.getInt("point");
                                String date = redeemResponse.getString("redeemtime");
                                Redeem redeem = new Redeem(date,curUser,rid,image,point);
                                listRedeem.add(redeem);

                            }
                            loadAllRedeem(listRedeem);
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

    public void loadAllRedeem(List<Redeem> r) {
        recyclerAdapter = new RedeemAdapter(getContext(), r);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        myRecyclerView.setLayoutManager(linearLayoutManager);
        myRecyclerView.setAdapter(recyclerAdapter);
    }
    private class UserAsyncTask extends AsyncTask<Void,Void,Void> {

        public UserAsyncTask() {

        }

        @Override
        protected Void doInBackground(Void... Voids) {
            List<User> allUsers=wowDatabase.userDao().loadAllUsers();
            curUser=allUsers.get(0).getName();

            return null;
        }
    }



}





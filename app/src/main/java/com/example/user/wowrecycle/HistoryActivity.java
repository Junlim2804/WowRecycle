package com.example.user.wowrecycle;

import android.app.ProgressDialog;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.user.wowrecycle.DataSource.AppDatabase;
import com.example.user.wowrecycle.Entity.User;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.example.user.wowrecycle.AppController.TAG;

public class HistoryActivity extends AppCompatActivity {

    private List<History> listHistory;
    private RecyclerView myRecyclerView;
    HistoryAdapter recyclerAdapter;
    RequestQueue queue;
    private AppDatabase wowDatabase;
    ProgressDialog progressDialog;
    private String uname;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_history);
        myRecyclerView = findViewById(R.id.record_rv);
        listHistory = new ArrayList<>();
        wowDatabase = Room.databaseBuilder(this,
                AppDatabase.class, "wow_db").build();
        try {
            new UserAsyncTask().execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        downloadHistory(this, AppConfig.URL_HISTORY);

        //listHistory.add(new History("abc","abc",1,"lala","R.drawable.reward2","lala"));
        //listHistory.add(new History("abc","abc",2,"lala","R.drawable.reward2","lala");
        // listHistory.add(new History("abc","abc",3,"lala","R.drawable.reward2","lala");
        // listHistory.add(new History("abc","abc",4,"lala","R.drawable.reward2","lala");


    }

    private void downloadHistory(Context context, String url) {
        progressDialog = ProgressDialog.show(this, "Loading...", "Please wait...", true);
        // Instantiate the RequestQueue
        queue = Volley.newRequestQueue(context);
        //progressDialog = ProgressDialog.show(getActivity(), "Loading...", "Please wait...", true);
        //if (!progressDialog.isShowing())
        //   progressDialog.setMessage("Syn with server...");
        //progressDialog.show();

        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(
                url + "?name=" + uname,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            listHistory.clear();
                            //Toast.makeText(getContext(), response.length()+"", Toast.LENGTH_LONG).show();
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject eventResponse = (JSONObject) response.get(i);
                                String location = eventResponse.getString("location");
                                String date = eventResponse.getString("date");
                                String time=eventResponse.getString("time");
                                int weight = eventResponse.getInt("weight");
                                String remarks = eventResponse.getString("remarks");
                                String photo = eventResponse.getString("photo");

                                String type = eventResponse.getString("type");
                                int test=eventResponse.getInt("done");
                                boolean done=false;
                                if(test==1)
                                {
                                    done=true;
                                }

                                History history = new History(uname,location, date,time, weight, remarks, photo, type, null,done);
                                listHistory.add(history);

                            }
                            loadHistory(listHistory);
                            if (progressDialog.isShowing())
                                progressDialog.dismiss();
                        } catch (Exception e) {
                            // Toast.makeText(getContext(), "Error:" + e.getMessage(), Toast.LENGTH_LONG).show();
                            if (progressDialog.isShowing())
                                progressDialog.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Toast.makeText(getContext(), "Error" + volleyError.getMessage(), Toast.LENGTH_LONG).show();
                        if (progressDialog.isShowing())
                            progressDialog.dismiss();
                    }
                });

        // Set the tag on the request.
        jsonObjectRequest.setTag(TAG);

        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest);
    }

    public void loadHistory(List<History> h) {


        recyclerAdapter = new HistoryAdapter(this, h);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        myRecyclerView.setLayoutManager(linearLayoutManager);
        myRecyclerView.setAdapter(recyclerAdapter);


    }

    private class UserAsyncTask extends AsyncTask<Void, Void, Void> {

        public UserAsyncTask() {

        }

        @Override
        protected Void doInBackground(Void... Voids) {
            List<User> allUsers = wowDatabase.userDao().loadAllUsers();
            uname = allUsers.get(0).getName();
            return null;
        }
    }
}
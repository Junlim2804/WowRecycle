package com.example.user.wowrecycle;

import android.app.ProgressDialog;
import androidx.room.Room;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.user.wowrecycle.DataSource.AppDatabase;
import com.example.user.wowrecycle.Entity.Reward;
import com.example.user.wowrecycle.Entity.User;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.example.user.wowrecycle.AppController.TAG;

public class FragmentBrowse extends Fragment {
    View view;
    private List<Reward> listReward;
    private RecyclerView myRecyclerView;
    private static int currentPoint=0;
    ProgressDialog progressDialog;
    RecyclerViewAdapter recyclerAdapter;
    public static final String FILE_NAME = "com.example.user.wowrecycle";
    public SharedPreferences sharedPreferences;
    private static String uname;
    private AppDatabase wowDatabase;
    private static String GET_URL = "https://wwwwowrecyclecom.000webhostapp.com/select_reward.php";
    RequestQueue queue;
    int itemPosition;

    public FragmentBrowse(){

    }

    @Nullable

    private TextView textView;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.browse_fragment, container,false);
        myRecyclerView = (RecyclerView)view.findViewById(R.id.reward_rv);
        listReward = new ArrayList<>();

        listReward.add(new Reward(" ", 100, "detail", "empty"));
        listReward.add(new Reward(" ", 100, "detail", "empty"));
        listReward.add(new Reward(" ", 100, "detail", "empty"));
        listReward.add(new Reward(" ", 100, "detail", "empty"));
        loadReward(listReward);
        //downloadReward(getActivity(), AppConfig.URL_REWARD);
        wowDatabase = Room.databaseBuilder(getActivity(),
                AppDatabase.class, getString(R.string.DATABASENAME)).build();
        try {
            new UserAsyncTask().execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        textView=(TextView)view.findViewById(R.id.tmp_point);
       // getBonus(getActivity(),AppConfig.URL_GETPOINTS);
        textView.setText(currentPoint+"");

        sharedPreferences = getActivity().getSharedPreferences(FILE_NAME, 0);
        //itemPosition = myRecyclerView.getChildLayoutPosition(view);

        /*Bundle bundle = new Bundle();
        bundle.putString("currentPoint", textView.toString());
        RewardDetails rewardDetails = new RewardDetails();
        rewardDetails.setArguments(bundle);*/

        return view;
    }



    private void downloadReward(Context context, String url) {
        // Instantiate the RequestQueue
        queue = Volley.newRequestQueue(context);
        //progressDialog = ProgressDialog.show(getActivity(), "Checking Reward", "Please wait...", true);
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

                            for (int i = 0; i < response.length(); i++) {
                                JSONObject rewardResponse = (JSONObject) response.get(i);
                                String photo = rewardResponse.getString("photo");
                                int points = rewardResponse.getInt("points");
                                String detail = rewardResponse.getString("detail");
                                String tnc = rewardResponse.getString("tnc");
                                //int rID = rewardResponse.getInt("rID");
                                Reward reward = new Reward(photo , points, detail, tnc);
                                listReward.add(reward);

                            }
                            recyclerAdapter.notifyDataSetChanged();
                          //  if (progressDialog.isShowing())
                          //          progressDialog.dismiss();
                        } catch (Exception e) {
                            //Toast.makeText(getContext(), "Error:" + e.getMessage(), Toast.LENGTH_LONG).show();
                          //  if (progressDialog.isShowing())
                           //     progressDialog.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                       // Toast.makeText(getContext(), "Error" + volleyError.getMessage(), Toast.LENGTH_LONG).show();
                       // if (progressDialog.isShowing())
                       //     progressDialog.dismiss();
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

    private class UserAsyncTask extends AsyncTask<Void,Void,Void> {

        public UserAsyncTask() {

        }

        @Override
        protected Void doInBackground(Void... Voids) {
            List<User> allUsers=wowDatabase.userDao().loadAllUsers();
            uname=allUsers.get(0).getName();
            currentPoint=allUsers.get(0).getBonus();
            downloadReward(getActivity(), AppConfig.URL_REWARD);
            return null;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("pointAvailable", Integer.parseInt(textView.getText().toString()));
        editor.putInt("rewardIndex", itemPosition);
        editor.putString("uname",uname);

        editor.commit();
    }



}

package com.example.user.wowrecycle;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.user.wowrecycle.DataSource.AppDatabase;
import com.example.user.wowrecycle.Entity.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.user.wowrecycle.AppController.TAG;


public class RewardDetails extends Fragment {
    private TextView txttnc;
    private TextView txtdetail;
    private TextView txtPoints;
    private ImageView imgPhoto;
    SharedPreferences sharedPref;
    private static int db_point;
    private AppDatabase wowDatabase;
    private String uname;
    private int rid;
    int rewardIndex;
    public static final String FILE_NAME = "com.example.user.wowrecycle";
    ProgressDialog progressDialog;
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

        wowDatabase = Room.databaseBuilder(getActivity(),
                AppDatabase.class,"wow_db" ).build();
        sharedPref = getActivity().getSharedPreferences(FILE_NAME, 0);
        uname=sharedPref.getString("uname","anonymous");
        //rewardIndex = sharedPref.getInt("rewardIndex",0);
        rid=sharedPref.getInt("id",0);
        txttnc=(TextView)v.findViewById(R.id.tv_tnc);
        txtdetail=(TextView)v.findViewById(R.id.tv_details);
        txtPoints=(TextView)v.findViewById(R.id.tv_points);
        imgPhoto=(ImageView) v.findViewById(R.id.iv_reward);
        getBonus(v.getContext(),AppConfig.URL_GETPOINTS);
        Button button = (Button) v.findViewById(R.id.btn_redeem);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirm();
                    }
        });

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(RecyclerViewAdapter.FILE_NAME, 0);
        String tnc=sharedPreferences.getString("tnc","no detail");
        String detail=sharedPreferences.getString("detail", "no detail");
        String points=sharedPreferences.getString("points", "no detail");
        String photo =sharedPreferences.getString("photo", "no detail");
        rewardIndex=sharedPreferences.getInt("rid",0);

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
    private void redeem(Context context, String url)
    {
        progressDialog = ProgressDialog.show(getActivity(), "Loading...", "Please wait...", true);
        String tag_string_req = "req_redeem";
        RequestQueue queue = Volley.newRequestQueue(context);

        if (!progressDialog.isShowing())
            progressDialog.setMessage("Syn with server...");
        progressDialog.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {


                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");


                    if (!error) {



                        progressDialog.hide();
                       // Toast.makeText(getActivity(), "Succesful Redeem", Toast.LENGTH_LONG).show();
                    } else {


                        progressDialog.hide();
                        Toast.makeText(getActivity(), jObj.getString("error_msg"), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    progressDialog.hide();
                    Toast.makeText(getActivity(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getActivity(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                progressDialog.hide();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();

                params.put("name", uname);
                params.put("point", db_point+"");
                params.put("rid",(rewardIndex)+"");

                return params;
            }

        };

        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
    private void getBonus(Context context, String url)
    {
        progressDialog = ProgressDialog.show(getActivity(), "Loading...", "Please wait...", true);
        //SQLiteHandler db = new SQLiteHandler(getActivity());
       // HashMap<String, String> user=db.getUserDetails();
        //String uname=user.get("name");
        //String uname="BK";
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
                                db_point = res.getInt("points");

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
    private class UserAsyncTask extends AsyncTask<Void,Void,Void> {

        public UserAsyncTask() {

        }

        @Override
        protected Void doInBackground(Void... Voids) {
            List<User> allUsers=wowDatabase.userDao().loadAllUsers();
            allUsers.get(0).setBonus(db_point);
            wowDatabase.userDao().updateUser(allUsers.get(0));
            return null;
        }
    }

    private void confirm()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Wow Recycle");
        builder.setMessage("Are you sure to claim this reward ??")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        claim();

                    }
                })
                .setNegativeButton("No",  new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        builder.show();
    }

    private void claim()
    {
        //SharedPreferences sharedPref = getActivity().getSharedPreferences(FILE_NAME, 0);

        //uname=sharedPref.getString("uname","anonymous");
        int pointHave = sharedPref.getInt("pointAvailable",0);
        int pointNeed = Integer.valueOf(txtPoints.getText().toString());
        String message = "";
        if(db_point >= pointNeed)
        {
            pointHave = db_point - pointNeed;
            db_point=pointHave;
            message = String.format("Reward claimed. You have %d left", pointHave);
            new UserAsyncTask().execute();
            redeem(getActivity(),AppConfig.URL_REDEEM);
            //update point left to database
            //redirect to my rewards page
            //if redeem rewards table exists then insert table (reward id)
        }
        else
        {
            message = "Reward failed to claim. You only have "+db_point+" points.";

        }
        Toast.makeText(getActivity(),message, Toast.LENGTH_LONG).show();
        // getFragmentManager().beginTransaction().replace(android.R.id.content,new FragmentBrowse()).commit();
        ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.content,new RewardFragment()).commit();

    }

}

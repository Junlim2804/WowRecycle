package com.example.user.wowrecycle;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import androidx.room.Room;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.user.wowrecycle.DataSource.AppDatabase;
import com.example.user.wowrecycle.Entity.History;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HistoryDetails extends AppCompatActivity {

    private List<History> mData;

    private HistoryAdapter adapter;

    RequestQueue queue;
    private AppDatabase wowDatabase;
    ProgressDialog progressDialog;
    private TextView txtDate;
    private TextView txtTime;
    private TextView txtStatus;
    private TextView txtLocation;
    private TextView txtWeight;
    private TextView txtRemarks;
    private ImageView imgPhoto;
    private TextView txtType;
    private TextView txtUname;

    SharedPreferences sharedPref;
    public static final String FILE_NAME = "com.example.user.wowrecycle";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_history_details);
        sharedPref = getApplicationContext().getSharedPreferences(FILE_NAME, 0);
        txtDate=(TextView)findViewById(R.id.tv_date);
        txtTime=(TextView)findViewById(R.id.tv_time);
        txtStatus=(TextView)findViewById(R.id.tv_status);
        txtLocation=(TextView)findViewById(R.id.tv_location);
        txtWeight=(TextView)findViewById(R.id.tv_weight);
        imgPhoto=(ImageView)findViewById(R.id.iv_photo);
        txtRemarks=(TextView)findViewById(R.id.tv_remarks);
        txtType=(TextView)findViewById(R.id.tv_type);


        SharedPreferences sharedPreferences = getSharedPreferences(HistoryAdapter.FILE_NAME, 0);
        final String location=sharedPreferences.getString("location","no detail");
        final String date =sharedPreferences.getString("date", "no detail");
        final String time=sharedPreferences.getString("time", "no detail");
        final String weight =sharedPreferences.getString("weight", "no detail");
        final String remarks=sharedPreferences.getString("remarks","no detail");
        final String type =sharedPreferences.getString("type", "no detail");
        final String status =sharedPreferences.getString("status", "no detail");
        final String image=sharedPreferences.getString("image", "no detail");
        final String uname =sharedPreferences.getString("uname", "no detail");

        txtLocation.setText(location);
        txtDate.setText(date);
        txtTime.setText(time);
        txtWeight.setText(weight);
        txtRemarks.setText(remarks);
        txtType.setText(type);
        txtStatus.setText(status);

        byte[] decodedString = Base64.decode(image,Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString,
                0, decodedString.length);
        if (decodedByte != null) {
            imgPhoto.setImageBitmap(decodedByte);
        }
        imgPhoto.setImageBitmap(decodedByte);


        if(status.equals("Completed")){


            Button button = (Button)findViewById(R.id.record_cancel);
            button.setVisibility(View.GONE);

        }
        else {
            Button button = (Button)findViewById(R.id.record_cancel);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Submit(uname,time,date);

                }});
        }


    }

    private void cancelDetail(final String bookname, final String bookTime, final String bookdate){

        String tag_string_req = "req_bookname";
        final ProgressDialog pDialog;
        pDialog=new ProgressDialog(HistoryDetails.this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Canceling");
        pDialog.show();
        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_CANCEL, new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    if (!error) {

                        //Toast.makeText(getApplicationContext(), "sucecesful Cancel", Toast.LENGTH_LONG).show();
                        pDialog.dismiss();

                    }
                } catch (JSONException e) {

                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    //pDialog.dismiss();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(AppController.TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                //pDialog.hide();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", bookname);
                params.put("time",bookTime);
                params.put("date",bookdate);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }


    private void Submit(final String bookname, final String bookTime, final String bookdate){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Wow Recycle");
        builder.setMessage("Delete Booking??")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        cancelDetail(bookname, bookTime, bookdate);

                        //Snackbar.make(getCurrentFocus(), "Record had been deleted.",Snackbar.LENGTH_LONG).setAction("Action", null).show();
                        Toast.makeText(HistoryDetails.this, "Booking has been deleted.", Toast.LENGTH_SHORT).show();
                        Intent myIntent = new Intent(HistoryDetails.this, HistoryActivity.class);
                        HistoryDetails.this.startActivity(myIntent);

                    }
                })
                .setNegativeButton("No",  new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        builder.show();
    }




}
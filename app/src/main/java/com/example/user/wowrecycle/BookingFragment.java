package com.example.user.wowrecycle;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Room;
import android.os.AsyncTask;
import android.support.v4.app.DialogFragment;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.user.wowrecycle.DataSource.AppDatabase;
import com.example.user.wowrecycle.Entity.User;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.app.Activity.RESULT_OK;


public class BookingFragment extends DialogFragment {
    private EditText setLocation,timeData,dateData;

    private static String uname;
    private Button btnSubmitBook;
    private static int RESULT_LOAD_IMG = 4;
    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL = 2;
    private static int REQUEST_IMAGE_CAPTURE=3;
    private ImageView uploader;
    Bitmap bitmap;
    String imageString;
    private  AppDatabase wowDatabase;
    private static final String TAG = "BookingFragment";
    private DatePickerDialog.OnDateSetListener mDataSetListener;
    private TimePickerDialog mTimePicker;
    int PLACE_PICKER_REQUEST =1;

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data,getActivity());
                String toastMsg = String.format("Place: %s", place.getName());
                Toast.makeText(getActivity(), toastMsg, Toast.LENGTH_LONG).show();
                setLocation.setText(place.getAddress());
            }
        }
        else if(requestCode==REQUEST_IMAGE_CAPTURE)
        {
            if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
                Bundle extras = data.getExtras();
                bitmap = (Bitmap) extras.get("data");
                uploader.setImageBitmap(bitmap);

                //converting image to base64 string
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] imageBytes = baos.toByteArray();
                imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);

            }
        }

    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_booking,container,false);

        if (ContextCompat.checkSelfPermission(this.getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this.getActivity(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this.getActivity(),
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
        dateData=(EditText)v.findViewById(R.id.dateData);
        dateData.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog=new DatePickerDialog(getActivity(),mDataSetListener,year,month,day);
                dialog.show();
            }
        });
        timeData=(EditText) v.findViewById(R.id.timedata);
        timeData.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        timeData.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });
        setLocation=(EditText) v.findViewById((R.id.txt_setLocation));
        setLocation.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                PlacePicker.IntentBuilder builder=new PlacePicker.IntentBuilder();
                Intent intent = null;
                try {
                    intent = builder.build(getActivity());
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
                // Start the Intent by requesting a result, identified by a request code.
                startActivityForResult(intent, PLACE_PICKER_REQUEST);
            }

        });
        mDataSetListener=new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month=month+1;
                Log.d(TAG,"onDateSet:mm/dd/yyyy:"+month+"/"+dayOfMonth+"/"+"year");
                String date=month+"-"+dayOfMonth+"-"+year;
                dateData.setText(date);
            }

        };



        uploader=(ImageView)v.findViewById(R.id.item_image);
        uploader.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {

                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);

                }

            }
        });

        btnSubmitBook=(Button)v.findViewById((R.id.btnUpload));
        final EditText edtxtRemark=v.findViewById(R.id.edtxtRemark);
        btnSubmitBook.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                //SQLiteHandler db = new SQLiteHandler(getActivity());
               // HashMap<String, String> user=db.getUserDetails();
               // String uname=user.get("name");
                 wowDatabase = Room.databaseBuilder(getActivity(),
                        AppDatabase.class,"wow_db" ).build();

                new UserAsyncTask().execute();


                Toast.makeText(getActivity(),
                        uname, Toast.LENGTH_LONG).show();
               uploadBookDetail(imageString, dateData.getText().toString(),timeData.getText().toString()
                       ,setLocation.getText().toString(),uname,edtxtRemark.getText().toString());

            }
        });

        return v;
    }


    private void uploadBookDetail(final String image,final String date,final String time,final String address,final String name,final String remark){

        String tag_string_req = "req_addbooking";



        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_BOOK, new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
                Log.d(AppController.TAG, "Login Response: " + response.toString());


                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    Toast.makeText(getActivity(),
                            response.toString(), Toast.LENGTH_LONG).show();
                    // Check for error node in json
                    if (!error) {



                    } else {

                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getActivity(),
                                errorMsg+"1", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(AppController.TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getActivity(),
                        error.getMessage(), Toast.LENGTH_LONG).show();

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", name);
                params.put("address",address);
                params.put("image",image);
                params.put("date", date);
                params.put("time",time);
                params.put("remark", remark);
                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private class UserAsyncTask extends AsyncTask<Void,Void,Void> {

        public UserAsyncTask() {

    }

        @Override
        protected Void doInBackground(Void... Voids) {
            List<User> allUsers=wowDatabase.userDao().loadAllUsers();
            uname=allUsers.get(0).getName();
            return null;

        }





    }

}

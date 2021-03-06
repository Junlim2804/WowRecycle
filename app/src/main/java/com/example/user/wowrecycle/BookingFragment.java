package com.example.user.wowrecycle;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;

import androidx.room.Room;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.core.content.ContextCompat;

import android.text.InputType;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.app.Activity.RESULT_OK;


public class BookingFragment extends DialogFragment {
    private EditText setLocation,timeData,dateData,editTextWeight,edtxtRemark;
    //TODO column size change after request should clear all input
    private static String uname,defaddress;
    private Button btnSubmitBook;
    private static int RESULT_LOAD_IMG = 4;
    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL = 2;

    private ImageView uploader;
    Bitmap bitmap;
    String imageString;
    private  AppDatabase wowDatabase;
    private static final String TAG = "BookingFragment";
    private DatePickerDialog.OnDateSetListener mDataSetListener;
    private TimePickerDialog mTimePicker;
    private Spinner spinner;
    private ArrayAdapter<CharSequence> adapter;
    int PLACE_PICKER_REQUEST =1;
    int photoUploaded = 0;
    private TextView labelBook;

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data,getActivity());
                String toastMsg = String.format("Place: %s", place.getName());
                //Toast.makeText(getActivity(), toastMsg, Toast.LENGTH_LONG).show();
                setLocation.setText(place.getAddress());
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
        editTextWeight=(EditText)v.findViewById(R.id.editText);
        final TableLayout tableLayout;
        tableLayout = v.findViewById(R.id.table_layout);
        final TableRow tb1=new TableRow(getActivity());

        //Adding 2 TextViews
        /*for (int i = 1; i <= 2; i++) {
            TextView textView = new TextView(getActivity());
            textView.setText("TextView " + String.valueOf(i));
            linearLayout.addView(textView);
        }*/
        Spinner s1=(Spinner)v.findViewById(R.id.spinner2);

        adapter = ArrayAdapter.createFromResource(getActivity(), R.array.type_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s1.setAdapter(adapter);




        tableLayout.addView(tb1);




        //spinner = (Spinner)v.findViewById(R.id.spinner);
        adapter = ArrayAdapter.createFromResource(getActivity(), R.array.type_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //spinner.setAdapter(adapter);


        Button add=(Button)v.findViewById(R.id.btn_addbook);
        add.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                Spinner s2=new Spinner(getActivity());
                s2.setAdapter(adapter);
                EditText kg=new EditText(getActivity());
                kg.setInputType(InputType.TYPE_CLASS_NUMBER);
                kg.setHint("Enter weight(kg)");
                final TableRow tb2=new TableRow(getActivity());
                Button btn_del=new Button(getActivity());
                btn_del.setText("X");
                btn_del.setBackgroundColor(Color.TRANSPARENT);
                btn_del.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View view) {
                        tableLayout.removeView(tb2);


                    }
                });
                tb2.addView(s2);
                tb2.addView(kg);
                tb2.addView(btn_del);
                tableLayout.addView(tb2,1);


            }
        });

        dateData=(EditText)v.findViewById(R.id.dateData);
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(c);

        dateData.setText(formattedDate);
        dateData.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog=new DatePickerDialog(getActivity(),mDataSetListener,year,month,day);
                dialog.getDatePicker().setMinDate(System.currentTimeMillis());

                dialog.show();
            }
        });
        timeData=(EditText) v.findViewById(R.id.timedata);
        timeData.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                mTimePicker = new TimePickerDialog(getActivity(), AlertDialog.THEME_HOLO_LIGHT,new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                        if(selectedHour<9||selectedHour>21)
                        {
                            timeData.setText(" ");
                            //Toast.makeText(getActivity(),"Out of service Time",Toast.LENGTH_SHORT).show();
                            Toast toast = Toast.makeText(getActivity(),"           Out of service Time\nService time is from 0900 - 2100", Toast.LENGTH_SHORT );
                            View view = toast.getView();
                            TextView text = (TextView) view.findViewById(android.R.id.message);
                            text.setTextColor(Color.parseColor("#FF0000"));

                            toast.show();
                        }
                        else
                            timeData.setText( String.format("%02d:%02d",selectedHour, selectedMinute));
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");

                mTimePicker.show();
            }
        });
        setLocation=(EditText) v.findViewById((R.id.txt_setLocation));
        wowDatabase = Room.databaseBuilder(getActivity(),
                AppDatabase.class,"wow_db" ).build();
        new UserAsyncTask().execute();
        setLocation.setText(defaddress);

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
                String date=year+"-"+month+"-"+dayOfMonth;
                dateData.setText(date);
            }

        };



        //uploader=(ImageView)v.findViewById(R.id.item_image);



        btnSubmitBook=(Button)v.findViewById((R.id.btnUpload));
        edtxtRemark=v.findViewById(R.id.edtxtRemark);


        btnSubmitBook.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                //SQLiteHandler db = new SQLiteHandler(getActivity());
                // HashMap<String, String> user=db.getUserDetails();
                // String uname=user.get("name");
                // new UserAsyncTask().execute();

                if(setLocation.getText().toString().matches("")){
                    //Toast.makeText(getActivity(), "Please fill in the location", Toast.LENGTH_LONG).show();
                    Toast toast = Toast.makeText(getActivity(),"Please fill in the location", Toast.LENGTH_SHORT );
                    View v = toast.getView();
                    TextView text = (TextView) v.findViewById(android.R.id.message);
                    text.setTextColor(Color.parseColor("#FF0000"));

                    toast.show();
                }
                /*else if (spinner.getSelectedItem().toString().equals(null)) {
                    Toast.makeText(getActivity(), "Please select the type of recycled item", Toast.LENGTH_LONG).show();
                }*/
                else if (editTextWeight.getText().toString().matches("")) {
                   // Toast.makeText(getActivity(), "Please fill in the weight of recycled item", Toast.LENGTH_LONG).show();
                    Toast toast = Toast.makeText(getActivity(),"Please fill in the weight of recycled item", Toast.LENGTH_SHORT );
                    View v = toast.getView();
                    TextView text = (TextView) v.findViewById(android.R.id.message);
                    text.setTextColor(Color.parseColor("#FF0000"));

                    toast.show();
                }
                else if (dateData.getText().toString().matches("")) {
                    //Toast.makeText(getActivity(), "Please fill in the date", Toast.LENGTH_LONG).show();
                    Toast toast = Toast.makeText(getActivity(),"Please fill in the date", Toast.LENGTH_SHORT );
                    View v = toast.getView();
                    TextView text = (TextView) v.findViewById(android.R.id.message);
                    text.setTextColor(Color.parseColor("#FF0000"));

                    toast.show();

                }
                else if (timeData.getText().toString().matches("")) {
                    //Toast.makeText(getActivity(), "Please fill in the time", Toast.LENGTH_LONG).show();
                    Toast toast = Toast.makeText(getActivity(),"Please fill in the time", Toast.LENGTH_SHORT );
                    View v = toast.getView();
                    TextView text = (TextView) v.findViewById(android.R.id.message);
                    text.setTextColor(Color.parseColor("#FF0000"));

                }
                //else if (photoUploaded == 0)
               // {
                //    Toast.makeText(getActivity(), "Please insert a picture for convenient pick up", Toast.LENGTH_LONG).show();
               // }
                else {

                    Submit();
                }

                /*Toast.makeText(getActivity(),
                        uname, Toast.LENGTH_LONG).show();
                String tempRemark=edtxtRemark.getText().toString();
                if(tempRemark==null)
                {
                    tempRemark=" ";
                }
               uploadBookDetail(imageString, dateData.getText().toString(),timeData.getText().toString()
                       ,setLocation.getText().toString(),uname,tempRemark,editTextWeight.getText().toString(),spinner.getSelectedItem().toString());*/

            }
        });

        return v;
    }


    private void uploadBookDetail(final String date,final String time,final String address,final String name,
                                  final String remark,final String quantity,final String type){

        String tag_string_req = "req_addbooking";



        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_BOOK, new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
                Log.d(AppController.TAG, "Booking Response: " + response.toString());


                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    // Toast.makeText(getActivity(),esponse.toString(), Toast.LENGTH_LONG).show();
                    // Check for error node in json
                    if (!error||error) {
                       // Toast.makeText(getActivity(),
                        //"Sucesful Submit", Toast.LENGTH_LONG).show();
                        //((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.content,new BookingFragment()).commit();


                    } else {

                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getActivity(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    //Toast.makeText(getActivity(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
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
               // params.put("image",image);
                params.put("date", date);
                params.put("time",time);
                params.put("remark", remark);
                params.put("quantity",quantity);
                params.put("type",type);
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
            defaddress=allUsers.get(0).getAddress();
            return null;
        }
    }

    private void Submit(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Wow Recycle");
        builder.setMessage("Sumbit Request?")
                .setCancelable(false)
                .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                       // uploadBookDetail(dateData.getText().toString(),timeData.getText().toString()
                       //         ,setLocation.getText().toString(),uname,edtxtRemark.getText().toString(),editTextWeight.getText().toString(),spinner.getSelectedItem().toString());
                        Toast.makeText(getActivity(), "Request has been submitted", Toast.LENGTH_LONG).show();
                        clearForm();
                        Intent intent = new Intent(getActivity(), ConfirmActivity.class);
                        startActivity(intent);



                    }
                })
                .setNegativeButton("No",  new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        builder.show();
    }

    private void clearForm()
    {
        setLocation.setText(null);
        //spinner.setSelection(0);
       // editTextWeight.setText(null);
        dateData.setText(null);
        timeData.setText(null);
        edtxtRemark.setText(null);
       // uploader.setImageResource(R.drawable.ic_photo_camera_black_24dp);
    }
}

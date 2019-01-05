package com.example.user.wowrecycle;

import android.app.DatePickerDialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import java.util.Calendar;

import static android.app.Activity.RESULT_OK;


public class BookingFragment extends DialogFragment {
    private Button btnsetLocation;
    private static final String TAG = "BookingFragment";
    private TextView dateData;
    private DatePickerDialog.OnDateSetListener mDataSetListener;
    int PLACE_PICKER_REQUEST =1;
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data,getActivity());
                String toastMsg = String.format("Place: %s", place.getName());
                Toast.makeText(getActivity(), toastMsg, Toast.LENGTH_LONG).show();
            }
        }
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_booking,container,false);
        dateData=(TextView)v.findViewById(R.id.datedata);
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
        btnsetLocation=(Button)v.findViewById((R.id.btnLocation));
        btnsetLocation.setOnClickListener(new View.OnClickListener(){
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
                String date=month+"/"+dayOfMonth+"/"+year;
                dateData.setText(date);
            }

        };
        return v;
    }

}

package com.example.user.wowrecycle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ConfirmActivity extends AppCompatActivity {

    private TextView pointsReceived, colAddress, colDate, colTime;
    private Button btnBack, btnOkay, btnok;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);

        pointsReceived = (TextView) findViewById(R.id.lblPointsReceived);
        colAddress = (TextView) findViewById(R.id.rColAdd);
        colDate = (TextView) findViewById(R.id.rColDate);
        colTime = (TextView) findViewById(R.id.rColTime);
        btnok = (Button) findViewById(R.id.btnOkay);




        // Capture button clicks
        btnok.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                // Start NewActivity.class
                Intent myIntent = new Intent(ConfirmActivity.this,
                        MainActivity.class);
                startActivity(myIntent);
            }
        });
    }




    }



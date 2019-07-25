package com.example.user.wowrecycle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class ConfirmActivity extends AppCompatActivity {

    private TextView pointsReceived, colAddress, colDate, colTime;
    private Button btnBack, btnOkay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);

        pointsReceived = (TextView) findViewById(R.id.lblPointsReceived);
        colAddress = (TextView) findViewById(R.id.rColAdd);
        colDate = (TextView) findViewById(R.id.rColDate);
        colTime = (TextView) findViewById(R.id.rColTime);



    }
}

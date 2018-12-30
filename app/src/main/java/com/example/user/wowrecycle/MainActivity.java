package com.example.user.wowrecycle;

import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.AnimationDrawable;
import android.icu.text.IDNA;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private EditText Username;
    private EditText Password;
    private TextView FPassword;
    private Button Login;
    private TextView Attempt;
    private int counter = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ConstraintLayout constraintLayout = findViewById(R.id.Layout);
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();

        Username = (EditText) findViewById(R.id.eUsername);
        Password = (EditText) findViewById(R.id.ePassword);
        FPassword = (TextView) findViewById(R.id.tvfpasswaord);
        Attempt = (TextView) findViewById(R.id.tvattempt);
        Login = (Button) findViewById(R.id.btnLogin);

        Attempt.setText("Number of attempt remaining: 5");

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate(Username.getText().toString(), Password.getText().toString());

            }
        });
    }

    private void validate(String userName, String userPassword){
        if((userName .equals("ADMIN")) && (userPassword.equals("1234"))){
            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            startActivity(intent);

        }
        else{
            counter--;
            Attempt.setText("Number of attempts remaining: " + String.valueOf(counter));
            if (counter == 0) {
                Login.setEnabled(false);

            }

        }
    }
}

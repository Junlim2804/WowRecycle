package com.example.user.wowrecycle;

import android.app.ProgressDialog;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.user.wowrecycle.DataSource.AppDatabase;
import com.example.user.wowrecycle.Entity.User;


import org.json.JSONException;
import org.json.JSONObject;


import java.util.HashMap;
import java.util.Map;





public class MainActivity extends AppCompatActivity {

    private SessionManager session;
    //private SQLiteHandler db;
    private EditText Username;
    private EditText Password;
    private TextView FPassword;
    private Button Login;
    private TextView Attempt;
    private int counter = 5;
    private ProgressDialog pDialog;

    private AppDatabase wowDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wowDatabase = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, getString(R.string.DATABASENAME)).build();
        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        RelativeLayout relativeLayout = findViewById(R.id.Layout);
        AnimationDrawable animationDrawable = (AnimationDrawable) relativeLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();

        Username = (EditText) findViewById(R.id.eUsername);
        Password = (EditText) findViewById(R.id.txtPassword);
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

        final TextView txtView = this.findViewById(R.id.tvregister);
        txtView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                MainActivity.this.startActivity(new Intent(MainActivity.this, RegisterActivity.class));
            }
        });
        // SQLite database handler
        //db = new SQLiteHandler(getApplicationContext());

        // Session manager
        session = new SessionManager(getApplicationContext());

        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            startActivity(intent);

        }
    }

    private void validate(final String userName, final String userPassword){

        String tag_string_req = "req_login";

        pDialog.setMessage("Logging in ...");
        showDialog();

        StringRequest strReq = new StringRequest(Method.POST,
                AppConfig.URL_LOGIN, new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
                Log.d(AppController.TAG, "Login Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error) {
                        // user successfully logged in
                        // Create login session
                        session.setLogin(true);

                        // Now store the user in SQLite
                        String uid = jObj.getString("uid");

                        JSONObject user = jObj.getJSONObject("user");
                        String name = user.getString("name");
                        String email = user.getString("email");
                        String created_at = user
                                .getString("created_at");
                        String fullname=user.getString("fullName");
                        int bonusPoint=user.getInt("bonusPoint");
                        String hpNo=user.getString("hpNo");
                        String ic=user.getString("icNo");
                        String hpno=user.getString("hpNo");
                        String address=user.getString("address");
                        User loguser=new User(uid,email,name,fullname,ic,bonusPoint,address,hpno);
                        // Inserting row in users table
                        //db.addUser(name, email, uid, created_at);
                        new UserAsyncTask(loguser).execute();

                        // Launch main activity
                        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        // Error in login. Get the error message
                        counter--;
                        Attempt.setText("Number of attempts remaining: " + String.valueOf(counter));
                        if (counter == 0) {
                            Login.setEnabled(false);
                            String errorMsg = jObj.getString("error_msg");
                            Toast.makeText(getApplicationContext(),
                                    errorMsg, Toast.LENGTH_LONG).show();

                        }
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(AppController.TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", userName);
                params.put("password", userPassword);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

        /*
        if((userName .equals("ADMIN")) && (userPassword.equals("1234"))){
            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            startActivity(intent);

        }
        else{
            counter--;
            Attempt.setText("Number of attempts remaining: " + String.valueOf(counter));
            if (counter == 0) {
                Login.setEnabled(false);

            }*/

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    private class UserAsyncTask extends AsyncTask<Void,Void,Void> {
        private User user;
        public UserAsyncTask(User user) {
            this.user=user;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            wowDatabase.userDao().insertUser(user);
            return null;
        }



    }

}

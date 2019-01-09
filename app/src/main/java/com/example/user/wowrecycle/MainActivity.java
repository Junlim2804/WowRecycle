package com.example.user.wowrecycle;

import android.Manifest;
import android.app.ProgressDialog;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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
    int PERMISSION_ALL = 1;
    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL = 2;
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

        String[] PERMISSIONS = {Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_SMS, Manifest.permission.CAMERA};

        boolean permissionGranted = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;

        if(!permissionGranted) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 200);
        }


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            }
            else if (!hasPermissions(this, PERMISSIONS)) {
                ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
            }
            else {


            }
        }

        Username = (EditText) findViewById(R.id.eUsername);
        Password = (EditText) findViewById(R.id.txtPassword);
        FPassword = (TextView) findViewById(R.id.tvfpasswaord);
        Attempt = (TextView) findViewById(R.id.tvattempt);
        Login = (Button) findViewById(R.id.btnLogin);


        Attempt.setText("Number of attempt remaining: 5");

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String in_username=Username.getText().toString();
                String in_password=Password.getText().toString();
                if(in_username.equals("")||in_username==null||in_password==null||in_password.equals(""))
                { Toast.makeText(getApplicationContext(),"Username and Password cannot be blank", Toast.LENGTH_SHORT ).show();
                    return;
                }


                validate(in_username, in_password);

            }
        });

        final TextView txtView = this.findViewById(R.id.tvregister);
        txtView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                MainActivity.this.startActivity(new Intent(MainActivity.this, RegisterActivity.class));
            }
        });

        FPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        session = new SessionManager(getApplicationContext());

        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            startActivity(intent);

        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Login.setEnabled(true);
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    Toast.makeText(getApplicationContext(),"Please Allow Permission",Toast.LENGTH_SHORT).show();
                    Login.setEnabled(false);
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
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


                    if (!error) {

                        session.setLogin(true);

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
                        String imageString=user.getString("image");
                        User loguser=new User(uid,email,name,fullname,ic,bonusPoint,address,hpno,imageString);
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


                        }
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
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

    public static boolean hasPermissions(Context context, String... permissions)
    {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null)
        {
            for (String permission : permissions)
            {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED)
                {
                    return false;
                }
            }
        }
        return true;
    }

}

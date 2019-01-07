package com.example.user.wowrecycle;

import android.app.ProgressDialog;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.user.wowrecycle.DataSource.AppDatabase;
import com.example.user.wowrecycle.Entity.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SubProfileActivity extends AppCompatActivity {
    private static final String TAG = SubProfileActivity.class.getSimpleName();
    private AppDatabase wowDatabase;
    private final static int RESULT_LOAD_IMAGE=1;
    private EditText email,fullname,ic,phoneno,address;
    private TextView username,changephoto;
    private Button btnSubmit;
    private static String uname;
    private Bitmap currentImage;
    private String imageString;
    private ImageView profilePic;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_profile);
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        username=findViewById(R.id.txtUsername);
        email=findViewById(R.id.txtEmail);
        profilePic=findViewById(R.id.imageViewProfilePic);
        fullname=findViewById(R.id.txtName);
        phoneno=findViewById(R.id.txtPhone);
        ic=findViewById(R.id.et_icno);
        address=findViewById(R.id.txtAddress);
        changephoto=findViewById(R.id.change_profile);
        changephoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });
        wowDatabase = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, getString(R.string.DATABASENAME)).build();
        new UserAsyncTask().execute();
        btnSubmit=findViewById(R.id.btnUpdateDetail);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }
    private ProgressDialog pDialog;
    private void UpdateUser(final String name, final String email,
                              final String password) {
        // Tag used to cancel the request
        String tag_string_req = "req_update";

        pDialog.setMessage("Updating");
        pDialog.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_UPDATEUSER, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                Log.d(TAG, "Update Response: " + response.toString());
                pDialog.hide();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        // User successfully stored in MySQL
                        // Now store the user in sqlite
                        String uid = jObj.getString("uid");

                        JSONObject user = jObj.getJSONObject("user");
                        String name = user.getString("name");
                        String email = user.getString("email");
                        String created_at = user
                                .getString("created_at");

                        // Inserting row in users table
                       // db.addUser(name, email, uid, created_at);

                        Toast.makeText(getApplicationContext(), "User successfully registered. Try login now!", Toast.LENGTH_LONG).show();

                        // Launch login activity
                        finish();
                        startActivity(getIntent());


                    } else {

                        // Error occurred in registration. Get the error
                        // message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Registration Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
               pDialog.hide();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", name);
                params.put("email", email);
                params.put("password", password);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK
                && null != data) {
            Uri selectedImage = data.getData();
            if (selectedImage != null) {
                try {
                    currentImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(),selectedImage);
                    profilePic.setImageBitmap(currentImage);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                   currentImage.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] imageBytes = baos.toByteArray();
                    imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }


    }

    private class UserAsyncTask extends AsyncTask<Void,Void,Void> {

        public UserAsyncTask() {

        }

        @Override
        protected Void doInBackground(Void... Voids) {
            List<User> allUsers=wowDatabase.userDao().loadAllUsers();
            username.setText(allUsers.get(0).getName());
            email.setText(allUsers.get(0).getEmail());
            fullname.setText(allUsers.get(0).getFullname());
            ic.setText(allUsers.get(0).getIc());
            phoneno.setText(allUsers.get(0).getHpno());
            address.setText(allUsers.get(0).getAddress());
            Bitmap bitmap;
            try{
                byte [] encodeByte=Base64.decode(allUsers.get(0).getImageString(),Base64.DEFAULT);

                InputStream inputStream  = new ByteArrayInputStream(encodeByte);
                 bitmap= BitmapFactory.decodeStream(inputStream);

            }catch(Exception e){
                e.getMessage();
                return null;

            }
            profilePic.setImageBitmap(bitmap);
            return null;

        }





    }

}



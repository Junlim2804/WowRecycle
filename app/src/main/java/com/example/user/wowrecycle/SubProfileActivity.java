package com.example.user.wowrecycle;

import android.app.Activity;
import android.app.ProgressDialog;
import androidx.room.Room;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
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
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;


public class SubProfileActivity extends AppCompatActivity {
    private static final String TAG = SubProfileActivity.class.getSimpleName();
    private AppDatabase wowDatabase;
    private final static int RESULT_LOAD_IMAGE=1;
    private EditText email,fullname,ic,phoneno,address;
    private TextView username,changephoto;
    private Button btnSubmit;
    private static User curUser;
    private static String uname;
    private Bitmap currentImage;
    private String imageString,uid;
    private ImageView profilePic;
    //TODO Interace column need add header
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
                pickImage();
            }
        });
        wowDatabase = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, getString(R.string.DATABASENAME)).build();
        try {
            new UserAsyncTask().execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        btnSubmit=findViewById(R.id.btnUpdateDetail);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO check all column not null all empty except image
                if (email.getText().toString().matches("") || fullname.getText().toString().matches("") ||
                        phoneno.getText().toString().matches("") || ic.getText().toString().matches("") || address.getText().toString().matches("")) {
                    Toast.makeText(getApplicationContext(), "Please do not leave blank in any field", Toast.LENGTH_LONG).show();
                } else {
                    UpdateUser();
                    Intent i = new Intent(SubProfileActivity.this, SecondActivity.class);
                }
            }
        });


    }
    private ProgressDialog pDialog;
                                            //Crop image

    //PICK IMAGE METHOD
    public void pickImage() {
        CropImage.startPickImageActivity(this);
    }

    private void croprequest(Uri imageUri) {
        CropImage.activity(imageUri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setMultiTouchEnabled(true)
                .start(this);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        //RESULT FROM SELECTED IMAGE
        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri imageUri = CropImage.getPickImageResultUri(this, data);
            croprequest(imageUri);
        }

        //RESULT FROM CROPING ACTIVITY
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), result.getUri());
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 60, baos);
                    byte[] imageBytes = baos.toByteArray();
                    imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                    ((ImageView)findViewById(R.id.imageViewProfilePic)).setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 555 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            pickImage();
        }
    }
                                    //Crop image
    private void UpdateUser() {

        String tag_string_req = "req_update";
        if(imageString==null)
        {

            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.noimage);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] imageBytes = baos.toByteArray();
            imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);

        }

        final String in_email=email.getText().toString();
        final String in_fullname=fullname.getText().toString();
        final String in_hpno=phoneno.getText().toString();
        final String in_ic=ic.getText().toString();
        final String in_address=address.getText().toString();
        pDialog.setMessage("Updating");
        pDialog.show();



        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_UPDATEUSER, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                Log.d(TAG, "Update Response: " + response.toString());


                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    if (!error) {
                        curUser.setEmail(in_email);
                        curUser.setImageString(imageString);
                        curUser.setFullname(in_fullname);
                        curUser.setHpno(in_hpno);
                        curUser.setIc(in_ic);
                        curUser.setAddress(in_address);

                        pDialog.dismiss();
                        pDialog.setMessage("Loading Please Wait");
                        pDialog.show();
                        new UpdateUserAsyncTask(curUser).execute().get();
                        pDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Successfully Updated", Toast.LENGTH_LONG).show();

                        // Launch login activity
                        finish();
                        Intent i=new Intent(SubProfileActivity.this,SecondActivity.class);
                        startActivity(i);


                    } else {
                        pDialog.dismiss();
                        // Error occurred in registration. Get the error
                        // message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),"error:"+
                                errorMsg, Toast.LENGTH_LONG).show();
                        //new UserAsyncTask().execute();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();

                    Toast.makeText(getApplicationContext(), "error:"+e.getMessage(), Toast.LENGTH_LONG).show();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                pDialog.hide();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Update Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
               pDialog.hide();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("uniqueid", uid);
                params.put("email", in_email);
                params.put("fullname",in_fullname);
                params.put("ic",in_ic);
                params.put("hpno",in_hpno);
                params.put("address",in_address);
                params.put("image",imageString);
                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }


//    public void onActivityResult(int requestCode, int resultCode, Intent data)
//    {
//        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK
//                && null != data) {
//            Uri selectedImage = data.getData();
//            if (selectedImage != null) {
//                try {
//                    currentImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(),selectedImage);
//                    profilePic.setImageBitmap(currentImage);
//                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                   currentImage.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//                    byte[] imageBytes = baos.toByteArray();
//                    imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//
//
//    }

    private class UserAsyncTask extends AsyncTask<Void,Void,Void> {

        public UserAsyncTask() {

        }

        @Override
        protected Void doInBackground(Void... Voids) {
            List<User> allUsers=wowDatabase.userDao().loadAllUsers();
            curUser=allUsers.get(0);
            uid=allUsers.get(0).getUid();
            username.setText(allUsers.get(0).getName());
            email.setText(allUsers.get(0).getEmail());
            fullname.setText(allUsers.get(0).getFullname());
            ic.setText(allUsers.get(0).getIc());
            phoneno.setText(allUsers.get(0).getHpno());
            address.setText(allUsers.get(0).getAddress());
            imageString=allUsers.get(0).getImageString();

                Bitmap bitmap;
                try {
                    byte[] encodeByte = Base64.decode(imageString, Base64.DEFAULT);

                    InputStream inputStream = new ByteArrayInputStream(encodeByte);
                    bitmap = BitmapFactory.decodeStream(inputStream);

                } catch (Exception e) {
                    e.getMessage();
                    return null;

                }

                profilePic.setImageBitmap(bitmap);

            return null;

        }





    }

    private class UpdateUserAsyncTask extends AsyncTask<Void,Void,Void> {
        private User user;
        public UpdateUserAsyncTask(User user) {
            this.user=user;
        }

        @Override
        protected Void doInBackground(Void... Voids) {
            wowDatabase.userDao().updateUser(this.user);

            return null;

        }





    }

}



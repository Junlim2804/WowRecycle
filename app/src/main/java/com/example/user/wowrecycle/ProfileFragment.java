package com.example.user.wowrecycle;

import android.app.ProgressDialog;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.user.wowrecycle.DataSource.AppDatabase;
import com.example.user.wowrecycle.Entity.User;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ProfileFragment extends Fragment {


    private boolean isOpen = false;
    private ConstraintSet layout1, layout2;
    private ConstraintLayout constraintLayout;
    private ImageView imageViewPhoto;
    private Button btnRefresh;
    private TextView txtDateProfile;
    private TextView profileBookDetial;
    private static List<BookDetail> BookArrayList=new ArrayList<>();

    private AppDatabase wowDatabase;
    private final static int RESULT_LOAD_IMAGE=1;
    private EditText email,ic,phoneno,address;
    private TextView username,changephoto,fullname;
    private Button btnSubmit;
    private static User curUser;
    private static String uname;
    private Bitmap currentImage;
    private String imageString,uid;
    private ImageView profilePic;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Window = getWindow();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_profile, container, false);

        fullname = v.findViewById(R.id.profile_fullname);
        username = v.findViewById(R.id.profile_username);
        profilePic = v.findViewById(R.id.profile_picture);

        wowDatabase = Room.databaseBuilder(getActivity(),
                AppDatabase.class, getString(R.string.DATABASENAME)).build();
        new UserAsyncTask().execute();
        //View root = inflater.inflate(R.layout.fragment_profile, null);


        downloadBookDetail(getActivity(),AppConfig.URL_GETBOOKDETAIL);
        profileBookDetial=(TextView)v.findViewById(R.id.profileBookDetail);
        btnRefresh=(Button)v.findViewById(R.id.refreshBookDetail);
        txtDateProfile=(TextView)v.findViewById(R.id.txtDateProfile);
        imageViewPhoto=(ImageView)v.findViewById(R.id.imgBookProfile);
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadBookDetail(getActivity(),AppConfig.URL_GETBOOKDETAIL);
                if(BookArrayList.size()!=0) {
                    profileBookDetial.setText(BookArrayList.get(0).getAddress());
                    txtDateProfile.setText(BookArrayList.get(0).getDate());
                    byte[] decodedString = Base64.decode(BookArrayList.get(0).getImage(),Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString,
                            0, decodedString.length);
                    if (decodedByte != null) {
                        imageViewPhoto.setImageBitmap(decodedByte);
                    }
                }
                else
                    profileBookDetial.setText("empty");
                // Inflate the layout for this fragment


            }
        });
        if(BookArrayList.size()!=0)
        profileBookDetial.setText(BookArrayList.toString());
        else
            profileBookDetial.setText("empty");
        // Inflate the layout for this fragment

        return v;

    }

   // private ProgressDialog progressDialog=new ProgressDialog(getActivity());;
    private void downloadBookDetail(Context context, String url) {
        //mPostCommentResponse.requestStarted();
        RequestQueue queue = Volley.newRequestQueue(context);
        url=url+"?name="+uname;
      //  if (!progressDialog.isShowing())
      //     progressDialog.setMessage("Syn with server...");
      //  progressDialog.show();

        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {


                        try{
                            //Clear list
                            BookArrayList.clear();

                            for(int i=0; i < response.length();i++){
                                JSONObject imageResponse = (JSONObject) response.get(i);
                                String name = imageResponse.getString("name");
                                String date = imageResponse.getString("bookdate");
                                String address = imageResponse.getString("address");
                                String image = imageResponse.getString("image");
                                String remark = imageResponse.getString("remark");
                                BookDetail bk=new BookDetail(name,address,image,date,remark);


                                BookArrayList.add(bk);
                            }


                       //   if (progressDialog.isShowing())
                      //         progressDialog.dismiss();

                        }catch (Exception e){
                            Toast.makeText(getActivity(), "Error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(getActivity(), "Error:" + volleyError.getMessage(), Toast.LENGTH_SHORT).show();
                       // if (progressDialog.isShowing())
                         //   progressDialog.dismiss();
                    }
                });

        queue.add(jsonObjectRequest);
    }
    public void onViewCreated(View view, Bundle saveInstanceState){
        //imageViewPhoto = view.findViewById(R.layout.fragment_profile);

    }


    private class UserAsyncTask extends AsyncTask<Void,Void,Void> {

        public UserAsyncTask() {

        }

        @Override
        protected Void doInBackground(Void... Voids) {
            List<User> allUsers=wowDatabase.userDao().loadAllUsers();
            curUser=allUsers.get(0);

            uname=allUsers.get(0).getName();

            fullname.setText(allUsers.get(0).getFullname());
            username.setText(allUsers.get(0).getName());
            imageString=allUsers.get(0).getImageString();
            Bitmap bitmap;
            try{
                byte [] encodeByte=Base64.decode(imageString,Base64.DEFAULT);

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


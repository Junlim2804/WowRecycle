package com.example.user.wowrecycle;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

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
    private static List<BookDetail> BookArrayList=new ArrayList<>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Window = getWindow();

    }
    private TextView profileBookDetial;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //View root = inflater.inflate(R.layout.fragment_profile, null);
        View v=inflater.inflate(R.layout.fragment_profile, container, false);

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
        url=url+"?name="+"LTJ";
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
                                String date = imageResponse.getString(no "bookdate");
                                String address = imageResponse.getString("address");
                                String image = imageResponse.getString("image");
                                String remark = imageResponse.getString("remark");
                                BookDetail bk=new BookDetail(name,address,image,date,remark);

                                Toast.makeText(getActivity(), "ADD 1", Toast.LENGTH_SHORT).show();
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
        //imageViewPhoto = view.findViewById(R.layout.activity_main);

    }
}

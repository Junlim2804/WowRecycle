package com.example.user.wowrecycle;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.user.wowrecycle.Entity.Event;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.example.user.wowrecycle.AppController.TAG;

public class EventFragment extends Fragment {
    View view;
    private RecyclerView myRecyclerView;
    EventAdapter recyclerAdapter;
    private List<Event> listEvent;
    private TextView txturl;
    RequestQueue queue;
    ProgressDialog progressDialog;



    public EventFragment(){

    }

    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_event, container,false);
        myRecyclerView = (RecyclerView)view.findViewById(R.id.event_rv);

        //listEvent = new ArrayList<>();
        loadEvent(listEvent);
        try {
            new DownloadAsyncTask().execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return view;
    }

    private void downloadEvent(Context context, String url) {
        //progressDialog = ProgressDialog.show(getActivity(), "Loading...", "Please wait...", true);
        //progressDialog.setCancelable(true);
        // Instantiate the RequestQueue
        queue = Volley.newRequestQueue(context);
        //progressDialog = ProgressDialog.show(getActivity(), "Loading...", "Please wait...", true);
        //if (!progressDialog.isShowing())
        //   progressDialog.setMessage("Syn with server...");
        //progressDialog.show();

        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(
                url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            listEvent.clear();
                      
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject eventResponse = (JSONObject) response.get(i);
                                String background = eventResponse.getString("background");
                                String eventDesc = eventResponse.getString("eventDesc");
                                String url = eventResponse.getString("url");
                                Event event = new Event(background , eventDesc, url);
                                listEvent.add(event);

                            }
                            recyclerAdapter.notifyDataSetChanged();
                           // if (progressDialog.isShowing())
                           //     progressDialog.dismiss();
                        } catch (Exception e) {
                            Toast.makeText(getContext(), "Error:" + e.getMessage(), Toast.LENGTH_LONG).show();
                         //   if (progressDialog.isShowing())
                           //     progressDialog.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(getContext(), "Error" + volleyError.getMessage(), Toast.LENGTH_LONG).show();
                      //  if (progressDialog.isShowing())
                     //       progressDialog.dismiss();
                    }
                });

        // Set the tag on the request.
        jsonObjectRequest.setTag(TAG);

        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest);
    }

    public void loadEvent(List<Event> e)
    {
        recyclerAdapter = new EventAdapter(getContext(),e);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        myRecyclerView.setLayoutManager(linearLayoutManager);
        myRecyclerView.setAdapter(recyclerAdapter);


    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        listEvent = new ArrayList<>();
        listEvent.add(new Event("", " ", " ", " "));
        //listEvent.add(new event("R.drawable.event2", "Save the Earth, avoid dumping E-Waste", "View", "https://www.google.com/"));
        //listEvent.add(new event("R.drawable.event3", "BROS 321 Trade-In Program", "View", "https://www.bros.com.my/v2/bros-321-trade-in-program/"));
        //listEvent.add(new event("R.drawable.event1", "Plasticity event in Malaysia", "View", "https://www.recyclingtoday.com/article/plasticity-malaysia-october-25-2018-recycling/"));


    }

    private class DownloadAsyncTask extends AsyncTask<Void,Void,Void> {

        public DownloadAsyncTask() {

        }

        @Override
        protected Void doInBackground(Void... Voids) {
            downloadEvent(getActivity(), AppConfig.URL_EVENT);
            return null;
        }
    }
}

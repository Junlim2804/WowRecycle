package com.example.user.wowrecycle;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    private List<History> listHistory;
    private RecyclerView myRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        myRecyclerView = findViewById(R.id.record_rv);

        listHistory = new ArrayList<>();
        listHistory.add(new History("abc","abc","avc","lala",R.drawable.reward2));
        listHistory.add(new History("abc","abc","avc","lala",R.drawable.reward2));
        listHistory.add(new History("abc","abc","avc","lala",R.drawable.reward2));
        listHistory.add(new History("abc","abc","avc","lala",R.drawable.reward2));

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        RecyclerView.LayoutManager rvLayoutManager = layoutManager;

        myRecyclerView.setLayoutManager(rvLayoutManager);

        HistoryAdapter adapter = new HistoryAdapter(this, listHistory);

        myRecyclerView.setAdapter(adapter);




    }
}
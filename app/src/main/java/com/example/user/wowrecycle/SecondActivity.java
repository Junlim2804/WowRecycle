package com.example.user.wowrecycle;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {
private DrawerLayout mDrawerLayout;
private ActionBarDrawerToggle mToggle;



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    transaction.replace(R.id.content,new HomeFragment()).commit();
                    return true;
                case R.id.navigation_event:
                    transaction.replace(R.id.content,new EventFragment()).commit();
                    return true;
                case R.id.navigation_book:
                    transaction.replace(R.id.content,new BookingFragment()).commit();
                    return true;
                case R.id.navigation_reward:
                    transaction.replace(R.id.content,new RewardFragment()).commit();
                    return true;
                case R.id.navigation_profile:
                    transaction.replace(R.id.content,new ProfileFragment()).commit();
                    return true;




            }
            return false;
        }
    };



    private NavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelected
            = new NavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            SessionManager session = new SessionManager(getApplicationContext());
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            switch (item.getItemId()) {
                case R.id.snav_profile:
                    transaction.replace(R.id.content,new ProfileFragment()).commit();
                    return true;
                case R.id.snav_history:
                    transaction.replace(R.id.content,new SubHistory()).commit();
                    return true;
                case R.id.snav_logout:
                    Intent intent = new Intent(SecondActivity.this,MainActivity.class);
                    session.setLogin(false);
                    SQLiteHandler db= new SQLiteHandler(getApplicationContext());
                    db.deleteUsers();
                    startActivity(intent);

            }
            return false;
        }
    };





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        NavigationView sidenavigation = (NavigationView) findViewById(R.id.sidenavigation);
        sidenavigation.setNavigationItemSelectedListener(mOnNavigationItemSelected);


        mDrawerLayout = (DrawerLayout)findViewById(R.id.container);
        mToggle = new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open, R.string.close);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       if(mToggle.onOptionsItemSelected(item)){
           return true;
       }
        return super.onOptionsItemSelected(item);
    }



}

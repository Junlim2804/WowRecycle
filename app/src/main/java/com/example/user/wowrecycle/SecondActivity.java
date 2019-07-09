package com.example.user.wowrecycle;

import androidx.room.Room;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.user.wowrecycle.DataSource.AppDatabase;

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
                    transaction.replace(R.id.content,new HomeFragment()).
                            addToBackStack(null).commit();
                    return true;
                case R.id.navigation_event:
                    transaction.replace(R.id.content,new EventFragment()).
                            addToBackStack(null).commit();
                    return true;
                case R.id.navigation_book:
                    transaction.replace(R.id.content,new BookingFragment()).
                            addToBackStack(null).commit();
                    return true;
                case R.id.navigation_reward:
                    transaction.replace(R.id.content,new RewardFragment()).
                            addToBackStack(null).commit();
                    return true;
                case R.id.navigation_profile:
                    transaction.replace(R.id.content,new ProfileFragment()).
                            addToBackStack(null).commit();
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
            Intent i;
            switch (item.getItemId()) {
                case R.id.snav_profile:
                    i = new Intent(SecondActivity.this, SubProfileActivity.class);
                    startActivity(i);
                    break;

                case R.id.snav_history:
                    i = new Intent(SecondActivity.this, HistoryActivity.class);
                    startActivity(i);
                    break;

                case R.id.snav_logout:
                    Intent intent = new Intent(SecondActivity.this,MainActivity.class);
                    session.setLogin(false);
                    wowDatabase = Room.databaseBuilder(getApplicationContext(),
                            AppDatabase.class, getString(R.string.DATABASENAME)).build();
                    new UserAsyncTask().execute();
                    startActivity(intent);


            }
            return false;
        }
    };
    AppDatabase  wowDatabase;
    private class UserAsyncTask extends AsyncTask<Void,Void,Void> {

        public UserAsyncTask() {

        }

        @Override
        protected Void doInBackground(Void... voids) {
            wowDatabase.userDao().deleteAll();
            return null;
        }



    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        if(savedInstanceState == null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();

            transaction.replace(R.id.content,new HomeFragment());
            transaction.commit();
        }

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

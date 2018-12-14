package com.example.user.wowrecycle;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment=null;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    selectedFragment=new HomeFragment();
                 //   mTextMessage.setText(R.string.title_home);

                    break;
                case R.id.navigation_event:

                  //  mTextMessage.setText(R.string.title_event);
                    selectedFragment=new EventFragment();
                    break;
                case R.id.navigation_book:
                    selectedFragment=new BookFragment();
                 //   mTextMessage.setText(R.string.title_book);
                    break;
                case R.id.navigation_reward:
                 //   mTextMessage.setText(R.string.title_reward);
                    break;
                case R.id.navigation_profile:
                 //   mTextMessage.setText(R.string.title_profile);
                    break;

            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment).commit();
            return true;
        }


    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
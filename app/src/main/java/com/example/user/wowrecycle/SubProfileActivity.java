package com.example.user.wowrecycle;

import android.arch.persistence.room.Room;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.example.user.wowrecycle.DataSource.AppDatabase;
import com.example.user.wowrecycle.Entity.User;

import java.util.List;


public class SubProfileActivity extends AppCompatActivity {
    private AppDatabase wowDatabase;

    private EditText email,fullname,ic,phoneno;
    private TextView username;
    private static String uname;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_profile);
        username=findViewById(R.id.txtUsername);
        email=findViewById(R.id.txtEmail);
        fullname=findViewById(R.id.txtName);
        phoneno=findViewById(R.id.txtPhone);
        ic=findViewById(R.id.et_icno);

        wowDatabase = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, getString(R.string.DATABASENAME)).build();
        new UserAsyncTask().execute();

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
            return null;

        }





    }

}

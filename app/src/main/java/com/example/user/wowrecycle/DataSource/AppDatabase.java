package com.example.user.wowrecycle.DataSource;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.user.wowrecycle.Entity.User;

@Database(entities = {User.class}, version = 1)
public abstract class  AppDatabase extends RoomDatabase {

    private static volatile AppDatabase INSTANCE;

    public abstract UserDao userDao();

    static AppDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (AppDatabase.class){
                if(INSTANCE == null){
                    //Create database here
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "user_db")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
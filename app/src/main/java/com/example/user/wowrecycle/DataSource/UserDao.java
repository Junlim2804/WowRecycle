package com.example.user.wowrecycle.DataSource;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.user.wowrecycle.Entity.User;

import java.util.List;
@Dao
public interface UserDao {

    @Query("SELECT * FROM user")
    List<User> loadAllUsers();

    @Query("SELECT * FROM user WHERE name=:name")
    List<User> findUserByName(String name);

    @Insert
    void insertUser(User user);

    @Update
    void updateUser(User user);

    @Query("DELETE FROM user")
    void deleteAll();

    @Delete
    void deleteUser(User user);
}

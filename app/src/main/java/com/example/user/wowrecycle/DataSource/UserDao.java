package com.example.user.wowrecycle.DataSource;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

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

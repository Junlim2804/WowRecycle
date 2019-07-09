package com.example.user.wowrecycle.DataSource;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.user.wowrecycle.Entity.Redeem;

import java.util.List;

@Dao
public interface RedeemDao {

    @Query("SELECT * FROM redeemTable")
    List<Redeem> loadAllRedeemedRewards();

    @Query("SELECT * FROM redeemTable rd, reward rw WHERE rd.rName=:name AND rd.rIndex = rw.rwID")
    List<Redeem> findRedeemedByName(String name);

    @Insert
    void insertRedeemed(Redeem redeem);

    @Update
    void updateRedeemed(Redeem redeem);

    @Query("DELETE FROM redeemTable")
    void deleteAllRedeemed();

    @Delete
    void deleteRedeemed(Redeem redeem);
}

package com.example.user.wowrecycle.DataSource;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

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

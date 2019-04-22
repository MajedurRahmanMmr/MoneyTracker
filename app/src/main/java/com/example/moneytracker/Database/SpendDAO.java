package com.example.moneytracker.Database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface SpendDAO {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMonthlyData(Spend data);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllMonthlyData(ArrayList<Spend> data);

    @Query("select * from spend ")
    List<Spend> getMonthlyDataById();


    @Query("select * from spend ")
    List<Spend> getAllMonthlyData();


    @Query("select * from spend ")
    LiveData<List<Spend>> getAllMonthlyDataLive();
}


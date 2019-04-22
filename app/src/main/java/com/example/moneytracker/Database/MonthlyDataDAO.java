package com.example.moneytracker.Database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface MonthlyDataDAO {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMonthlyData(MonthlyData data);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllMonthlyData(ArrayList<MonthlyData> data);

    @Query("select * from monthlydata where monthId=:monthId")
    MonthlyData getMonthlyDataById(int monthId);


    @Query("select * from monthlydata ")
    List<MonthlyData> getAllMonthlyData();


    @Query("select * from monthlydata ")
    LiveData<List<MonthlyData>> getAllMonthlyDataLive();
}


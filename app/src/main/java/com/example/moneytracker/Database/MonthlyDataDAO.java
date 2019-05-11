package com.example.moneytracker.Database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.ArrayList;
import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface MonthlyDataDAO {


    @Insert(onConflict = REPLACE)
    void insertMonthlyData(MonthlyData data);


    @Insert(onConflict = REPLACE)
    void insertAllMonthlyData(ArrayList<MonthlyData> data);

    @Query("select * from monthlydata where monthId=:monthId")
    MonthlyData getMonthlyDataById(int monthId);


    @Query("select * from monthlydata ")
    List<MonthlyData> getAllMonthlyData();


    @Query("select * from monthlydata ")
    LiveData<List<MonthlyData>> getAllMonthlyDataLive();


    @Query("select * from dailyspenddm where month=:month")
    LiveData<List<DailySpendDM>> getAllDailyDataByMonthLive(int month);

    @Query("select * from dailyspenddm where month=:month")
    LiveData<List<DailySpendDM>> getAllDailyDataLive(int month);

    @Query("select * from dailyspenddm")
    LiveData<List<DailySpendDM>> getAllDailyDataLive();


    @Query("select * from dailyspenddm where month=:month")
    List<DailySpendDM> getAllDailyDataByMonth(int month);


    @Query("select * from dailyspenddm where date>=:start and date< :end")
    List<DailySpendDM> getAllDailyDataByDate(long start, long end);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDaylySpend(DailySpendDM dailySpendDM);
}


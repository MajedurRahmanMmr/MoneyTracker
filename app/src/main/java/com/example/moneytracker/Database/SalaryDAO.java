package com.example.moneytracker.Database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface SalaryDAO {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMonthlyData(Salary data);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMonthlyData(SalaryMonth data);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllMonthlyData(ArrayList<Salary> data);

    @Query("select * from salary ")
    List<Salary> getMonthlyDataById();


    @Query("select * from salary ")
    List<Salary> getAllMonthlyData();

    @Query("select * from salarymonth")
    List<SalaryMonth> getAllMonthlySalaryData();

    @Query("select * from salarymonth where monthId=:month")
    SalaryMonth getAllMonthlyDataByMonthId(int month);

    @Query("select * from salary ")
    LiveData<List<Salary>> getAllMonthlyDataLive();
}


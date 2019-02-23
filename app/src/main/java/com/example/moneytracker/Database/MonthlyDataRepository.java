package com.example.moneytracker.Database;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import java.util.List;

public class MonthlyDataRepository {

    private MonthlyDataDAO mWordDao;
    private LiveData<List<MonthlyData>> mAllWords;

   public MonthlyDataRepository(Application application) {
        RoomDataBase db = RoomDataBase.getDatabase(application);
        mWordDao = db.wordDao();
    }

    LiveData<List<MonthlyData>> getAllWords() {
        return mWordDao.getAllMonthlyDataLive();
    }
}
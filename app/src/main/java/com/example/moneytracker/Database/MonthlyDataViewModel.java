package com.example.moneytracker.Database;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

public class MonthlyDataViewModel extends AndroidViewModel {

    private MonthlyDataRepository mRepository;

    private LiveData<List<MonthlyData>> mAllWords;

    public MonthlyDataViewModel(Application application) {
        super(application);
        mRepository = new MonthlyDataRepository(application);
    }

    public LiveData<List<MonthlyData>> getAllMonthlyIncomeObserver() {
        return mRepository.getAllWords();
    }


}
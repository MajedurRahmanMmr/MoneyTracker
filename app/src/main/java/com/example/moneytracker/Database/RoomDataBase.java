package com.example.moneytracker.Database;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;


@Database(entities = {MonthlyData.class, Salary.class, Spend.class, DailySpendDM.class, SalaryMonth.class}, version = 8)
public abstract class RoomDataBase extends RoomDatabase {

    public abstract MonthlyDataDAO monthlyDataDAO();
    public abstract SalaryDAO salaryDAO();
    public abstract SpendDAO spendDAO();

    private static volatile RoomDataBase INSTANCE;

   public static RoomDataBase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (RoomDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            RoomDataBase.class, "money_db.sqlite")
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }



}
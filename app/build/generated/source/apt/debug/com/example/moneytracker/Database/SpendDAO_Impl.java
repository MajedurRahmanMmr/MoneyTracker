package com.example.moneytracker.Database;

import android.arch.lifecycle.ComputableLiveData;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.db.SupportSQLiteStatement;
import android.arch.persistence.room.EntityInsertionAdapter;
import android.arch.persistence.room.InvalidationTracker.Observer;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.RoomSQLiteQuery;
import android.database.Cursor;
import android.support.annotation.NonNull;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@SuppressWarnings("unchecked")
public class SpendDAO_Impl implements SpendDAO {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfSalary;

  public SpendDAO_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfSalary = new EntityInsertionAdapter<Salary>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `Salary`(`id`,`salaryAmount`,`updatedTime`) VALUES (?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Salary value) {
        stmt.bindLong(1, value.getId());
        stmt.bindLong(2, value.getSalaryAmount());
        stmt.bindLong(3, value.getUpdatedTime());
      }
    };
  }

  @Override
  public void insertMonthlyData(Salary data) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfSalary.insert(data);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void insertAllMonthlyData(ArrayList<Salary> data) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfSalary.insert(data);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<Salary> getMonthlyDataById() {
    final String _sql = "select * from salary ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
      final int _cursorIndexOfSalaryAmount = _cursor.getColumnIndexOrThrow("salaryAmount");
      final int _cursorIndexOfUpdatedTime = _cursor.getColumnIndexOrThrow("updatedTime");
      final List<Salary> _result = new ArrayList<Salary>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Salary _item;
        _item = new Salary();
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        _item.setId(_tmpId);
        final int _tmpSalaryAmount;
        _tmpSalaryAmount = _cursor.getInt(_cursorIndexOfSalaryAmount);
        _item.setSalaryAmount(_tmpSalaryAmount);
        final long _tmpUpdatedTime;
        _tmpUpdatedTime = _cursor.getLong(_cursorIndexOfUpdatedTime);
        _item.setUpdatedTime(_tmpUpdatedTime);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<Salary> getAllMonthlyData() {
    final String _sql = "select * from salary ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
      final int _cursorIndexOfSalaryAmount = _cursor.getColumnIndexOrThrow("salaryAmount");
      final int _cursorIndexOfUpdatedTime = _cursor.getColumnIndexOrThrow("updatedTime");
      final List<Salary> _result = new ArrayList<Salary>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Salary _item;
        _item = new Salary();
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        _item.setId(_tmpId);
        final int _tmpSalaryAmount;
        _tmpSalaryAmount = _cursor.getInt(_cursorIndexOfSalaryAmount);
        _item.setSalaryAmount(_tmpSalaryAmount);
        final long _tmpUpdatedTime;
        _tmpUpdatedTime = _cursor.getLong(_cursorIndexOfUpdatedTime);
        _item.setUpdatedTime(_tmpUpdatedTime);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public LiveData<List<Salary>> getAllMonthlyDataLive() {
    final String _sql = "select * from salary ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return new ComputableLiveData<List<Salary>>() {
      private Observer _observer;

      @Override
      protected List<Salary> compute() {
        if (_observer == null) {
          _observer = new Observer("salary") {
            @Override
            public void onInvalidated(@NonNull Set<String> tables) {
              invalidate();
            }
          };
          __db.getInvalidationTracker().addWeakObserver(_observer);
        }
        final Cursor _cursor = __db.query(_statement);
        try {
          final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
          final int _cursorIndexOfSalaryAmount = _cursor.getColumnIndexOrThrow("salaryAmount");
          final int _cursorIndexOfUpdatedTime = _cursor.getColumnIndexOrThrow("updatedTime");
          final List<Salary> _result = new ArrayList<Salary>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Salary _item;
            _item = new Salary();
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            _item.setId(_tmpId);
            final int _tmpSalaryAmount;
            _tmpSalaryAmount = _cursor.getInt(_cursorIndexOfSalaryAmount);
            _item.setSalaryAmount(_tmpSalaryAmount);
            final long _tmpUpdatedTime;
            _tmpUpdatedTime = _cursor.getLong(_cursorIndexOfUpdatedTime);
            _item.setUpdatedTime(_tmpUpdatedTime);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    }.getLiveData();
  }
}

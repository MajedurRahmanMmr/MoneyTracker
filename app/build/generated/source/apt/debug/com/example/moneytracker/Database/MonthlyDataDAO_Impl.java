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
public class MonthlyDataDAO_Impl implements MonthlyDataDAO {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfMonthlyData;

  public MonthlyDataDAO_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfMonthlyData = new EntityInsertionAdapter<MonthlyData>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `MonthlyData`(`monthId`,`monthName`,`monthlyIncome`,`monthlySpend`) VALUES (?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, MonthlyData value) {
        stmt.bindLong(1, value.getMonthId());
        if (value.getMonthName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getMonthName());
        }
        stmt.bindLong(3, value.getMonthlyIncome());
        stmt.bindLong(4, value.getMonthlySpend());
      }
    };
  }

  @Override
  public void insertMonthlyData(MonthlyData data) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfMonthlyData.insert(data);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void insertAllMonthlyData(ArrayList<MonthlyData> data) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfMonthlyData.insert(data);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<MonthlyData> getMonthlyDataById(int monthId) {
    final String _sql = "select * from monthlydata where monthId=?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, monthId);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfMonthId = _cursor.getColumnIndexOrThrow("monthId");
      final int _cursorIndexOfMonthName = _cursor.getColumnIndexOrThrow("monthName");
      final int _cursorIndexOfMonthlyIncome = _cursor.getColumnIndexOrThrow("monthlyIncome");
      final int _cursorIndexOfMonthlySpend = _cursor.getColumnIndexOrThrow("monthlySpend");
      final List<MonthlyData> _result = new ArrayList<MonthlyData>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final MonthlyData _item;
        final int _tmpMonthId;
        _tmpMonthId = _cursor.getInt(_cursorIndexOfMonthId);
        _item = new MonthlyData(_tmpMonthId);
        final String _tmpMonthName;
        _tmpMonthName = _cursor.getString(_cursorIndexOfMonthName);
        _item.setMonthName(_tmpMonthName);
        final int _tmpMonthlyIncome;
        _tmpMonthlyIncome = _cursor.getInt(_cursorIndexOfMonthlyIncome);
        _item.setMonthlyIncome(_tmpMonthlyIncome);
        final int _tmpMonthlySpend;
        _tmpMonthlySpend = _cursor.getInt(_cursorIndexOfMonthlySpend);
        _item.setMonthlySpend(_tmpMonthlySpend);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<MonthlyData> getAllMonthlyData() {
    final String _sql = "select * from monthlydata ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfMonthId = _cursor.getColumnIndexOrThrow("monthId");
      final int _cursorIndexOfMonthName = _cursor.getColumnIndexOrThrow("monthName");
      final int _cursorIndexOfMonthlyIncome = _cursor.getColumnIndexOrThrow("monthlyIncome");
      final int _cursorIndexOfMonthlySpend = _cursor.getColumnIndexOrThrow("monthlySpend");
      final List<MonthlyData> _result = new ArrayList<MonthlyData>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final MonthlyData _item;
        final int _tmpMonthId;
        _tmpMonthId = _cursor.getInt(_cursorIndexOfMonthId);
        _item = new MonthlyData(_tmpMonthId);
        final String _tmpMonthName;
        _tmpMonthName = _cursor.getString(_cursorIndexOfMonthName);
        _item.setMonthName(_tmpMonthName);
        final int _tmpMonthlyIncome;
        _tmpMonthlyIncome = _cursor.getInt(_cursorIndexOfMonthlyIncome);
        _item.setMonthlyIncome(_tmpMonthlyIncome);
        final int _tmpMonthlySpend;
        _tmpMonthlySpend = _cursor.getInt(_cursorIndexOfMonthlySpend);
        _item.setMonthlySpend(_tmpMonthlySpend);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public LiveData<List<MonthlyData>> getAllMonthlyDataLive() {
    final String _sql = "select * from monthlydata ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return new ComputableLiveData<List<MonthlyData>>() {
      private Observer _observer;

      @Override
      protected List<MonthlyData> compute() {
        if (_observer == null) {
          _observer = new Observer("monthlydata") {
            @Override
            public void onInvalidated(@NonNull Set<String> tables) {
              invalidate();
            }
          };
          __db.getInvalidationTracker().addWeakObserver(_observer);
        }
        final Cursor _cursor = __db.query(_statement);
        try {
          final int _cursorIndexOfMonthId = _cursor.getColumnIndexOrThrow("monthId");
          final int _cursorIndexOfMonthName = _cursor.getColumnIndexOrThrow("monthName");
          final int _cursorIndexOfMonthlyIncome = _cursor.getColumnIndexOrThrow("monthlyIncome");
          final int _cursorIndexOfMonthlySpend = _cursor.getColumnIndexOrThrow("monthlySpend");
          final List<MonthlyData> _result = new ArrayList<MonthlyData>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final MonthlyData _item;
            final int _tmpMonthId;
            _tmpMonthId = _cursor.getInt(_cursorIndexOfMonthId);
            _item = new MonthlyData(_tmpMonthId);
            final String _tmpMonthName;
            _tmpMonthName = _cursor.getString(_cursorIndexOfMonthName);
            _item.setMonthName(_tmpMonthName);
            final int _tmpMonthlyIncome;
            _tmpMonthlyIncome = _cursor.getInt(_cursorIndexOfMonthlyIncome);
            _item.setMonthlyIncome(_tmpMonthlyIncome);
            final int _tmpMonthlySpend;
            _tmpMonthlySpend = _cursor.getInt(_cursorIndexOfMonthlySpend);
            _item.setMonthlySpend(_tmpMonthlySpend);
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

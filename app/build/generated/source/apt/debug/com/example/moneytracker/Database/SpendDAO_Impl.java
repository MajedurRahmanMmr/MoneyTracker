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

  private final EntityInsertionAdapter __insertionAdapterOfSpend;

  public SpendDAO_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfSpend = new EntityInsertionAdapter<Spend>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `Spend`(`id`,`monthId`,`spendAmount`,`time`) VALUES (nullif(?, 0),?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Spend value) {
        stmt.bindLong(1, value.getId());
        stmt.bindLong(2, value.getMonthId());
        stmt.bindLong(3, value.getSpendAmount());
        stmt.bindLong(4, value.getTime());
      }
    };
  }

  @Override
  public void insertMonthlyData(Spend data) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfSpend.insert(data);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void insertAllMonthlyData(ArrayList<Spend> data) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfSpend.insert(data);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<Spend> getMonthlyDataById() {
    final String _sql = "select * from spend ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
      final int _cursorIndexOfMonthId = _cursor.getColumnIndexOrThrow("monthId");
      final int _cursorIndexOfSpendAmount = _cursor.getColumnIndexOrThrow("spendAmount");
      final int _cursorIndexOfTime = _cursor.getColumnIndexOrThrow("time");
      final List<Spend> _result = new ArrayList<Spend>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Spend _item;
        _item = new Spend();
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        _item.setId(_tmpId);
        final int _tmpMonthId;
        _tmpMonthId = _cursor.getInt(_cursorIndexOfMonthId);
        _item.setMonthId(_tmpMonthId);
        final int _tmpSpendAmount;
        _tmpSpendAmount = _cursor.getInt(_cursorIndexOfSpendAmount);
        _item.setSpendAmount(_tmpSpendAmount);
        final long _tmpTime;
        _tmpTime = _cursor.getLong(_cursorIndexOfTime);
        _item.setTime(_tmpTime);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<Spend> getAllMonthlyData() {
    final String _sql = "select * from spend ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
      final int _cursorIndexOfMonthId = _cursor.getColumnIndexOrThrow("monthId");
      final int _cursorIndexOfSpendAmount = _cursor.getColumnIndexOrThrow("spendAmount");
      final int _cursorIndexOfTime = _cursor.getColumnIndexOrThrow("time");
      final List<Spend> _result = new ArrayList<Spend>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Spend _item;
        _item = new Spend();
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        _item.setId(_tmpId);
        final int _tmpMonthId;
        _tmpMonthId = _cursor.getInt(_cursorIndexOfMonthId);
        _item.setMonthId(_tmpMonthId);
        final int _tmpSpendAmount;
        _tmpSpendAmount = _cursor.getInt(_cursorIndexOfSpendAmount);
        _item.setSpendAmount(_tmpSpendAmount);
        final long _tmpTime;
        _tmpTime = _cursor.getLong(_cursorIndexOfTime);
        _item.setTime(_tmpTime);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public LiveData<List<Spend>> getAllMonthlyDataLive() {
    final String _sql = "select * from spend ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return new ComputableLiveData<List<Spend>>() {
      private Observer _observer;

      @Override
      protected List<Spend> compute() {
        if (_observer == null) {
          _observer = new Observer("spend") {
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
          final int _cursorIndexOfMonthId = _cursor.getColumnIndexOrThrow("monthId");
          final int _cursorIndexOfSpendAmount = _cursor.getColumnIndexOrThrow("spendAmount");
          final int _cursorIndexOfTime = _cursor.getColumnIndexOrThrow("time");
          final List<Spend> _result = new ArrayList<Spend>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Spend _item;
            _item = new Spend();
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            _item.setId(_tmpId);
            final int _tmpMonthId;
            _tmpMonthId = _cursor.getInt(_cursorIndexOfMonthId);
            _item.setMonthId(_tmpMonthId);
            final int _tmpSpendAmount;
            _tmpSpendAmount = _cursor.getInt(_cursorIndexOfSpendAmount);
            _item.setSpendAmount(_tmpSpendAmount);
            final long _tmpTime;
            _tmpTime = _cursor.getLong(_cursorIndexOfTime);
            _item.setTime(_tmpTime);
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

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

  private final EntityInsertionAdapter __insertionAdapterOfDailySpendDM;

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
    this.__insertionAdapterOfDailySpendDM = new EntityInsertionAdapter<DailySpendDM>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `DailySpendDM`(`id`,`date`,`month`,`amount`,`type`) VALUES (nullif(?, 0),?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, DailySpendDM value) {
        stmt.bindLong(1, value.getId());
        stmt.bindLong(2, value.getDate());
        stmt.bindLong(3, value.getMonth());
        stmt.bindLong(4, value.getAmount());
        stmt.bindLong(5, value.getType());
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
  public void insertDaylySpend(DailySpendDM dailySpendDM) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfDailySpendDM.insert(dailySpendDM);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public MonthlyData getMonthlyDataById(int monthId) {
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
      final MonthlyData _result;
      if(_cursor.moveToFirst()) {
        final int _tmpMonthId;
        _tmpMonthId = _cursor.getInt(_cursorIndexOfMonthId);
        _result = new MonthlyData(_tmpMonthId);
        final String _tmpMonthName;
        _tmpMonthName = _cursor.getString(_cursorIndexOfMonthName);
        _result.setMonthName(_tmpMonthName);
        final int _tmpMonthlyIncome;
        _tmpMonthlyIncome = _cursor.getInt(_cursorIndexOfMonthlyIncome);
        _result.setMonthlyIncome(_tmpMonthlyIncome);
        final int _tmpMonthlySpend;
        _tmpMonthlySpend = _cursor.getInt(_cursorIndexOfMonthlySpend);
        _result.setMonthlySpend(_tmpMonthlySpend);
      } else {
        _result = null;
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

  @Override
  public LiveData<List<DailySpendDM>> getAllDailyDataByMonthLive(int month) {
    final String _sql = "select * from dailyspenddm where month=?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, month);
    return new ComputableLiveData<List<DailySpendDM>>() {
      private Observer _observer;

      @Override
      protected List<DailySpendDM> compute() {
        if (_observer == null) {
          _observer = new Observer("dailyspenddm") {
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
          final int _cursorIndexOfDate = _cursor.getColumnIndexOrThrow("date");
          final int _cursorIndexOfMonth = _cursor.getColumnIndexOrThrow("month");
          final int _cursorIndexOfAmount = _cursor.getColumnIndexOrThrow("amount");
          final int _cursorIndexOfType = _cursor.getColumnIndexOrThrow("type");
          final List<DailySpendDM> _result = new ArrayList<DailySpendDM>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final DailySpendDM _item;
            final long _tmpDate;
            _tmpDate = _cursor.getLong(_cursorIndexOfDate);
            final int _tmpMonth;
            _tmpMonth = _cursor.getInt(_cursorIndexOfMonth);
            final int _tmpAmount;
            _tmpAmount = _cursor.getInt(_cursorIndexOfAmount);
            final int _tmpType;
            _tmpType = _cursor.getInt(_cursorIndexOfType);
            _item = new DailySpendDM(_tmpDate,_tmpAmount,_tmpType,_tmpMonth);
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            _item.setId(_tmpId);
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

  @Override
  public LiveData<List<DailySpendDM>> getAllDailyDataLive(int month) {
    final String _sql = "select * from dailyspenddm where month=?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, month);
    return new ComputableLiveData<List<DailySpendDM>>() {
      private Observer _observer;

      @Override
      protected List<DailySpendDM> compute() {
        if (_observer == null) {
          _observer = new Observer("dailyspenddm") {
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
          final int _cursorIndexOfDate = _cursor.getColumnIndexOrThrow("date");
          final int _cursorIndexOfMonth = _cursor.getColumnIndexOrThrow("month");
          final int _cursorIndexOfAmount = _cursor.getColumnIndexOrThrow("amount");
          final int _cursorIndexOfType = _cursor.getColumnIndexOrThrow("type");
          final List<DailySpendDM> _result = new ArrayList<DailySpendDM>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final DailySpendDM _item;
            final long _tmpDate;
            _tmpDate = _cursor.getLong(_cursorIndexOfDate);
            final int _tmpMonth;
            _tmpMonth = _cursor.getInt(_cursorIndexOfMonth);
            final int _tmpAmount;
            _tmpAmount = _cursor.getInt(_cursorIndexOfAmount);
            final int _tmpType;
            _tmpType = _cursor.getInt(_cursorIndexOfType);
            _item = new DailySpendDM(_tmpDate,_tmpAmount,_tmpType,_tmpMonth);
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            _item.setId(_tmpId);
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

  @Override
  public LiveData<List<DailySpendDM>> getAllDailyDataLive() {
    final String _sql = "select * from dailyspenddm";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return new ComputableLiveData<List<DailySpendDM>>() {
      private Observer _observer;

      @Override
      protected List<DailySpendDM> compute() {
        if (_observer == null) {
          _observer = new Observer("dailyspenddm") {
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
          final int _cursorIndexOfDate = _cursor.getColumnIndexOrThrow("date");
          final int _cursorIndexOfMonth = _cursor.getColumnIndexOrThrow("month");
          final int _cursorIndexOfAmount = _cursor.getColumnIndexOrThrow("amount");
          final int _cursorIndexOfType = _cursor.getColumnIndexOrThrow("type");
          final List<DailySpendDM> _result = new ArrayList<DailySpendDM>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final DailySpendDM _item;
            final long _tmpDate;
            _tmpDate = _cursor.getLong(_cursorIndexOfDate);
            final int _tmpMonth;
            _tmpMonth = _cursor.getInt(_cursorIndexOfMonth);
            final int _tmpAmount;
            _tmpAmount = _cursor.getInt(_cursorIndexOfAmount);
            final int _tmpType;
            _tmpType = _cursor.getInt(_cursorIndexOfType);
            _item = new DailySpendDM(_tmpDate,_tmpAmount,_tmpType,_tmpMonth);
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            _item.setId(_tmpId);
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

  @Override
  public List<DailySpendDM> getAllDailyDataByMonth(int month) {
    final String _sql = "select * from dailyspenddm where month=?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, month);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
      final int _cursorIndexOfDate = _cursor.getColumnIndexOrThrow("date");
      final int _cursorIndexOfMonth = _cursor.getColumnIndexOrThrow("month");
      final int _cursorIndexOfAmount = _cursor.getColumnIndexOrThrow("amount");
      final int _cursorIndexOfType = _cursor.getColumnIndexOrThrow("type");
      final List<DailySpendDM> _result = new ArrayList<DailySpendDM>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final DailySpendDM _item;
        final long _tmpDate;
        _tmpDate = _cursor.getLong(_cursorIndexOfDate);
        final int _tmpMonth;
        _tmpMonth = _cursor.getInt(_cursorIndexOfMonth);
        final int _tmpAmount;
        _tmpAmount = _cursor.getInt(_cursorIndexOfAmount);
        final int _tmpType;
        _tmpType = _cursor.getInt(_cursorIndexOfType);
        _item = new DailySpendDM(_tmpDate,_tmpAmount,_tmpType,_tmpMonth);
        final long _tmpId;
        _tmpId = _cursor.getLong(_cursorIndexOfId);
        _item.setId(_tmpId);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<DailySpendDM> getAllDailyDataByDate(long start, long end) {
    final String _sql = "select * from dailyspenddm where date>=? and date< ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, start);
    _argIndex = 2;
    _statement.bindLong(_argIndex, end);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
      final int _cursorIndexOfDate = _cursor.getColumnIndexOrThrow("date");
      final int _cursorIndexOfMonth = _cursor.getColumnIndexOrThrow("month");
      final int _cursorIndexOfAmount = _cursor.getColumnIndexOrThrow("amount");
      final int _cursorIndexOfType = _cursor.getColumnIndexOrThrow("type");
      final List<DailySpendDM> _result = new ArrayList<DailySpendDM>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final DailySpendDM _item;
        final long _tmpDate;
        _tmpDate = _cursor.getLong(_cursorIndexOfDate);
        final int _tmpMonth;
        _tmpMonth = _cursor.getInt(_cursorIndexOfMonth);
        final int _tmpAmount;
        _tmpAmount = _cursor.getInt(_cursorIndexOfAmount);
        final int _tmpType;
        _tmpType = _cursor.getInt(_cursorIndexOfType);
        _item = new DailySpendDM(_tmpDate,_tmpAmount,_tmpType,_tmpMonth);
        final long _tmpId;
        _tmpId = _cursor.getLong(_cursorIndexOfId);
        _item.setId(_tmpId);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}

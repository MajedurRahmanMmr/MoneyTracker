package com.example.moneytracker.Database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.db.SupportSQLiteOpenHelper.Callback;
import android.arch.persistence.db.SupportSQLiteOpenHelper.Configuration;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.RoomOpenHelper;
import android.arch.persistence.room.RoomOpenHelper.Delegate;
import android.arch.persistence.room.util.TableInfo;
import android.arch.persistence.room.util.TableInfo.Column;
import android.arch.persistence.room.util.TableInfo.ForeignKey;
import android.arch.persistence.room.util.TableInfo.Index;
import java.lang.IllegalStateException;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;
import java.util.HashSet;

@SuppressWarnings("unchecked")
public class RoomDataBase_Impl extends RoomDataBase {
  private volatile MonthlyDataDAO _monthlyDataDAO;

  private volatile SalaryDAO _salaryDAO;

  private volatile SpendDAO _spendDAO;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(5) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `MonthlyData` (`monthId` INTEGER NOT NULL, `monthName` TEXT, `monthlyIncome` INTEGER NOT NULL, `monthlySpend` INTEGER NOT NULL, PRIMARY KEY(`monthId`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `Salary` (`id` INTEGER NOT NULL, `salaryAmount` INTEGER NOT NULL, `updatedTime` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `Spend` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `monthId` INTEGER NOT NULL, `spendAmount` INTEGER NOT NULL, `time` INTEGER NOT NULL)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `DailySpendDM` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `date` INTEGER NOT NULL, `month` INTEGER NOT NULL, `amount` INTEGER NOT NULL, `type` INTEGER NOT NULL)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, \"99cf83045f2a07bc6d7734e45c1c2784\")");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `MonthlyData`");
        _db.execSQL("DROP TABLE IF EXISTS `Salary`");
        _db.execSQL("DROP TABLE IF EXISTS `Spend`");
        _db.execSQL("DROP TABLE IF EXISTS `DailySpendDM`");
      }

      @Override
      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      protected void validateMigration(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsMonthlyData = new HashMap<String, TableInfo.Column>(4);
        _columnsMonthlyData.put("monthId", new TableInfo.Column("monthId", "INTEGER", true, 1));
        _columnsMonthlyData.put("monthName", new TableInfo.Column("monthName", "TEXT", false, 0));
        _columnsMonthlyData.put("monthlyIncome", new TableInfo.Column("monthlyIncome", "INTEGER", true, 0));
        _columnsMonthlyData.put("monthlySpend", new TableInfo.Column("monthlySpend", "INTEGER", true, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysMonthlyData = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesMonthlyData = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoMonthlyData = new TableInfo("MonthlyData", _columnsMonthlyData, _foreignKeysMonthlyData, _indicesMonthlyData);
        final TableInfo _existingMonthlyData = TableInfo.read(_db, "MonthlyData");
        if (! _infoMonthlyData.equals(_existingMonthlyData)) {
          throw new IllegalStateException("Migration didn't properly handle MonthlyData(com.example.moneytracker.Database.MonthlyData).\n"
                  + " Expected:\n" + _infoMonthlyData + "\n"
                  + " Found:\n" + _existingMonthlyData);
        }
        final HashMap<String, TableInfo.Column> _columnsSalary = new HashMap<String, TableInfo.Column>(3);
        _columnsSalary.put("id", new TableInfo.Column("id", "INTEGER", true, 1));
        _columnsSalary.put("salaryAmount", new TableInfo.Column("salaryAmount", "INTEGER", true, 0));
        _columnsSalary.put("updatedTime", new TableInfo.Column("updatedTime", "INTEGER", true, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysSalary = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesSalary = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoSalary = new TableInfo("Salary", _columnsSalary, _foreignKeysSalary, _indicesSalary);
        final TableInfo _existingSalary = TableInfo.read(_db, "Salary");
        if (! _infoSalary.equals(_existingSalary)) {
          throw new IllegalStateException("Migration didn't properly handle Salary(com.example.moneytracker.Database.Salary).\n"
                  + " Expected:\n" + _infoSalary + "\n"
                  + " Found:\n" + _existingSalary);
        }
        final HashMap<String, TableInfo.Column> _columnsSpend = new HashMap<String, TableInfo.Column>(4);
        _columnsSpend.put("id", new TableInfo.Column("id", "INTEGER", true, 1));
        _columnsSpend.put("monthId", new TableInfo.Column("monthId", "INTEGER", true, 0));
        _columnsSpend.put("spendAmount", new TableInfo.Column("spendAmount", "INTEGER", true, 0));
        _columnsSpend.put("time", new TableInfo.Column("time", "INTEGER", true, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysSpend = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesSpend = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoSpend = new TableInfo("Spend", _columnsSpend, _foreignKeysSpend, _indicesSpend);
        final TableInfo _existingSpend = TableInfo.read(_db, "Spend");
        if (! _infoSpend.equals(_existingSpend)) {
          throw new IllegalStateException("Migration didn't properly handle Spend(com.example.moneytracker.Database.Spend).\n"
                  + " Expected:\n" + _infoSpend + "\n"
                  + " Found:\n" + _existingSpend);
        }
        final HashMap<String, TableInfo.Column> _columnsDailySpendDM = new HashMap<String, TableInfo.Column>(5);
        _columnsDailySpendDM.put("id", new TableInfo.Column("id", "INTEGER", true, 1));
        _columnsDailySpendDM.put("date", new TableInfo.Column("date", "INTEGER", true, 0));
        _columnsDailySpendDM.put("month", new TableInfo.Column("month", "INTEGER", true, 0));
        _columnsDailySpendDM.put("amount", new TableInfo.Column("amount", "INTEGER", true, 0));
        _columnsDailySpendDM.put("type", new TableInfo.Column("type", "INTEGER", true, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysDailySpendDM = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesDailySpendDM = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoDailySpendDM = new TableInfo("DailySpendDM", _columnsDailySpendDM, _foreignKeysDailySpendDM, _indicesDailySpendDM);
        final TableInfo _existingDailySpendDM = TableInfo.read(_db, "DailySpendDM");
        if (! _infoDailySpendDM.equals(_existingDailySpendDM)) {
          throw new IllegalStateException("Migration didn't properly handle DailySpendDM(com.example.moneytracker.Database.DailySpendDM).\n"
                  + " Expected:\n" + _infoDailySpendDM + "\n"
                  + " Found:\n" + _existingDailySpendDM);
        }
      }
    }, "99cf83045f2a07bc6d7734e45c1c2784", "20944dd647dcce6c05df7ca6d79e728b");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    return new InvalidationTracker(this, "MonthlyData","Salary","Spend","DailySpendDM");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `MonthlyData`");
      _db.execSQL("DELETE FROM `Salary`");
      _db.execSQL("DELETE FROM `Spend`");
      _db.execSQL("DELETE FROM `DailySpendDM`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  public MonthlyDataDAO monthlyDataDAO() {
    if (_monthlyDataDAO != null) {
      return _monthlyDataDAO;
    } else {
      synchronized(this) {
        if(_monthlyDataDAO == null) {
          _monthlyDataDAO = new MonthlyDataDAO_Impl(this);
        }
        return _monthlyDataDAO;
      }
    }
  }

  @Override
  public SalaryDAO salaryDAO() {
    if (_salaryDAO != null) {
      return _salaryDAO;
    } else {
      synchronized(this) {
        if(_salaryDAO == null) {
          _salaryDAO = new SalaryDAO_Impl(this);
        }
        return _salaryDAO;
      }
    }
  }

  @Override
  public SpendDAO spendDAO() {
    if (_spendDAO != null) {
      return _spendDAO;
    } else {
      synchronized(this) {
        if(_spendDAO == null) {
          _spendDAO = new SpendDAO_Impl(this);
        }
        return _spendDAO;
      }
    }
  }
}

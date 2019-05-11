package com.example.moneytracker.Database;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class DailySpendDM {

    @PrimaryKey(autoGenerate = true)
    long id;
    private long date;

    private int month;
    private int amount;
    private int type;

    public DailySpendDM(long date, int amount, int type, int month) {
        this.date = date;
        this.amount = amount;
        this.month = month;
        this.type = type;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getType() {
        return type;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setType(int type) {
        this.type = type;
    }
}

package com.example.moneytracker.Database;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class MonthlyData {
    @PrimaryKey
    private int monthId;
    private String monthName="";
    private int monthlyIncome;
    private int monthlySpend;

    public MonthlyData(int monthId) {
        this.monthId = monthId;
    }

    public String getMonthName() {
        return monthName;
    }

    public void setMonthName(String monthName) {
        this.monthName = monthName;
    }

    public int getMonthId() {
        return monthId;
    }

    public void setMonthId(int monthId) {
        this.monthId = monthId;
    }

    public int getMonthlyIncome() {
        return monthlyIncome;
    }

    public void setMonthlyIncome(int monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
    }

    public int getMonthlySpend() {
        return monthlySpend;
    }

    public void setMonthlySpend(int monthlySpend) {
        this.monthlySpend = monthlySpend;
    }
}

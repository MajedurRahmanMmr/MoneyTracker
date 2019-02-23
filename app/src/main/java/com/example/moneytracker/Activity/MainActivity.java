package com.example.moneytracker.Activity;

import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.moneytracker.Database.MonthlyData;
import com.example.moneytracker.Database.MonthlyDataViewModel;
import com.example.moneytracker.Database.RoomDataBase;
import com.example.moneytracker.Database.Salary;
import com.example.moneytracker.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    RoomDataBase db;
    private MonthlyDataViewModel monthlyDataViewModel;
    private RecyclerView recyclerView;
    private MonthlyDataAdapter adapter;
    private BottomSheetBehavior mBottomSheetBehavior;
    private TextView mTextViewState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = RoomDataBase.getDatabase(this);
        monthlyDataViewModel = ViewModelProviders.of(this).get(MonthlyDataViewModel.class);

        List<MonthlyData> allMonthlyData = db.wordDao().getAllMonthlyData();

        if (allMonthlyData.size() == 0) {
            insertInitialData();
        }

        recyclerView = findViewById(R.id.recycler_monthly_income);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new MonthlyDataAdapter(this);
        recyclerView.setAdapter(adapter);


        monthlyDataViewModel.getAllMonthlyIncomeObserver().observe(this, new Observer<List<MonthlyData>>() {
            @Override
            public void onChanged(@Nullable List<MonthlyData> monthlyData) {
                adapter.setData(monthlyData);
            }
        });


        findViewById(R.id.top_bar_money_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSalaryDialog();
            }
        });


        List<Salary> salaries = db.salaryDAO().getAllMonthlyData();

        if (salaries.size() == 0) {
            showSalaryDialog();
        }


    }

    private void showSalaryDialog() {
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.dialog_layout_add_salary);
        dialog.setCancelable(false);
        dialog.show();
        final EditText amount_salary = dialog.findViewById(R.id.salary_amount);

        dialog.findViewById(R.id.save_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String amount = amount_salary.getText().toString();

                if (!amount.isEmpty()) {

                    int actual_amount = Integer.parseInt(amount);

                    Salary salary = new Salary();
                    salary.setSalaryAmount(actual_amount);
                    db.salaryDAO().insertMonthlyData(salary);
                    dialog.dismiss();
                    adapter.notifyData();

                }


            }
        });


        dialog.findViewById(R.id.cancel_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    private void insertInitialData() {

        final ArrayList<MonthlyData> data = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            MonthlyData monthlyData = new MonthlyData(i);
            monthlyData.setMonthlyIncome(0);
            monthlyData.setMonthlySpend(0);
            monthlyData.setMonthName(formatMonth(i, Locale.US));
            data.add(monthlyData);

        }

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {

                if (db != null) {
                    db.wordDao().insertAllMonthlyData(data);
                }
            }
        });
    }

    public String formatMonth(int month, Locale locale) {
        DateFormat formatter = new SimpleDateFormat("MMMM", locale);
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.MONTH, month - 1);
        return formatter.format(calendar.getTime());
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (adapter != null) {

            adapter.notifyDataSetChanged();
        }
    }
}

package com.example.moneytracker.Activity;

import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moneytracker.Adapter.MonthlyDataAdapter;
import com.example.moneytracker.Database.DailySpendDM;
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
    private int ammountType;
    private List<MonthlyData> monthlyDataLocal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = RoomDataBase.getDatabase(this);
        monthlyDataViewModel = ViewModelProviders.of(this).get(MonthlyDataViewModel.class);

        List<MonthlyData> allMonthlyData = db.monthlyDataDAO().getAllMonthlyData();

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
                monthlyDataLocal = monthlyData;
                setHomeAdapterData(monthlyData);
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


        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });


        findViewById(R.id.all_history).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, HistoryActivity.class));
            }
        });
        db.monthlyDataDAO().getAllDailyDataLive().observe(this, new Observer<List<DailySpendDM>>() {
            @Override
            public void onChanged(@Nullable List<DailySpendDM> dailySpendDMS) {
                setHomeAdapterData(monthlyDataLocal);

            }
        });
    }

    private void setHomeAdapterData(List<MonthlyData> monthlyData) {
        if (monthlyDataLocal != null)
            for (MonthlyData item : monthlyDataLocal) {

                int totalSpendMonth = 0;
                List<DailySpendDM> allDailyDataByMonth = db.monthlyDataDAO().getAllDailyDataByMonth(item.getMonthId());

                for (DailySpendDM dailyItem : allDailyDataByMonth) {
                    totalSpendMonth = dailyItem.getAmount() + totalSpendMonth;

                }
                item.setMonthlySpend(totalSpendMonth);
            }


        adapter.setData(monthlyData);
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

                    try {
                        int actual_amount = Integer.parseInt(amount);

                        Salary salary = new Salary();
                        salary.setSalaryAmount(actual_amount);
                        db.salaryDAO().insertMonthlyData(salary);
                        dialog.dismiss();
                        adapter.notifyData();
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this, "Please enter a valid amount", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(MainActivity.this, "Please enter a valid amount", Toast.LENGTH_SHORT).show();

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
                    db.monthlyDataDAO().insertAllMonthlyData(data);
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


    private void saveData() {
        // Toast.makeText(context, "Running", Toast.LENGTH_SHORT).show();

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_layout_add_spendmoney);
        dialog.setCancelable(false);
        dialog.show();
        final int month = Calendar.getInstance().get(Calendar.MONTH);


        final EditText ammount = dialog.findViewById(R.id.shopping_amount);


        RadioGroup buttonGroup = dialog.findViewById(R.id.shoppingTypeGroup);

        ammountType = -1;

        buttonGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {


                if (checkedId == R.id.shoppingType) {
                    ammountType = 1;
                } else if (checkedId == R.id.foodType) {
                    ammountType = 2;
                } else {
                    ammountType = 3;
                }
            }
        });

        dialog.findViewById(R.id.save_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String amount = ammount.getText().toString();

                if (amount.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Amount is empty", Toast.LENGTH_SHORT).show();

                    return;
                }

                if (ammountType == -1) {
                    Toast.makeText(MainActivity.this, "Please choose amount type", Toast.LENGTH_SHORT).show();
                    return;
                }


                DailySpendDM dailySpendDM = new DailySpendDM(System.currentTimeMillis(), Integer.parseInt(amount), ammountType, (month + 1));
                db.monthlyDataDAO().insertDaylySpend(dailySpendDM);
                dialog.dismiss();

            }
        });


        dialog.findViewById(R.id.cancel_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();

        if (adapter != null) {

            adapter.notifyDataSetChanged();
        }
    }
}

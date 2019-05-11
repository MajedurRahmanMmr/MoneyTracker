package com.example.moneytracker.Activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moneytracker.Adapter.HistoryAdapter;
import com.example.moneytracker.Database.DailySpendDM;
import com.example.moneytracker.Database.MonthlyDataDAO;
import com.example.moneytracker.Database.RoomDataBase;
import com.example.moneytracker.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    private MonthlyDataDAO monthlyDataDAO;
    private int ammountType;
    private HistoryAdapter adapter;
    private int month;

    private long starDate = 0L;
    private long endDate = System.currentTimeMillis();


    private List<DailySpendDM> listDailySpend;
    TextView start_text, end_text;

    public enum DataType {
        Start,
        End
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("History Data");


        month = getIntent().getIntExtra("month", 0);

        monthlyDataDAO = RoomDataBase.getDatabase(this).monthlyDataDAO();

        RecyclerView historicalView = findViewById(R.id.historyRecyclerView);
        historicalView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new HistoryAdapter(this);
        historicalView.setAdapter(adapter);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
            }
        });


        if (month > 0) {

            starDate = 0L;
            endDate = System.currentTimeMillis();

            monthlyDataDAO.getAllDailyDataByMonthLive(month).observe(this, new Observer<List<DailySpendDM>>() {
                @Override
                public void onChanged(@Nullable List<DailySpendDM> dailySpendDMS) {
                    adapter.setData(dailySpendDMS);

                }
            });

            findViewById(R.id.top_date_picker_wrapper).setVisibility(View.GONE);

        } else {
            starDate = 0L;
            endDate = System.currentTimeMillis();

            findViewById(R.id.fab).setVisibility(View.GONE);
            monthlyDataDAO.getAllDailyDataLive().observe(this, new Observer<List<DailySpendDM>>() {
                @Override
                public void onChanged(@Nullable List<DailySpendDM> dailySpendDMS) {
                    listDailySpend = dailySpendDMS;
                    refreshData(dailySpendDMS);
                }
            });
            findViewById(R.id.top_date_picker_wrapper).setVisibility(View.VISIBLE);


            start_text = findViewById(R.id.start_text);
            end_text = findViewById(R.id.end_text);
        }
    }

    void refreshData(List<DailySpendDM> dailySpendDMS) {

        if (listDailySpend != null) {
            List<DailySpendDM> listItem = new ArrayList<>();
            for (DailySpendDM item : dailySpendDMS) {
                if (item.getDate() >= starDate && endDate >= item.getDate()) {
                    listItem.add(item);
                }
            }
            adapter.setData(listItem);
        }
    }


    private void saveData() {
        // Toast.makeText(context, "Running", Toast.LENGTH_SHORT).show();

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_layout_add_spendmoney);
        dialog.setCancelable(false);
        dialog.show();


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
                    Toast.makeText(HistoryActivity.this, "Amount is empty", Toast.LENGTH_SHORT).show();

                    return;
                }

                if (ammountType == -1) {
                    Toast.makeText(HistoryActivity.this, "Please choose amount type", Toast.LENGTH_SHORT).show();
                    return;
                }


                DailySpendDM dailySpendDM = new DailySpendDM(System.currentTimeMillis(), Integer.parseInt(amount), ammountType, month);
                monthlyDataDAO.insertDaylySpend(dailySpendDM);
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


    public void onClickCalender(View view) {

        int id = view.getId();

        switch (id) {
            case R.id.start_calender_icon:
                openCalender(DataType.Start);
                break;
            case R.id.start_date_text_wrapper:
                openCalender(DataType.Start);
                break;

            case R.id.end_calender_icon:
                openCalender(DataType.End);
                break;
            case R.id.end_date_text_wrapper:
                openCalender(DataType.End);
                break;

        }
    }


    public void openCalender(final DataType type) {
        // Process to get Current Date
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        final int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        // Launch Date Picker Dialog
        DatePickerDialog dpd = new DatePickerDialog(HistoryActivity.this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        if (type == DataType.Start) {

                            Calendar c = Calendar.getInstance();
                            c.set(year, monthOfYear, dayOfMonth);
                            starDate = c.getTimeInMillis();
                            start_text.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            //refreshData(listDailySpend);

                        } else if (type == DataType.End) {
                            end_text.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            Calendar c = Calendar.getInstance();
                            c.set(year, monthOfYear, dayOfMonth);
                            endDate = c.getTimeInMillis();
                            //refreshData(listDailySpend);
                        }


                        if (starDate > endDate && endDate != 0L) {
                            Toast.makeText(HistoryActivity.this, "Start date should be less then end date", Toast.LENGTH_SHORT).show();
                            return;
                        }


                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY hh:mm a");

                        Date date = new Date(endDate);
                        String dateData = dateFormat.format(date);

                        Toast.makeText(HistoryActivity.this, dateData, Toast.LENGTH_SHORT).show();
                        List<DailySpendDM> allDailyDataByDate = monthlyDataDAO.getAllDailyDataByDate(starDate, endDate);
                        adapter.setData(allDailyDataByDate);
                    }
                }, mYear, mMonth, mDay);

        dpd.show();
    }
}

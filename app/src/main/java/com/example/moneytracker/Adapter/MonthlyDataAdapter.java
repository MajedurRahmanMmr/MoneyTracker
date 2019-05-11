package com.example.moneytracker.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moneytracker.Activity.HistoryActivity;
import com.example.moneytracker.Database.MonthlyData;
import com.example.moneytracker.Database.RoomDataBase;
import com.example.moneytracker.Database.Salary;
import com.example.moneytracker.R;

import java.util.Calendar;
import java.util.List;

public class MonthlyDataAdapter extends RecyclerView.Adapter<MonthlyDataAdapter.ViewHolder> {


    List<MonthlyData> data;

    LayoutInflater layoutInflater;
    Context context;
    private RoomDataBase database;
    private List<Salary> salaries;

    public MonthlyDataAdapter(Context context) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        database = RoomDataBase.getDatabase(context);
        salaries = database.salaryDAO().getAllMonthlyData();

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(layoutInflater.inflate(R.layout.month_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {


        final MonthlyData currentMonthData = data.get(i);
        viewHolder.nameMonth.setText(currentMonthData.getMonthName());

        final int month = Calendar.getInstance().get(Calendar.MONTH);

        if (month == currentMonthData.getMonthId() - 1) {
            viewHolder.statusMonth.setText("Running");
            viewHolder.statusMonth.setTextColor(Color.parseColor("#FFE53935"));
            viewHolder.cardViewMonth.setCardBackgroundColor(Color.parseColor("#ffeb3b"));

        } else if (month > currentMonthData.getMonthId() - 1) {
            viewHolder.statusMonth.setText("Already Past");
            viewHolder.statusMonth.setTextColor(Color.parseColor("#bababa"));
            viewHolder.cardViewMonth.setCardBackgroundColor(Color.parseColor("#FFFFFFFF"));

        } else {
            viewHolder.statusMonth.setText("Upcoming");
            viewHolder.statusMonth.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            viewHolder.cardViewMonth.setCardBackgroundColor(Color.parseColor("#FFFFFFFF"));

        }

        if (month >= currentMonthData.getMonthId() - 1) {
            if (salaries.size() != 0) {
                viewHolder.linearLayout.setVisibility(View.VISIBLE);
                Salary salary = salaries.get(0);

                if (salary != null && salary.getSalaryAmount() > 0) {
                    try {
                        int availableAmount = salary.getSalaryAmount() - currentMonthData.getMonthlySpend();
                        int alreadySpend = currentMonthData.getMonthlySpend();
                        int remaining = salary.getSalaryAmount() - alreadySpend;

                        viewHolder.available.setText(String.format("%d", salary.getSalaryAmount()));
                        viewHolder.remaining.setText(String.format("%d", remaining));
                        viewHolder.alreadySpend.setText(String.format("%d", alreadySpend));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            } else {
                viewHolder.linearLayout.setVisibility(View.GONE);

            }
        } else {
            viewHolder.linearLayout.setVisibility(View.GONE);
        }


        viewHolder.cardViewMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (month >= currentMonthData.getMonthId() - 1) {
                    Intent intent = new Intent(context, HistoryActivity.class);

                    intent.putExtra("month", currentMonthData.getMonthId());
                    context.startActivity(intent);
                    // saveData(currentMonthData);
                } else {
                    Toast.makeText(context, "You can change only running and already past month data!!", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }


    public void setData(List<MonthlyData> data) {
        this.data = data;
        notifyData();
    }

    public void notifyData() {
        salaries = database.salaryDAO().getAllMonthlyData();

        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView nameMonth, statusMonth, available, alreadySpend, remaining;
        LinearLayout linearLayout;
        CardView cardViewMonth;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameMonth = itemView.findViewById(R.id.month_name);
            available = itemView.findViewById(R.id.available_money);
            alreadySpend = itemView.findViewById(R.id.already_spend);
            remaining = itemView.findViewById(R.id.remaining_money);
            statusMonth = itemView.findViewById(R.id.month_status);
            cardViewMonth = itemView.findViewById(R.id.month_card);

            linearLayout = itemView.findViewById(R.id.display_info);

        }


    }


}

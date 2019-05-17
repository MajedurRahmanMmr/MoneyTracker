package com.example.moneytracker.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moneytracker.Activity.HistoryActivity;
import com.example.moneytracker.Database.MonthlyData;
import com.example.moneytracker.Database.RoomDataBase;
import com.example.moneytracker.Database.Salary;
import com.example.moneytracker.Database.SalaryMonth;
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

                SalaryMonth monthlyDataByMonthId = database.salaryDAO().getAllMonthlyDataByMonthId(currentMonthData.getMonthId());


                if (monthlyDataByMonthId != null && monthlyDataByMonthId.getSalaryAmount() > 0) {
                    try {
                        int availableAmount = monthlyDataByMonthId.getSalaryAmount() - currentMonthData.getMonthlySpend();
                        int alreadySpend = currentMonthData.getMonthlySpend();
                        int remaining = monthlyDataByMonthId.getSalaryAmount() - alreadySpend;

                        viewHolder.available.setText(String.format("%d", monthlyDataByMonthId.getSalaryAmount()));
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


        viewHolder.cardViewMonth.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (month >= currentMonthData.getMonthId() - 1) {
                    showSalaryDialog(currentMonthData);
                } else {
                    Toast.makeText(context, "You can change only running and already past month data!!", Toast.LENGTH_SHORT).show();
                }


                return false;
            }
        });


    }


    private void showSalaryDialog(final MonthlyData currentMonthData) {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_layout_add_salary);
        dialog.setCancelable(false);

        TextView title = dialog.findViewById(R.id.text_title);

        title.setText("Update Salary Amount");
        dialog.show();
        final EditText amount_salary = dialog.findViewById(R.id.salary_amount);

        dialog.findViewById(R.id.save_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String amount = amount_salary.getText().toString();

                if (!amount.isEmpty()) {

                    try {
                        int actual_amount = Integer.parseInt(amount);
                        final int month = currentMonthData.getMonthId();

                        List<SalaryMonth> allMonthlySalaryData = database.salaryDAO().getAllMonthlySalaryData();

                        int salaryValue = 0;
                        for (SalaryMonth item : allMonthlySalaryData) {
                            salaryValue = item.getSalaryAmount() + salaryValue;
                        }

                        if (salaryValue > 0) {
                            SalaryMonth monthData = database.salaryDAO().getAllMonthlyDataByMonthId(month);
                            if (monthData == null) {
                                SalaryMonth salaryMonth = new SalaryMonth();
                                salaryMonth.setMonthId(month);
                                salaryMonth.setSalaryAmount(actual_amount);
                                salaryMonth.setUpdatedTime(System.currentTimeMillis());
                                database.salaryDAO().insertMonthlyData(salaryMonth);
                            } else {
                                monthData.setSalaryAmount(actual_amount);
                                database.salaryDAO().insertMonthlyData(monthData);
                            }
                        } else {
                            for (int i = 1; i <= 12; i++) {
                                SalaryMonth salaryMonth = new SalaryMonth();
                                salaryMonth.setMonthId(i);
                                salaryMonth.setSalaryAmount(actual_amount);
                                salaryMonth.setUpdatedTime(System.currentTimeMillis());
                                database.salaryDAO().insertMonthlyData(salaryMonth);
                            }
                        }
                        Salary salary = new Salary();
                        salary.setSalaryAmount(actual_amount);
                        database.salaryDAO().insertMonthlyData(salary);
                        dialog.dismiss();
                        notifyData();
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                        Toast.makeText(context, "Please enter a valid amount", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(context, "Please enter a valid amount", Toast.LENGTH_SHORT).show();

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

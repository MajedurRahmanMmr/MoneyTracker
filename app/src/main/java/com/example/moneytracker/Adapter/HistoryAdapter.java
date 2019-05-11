package com.example.moneytracker.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moneytracker.Database.DailySpendDM;
import com.example.moneytracker.Database.MonthlyDataDAO;
import com.example.moneytracker.Database.RoomDataBase;
import com.example.moneytracker.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {


    private Context context;

    private LayoutInflater layoutInflater;

    List<DailySpendDM> dailySpendDMList;
    private int ammountType;
    private final MonthlyDataDAO monthlyDataDAO;

    public HistoryAdapter(Context context) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        monthlyDataDAO = RoomDataBase.getDatabase(context).monthlyDataDAO();

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(layoutInflater.inflate(R.layout.daily_spen_ammount_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {


        final DailySpendDM dailySpendDM = dailySpendDMList.get(i);
        viewHolder.amount.setText(dailySpendDM.getAmount() + "");


        String type = getType(dailySpendDM);
        viewHolder.type.setText(type);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY hh:mm a");

        Date date = new Date(dailySpendDM.getDate());
        String dateData = dateFormat.format(date);
        viewHolder.date.setText(dateData + "");


        viewHolder.itemCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData(dailySpendDM);
            }
        });
    }

    private void saveData(DailySpendDM dailySpendDM) {
        // Toast.makeText(context, "Running", Toast.LENGTH_SHORT).show();

        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_layout_add_spendmoney);
        dialog.setCancelable(false);
        ammountType = -1;

        final EditText ammount = dialog.findViewById(R.id.shopping_amount);


        ammount.setText(dailySpendDM.getAmount() + "");


        RadioGroup buttonGroup = dialog.findViewById(R.id.shoppingTypeGroup);

        final int month = dailySpendDM.getMonth();
        final long id = dailySpendDM.getId();
        if (dailySpendDM.getType() == 1) {
            buttonGroup.check(R.id.shoppingType);
            ammountType = 1;
        } else if (dailySpendDM.getType() == 2) {

            buttonGroup.check(R.id.foodType);
            ammountType = 2;
        } else {
            buttonGroup.check(R.id.otherType);
            ammountType = 3;
        }
        dialog.show();

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
                    Toast.makeText(context, "Amount is empty", Toast.LENGTH_SHORT).show();

                    return;
                }

                if (ammountType == -1) {
                    Toast.makeText(context, "Please choose amount type", Toast.LENGTH_SHORT).show();
                    return;
                }

                DailySpendDM dailySpendDM = new DailySpendDM(System.currentTimeMillis(), Integer.parseInt(amount), ammountType, month);
                dailySpendDM.setId(id);
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

    private String getType(DailySpendDM dailySpendDM) {
        if (dailySpendDM.getType() == 1) {
            return "Shopping";
        } else if (dailySpendDM.getType() == 2) {
            return "Food";
        } else {
            return "Other";
        }
    }

    public void setData(List<DailySpendDM> dailySpendDMList) {
        this.dailySpendDMList = dailySpendDMList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return dailySpendDMList == null ? 0 : dailySpendDMList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView amount, type, date;
        CardView itemCard;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            amount = itemView.findViewById(R.id.spend_amount_item);
            type = itemView.findViewById(R.id.spend_type_item);
            date = itemView.findViewById(R.id.spend_date_item);
            itemCard = itemView.findViewById(R.id.spend_card_item);
        }
    }
}

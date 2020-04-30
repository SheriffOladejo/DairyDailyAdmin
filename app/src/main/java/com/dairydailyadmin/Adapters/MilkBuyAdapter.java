package com.dairydailyadmin.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dairydailyadmin.Models.MilkBuyModel;
import com.dairydailyadmin.R;

import java.util.ArrayList;

public class MilkBuyAdapter extends RecyclerView.Adapter<MilkBuyAdapter.ViewHolder> {

    Context context;
    ArrayList<MilkBuyModel> list;

    public MilkBuyAdapter(Context context, ArrayList<MilkBuyModel> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MilkBuyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.milk_buy_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MilkBuyAdapter.ViewHolder viewHolder, int i) {
        String name = list.get(i).getName();
        String weight = list.get(i).getWeight();
        String fat = list.get(i).getFat();
        String snf = list.get(i).getSnf();
        String rate = list.get(i).getRate();
        String amount = list.get(i).getAmount();
        String shift  = list.get(i).getShift();
        String sr = String.valueOf(Integer.valueOf(i+1));
        viewHolder.setData(sr, name, weight, fat, snf, rate, amount, shift);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        View view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
        }

        public void setData(String sr, String name, String weight, String fat, String snf, String rate, String amount, String shift){
            TextView srView = view.findViewById(R.id.sr);
            TextView nameView = view.findViewById(R.id.name);
            TextView weightView = view.findViewById(R.id.weight);
            TextView fatView = view.findViewById(R.id.snf);
            TextView rateView = view.findViewById(R.id.rate);
            TextView amountView = view.findViewById(R.id.amount);
            TextView shiftView = view.findViewById(R.id.shift);

            srView.setText(sr);
            shiftView.setText(shift);
            nameView.setText(name);
            weightView.setText(weight);
            fatView.setText(fat+"/"+snf);
            rateView.setText(rate);
            amountView.setText(amount);
        }
    }
}

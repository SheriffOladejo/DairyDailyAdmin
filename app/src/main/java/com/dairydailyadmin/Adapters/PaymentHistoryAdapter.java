package com.dairydailyadmin.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dairydailyadmin.Models.PaymentHistoryModel;
import com.dairydailyadmin.R;

import java.util.ArrayList;

public class PaymentHistoryAdapter extends RecyclerView.Adapter<PaymentHistoryAdapter.ViewHolder> {

    Context context;
    ArrayList<PaymentHistoryModel> list;

    public PaymentHistoryAdapter(Context context, ArrayList<PaymentHistoryModel> list){
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.payment_history_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        String date = list.get(i).getDate();
        String amount = list.get(i).getAmount();
        String expiry_date = list.get(i).getExpiryDate();

        viewHolder.setData(date, amount, expiry_date);
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

        void setData(String date, String amount, String expiry_date){
            TextView dateView = view.findViewById(R.id.date);
            TextView amountView = view.findViewById(R.id.amount);
            TextView expiry_dateView = view.findViewById(R.id.expiry_date);

            dateView.setText("Payment Date: "+date);
            amountView.setText("Amount(Rs): " + amount);
            expiry_dateView.setText("Expiry Date: " + expiry_date);
        }
    }
}

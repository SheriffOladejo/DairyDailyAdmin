package com.dairydailyadmin.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dairydailyadmin.Models.UsersModel;
import com.dairydailyadmin.R;
import com.dairydailyadmin.UI.UserDetailsActivity;

import java.util.ArrayList;

public class CustomersAdapter extends RecyclerView.Adapter<CustomersAdapter.ViewHolder> {

    ArrayList<UsersModel> list;
    Context context;

    public CustomersAdapter(Context context, ArrayList<UsersModel> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.users_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        String name = list.get(i).getName();
        String phone_no = list.get(i).getMobile_no();
        viewHolder.setData(name, phone_no);
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

        void setData(String name, String phone_no){
            TextView nameView = view.findViewById(R.id.name);
            TextView mobile_no = view.findViewById(R.id.mobileno);
            nameView.setText(name);
            mobile_no.setText(phone_no);
        }
    }
}

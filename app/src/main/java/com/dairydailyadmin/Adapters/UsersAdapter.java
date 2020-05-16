package com.dairydailyadmin.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.dairydailyadmin.Models.UsersModel;
import com.dairydailyadmin.R;
import com.dairydailyadmin.UI.UserDetailsActivity;

import java.util.ArrayList;
import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> implements Filterable {

    private static ArrayList<UsersModel> list;
    private static ArrayList<UsersModel> listFull;
    Context context;
    private static int number = 0;

    public UsersAdapter(Context context, ArrayList<UsersModel> list){
        this.context = context;
        this.list = list;
        Log.d("UsersAdapter", "list: " + list.size());
        listFull = new ArrayList<>(list);
        number = list.size()+1;
    }

    public UsersAdapter(){}

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
        String id = String.valueOf(i+1);
        viewHolder.setData(id + ". " + name, phone_no);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    private Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<UsersModel> filteredList = new ArrayList<>();
            Log.d("UsersAdapter", "filteredList size: " + listFull.size());

            if(constraint == null || constraint.length() == 0){
                filteredList.addAll(listFull);
            }
            else{
                String filterPattern = constraint.toString().toLowerCase().trim();
                for(UsersModel item: listFull){
                    if(item.getMobile_no().toLowerCase().contains(filterPattern)){
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            list.clear();
            list.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };

    public class ViewHolder extends RecyclerView.ViewHolder {

        View view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String address = list.get(getAdapterPosition()).getAddress();
                    String name = list.get(getAdapterPosition()).getName();
                    String mobile_no = list.get(getAdapterPosition()).getMobile_no();
                    String all_buyers = list.get(getAdapterPosition()).getAll_buyers();
                    String all_sellers = list.get(getAdapterPosition()).getAll_sellers();
                    String email = list.get(getAdapterPosition()).getEmail();
                    String milk_buy_data = list.get(getAdapterPosition()).getMilk_buy_data();
                    String milk_sale_data = list.get(getAdapterPosition()).getMilk_sale_data();
                    String city = list.get(getAdapterPosition()).getCity();
                    String expiry_date = list.get(getAdapterPosition()).getExpiry_date();
                    String state = list.get(getAdapterPosition()).getState();
                    String password = list.get(getAdapterPosition()).getPassword();
                    ArrayList<String> sub_details = list.get(getAdapterPosition()).getSubscription_details();

                    Intent intent = new Intent(context, UserDetailsActivity.class);

                    intent.putExtra("name", name);
                    intent.putExtra("password", password);
                    intent.putExtra("address", address);
                    intent.putExtra("mobile_no", mobile_no);
                    intent.putExtra("all_buyers", all_buyers);
                    intent.putExtra("all_sellers", all_sellers);
                    intent.putExtra("email", email);
                    intent.putExtra("milk_buy_data", milk_buy_data);
                    intent.putExtra("milk_sale_data", milk_sale_data);
                    intent.putExtra("city", city);
                    intent.putExtra("expiry_date", expiry_date);
                    intent.putExtra("state", state);
                    intent.putStringArrayListExtra("sub_details", sub_details);
                    context.startActivity(intent);
                }
            });
        }

        void setData(String name, String phone_no){
            TextView nameView = view.findViewById(R.id.name);
            TextView mobile_no = view.findViewById(R.id.mobileno);
            nameView.setText(name);
            mobile_no.setText(phone_no);
        }
    }
}

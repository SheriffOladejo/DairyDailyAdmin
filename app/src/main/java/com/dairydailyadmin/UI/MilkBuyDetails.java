package com.dairydailyadmin.UI;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dairydailyadmin.Adapters.MilkBuyAdapter;
import com.dairydailyadmin.Models.MilkBuyModel;
import com.dairydailyadmin.R;

import org.json.JSONObject;

import java.util.ArrayList;

import static com.dairydailyadmin.Others.UtitlityMethods.truncate;

public class MilkBuyDetails extends Fragment {

    String data;
    String type;
    RecyclerView recyclerView;
    ArrayList<MilkBuyModel> list = new ArrayList<>();
    MilkBuyAdapter adapter;
    private OnFragmentInteractionListener mListener;

    public MilkBuyDetails() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public MilkBuyDetails(String data, String type) {
        this.data=data;
        this.type = type;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_milk_buy_details, container, false);
        recyclerView = view.findViewById(R.id.recyclerview);
        if(type.equals("buy")){
            try {
                Log.d("MilkBuyDetails", "retrieveMilkBuyData: " + data);
                JSONObject jsonObject = new JSONObject(data);
                for(int i = 0; i<jsonObject.names().length(); i++){
                    JSONObject obj = jsonObject.getJSONObject(jsonObject.names().getString(i));
                    int id = obj.getInt("ID");
                    String name = obj.getString("Name");
                    String weight = truncate(Double.valueOf(obj.getString("Weight")));
                    String amount = truncate(Double.valueOf(obj.getString("Amount")));
                    String rate = truncate(Double.valueOf(obj.getString("Rate")));
                    String shift = obj.getString("Shift");
                    String date = obj.getString("Date");
                    String type = obj.getString("Type");
                    Log.d("BackupHandler", "retrieveMilkBuyData: " + date);
                    String fat = obj.getString("Fat");
                    String snf = obj.getString("SNF");
                    MilkBuyModel model = new MilkBuyModel(name, weight, fat, snf, rate, amount, shift.substring(0,1));
                    list.add(model);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else{
            try {
                JSONObject jsonObject = new JSONObject(data);
                for(int i = 0; i<jsonObject.names().length(); i++){
                    JSONObject obj = jsonObject.getJSONObject(jsonObject.names().getString(i));
                    int id = obj.getInt("ID");
                    String name = obj.getString("Name");
                    String weight = truncate(Double.valueOf(obj.getString("Weight")));
                    String amount = truncate(Double.valueOf(obj.getString("Amount")));
                    String rate = truncate(Double.valueOf(obj.getString("Rate")));
                    String shift = obj.getString("Shift");
                    String date = obj.getString("Date");
                    Log.d("BackupHandler", "retrieveMilkSaleData: " + date);
                    String credit = truncate(Double.valueOf(obj.getString("Credit")));
                    String debit = truncate(Double.valueOf(obj.getString("Debit")));
                    MilkBuyModel model = new MilkBuyModel(name, weight, rate, "0", rate, amount, shift.substring(0,1));
                    list.add(model);
                }
            } catch (Exception e) {
                e.printStackTrace();
                //toast(context, "Unable to retrieve milk sale data");
            }
        }
        adapter = new MilkBuyAdapter(getContext(), list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

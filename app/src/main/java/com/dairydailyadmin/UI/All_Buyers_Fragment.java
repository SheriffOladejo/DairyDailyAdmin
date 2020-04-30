package com.dairydailyadmin.UI;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dairydailyadmin.Adapters.CustomersAdapter;
import com.dairydailyadmin.Adapters.UsersAdapter;
import com.dairydailyadmin.Models.UsersModel;
import com.dairydailyadmin.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class All_Buyers_Fragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    String customer;
    CustomersAdapter adapter;
    RecyclerView recyclerView;
    ArrayList<UsersModel> list = new ArrayList<>();

    public All_Buyers_Fragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public All_Buyers_Fragment(String customer){
        this.customer = customer;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_buyers_, container, false);
        try {
            JSONObject jsonObject = new JSONObject(customer);
            for(int i = 0; i<jsonObject.names().length(); i++){
                JSONObject obj = jsonObject.getJSONObject(jsonObject.names().getString(i));
                int id = obj.getInt("ID");
                String name = obj.getString("Name");
                String phoneNumber = obj.getString("PhoneNumber");
                String status = obj.getString("Status");
                String address = obj.getString("Address");
                UsersModel model = new UsersModel(name, phoneNumber, "", "", "", "", "", "", "", "","","","",new ArrayList<String>());
                list.add(model);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        adapter = new CustomersAdapter(getContext(), list);
        recyclerView = view.findViewById(R.id.recyclerview);
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

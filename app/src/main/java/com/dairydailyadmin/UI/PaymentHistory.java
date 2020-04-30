package com.dairydailyadmin.UI;

import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.dairydailyadmin.Adapters.PaymentHistoryAdapter;
import com.dairydailyadmin.Models.PaymentHistoryModel;
import com.dairydailyadmin.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

public class PaymentHistory extends AppCompatActivity {

    ArrayList<String> payment_history;
    ArrayList<PaymentHistoryModel> list = new ArrayList<>();
    Button set_expiry_date;
    DatePickerDialog datePickerDialog;
    int day;
    int month;
    int year;
    long expiry_date;
    String passed_expiry_date;

    TextView days_remaining;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_history);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        set_expiry_date = findViewById(R.id.set_expiry_date);
        passed_expiry_date = getIntent().getStringExtra("expiry_date");
        days_remaining = findViewById(R.id.days_remaining);

        getSupportActionBar().setTitle("Payment History");

        payment_history = getIntent().getStringArrayListExtra("payment_history");
        for(String object: payment_history){
            try {
                JSONObject jsonObject = new JSONObject(object);
                String transaction_date = jsonObject.getString("Transaction Date");
                String amount = jsonObject.getString("Transaction Amount");
                String expiry_date = jsonObject.getString("Expiry Date");
                //Toast.makeText(PaymentHistory.this, expiry_date, Toast.LENGTH_LONG).show();
                PaymentHistoryModel model = new PaymentHistoryModel(transaction_date, amount, new SimpleDateFormat("dd/MM/YYYY").format(new Date(Long.valueOf(expiry_date))));
                list.add(model);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Collections.reverse(list);
        PaymentHistoryAdapter adapter = new PaymentHistoryAdapter(PaymentHistory.this, list);
        recyclerView.setAdapter(adapter);

        final String latest_expiry_date = new SimpleDateFormat("dd/MM/YYYY").format(new Date(Long.valueOf(passed_expiry_date)));
        String month_in_string = latest_expiry_date.substring(3,5);
        String year_in_string = latest_expiry_date.substring(6,10);
        String day_in_string = latest_expiry_date.substring(0,2);
        //Toast.makeText(PaymentHistory.this, list.get(0).getExpiryDate(), Toast.LENGTH_LONG).show();

        if(day_in_string.charAt(0) == '0'){
            day = Integer.valueOf(day_in_string.substring(1,2));
        }
        else{
            day = Integer.valueOf(day_in_string);
        }

        if(month_in_string.charAt(0) == '0'){
            month = Integer.valueOf(month_in_string.substring(1,2));
        }
        else{
            month = Integer.valueOf(month_in_string);
        }
        //Toast.makeText(PaymentHistory.this, ""+month, Toast.LENGTH_LONG).show();
        year = Integer.valueOf(year_in_string);
        set_expiry_date.setText("Set Expiry Date\nCurrent: " + latest_expiry_date);

        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                final Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, dayOfMonth);
                expiry_date = calendar.getTime().getTime();
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users").child(getIntent().getStringExtra("phone_number")).child("Expiry Date");
                ref.setValue(""+expiry_date).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(PaymentHistory.this, "Profile Updated", Toast.LENGTH_LONG).show();
                            String new_expiry_date = new SimpleDateFormat("dd/MM/YYYY").format(new Date(Long.valueOf(expiry_date)));
                            String day_in_string = new_expiry_date.substring(0,2);
                            String month_in_string = new_expiry_date.substring(3,5);
                            String year_in_string = new_expiry_date.substring(6,10);
                            int day= 0;
                            int month = 0;
                            int year = 0;
                            if(day_in_string.charAt(0) == '0'){
                                day = Integer.valueOf(day_in_string.substring(1,2));
                            }
                            else{
                                day = Integer.valueOf(day_in_string);
                            }

                            if(month_in_string.charAt(0) == '0'){
                                month = Integer.valueOf(month_in_string.substring(1,2));
                            }
                            else{
                                month = Integer.valueOf(month_in_string);
                            }
                            //Toast.makeText(PaymentHistory.this, ""+month, Toast.LENGTH_LONG).show();
                            year = Integer.valueOf(year_in_string);
                            long d = 0;
                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                                LocalDate ex = LocalDate.of(year, month, day);
                                LocalDate now = LocalDate.now();
                                d = ChronoUnit.DAYS.between(now, ex);
                            }
                            days_remaining.setText("Days Remaining: " + d);
                            set_expiry_date.setText("Set Expiry Date\nCurrent: " + new_expiry_date);
                        }
                    }
                });
                Toast.makeText(PaymentHistory.this, ""+expiry_date, Toast.LENGTH_LONG).show();
            }
        }, year, month-1, day);
        set_expiry_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });

        long d = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalDate ex = LocalDate.of(year, month, day);
            LocalDate now = LocalDate.now();
            d = ChronoUnit.DAYS.between(now, ex);
        }
        Date today = new Date();
        days_remaining.setText("Days Remaining: " + d);
    }

    private int daysBetween(Date d1, Date d2){
        return (int)((d2.getTime()-d1.getTime()) / (1000 * 60 * 60 * 24));
    }

}

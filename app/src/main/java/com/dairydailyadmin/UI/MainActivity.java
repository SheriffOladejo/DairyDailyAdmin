package com.dairydailyadmin.UI;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.CircularProgressDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.dairydailyadmin.Adapters.UsersAdapter;
import com.dairydailyadmin.Models.UsersModel;
import com.dairydailyadmin.Others.Prevalent;
import com.dairydailyadmin.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    ArrayList<UsersModel> list= new ArrayList<>();
    UsersAdapter adapter;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    FloatingActionButton upload_chart;
    Button selectFile, uploadFile;
    TextView selectedFile;
    Uri fileUri, fileUri1, fileUri2, fileUri3, fileUri4;
    Dialog dialog;
    ProgressDialog progressDialog;
    TextView search;
    TextView image1, image2, image3, image4;

    String showAds;

    private static String show = "";
    private static int imageCount = 0;
    private String imageName;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerview);
        progressBar = findViewById(R.id.progress_bar);
        upload_chart = findViewById(R.id.upload_rate_chart);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading Rate File");
        progressDialog.setCancelable(false);

        getSupportActionBar().setTitle("All Users");

        adapter = new UsersAdapter();

        upload_chart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                    showDialog();
                }
                else{
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 9);
                }
            }
        });

        search = findViewById(R.id.search);

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s.toString().toLowerCase());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        checkFilePermissions();
        refresh();
    }

    private void refresh(){
        recyclerView.setVisibility(View.GONE);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    list.clear();
                    for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                        String all_buyers;
                        String all_sellers;
                        String milk_buy_data;
                        String milk_sale_data;
                        String reg_date;
                        ArrayList<String> subscription_details = new ArrayList<>();
                        try {
                            all_buyers = snapshot.child("All Buyers").getValue().toString();
                        }
                        catch (Exception e){
                            all_buyers = "";
                        }
                        try {
                            all_sellers = snapshot.child("All Sellers").getValue().toString();
                        }
                        catch(Exception e){
                            all_sellers = "";
                        }
                        String city = snapshot.child("City").getValue().toString();
                        try {
                            milk_buy_data = snapshot.child("Milk Buy Data").getValue().toString();
                        }
                        catch(Exception e){
                            milk_buy_data = "";
                        }
                        try {
                            milk_sale_data = snapshot.child("Milk Sale Data").getValue().toString();
                        }catch(Exception e){
                            milk_sale_data = "";
                        }
                        try{
                            for(DataSnapshot dataSnapshot1 : snapshot.child("Payment History").getChildren()){
                                subscription_details.add(dataSnapshot1.child("Payment History").getValue().toString());
                            }
                        }
                        catch (Exception e){
                            Toast.makeText(MainActivity.this, "Unable to create subscription details object" +e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                        try{
                            reg_date = snapshot.child("Date Created").getValue().toString();
                        }
                        catch(Exception e){
                            reg_date = "0";
                        }
                        String address = snapshot.child("Address").getValue().toString();
                        String email = snapshot.child("Email").getValue().toString();
                        String expiry_date = snapshot.child("Expiry Date").getValue().toString();
                        String firstname = snapshot.child("Firstname").getValue().toString();
                        String lastname = snapshot.child("Lastname").getValue().toString();
                        String phone_number = snapshot.child("Phone Number").getValue().toString();
                        String state = snapshot.child("State").getValue().toString();
                        String password = snapshot.child("Password").getValue().toString();
                        UsersModel model = new UsersModel(firstname+" " + lastname, phone_number,address,
                                all_buyers, all_sellers, city, email, milk_buy_data, milk_sale_data, expiry_date, state, password, reg_date, subscription_details
                        );
                        list.add(model);
                    }

                    adapter = new UsersAdapter(MainActivity.this, sort(list));
                    recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    recyclerView.setAdapter(adapter);
                }
                recyclerView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference().child("Show Ads");
        reference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    show = dataSnapshot.getValue().toString();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Pricing");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String starter_plan = dataSnapshot.child("Starter Plan").getValue().toString();
                    String spark_plan = dataSnapshot.child("Spark Plan").getValue().toString();
                    String enterprise_plan = dataSnapshot.child("Enterprise Plan").getValue().toString();
                    Prevalent.starter_plan = Double.valueOf(starter_plan);
                    Prevalent.spark_plan = Double.valueOf(spark_plan);
                    Prevalent.enterprise_plan = Double.valueOf(enterprise_plan);
                }
                else{
                    Toast.makeText(MainActivity.this, "Unable to fetch pricing plans", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, "Unable to fetch pricing plans", Toast.LENGTH_LONG).show();
            }
        });
    }

    ArrayList<UsersModel> sort (ArrayList<UsersModel> list){
        for(int j = 0; j <= list.size(); j++){
            for(int i = 0; i<list.size()-1; i++){
                UsersModel temp;
                if(Long.valueOf(list.get(i).getDate_created()) < Long.valueOf(list.get(i+1).getDate_created())){
                    temp = list.get(i);
                    list.set(i, list.get(i+1));
                    list.set(i+1, temp);
                }
            }
        }
        return list;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if(requestCode == 9 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
            showDialog();
        }
        else{
            //Toast.makeText(MainActivity.this, "Please provide permission to read storage.", Toast.LENGTH_LONG).show();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void checkFilePermissions() {
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP){
            int permissionCheck = this.checkSelfPermission("Manifest.Permission.READ_EXTERNAL_STORAGE");
            permissionCheck += this.checkSelfPermission("Manifest.permission.WRITE_EXTERNAL_STORAGE");
            if(permissionCheck != 0){
                this.requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1001);
            }
            else{
                Log.d("MainActivity", "checkPermission: No need to check permission, SDK version  LOLLIPOP");
            }
        }
    }

    private void showDialog() {
        dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_select_file);
        dialog.setCancelable(true);
        selectFile = dialog.findViewById(R.id.select_file);
        uploadFile = dialog.findViewById(R.id.upload_file);
        selectedFile = dialog.findViewById(R.id.selected_file);

        selectFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectFileToUpload();
            }
        });

        uploadFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fileUri != null){
                    if(fileUri.getPath().endsWith(".csv")){
                        progressDialog.show();
                        uploadSelectedFile();
                    }
                    else{
                        Toast.makeText(MainActivity.this, "Please select a CSV file.", Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    Toast.makeText(MainActivity.this, "Please select a file.", Toast.LENGTH_LONG).show();
                }
            }
        });
        dialog.show();
    }

    private void selectFileToUpload() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
        intent.setType("*/*");
        //intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 1 && resultCode == RESULT_OK && data != null){
            fileUri = data.getData();
            selectedFile.setText(fileUri.getPath());
        }
        else if(requestCode == 2 && resultCode == RESULT_OK && data != null){
            if(data.getData().getPath().endsWith(".jpg") || data.getData().getPath().endsWith(".png")){
                imageCount += 1;
                switch (imageCount){
                    case 1:
                        fileUri1 = data.getData();
                        image1.setText(fileUri1.getPath());
                        break;
                    case 2:
                        fileUri2 = data.getData();
                        image2.setText(fileUri2.getPath());
                        break;
                    case 3:
                        fileUri3 = data.getData();
                        image3.setText(fileUri3.getPath());
                        break;
                    case 4:
                        fileUri4 = data.getData();
                        image4.setText(fileUri4.getPath());
                        uploadImages();
                        break;
                    default:
                        break;
                }
            }
            else{
                Toast.makeText(MainActivity.this, "Please select a valid image file.", Toast.LENGTH_LONG).show();
            }
        }
        else{
            Toast.makeText(MainActivity.this, "Please select a CSV file.", Toast.LENGTH_LONG).show();
        }
    }

    private void uploadImages(){
        final ProgressDialog dialog = new ProgressDialog(MainActivity.this);
        dialog.setTitle("Uploading ads");
        dialog.setCancelable(false);
        dialog.show();
        StorageReference ref = FirebaseStorage.getInstance().getReference().child("Ads");
        ref.child("Image 1").putFile(fileUri1);
        ref.child("Image 2").putFile(fileUri2);
        ref.child("Image 3").putFile(fileUri3);
        ref.child("Image 4").putFile(fileUri4).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                dialog.dismiss();
                Toast.makeText(MainActivity.this, "Images uploaded", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void selectAdImages(){
        Dialog dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.select_images_dialog);
        Button select_images = dialog.findViewById(R.id.select_images);
        image1 = dialog.findViewById(R.id.image1);
        image2 = dialog.findViewById(R.id.image2);
        image3 = dialog.findViewById(R.id.image3);
        image4 = dialog.findViewById(R.id.image4);
        final Switch adSwitch = dialog.findViewById(R.id.ads);
        if(show.equals("true")){
            adSwitch.setChecked(true);
        }
        else if(show.equals("false")){
            adSwitch.setChecked(false);
        }
        else{
            adSwitch.setChecked(false);
        }

        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Show Ads");
        adSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(adSwitch.isChecked()){
                    reference.setValue("true");
                }
                else{
                    reference.setValue("false");
                }
            }
        });
        select_images.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imageCount == 4){
                    Toast.makeText(MainActivity.this, "Cannot select more than 4 images", Toast.LENGTH_LONG).show();
                }
                else{
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
                    intent.setType("*/*");
                    startActivityForResult(intent, 2);
                }
            }
        });
        dialog.show();
    }

    private void uploadSelectedFile() {
        String filename = "Rate File";
        StorageReference ref = FirebaseStorage.getInstance().getReference().child("Rate Chart").child(filename);
        ref.putFile(fileUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if(task.isSuccessful()){
                    Toast.makeText(MainActivity.this, "File uploaded.", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    progressDialog.dismiss();
                    notifyUsers();
                }
                else{
                    Toast.makeText(MainActivity.this, "Something went wrong while uploading the file." + task.getException(), Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }
            }
        });
    }

    private void notifyUsers() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users").child(snapshot.child("Phone Number").getValue().toString()).child("General Rate File Status");
                        ref.setValue("new").addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.set_plans, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.pricing){
            showPlanDialog();
            return true;
        }
        else if(item.getItemId() == R.id.upload){
            selectAdImages();
            return true;
        }
        else
            return false;
    }

    private void showPlanDialog(){
        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setTitle("Updating");
        progressDialog.setCancelable(false);
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.plan_dialog);
        final EditText starter_plan, spark_plan, enterprise_plan;
        starter_plan = dialog.findViewById(R.id.starter_plan);
        spark_plan = dialog.findViewById(R.id.spark_plan);
        enterprise_plan = dialog.findViewById(R.id.enterprise_plan);
        starter_plan.setText(""+Prevalent.starter_plan);
        spark_plan.setText(""+Prevalent.spark_plan);
        enterprise_plan.setText(""+Prevalent.enterprise_plan);
        Button update = dialog.findViewById(R.id.update);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    progressDialog.show();
                    double starter = Double.valueOf(starter_plan.getText().toString());
                    double spark = Double.valueOf(spark_plan.getText().toString());
                    double enterprise = Double.valueOf(enterprise_plan.getText().toString());
                    HashMap<String, Object> map= new HashMap<>();
                    map.put("Starter Plan", ""+starter);
                    map.put("Spark Plan", ""+spark);
                    map.put("Enterprise Plan", enterprise);
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Pricing");
                    ref.updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                progressDialog.dismiss();
                                Toast.makeText(MainActivity.this, "Pricing updated.", Toast.LENGTH_LONG).show();
                                dialog.dismiss();
                            }
                            else{
                                progressDialog.dismiss();
                                Toast.makeText(MainActivity.this, "Unable to update. Please try again", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
                catch(Exception e){
                    progressDialog.dismiss();
                    Toast.makeText(MainActivity.this, "Please check values", Toast.LENGTH_LONG).show();
                }
            }
        });
        dialog.show();
    }
}

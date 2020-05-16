package com.dairydailyadmin.UI;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dairydailyadmin.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

public class UserDetailsActivity extends AppCompatActivity {

    CardView profile, send_message, payment_history, all_customers, total_entries, upload_rate_file, delete;
    Button selectFile, uploadFile;
    TextView selectedFile;
    Uri fileUri;
    Dialog dialog;
    ProgressDialog progressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_details_page);

        profile = findViewById(R.id.profile);
        send_message = findViewById(R.id.send_message);
        payment_history = findViewById(R.id.payment_history);
        all_customers = findViewById(R.id.all_customers);
        total_entries = findViewById(R.id.total_entries);
        upload_rate_file = findViewById(R.id.upload_rate_file);
        delete = findViewById(R.id.delete);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkFilePermissions();
        }

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading Rate File");

        upload_rate_file.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                checkFilePermissions();
                showDialog();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(UserDetailsActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.delete_account_dialog);
                Button yes = dialog.findViewById(R.id.yes);
                Button no = dialog.findViewById(R.id.no);
                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        FirebaseAuth.getInstance().signInWithEmailAndPassword(getIntent().getStringExtra("email"), getIntent().getStringExtra("password")).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                            @Override
//                            public void onComplete(@NonNull Task<AuthResult> task) {
//                                if(task.isSuccessful()){
//                                    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//                                    user.unlink(user.getProviderId()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                                        @Override
//                                        public void onComplete(@NonNull Task<AuthResult> task) {
//                                            if(task.isSuccessful()){
//                                                Toast.makeText(UserDetailsActivity.this, "Phone number deleted", Toast.LENGTH_LONG).show();
//                                            }
//                                            else{
//                                                Toast.makeText(UserDetailsActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
//                                            }
//                                        }
//                                    });
//                                    user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
//                                        @Override
//                                        public void onComplete(@NonNull Task<Void> task) {
//                                            if(task.isSuccessful()){
//                                                Toast.makeText(UserDetailsActivity.this, "User deleted successfully", Toast.LENGTH_LONG).show();
//                                            }
//                                        }
//                                    });
//                                }
//                            }
//                        });

                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users").child(getIntent().getStringExtra("mobile_no")).child("Active");
                        ref.setValue("false");
                        Toast.makeText(UserDetailsActivity.this, "User suspended", Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                        startActivity(new Intent(UserDetailsActivity.this, MainActivity.class));
                        finish();
                    }
                });
                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

        payment_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserDetailsActivity.this, PaymentHistory.class);
                intent.putStringArrayListExtra("payment_history", getIntent().getStringArrayListExtra("sub_details"));
                intent.putExtra("phone_number", getIntent().getStringExtra("mobile_no"));
                intent.putExtra("expiry_date", getIntent().getStringExtra("expiry_date"));
                //Toast.makeText(UserDetailsActivity.this, getIntent().getStringExtra("mobile_no"), Toast.LENGTH_LONG).show();
                startActivity(intent);
            }
        });

        getSupportActionBar().setTitle(getIntent().getStringExtra("name"));

        send_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(UserDetailsActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.send_message_dialog);
                TextView message_to = dialog.findViewById(R.id.message_to);
                final EditText message= dialog.findViewById(R.id.message);
                Button send = dialog.findViewById(R.id.send);
                dialog.setCancelable(true);

                message_to.setText("Message To: "+getIntent().getStringExtra("name"));
                send.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String messageToSend = message.getText().toString();
                        String time = ""+System.currentTimeMillis();
                        HashMap<String, Object> map = new HashMap();
                        map.put("message", messageToSend);
                        map.put("time", time);
                        map.put("status", "unread");
                        if(messageToSend.isEmpty()){
                            Toast.makeText(UserDetailsActivity.this, "Message cannot be empty.", Toast.LENGTH_LONG).show();
                        }
                        else{
                            DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users").child(getIntent().getStringExtra("mobile_no")).child("Messages").child(time);
                            ref.updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(UserDetailsActivity.this, "Message sent.", Toast.LENGTH_LONG).show();
                                        dialog.dismiss();
                                    }
                                    else{
                                        Toast.makeText(UserDetailsActivity.this, "Message not sent.", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }
                    }
                });
                dialog.show();
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserDetailsActivity.this, ProfileActivity.class);
                intent.putExtra("address", getIntent().getStringExtra("address"));
                intent.putExtra("name", getIntent().getStringExtra("name"));
                intent.putExtra("city", getIntent().getStringExtra("city"));
                intent.putExtra("state", getIntent().getStringExtra("state"));
                intent.putExtra("email", getIntent().getStringExtra("email"));
                intent.putExtra("phone_number", getIntent().getStringExtra("mobile_no"));
                startActivity(intent);
            }
        });

        all_customers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String all_buyers = getIntent().getStringExtra("all_buyers");
                String all_sellers = getIntent().getStringExtra("all_sellers");
                Intent intent = new Intent(UserDetailsActivity.this, AllCustomersActivity.class);
                intent.putExtra("all_sellers", all_sellers);
                intent.putExtra("all_buyers", all_buyers);
                startActivity(intent);
            }
        });

        total_entries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String milk_buy_data = getIntent().getStringExtra("milk_buy_data");
                String milk_sale_data = getIntent().getStringExtra("milk_sale_data");
                Intent intent = new Intent(UserDetailsActivity.this, EntriesActivity.class);
                intent.putExtra("milk_buy_data", milk_buy_data);
                intent.putExtra("milk_sale_data", milk_sale_data);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(UserDetailsActivity.this, MainActivity.class));
        finish();
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
        dialog = new Dialog(UserDetailsActivity.this);
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
                        Toast.makeText(UserDetailsActivity.this, "Please select a CSV file.", Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    Toast.makeText(UserDetailsActivity.this, "Please select a file.", Toast.LENGTH_LONG).show();
                }
            }
        });
        dialog.show();
    }

    private void selectFileToUpload() {
        Intent intent = new Intent();
        intent.setType("*/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 1 && resultCode == RESULT_OK && data != null){
            fileUri = data.getData();
            selectedFile.setText(fileUri.getPath());
        }
        else{
            Toast.makeText(UserDetailsActivity.this, "Please select a CSV file.", Toast.LENGTH_LONG).show();
        }
    }

    private void uploadSelectedFile() {
        String filename = "Rate File";
        StorageReference ref = FirebaseStorage.getInstance().getReference().child("Users").child(getIntent().getStringExtra("mobile_no")).child(filename);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users").child(getIntent().getStringExtra("mobile_no")).child("Personal Rate File Status");
        reference.setValue("new");
        ref.putFile(fileUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if(task.isSuccessful()){
                    Toast.makeText(UserDetailsActivity.this, "File uploaded.", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    progressDialog.dismiss();
                }
                else{
                    Toast.makeText(UserDetailsActivity.this, "Something went wrong while uploading the file." + task.getException(), Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }
            }
        });
    }
}

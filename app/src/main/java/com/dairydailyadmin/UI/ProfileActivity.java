package com.dairydailyadmin.UI;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.dairydailyadmin.R;

public class ProfileActivity extends AppCompatActivity {

    Button name, addresss, email, phone_number, city, state;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        getSupportActionBar().setTitle("User Profile");

        RelativeLayout r1 = findViewById(R.id.relativeLayout3);
        RelativeLayout r2 = findViewById(R.id.relativeLayout4);
        name = findViewById(R.id.name);
        addresss = findViewById(R.id.address);
        email = findViewById(R.id.email);
        phone_number = findViewById(R.id.phone_number);
        city = findViewById(R.id.city);
        state = findViewById(R.id.state);

//        String nameString = Paper.book().read(Prevalent.name);
//        String addressString = Paper.book().read(Prevalent.address);
//        String emailString= Paper.book().read(Prevalent.email);
//        String phoneString = Paper.book().read(Prevalent.phone_number);
//        String cityString = Paper.book().read(Prevalent.city);
//        String stateString = Paper.book().read(Prevalent.state);
//        String updateString = Paper.book().read(Prevalent.last_update);
//        String printerString = Paper.book().read(Prevalent.selected_device);

        String nameString = getIntent().getStringExtra("name");
        String addressString = getIntent().getStringExtra("address");
        String emailString = getIntent().getStringExtra("email");
        String phoneString = getIntent().getStringExtra("phone_number");
        String cityString = getIntent().getStringExtra("city");
        String stateString = getIntent().getStringExtra("state");

        Animation slide = AnimationUtils.loadAnimation(ProfileActivity.this, R.anim.image_slide_left);
        r1.startAnimation(slide);
        r2.startAnimation(slide);

        if(nameString == null){
            name.setText("");
        }
        else{
            name.setText(nameString);
        }
        if(addressString == null){
            addresss.setText("");
        }
        else{
            addresss.setText(addressString);
        }
        if(emailString == null){
            email.setText("");
        }
        else{
            email.setText(emailString);
        }
        if(phoneString == null){
            phone_number.setText("");
        }
        else{
            phone_number.setText(phoneString);
        }if(cityString == null){
            city.setText("");
        }
        else{
            city.setText(cityString);
        }if(stateString == null){
            state.setText("");
        }
        else{
            state.setText(stateString);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.close, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.close){
            finish();
            return true;
        }
        else{
            return false;
        }
    }
}

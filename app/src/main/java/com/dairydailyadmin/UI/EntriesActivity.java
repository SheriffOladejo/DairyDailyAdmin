package com.dairydailyadmin.UI;

import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.dairydailyadmin.Adapters.EntriesAdapter;
import com.dairydailyadmin.Adapters.PagerAdapter;
import com.dairydailyadmin.R;

public class EntriesActivity extends AppCompatActivity implements MilkBuyDetails.OnFragmentInteractionListener {

    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entries);

        viewPager = findViewById(R.id.viewPager);

        TabLayout tabLayout = findViewById(R.id.tablayout);
        tabLayout.setTabTextColors(getResources().getColor(R.color.white), getResources().getColor(R.color.black));
        tabLayout.addTab(tabLayout.newTab().setText("Milk Buy"));
        tabLayout.addTab(tabLayout.newTab().setText("Milk Sale"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        getSupportActionBar().setTitle("All Entries");

        String milk_buy_data = getIntent().getStringExtra("milk_buy_data");
        String milk_sale_data = getIntent().getStringExtra("milk_sale_data");

        final EntriesAdapter adapter = new EntriesAdapter(getSupportFragmentManager(), tabLayout.getTabCount(), milk_buy_data, milk_sale_data);
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}

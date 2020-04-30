package com.dairydailyadmin.UI;

import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.dairydailyadmin.Adapters.PagerAdapter;
import com.dairydailyadmin.R;

public class AllCustomersActivity extends AppCompatActivity implements
        All_Buyers_Fragment.OnFragmentInteractionListener,
        All_Sellers_Fragment.OnFragmentInteractionListener{

    ViewPager viewPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_cutomers_activity);

        viewPager = findViewById(R.id.viewPager);

        TabLayout tabLayout = findViewById(R.id.tablayout);
        tabLayout.setTabTextColors(getResources().getColor(R.color.white), getResources().getColor(R.color.black));
        tabLayout.addTab(tabLayout.newTab().setText("Buyers"));
        tabLayout.addTab(tabLayout.newTab().setText("Sellers"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        getSupportActionBar().setTitle("All Customers");

        String all_buyers = getIntent().getStringExtra("all_buyers");
        String all_sellers = getIntent().getStringExtra("all_sellers");
        Log.d("AllCustomersActivity", "all_buyers: " + all_buyers + " all_sellers: " + all_sellers);

        final PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount(), all_buyers, all_sellers);
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

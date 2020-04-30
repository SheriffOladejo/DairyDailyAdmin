package com.dairydailyadmin.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.dairydailyadmin.UI.MilkBuyDetails;

public class EntriesAdapter extends FragmentStatePagerAdapter {
    String buy_data,sale_data;
    int numberOfTabs;

    public EntriesAdapter(FragmentManager fm, int numberOfTabs, String buy_data, String sale_data) {
        super(fm);
        this.numberOfTabs = numberOfTabs;
        this.buy_data= buy_data;
        this.sale_data = sale_data;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0:
                return new MilkBuyDetails(buy_data, "buy");
            case 1:
                return new MilkBuyDetails(sale_data, "sale");
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }
}

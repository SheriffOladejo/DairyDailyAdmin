package com.dairydailyadmin.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.dairydailyadmin.UI.All_Buyers_Fragment;
import com.dairydailyadmin.UI.All_Sellers_Fragment;

public class PagerAdapter extends FragmentStatePagerAdapter {

    String buyers,sellers;
    int numberOfTabs;

    public PagerAdapter(FragmentManager fm, int numberOfTabs, String buyer, String seller) {
        super(fm);
        this.numberOfTabs = numberOfTabs;
        this.buyers= buyer;
        this.sellers = seller;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0:
                return new All_Buyers_Fragment(buyers);
            case 1:
                return new All_Sellers_Fragment(sellers);
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }
}

package com.bonnetrouge.quasar.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.bonnetrouge.quasar.Fragments.BorderFragment;
import com.bonnetrouge.quasar.Fragments.FilterFragment;

import java.util.ArrayList;

public class PagerAdapter extends FragmentStatePagerAdapter {

    private float pageWidth = 0.85f;

    private ArrayList<Fragment> fragments = new ArrayList<>();

    public PagerAdapter(FragmentManager fm) {
        super(fm);
        fragments.add(new FilterFragment());
        fragments.add(new BorderFragment());
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public float getPageWidth(int position) {
        return pageWidth;
    }
}

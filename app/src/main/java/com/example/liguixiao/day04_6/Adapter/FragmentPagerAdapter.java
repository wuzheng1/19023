package com.example.liguixiao.day04_6.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.ArrayList;

/**
 * Created by liguixiao on 2019/9/15.
 */

public class FragmentPagerAdapter extends android.support.v4.app.FragmentPagerAdapter{

    private ArrayList<Fragment> fragments;

    public FragmentPagerAdapter(FragmentManager fm,ArrayList<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}

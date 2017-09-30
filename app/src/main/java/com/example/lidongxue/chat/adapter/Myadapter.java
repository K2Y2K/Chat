package com.example.lidongxue.chat.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by lidongxue on 17-9-29.
 */

public class Myadapter extends FragmentPagerAdapter {

    List<Fragment> fragmentList;

    public Myadapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        this.fragmentList = fragmentList;
    }


    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return (fragmentList == null) ? 0 : fragmentList.size();

    }
}

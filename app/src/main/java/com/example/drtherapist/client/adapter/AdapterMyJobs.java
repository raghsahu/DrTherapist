package com.example.drtherapist.client.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.drtherapist.client.fragment.FragmentCloseJobs;
import com.example.drtherapist.client.fragment.FragmentOpenJobs;

public class AdapterMyJobs extends FragmentPagerAdapter {
    private Context context;
    int totalTabs;

    public AdapterMyJobs(FragmentManager fm, Context context, int totalTabs) {
        super(fm);
        this.context = context;
        this.totalTabs = totalTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                FragmentOpenJobs fragmentOpenJobs=new FragmentOpenJobs();
                return fragmentOpenJobs;
            case 1:
                FragmentCloseJobs fragmentCloseJobs=new FragmentCloseJobs();
                return fragmentCloseJobs;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}



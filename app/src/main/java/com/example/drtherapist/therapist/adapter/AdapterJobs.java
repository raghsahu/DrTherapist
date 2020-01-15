package com.example.drtherapist.therapist.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.drtherapist.client.fragment.FragmentCloseJobs;
import com.example.drtherapist.client.fragment.FragmentOpenJobs;
import com.example.drtherapist.therapist.fragment.FragmentBestMatch;
import com.example.drtherapist.therapist.fragment.FragmentNearBy;
import com.example.drtherapist.therapist.fragment.FragmentNewJobs;

public class AdapterJobs extends FragmentPagerAdapter {
    private Context context;
    int totalTabs;

    public AdapterJobs(FragmentManager fm, Context context, int totalTabs) {
        super(fm);
        this.context = context;
        this.totalTabs = totalTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                FragmentBestMatch fragmentBestMatch=new FragmentBestMatch();
                return fragmentBestMatch;
            case 1:
                FragmentNewJobs fragmentNewJobs=new FragmentNewJobs();
                return fragmentNewJobs;
            case 2:
                FragmentNearBy fragmentNearBy=new FragmentNearBy();
                return fragmentNearBy;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}




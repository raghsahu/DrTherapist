package com.example.drtherapist.Adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.drtherapist.Fragments.AlertFragment;
import com.example.drtherapist.Fragments.MessageFragment;

public class MessageAlertAdapter extends FragmentPagerAdapter {
    int totalTabs;
    private Context context;

    public MessageAlertAdapter(FragmentManager fm, Context context, int totalTabs) {
        super(fm);
        this.context = context;
        this.totalTabs = totalTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                MessageFragment messageFragment = new MessageFragment();
                return messageFragment;
            case 1:
                AlertFragment alertFragment = new AlertFragment();
                return alertFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}


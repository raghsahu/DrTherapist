package com.example.drtherapist.Adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.drtherapist.client.fragment.ClientLoginFragment;
import com.example.drtherapist.therapist.fragment.TherapistLoginFragment;

public class LoginAdapter extends FragmentPagerAdapter {
    int totalTabs;
    private Context context;

    public LoginAdapter(FragmentManager fm, Context context, int totalTabs) {
        super(fm);
        this.context = context;
        this.totalTabs = totalTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                ClientLoginFragment clientLoginFragment = new ClientLoginFragment();
                return clientLoginFragment;
            case 1:
                TherapistLoginFragment therapistLoginFraggment = new TherapistLoginFragment();
                return therapistLoginFraggment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}

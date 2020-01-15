package com.example.drtherapist.Adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.drtherapist.client.fragment.ClientSignUpFragment;
import com.example.drtherapist.therapist.fragment.TherapistSignUpFragment;


public class SignUpAdapter extends FragmentPagerAdapter {
    int totalTabs;
    private Context context;

    public SignUpAdapter(FragmentManager fm, Context context, int totalTabs) {
        super(fm);
        this.context = context;
        this.totalTabs = totalTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                ClientSignUpFragment clientSignUpFragment = new ClientSignUpFragment();
                return clientSignUpFragment;
            case 1:
                TherapistSignUpFragment therapistSignUpFragment = new TherapistSignUpFragment();
                return therapistSignUpFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}
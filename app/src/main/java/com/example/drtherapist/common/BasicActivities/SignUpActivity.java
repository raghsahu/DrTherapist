package com.example.drtherapist.common.BasicActivities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.example.drtherapist.Adapters.SignUpAdapter;
import com.example.drtherapist.R;

public class SignUpActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;

    /*String stringLatitude = null,stringLongitude = null;
    private ArrayList permissionsToRequest;
    private ArrayList permissionsRejected = new ArrayList();
    private ArrayList permissions = new ArrayList();
    private final static int ALL_PERMISSIONS_RESULT = 101;

    LocationTrack locationTrack;
    double longitude, latitude;*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        tabLayout.addTab(tabLayout.newTab().setText("Client"));
        tabLayout.addTab(tabLayout.newTab().setText("Therapist"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final SignUpAdapter signUpAdapter = new SignUpAdapter(getSupportFragmentManager(), this, tabLayout.getTabCount());
        viewPager.setAdapter(signUpAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                viewPager.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}

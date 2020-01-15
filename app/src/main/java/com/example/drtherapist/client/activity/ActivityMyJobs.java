package com.example.drtherapist.client.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.drtherapist.R;
import com.example.drtherapist.client.adapter.AdapterMyJobs;

public class ActivityMyJobs extends AppCompatActivity {
    TabLayout view_job_tabLayout;
    ViewPager view_job_viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_job);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_my_job);
        //setSupportActionBar(toolbar);
        toolbar.setTitle("My Jobs");
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_backspace_black_24dp);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        view_job_tabLayout =(TabLayout)findViewById(R.id.view_job_tabLayout);
        view_job_viewPager =(ViewPager)findViewById(R.id.view_job_viewPager);
        view_job_tabLayout.addTab(view_job_tabLayout.newTab().setText("Open Jobs"));
        view_job_tabLayout.addTab(view_job_tabLayout.newTab().setText("Close Jobs"));
        view_job_tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final AdapterMyJobs adapterMyJobs=new AdapterMyJobs(getSupportFragmentManager(),this,view_job_tabLayout.getTabCount());
        view_job_viewPager.setAdapter(adapterMyJobs);
        view_job_viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(view_job_tabLayout));

        view_job_tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                view_job_viewPager.setCurrentItem(tab.getPosition());
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

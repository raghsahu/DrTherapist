package com.example.drtherapist.therapist.drawer;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.drtherapist.R;
import com.example.drtherapist.client.adapter.AdapterMyJobs;
import com.example.drtherapist.common.Utils.NetworkUtil;
import com.example.drtherapist.common.Utils.ToastClass;
import com.example.drtherapist.common.Utils.Utils;
import com.example.drtherapist.common.remote.API;
import com.example.drtherapist.common.remote.Session;
import com.example.drtherapist.therapist.adapter.AdapterJobs;
import com.example.drtherapist.therapist.adapter.ViewAllJobListAdapterDr;
import com.example.drtherapist.therapist.model.JobListDataDr;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ActivityJobListDr extends AppCompatActivity {
    TabLayout view_job_tabLayout;
    ViewPager view_job_viewPager;
    ImageView iv_back;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_list_dr);

        iv_back=findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        view_job_tabLayout = (TabLayout) findViewById(R.id.view_jobs_tabLayout);
        view_job_viewPager = (ViewPager) findViewById(R.id.view_jobs_viewPager);

        view_job_tabLayout.addTab(view_job_tabLayout.newTab().setText("Best Match"));
        view_job_tabLayout.addTab(view_job_tabLayout.newTab().setText("New"));
        view_job_tabLayout.addTab(view_job_tabLayout.newTab().setText("Near By"));
        view_job_tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final AdapterJobs adapterJobs = new AdapterJobs(getSupportFragmentManager(), this, view_job_tabLayout.getTabCount());
        view_job_viewPager.setAdapter(adapterJobs);
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

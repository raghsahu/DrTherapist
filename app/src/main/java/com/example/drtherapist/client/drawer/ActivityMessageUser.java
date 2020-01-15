package com.example.drtherapist.client.drawer;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.example.drtherapist.R;
import com.example.drtherapist.common.Utils.NetworkUtil;
import com.example.drtherapist.common.Utils.ToastClass;
import com.example.drtherapist.common.remote.API;

import java.util.ArrayList;

public class ActivityMessageUser extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayout ll_no_record;
    private RecyclerView recyclerview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_user);
        initView();
        ClickListner();
    }

    private void initView() {
        //drList = new ArrayList<>();
        recyclerview = findViewById(R.id.recycler_view_user);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        ll_no_record = findViewById(R.id.ll_no_record);
        //tv_header = findViewById(R.id.tv_header);
        //tv_header.setText(cateName);

//        if (NetworkUtil.isNetworkConnected(this)) {
//            try {
//                url = API.BASE_URL + "doctorListByCatId";
//                Log.e("getCategory list URL = ", url);
//                getdoctorListByCatId(url);
//            } catch (NullPointerException e) {
//                ToastClass.showToast(this, getString(R.string.too_slow));
//            }
//        } else {
//            ToastClass.showToast(this, getString(R.string.no_internet_access));
//        }
    }
    private void ClickListner() {
        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
//        drList.clear();
        // stopping swipe refresh
        swipeRefreshLayout.setRefreshing(false);
        //getdoctorListByCatId(url);
    }
}

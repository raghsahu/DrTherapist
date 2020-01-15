package com.example.drtherapist.client.activity;

import android.content.Intent;
import android.os.Bundle;
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
import com.example.drtherapist.client.adapter.DrListAdapterClient;
import com.example.drtherapist.client.model.DrListDataClient;
import com.example.drtherapist.common.Utils.NetworkUtil;
import com.example.drtherapist.common.Utils.ToastClass;
import com.example.drtherapist.common.Utils.Utils;
import com.example.drtherapist.common.remote.API;
import com.example.drtherapist.therapist.model.CategoryListData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ActivityDrListClient extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    private CategoryListData categoryData;
    private String categoryId,cateName;
    private RecyclerView recyclerview;
    private String url;
    private ImageView iv_back;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayout ll_no_record;
    private TextView tv_header;

    private List<DrListDataClient> drList;
    private DrListAdapterClient mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dr_list_client);

        Intent intent = getIntent();
        if (intent != null) {
            categoryId = intent.getStringExtra("CATEGORY");
            cateName = intent.getStringExtra("CATE_NAME");
        }

        initView();
        ClickListner();
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                //finish();
            }
        });
    }

    private void initView() {
        drList = new ArrayList<>();
        recyclerview = findViewById(R.id.recycler_view);
        iv_back = findViewById(R.id.iv_back);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        ll_no_record = findViewById(R.id.ll_no_record);
        tv_header = findViewById(R.id.tv_header);
            tv_header.setText(cateName);

        if (NetworkUtil.isNetworkConnected(this)) {
            try {
                url = API.BASE_URL + "doctorListByCatId";
                Log.e("getCategory list URL = ", url);
                getdoctorListByCatId(url);
            } catch (NullPointerException e) {
                ToastClass.showToast(this, getString(R.string.too_slow));
            }
        } else {
            ToastClass.showToast(this, getString(R.string.no_internet_access));
        }
    }

    private void ClickListner() {

        swipeRefreshLayout.setOnRefreshListener(this);
    }
    private void getdoctorListByCatId(String url) {
        Utils.showDialog(this, "Loading Please Wait...");
        AndroidNetworking.post(url)
                .addBodyParameter("cat_id", categoryId)
                .setTag("userLogin")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Utils.dismissDialog();
                        Log.e("Chat",""+jsonObject);
                        try {
                            String message = jsonObject.getString("msg");
                            String result = jsonObject.getString("result");

                            if (result.equalsIgnoreCase("true")) {
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject job = jsonArray.getJSONObject(i);
                                    DrListDataClient catData = new DrListDataClient();
                                    catData.id = job.getString("dr_id");
                                    catData.cat_id = job.getString("cat_id");
                                    catData.name = job.getString("dr_fname");
                                    catData.email = job.getString("dr_email");
                                    catData.experience = job.getString("experience");
                                    catData.address = job.getString("dr_address");
                                    catData.fee = job.getString("fee");
                                    catData.specialization = job.getString("specialization");
                                    catData.resume = job.getString("resume");
                                    catData.age = job.getString("age");
                                    catData.image = job.getString("dr_image");
                                    catData.rating = job.getString("rating");
                                    catData.status = job.getString("status");
                                    catData.location = job.getString("location");
                                    catData.fcm_id = job.getString("fcm_id");
                                    catData.dr_unique_key = job.getString("dr_unique_key");
                                    catData.cat_name = cateName;

                                    drList.add(catData);
                                }

                                mAdapter = new DrListAdapterClient(drList, ActivityDrListClient.this);
                                RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(ActivityDrListClient.this);
                                recyclerview.setLayoutManager(mLayoutManger);
                                recyclerview.setLayoutManager(new LinearLayoutManager(ActivityDrListClient.this, LinearLayoutManager.VERTICAL, false));
                                recyclerview.setItemAnimator(new DefaultItemAnimator());
                                recyclerview.setAdapter(mAdapter);

                                mAdapter.notifyDataSetChanged();
                            }

                            //check arraylist size
                            else {
                                swipeRefreshLayout.setVisibility(View.GONE);
                                ll_no_record.setVisibility(View.VISIBLE);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError error) {

                        Log.e("error = ", "" + error);
                    }
                });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //finish();
    }

    @Override
    public void onRefresh() {
        drList.clear();
        // stopping swipe refresh
        swipeRefreshLayout.setRefreshing(false);
        getdoctorListByCatId(url);
    }
}

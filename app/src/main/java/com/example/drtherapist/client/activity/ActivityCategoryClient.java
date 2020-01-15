package com.example.drtherapist.client.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.drtherapist.R;
import com.example.drtherapist.client.adapter.CategoryAdapterClient;
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

public class ActivityCategoryClient extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView recyclerview;
    private String url;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayout l_no_record;

    private List<CategoryListData> catagoryList;

    private CategoryAdapterClient mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_client);

        initView();
        ClickListner();
    }

    private void initView() {
        catagoryList = new ArrayList<>();
        recyclerview = findViewById(R.id.recycler_view);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        l_no_record = findViewById(R.id.no_record);


        if (NetworkUtil.isNetworkConnected(this)) {
            try {
                url = API.BASE_URL + "getCategory";
                Log.e("getCategory list URL = ", url);
                getcategoryList(url);
            } catch (NullPointerException e) {
                ToastClass.showToast(this, getString(R.string.too_slow));
            }
        } else {
            ToastClass.showToast(this, getString(R.string.no_internet_access));
        }
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


    private void getcategoryList(String url) {
        Utils.showDialog(this, "Loading Please Wait...");
        AndroidNetworking.get(url)
                /*.addPathParameter("pageNumber", "0")
                .addQueryParameter("limit", "3")
                .addHeaders("token", "1234")*/
                .setTag("category list")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Utils.dismissDialog();
                        try {
                            Log.e("GetCategory"," "+jsonObject);
                            String message = jsonObject.getString("msg");
                            String result = jsonObject.getString("result");

                            if (result.equalsIgnoreCase("true")) {
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject job = jsonArray.getJSONObject(i);
                                    CategoryListData catData = new CategoryListData();
                                    catData.id = job.getString("cat_id");
                                    catData.name = job.getString("cate_name");
                                    catData.image = job.getString("cat_img");

                                    catagoryList.add(catData);

                                }

                                mAdapter = new CategoryAdapterClient(catagoryList, ActivityCategoryClient.this);
                                RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(ActivityCategoryClient.this);
                                recyclerview.setLayoutManager(mLayoutManger);
                                recyclerview.setLayoutManager(new LinearLayoutManager(ActivityCategoryClient.this, LinearLayoutManager.VERTICAL, false));
                                recyclerview.setItemAnimator(new DefaultItemAnimator());
                                recyclerview.setAdapter(mAdapter);

                                mAdapter.notifyDataSetChanged();
                            }

                            //check arraylist size
                            if (catagoryList.size() == 0) {
                                swipeRefreshLayout.setVisibility(View.GONE);
                                l_no_record.setVisibility(View.VISIBLE);
                            } else {
                                swipeRefreshLayout.setVisibility(View.VISIBLE);
                                l_no_record.setVisibility(View.GONE);
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
    public void onRefresh() {
        catagoryList.clear();
        // stopping swipe refresh
        swipeRefreshLayout.setRefreshing(false);
        getcategoryList(url);
    }
}

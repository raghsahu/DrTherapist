package com.example.drtherapist.therapist.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.drtherapist.R;
import com.example.drtherapist.client.activity.ActivityCategoryClient;
import com.example.drtherapist.client.adapter.CategoryAdapterClient;
import com.example.drtherapist.client.adapter.OpenJobsAdapter;
import com.example.drtherapist.client.model.OpenCloseJobsModel;
import com.example.drtherapist.common.Utils.NetworkUtil;
import com.example.drtherapist.common.Utils.ToastClass;
import com.example.drtherapist.common.Utils.Utils;
import com.example.drtherapist.common.remote.API;
import com.example.drtherapist.common.remote.Session;
import com.example.drtherapist.therapist.adapter.NotificattionAdapter;
import com.example.drtherapist.therapist.model.CategoryListData;
import com.example.drtherapist.therapist.model.NotificationModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NotificationActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    ImageView iv_back;
    private RecyclerView recyclerview;
    private String url,userId;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayout l_no_record;
    Session session;
    private List<NotificationModel> notificationModels;
    private NotificattionAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        iv_back=findViewById(R.id.iv_back);

        session=new Session(this);
        userId =session.getUser().id;

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        initView();
        mAdapter = new NotificattionAdapter(notificationModels, NotificationActivity.this);
        RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(NotificationActivity.this);
        recyclerview.setLayoutManager(mLayoutManger);
        recyclerview.setLayoutManager(new LinearLayoutManager(NotificationActivity.this, LinearLayoutManager.VERTICAL, false));
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        recyclerview.setAdapter(mAdapter);
    }

    private void initView() {
        notificationModels = new ArrayList<>();
        recyclerview = findViewById(R.id.recycler_view);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        l_no_record = findViewById(R.id.no_record);


        if (NetworkUtil.isNetworkConnected(this)) {
            try {
               // url = API.BASE_URL + "getCategory";
                url ="jobListByStatus";
                Log.e("Notify list URL = ", url);
                //getNotificationApi(url);
            } catch (NullPointerException e) {
                ToastClass.showToast(this, getString(R.string.too_slow));
            }
        } else {
            ToastClass.showToast(this, getString(R.string.no_internet_access));
        }
    }

    private void getNotificationApi(String url) {
        Utils.showDialog(this, "Loading Please Wait...");
        AndroidNetworking.post(url)
                .addBodyParameter("status","1")
                .addBodyParameter("user_id", userId)
                .setTag("notify list")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Utils.dismissDialog();
                        try {
                            Log.e("notify res", "" + jsonObject);
                            String message = jsonObject.getString("msg");
                            String result = jsonObject.getString("result");
                            String is_status = jsonObject.getString("is_status");

                            if (result.equalsIgnoreCase("true")) {
                                JSONArray jsonArray = jsonObject.getJSONArray("data");

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject job = jsonArray.getJSONObject(i);
                                    NotificationModel notificationModel=new NotificationModel();

                                   // notificationModel.circleText=job.getString("cate_name").substring(0,1);
                                    notificationModel.headText=job.getString("cate_name");
                                    notificationModel.subText=job.getString("title");
                                    notificationModel.desText=job.getString("user_job_msg");

                                    notificationModels.add(notificationModel);
                                }
                                mAdapter = new NotificattionAdapter(notificationModels, NotificationActivity.this);
                                RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(NotificationActivity.this);
                                recyclerview.setLayoutManager(mLayoutManger);
                                recyclerview.setLayoutManager(new LinearLayoutManager(NotificationActivity.this, LinearLayoutManager.VERTICAL, false));
                                recyclerview.setItemAnimator(new DefaultItemAnimator());
                                recyclerview.setAdapter(mAdapter);

                                mAdapter.notifyDataSetChanged();
                            }


                            //check arraylist size



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
       // catagoryList.clear();
        // stopping swipe refresh
        swipeRefreshLayout.setRefreshing(false);
       // getcategoryList(url);
    }
}

package com.example.drtherapist.therapist.drawer;

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
import com.example.drtherapist.common.Utils.NetworkUtil;
import com.example.drtherapist.common.Utils.ToastClass;
import com.example.drtherapist.common.remote.API;
import com.example.drtherapist.common.remote.Session;
import com.example.drtherapist.therapist.adapter.AppliedJobsAdapter;
import com.example.drtherapist.therapist.model.ApplliedJobsModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ActivityApplyJobsList extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    private List<ApplliedJobsModel> applliedJobsModels;
    private AppliedJobsAdapter mAdapter;
    private RecyclerView recyclerview;
    private ImageView iv_back;
    private LinearLayout ll_no_record;
    private String url, DrId, catId;
    private Session session;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_jobs_list);
        session = new Session(this);
        DrId = session.getUser().id;
        catId = session.getUser().cat_id;
        initView();
        ClickListner();
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void initView() {
        applliedJobsModels = new ArrayList<>();
        recyclerview = findViewById(R.id.recycler_view);
        iv_back = findViewById(R.id.iv_back);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        ll_no_record = findViewById(R.id.ll_no_record);
        iv_back = findViewById(R.id.iv_back);
//        tv_header = findViewById(R.id.tv_header);
//        tv_header.setText(cateName);

        if (NetworkUtil.isNetworkConnected(this)) {
            try {
                url = API.BASE_URL + "JobListByDrId";
                Log.e("getCategory list URL = ", url);
                AppliedJobsApi(url);
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

    private void AppliedJobsApi(String url) {
        // Utils.showDialog(getActivity(), "Loading Please Wait...");
        AndroidNetworking.post(url)
                .addBodyParameter("dr_id", DrId)
                .setTag("joblist")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        //Utils.dismissDialog();
                        Log.e("AppliedList rep = ", "" + jsonObject);
                        try {
                            String message = jsonObject.getString("msg");
                            String result = jsonObject.getString("result");

                            if (result.equalsIgnoreCase("true")) {
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject job = jsonArray.getJSONObject(i);
                                    //BestMatchModel bestMatchModel=new BestMatchModel();
                                    ApplliedJobsModel applliedJobsModel = new ApplliedJobsModel();
                                    applliedJobsModel.aplied_id = job.getString("aplied_id");
                                    applliedJobsModel.job_id = job.getString("job_id");
//                                    applliedJobsModel.title = job.getString("title");
//                                    applliedJobsModel.cat_id = job.getString("cat_id");
//                                    applliedJobsModel.user_id = job.getString("user_id");
                                    applliedJobsModel.title = job.getString("title");
//                                    applliedJobsModel.job_request = job.getString("job_request");
                                    applliedJobsModel.location = job.getString("location");
//                                    applliedJobsModel.distance = job.getString("distance");
//                                    applliedJobsModel.description = job.getString("user_job_msg");
//                                    applliedJobsModel.job_type = job.getString("full_part_tym");
//                                    applliedJobsModel.days = job.getString("days");
//                                    applliedJobsModel.client_count = job.getString("client_count");
                                    applliedJobsModel.doller_rate = job.getString("doller_rate");
//                                    applliedJobsModel.post_date_in_days = job.getString("post_date_in_days");
//                                    applliedJobsModel.apply_status = job.getString("apply_status");
                                    applliedJobsModel.start_date = job.getString("start_date");
                                    applliedJobsModel.end_date = job.getString("end_date");
                                    applliedJobsModel.start_time = job.getString("start_time");
                                    applliedJobsModel.end_time = job.getString("end_time");
//                                    applliedJobsModel.max_price = job.getString("max_price");
//                                    applliedJobsModel.min_price = job.getString("min_price");
//                                    applliedJobsModel.date = job.getString("date");
//                                    applliedJobsModel.job_status = job.getString("job_status");
//                                    applliedJobsModel.user_name = job.getString("user_name");
//                                    applliedJobsModel.image = job.getString("user_image");
//                                    applliedJobsModel.user_age = job.getString("user_age");

                                    applliedJobsModels.add(applliedJobsModel);


                                }
                                mAdapter = new AppliedJobsAdapter(applliedJobsModels, ActivityApplyJobsList.this);
                                RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(ActivityApplyJobsList.this);
                                recyclerview.setLayoutManager(mLayoutManger);
                                recyclerview.setLayoutManager(new LinearLayoutManager(ActivityApplyJobsList.this, LinearLayoutManager.VERTICAL, false));
                                recyclerview.setItemAnimator(new DefaultItemAnimator());
                                recyclerview.setAdapter(mAdapter);

                                mAdapter.notifyDataSetChanged();
                            } else {
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
    public void onRefresh() {
        applliedJobsModels.clear();
        // stopping swipe refresh
        swipeRefreshLayout.setRefreshing(false);
        AppliedJobsApi(url);
    }
}

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
import com.example.drtherapist.client.adapter.ApplicantsAdapterClient;
import com.example.drtherapist.client.adapter.CategoryAdapterClient;
import com.example.drtherapist.client.model.ApplicantListData;
import com.example.drtherapist.common.Utils.NetworkUtil;
import com.example.drtherapist.common.Utils.ToastClass;
import com.example.drtherapist.common.Utils.Utils;
import com.example.drtherapist.common.remote.API;
import com.example.drtherapist.common.remote.Session;
import com.example.drtherapist.therapist.model.CategoryListData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ActivityViewApplicant extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView recyclerview_applicants;
    private String url;
    private SwipeRefreshLayout swipeRefreshLayout_applicants;
    private LinearLayout l_no_record_applicants;
    Session session;
    String userId;
    String JobId;

    private List<ApplicantListData> applicantListData;
    private ApplicantsAdapterClient applicantsAdapterClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_applicant);
        session = new Session(this);
        userId=session.getUser().id;

         JobId=getIntent().getStringExtra("JOBID");

        initView();
        ClickListner();

    }

    private void initView() {
        applicantListData = new ArrayList<>();
        recyclerview_applicants = findViewById(R.id.recycler_view_applicants);
        swipeRefreshLayout_applicants = findViewById(R.id.swipe_refresh_layout_applicants);
        l_no_record_applicants = findViewById(R.id.no_record_applicants);


        if (NetworkUtil.isNetworkConnected(this)) {
            try {
                url = API.BASE_URL + "applicantsList";
                Log.e("applicantsList URL = ", url);
                getApplicantsList(url);
            } catch (NullPointerException e) {
                ToastClass.showToast(this, getString(R.string.too_slow));
            }
        } else {
            ToastClass.showToast(this, getString(R.string.no_internet_access));
        }

    }

    private void getApplicantsList(String url) {
        Utils.showDialog(this, "Loading Please Wait...");
        AndroidNetworking.post(url)
                /*.addPathParameter("pageNumber", "0")
                .addQueryParameter("limit", "3")
                .addHeaders("token", "1234")*/
                .addBodyParameter("job_id",JobId)
                .setTag("applicants list")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Utils.dismissDialog();
                        try {
                            Log.e("applicants===",""+jsonObject);
                            String message = jsonObject.getString("msg");
                            String result = jsonObject.getString("result");

                            if (result.equalsIgnoreCase("true")) {
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject applicants = jsonArray.getJSONObject(i);
                                    ApplicantListData applicantData = new ApplicantListData();

                                    applicantData.job_id = applicants.getString("job_id");
                                    applicantData.dr_id = applicants.getString("dr_id");
                                    applicantData.dr_fname = applicants.getString("dr_fname");
                                    applicantData.dr_contact = applicants.getString("dr_contact");
                                    applicantData.dr_email = applicants.getString("dr_email");
                                    applicantData.dr_pass = applicants.getString("dr_pass");
                                    applicantData.dr_address = applicants.getString("dr_address");
                                    applicantData.dr_image = applicants.getString("dr_image");
                                    applicantData.dr_availability = applicants.getString("dr_availability");
                                    applicantData.timing = applicants.getString("timing");
                                    applicantData.fee = applicants.getString("fee");
                                    applicantData.specialization = applicants.getString("specialization");
                                    applicantData.lat = applicants.getString("lat");
                                    applicantData.lng = applicants.getString("lng");
                                    applicantData.location = applicants.getString("location");
                                    applicantData.experience = applicants.getString("experience");
                                    applicantData.review = applicants.getString("review");
                                    applicantData.resume = applicants.getString("resume");
                                    applicantData.age = applicants.getString("age");
                                    applicantData.created_date = applicants.getString("created_date");
                                    applicantData.updated_date = applicants.getString("updated_date");
                                    applicantData.dob = applicants.getString("dob");
                                    applicantData.city = applicants.getString("city");
                                    applicantData.state = applicants.getString("state");
                                    applicantData.zip = applicants.getString("zip");
                                    applicantData.auth_id = applicants.getString("auth_id");
                                    applicantData.fcm_id = applicants.getString("fcm_id");
                                    applicantData.rating = applicants.getString("rating");
                                    applicantData.doller_rate = applicants.getString("doller_rate");
                                    applicantData.type = applicants.getString("type");
                                    applicantData.gender = applicants.getString("gender");
                                    applicantData.aplied_id = applicants.getString("aplied_id");
                                    applicantData.job_id = applicants.getString("job_id");
                                    applicantData.dr_unique_key = applicants.getString("dr_unique_key");

                                    applicantListData.add(applicantData);
                                }

                                applicantsAdapterClient = new ApplicantsAdapterClient(applicantListData, ActivityViewApplicant.this);
                                RecyclerView.LayoutManager applicantsLayoutManger = new LinearLayoutManager(ActivityViewApplicant.this);
                                recyclerview_applicants.setLayoutManager(applicantsLayoutManger);
                                recyclerview_applicants.setLayoutManager(new LinearLayoutManager(ActivityViewApplicant.this, LinearLayoutManager.VERTICAL, false));
                                recyclerview_applicants.setItemAnimator(new DefaultItemAnimator());
                                recyclerview_applicants.setAdapter(applicantsAdapterClient);

                                applicantsAdapterClient.notifyDataSetChanged();
                            }

                            //check arraylist size
                            if (applicantListData.size() == 0) {
                                swipeRefreshLayout_applicants.setVisibility(View.GONE);
                                l_no_record_applicants.setVisibility(View.VISIBLE);
                            } else {
                                swipeRefreshLayout_applicants.setVisibility(View.VISIBLE);
                                l_no_record_applicants.setVisibility(View.GONE);
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


    private void ClickListner() {
        findViewById(R.id.iv_back_applicants).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        swipeRefreshLayout_applicants.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        applicantListData.clear();
        // stopping swipe refresh
        swipeRefreshLayout_applicants.setRefreshing(false);
        getApplicantsList(url);
    }
}

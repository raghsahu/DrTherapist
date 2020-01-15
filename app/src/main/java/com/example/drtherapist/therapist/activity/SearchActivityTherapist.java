package com.example.drtherapist.therapist.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.drtherapist.R;
import com.example.drtherapist.common.Utils.GPSTracker;
import com.example.drtherapist.common.Utils.NetworkUtil;
import com.example.drtherapist.common.Utils.ToastClass;
import com.example.drtherapist.common.Utils.Utils;
import com.example.drtherapist.common.remote.API;
import com.example.drtherapist.common.remote.Session;
import com.example.drtherapist.therapist.adapter.SearchTherapistAdapter;
import com.example.drtherapist.therapist.model.SearchTherapist;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchActivityTherapist extends AppCompatActivity {


    String url, query, categoryId;
    private Session session;
    private RecyclerView recycler_view_search;

    private List<SearchTherapist> searchTherapists;
    private SearchTherapistAdapter mAdapter;
    private LinearLayout ll_no_record;
    GPSTracker tracker;
    ImageView iv_back;
    double longitude, latitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_therapist);
        session = new Session(this);
        categoryId = session.getUser().cat_id;
        Log.e("catId_AllJobs", "" + categoryId);

        iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Intent intent = getIntent();
        query = intent.getStringExtra("SEARCHQUERY");
        Log.e("query", "" + query);
        tracker = new GPSTracker(SearchActivityTherapist.this);
        if (tracker.canGetLocation()) {
            latitude = tracker.getLatitude();
            longitude = tracker.getLongitude();
            Log.e("current_lat ", " " + String.valueOf(latitude));
            Log.e("current_Lon ", " " + String.valueOf(longitude));
//            address = getAddress(latitude, longitude);
//            Log.e("Address ", " " + getAddress(latitude, longitude));
        }else {
            Toast.makeText(this, "Location not found", Toast.LENGTH_SHORT).show();
        }

        initView();
    }

    private void initView() {

        recycler_view_search = findViewById(R.id.therepist_recycler_view_search);
        ll_no_record = findViewById(R.id.ll_no_record);

        if (NetworkUtil.isNetworkConnected(this)) {
            try {
                url = API.BASE_URL + "Search_jobs";
                searchApi(url);
            } catch (NullPointerException e) {
                ToastClass.showToast(this, getString(R.string.too_slow));
            }
        } else {
            ToastClass.showToast(this, getString(R.string.no_internet_access));
        }

    }



//http://logicalsofttech.com/therapist/index.php/Api/Search_jobs
    private void searchApi(String url) {
        Utils.showDialog(this, "Loading Please Wait...");
        AndroidNetworking.post(url)
                .addQueryParameter("search", query)
                .addQueryParameter("lat", String.valueOf(latitude))
                .addQueryParameter("lng", String.valueOf(longitude))
                .setTag("Searchjobs")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Utils.dismissDialog();
                        try {
                            Log.e("Searchjobs res", "" + jsonObject);
                            //String message = jsonObject.getString("msg");
                            String result = jsonObject.getString("result");
                            searchTherapists = new ArrayList<>();
                            if (result.equalsIgnoreCase("true")) {
                                JSONArray jsonArray = jsonObject.getJSONArray("data");

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject job = jsonArray.getJSONObject(i);
                                    SearchTherapist searchTherapist=new SearchTherapist();
                                    searchTherapist.job_id = job.getString("job_id");
                                    searchTherapist.cat_id = job.getString("cat_id");
                                    searchTherapist.cate_name = job.getString("cate_name");
                                    //searchTherapist.dr_id = job.getString("dr_id");
                                    searchTherapist.user_id = job.getString("user_id");
                                    searchTherapist.title = job.getString("title");
                                    searchTherapist.location = job.getString("location");
                                    searchTherapist.distance = job.getString("distance");
                                    searchTherapist.user_job_msg = job.getString("user_job_msg");
                                    //searchTherapist.full_part_tym = job.getString("full_part_tym");
                                   // searchTherapist.days = job.getString("days");
                                    //searchTherapist.client_count = job.getString("client_count");
                                    searchTherapist.doller_rate = job.getString("doller_rate");
                                    searchTherapist.apply_status = job.getString("apply_status");
                                    searchTherapist.start_date = job.getString("start_date");
                                    searchTherapist.end_date = job.getString("end_date");
                                    searchTherapist.start_time = job.getString("start_time");
                                    searchTherapist.end_time = job.getString("end_time");
                                    searchTherapist.max_price = job.getString("max_price");
                                    searchTherapist.min_price = job.getString("min_price");
                                    searchTherapist.date = job.getString("date");
                                    searchTherapist.job_status = job.getString("job_status");

//                                    all_jobs_model.user_name = job.getString("user_name");
//                                    all_jobs_model.user_image = job.getString("user_image");
//                                    all_jobs_model.user_age = job.getString("user_age");

                                    searchTherapists.add(searchTherapist);
                                }

                                mAdapter=new SearchTherapistAdapter(searchTherapists,SearchActivityTherapist.this);
                                RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(SearchActivityTherapist.this);
                                recycler_view_search.setLayoutManager(mLayoutManger);
                                recycler_view_search.setLayoutManager(new LinearLayoutManager(SearchActivityTherapist.this, LinearLayoutManager.VERTICAL, false));
                                recycler_view_search.setItemAnimator(new DefaultItemAnimator());
                                recycler_view_search.setAdapter(mAdapter);
                                mAdapter.notifyDataSetChanged();
                            } else {
                                Toast.makeText(getApplicationContext(),"Result search",Toast.LENGTH_SHORT).show();
                            }

                            //check arraylist size
                            if (searchTherapists.size() == 0) {
                                ll_no_record.setVisibility(View.VISIBLE);
                            } else {
                                ll_no_record.setVisibility(View.GONE);
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


}

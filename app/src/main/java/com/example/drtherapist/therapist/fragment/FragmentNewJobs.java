package com.example.drtherapist.therapist.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.drtherapist.R;
import com.example.drtherapist.common.Utils.NetworkUtil;
import com.example.drtherapist.common.Utils.ToastClass;
import com.example.drtherapist.common.Utils.Utils;
import com.example.drtherapist.common.remote.API;
import com.example.drtherapist.common.remote.Session;
import com.example.drtherapist.therapist.adapter.BestMatchAdapter;
import com.example.drtherapist.therapist.adapter.NewJobsAdapter;
import com.example.drtherapist.therapist.model.BestMatchModel;
import com.example.drtherapist.therapist.model.NewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FragmentNewJobs  extends Fragment {

    public FragmentNewJobs() {
    }
    private List<NewModel> newModels;
    private NewJobsAdapter newJobsAdapter;

    private RecyclerView recycler_view_new_jobs;
    String url, userId,category_id;
    LinearLayout ll_no_record;
    Session session;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_jobs, container, false);
        session = new Session(getActivity());
        userId = session.getUser().id;

//        Log.e("userId", "" + userId);
        newModels = new ArrayList<>();


       recycler_view_new_jobs = view.findViewById(R.id.recycler_view_new_jobs);
        ll_no_record = view.findViewById(R.id.ll_no_record);


        if (getActivity().getIntent() != null) {
            category_id = getActivity().getIntent().getStringExtra("CATEGORY");
            Log.e("intent joblist  = ", " category_id = " + category_id);
        }

         initView();
        return view;
    }

    private void initView() {

        if (NetworkUtil.isNetworkConnected(getActivity())) {
            try {
                url = API.BASE_URL + "NewJobList";
                newJobsApi(url);
            } catch (NullPointerException e) {
                ToastClass.showToast(getActivity(), getString(R.string.too_slow));
            }
        } else {
            ToastClass.showToast(getActivity(), getString(R.string.no_internet_access));
        }

    }
    private void newJobsApi(String url) {
        Utils.showDialog(getActivity(), "Loading Please Wait...");
        AndroidNetworking.post(url)
                .setTag("joblist")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Utils.dismissDialog();
                        Log.e("newJobs rep = ", "" + jsonObject);
                        try {
                            String message = jsonObject.getString("msg");
                            String result = jsonObject.getString("result");

                            if (result.equalsIgnoreCase("true")) {
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject job = jsonArray.getJSONObject(i);
                                    NewModel newModel=new NewModel();
                                    newModel.job_id = job.getString("job_id");
                                    newModel.cat_id = job.getString("cat_id");
                                    newModel.user_id = job.getString("user_id");
                                    newModel.title = job.getString("title");
                                    newModel.job_request = job.getString("job_request");
                                    newModel.location = job.getString("location");
                                    newModel.distance = job.getString("distance");
                                    newModel.description = job.getString("user_job_msg");
                                    newModel.job_type = job.getString("full_part_tym");
                                    newModel.days = job.getString("days");
                                    newModel.client_count = job.getString("client_count");
                                    newModel.doller_rate = job.getString("doller_rate");
                                    newModel.post_date_in_days = job.getString("post_date_in_days");
                                    newModel.apply_status = job.getString("apply_status");
                                    newModel.start_date = job.getString("start_date");
                                    newModel.end_date = job.getString("end_date");
                                    newModel.start_time = job.getString("start_time");
                                    newModel.end_time = job.getString("end_time");
                                    newModel.max_price = job.getString("max_price");
                                    newModel.min_price = job.getString("min_price");
                                    newModel.date = job.getString("date");
                                    newModel.job_status = job.getString("job_status");
                                    newModel.user_name = job.getString("user_name");
                                    newModel.image = job.getString("user_image");
                                    newModel.user_age = job.getString("user_age");

                                    newModels.add(newModel);
                                }
                                newJobsAdapter=new NewJobsAdapter(newModels,getActivity());
                                RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(getActivity());
                                recycler_view_new_jobs.setLayoutManager(mLayoutManger);
                                recycler_view_new_jobs.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                                recycler_view_new_jobs.setItemAnimator(new DefaultItemAnimator());
                                recycler_view_new_jobs.setAdapter(newJobsAdapter);

                                newJobsAdapter.notifyDataSetChanged();
                            }

                            //check arraylist size
                            if (newModels.size() == 0) {
                                // swipeRefreshLayout.setVisibility(View.GONE);
                                ll_no_record.setVisibility(View.VISIBLE);
                            } else {
                                //swipeRefreshLayout.setVisibility(View.VISIBLE);
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


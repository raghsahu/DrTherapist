package com.example.drtherapist.therapist.fragment;

import android.content.Context;
import android.content.Intent;
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
import android.widget.Button;
import android.widget.LinearLayout;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.drtherapist.R;
import com.example.drtherapist.client.activity.ActivityPostJobClient;
import com.example.drtherapist.client.adapter.CloseJobsAdapter;
import com.example.drtherapist.client.model.OpenCloseJobsModel;
import com.example.drtherapist.common.Utils.NetworkUtil;
import com.example.drtherapist.common.Utils.ToastClass;
import com.example.drtherapist.common.Utils.Utils;
import com.example.drtherapist.common.remote.API;
import com.example.drtherapist.common.remote.Session;
import com.example.drtherapist.therapist.adapter.BestMatchAdapter;
import com.example.drtherapist.therapist.model.BestMatchModel;
import com.example.drtherapist.therapist.model.JobListDataDr;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FragmentBestMatch extends Fragment {

    public FragmentBestMatch() {
    }

    private List<BestMatchModel> bestMatchModels;
    private BestMatchAdapter bestMatchAdapter;

    private RecyclerView recycler_view_best_match;
    String url, userId,category_id;
    LinearLayout ll_no_record;

    Session session;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_best_match, container, false);


        recycler_view_best_match = view.findViewById(R.id.recycler_view_best_match);
        ll_no_record = view.findViewById(R.id.ll_no_record);

        session = new Session(getActivity());
        userId = session.getUser().id;
//        Log.e("userId", "" + userId);
        bestMatchModels = new ArrayList<>();

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
                url = API.BASE_URL + "matchJobsListByCategory";
                bestMatchApi(url);
            } catch (NullPointerException e) {
                ToastClass.showToast(getActivity(), getString(R.string.too_slow));
            }
        } else {
            ToastClass.showToast(getActivity(), getString(R.string.no_internet_access));
        }

    }

    private void bestMatchApi(String url) {
       // Utils.showDialog(getActivity(), "Loading Please Wait...");
        AndroidNetworking.post(url)
                .addBodyParameter("cat_id", category_id)
                .addBodyParameter("user_id", userId)
                .setTag("joblist")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        //Utils.dismissDialog();
                        Log.e("bestMatch rep = ", "" + jsonObject);
                        try {
                            String message = jsonObject.getString("msg");
                            String result = jsonObject.getString("result");

                            if (result.equalsIgnoreCase("true")) {
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject job = jsonArray.getJSONObject(i);
                                    BestMatchModel bestMatchModel=new BestMatchModel();
                                    bestMatchModel.job_id = job.getString("job_id");
                                    bestMatchModel.cat_id = job.getString("cat_id");
                                    bestMatchModel.user_id = job.getString("user_id");
                                    bestMatchModel.title = job.getString("title");
                                    bestMatchModel.job_request = job.getString("job_request");
                                    bestMatchModel.location = job.getString("location");
                                    bestMatchModel.distance = job.getString("distance");
                                    bestMatchModel.description = job.getString("user_job_msg");
                                    bestMatchModel.job_type = job.getString("full_part_tym");
                                    bestMatchModel.days = job.getString("days");
                                    bestMatchModel.client_count = job.getString("client_count");
                                    bestMatchModel.doller_rate = job.getString("doller_rate");
                                    bestMatchModel.post_date_in_days = job.getString("post_date_in_days");
                                    bestMatchModel.apply_status = job.getString("apply_status");
                                    bestMatchModel.start_date = job.getString("start_date");
                                    bestMatchModel.end_date = job.getString("end_date");
                                    bestMatchModel.start_time = job.getString("start_time");
                                    bestMatchModel.end_time = job.getString("end_time");
                                    bestMatchModel.max_price = job.getString("max_price");
                                    bestMatchModel.min_price = job.getString("min_price");
                                    bestMatchModel.date = job.getString("date");
                                    bestMatchModel.job_status = job.getString("job_status");
                                    bestMatchModel.user_name = job.getString("user_name");
                                    bestMatchModel.image = job.getString("user_image");
                                    bestMatchModel.user_age = job.getString("user_age");

                                    bestMatchModels.add(bestMatchModel);


                                }
                                bestMatchAdapter=new BestMatchAdapter(bestMatchModels,getActivity());
                                RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(getActivity());
                                recycler_view_best_match.setLayoutManager(mLayoutManger);
                                recycler_view_best_match.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                                recycler_view_best_match.setItemAnimator(new DefaultItemAnimator());
                                recycler_view_best_match.setAdapter(bestMatchAdapter);

                                bestMatchAdapter.notifyDataSetChanged();
                            }

                            //check arraylist size
                            if (bestMatchModels.size() == 0) {
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


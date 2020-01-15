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
import com.example.drtherapist.therapist.adapter.NearByAdapter;
import com.example.drtherapist.therapist.adapter.NewJobsAdapter;
import com.example.drtherapist.therapist.model.NearByModel;
import com.example.drtherapist.therapist.model.NewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FragmentNearBy extends Fragment {

    public FragmentNearBy() {
    }
    private List<NearByModel> nearByModels;
    private NearByAdapter nearByAdapter;

    String latitute,longitute;
    String url, userId;
    Session session;
    private RecyclerView recycler_view_near_by;
    LinearLayout ll_no_record;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_near_by, container, false);
        session = new Session(getActivity());
        userId = session.getUser().id;
//        Log.e("userId", "" + userId);
        nearByModels = new ArrayList<>();
        latitute=session.getUser().lat;
        longitute=session.getUser().lng;

        recycler_view_near_by = view.findViewById(R.id.recycler_view_near_by);
        ll_no_record = view.findViewById(R.id.ll_no_record);


         initView();
        return view;
    }
    private void initView() {

        if (NetworkUtil.isNetworkConnected(getActivity())) {
            try {
                url = API.BASE_URL + "NewJobList";
                nearByApi(url);
            } catch (NullPointerException e) {
                ToastClass.showToast(getActivity(), getString(R.string.too_slow));
            }
        } else {
            ToastClass.showToast(getActivity(), getString(R.string.no_internet_access));
        }

    }


    private void nearByApi(String url) {
      //  Utils.showDialog(getActivity(), "Loading Please Wait...");
        AndroidNetworking.post(url)
//                .addBodyParameter("user_id", userId)
//                .addBodyParameter("lat", latitute)
//                .addBodyParameter("lng", longitute)
                .setTag("joblist")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                       // Utils.dismissDialog();
                        Log.e("NearByJobs rep = ", "" + jsonObject);
                        try {
                            String message = jsonObject.getString("msg");
                            String result = jsonObject.getString("result");

                            if (result.equalsIgnoreCase("true")) {
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject job = jsonArray.getJSONObject(i);
                                    NearByModel nearByModel=new NearByModel();
                                    nearByModel.job_id = job.getString("job_id");
                                    nearByModel.cat_id = job.getString("cat_id");
                                    nearByModel.user_id = job.getString("user_id");
                                    nearByModel.title = job.getString("title");
                                    nearByModel.job_request = job.getString("job_request");
                                    nearByModel.location = job.getString("location");
                                    nearByModel.distance = job.getString("distance");
                                    nearByModel.description = job.getString("user_job_msg");
                                    nearByModel.job_type = job.getString("full_part_tym");
                                    nearByModel.days = job.getString("days");
                                    nearByModel.client_count = job.getString("client_count");
                                    nearByModel.doller_rate = job.getString("doller_rate");
                                    nearByModel.post_date_in_days = job.getString("post_date_in_days");
                                    nearByModel.apply_status = job.getString("apply_status");
                                    nearByModel.start_date = job.getString("start_date");
                                    nearByModel.end_date = job.getString("end_date");
                                    nearByModel.start_time = job.getString("start_time");
                                    nearByModel.end_time = job.getString("end_time");
                                    nearByModel.max_price = job.getString("max_price");
                                    nearByModel.min_price = job.getString("min_price");
                                    nearByModel.date = job.getString("date");
                                    nearByModel.job_status = job.getString("job_status");
                                    nearByModel.user_name = job.getString("user_name");
                                    nearByModel.image = job.getString("user_image");
                                    nearByModel.user_age = job.getString("user_age");

                                    nearByModels.add(nearByModel);
                                }
                                nearByAdapter=new NearByAdapter(nearByModels,getActivity());
                                RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(getActivity());
                                recycler_view_near_by.setLayoutManager(mLayoutManger);
                                recycler_view_near_by.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                                recycler_view_near_by.setItemAnimator(new DefaultItemAnimator());
                                recycler_view_near_by.setAdapter(nearByAdapter);

                                nearByAdapter.notifyDataSetChanged();
                            }

                            //check arraylist size
                            if (nearByModels.size() == 0) {
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


package com.example.drtherapist.client.fragment;

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
import com.example.drtherapist.client.activity.ActivityViewApplicant;
import com.example.drtherapist.client.adapter.CloseJobsAdapter;
import com.example.drtherapist.client.adapter.OpenJobsAdapter;
import com.example.drtherapist.client.model.OpenCloseJobsModel;
import com.example.drtherapist.common.Utils.NetworkUtil;
import com.example.drtherapist.common.Utils.ToastClass;
import com.example.drtherapist.common.Utils.Utils;
import com.example.drtherapist.common.remote.API;
import com.example.drtherapist.common.remote.Session;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FragmentCloseJobs extends Fragment {

    public FragmentCloseJobs() {
    }

    LinearLayout linear_job_detail;
    Button btn_ViewApplicants_myjobs, btn_myjobs_post_job_close;
    private RecyclerView recycler_view_closeJobs;
    String url, userId;
    private Context context;
    Session session;
    private List<OpenCloseJobsModel> openCloseJobsModels;
    private CloseJobsAdapter mAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_close_jobs, container, false);
        session = new Session(getActivity());
        userId = session.getUser().id;
        Log.e("userId", "" + userId);
        openCloseJobsModels = new ArrayList<>();


        recycler_view_closeJobs = view.findViewById(R.id.recycler_view_closeJobs);

        btn_ViewApplicants_myjobs = (Button) view.findViewById(R.id.btn_ViewApplicants_myjobs);
        btn_myjobs_post_job_close = (Button) view.findViewById(R.id.btn_myjobs_post_job_close);


//        btn_ViewApplicants_myjobs.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(getActivity(), ActivityViewApplicant.class);
//                startActivity(intent);
//
//            }
//        });
        btn_myjobs_post_job_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ActivityPostJobClient.class);
                startActivity(intent);

            }
        });


        initView();
        return view;
    }

    private void initView() {

        if (NetworkUtil.isNetworkConnected(getActivity())) {
            try {
                url = API.BASE_URL + "jobListByStatus";
                closeJobsApi(url);
            } catch (NullPointerException e) {
                ToastClass.showToast(getActivity(), getString(R.string.too_slow));
            }
        } else {
            ToastClass.showToast(getActivity(), getString(R.string.no_internet_access));
        }

    }
    private void closeJobsApi(String url) {
        Utils.showDialog(context, "Loading Please Wait...");
        AndroidNetworking.post(url)
                .addBodyParameter("status","0")
                .addBodyParameter("user_id", userId)
                .setTag("closeJobs list")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Utils.dismissDialog();
                        try {
                            Log.e("closeJobs res", "" + jsonObject);
                            String message = jsonObject.getString("msg");
                            String result = jsonObject.getString("result");
                            String is_status = jsonObject.getString("is_status");

                            if (result.equalsIgnoreCase("true")) {
                                JSONArray jsonArray = jsonObject.getJSONArray("data");

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject job = jsonArray.getJSONObject(i);
                                    OpenCloseJobsModel openCloseJobsModel =new OpenCloseJobsModel();
                                    openCloseJobsModel.job_id=job.getString("job_id");
                                    openCloseJobsModel.cat_id=job.getString("cat_id");
                                    openCloseJobsModel.dr_id=job.getString("dr_id");
                                    openCloseJobsModel.user_id=job.getString("user_id");
                                    openCloseJobsModel.title=job.getString("title");
                                    openCloseJobsModel.job_request=job.getString("job_request");
                                    openCloseJobsModel.location=job.getString("location");
                                    openCloseJobsModel.distance=job.getString("distance");
                                    openCloseJobsModel.user_job_msg=job.getString("user_job_msg");
                                    openCloseJobsModel.full_part_tym=job.getString("full_part_tym");
                                    openCloseJobsModel.days=job.getString("days");
                                    openCloseJobsModel.client_count=job.getString("client_count");
                                    openCloseJobsModel.client_count=job.getString("doller_rate");
                                    openCloseJobsModel.post_date_in_days=job.getString("post_date_in_days");
                                    openCloseJobsModel.apply_status=job.getString("apply_status");
                                    openCloseJobsModel.start_date=job.getString("start_date");
                                    openCloseJobsModel.end_date=job.getString("end_date");
                                    openCloseJobsModel.end_time=job.getString("end_time");
                                    openCloseJobsModel.start_time=job.getString("start_time");
                                    openCloseJobsModel.max_price=job.getString("max_price");
                                    openCloseJobsModel.min_price=job.getString("min_price");
                                    openCloseJobsModel.date=job.getString("date");
                                    openCloseJobsModel.job_status=job.getString("job_status");

                                    openCloseJobsModels.add(openCloseJobsModel);
                                }


                                mAdapter=new CloseJobsAdapter(openCloseJobsModels,getActivity());
                                RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(context);
                                recycler_view_closeJobs.setLayoutManager(mLayoutManger);
                                recycler_view_closeJobs.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                                recycler_view_closeJobs.setItemAnimator(new DefaultItemAnimator());
                                recycler_view_closeJobs.setAdapter(mAdapter);

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
}

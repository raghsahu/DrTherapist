package com.example.drtherapist.therapist.drawer;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.drtherapist.R;
import com.example.drtherapist.client.activity.ActivityDrListClient;
import com.example.drtherapist.client.activity.ActivityMemberShipClient;
import com.example.drtherapist.client.drawer.ActivityMessageClient;
import com.example.drtherapist.client.model.ApplicantListData;
import com.example.drtherapist.client.model.DrListDataClient;
import com.example.drtherapist.client.model.DrListDataExp;
import com.example.drtherapist.common.Utils.NetworkUtil;
import com.example.drtherapist.common.Utils.ToastClass;
import com.example.drtherapist.common.Utils.Utils;
import com.example.drtherapist.common.remote.API;
import com.example.drtherapist.common.remote.Session;
import com.example.drtherapist.therapist.activity.ActivityMemberShipDr;
import com.example.drtherapist.therapist.activity.ActivityNavigationDr;
import com.example.drtherapist.therapist.adapter.BestMatchAdapter;
import com.example.drtherapist.therapist.model.ApplliedJobsModel;
import com.example.drtherapist.therapist.model.BestMatchModel;
import com.example.drtherapist.therapist.model.JobListDataDr;
import com.example.drtherapist.therapist.model.NearByModel;
import com.example.drtherapist.therapist.model.NewModel;
import com.example.drtherapist.therapist.model.SearchTherapist;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ActivityApplyJob extends AppCompatActivity implements View.OnClickListener  {
    LinearLayout li_apply;
    TextView txt_applyJobs, tv_job_title_Apply, tv_jobtype_apply,
            tv_desc_apply, tv_min_price_apply, tv_max_price_apply,
            tv_child_number, tv_age_apply, tv_startdate_apply, tv_startdate, tv_endDate, tv_days, tv_location_apply, tv_children, tv_post_date, tv_username;
    BestMatchModel bestMatchModel;
    NewModel newModel;
    NearByModel nearByModel;
    SearchTherapist searchTherapist;
    ApplliedJobsModel applliedJobsModel;
    String jobModel;
    Session session;
    String userId, catId;
    String jobId,member;
    String url_applicant;
    Dialog dialog;
    TextView tv_ok;
    TextView tv_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_job);
        session = new Session(this);
        userId = session.getUser().id;
        catId = session.getUser().cat_id;
        Log.e("userIDApply", "" + userId);
        Log.e("catIDApply", "" + catId);

        Intent intent = getIntent();
        if (intent != null) {

            if (intent.hasExtra("best")) {
                bestMatchModel = (BestMatchModel) getIntent().getSerializableExtra("jobModel");
            }
            if (intent.hasExtra("new")) {
                newModel = (NewModel) getIntent().getSerializableExtra("jobModel");
            }
            if (intent.hasExtra("nearby")) {
                nearByModel = (NearByModel) getIntent().getSerializableExtra("jobModel");
            }
            if (intent.hasExtra("search")) {
                searchTherapist = (SearchTherapist) getIntent().getSerializableExtra("jobModel");
            }
            if (intent.hasExtra("latest")) {
                bestMatchModel = (BestMatchModel) getIntent().getSerializableExtra("jobModel");
            }
            if (intent.hasExtra("applied")) {
                applliedJobsModel = (ApplliedJobsModel) getIntent().getSerializableExtra("jobModel");
            }
        }

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        member = pref.getString("MEMBEREDDr", null);
        Log.e("member", "" + member);
        if (member == null) {
          //  openDialogMember();     // open dialog after api check membership status
        }
        Toolbar toolbar = findViewById(R.id.toolbar_apply_list);
        //setSupportActionBar(toolbar);
        toolbar.setTitle("Job Detail");
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_backspace_black_24dp);
        txt_applyJobs = (TextView) findViewById(R.id.apply_jobs_apply_now);
        li_apply = findViewById(R.id.li_apply);
        tv_job_title_Apply = (TextView) findViewById(R.id.tv_job_title_Apply);
        //tv_jobtype_apply = (TextView) findViewById(R.id.tv_jobtype_apply);
        tv_desc_apply = (TextView) findViewById(R.id.tv_desc_apply);
        tv_min_price_apply = (TextView) findViewById(R.id.tv_min_price_apply);
        tv_max_price_apply = (TextView) findViewById(R.id.tv_max_price_apply);
        tv_child_number = (TextView) findViewById(R.id.tv_child_number);
        tv_age_apply = (TextView) findViewById(R.id.tv_age_apply);
        tv_startdate_apply = (TextView) findViewById(R.id.tv_startdate_apply);
        tv_startdate = (TextView) findViewById(R.id.tv_startdate);
        tv_endDate = (TextView) findViewById(R.id.tv_endDate);
        tv_location_apply = (TextView) findViewById(R.id.tv_location_apply);
        tv_days = (TextView) findViewById(R.id.tv_days);
        tv_children = (TextView) findViewById(R.id.tv_children);
        tv_post_date = (TextView) findViewById(R.id.tv_post_date);
        //tv_username = (TextView) findViewById(R.id.tv_username);



        if (NetworkUtil.isNetworkConnected(getApplicationContext())) {
            try {
              String  url = API.BASE_URL + "Dr_APP_Membership";
                userAppMemberApi(url);
            } catch (NullPointerException e) {
                ToastClass.showToast(getApplicationContext(), getString(R.string.too_slow));
            }
        } else {
            ToastClass.showToast(getApplicationContext(), getString(R.string.no_internet_access));
        }


        txt_applyJobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NetworkUtil.isNetworkConnected(getApplicationContext())) {
                    try {
                        url_applicant = API.BASE_URL + "ApplyByDr";
                        applyJob(url_applicant);
                    } catch (NullPointerException e) {
                        ToastClass.showToast(getApplicationContext(), getString(R.string.too_slow));
                    }
                } else {
                    ToastClass.showToast(getApplicationContext(), getString(R.string.no_internet_access));
                }
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        if (bestMatchModel != null) {
            jobId = bestMatchModel.job_id;
            Log.e("jobId_apply", "" + jobId);

            tv_job_title_Apply.setText(bestMatchModel.title);
            //tv_jobtype_apply.setText(bestMatchModel.job_type);
            tv_desc_apply.setText(bestMatchModel.description);
            tv_min_price_apply.setText(bestMatchModel.min_price);
            tv_max_price_apply.setText(bestMatchModel.max_price);
            tv_child_number.setText(bestMatchModel.client_count);
            tv_age_apply.setText(bestMatchModel.user_age);
            tv_startdate_apply.setText(bestMatchModel.start_date);
            tv_startdate.setText("Starts: " + bestMatchModel.start_date);
            tv_endDate.setText("End: " + bestMatchModel.end_date);
            tv_location_apply.setText(bestMatchModel.location);
            tv_days.setText(bestMatchModel.days);
            tv_children.setText(bestMatchModel.client_count);
            tv_post_date.setText("Posted " + bestMatchModel.date + "   " + " by:- " + bestMatchModel.user_name);
            // tv_username.setText(jobListDataDr.user_name);

        }

        if (newModel != null) {
            jobId = newModel.job_id;
            Log.e("jobId_apply", "" + jobId);

            tv_job_title_Apply.setText(newModel.title);
            // tv_jobtype_apply.setText(newModel.job_type);
            tv_desc_apply.setText(newModel.description);
            tv_min_price_apply.setText(newModel.min_price);
            tv_max_price_apply.setText(newModel.max_price);
            tv_child_number.setText(newModel.client_count);
            tv_age_apply.setText(newModel.user_age);
            tv_startdate_apply.setText(newModel.start_date);
            tv_startdate.setText("Starts: " + newModel.start_date);
            tv_endDate.setText("End: " + newModel.end_date);
            tv_location_apply.setText(newModel.location);
            tv_days.setText(newModel.days);
            tv_children.setText(newModel.client_count);
            tv_post_date.setText("Posted " + newModel.date + "   " + " by:- " + newModel.user_name);
            // tv_username.setText(jobListDataDr.user_name);

        }
        if (nearByModel != null) {
            jobId = nearByModel.job_id;
            Log.e("jobId_apply", "" + jobId);

            tv_job_title_Apply.setText(nearByModel.title);
            // tv_jobtype_apply.setText(nearByModel.job_type);
            tv_desc_apply.setText(nearByModel.description);
            tv_min_price_apply.setText(nearByModel.min_price);
            tv_max_price_apply.setText(nearByModel.max_price);
            tv_child_number.setText(nearByModel.client_count);
            tv_age_apply.setText(nearByModel.user_age);
            tv_startdate_apply.setText(nearByModel.start_date);
            tv_startdate.setText("Starts: " + nearByModel.start_date);
            tv_endDate.setText("End: " + nearByModel.end_date);
            tv_location_apply.setText(nearByModel.location);
            tv_days.setText(nearByModel.days);
            tv_children.setText(nearByModel.client_count);
            tv_post_date.setText("Posted " + nearByModel.date + "   " + " by:- " + nearByModel.user_name);
            // tv_username.setText(jobListDataDr.user_name);

        }

        if (searchTherapist != null) {
            jobId = searchTherapist.job_id;
            Log.e("jobId_apply", "" + jobId);

            tv_job_title_Apply.setText(searchTherapist.title);
            //tv_jobtype_apply.setText(searchTherapist.full_part_tym);
            tv_desc_apply.setText(searchTherapist.user_job_msg);
            tv_min_price_apply.setText(searchTherapist.min_price);
            tv_max_price_apply.setText(searchTherapist.max_price);
            tv_child_number.setText(searchTherapist.client_count);
            // tv_age_apply.setText(searchTherapist.user_age);
            tv_startdate_apply.setText(searchTherapist.start_date);
            tv_startdate.setText("Starts: " + searchTherapist.start_date);
            tv_endDate.setText("End: " + searchTherapist.end_date);
            tv_location_apply.setText(searchTherapist.location);
            tv_days.setText(searchTherapist.days);
            tv_children.setText(searchTherapist.client_count);
            // tv_post_date.setText("Posted " + searchTherapist.date + "   " + " by:- " + searchTherapist.user_name);
            // tv_username.setText(jobListDataDr.user_name);

        }

        if (applliedJobsModel != null) {
            jobId = applliedJobsModel.job_id;
            Log.e("jobId_apply", "" + jobId);
            Log.e("applystatus", "" + applliedJobsModel.aplied_id);
            if (applliedJobsModel.aplied_id != null) {
                li_apply.setVisibility(View.GONE);
            }

            tv_job_title_Apply.setText(applliedJobsModel.title);
            //tv_jobtype_apply.setText(searchTherapist.full_part_tym);
            // tv_desc_apply.setText(applliedJobsModel.user_job_msg);
            tv_min_price_apply.setText(applliedJobsModel.min_price);
            tv_max_price_apply.setText(applliedJobsModel.max_price);
            tv_child_number.setText(applliedJobsModel.client_count);
            // tv_age_apply.setText(searchTherapist.user_age);
            tv_startdate_apply.setText(applliedJobsModel.start_date);
            tv_startdate.setText("Starts: " + applliedJobsModel.start_date);
            tv_endDate.setText("End: " + applliedJobsModel.end_date);
            tv_location_apply.setText(applliedJobsModel.location);
            tv_days.setText(applliedJobsModel.days);
            tv_children.setText(applliedJobsModel.client_count);
            // tv_post_date.setText("Posted " + searchTherapist.date + "   " + " by:- " + searchTherapist.user_name);
            // tv_username.setText(jobListDataDr.user_name);

        }


    }

    private void userAppMemberApi(String url) {
        Utils.showDialog(this, "Loading Please Wait...");
        AndroidNetworking.post(url)
                /*.addPathParameter("pageNumber", "0")
                .addQueryParameter("limit", "3")
                .addHeaders("token", "1234")*/
                .addBodyParameter("user_id", userId)
                .addBodyParameter("type", "threpist")
                .setTag("membershipApp job")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Utils.dismissDialog();
                        Log.e("memberAppUser resp = ", "" + jsonObject);
                        try {
                            String message = jsonObject.getString("msg");
                            String result = jsonObject.getString("result");

                            if (result.equalsIgnoreCase("true")) {
                               // Toast.makeText(getApplicationContext(), "Thank you !!!!!!!!", Toast.LENGTH_LONG).show();



                            } else {
                                openDialogMember();
                                Toast.makeText(getApplicationContext(), "" + message, Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("exception = ", "" + e);
                        }
                    }

                    @Override
                    public void onError(ANError error) {

                        Log.e("error = ", "" + error);
                    }
                });

    }

    private void openDialogMember() {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.membership_dialog_layout);
        tv_ok = dialog.findViewById(R.id.tv_ok);
        tv_back = dialog.findViewById(R.id.tv_back);
//        btn_forgot_submit = dialog.findViewById(R.id.btn_forgot_submit);

        tv_ok.setOnClickListener(this);
        tv_back.setOnClickListener(this);

        dialog.show();
    }


    private void applyJob(String url) {

        Utils.showDialog(this, "Loading Please Wait...");
        AndroidNetworking.post(url)
                /*.addPathParameter("pageNumber", "0")
                .addQueryParameter("limit", "3")
                .addHeaders("token", "1234")*/
                .addBodyParameter("dr_id", userId)
                .addBodyParameter("cat_id", catId)
                .addBodyParameter("status", "yes")
                .addBodyParameter("job_id", jobId)

                .setTag("apply job")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Utils.dismissDialog();
                        Log.e("apply job resp = ", "" + jsonObject);
                        try {
                            String message = jsonObject.getString("msg");
                            String result = jsonObject.getString("result");

                            if (result.equalsIgnoreCase("true")) {
                                Toast.makeText(getApplicationContext(), "Successfully Apply Job", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getApplicationContext(), ActivityNavigationDr.class);
                                startActivity(intent);
                                // applicantList.add(applicantData);
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject applicants = jsonArray.getJSONObject(i);
                                    // ApplicantListData applicantData = new ApplicantListData();
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "" + message, Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("exception = ", "" + e);
                        }
                    }

                    @Override
                    public void onError(ANError error) {

                        Log.e("error = ", "" + error);
                    }
                });

    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {

            case R.id.tv_ok:
                intent = new Intent(getApplicationContext(), ActivityMemberShipDr.class);
                intent.putExtra("login_type","threpist");
                startActivity(intent);
                break;
        case R.id.tv_back:

            Intent intent1=new Intent(getApplicationContext(), ActivityNavigationDr.class);
            startActivity(intent1);
            finish();
                break;

        }


    }


    @Override
    public void onBackPressed() {
     //   super.onBackPressed();

        Intent intent1=new Intent(getApplicationContext(), ActivityNavigationDr.class);
        startActivity(intent1);
        finish();
    }
}

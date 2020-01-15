package com.example.drtherapist.therapist.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.drtherapist.R;
import com.example.drtherapist.client.activity.ActivityDrDetailClient;
import com.example.drtherapist.client.activity.MainActivity;
import com.example.drtherapist.common.Adapter.AllBlogAdapter;
import com.example.drtherapist.common.Adapter.MemberPlanAdapter;
import com.example.drtherapist.common.BasicActivities.ActivityBlog;
import com.example.drtherapist.common.BasicActivities.ActivitySupport;
import com.example.drtherapist.common.Model.MemberPlanModel;
import com.example.drtherapist.common.Utils.NetworkUtil;
import com.example.drtherapist.common.Utils.ToastClass;
import com.example.drtherapist.common.Utils.Utils;
import com.example.drtherapist.common.remote.API;
import com.example.drtherapist.common.remote.Session;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.drtherapist.common.remote.API.BASE_URL;

public class ActivityMemberShipDr extends AppCompatActivity {
    String url,urlEmail, userId,emailUser;
    Session session;
    TextView txt_callUsNow,tv_member_name;
    Context context;
    ImageView iv_plan1, iv_plan2, iv_plan3;
    RecyclerView recycler_plan;
    MemberPlanAdapter memberPlanAdapter;
    ArrayList<MemberPlanModel>memberPlanModels=new ArrayList<>();
     String login_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_ship_dr);

        session = new Session(this);
        userId = session.getUser().id;
        emailUser = session.getUser().email;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_booknow);
        //setSupportActionBar(toolbar);
        toolbar.setTitle("Book Now");
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_backspace_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        txt_callUsNow = findViewById(R.id.call_us_now);
        recycler_plan = findViewById(R.id.recycler_plan);
        iv_plan1 = findViewById(R.id.iv_plan1);
        iv_plan2 = findViewById(R.id.iv_plan2);
        iv_plan3 = findViewById(R.id.iv_plan3);
        tv_member_name = findViewById(R.id.tv_member_name);
        tv_member_name.setText(session.getUser().username);

        try {
            if (getIntent()!=null){
                login_type=getIntent().getStringExtra("login_type");
            }


        }catch (Exception e){

        }

        if (NetworkUtil.isNetworkConnected(this)){
            MemberPlanType();
        }else {
            Toast.makeText(this, "Please check internet", Toast.LENGTH_SHORT).show();
        }

        //********************onclick*********************************
        txt_callUsNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_CALL);
//
//                intent.setData(Uri.parse("tel:" + 1234567890));
//                startActivity(intent);
            }
        });
        iv_plan1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (NetworkUtil.isNetworkConnected(getApplicationContext())) {
                    try {
                        url = API.BASE_URL + "Dr_APP_Membership";
                        urlEmail = API.BASE_URL + "Send_Email_Membership";
                        userAppMemberApi(url);
                    } catch (NullPointerException e) {
                        ToastClass.showToast(getApplicationContext(), getString(R.string.too_slow));
                    }
                } else {
                    ToastClass.showToast(getApplicationContext(), getString(R.string.no_internet_access));
                }
                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("MEMBEREDDr", "MEMBEREDDr");
                editor.commit();
                Intent intent=new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);

            }
        });
        iv_plan2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NetworkUtil.isNetworkConnected(getApplicationContext())) {
                    try {
                        url = API.BASE_URL + "Dr_APP_Membership";
                        urlEmail = API.BASE_URL + "Send_Email_Membership";
                        userAppMemberApi(url);
                    } catch (NullPointerException e) {
                        ToastClass.showToast(getApplicationContext(), getString(R.string.too_slow));
                    }
                } else {
                    ToastClass.showToast(getApplicationContext(), getString(R.string.no_internet_access));
                }
                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("MEMBEREDDr", "MEMBEREDDr");
                editor.commit();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);

            }
        });
        iv_plan3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NetworkUtil.isNetworkConnected(getApplicationContext())) {
                    try {
                        url = API.BASE_URL + "Dr_APP_Membership";
                        urlEmail = API.BASE_URL + "Send_Email_Membership";
                        userAppMemberApi(url);
                    } catch (NullPointerException e) {
                        ToastClass.showToast(getApplicationContext(), getString(R.string.too_slow));
                    }
                } else {
                    ToastClass.showToast(getApplicationContext(), getString(R.string.no_internet_access));
                }
                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("MEMBEREDDr", "MEMBEREDDr");
                editor.commit();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);

            }
        });


    }

    private void MemberPlanType() {
        Utils.showDialog(this, "Loading Please Wait...");
        AndroidNetworking.get(BASE_URL+"plan_list")
               // .addBodyParameter("user_id", user_id)
               // .setTag("joblist")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Utils.dismissDialog();
                        Log.e("member_plan_res= ", "" + jsonObject);
                        try {
                            String message = jsonObject.getString("msg");
                            String result = jsonObject.getString("result");

                           // Toast.makeText(ActivityMemberShipDr.this, ""+message, Toast.LENGTH_SHORT).show();
                            if (result.equalsIgnoreCase("true")) {

                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject job = jsonArray.getJSONObject(i);

                                    String id = job.getString("id");
                                    String daytime_slot_name = job.getString("daytime_slot_name");
                                    String price = job.getString("price");
                                    String plan_type = job.getString("plan_type");

                                    memberPlanModels.add(i, new MemberPlanModel(
                                            id,daytime_slot_name,price,plan_type
                                    ));

                                 }


                                memberPlanAdapter= new MemberPlanAdapter( ActivityMemberShipDr.this,memberPlanModels,login_type);
                                RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(ActivityMemberShipDr.this);
                                recycler_plan.setLayoutManager(mLayoutManger);
                                recycler_plan.setLayoutManager(new LinearLayoutManager(ActivityMemberShipDr.this, RecyclerView.VERTICAL, false));
                                recycler_plan.setItemAnimator(new DefaultItemAnimator());
                                recycler_plan.setAdapter(memberPlanAdapter);

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        Utils.dismissDialog();
                        Log.e("error = ", "" + error);
                    }
                });

    }


    private void userAppMemberApi(String url) {
        Utils.showDialog(this, "Loading Please Wait...");
        AndroidNetworking.post(url)
                /*.addPathParameter("pageNumber", "0")
                .addQueryParameter("limit", "3")
                .addHeaders("token", "1234")*/
                .addBodyParameter("dr_id", userId)
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
                                Toast.makeText(getApplicationContext(), "Thank you !!!!!!!!", Toast.LENGTH_LONG).show();
                                // applicantList.add(applicantData);
                                //JSONObject job = jsonObject.getJSONObject("data");
//                                job.getString("user_id");
//                                job.getString("status");
                                //Log.e("status", "" + job.getString("status"));

                                sendEmailDr(urlEmail);

                                Intent intent1=new Intent(getApplicationContext(), ActivityNavigationDr.class);
                                startActivity(intent1);
                                finish();

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

    private void sendEmailDr(String url) {

        //mdialog = Utils.showProgress(ActivityLogin.this);
        Utils.showDialog(context, "Loading Please Wait...");
        AndroidNetworking.post(url)
                .addBodyParameter("user_email", emailUser)
                .setTag("Membership Email")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        //Utils.hideProgress(mdialog);
                        Utils.dismissDialog();
                        //Log.e("Forgot resp = ", "" + jsonObject);
                        try {

                            //JSONObject jsonObject = new JSONObject(response);
                            String message = jsonObject.getString("msg");
                            String result = jsonObject.getString("result");

                            if (result.equalsIgnoreCase("true")) {

                                //Toast.makeText(context, "Password Send Successfully", Toast.LENGTH_SHORT).show();

                                //dialog.dismiss();
                            } else {
                                ToastClass.showToast(ActivityMemberShipDr.this, result);
                                //Utils.openAlertDialog(context, message);
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

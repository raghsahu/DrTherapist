package com.example.drtherapist.client.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
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
import com.example.drtherapist.common.Utils.NetworkUtil;
import com.example.drtherapist.common.Utils.ToastClass;
import com.example.drtherapist.common.Utils.Utils;
import com.example.drtherapist.common.remote.API;
import com.example.drtherapist.common.remote.Session;

import org.json.JSONException;
import org.json.JSONObject;

public class ActivityMemberShipClient extends AppCompatActivity {
    TextView txt_callUsNow;
    Context context;
    Bundle bundle;
    String url, urlEmail, userId, emailUser;
    Session session;
    private static final int REQUEST_PHONE_CALL = 1;

    ImageView iv_plan1, iv_plan2, iv_plan3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_ship_client);
        context=ActivityMemberShipClient.this;
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


//        Intent intent = getIntent();
//        if (intent != null) {
//            cateName = intent.getStringExtra("CATE_NAME");
//        }

        txt_callUsNow = findViewById(R.id.call_us_now);
        iv_plan1 = findViewById(R.id.iv_plan1);
        iv_plan2 = findViewById(R.id.iv_plan2);
        iv_plan3 = findViewById(R.id.iv_plan3);
        txt_callUsNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "call", Toast.LENGTH_SHORT).show();


                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "+918511812660"));

                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(ActivityMemberShipClient.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(ActivityMemberShipClient.this, new String[]{Manifest.permission.CALL_PHONE},REQUEST_PHONE_CALL);
                    }
                    else
                    {
                        startActivity(intent);
                    }
                }
                else
                {
                    startActivity(intent);
                }



              /*  Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + 1234567890));
                startActivity(intent);*/



            }
        });
        iv_plan1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (NetworkUtil.isNetworkConnected(getApplicationContext())) {
                    try {
                        url = API.BASE_URL + "User_APP_Membership";
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
                editor.putString("MEMBERED", "MEMBERED");
                editor.commit();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);

            }
        });
        iv_plan2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NetworkUtil.isNetworkConnected(getApplicationContext())) {
                    try {
                        url = API.BASE_URL + "User_APP_Membership";
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
                editor.putString("MEMBERED", "MEMBERED");
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
                        url = API.BASE_URL + "User_APP_Membership";
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
                editor.putString("MEMBERED", "MEMBERED");
                editor.commit();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);

            }
        });


    }

    private void userAppMemberApi(String url) {
        Utils.showDialog(this, "Loading Please Wait...");
        AndroidNetworking.post(url)
                /*.addPathParameter("pageNumber", "0")
                .addQueryParameter("limit", "3")
                .addHeaders("token", "1234")*/
                .addBodyParameter("user_id", userId)
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

                                sendEmailUser(urlEmail);

                                Intent intent1 = new Intent(getApplicationContext(), ActivityDrDetailClient.class);
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

    private void sendEmailUser(String url) {

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
                                //ToastClass.showToast(ActivityLogin.this, result);
                                Utils.openAlertDialog(context, message);
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

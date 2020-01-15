package com.example.drtherapist.client.drawer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.drtherapist.R;
import com.example.drtherapist.client.activity.MainActivity;
import com.example.drtherapist.client.adapter.ClientBookingHistoryAdapter;
import com.example.drtherapist.client.adapter.ExpDrListAdapterClient;
import com.example.drtherapist.client.model.ClientSideHistoryModel;
import com.example.drtherapist.client.model.DrListDataExp;
import com.example.drtherapist.common.Utils.NetworkUtil;
import com.example.drtherapist.common.Utils.Utils;
import com.example.drtherapist.common.remote.API;
import com.example.drtherapist.common.remote.Session;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ActivityBookingClient extends AppCompatActivity {

    RecyclerView recycler_history;
    Session session;
    String userId;
    private List<ClientSideHistoryModel> drList=new ArrayList<>();
    ClientBookingHistoryAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_client);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_booking);
        //setSupportActionBar(toolbar);
        toolbar.setTitle("Booking List");
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_backspace_black_24dp);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        recycler_history=findViewById(R.id.recycler_history);

        session = new Session(this);
        userId = session.getUser().id;

        if (NetworkUtil.isNetworkConnected(ActivityBookingClient.this)){
           String url = API.BASE_URL + "user_booking_history";
            GetMyBooking(url);

        }else {
            Toast.makeText(this, "Please check internet", Toast.LENGTH_SHORT).show();
        }



    }

    private void GetMyBooking(String url) {

        Utils.showDialog(this, "Loading Please Wait...");
        AndroidNetworking.post(url)
                .addBodyParameter("user_id",userId)
                .setTag("dr list")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Utils.dismissDialog();
                        Log.e("dr list resp = ", "" + jsonObject);
                        try {
                            String message = jsonObject.getString("msg");
                            String result = jsonObject.getString("result");

                            if (result.equalsIgnoreCase("true")) {
                                JSONArray jsonArray = jsonObject.getJSONArray("data");

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject job = jsonArray.getJSONObject(i);
                                    ClientSideHistoryModel drData = new ClientSideHistoryModel();

                                    drData.id = job.getString("id");
                                    drData.user_id = job.getString("user_id");
                                    drData.dr_id = job.getString("dr_id");
                                    drData.name = job.getString("name");
                                    drData.email = job.getString("email");
                                    drData.mobile = job.getString("mobile");
                                    drData.start_date = job.getString("start_date");
                                    drData.end_date = job.getString("end_date");
                                    drData.amount = job.getString("amount");
                                    drData.description = job.getString("description");
                                    drData.member = job.getString("member");
                                    drData.create_date = job.getString("create_date");
                                    drData.status = job.getString("status");
                                    drData.create_time = job.getString("create_time");
                                    drData.dr_fname = job.getString("dr_fname");
                                    drData.dr_image = job.getString("dr_image");
                                    drData.cate_name = job.getString("cate_name");
                                    drData.dr_contact = job.getString("dr_contact");
                                    drData.doller_rate = job.getString("doller_rate");
                                    drData.experience = job.getString("experience");


                                    drList.add(drData);



                                }
                                mAdapter = new ClientBookingHistoryAdapter(drList,ActivityBookingClient.this );
                                RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(ActivityBookingClient.this );
                                recycler_history.setLayoutManager(mLayoutManger);
                                recycler_history.setLayoutManager(new LinearLayoutManager(ActivityBookingClient.this , LinearLayoutManager.VERTICAL, false));
                                recycler_history.setItemAnimator(new DefaultItemAnimator());
                                recycler_history.setAdapter(mAdapter);


                                mAdapter.notifyDataSetChanged();
                            }

//                            //check arraylist size
//                            if (drList.size() == 0) {
//                                ll_view_dr.setVisibility(View.GONE);
//                                /*swipeRefreshLayout.setVisibility(View.GONE);
//                                l_no_record.setVisibility(View.VISIBLE);*/
//                            } else {
//                                ll_view_dr.setVisibility(View.VISIBLE);
//                                /*swipeRefreshLayout.setVisibility(View.VISIBLE);
//                                l_no_record.setVisibility(View.GONE);*/
//                            }



                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("exception = ", "" + e);
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        Utils.dismissDialog();
                        Log.e("error_dr_list", "" + error.toString());
                    }
                });
    }
}

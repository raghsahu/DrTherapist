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
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.drtherapist.R;
import com.example.drtherapist.client.activity.ActivityDrListClient;
import com.example.drtherapist.client.adapter.DrListAdapterClient;
import com.example.drtherapist.client.adapter.FavoritesAdapter;
import com.example.drtherapist.client.model.DrListDataClient;
import com.example.drtherapist.client.model.FavoritesTherapist;
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

import okhttp3.HttpUrl;

/**
 * Created by Ravindra Birla on 29/04/2019.
 */
public class ActivityFavoritesClient extends AppCompatActivity {

    ImageView iv_back;
    String url, userId;
    Session session;
    private RecyclerView recycler_view_favorite;
    private List<FavoritesTherapist> favoritesTherapists;
    private FavoritesAdapter favoritesAdapter;
    private LinearLayout ll_no_record;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites_client);
        session = new Session(this);
        userId = session.getUser().id;
        Log.e("UserFavId", "" + userId);

        iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        initView();
    }

    private void initView() {
        favoritesTherapists = new ArrayList<>();
        recycler_view_favorite = findViewById(R.id.recycler_view_favorite);
        ll_no_record = findViewById(R.id.ll_no_record);

        if (NetworkUtil.isNetworkConnected(this)) {
            try {
                url = API.BASE_URL + "All_Favrt_Dr_List";
                favoritesApi(url);
                Log.e("fav url = ", url);
            } catch (NullPointerException e) {
                ToastClass.showToast(this, getString(R.string.too_slow));
            }
        } else {
            ToastClass.showToast(this, getString(R.string.no_internet_access));
        }

    }

    private void favoritesApi(String url) {
        Utils.showDialog(this, "Loading Please Wait...");
        AndroidNetworking.post(url)
                .addBodyParameter("user_id", userId)
                .setTag("userLogin")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Utils.dismissDialog();
                        try {
                            Log.e("fav res", "" + jsonObject);
                            String message = jsonObject.getString("msg");
                            String result = jsonObject.getString("result");

                            if (result.equalsIgnoreCase("true")) {
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject job = jsonArray.getJSONObject(i);
                                    FavoritesTherapist favoritesTherapist = new FavoritesTherapist();
                                    favoritesTherapist.id = job.getString("id");
                                    favoritesTherapist.dr_id = job.getString("dr_id");
                                    favoritesTherapist.dr_fname = job.getString("dr_fname");
                                    favoritesTherapist.dr_contact = job.getString("dr_contact");
                                    favoritesTherapist.dr_email = job.getString("dr_email");
                                    favoritesTherapist.cat_id = job.getString("cat_id");
                                    //favoritesTherapist.location = job.getString("location");
                                    favoritesTherapist.lat = job.getString("lat");
                                    favoritesTherapist.lng = job.getString("lng");
                                    favoritesTherapist.dr_address = job.getString("dr_address");
                                    favoritesTherapist.dr_image = job.getString("dr_image");
                                    favoritesTherapist.dr_availability = job.getString("dr_availability");
                                    favoritesTherapist.timing = job.getString("timing");
                                    favoritesTherapist.fee = job.getString("fee");
                                    favoritesTherapist.specialization = job.getString("specialization");
                                    favoritesTherapist.experience = job.getString("experience");
                                    favoritesTherapist.review = job.getString("review");
                                    favoritesTherapist.resume = job.getString("resume");
                                    favoritesTherapist.age = job.getString("age");
//                                    favoritesTherapist.created_date = job.getString("created_date");
//                                    favoritesTherapist.updated_date = job.getString("updated_date");
                                    favoritesTherapist.dob = job.getString("dob");
                                    favoritesTherapist.zip = job.getString("zip");
                                    favoritesTherapist.rating = job.getString("rating");
                                    favoritesTherapist.doller_rate = job.getString("doller_rate");
                                   // favoritesTherapist.gender = job.getString("gender");
                                    favoritesTherapist.status = job.getString("status");
                                    favoritesTherapist.dr_unique_key = job.getString("dr_unique_key");
                                    favoritesTherapists.add(favoritesTherapist);
                                }
                                favoritesAdapter = new FavoritesAdapter(favoritesTherapists, ActivityFavoritesClient.this);
                                RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(ActivityFavoritesClient.this);
                                recycler_view_favorite.setLayoutManager(mLayoutManger);
                                recycler_view_favorite.setLayoutManager(new LinearLayoutManager(ActivityFavoritesClient.this, LinearLayoutManager.VERTICAL, false));
                                recycler_view_favorite.setItemAnimator(new DefaultItemAnimator());
                                recycler_view_favorite.setAdapter(favoritesAdapter);

                                favoritesAdapter.notifyDataSetChanged();
                            }

                            //check arraylist size
                            else {
                                ll_no_record.setVisibility(View.VISIBLE);
                                recycler_view_favorite.setVisibility(View.GONE);
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


//    private void favoritesApi(String url) {
//        Utils.showDialog(this, "Loading Please Wait...");
//        AndroidNetworking.post(url)
//                //.addQueryParameter("user_id", userId)
//                .addQueryParameter("cat_id", "1")
//                .setTag("favorites list")
//                .setPriority(Priority.MEDIUM)
//                .build()
//                .getAsJSONObject(new JSONObjectRequestListener() {
//                    @Override
//                    public void onResponse(JSONObject jsonObject) {
//                        Utils.dismissDialog();
//                        Log.e("fav res", "" + jsonObject);
//                        try {
//
//                            // String message = jsonObject.getString("msg");
//                            String result = jsonObject.getString("result");
//
//                            if (result.equalsIgnoreCase("true")) {
//                                JSONArray jsonArray = jsonObject.getJSONArray("data");
//                                for (int i = 0; i < jsonArray.length(); i++) {
//                                    JSONObject job = jsonArray.getJSONObject(i);
//                                    FavoritesTherapist favoritesTherapist = new FavoritesTherapist();
////                                    favoritesTherapist.id = job.getString("id");
////                                    favoritesTherapist.dr_id = job.getString("dr_id");
//                                   // favoritesTherapist.dr_fname = job.getString("dr_fname");
////                                    favoritesTherapist.dr_contact = job.getString("dr_contact");
////                                    favoritesTherapist.dr_email = job.getString("dr_email");
////                                    favoritesTherapist.cat_id = job.getString("cat_id");
////                                    favoritesTherapist.location = job.getString("location");
////                                    favoritesTherapist.lat = job.getString("lat");
////                                    favoritesTherapist.lng = job.getString("lng");
////                                    favoritesTherapist.dr_address = job.getString("dr_address");
////                                    favoritesTherapist.dr_image = job.getString("dr_image");
////                                    favoritesTherapist.dr_availability = job.getString("dr_availability");
////                                    favoritesTherapist.timing = job.getString("timing");
////                                    favoritesTherapist.fee = job.getString("fee");
////                                    favoritesTherapist.specialization = job.getString("specialization");
////                                    favoritesTherapist.experience = job.getString("experience");
////                                    favoritesTherapist.review = job.getString("review");
////                                    favoritesTherapist.resume = job.getString("resume");
////                                    favoritesTherapist.age = job.getString("age");
////                                    favoritesTherapist.created_date = job.getString("created_date");
////                                    favoritesTherapist.updated_date = job.getString("updated_date");
////                                    favoritesTherapist.dob = job.getString("dob");
////                                    favoritesTherapist.zip = job.getString("zip");
////                                    favoritesTherapist.rating = job.getString("rating");
////                                    favoritesTherapist.doller_rate = job.getString("doller_rate");
////                                    favoritesTherapist.gender = job.getString("gender");
////                                    favoritesTherapist.status = job.getString("status");
//                                    favoritesTherapists.add(favoritesTherapist);
//                                }
//                                favoritesAdapter = new FavoritesAdapter(favoritesTherapists, ActivityFavoritesClient.this);
//                                RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(ActivityFavoritesClient.this);
//                                recycler_view_favorite.setLayoutManager(mLayoutManger);
//                                recycler_view_favorite.setLayoutManager(new LinearLayoutManager(ActivityFavoritesClient.this, LinearLayoutManager.VERTICAL, false));
//                                recycler_view_favorite.setItemAnimator(new DefaultItemAnimator());
//                                recycler_view_favorite.setAdapter(favoritesAdapter);
//
//                                favoritesAdapter.notifyDataSetChanged();
//                            }
//                            //check arraylist size
//                            else {
//                                ll_no_record.setVisibility(View.VISIBLE);
//                                recycler_view_favorite.setVisibility(View.GONE);
//                            }
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                    @Override
//                    public void onError(ANError error) {
//
//                        Log.e("error = ", "" + error);
//                    }
//                });
//
//
//    }


}

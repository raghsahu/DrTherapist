package com.example.drtherapist.common.BasicActivities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.drtherapist.R;
import com.example.drtherapist.common.Adapter.AllBlogAdapter;
import com.example.drtherapist.common.Interface.Config;
import com.example.drtherapist.common.Model.Show_Blog_Model;
import com.example.drtherapist.common.Utils.ToastClass;
import com.example.drtherapist.common.Utils.Utils;
import com.example.drtherapist.therapist.activity.ActivityEditProfileTherapist;
import com.example.drtherapist.therapist.adapter.All_Jobs_Adapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ActivityBlog extends AppCompatActivity {
    RecyclerView recycler_blog;
    ImageView iv_back;
    AllBlogAdapter all_blog_adapter;
    private ArrayList<Show_Blog_Model> show_blog_list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog);

        iv_back = findViewById(R.id.iv_back);
        recycler_blog = findViewById(R.id.recycler_blog);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        getAllBlog(Config.Base_Url+Config.Blog);


    }

    private void getAllBlog(String url) {

        Utils.showDialog(ActivityBlog.this, "Loading Please Wait...");
        AndroidNetworking.get(url)
                //.addBodyParameter("dr_id", userId)
                .setTag("blog")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        //Utils.hideProgress(mdialog);
                        Utils.dismissDialog();
                        Log.e("BlogResponce= ", "" + jsonObject.toString());
                        try {
                            String msg = jsonObject.getString("msg");
                            String result = jsonObject.getString("result");

                            if (result.equalsIgnoreCase("true")) {
                                Utils.dismissDialog();
                                ToastClass.showToast(ActivityBlog.this, msg);

                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject blog_dataObject = jsonArray.getJSONObject(i);

                                    Show_Blog_Model showBlogModel = new Show_Blog_Model();
                                    showBlogModel.id=blog_dataObject.getString("id");
                                    showBlogModel.image=blog_dataObject.getString("image");
                                    showBlogModel.date=blog_dataObject.getString("date");
                                    showBlogModel.title=blog_dataObject.getString("title");
                                    showBlogModel.description=blog_dataObject.getString("description");

                                    show_blog_list.add(0,showBlogModel);

                                }

                            } else {
                                Utils.openAlertDialog(ActivityBlog.this, msg);
                            }

                            all_blog_adapter= new AllBlogAdapter( ActivityBlog.this,show_blog_list);
                            RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(ActivityBlog.this);
                            recycler_blog.setLayoutManager(mLayoutManger);
                            recycler_blog.setLayoutManager(new LinearLayoutManager(ActivityBlog.this, RecyclerView.VERTICAL, false));
                            recycler_blog.setItemAnimator(new DefaultItemAnimator());
                            recycler_blog.setAdapter(all_blog_adapter);




                        } catch (JSONException e) {
                            Utils.dismissDialog();
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        Utils.dismissDialog();
                    }
                });


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}

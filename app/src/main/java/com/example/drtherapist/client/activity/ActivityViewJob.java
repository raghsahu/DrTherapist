package com.example.drtherapist.client.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.drtherapist.R;
import com.example.drtherapist.client.model.ApplicantListData;
import com.example.drtherapist.client.model.DrListDataClient;
import com.example.drtherapist.client.model.DrListDataExp;
import com.example.drtherapist.client.model.FavoritesTherapist;
import com.example.drtherapist.client.model.OpenCloseJobsModel;
import com.squareup.picasso.Picasso;

public class ActivityViewJob extends AppCompatActivity {
private OpenCloseJobsModel openCloseJobsModel;
TextView tv_jobs_title,tv_start_date,tv_job_des,
        tv_location,tv_start_date2,tv_end_date,tv_start_time,tv_end_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_job);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_view_job);
        //setSupportActionBar(toolbar);
        toolbar.setTitle("View Job");
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_backspace_black_24dp);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Intent intent = getIntent();
        if (intent != null) {
            openCloseJobsModel = (OpenCloseJobsModel) intent.getSerializableExtra("openjobModel");
        }

        initView();

    }

    private void initView() {

        tv_jobs_title=findViewById(R.id.tv_jobs_title);
        tv_start_date=findViewById(R.id.tv_start_date);
        tv_job_des=findViewById(R.id.tv_job_des);
        tv_location=findViewById(R.id.tv_location);
        tv_start_date2=findViewById(R.id.tv_start_date2);
        tv_end_date=findViewById(R.id.tv_end_date);
        tv_start_time=findViewById(R.id.tv_start_time);
        tv_end_time=findViewById(R.id.tv_end_time);

        if (openCloseJobsModel != null) {
            tv_jobs_title.setText(openCloseJobsModel.title);
            tv_start_date.setText("Start "+openCloseJobsModel.start_date);
            tv_job_des.setText(openCloseJobsModel.user_job_msg);
            tv_location.setText(openCloseJobsModel.location);
            tv_start_date2.setText("Start "+openCloseJobsModel.start_date);
            tv_end_date.setText("End "+openCloseJobsModel.end_date);
            tv_start_time.setText("Start Time"+openCloseJobsModel.start_time);
            tv_end_time.setText("End Time"+openCloseJobsModel.end_time);
        }
    }
}

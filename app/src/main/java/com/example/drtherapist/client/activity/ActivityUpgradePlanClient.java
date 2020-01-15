package com.example.drtherapist.client.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.drtherapist.R;
import com.example.drtherapist.therapist.drawer.ActivityApplyJob;

public class ActivityUpgradePlanClient extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upgrade_plan);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_updrade_plan);
        //setSupportActionBar(toolbar);
        toolbar.setTitle("Updrade");
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_backspace_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ActivityApplyJob.class);
                startActivity(intent);
            }
        });

    }
}

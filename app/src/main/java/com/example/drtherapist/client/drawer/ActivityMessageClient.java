package com.example.drtherapist.client.drawer;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.drtherapist.Adapters.MessageAlertAdapter;
import com.example.drtherapist.R;
import com.example.drtherapist.client.activity.MainActivity;

public class ActivityMessageClient extends AppCompatActivity {
    TabLayout messageTabLayout;
    ViewPager messageViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_client);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_message);
        //setSupportActionBar(toolbar);
        toolbar.setTitle("Message_new");
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_backspace_black_24dp);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        messageTabLayout = (TabLayout) findViewById(R.id.message_tabLayout);
        messageViewPager = (ViewPager) findViewById(R.id.message_viewPager);
        messageTabLayout.addTab(messageTabLayout.newTab().setText("Message_new"));
        messageTabLayout.addTab(messageTabLayout.newTab().setText("Alert"));
        messageTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final MessageAlertAdapter messageAlertAdapter = new MessageAlertAdapter(getSupportFragmentManager(), this, messageTabLayout.getTabCount());
        messageViewPager.setAdapter(messageAlertAdapter);
        messageViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(messageTabLayout));

        messageTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                messageViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }
}

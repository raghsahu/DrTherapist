package com.example.drtherapist.common.BasicActivities;

import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.drtherapist.R;
import com.example.drtherapist.common.Adapter.MsgConversationAdapter;
import com.example.drtherapist.common.Model.ChatHistory;
import com.example.drtherapist.common.Model.ChatUserModal;
import com.example.drtherapist.common.Model.Messages;
import com.example.drtherapist.common.Model.MsgConversationModel;
import com.example.drtherapist.common.Utils.NetworkUtil;
import com.example.drtherapist.common.Utils.ToastClass;
import com.example.drtherapist.common.Utils.Utils;
import com.example.drtherapist.common.remote.Session;
import com.example.drtherapist.db.DatabaseHelper;
import com.example.drtherapist.therapist.activity.NotificationActivity;
import com.example.drtherapist.therapist.adapter.NotificattionAdapter;
import com.example.drtherapist.therapist.model.NotificationModel;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.drtherapist.common.Utils.Const.API.ARG_HISTORY;

public class MessageConversation extends AppCompatActivity {
    ImageView iv_back;
    private DatabaseReference mRootReference;
    private RecyclerView recyclerview;
    private String url, userId;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayout l_no_record;
    Session session;
    private DatabaseHelper db;
    private List<ChatUserModal> msgConversationModels;
    private List<ChatHistory> mHistory;
    private MsgConversationAdapter mAdapter;

    private DatabaseReference mReference = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_conversation);

         db = new DatabaseHelper(this);
         FirebaseDatabase.getInstance().getReference().child("message");


        iv_back = findViewById(R.id.iv_back);
        session = new Session(this);
        userId = session.getUser().id;

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        initView();


        // loadMessages();
    }
    private void initView() {
        mHistory = new ArrayList<>();
        recyclerview = findViewById(R.id.recycler_view);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        l_no_record = findViewById(R.id.no_record);
        Log.e("Msg_size", "" + mHistory.size());
//        msgConversationModels = db.getAllUser();
//            Log.e("all_user"," "+db.getAllUser());

        mAdapter = new MsgConversationAdapter(mHistory, MessageConversation.this);
        RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(MessageConversation.this);
        recyclerview.setLayoutManager(mLayoutManger);
        recyclerview.setLayoutManager(new LinearLayoutManager(MessageConversation.this, LinearLayoutManager.VERTICAL, false));
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        recyclerview.setAdapter(mAdapter);
       // mAdapter.notifyDataSetChanged();


        mReference.child(ARG_HISTORY).child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mHistory.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    mHistory.add(ds.getValue(ChatHistory.class));

                    Log.e("history_list",""+mHistory.size());
                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





    }


}

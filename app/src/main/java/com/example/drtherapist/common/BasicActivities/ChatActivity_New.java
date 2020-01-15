package com.example.drtherapist.common.BasicActivities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.drtherapist.R;
import com.example.drtherapist.common.Adapter.MessageAdapter1;
import com.example.drtherapist.common.Model.ChatHistory;
import com.example.drtherapist.common.Model.Message_new;
import com.example.drtherapist.common.Model.Messages;
import com.example.drtherapist.common.remote.Session;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.drtherapist.common.Utils.Const.API.ARG_CHATROOM;
import static com.example.drtherapist.common.Utils.Const.API.ARG_EXTRA;
import static com.example.drtherapist.common.Utils.Const.API.ARG_FROM;
import static com.example.drtherapist.common.Utils.Const.API.ARG_HISTORY;

public class ChatActivity_New extends AppCompatActivity implements View.OnClickListener {

    private String mCurrentUserId = "";
    private ImageButton mChatSendButton, mChatAddButton;
    private EditText mMessageView;
    private String mChatUser = "";
    private String mChatRoom;

    ImageView iv_back;
    private LinearLayoutManager mLinearLayoutManager;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mMessagesList;
   // private final List<Messages> messagesList = new ArrayList<>();
    private final List<Message_new> messagesList = new ArrayList<>();
    private MessageAdapter1 mMessageAdapter;

    private DatabaseReference mReference = FirebaseDatabase.getInstance().getReference();

    public static final String FCM_MESSAGE_URL = "https://fcm.googleapis.com/fcm/send";

    OkHttpClient mClient = new OkHttpClient();
    String fcmToken = "";
    String userName = "";
    String mImage = "";
    String register_id = "";
    private String userId = "";
    public static String paire = "";
    TextView pared_msg;
    private Session session;
    private boolean isclick = false;
    private String chat_user;
    private Boolean isInternetPresent = false;
   private ConnectionDetector cd;
    private String block_status = "";
    ValueEventListener seenListener;
   // private ChatHistory mHistory;
   DatabaseReference mDatabaseReference;
    private FirebaseAuth mAuth;

    private ChatHistory mHistory;
    private String opUserName;
    private String oImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat1);

        session = new Session(this);
        userId = session.getUser().id;
        userName = session.getUser().username;
        mImage = session.getUser().image;
        register_id = session.getUser().register_id;

        CheckInternet();
        initView();
        clickListner();

    }
    private void initView() {

        mChatSendButton = (ImageButton) findViewById(R.id.chatSendButton);
        mMessageView = (EditText) findViewById(R.id.chatMessageView);
        iv_back =  findViewById(R.id.iv_back);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("users");
       // mRootReference = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        mCurrentUserId = mAuth.getCurrentUser().getUid();
        Log.e("Chat", mCurrentUserId);
        mChatSendButton = (ImageButton) findViewById(R.id.chatSendButton);
        mMessageView = (EditText) findViewById(R.id.chatMessageView);
        mMessagesList = (RecyclerView) findViewById(R.id.recycleViewMessageList);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.message_swipe_layout);

//        mMessageAdapter = new MessageAdapter1(messagesList,this);
//        mLinearLayoutManager = new LinearLayoutManager(ChatActivity_New.this);
//        mMessagesList.setLayoutManager(mLinearLayoutManager);
//        mMessagesList.setAdapter(mMessageAdapter);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        Intent intent = getIntent();
        if (ARG_HISTORY.equals(intent.getStringExtra(ARG_FROM))) {
            mHistory = (ChatHistory) intent.getSerializableExtra(ARG_EXTRA);
            mChatRoom = mHistory.roomId;
            mChatUser = mHistory.receiverId;
            opUserName = mHistory.historyName;
            oImage = mHistory.image;
            fcmToken = mHistory.register_id;


        } else if (intent.getExtras() != null) {
            fcmToken = intent.getStringExtra("FCMTOKEN");
            mChatUser = intent.getStringExtra("FUID");
            opUserName = intent.getStringExtra("user_name");
           // oImage = intent.getStringExtra("IMAGE");

            Log.e("mchatuser", mChatUser);
            Log.e("userId", userId);
            mChatRoom = getChatNode(userId, mChatUser);
            Log.e("fcmToken", "FCM TOKEN =" + fcmToken + "FUID =" + mChatUser);

        }

        final JSONArray jsonArray = new JSONArray();
        jsonArray.put(fcmToken);

        mMessageAdapter = new MessageAdapter1(messagesList, ChatActivity_New.this);
        mLinearLayoutManager = new LinearLayoutManager(ChatActivity_New.this);
        mLinearLayoutManager.setStackFromEnd(true);
        mMessagesList.setLayoutManager(mLinearLayoutManager);
        mMessagesList.setAdapter(mMessageAdapter);

        loadMessages();

        mChatSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String message = mMessageView.getText().toString();
                if (!TextUtils.isEmpty(message)) {
                    final Map<String, Object> messageMap = new HashMap<>();
                    messageMap.put("message", message);
                    messageMap.put("senderId", userId);
                    messageMap.put("receiverId", mChatUser);
                    messageMap.put("time", ServerValue.TIMESTAMP);
                    messageMap.put("image", mImage);
                    messageMap.put("token", register_id);


                    sendChatMessage(messageMap, jsonArray);
                    //messagesList.clear();
                    mMessageAdapter = new MessageAdapter1(messagesList, ChatActivity_New.this);
                    mLinearLayoutManager = new LinearLayoutManager(ChatActivity_New.this);
                    mLinearLayoutManager.setStackFromEnd(true);
                    mMessagesList.setLayoutManager(mLinearLayoutManager);
                    mMessagesList.setAdapter(mMessageAdapter);
                    mMessageAdapter.notifyDataSetChanged();

                }
            }
        });

        
        
    }

    private void loadMessages() {

        mReference.child(ARG_CHATROOM).getRef().child(mChatRoom)
                .addChildEventListener(new ChildEventListener() {

                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        messagesList.add(dataSnapshot.getValue(Message_new.class));
                        mMessageAdapter = new MessageAdapter1(messagesList, ChatActivity_New.this);
                        mLinearLayoutManager = new LinearLayoutManager(ChatActivity_New.this);
                        mLinearLayoutManager.setStackFromEnd(true);
                        mMessagesList.setLayoutManager(mLinearLayoutManager);
                        mMessagesList.setAdapter(mMessageAdapter);
                        mMessageAdapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                /*Message_new newMessage = dataSnapshot.getValue(Message_new.class);
                if (newMessage != null) {
                    for (int i = 0; i < messagesList.size(); i++) {
                        if (messagesList.get(i).message == newMessage.message) {
                            messagesList.remove(i);
                            messagesList.add(i, newMessage);
                            break;
                        }
                    }
                }*/
                        //mMessageAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                        String key = dataSnapshot.getKey();
                        int index = messagesList.indexOf(key);

                        if (index != -1) {
                            messagesList.remove(index);
                            // messagesList.remove(index);
                            // mMessageAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    private String getChatNode(String mUserId, String oUserId) {
        int mId = Integer.parseInt(mUserId);
        int oId = Integer.parseInt(oUserId);

        String mNode;
        if (mId > oId) {
            mNode = oId + "_" + mId;
        } else {
            mNode = mId + "_" + oId;
        }
        return mNode;
    }

    private void sendChatMessage(final Map<String, Object> data, final JSONArray tokens) {
        mReference.child(ARG_CHATROOM).getRef().child(mChatRoom).push().setValue(data).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                data.put("seen", false);
                data.put("roomId", mChatRoom);

                sendToMyChatHistory(data);
                sendToOtherChatHistory(data);

                sendMessage(tokens, userName, String.valueOf(data.get("message")), "Http:\\google.com", String.valueOf(data.get("message")));
                mMessageView.setText("");
            }
        });

    }

    public void sendMessage(final JSONArray recipients, final String title, final String body, final String icon, final String message) {

        new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... params) {
                try {

                    Log.e("useridand_name", userId + "  name " + userName);
                    JSONObject root = new JSONObject();
                    JSONObject notification = new JSONObject();
                    notification.put("body", body);
                    notification.put("title", title);
                    notification.put("icon", icon);

                    JSONObject data = new JSONObject();
                    data.put("message", message);
                    data.put("fcmId", fcmToken);
                    //data.put("Udid", mChatUser);
                    data.put("Udid", "" + mCurrentUserId);
                    data.put("userId", userId);
                    data.put("userName", userName);

                    root.put("notification", notification);
                    root.put("data", data);
                    root.put("registration_ids", recipients);

                    String result = postToFCM(root.toString());
                    Log.e("Chat_Activity", "Result: " + result);
                    return result;
                } catch (Exception ex) {
                    Log.e("error_chat_noti", ex.getMessage());
                    ex.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(String result) {
                try {
                    JSONObject resultJson = new JSONObject(result);
                    Log.e("chat_noti_result", result.toString());
                    int success, failure;
                    success = resultJson.getInt("success");
                    failure = resultJson.getInt("failure");
                    // Toast.makeText(ChatActivity1.this, "Message_new Success: " + success + "Message_new Failed: " + failure, Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    Log.e("error_chat_noti_result", e.getMessage());
                    e.printStackTrace();
                    // Toast.makeText(ChatActivity1.this, "Message_new Failed, Unknown error occurred.", Toast.LENGTH_LONG).show();
                }
            }
        }.execute();
    }

    String postToFCM(String bodyString) throws IOException {
        final MediaType JSON
                = MediaType.parse("application/json; charset=utf-8");

        RequestBody body = RequestBody.create(JSON, bodyString);
        Request request = new Request.Builder()
                .url(FCM_MESSAGE_URL)
                .post(body)
                //.addHeader("Authorization", "key=" + "AAAASGMi2Jk:APA91bGHycPZElcdpr1qDB8V00Buocmyu4Wm2puA9kFyi0O3zT9ZzZin1yheegCdbxzXZwShiwRChnz7yqzI3amy2DW-YyGh2R7YY-mRjIZO40zcluI8t6gxSRAUPahd3Bruk2Ywy5lV")
                .addHeader("Authorization", "key=" + "AAAAmEjCzy4:APA91bESlRHb8YBA4Hu52MGeoA_ASLOPr8hbnaD7WFOeUiBLwXv0Y24Hh-zHyj3Q9838Xs07uP79SMFuAnEAuM_7nTPP46uUJY0ylBfkfQ2Nkzy9mPgeLrTnEf4kSgK5kABbdUKvf_94")
                .build();

        Response response = mClient.newCall(request).execute();
        return response.body().string();
    }

    @Override
    protected void onResume() {
        super.onResume();
       /* LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("custom-event-name"));*/
        messagesList.clear();
    }

    private void sendToOtherChatHistory(Map<String, Object> data) {
        data.put("historyName", userName);
        data.put("image", mImage);
        data.put("register_id", register_id);
        mReference.child(ARG_HISTORY).getRef().child(mChatUser).child(mChatRoom).setValue(data);
    }

    private void sendToMyChatHistory(Map<String, Object> data) {
        data.put("historyName", opUserName);
        data.put("image", oImage);
        data.put("register_id", fcmToken);
        mReference.child(ARG_HISTORY).getRef().child(userId).child(mChatRoom).setValue(data);
    }


    private void clickListner() {
    }



    private void CheckInternet() {
        cd = new ConnectionDetector(this);
        isInternetPresent = cd.isConnectingToInternet();
        super.onStart();
    }


    @Override
    public void onClick(View v) {

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}

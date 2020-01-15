package com.example.drtherapist.therapist.activity;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.drtherapist.R;
import com.example.drtherapist.client.model.ChatMessage;
import com.example.drtherapist.common.Utils.Constants;
import com.example.drtherapist.therapist.model.Chat;
import com.firebase.client.Firebase;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
public class ChatActivityDr extends AppCompatActivity {
    private static final int SIGN_IN_REQUEST_CODE = 111;
    private FirebaseListAdapter<ChatMessage> adapter;
    private ListView listView;
    RelativeLayout activity_main;
    String uid;
    Firebase reference1, reference2;
    private String loggedInUserName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_dr);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        final EditText input = (EditText) findViewById(R.id.input);
        listView = (ListView) findViewById(R.id.list);
        activity_main = findViewById(R.id.activity_main);

//        Firebase.setAndroidContext(this);
//        reference1=new Firebase("https://drtherapist-a5a34.firebaseio.com/message/"+input.getText().toString());
//        reference2=new Firebase("https://drtherapist-a5a34.firebaseio.com/message/"+input.getText().toString());
        Intent intent=getIntent();
        uid=intent.getStringExtra("UID");
        Log.e("currentUser",""+ FirebaseAuth.getInstance().getCurrentUser());

        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().build(), SIGN_IN_REQUEST_CODE);
        } else {
            //Snackbar.make(activity_main, "Welcome " + FirebaseAuth.getInstance().getCurrentUser().getEmail(), Snackbar.LENGTH_SHORT).show();
            showAllOldMessages();
            //lastTenMessage==
        }
        //find views by Ids
        Log.d("reference", "" + FirebaseDatabase.getInstance().getReference());

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (input.getText().toString().trim().equals("")) {
                    Toast.makeText(ChatActivityDr.this, "Please enter some texts!", Toast.LENGTH_SHORT).show();
                } else {
//                    Map<String, String> map = new HashMap<String, String>();
//                    map.put("message", input.getText().toString());
//                    //map.put("user", UserDetails.username);
//                    reference1.push().setValue(map);
//                    reference2.push().setValue(map);
//                    input.setText("");

                    FirebaseDatabase.getInstance()
                            .getReference()
                            .push()
                            .setValue(new ChatMessage(input.getText().toString(),
                                    FirebaseAuth.getInstance().getCurrentUser().getDisplayName(),
                                    FirebaseAuth.getInstance().getCurrentUser().getUid())
                            );
                    input.setText("");
//                    FirebaseDatabase.getInstance()
//                            .getReference()
//                            .push()
//                            .setValue(new ChatMessage(input.getText().toString(),
//                                    FirebaseAuth.getInstance().getCurrentUser().getDisplayName(),
//                                    FirebaseAuth.getInstance().getCurrentUser().getUid())
//                            );
//                    input.setText("");
                    showAllOldMessages();
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SIGN_IN_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "Signed in successful!", Toast.LENGTH_LONG).show();
                showAllOldMessages();
            } else {
                Toast.makeText(this, "Sign in failed, please try again later", Toast.LENGTH_LONG).show();
                // Close the app
                finish();
            }
        }
    }

    private void showAllOldMessages() {
        loggedInUserName = FirebaseAuth.getInstance().getCurrentUser().getUid();

        Log.d("Main", "user id: " + loggedInUserName);


        adapter = new FirebaseListAdapter<ChatMessage>(this, ChatMessage.class, R.layout.list_item,
                FirebaseDatabase.getInstance().getReference().child(uid)) {

            @Override
            protected void populateView(View v, ChatMessage model, int position) {
                TextView messageText = (TextView)v.findViewById(R.id.message_text);
                TextView messageUser = (TextView)v.findViewById(R.id.message_user);
                TextView messageTime = (TextView)v.findViewById(R.id.message_time);

                // Set their text
                messageText.setText(model.getMessageText());
                messageUser.setText(model.getMessageUser());
            }
        };
        listView.setAdapter(adapter);
    }

    public String getLoggedInUserName() {
        return loggedInUserName;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == R.id.menu_sign_out) {
//            AuthUI.getInstance().signOut(this)
//                    .addOnCompleteListener(new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull com.google.android.gms.tasks.Task<Void> task) {
//
//                        }
//
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//                            Toast.makeText(ChatActivity.this, "You have logged out!", Toast.LENGTH_SHORT).show();
//                            finish();
//                        }
//                    });
//        }
        return true;
    }



//    public void sendMessageToFirebaseUser(final Context context,
//                                          final Chat chat,
//                                          final String receiverFirebaseToken) {
//        final String room_type_1 = chat.senderUid + "_" + chat.receiverUid;
//        final String room_type_2 = chat.receiverUid + "_" + chat.senderUid;
//
//        final DatabaseReference databaseReference = FirebaseDatabase.getInstance()
//                .getReference();
//
//        databaseReference.child(Constants.ARG_CHAT_ROOMS)
//                .getRef()
//                .addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        if (dataSnapshot.hasChild(room_type_1)) {
//                            Log.e("Chating", "sendMessageToFirebaseUser: " + room_type_1 + " exists");
//                            databaseReference.child(Constants.ARG_CHAT_ROOMS)
//                                    .child(room_type_1)
//                                    .child(String.valueOf(chat.timestamp))
//                                    .setValue(chat);
//                        } else if (dataSnapshot.hasChild(room_type_2)) {
//                            Log.e("Chating", "sendMessageToFirebaseUser: " + room_type_2 + " exists");
//                            databaseReference.child(Constants.ARG_CHAT_ROOMS)
//                                    .child(room_type_2)
//                                    .child(String.valueOf(chat.timestamp))
//                                    .setValue(chat);
//                        } else {
//                            Log.e("Chating", "sendMessageToFirebaseUser: success");
//                            databaseReference.child(Constants.ARG_CHAT_ROOMS)
//                                    .child(room_type_1)
//                                    .child(String.valueOf(chat.timestamp))
//                                    .setValue(chat);
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//                        // Unable to send message.
//                    }
//                });
//    }
}






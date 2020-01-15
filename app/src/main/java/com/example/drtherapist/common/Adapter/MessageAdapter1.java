package com.example.drtherapist.common.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.drtherapist.R;
import com.example.drtherapist.common.Model.Message_new;
import com.example.drtherapist.common.Model.Messages;
import com.example.drtherapist.common.remote.Session;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

/**
 * Created by KSHITIZ on 3/27/2018.
 */

public class MessageAdapter1 extends RecyclerView.Adapter{
    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;
   // private List<Messages> mMessagesList;
    private List<Message_new> mMessagesList;
    private FirebaseAuth mAuth;
    DatabaseReference mDatabaseReference ;
    Context context;
    Session session;

    //-----GETTING LIST OF ALL MESSAGES FROM CHAT ACTIVITY ----
    public MessageAdapter1(List<Message_new> mMessagesList,Context context) {
        this.context=context;
        session =new Session(context);
    this.mMessagesList = mMessagesList;
    }


    //---CREATING SINGLE HOLDER AND RETURNING ITS VIEW---
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        mAuth = FirebaseAuth.getInstance();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        if (viewType==VIEW_TYPE_MESSAGE_SENT) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_sent,parent,false);

            return new SenderMessageHolder(view);
        }
       else if (viewType==VIEW_TYPE_MESSAGE_RECEIVED){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_received,parent,false);
            return new ReceivedMessageHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        Message_new messages=mMessagesList.get(i);
        switch (viewHolder.getItemViewType()){
            case VIEW_TYPE_MESSAGE_SENT:{
                ((SenderMessageHolder)viewHolder).bind(messages);
                break;
            }
            case VIEW_TYPE_MESSAGE_RECEIVED:{
                ((ReceivedMessageHolder)viewHolder).bind(messages);
                break;
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        Message_new messages=mMessagesList.get(position);
        Log.e("iddddd",FirebaseAuth.getInstance().getCurrentUser().getUid());
      //  Log.e("iddddd",messages.getFrom());
//        if(FirebaseAuth.getInstance().getCurrentUser().getUid().equals(messages.senderId)){
//            return VIEW_TYPE_MESSAGE_SENT;
//        }else {
//            return VIEW_TYPE_MESSAGE_RECEIVED;
//        }

        if (session.getUser().id.equals(mMessagesList.get(position).senderId)) {
            return VIEW_TYPE_MESSAGE_SENT;
        } else {
            return VIEW_TYPE_MESSAGE_RECEIVED;
        }

    }

    @Override
    public int getItemCount() {
        return mMessagesList.size();
    }
}

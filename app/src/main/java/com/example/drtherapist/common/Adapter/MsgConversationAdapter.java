package com.example.drtherapist.common.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.drtherapist.R;
import com.example.drtherapist.client.activity.ActivityDrListClient;
import com.example.drtherapist.common.BasicActivities.ChatActivity1;
import com.example.drtherapist.common.BasicActivities.ChatActivity_New;
import com.example.drtherapist.common.Model.ChatHistory;
import com.example.drtherapist.common.Model.ChatUserModal;
import com.example.drtherapist.common.Model.MsgConversationModel;
import com.example.drtherapist.therapist.adapter.NotificattionAdapter;
import com.example.drtherapist.therapist.model.NotificationModel;

import java.util.List;
import java.util.Random;

import static com.example.drtherapist.common.Utils.Const.API.ARG_EXTRA;
import static com.example.drtherapist.common.Utils.Const.API.ARG_FROM;
import static com.example.drtherapist.common.Utils.Const.API.ARG_HISTORY;

public class MsgConversationAdapter extends RecyclerView.Adapter<MsgConversationAdapter.ViewHolder> {
   // private List<ChatUserModal> msgConversationModels;
   private List<ChatHistory> histories;
    private ChatHistory msgConversationModel;
    private Context context;

    public MsgConversationAdapter(List<ChatHistory> msgConversationModels, Context context) {
        this.histories = msgConversationModels;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_conversation, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        if (histories.size() > 0) {
            msgConversationModel = histories.get(position);
            // holder.tv_name.setText(categoryData.name);
          //  Log.e("msg",""+msgConversationModel.getMessage());
           // Log.e("audid",msgConversationModel.getUdid());
//            Random mRandom = new Random();
//            int color = Color.argb(255, mRandom.nextInt(256), mRandom.nextInt(256), mRandom.nextInt(256));
//            ((GradientDrawable) holder.textCircle.getBackground()).setColor(color);

           // holder.textCircle.setText(msgConversationModel.circleText);
            //holder.textHead.setText(msgConversationModel.headText);
            //holder.textSub.setText(msgConversationModel.subText);
            holder.textHead.setText(msgConversationModel.historyName);
            Log.e("printMessagename",""+msgConversationModel.historyName);
           // holder.textDate.setText(msgConversationModel.dateText);

            holder.li_notify_message.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   // Intent i = new Intent(context, ChatActivity1.class);
                    //To passmodel
//                    i.putExtra("UID", msgConversationModel.getUdid());
//                    i.putExtra("fcmToken", msgConversationModel.getFcmId());
//                    i.putExtra("userId", msgConversationModel.getUserId());
//
//                    Log.e("fcmID",""+msgConversationModel.getFcmId());
//                    Log.e("UID",""+msgConversationModel.getUdid());

                   // Intent i = new Intent(context, ChatActivity1.class);
                    Intent i = new Intent(context, ChatActivity_New.class);
                    i.putExtra(ARG_FROM, ARG_HISTORY);
                    i.putExtra(ARG_EXTRA, histories.get(position));
                    context.startActivity(i);
                }
            });

        }
    }
    @Override
    public int getItemCount() {
        return histories.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView textCircle,textHead,textSub,textDes,textDate;
        LinearLayout li_notify_message;

        public ViewHolder(View parent) {
            super(parent);
            textCircle = (TextView)itemView.findViewById(R.id.circleText);
            textHead = (TextView)itemView.findViewById(R.id.headText);
            textSub = (TextView)itemView.findViewById(R.id.subText);
            textDes = (TextView)itemView.findViewById(R.id.desText);
            textDate = (TextView)itemView.findViewById(R.id.dateText);
            li_notify_message = itemView.findViewById(R.id.li_notify_message);
        }

        @Override
        public void onClick(View v) {
//            categoryData =  categaoryList.get(getAdapterPosition());
//            switch (v.getId()) {
//                case R.id.ll_item:
//                    categoryData =  categaoryList.get(getAdapterPosition());
//                    Intent i = new Intent(context, ActivityDrListClient.class);
//                    //To passmodel
//                    i.putExtra("MODEL", categoryData);
//                    context.startActivity(i);
//                    break;
//            }


            msgConversationModel =  histories.get(getAdapterPosition());
/*
            switch (v.getId()) {
                case R.id.ll_item:
                    notificationModel =  notificationModels.get(getAdapterPosition());
                    Intent i = new Intent(context, ActivityDrListClient.class);
                    //To passmodel
                    Log.e("catName",""+notificationModel.name);
                    i.putExtra("CATEGORY", notificationModel.id);
                    i.putExtra("CATE_NAME", notificationModel.name);
                    context.startActivity(i);
                    break;
            }
*/
        }

    }
}


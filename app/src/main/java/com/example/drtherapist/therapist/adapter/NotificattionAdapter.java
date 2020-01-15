package com.example.drtherapist.therapist.adapter;

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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.drtherapist.R;
import com.example.drtherapist.client.activity.ActivityDrListClient;
import com.example.drtherapist.client.adapter.CategoryAdapterClient;
import com.example.drtherapist.therapist.model.CategoryListData;
import com.example.drtherapist.therapist.model.NotificationModel;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Random;

public class NotificattionAdapter extends RecyclerView.Adapter<NotificattionAdapter.ViewHolder> {
    private List<NotificationModel> notificationModels;
    private NotificationModel notificationModel;
    private Context context;

    public NotificattionAdapter(List<NotificationModel> notificationModels, Context context) {
        this.notificationModels = notificationModels;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_notification, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificattionAdapter.ViewHolder holder, int position) {
        if (notificationModels.size() > 0) {
            notificationModel = notificationModels.get(position);
           // holder.tv_name.setText(categoryData.name);
            Random mRandom = new Random();
            int color = Color.argb(255, mRandom.nextInt(256), mRandom.nextInt(256), mRandom.nextInt(256));
            ((GradientDrawable) holder.textCircle.getBackground()).setColor(color);

            holder.textCircle.setText(notificationModel.circleText);
            holder.textHead.setText(notificationModel.headText);
            holder.textSub.setText(notificationModel.subText);
            holder.textDes.setText(notificationModel.desText);
            holder.textDate.setText(notificationModel.dateText);
        }
    }
    @Override
    public int getItemCount() {
        return 10;
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView textCircle,textHead,textSub,textDes,textDate;

        public ViewHolder(View parent) {
            super(parent);
            textCircle = (TextView)itemView.findViewById(R.id.circleText);
            textHead = (TextView)itemView.findViewById(R.id.headText);
            textSub = (TextView)itemView.findViewById(R.id.subText);
            textDes = (TextView)itemView.findViewById(R.id.desText);
            textDate = (TextView)itemView.findViewById(R.id.dateText);
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


            notificationModel =  notificationModels.get(getAdapterPosition());
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

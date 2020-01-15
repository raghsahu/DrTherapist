package com.example.drtherapist.client.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.drtherapist.R;
import com.example.drtherapist.client.activity.ActivityDrDetailClient;
import com.example.drtherapist.client.model.MessageModel;
import com.example.drtherapist.common.remote.Session;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    private List<MessageModel> messageModels;
    private MessageModel messageModel;
    private Context context;
    String url, dr_Id, userId;
    Session session;

    public MessageAdapter(List<MessageModel> messageModels, Context context) {
        this.messageModels = messageModels;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_dr_list_client, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MessageAdapter.ViewHolder holder, int position) {
        if (messageModels.size() > 0) {
            messageModel = messageModels.get(position);
//            holder.tv_name.setText(drListData.name);
//            holder.tv_experience.setText(drListData.experience + " year exp");
//            holder.ratingBar.setRating(Float.parseFloat(drListData.rating));
//            dr_Id = drListData.id;
            session = new Session(context);
            userId = session.getUser().id;
//            drListData.
//            if (drListData.status == "0") {
//                holder.iv_fav.setImageResource(R.drawable.ic_favorite);
//            }
//            else {
//                holder.iv_fav.setImageResource(R.drawable.ic_favorite_fill_24dp);
//            }
//            if (!drListData.image.equalsIgnoreCase(null) && !drListData.image.equalsIgnoreCase("")) {
//                Picasso.with(context).load(drListData.image).fit().centerCrop()
//                        .placeholder(R.drawable.doctor)
//                        .error(R.drawable.doctor)
//                        .into(holder.img_profile);
//            }
        }
    }


    @Override
    public int getItemCount() {
        return messageModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView tv_name, tv_experience, tv_price;
        public ImageView img_profile;
        public ImageView iv_fav;
        public RatingBar ratingBar;
        public LinearLayout ll_item;


        public ViewHolder(View parent) {
            super(parent);
            tv_name = parent.findViewById(R.id.tv_name);
            tv_experience = parent.findViewById(R.id.tv_experience);
            tv_price = parent.findViewById(R.id.tv_price);
            ratingBar = parent.findViewById(R.id.ratingBar_Dr_detail);
            img_profile = parent.findViewById(R.id.img_profile);
            ll_item = parent.findViewById(R.id.ll_item);
            iv_fav = parent.findViewById(R.id.iv_fav);
            ll_item.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            messageModel = messageModels.get(getAdapterPosition());
            switch (v.getId()) {
                case R.id.ll_item:
                    messageModel = messageModels.get(getAdapterPosition());
                    Intent i = new Intent(context, ActivityDrDetailClient.class);
                    //To passmodel
                    i.putExtra("MODEL", messageModel);
                    i.putExtra("DrList", "DrList");
                    context.startActivity(i);
                    break;
            }
        }
    }


}


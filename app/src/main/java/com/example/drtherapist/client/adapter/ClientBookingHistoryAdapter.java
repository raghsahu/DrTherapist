package com.example.drtherapist.client.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.drtherapist.R;
import com.example.drtherapist.client.model.AllTharapistModel;
import com.example.drtherapist.client.model.ClientSideHistoryModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ClientBookingHistoryAdapter  extends RecyclerView.Adapter<ClientBookingHistoryAdapter.ViewHolder> {

    private List<ClientSideHistoryModel> allTharapistModels;
    private ClientSideHistoryModel allTharapistModel;
    private Context context;

    public ClientBookingHistoryAdapter(List<ClientSideHistoryModel> allTharapistModels, Context context) {
        this.allTharapistModels = allTharapistModels;
        this.context = context;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.client_history_booking_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (allTharapistModels.size() > 0) {
            Log.e("size>>>", "" + allTharapistModels.size());
            allTharapistModel = allTharapistModels.get(position);
            holder.booking_list_date.setText(allTharapistModel.create_date);
            holder.booking_list_name.setText(allTharapistModel.name);
            holder.booking_member.setText(allTharapistModel.member);
            holder.booking_list_exp_txt.setText(allTharapistModel.experience + " year exp");
            //holder.ratingBar.setRating(Float.parseFloat(allTharapistModel.rating));


//            if (!allTharapistModel.image.equalsIgnoreCase(null) && !allTharapistModel.image.equalsIgnoreCase("")) {
//
//                Picasso.with(context).load(allTharapistModel.image).fit().centerCrop()
//                        .placeholder(R.drawable.doctor)
//                        .error(R.drawable.doctor)
//                        .into(holder.dr_img);
//            }

        }
    }


    @Override
    public int getItemCount() {
        return allTharapistModels.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView booking_list_date, booking_list_name,booking_list_exp_txt,booking_list_rate_txt,booking_member;
        public ImageView dr_img;
        public RatingBar ratingBar;
        public LinearLayout ll_item;


        public ViewHolder(View parent) {
            super(parent);
            booking_list_date = parent.findViewById(R.id.booking_list_date);
            booking_list_name = parent.findViewById(R.id.booking_list_name);
            booking_list_exp_txt = parent.findViewById(R.id.booking_list_exp_txt);
            booking_list_rate_txt = parent.findViewById(R.id.booking_list_rate_txt);
            booking_member = parent.findViewById(R.id.booking_member);
            dr_img = parent.findViewById(R.id.dr_img);



        }

    }

}

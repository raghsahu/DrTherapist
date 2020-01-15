package com.example.drtherapist.therapist.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.example.drtherapist.client.activity.ActivityDrDetailClient;
import com.example.drtherapist.therapist.drawer.ActivityApplyJob;
import com.example.drtherapist.therapist.model.SearchTherapist;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SearchTherapistAdapter extends RecyclerView.Adapter<SearchTherapistAdapter.ViewHolder> {

    private List<SearchTherapist> searchTherapists;
    private SearchTherapist searchTherapist;
    private Context context;

    String postDate;

    public SearchTherapistAdapter(List<SearchTherapist> searchTherapists, Context context) {
        this.searchTherapists = searchTherapists;
        this.context = context;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_all_jobs, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        if (searchTherapists.size() > 0) {

            Log.e("size>>>", "" + searchTherapists.size());
            searchTherapist = searchTherapists.get(position);
            holder.tv_title.setText(searchTherapist.title);

            holder.tv_address.setText(searchTherapist.location);
            holder.tv_distance.setText(searchTherapist.distance);
             holder.tv_days.setText(searchTherapist.days);
            holder.tv_children.setText(searchTherapist.client_count);
             holder.tv_price.setText(searchTherapist.doller_rate);

              postDate=searchTherapist.date;
            holder.tv_jobpost_day.setText(covertTimeToText(postDate));


            holder.img_profile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    searchTherapist = searchTherapists.get(position);
                    Intent i = new Intent(context, ActivityApplyJob.class);
                    //To passmodel
                    i.putExtra("jobModel", searchTherapist);
                    i.putExtra("search", "search");
                    context.startActivity(i);
                }
            });
            holder.li_joblist_dr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    searchTherapist =  searchTherapists.get(position);
                    Intent i = new Intent(context, ActivityApplyJob.class);
                    //To passmodel
                    i.putExtra("jobModel",  searchTherapist);
                    i.putExtra("search", "search");
                    context.startActivity(i);
                }
            });
//
//
//            if (!all_jobs_model.user_image.equalsIgnoreCase(null) && !all_jobs_model.user_image.equalsIgnoreCase("")) {
//
//                Picasso.with(context).load(all_jobs_model.user_image).fit().centerCrop()
//                        .placeholder(R.drawable.doctor)
//                        .error(R.drawable.doctor)
//                        .into(holder.img_profile);
//            }
        }
    }


    @Override
    public int getItemCount() {
        return searchTherapists.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView tv_title, tv_jobpost_day, tv_address, tv_distance, tv_days, tv_children, tv_price;
        public ImageView img_profile;
        public LinearLayout li_joblist_dr;


        public ViewHolder(View parent) {
            super(parent);
            tv_title = parent.findViewById(R.id.tv_title);
            tv_jobpost_day = parent.findViewById(R.id.tv_jobpost_day);
            tv_address = parent.findViewById(R.id.tv_address);
            tv_distance = parent.findViewById(R.id.tv_distance);
            tv_days = parent.findViewById(R.id.tv_days);
            tv_children = parent.findViewById(R.id.tv_children);
            tv_price = parent.findViewById(R.id.tv_price);
            img_profile = parent.findViewById(R.id.img_profile);
            li_joblist_dr = parent.findViewById(R.id.li_joblist_dr);

            //ll_item.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            searchTherapist = searchTherapists.get(getAdapterPosition());
            switch (v.getId()) {
                case R.id.ll_item:
                    searchTherapist = searchTherapists.get(getAdapterPosition());
                    Intent i = new Intent(context, ActivityDrDetailClient.class);
                    //To passmodel
                    i.putExtra("MODEL", searchTherapist);
                    i.putExtra("DrList", "DrList");
                    context.startActivity(i);
                    break;
            }
        }
    }

    public String covertTimeToText(String dataDate) {

        String convTime = null;

        String prefix = "";
        String suffix = "ago";

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss");
            Date pasTime = dateFormat.parse(dataDate);

            Date nowTime = new Date();

            long dateDiff = nowTime.getTime() - pasTime.getTime();

            long second = TimeUnit.MILLISECONDS.toSeconds(dateDiff);
            long minute = TimeUnit.MILLISECONDS.toMinutes(dateDiff);
            long hour = TimeUnit.MILLISECONDS.toHours(dateDiff);
            long day = TimeUnit.MILLISECONDS.toDays(dateDiff);

            if (second < 60) {
                convTime = second + " sec " + suffix;

            } else if (minute < 60) {
                convTime = minute + " min " + suffix;
            } else if (hour < 24) {
                convTime = hour + " hr " + suffix;
            } else if (day >= 7) {
                if (day > 30) {
                    convTime = (day / 30) + " month " + suffix;
                } else if (day > 360) {
                    convTime = (day / 360) + " yrs " + suffix;
                } else {
                    convTime = (day / 7) + " week " + suffix;
                }
            } else if (day < 7) {
                convTime = day + " days " + suffix;
            }

        } catch (ParseException e) {
            e.printStackTrace();
            Log.e("ConvTimeE", e.getMessage());
        }

        return convTime;

    }

}

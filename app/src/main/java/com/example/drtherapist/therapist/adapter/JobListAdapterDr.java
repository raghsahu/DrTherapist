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
import com.example.drtherapist.therapist.drawer.ActivityApplyJob;
import com.example.drtherapist.therapist.model.BestMatchModel;
import com.example.drtherapist.therapist.model.JobListDataDr;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Ravindra Birla on 02/05/2019.
 */
public class JobListAdapterDr extends RecyclerView.Adapter<JobListAdapterDr.ViewHolder> {
    private List<BestMatchModel> bestMatchModels;
    private BestMatchModel bestMatchModel;
    private Context context;
    String postDate;

    public JobListAdapterDr(List<BestMatchModel> bestMatchModels, Context context) {
        this.bestMatchModels = bestMatchModels;
        this.context = context;
    }

    @NonNull
    @Override
    public JobListAdapterDr.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_job_dr, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull JobListAdapterDr.ViewHolder holder, final int position) {
        if (bestMatchModels.size() > 0) {
            bestMatchModel = bestMatchModels.get(position);
            holder.tv_title.setText(bestMatchModel.title);

            holder.tv_address.setText(bestMatchModel.location);
            holder.tv_distance.setText(bestMatchModel.distance);
            holder.tv_days.setText(bestMatchModel.days);
            holder.tv_children.setText(bestMatchModel.client_count);
            holder.tv_price.setText(bestMatchModel.doller_rate);

            postDate=bestMatchModel.date;
            holder.tv_jobpost_day.setText(covertTimeToText(postDate));


            holder.img_profile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bestMatchModel = bestMatchModels.get(position);
                    Intent i = new Intent(context, ActivityApplyJob.class);
                    //To passmodel
                    i.putExtra("jobModel", bestMatchModel);
                    i.putExtra("latest", "latest");
                    context.startActivity(i);
                }
            });
            holder.li_joblist_dr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bestMatchModel =  bestMatchModels.get(position);
                    Intent i = new Intent(context, ActivityApplyJob.class);
                    //To passmodel
                    i.putExtra("jobModel",  bestMatchModel);
                    i.putExtra("latest", "latest");
                    context.startActivity(i);
                }
            });


            if (!bestMatchModel.image.equalsIgnoreCase(null) && !bestMatchModel.image.equalsIgnoreCase("")) {

                Picasso.with(context).load(bestMatchModel.image).fit().centerCrop()
                        .placeholder(R.drawable.doctor)
                        .error(R.drawable.doctor)
                        .into(holder.img_profile);
            }
        }
    }

    @Override
    public int getItemCount() {
        if (bestMatchModels.size() == 1) {
            return 1;
        } else if (bestMatchModels.size() == 2) {
            return 2;
        }
        return 3;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
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

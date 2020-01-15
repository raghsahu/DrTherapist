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
import com.example.drtherapist.therapist.model.NewModel;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class NewJobsAdapter extends RecyclerView.Adapter<NewJobsAdapter.ViewHolder> {
    private List<NewModel> newModels;
    private NewModel newModel;
    private Context context;
    String postDate;

    public NewJobsAdapter(List<NewModel> newModels, Context context) {
        this.newModels = newModels;
        this.context = context;
    }
    @NonNull
    @Override
    public NewJobsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_job_dr, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NewJobsAdapter.ViewHolder holder, final int position) {
        if (newModels.size() > 0) {
            newModel = newModels.get(position);
            holder.tv_title.setText(newModel.title);
            holder.tv_address.setText(newModel.location);
            holder.tv_distance.setText(newModel.distance);
            holder.tv_days.setText(newModel.days);
            holder.tv_children.setText(newModel.client_count);
            holder.tv_price.setText(newModel.doller_rate);

            postDate = newModel.date;
            holder.tv_jobpost_day.setText(covertTimeToText(postDate));




            holder.img_profile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    newModel = newModels.get(position);
                    Intent i = new Intent(context, ActivityApplyJob.class);
                    //To passmodel
                    i.putExtra("jobModel", newModel);
                    i.putExtra("new", "new");
                    context.startActivity(i);
                }
            });
            holder.li_joblist_dr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    newModel = newModels.get(position);
                    Intent i = new Intent(context, ActivityApplyJob.class);
                    //To passmodel
                    i.putExtra("jobModel", newModel);
                    i.putExtra("new", "new");
                    context.startActivity(i);
                }
            });
            if (!newModel.image.equalsIgnoreCase(null) && !newModel.image.equalsIgnoreCase("")) {

                Picasso.with(context).load(newModel.image)
                        .placeholder(R.drawable.doctor)
                        .error(R.drawable.doctor)
                        .into(holder.img_profile);
            }
        }
    }

    @Override
    public int getItemCount() {
        return newModels.size();
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


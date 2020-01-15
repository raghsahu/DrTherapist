package com.example.drtherapist.client.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.drtherapist.R;
import com.example.drtherapist.client.activity.ActivityViewApplicant;
import com.example.drtherapist.client.activity.ActivityViewJob;
import com.example.drtherapist.client.model.OpenCloseJobsModel;

import java.util.List;

public class CloseJobsAdapter extends RecyclerView.Adapter<CloseJobsAdapter.ViewHolder>  {

    private List<OpenCloseJobsModel> openCloseJobsModels;
    private OpenCloseJobsModel openCloseJobsModel;
    private Context context;

    public CloseJobsAdapter(List<OpenCloseJobsModel> openCloseJobsModels, Context context) {
        this.openCloseJobsModels = openCloseJobsModels;
        this.context = context;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CloseJobsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_close_jobs, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        if (openCloseJobsModels.size() > 0) {
            Log.e("size>>>", "" + openCloseJobsModels.size());
            openCloseJobsModel = openCloseJobsModels.get(position);

            holder.tv_title_close.setText(openCloseJobsModel.title + " " + openCloseJobsModel.full_part_tym + " in " + openCloseJobsModel.location);
            holder.tv_startDate2.setText("Start " + openCloseJobsModel.start_date);
            holder.tv_closeDate.setText("Closed " + openCloseJobsModel.end_date);

            holder.btn_ViewApplicants_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openCloseJobsModel = openCloseJobsModels.get(position);
                    String jobId = openCloseJobsModel.job_id;
                    Log.e("jobId_cls", "" + jobId);
                    Intent i = new Intent(context, ActivityViewApplicant.class);
                    //To passmodel
                    i.putExtra("JOBID", jobId);
                    context.startActivity(i);
                }
            });
            holder.li_close_jobs_List.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openCloseJobsModel = openCloseJobsModels.get(position);
                    String jobId = openCloseJobsModel.job_id;
                    Log.e("jobId_cls", "" + jobId);
                    Intent i = new Intent(context, ActivityViewJob.class);
                    //To passmodel
                    i.putExtra("JOBID", jobId);
                    context.startActivity(i);
                }
            });




//            if (!allTharapistModel.image.equalsIgnoreCase(null) && !allTharapistModel.image.equalsIgnoreCase("")) {
//
//                Picasso.with(context).load(allTharapistModel.image).fit().centerCrop()
//                        .placeholder(R.drawable.doctor)
//                        .error(R.drawable.doctor)
//                        .into(holder.iv_cat_openjobs);
//            }

        }

    }

    @Override
    public int getItemCount() {
        return openCloseJobsModels.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView tv_title_close, tv_startDate2,tv_closeDate;
        public ImageView iv_cat_openjobs;
        public Button btn_ViewApplicants_close;
//        public RatingBar ratingBar;
        public LinearLayout li_close_jobs_List;


        public ViewHolder(View parent) {
            super(parent);
            tv_title_close = parent.findViewById(R.id.tv_title_close);
            tv_startDate2 = parent.findViewById(R.id.tv_startDate2);
            tv_closeDate = parent.findViewById(R.id.tv_closeDate);
            iv_cat_openjobs = parent.findViewById(R.id.iv_cat_openjobs);
            li_close_jobs_List = parent.findViewById(R.id.li_close_jobs_List);
            btn_ViewApplicants_close = parent.findViewById(R.id.btn_ViewApplicants_close);


        }

        @Override
        public void onClick(View v) {
            openCloseJobsModel = openCloseJobsModels.get(getAdapterPosition());
            switch (v.getId()) {
                case R.id.ll_item:
//                    allTharapistModel = allTharapistModels.get(getAdapterPosition());
//                    Intent i = new Intent(context, ActivityDrDetailClient.class);
//                    //To passmodel
//                    i.putExtra("MODEL", allTharapistModel);
//                    i.putExtra("DrList", "DrList");
//                    context.startActivity(i);
                    break;
            }
        }
    }

}

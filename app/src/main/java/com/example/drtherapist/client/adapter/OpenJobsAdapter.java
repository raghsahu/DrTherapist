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

public class OpenJobsAdapter extends RecyclerView.Adapter<OpenJobsAdapter.ViewHolder> {

    private List<OpenCloseJobsModel> openCloseJobsModels;
    private OpenCloseJobsModel openCloseJobsModel;
    private Context context;

    public OpenJobsAdapter(List<OpenCloseJobsModel> openCloseJobsModels, Context context) {
        this.openCloseJobsModels = openCloseJobsModels;
        this.context = context;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OpenJobsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_open_jobs, parent, false);
        return new ViewHolder(itemView);
    }

    @NonNull
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        if (openCloseJobsModels.size() > 0) {
            Log.e("size>>>", "" + openCloseJobsModels.size());
            openCloseJobsModel = openCloseJobsModels.get(position);

            holder.tv_open_job_title.setText(openCloseJobsModel.title +  " in " + openCloseJobsModel.location);
            holder.tv_startDate.setText("Start " + openCloseJobsModel.start_date);

            holder.btn_ViewApplicants_myjobs.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    openCloseJobsModel = openCloseJobsModels.get(position);
                    String jobId = openCloseJobsModel.job_id;
                    Log.e("jobId_Opn", "" + jobId);
                    Intent i = new Intent(context, ActivityViewApplicant.class);
                    //To passmodel
                    i.putExtra("JOBID", jobId);
                    context.startActivity(i);
                }
            });
            holder.linear_job_detail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openCloseJobsModel = openCloseJobsModels.get(position);
                    String jobId = openCloseJobsModel.job_id;
                    Log.e("jobId_Opn", "" + jobId);
                    Intent i = new Intent(context, ActivityViewJob.class);
                    //To passmodel
                    i.putExtra("JOBID", jobId);
                    i.putExtra("openjobModel", openCloseJobsModel);
                    context.startActivity(i);
                }
            });



//            holder.tv_name.setText(allTharapistModel.name);
//            holder.tv_experience.setText(allTharapistModel.experience + " year exp");
//            holder.ratingBar.setRating(Float.parseFloat(allTharapistModel.rating));


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
        public TextView tv_open_job_title, tv_startDate;
        public ImageView iv_cat_openjobs;
        public Button btn_ViewApplicants_myjobs;
//        public RatingBar ratingBar;
        public LinearLayout linear_job_detail;


        public ViewHolder(View parent) {
            super(parent);
            tv_open_job_title = parent.findViewById(R.id.tv_open_job_title);
            tv_startDate = parent.findViewById(R.id.tv_startDate);
            iv_cat_openjobs = parent.findViewById(R.id.iv_cat_openjobs);
            linear_job_detail = parent.findViewById(R.id.linear_job_detail);
            btn_ViewApplicants_myjobs = parent.findViewById(R.id.btn_ViewApplicants_myjobs);


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

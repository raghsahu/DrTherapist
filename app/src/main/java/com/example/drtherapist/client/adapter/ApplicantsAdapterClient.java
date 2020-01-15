package com.example.drtherapist.client.adapter;

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
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.drtherapist.R;
import com.example.drtherapist.client.activity.ActivityDrDetailClient;
import com.example.drtherapist.client.activity.ActivityDrListClient;
import com.example.drtherapist.client.model.ApplicantListData;
import com.example.drtherapist.therapist.model.CategoryListData;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Mahesh Dhakad on 15/05/2019.
 */
public class ApplicantsAdapterClient extends RecyclerView.Adapter<ApplicantsAdapterClient.ViewHolder> {
    private List<ApplicantListData> applicantListData;
    private ApplicantListData applicantData;
    private Context context;


    public ApplicantsAdapterClient(List<ApplicantListData> applicantList, Context context) {
        this.applicantListData = applicantList;
        this.context = context;
    }

    @NonNull
    @Override
    public ApplicantsAdapterClient.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_applicants, parent, false);
        return new ApplicantsAdapterClient.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        if (applicantListData.size() > 0) {
            applicantData = applicantListData.get(position);
            viewHolder.tv_age_applicant.setText(applicantData.age);
            viewHolder.tv_dr_name_applicants.setText(applicantData.dr_fname);
            viewHolder.tv_address_applicants.setText(applicantData.dr_address);
            viewHolder.tv_exp_application.setText(applicantData.experience);
            viewHolder.tv_rate_applicants.setText(applicantData.doller_rate);
            viewHolder.ratingbar_applicants.setRating(Float.parseFloat(applicantData.rating));
            if (!applicantData.dr_image.equalsIgnoreCase("")) {

                Picasso.with(context).load(applicantData.dr_image)
                        .placeholder(R.drawable.doctor)
                        .into(viewHolder.img_view_applicants);
            }
        }

    }


    @Override
    public int getItemCount() {
        return applicantListData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView tv_age_applicant, tv_dr_name_applicants,
                tv_address_applicants, tv_dis_applicants,
                tv_rate_applicants, tv_jobtype_applicants, tv_exp_application;
        //        public ImageView img_profile;
        CircleImageView img_view_applicants;
        RatingBar ratingbar_applicants;
         public LinearLayout li_applicants;


        public ViewHolder(View parent) {
            super(parent);
            tv_age_applicant = parent.findViewById(R.id.tv_age_applicant);
            img_view_applicants = parent.findViewById(R.id.img_view_applicants);
            ratingbar_applicants = parent.findViewById(R.id.ratingbar_applicants);
            tv_dr_name_applicants = parent.findViewById(R.id.tv_dr_name_applicants);
            tv_address_applicants = parent.findViewById(R.id.tv_address_applicants);
            tv_dis_applicants = parent.findViewById(R.id.tv_dis_applicants);
            tv_jobtype_applicants = parent.findViewById(R.id.tv_jobtype_applicants);
            tv_exp_application = parent.findViewById(R.id.tv_exp_application);
            tv_rate_applicants = parent.findViewById(R.id.tv_rate_applicants);
            li_applicants = parent.findViewById(R.id.li_applicants);

            li_applicants.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            applicantData =  applicantListData.get(getAdapterPosition());
            switch (v.getId()) {
                case R.id.li_applicants:
                    applicantData =  applicantListData.get(getAdapterPosition());
                    Intent i = new Intent(context, ActivityDrDetailClient.class);
                    //To passmodel
                    i.putExtra("MODEL", applicantData);
                    i.putExtra("applicantData","applicantData");
                    context.startActivity(i);
                    break;
            }
        }

    }


}


package com.example.drtherapist.common.Adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.drtherapist.R;
import com.example.drtherapist.common.BasicActivities.ActivityBlogDetails;
import com.example.drtherapist.common.BasicActivities.ActivityPaypalActivity;
import com.example.drtherapist.common.Interface.Config;
import com.example.drtherapist.common.Model.MemberPlanModel;
import com.example.drtherapist.common.Model.Show_Blog_Model;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Raghvendra Sahu on 25/12/2019.
 */
public class MemberPlanAdapter extends RecyclerView.Adapter<MemberPlanAdapter.ViewHolder> {

    private static final String TAG = "Show_Blog_Model";
    private final String Login_type;
    private ArrayList<MemberPlanModel> show_blog_models_list;
    MemberPlanModel show_blog_model;
    public Context context;
    View viewlike;
    ProgressDialog dialog;
   public String InStock;

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_month,tv_amount;
        ImageView img_blog;
        LinearLayout ll_member_plan;
        int pos;


        public ViewHolder(View view) {
            super(view);
            viewlike = view;
            img_blog=viewlike.findViewById(R.id.img_blog);
            tv_month=viewlike.findViewById(R.id.tv_month);
            tv_amount=viewlike.findViewById(R.id.tv_amount);
            ll_member_plan=viewlike.findViewById(R.id.ll_member_plan);

        }
    }

    public MemberPlanAdapter(Context mContext, ArrayList<MemberPlanModel> show_blog_modelArrayList, String login_type) {
        context = mContext;
        this.Login_type = login_type;
        show_blog_models_list = show_blog_modelArrayList;


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.plan_item_adapter, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        // final Show_Menu_Model showMenuModel = show_menu_modelArrayList.get(position);

        if (show_blog_models_list.size() > 0) {
            show_blog_model = show_blog_models_list.get(position);

            viewHolder.tv_amount.setText(" $ "+show_blog_model.getPrice());
            viewHolder.tv_month.setText(show_blog_model.getDaytime_slot_name());


        } else
        {
            Toast.makeText(context, "no record for list", Toast.LENGTH_SHORT).show();
        }


        viewHolder.ll_member_plan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, ActivityPaypalActivity.class);
                intent.putExtra("MemberAmount", show_blog_models_list.get(position).getPrice());
                intent.putExtra("MemberPlanId", show_blog_models_list.get(position).getId());
                intent.putExtra("Login_type", Login_type);
                context.startActivity(intent);
                 ((Activity)context).finish();


            }
        });

    }

    @Override
    public int getItemCount() {
        return show_blog_models_list.size();
    }

}

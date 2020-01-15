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
import com.example.drtherapist.client.model.AllTharapistModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class All_Dr_ListAdapter extends RecyclerView.Adapter<All_Dr_ListAdapter.ViewHolder> {

    private List<AllTharapistModel> allTharapistModels;
    private AllTharapistModel allTharapistModel;
    private Context context;

    public All_Dr_ListAdapter(List<AllTharapistModel> allTharapistModels, Context context) {
        this.allTharapistModels = allTharapistModels;
        this.context = context;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public All_Dr_ListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_all_dr_search, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (allTharapistModels.size() > 0) {
            Log.e("size>>>", "" + allTharapistModels.size());
            allTharapistModel = allTharapistModels.get(position);
            holder.tv_name.setText(allTharapistModel.name);
            holder.tv_experience.setText(allTharapistModel.experience + " year exp");
            holder.ratingBar.setRating(Float.parseFloat(allTharapistModel.rating));
            if (!allTharapistModel.image.equalsIgnoreCase(null) && !allTharapistModel.image.equalsIgnoreCase("")) {

                Picasso.with(context).load(allTharapistModel.image).fit().centerCrop()
                        .placeholder(R.drawable.doctor)
                        .error(R.drawable.doctor)
                        .into(holder.img_profile);
            }

        }
    }


    @Override
    public int getItemCount() {
        return allTharapistModels.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView tv_name, tv_experience, tv_price;
        public ImageView img_profile;
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

            ll_item.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            allTharapistModel = allTharapistModels.get(getAdapterPosition());
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

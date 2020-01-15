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
import com.example.drtherapist.client.model.DrListDataExp;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.drtherapist.common.remote.API.URL_DrImage;

/**
 * Created by Ravindra Birla on 02/05/2019.
 */
public class ExpDrListAdapterClient extends RecyclerView.Adapter<ExpDrListAdapterClient.ViewHolder> {
    private List<DrListDataExp> drList;
    private DrListDataExp drListData;
    private Context context;

    public ExpDrListAdapterClient(List<DrListDataExp> drList, Context context) {
        this.drList = drList;
        this.context = context;
    }

    @NonNull
    @Override
    public ExpDrListAdapterClient.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_dr_experience, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpDrListAdapterClient.ViewHolder holder, int position) {
        if (drList.size() > 0) {
            drListData = drList.get(position);
            holder.tv_name.setText(drListData.name);
            holder.tv_experience.setText(drListData.experience + " year exp");
            holder.tv_price.setText("$" + drListData.price);
            holder.ratingBar.setRating(Float.parseFloat(drListData.rating));


            if (!drListData.image.equalsIgnoreCase(null) && !drListData.image.equalsIgnoreCase("")) {

                Picasso.with(context).load(URL_DrImage+drListData.image)
                        .fit().centerCrop()
                        .placeholder(R.drawable.doctor)
                        .error(R.drawable.doctor)
                        .into(holder.img_profile);
            }



        }
    }

    @Override
    public int getItemCount() {
        return drList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {
        public TextView tv_name, tv_experience, tv_price;
        public RoundedImageView img_profile;
        LinearLayout li_exp_dr_list;
        public RatingBar ratingBar;


        public ViewHolder(View parent) {
            super(parent);
            tv_name = parent.findViewById(R.id.tv_name);
            tv_experience = parent.findViewById(R.id.tv_experience);
            tv_price = parent.findViewById(R.id.tv_price);
            ratingBar = parent.findViewById(R.id.ratingBar_Dr_detail);
            img_profile = parent.findViewById(R.id.img_profile);
            li_exp_dr_list = parent.findViewById(R.id.li_exp_dr_list);

            li_exp_dr_list.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            drListData =  drList.get(getAdapterPosition());
            switch (v.getId()) {
                case R.id.li_exp_dr_list:
                    drListData =  drList.get(getAdapterPosition());
                    Intent i = new Intent(context, ActivityDrDetailClient.class);
                    //To passmodel
                    Log.e("drListdata",""+drListData);
                    i.putExtra("MODEL", drListData);
                    i.putExtra("exp","EXP");
                    context.startActivity(i);
                    break;
            }
        }
    }

}

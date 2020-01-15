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
import com.example.drtherapist.client.model.FavoritesTherapist;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.ViewHolder> {

    private List<FavoritesTherapist> favoritesTherapists;
    private FavoritesTherapist favoritesTherapist;
    private Context context;

    public FavoritesAdapter(List<FavoritesTherapist> favoritesTherapists, Context context) {
        this.favoritesTherapists = favoritesTherapists;
        this.context = context;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FavoritesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_favorites, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (favoritesTherapists.size() > 0) {
            Log.e("favsize", "" + favoritesTherapists.size());
            favoritesTherapist = favoritesTherapists.get(position);

            holder.tv_name.setText(favoritesTherapist.dr_fname);
            holder.favorites_list_age_txt.setText(favoritesTherapist.age);
            holder.favorites_list_address_txt.setText(favoritesTherapist.dr_address);
            holder.tv_price.setText(favoritesTherapist.doller_rate);
            holder.tv_experience.setText(favoritesTherapist.experience + " year exp");
            holder.favorites_list_dr_ratting_txt.setText(favoritesTherapist.rating + " Rating");
            holder.ratingBar.setRating(Float.parseFloat(favoritesTherapist.rating));

            if (!favoritesTherapist.dr_image.equalsIgnoreCase(null) && !favoritesTherapist.dr_image.equalsIgnoreCase("")) {

                Picasso.with(context).load(favoritesTherapist.dr_image).fit().centerCrop()
                        .placeholder(R.drawable.doctor)
                        .error(R.drawable.doctor)
                        .into(holder.thumbnail_favorites);
            }
        }
    }
    @Override
    public int getItemCount() {
        return favoritesTherapists.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView tv_name, tv_experience,favorites_list_address_txt, tv_price,favorites_list_age_txt,favorites_list_dr_ratting_txt;
        public ImageView thumbnail_favorites;
        public RatingBar ratingBar;
        public LinearLayout ll_item,favorites_second_lay;


        public ViewHolder(View parent) {
            super(parent);
            tv_name = parent.findViewById(R.id.favorites_list_name);
            tv_experience = parent.findViewById(R.id.favorites_list_exp_txt);
            favorites_list_age_txt = parent.findViewById(R.id.favorites_list_age_txt);
            tv_price = parent.findViewById(R.id.favorites_list_rate_txt);
            favorites_list_address_txt = parent.findViewById(R.id.favorites_list_address_txt);
            ratingBar = parent.findViewById(R.id.favorites_list_ratingBar);
            thumbnail_favorites = parent.findViewById(R.id.thumbnail_favorites);
            favorites_second_lay = parent.findViewById(R.id.favorites_second_lay);
            favorites_list_dr_ratting_txt = parent.findViewById(R.id.favorites_list_dr_ratting_txt);
            favorites_second_lay.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            favoritesTherapist = favoritesTherapists.get(getAdapterPosition());
            switch (v.getId()) {
                case R.id.favorites_second_lay:
                    favoritesTherapist = favoritesTherapists.get(getAdapterPosition());
                    Intent i = new Intent(context, ActivityDrDetailClient.class);
                    //To passmodel
                    i.putExtra("MODEL", favoritesTherapist);
                    i.putExtra("fav", "fav");
                    context.startActivity(i);
                    break;
            }
        }
    }

}

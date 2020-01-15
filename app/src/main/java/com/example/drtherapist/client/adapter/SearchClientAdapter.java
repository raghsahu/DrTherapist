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
import com.example.drtherapist.client.model.SearchClient;
import com.example.drtherapist.common.Interface.Config;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SearchClientAdapter extends RecyclerView.Adapter<SearchClientAdapter.ViewHolder> {

    private List<SearchClient> searchClients;
    private SearchClient searchClient;
    private Context context;

    public SearchClientAdapter(List<SearchClient> searchClients, Context context) {
        this.searchClients = searchClients;
        this.context = context;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SearchClientAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_all_dr_search, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (searchClients.size() > 0) {
            Log.e("size>>>", "" + searchClients.size());
            searchClient = searchClients.get(position);


            holder.tv_price.setText("fee "+searchClients.get(position).fee);
            holder.tv_name.setText(searchClients.get(position).dr_fname);
            holder.tv_experience.setText(searchClients.get(position).experience + " year exp");
//            holder.tv_experience.setText(searchClients.experience + " year exp");
            //holder.ratingBar.setRating(Float.parseFloat(searchClient.rating));


            if (!searchClients.get(position).dr_image.equalsIgnoreCase(null) && !searchClient.dr_image.equalsIgnoreCase("")) {

                Picasso.with(context).load(Config.Image_Url+searchClients.get(position).dr_image).fit().centerCrop()
                        .placeholder(R.drawable.doctor)
                        .error(R.drawable.doctor)
                        .into(holder.img_profile);
            }
            /*if (!searchClient.image.equalsIgnoreCase(null) && !searchClient.image.equalsIgnoreCase("")) {

                Picasso.with(context).load(searchClient.image).fit().centerCrop()
                        .placeholder(R.drawable.doctor)
                        .error(R.drawable.doctor)
                        .into(holder.img_profile);
            }*/

        }
    }


    @Override
    public int getItemCount() {
        return searchClients.size();
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
            searchClient = searchClients.get(getAdapterPosition());
            switch (v.getId()) {
                case R.id.ll_item:
                    searchClient = searchClients.get(getAdapterPosition());
                    Intent i = new Intent(context, ActivityDrDetailClient.class);
                    i.putExtra("MODEL", searchClient);
                    i.putExtra("SearchDrList", "SearchDrList");
                    context.startActivity(i);
                    break;
            }
        }
    }
    public void filterList(ArrayList<SearchClient> filterdNames) {
        this.searchClients = filterdNames;
        notifyDataSetChanged();
    }
}

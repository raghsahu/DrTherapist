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
import android.widget.TextView;

import com.example.drtherapist.R;
import com.example.drtherapist.client.activity.ActivityDrListClient;
import com.example.drtherapist.therapist.model.CategoryListData;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Ravindra Birla on 02/05/2019.
 */
public class CategoryAdapterClient extends RecyclerView.Adapter<CategoryAdapterClient.ViewHolder> {
    private List<CategoryListData> categaoryList;
    private CategoryListData categoryData;
    private Context context;

    public CategoryAdapterClient(List<CategoryListData> categaoryList, Context context) {
        this.categaoryList = categaoryList;
        this.context = context;
    }

    @NonNull
    @Override
    public CategoryAdapterClient.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_category, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapterClient.ViewHolder holder, int position) {
        if (categaoryList.size() > 0) {
            categoryData = categaoryList.get(position);
            holder.tv_name.setText(categoryData.name);


            if (!categoryData.image.equalsIgnoreCase("")) {

                Picasso.with(context).load(categoryData.image).fit().centerCrop()
                        .placeholder(R.drawable.doctor)
                        .error(R.drawable.doctor)
                        .into(holder.img_profile);
            }
        }
    }
    @Override
    public int getItemCount() {
        return categaoryList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView tv_name;
        public ImageView img_profile;
        public LinearLayout ll_item;


        public ViewHolder(View parent) {
            super(parent);
            tv_name = parent.findViewById(R.id.tv_name);
            img_profile = parent.findViewById(R.id.img_profile);
            ll_item = parent.findViewById(R.id.ll_item);

            ll_item.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
//            categoryData =  categaoryList.get(getAdapterPosition());
//            switch (v.getId()) {
//                case R.id.ll_item:
//                    categoryData =  categaoryList.get(getAdapterPosition());
//                    Intent i = new Intent(context, ActivityDrListClient.class);
//                    //To passmodel
//                    i.putExtra("MODEL", categoryData);
//                    context.startActivity(i);
//                    break;
//            }


            categoryData =  categaoryList.get(getAdapterPosition());
            switch (v.getId()) {
                case R.id.ll_item:
                    categoryData =  categaoryList.get(getAdapterPosition());
                    Intent i = new Intent(context, ActivityDrListClient.class);
                    //To passmodel
                    Log.e("catName",""+categoryData.name);
                    i.putExtra("CATEGORY", categoryData.id);
                    i.putExtra("CATE_NAME", categoryData.name);
                    context.startActivity(i);
                    break;
            }
        }

    }
}

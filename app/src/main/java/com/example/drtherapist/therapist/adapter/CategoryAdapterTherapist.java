package com.example.drtherapist.therapist.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.drtherapist.R;
import com.example.drtherapist.therapist.model.CategoryListData;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Ravindra Birla on 02/05/2019.
 */
public class CategoryAdapterTherapist extends RecyclerView.Adapter<CategoryAdapterTherapist.ViewHolder> {
    private List<CategoryListData> categaoryList;
    private CategoryListData categoryData;
    private Context context;

    public CategoryAdapterTherapist(List<CategoryListData> categaoryList, Context context) {
        this.categaoryList = categaoryList;
        this.context = context;
    }

    @NonNull
    @Override
    public CategoryAdapterTherapist.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_category, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapterTherapist.ViewHolder holder, int position) {
        if (categaoryList.size() > 0) {
            categoryData = categaoryList.get(position);
            holder.tv_name.setText(categoryData.name);


            if (!categoryData.image.equalsIgnoreCase(null)){

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

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView  tv_name;
        public ImageView img_profile;


        public ViewHolder(View parent) {
            super(parent);
            tv_name = parent.findViewById(R.id.tv_name);
            img_profile = parent.findViewById(R.id.img_profile);

        }
    }
}

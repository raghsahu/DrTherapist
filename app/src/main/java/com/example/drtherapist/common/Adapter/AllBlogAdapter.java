package com.example.drtherapist.common.Adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.drtherapist.R;
import com.example.drtherapist.common.BasicActivities.ActivityBlog;
import com.example.drtherapist.common.BasicActivities.ActivityBlogDetails;
import com.example.drtherapist.common.Interface.Config;
import com.example.drtherapist.common.Model.Show_Blog_Model;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AllBlogAdapter extends RecyclerView.Adapter<AllBlogAdapter.ViewHolder> {

    private static final String TAG = "Show_Blog_Model";
    private ArrayList<Show_Blog_Model> show_blog_models_list;
    Show_Blog_Model show_blog_model;
    public Context context;
    View viewlike;
    ProgressDialog dialog;
    String InStock;

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txt_blog,txt_time_blog,txt_description;
        CardView cardeview;
        ImageView img_blog;
        LinearLayout ll_read_more;
        int pos;

        public ViewHolder(View view) {
            super(view);
            viewlike = view;
            img_blog=viewlike.findViewById(R.id.img_blog);
            txt_blog=viewlike.findViewById(R.id.txt_blog);
            txt_time_blog=viewlike.findViewById(R.id.txt_time_blog);
            txt_description=viewlike.findViewById(R.id.txt_description);
            ll_read_more=viewlike.findViewById(R.id.ll_read_more);

        }
    }

    public AllBlogAdapter(Context mContext, ArrayList<Show_Blog_Model> show_blog_modelArrayList) {
        context = mContext;
        show_blog_models_list = show_blog_modelArrayList;


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.show_blog_item, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        // final Show_Menu_Model showMenuModel = show_menu_modelArrayList.get(position);

        if (show_blog_models_list.size() > 0) {
            show_blog_model = show_blog_models_list.get(position);

            viewHolder.txt_time_blog.setText(show_blog_model.date);
            viewHolder.txt_blog.setText(show_blog_model.title);
            viewHolder.txt_description.setText(show_blog_model.description);

        } else
        {
            Toast.makeText(context, "no record for list", Toast.LENGTH_SHORT).show();
        }

        viewHolder.img_blog.setImageResource(R.drawable.doctor);
        Picasso.with(context)
                .load(Config.Image_Url+show_blog_model.image)
                .placeholder(R.drawable.doctor)
                .into(viewHolder.img_blog);

        viewHolder.pos = position;
        viewHolder.ll_read_more.setTag(viewHolder);


        viewHolder.ll_read_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ActivityBlogDetails.class);
                intent.putExtra("blog_model", show_blog_models_list.get(position));
                context.startActivity(intent);
               // ((Activity)context).finish();


            }
        });

    }

    @Override
    public int getItemCount() {
        return show_blog_models_list.size();
    }

}


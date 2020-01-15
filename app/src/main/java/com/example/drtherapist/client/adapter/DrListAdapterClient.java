package com.example.drtherapist.client.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.drtherapist.R;
import com.example.drtherapist.client.activity.ActivityDrDetailClient;
import com.example.drtherapist.client.activity.ActivityDrListClient;
import com.example.drtherapist.client.model.DrListDataClient;
import com.example.drtherapist.common.Utils.NetworkUtil;
import com.example.drtherapist.common.Utils.ToastClass;
import com.example.drtherapist.common.Utils.Utils;
import com.example.drtherapist.common.remote.API;
import com.example.drtherapist.common.remote.Session;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Ravindra Birla on 02/05/2019.
 */
public class DrListAdapterClient extends RecyclerView.Adapter<DrListAdapterClient.ViewHolder> {
    private List<DrListDataClient> drList;
    private DrListDataClient drListData;
    private Context context;
    String url, dr_Id, userId;
    Session session;

    public DrListAdapterClient(List<DrListDataClient> drList, Context context) {
        this.drList = drList;
        this.context = context;
    }
    @NonNull
    @Override
    public DrListAdapterClient.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_dr_list_client, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final DrListAdapterClient.ViewHolder holder, int position) {
        if (drList.size() > 0) {
            drListData = drList.get(position);
            holder.tv_name.setText(drListData.name);
            holder.tv_experience.setText(drListData.experience + " year exp");
            holder.ratingBar.setRating(Float.parseFloat(drListData.rating));
            dr_Id = drListData.id;
            session = new Session(context);
            userId = session.getUser().id;
//            drListData.
            if (drListData.status == "0") {
                holder.iv_fav.setImageResource(R.drawable.ic_favorite);
            }
            else {
                holder.iv_fav.setImageResource(R.drawable.ic_favorite_fill_24dp);
            }
            if (!drListData.image.equalsIgnoreCase(null) && !drListData.image.equalsIgnoreCase("")) {
                Picasso.with(context).load(drListData.image)
                        .placeholder(R.drawable.doctor)
                        .error(R.drawable.doctor)
                        .into(holder.img_profile);
            }
            holder.iv_fav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (NetworkUtil.isNetworkConnected(context)) {
                        try {
                            url = API.BASE_URL + "Add_To_Favrt";
                            Log.e("favorites list URL = ", url);
                            callFavoriteApi(url);

                        } catch (NullPointerException e) {
                            ToastClass.showToast(context, context.getString(R.string.too_slow));
                        }
                    } else {
                        ToastClass.showToast(context, context.getString(R.string.no_internet_access));
                    }

                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return drList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView tv_name, tv_experience, tv_price;
        public ImageView img_profile;
        public ImageView iv_fav;
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
            iv_fav = parent.findViewById(R.id.iv_fav);

            ll_item.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            drListData = drList.get(getAdapterPosition());
            switch (v.getId()) {
                case R.id.ll_item:
                    drListData = drList.get(getAdapterPosition());
                    Intent i = new Intent(context, ActivityDrDetailClient.class);
                    //To passmodel
                    i.putExtra("MODEL", drListData);
                    i.putExtra("DrList", "DrList");
                    context.startActivity(i);
                    ((Activity)context).finish();
                    break;
            }
        }
    }

    private void callFavoriteApi(String url) {
        Utils.showDialog(context, "Loading Please Wait...");
        AndroidNetworking.post(url)
                .addBodyParameter("user_id", userId)
                .addBodyParameter("dr_id", dr_Id)
                .addBodyParameter("status", "1")
                .setTag("userLogin")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Utils.dismissDialog();
                        try {
                            String message = jsonObject.getString("msg");
                            String result = jsonObject.getString("result");

                            if (result.equalsIgnoreCase("true")) {

                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError error) {

                        Log.e("error = ", "" + error);
                    }
                });


    }

}

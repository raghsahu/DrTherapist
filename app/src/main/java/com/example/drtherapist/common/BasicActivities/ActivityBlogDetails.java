package com.example.drtherapist.common.BasicActivities;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.drtherapist.R;
import com.example.drtherapist.common.Interface.Config;
import com.example.drtherapist.common.Model.Show_Blog_Model;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class ActivityBlogDetails extends AppCompatActivity {

    Show_Blog_Model show_blog_model;
    TextView txt_time_blog,tv_title,tv_descr;
    ImageView img_blog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_details);
        show_blog_model=new Show_Blog_Model();

        txt_time_blog=findViewById(R.id.txt_time_blog);
        tv_title=findViewById(R.id.tv_title);
        tv_descr=findViewById(R.id.tv_descr);
        img_blog=findViewById(R.id.img_blog);

        if (getIntent()!=null){
            show_blog_model= (Show_Blog_Model) getIntent().getSerializableExtra("blog_model");
            txt_time_blog.setText(show_blog_model.date);
            tv_title.setText(show_blog_model.title);
            tv_descr.setText(show_blog_model.description);

            Picasso.with(ActivityBlogDetails.this)
                    .load(Config.Image_Url+show_blog_model.image)
                    .placeholder(R.drawable.doctor)
                    .into(img_blog);


        }
    }
}

package com.example.drtherapist.therapist.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.drtherapist.R;
import com.example.drtherapist.common.BasicActivities.ActivitySupport;
import com.example.drtherapist.common.BasicActivities.About_us;
import com.example.drtherapist.common.BasicActivities.ActivityBlog;
import com.example.drtherapist.common.BasicActivities.MessageConversation;
import com.example.drtherapist.common.BasicActivities.PrivacyPolicy;
import com.example.drtherapist.common.BasicActivities.TermsOfUse;
import com.example.drtherapist.common.Interface.Config;
import com.example.drtherapist.common.Utils.NetworkUtil;
import com.example.drtherapist.common.Utils.ToastClass;
import com.example.drtherapist.common.Utils.Utils;
import com.example.drtherapist.common.Utils.Validation;
import com.example.drtherapist.common.remote.API;
import com.example.drtherapist.common.remote.Session;
import com.example.drtherapist.therapist.adapter.JobListAdapterDr;
import com.example.drtherapist.therapist.drawer.ActivityApplyJobsList;
import com.example.drtherapist.therapist.drawer.ActivityJobListDr;
import com.example.drtherapist.therapist.model.BestMatchModel;
import com.example.drtherapist.therapist.model.SliderImage;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;
import me.relex.circleindicator.CircleIndicator;

public class ActivityNavigationDr extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private LinearLayout nav_header, ll_job;
    private TextView tv_view_all, tv_cat_name_txt;
    private ImageView iv_search;
    private TextView tv_find_therapist;
    EditText et_search;

    private ViewPager VP_banner_slidder;
    private CircleIndicator CI_indicator;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;

    // private FloatingActionButton fab;
    private RecyclerView recycler_view;
    private String url;

    private List<BestMatchModel> bestMatchModels;
    private JobListAdapterDr mAdapter;
    private DrawerLayout drawer;
    private View headerview;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private NavigationView navigationView1;
    private Button btn_viewApplicants;
    private LinearLayout ll_baby_care, ll_senier_care, ll_lady_care, ll_gen;
    private List<SliderImage> sliderImages;
    private Session session;
    private String category_id = "";
    private String username = "";
    private String image = "";

    SharedPreferences pref;
    boolean doubleBackToExitPressedOnce = false;
    GoogleSignInClient mGoogleSignInClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_dr);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        session = new Session(this);

        try {
            if (session != null) {
                username = session.getUser().username;
                image = session.getUser().image;
                category_id = session.getUser().cat_id;
                Log.e("session = ", "username = " + username + " image=" + image + " add = " + session.getUser().address + " category_id = " + category_id);
            }
        }catch (Exception e){
        }

        initView();
        ClickListner();


        sliderImages = new ArrayList<>();

        SliderImage sliderImage = new SliderImage();
        sliderImage.setSlider_image(R.drawable.slider_img);
        sliderImages.add(sliderImage);

        SliderImage sliderImage1 = new SliderImage();
        sliderImage1.setSlider_image(R.drawable.slider_img2);
        sliderImages.add(sliderImage1);

        SliderImage sliderImage2 = new SliderImage();
        sliderImage2.setSlider_image(R.drawable.slider_img3);
        sliderImages.add(sliderImage2);

        SliderImage sliderImage3 = new SliderImage();
        sliderImage3.setSlider_image(R.drawable.slider_img);
        sliderImages.add(sliderImage3);

        CustomPagerAdapter customPagerAdapter = new CustomPagerAdapter(ActivityNavigationDr.this, sliderImages);
        VP_banner_slidder.setAdapter(customPagerAdapter);
        CI_indicator.setViewPager(VP_banner_slidder);
        NUM_PAGES = sliderImages.size();
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                VP_banner_slidder.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 3000, 3000);

        CI_indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                currentPage = position;
            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int pos) {

            }
        });


    }

    private void initView() {
        bestMatchModels = new ArrayList<>();
        // iv_search = findViewById(R.id.iv_search);
        tv_find_therapist = findViewById(R.id.tv_find_therapist);
        et_search = findViewById(R.id.et_search);
        ll_baby_care = findViewById(R.id.ll_baby_care);
        ll_senier_care = findViewById(R.id.ll_senier_care);
        ll_lady_care = findViewById(R.id.ll_lady_care);
        ll_gen = findViewById(R.id.ll_gen);
        ll_job = findViewById(R.id.ll_job);
        recycler_view = findViewById(R.id.recycler_view);
        //fab = findViewById(R.id.fab);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        navigationView1 = findViewById(R.id.nav_view);
        headerview = navigationView1.getHeaderView(0);
        nav_header = headerview.findViewById(R.id.nav_header_lay1);
        CircleImageView nav_imageView_therapist = headerview.findViewById(R.id.nav_imageView_therapist);
        TextView nav_tv_name = headerview.findViewById(R.id.nav_tv_name);


        VP_banner_slidder = findViewById(R.id.user_home_viewpager);
        CI_indicator = findViewById(R.id.user_home_ci_indicator);


        tv_view_all = findViewById(R.id.tv_view_all);
        // tv_cat_name_txt = findViewById(R.job_id.tv_cat_name_txt);
//        et_search.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(ActivityNavigationDr.this, SearchActivityTherapist.class);
//                startActivity(i);
//            }
//        });

        tv_find_therapist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = et_search.getText().toString().trim();
                Validation validation = new Validation(ActivityNavigationDr.this);
                if (!validation.isEmpty(name)) {
                    ToastClass.showToast(ActivityNavigationDr.this, getString(R.string.search));
                    et_search.requestFocus();
                }
                else {
                    String searchQuery = et_search.getText().toString();
                    Intent i = new Intent(ActivityNavigationDr.this, SearchActivityTherapist.class);
                    //To passmodel
//                Log.e("catName",""+categoryData.name);
//                i.putExtra("CATEGORY", categoryData.id);
                    i.putExtra("SEARCHQUERY", searchQuery);
                    startActivity(i);
                }
            }
        });

        try{
            if (session != null) {
                if (!session.getUser().username.equalsIgnoreCase("") && session.getUser().username != null) {
                    nav_tv_name.setText(username);
                    Log.e("username", "" + username);
                }


                if (!session.getUser().image.equalsIgnoreCase("") && session.getUser().image != null) {
                    Log.e("image", "" + image);
                    Picasso.with(this).load(Config.DrImage_Url+image)
                            .placeholder(R.drawable.doctor)
                            .error(R.drawable.doctor)
                            .into(nav_imageView_therapist);
                } else {
                    Picasso.with(this).load(R.drawable.doctor)
                            .placeholder(R.drawable.doctor)
                            .into(nav_imageView_therapist);
                }
            }

        }catch (Exception e){
            Log.e("sess_error", e.toString());
        }

        toolbar.setTitle("Therapist Home");
        setSupportActionBar(toolbar);


        /*swipeRefreshLayout = findViewById(R.job_id.swipe_refresh_layout);
        l_no_record = findViewById(R.job_id.no_record);*/

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        if (NetworkUtil.isNetworkConnected(this)) {
            try {
                url = API.BASE_URL + "jobList";
                Log.e("job list URL = ", url);
                getjobList(url);
            } catch (NullPointerException e) {
                ToastClass.showToast(this, getString(R.string.too_slow));
            }
        } else {
            ToastClass.showToast(this, getString(R.string.no_internet_access));
        }


    }

    private void ClickListner() {

        ll_baby_care.setOnClickListener(this);
        ll_senier_care.setOnClickListener(this);
        ll_lady_care.setOnClickListener(this);
        ll_gen.setOnClickListener(this);
        tv_view_all.setOnClickListener(this);
        nav_header.setOnClickListener(this);
        // fab.setOnClickListener(this);

        navigationView.setNavigationItemSelectedListener(this);

        //swipeRefreshLayout.setOnRefreshListener(this);
    }


    @Override
    public void onBackPressed() {

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);

        } else {

            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please Press Back again to exit the app", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {

                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

//        if (id == R.id.nav_medical_record_dr) {
//            /*Intent intent = new Intent(getApplicationContext(), DrProfileActivity.class);
//            startActivity(intent);*/
//        }
         if (id == R.id.nav_message_dr) {
            Intent intent = new Intent(getApplicationContext(), MessageConversation.class);
            startActivity(intent);
        } else if (id == R.id.nav_applied_jobs) {
            Intent intent = new Intent(getApplicationContext(), ActivityApplyJobsList.class);
            startActivity(intent);
        }
         else if (id == R.id.nav_notification_dr) {
            Intent intent = new Intent(getApplicationContext(), NotificationActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_jobs_dr) {
            Intent intent = new Intent(this, ActivityJobListDr.class);
            intent.putExtra("CATEGORY", category_id);
            Log.e("category_id", "" + category_id);
            startActivity(intent);
        }
        else if (id == R.id.nav_blog_dr) {
           Intent intent = new Intent(getApplicationContext(), ActivityBlog.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_support_dr) {
             Intent intent = new Intent(getApplicationContext(), ActivitySupport.class);
             startActivity(intent);


        } else if (id == R.id.nav_logout_dr) {
            showDialogConformation();
        } else if (id == R.id.nav_share_dr) {
            Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Therapist Hire");
            String app_url = "https://play.google.com/store/apps/details?job_id=example.therapist.hire";
            shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, app_url);
            startActivity(Intent.createChooser(shareIntent, "Share via"));
        } else if (id == R.id.nav_about) {
            Intent intent = new Intent(getApplicationContext(), About_us.class);
            startActivity(intent);
        } else if (id == R.id.nav_privacy) {
            Intent intent = new Intent(getApplicationContext(), PrivacyPolicy.class);
            startActivity(intent);
        } else if (id == R.id.nav_terms) {
            Intent intent = new Intent(getApplicationContext(), TermsOfUse.class);
            startActivity(intent);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showDialogConformation() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.confirm));
        builder.setMessage(getString(R.string.logout));
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                //LogoutApi();
                session.logout();


                mGoogleSignInClient.signOut();


                //for fb logout
//                LoginManager.getInstance().logOut();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.nav_header_lay1:
                intent = new Intent(this, ActivityEditProfileTherapist.class);
                startActivity(intent);
                break;
            case R.id.tv_view_all:
                intent = new Intent(this, ActivityJobListDr.class);
                intent.putExtra("CATEGORY", category_id);
                startActivity(intent);
                break;
            case R.id.ll_baby_care:
                intent = new Intent(this, ActivityJobListDr.class);
                intent.putExtra("CATEGORY", "1");
                startActivity(intent);
                break;
            case R.id.ll_senier_care:
                intent = new Intent(this, ActivityJobListDr.class);
                intent.putExtra("CATEGORY", "2");
                startActivity(intent);
                break;
            case R.id.ll_lady_care:
                intent = new Intent(this, ActivityJobListDr.class);
                intent.putExtra("CATEGORY", "3");
                startActivity(intent);
                break;
            case R.id.ll_gen:
                intent = new Intent(this, ActivityJobListDr.class);
                intent.putExtra("CATEGORY", "4");
                startActivity(intent);
                break;
        }
    }

    private void getjobList(String url) {
        Utils.showDialog(this, "Loading Please Wait...");
        AndroidNetworking.post(url)
                .addBodyParameter("cat_id", category_id)
                .setTag("joblist")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Utils.dismissDialog();
                        Log.e("navig job list rep = ", "" + jsonObject);
                        try {
                            String message = jsonObject.getString("msg");
                            String result = jsonObject.getString("result");

                            if (result.equalsIgnoreCase("true")) {
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject job = jsonArray.getJSONObject(i);
                                    BestMatchModel bestMatchModel=new BestMatchModel();
                                    bestMatchModel.job_id = job.getString("job_id");
                                    bestMatchModel.cat_id = job.getString("cat_id");
                                    bestMatchModel.user_id = job.getString("user_id");
                                    bestMatchModel.title = job.getString("title");
                                    bestMatchModel.location = job.getString("location");
                                    bestMatchModel.distance = job.getString("distance");
                                    bestMatchModel.description = job.getString("user_job_msg");
                                    bestMatchModel.job_type = job.getString("full_part_tym");
                                    bestMatchModel.days = job.getString("days");
                                    bestMatchModel.client_count = job.getString("client_count");
                                    bestMatchModel.date = job.getString("date");
                                    bestMatchModel.user_name = job.getString("user_name");
                                    bestMatchModel.user_age = job.getString("user_age");
                                    bestMatchModel.image = job.getString("user_image");
                                    bestMatchModel.start_date = job.getString("start_date");
                                    bestMatchModel.end_date = job.getString("end_date");
                                    bestMatchModel.max_price = job.getString("max_price");
                                    bestMatchModel.min_price = job.getString("min_price");
                                    //jobData.experience = job.getString("experience");
                                    bestMatchModel.doller_rate = job.getString("doller_rate");

                                    //jobData.experience = job.getString("experience");
                                    //jobData.fee = job.getString("fee");

                                    //jobData.cate_name = job.getString("cate_name");

                                    bestMatchModels.add(bestMatchModel);


                                }

                                mAdapter = new JobListAdapterDr(bestMatchModels, ActivityNavigationDr.this);
                                RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(ActivityNavigationDr.this);
                                recycler_view.setLayoutManager(mLayoutManger);
                                recycler_view.setLayoutManager(new LinearLayoutManager(ActivityNavigationDr.this, LinearLayoutManager.VERTICAL, false));
                                recycler_view.setItemAnimator(new DefaultItemAnimator());
                                recycler_view.setAdapter(mAdapter);

                                mAdapter.notifyDataSetChanged();
                            }

                            //check arraylist size
                            if (bestMatchModels.size() == 0) {
                                ll_job.setVisibility(View.GONE);

                            } else {
                                ll_job.setVisibility(View.VISIBLE);
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

    public class CustomPagerAdapter extends PagerAdapter {
        // private ArrayList<Integer> IMAGES;
        public List<SliderImage> bannerLists;
        private LayoutInflater inflater;
        private Context context;


        public CustomPagerAdapter(Context context, List<SliderImage> bannerLists) {
            this.context = context;
            this.bannerLists = bannerLists;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return bannerLists.size();
        }

        @Override
        public Object instantiateItem(ViewGroup view, final int position) {
            View imageLayout = inflater.inflate(R.layout.banner_pojo_layout, view, false);

            assert imageLayout != null;
            final ImageView imageView = imageLayout.findViewById(R.id.banner_imageview);
            final TextView get_banner_txt = imageLayout.findViewById(R.id.get_banner_txt);
            final TextView get_banner_price_txt = imageLayout.findViewById(R.id.get_banner_price_txt);


            Picasso.with(context).load(bannerLists.get(position).getSlider_image()).placeholder(R.drawable.slider_img).into(imageView);
            //get_banner_txt.setText(bannerLists.get(position).getTitle());
            //get_banner_price_txt.setText("$" + bannerLists.get(position).getPrice());
            view.addView(imageLayout, 0);


            return imageLayout;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view.equals(object);
        }

        @Override
        public void restoreState(Parcelable state, ClassLoader loader) {
        }

        @Override
        public Parcelable saveState() {
            return null;
        }


    }


}

package com.example.drtherapist.client.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.example.drtherapist.client.adapter.ExpDrListAdapterClient;
import com.example.drtherapist.client.drawer.ActivityBookingClient;
import com.example.drtherapist.client.drawer.ActivityFavoritesClient;
import com.example.drtherapist.client.model.ApplicantListData;
import com.example.drtherapist.client.model.DrListDataExp;
import com.example.drtherapist.common.BasicActivities.About_us;
import com.example.drtherapist.common.BasicActivities.ActivityBlog;
import com.example.drtherapist.common.BasicActivities.ActivitySupport;
import com.example.drtherapist.common.BasicActivities.MessageConversation;
import com.example.drtherapist.common.BasicActivities.PrivacyPolicy;
import com.example.drtherapist.common.BasicActivities.TermsOfUse;
import com.example.drtherapist.common.Utils.NetworkUtil;
import com.example.drtherapist.common.Utils.ToastClass;
import com.example.drtherapist.common.Utils.Utils;
import com.example.drtherapist.common.Utils.Validation;
import com.example.drtherapist.common.remote.API;
import com.example.drtherapist.common.remote.Session;
import com.example.drtherapist.therapist.model.SliderImage;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
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

import static com.example.drtherapist.common.remote.API.URL_ClientImage;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    private LinearLayout nav_header, ll_view_dr, ll_post_job,
            ll_baby_care,ll_gen,ll_lady_care,ll_senier_care;
    private TextView tv_view_more, tv_applicant_no, tv_view_jobs;

    private TextView tv_find_client;
    private List<SliderImage> sliderImages;

    private ViewPager VP_banner_slidder;
    private CircleIndicator CI_indicator;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;

    Session session;
    String userId, catId;
    private SwipeRefreshLayout swipeRefreshLayout;
    private FloatingActionButton fab;
    private RecyclerView recyclerview;
    private String url;

    private List<DrListDataExp> drList;
    private List<ApplicantListData> applicantList;
    private ExpDrListAdapterClient mAdapter;
    private DrawerLayout drawer;
    private View headerview;
    private NavigationView navigationView;
    private Toolbar toolbar;
    //private NavigationView navigationView1;
    private Button btn_viewApplicants, btn_find, btn_post;
    EditText et_search;

    private String username = "";
    private String image = "";

    private GoogleSignInOptions gso;
    public static GoogleApiClient mGoogleApiClient;
    GoogleSignInClient mGoogleSignInClient;

    boolean doubleBackToExitPressedOnce = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        session = new Session(this);
        userId = session.getUser().id;
        catId = session.getUser().cat_id;
        //Log.e("catId  = ", "" + catId);
        Log.e("tokenMainActivity= ", "" + FirebaseInstanceId.getInstance().getToken());

        if (session != null) {
            username = session.getUser().username;
            image = session.getUser().image;
        }
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

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

       CustomPagerAdapter customPagerAdapter=new CustomPagerAdapter(MainActivity.this,sliderImages);

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

        et_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MainActivity.this, SearchActivityClient.class);
                startActivity(i);
            }
        });

        tv_find_client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = et_search.getText().toString().trim();
                Validation validation = new Validation(MainActivity.this);
                if (!validation.isEmpty(name)) {
                    ToastClass.showToast(MainActivity.this, getString(R.string.search));
                    et_search.requestFocus();
                }
                else {
                    String searchQuery= et_search.getText().toString();
                    Intent i = new Intent(MainActivity.this, SearchActivityClient.class);
                    //To passmodel
//                Log.e("catName",""+categoryData.name);
//                i.putExtra("CATEGORY", categoryData.id);
                    i.putExtra("SEARCH_QUERY", searchQuery);
                    startActivity(i);
                }

                //Toast.makeText(getApplicationContext(), "Searching", Toast.LENGTH_LONG).show();
            }
        });

    }



    private void initView() {
        drList = new ArrayList<>();
        applicantList = new ArrayList<>();
        tv_find_client = findViewById(R.id.tv_find_client);
        btn_post = findViewById(R.id.btn_post);
        btn_find = findViewById(R.id.btn_find);
        et_search = findViewById(R.id.et_search);
        btn_viewApplicants = findViewById(R.id.btn_viewApplicants);
        ll_gen = findViewById(R.id.ll_gen);
        ll_lady_care = findViewById(R.id.ll_lady_care);
        ll_senier_care = findViewById(R.id.ll_senier_care);
        ll_baby_care = findViewById(R.id.ll_baby_care);
        ll_view_dr = findViewById(R.id.ll_view_dr);
        ll_post_job = findViewById(R.id.ll_post_job);
        recyclerview = findViewById(R.id.recycler_view);
        // fab = findViewById(R.id.fab);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        //navigationView1 = findViewById(R.job_id.nav_view);
        headerview = navigationView.getHeaderView(0);
        nav_header = headerview.findViewById(R.id.nav_header_lay);
        CircleImageView nav_imageView_client = headerview.findViewById(R.id.nav_imageView_client);
        TextView nav_tv_name = headerview.findViewById(R.id.nav_tv_name);
        toolbar.setTitle("Client Home");

        tv_view_more = findViewById(R.id.tv_view_more);
        tv_applicant_no = findViewById(R.id.tv_applicant_no);
        tv_view_jobs = findViewById(R.id.tv_view_jobs);

        VP_banner_slidder = findViewById(R.id.user_home_viewpager);
        CI_indicator = findViewById(R.id.user_home_ci_indicator);

        if (session != null) {

            if (!session.getUser().username.equalsIgnoreCase("") && session.getUser().username != null) {
                nav_tv_name.setText(username);
            }


            if (session.getUser().image != null && !session.getUser().image.isEmpty()) {

                Picasso.with(this).load(URL_ClientImage+image)
                        .placeholder(R.drawable.doctor)
                        .into(nav_imageView_client);
            } else {
                Picasso.with(this).load(R.drawable.doctor)
                        .placeholder(R.drawable.doctor)
                        .into(nav_imageView_client);
            }

        }
        setSupportActionBar(toolbar);

        /*swipeRefreshLayout = findViewById(R.job_id.swipe_refresh_layout);
        l_no_record = findViewById(R.job_id.no_record);*/

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        if (NetworkUtil.isNetworkConnected(this)) {
            try {
                url = API.BASE_URL + "DoctorListByExp";
                //Log.e("dr list URL = ", url);
                getdrList(url);
            } catch (NullPointerException e) {
                ToastClass.showToast(this, getString(R.string.too_slow));
            }
        } else {
            ToastClass.showToast(this, getString(R.string.no_internet_access));
        }



        if (NetworkUtil.isNetworkConnected(this)) {
            try {
                String url_applicant = API.BASE_URL + "applicantsList";
                //Log.e("applicant list URL = ", url);
                getApplicantList(url_applicant);
            } catch (NullPointerException e) {
                ToastClass.showToast(this, getString(R.string.too_slow));
            }
        } else {
            ToastClass.showToast(this, getString(R.string.no_internet_access));
        }

    }

    private void getApplicantList(String url) {

        Utils.showDialog(this, "Loading Please Wait...");
        AndroidNetworking.post(url)
                /*.addPathParameter("pageNumber", "0")
                .addQueryParameter("limit", "3")
                .addHeaders("token", "1234")*/
                .addBodyParameter("user_id", userId)

                .setTag("dr list")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Utils.dismissDialog();
                        Log.e("applicant list resp = ", "" + jsonObject);
                        try {
                            String message = jsonObject.getString("msg");
                            String result = jsonObject.getString("result");

                            if (result.equalsIgnoreCase("true")) {
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject applicants = jsonArray.getJSONObject(i);
                                    ApplicantListData applicantData = new ApplicantListData();
                                    applicantData.job_id = applicants.getString("job_id");
                                    applicantData.cat_id = applicants.getString("cat_id");
//                                    applicantData.dr_id = applicants.getString("dr_id");
//                                    applicantData.user_id = applicants.getString("user_id");
//                                    applicantData.title = applicants.getString("title");
//                                    //applicantData.job_request = applicants.getString("job_request");
//                                    applicantData.location = applicants.getString("location");
//                                    applicantData.distance = applicants.getString("distance");
//                                    applicantData.user_job_msg = applicants.getString("user_job_msg");
//                                    applicantData.full_part_tym = applicants.getString("full_part_tym");
//                                    //applicantData.days = applicants.getString("days");
//                                    applicantData.client_count = applicants.getString("client_count");
//                                    applicantData.doller_rate = applicants.getString("doller_rate");
//                                    applicantData.date = applicants.getString("date");
//                                    applicantData.apply_status = applicants.getString("apply_status");
//                                    applicantData.start_date = applicants.getString("start_date");
//                                    applicantData.end_date = applicants.getString("end_date");
//                                    applicantData.end_time = applicants.getString("end_time");
//                                    applicantData.start_time = applicants.getString("start_time");
//                                    applicantData.dr_fname = applicants.getString("dr_fname");
//                                    applicantData.dr_contact = applicants.getString("dr_contact");
//                                    applicantData.dr_email = applicants.getString("dr_email");
//                                    applicantData.dr_pass = applicants.getString("dr_pass");
//                                    applicantData.dr_address = applicants.getString("dr_address");
//                                    applicantData.dr_address = applicants.getString("dr_address");
//                                    applicantData.dr_image = applicants.getString("dr_image");
//                                    applicantData.dr_availability = applicants.getString("dr_availability");
//                                    applicantData.timing = applicants.getString("timing");
//                                    applicantData.fee = applicants.getString("fee");
//                                    applicantData.specialization = applicants.getString("specialization");
//                                    applicantData.lat = applicants.getString("lat");
//                                    applicantData.lng = applicants.getString("lng");
//                                    applicantData.experience = applicants.getString("experience");
//                                    applicantData.review = applicants.getString("review");
//                                    applicantData.resume = applicants.getString("resume");
//                                    applicantData.age = applicants.getString("age");
//                                    applicantData.date = applicants.getString("date");
//                                    applicantData.dob = applicants.getString("dob");
//                                    applicantData.city = applicants.getString("city");
//                                    applicantData.state = applicants.getString("state");
//                                    applicantData.zip = applicants.getString("zip");
//                                    applicantData.social_id = applicants.getString("social_id");
//                                    applicantData.oauth_provider = applicants.getString("oauth_provider");
//                                    applicantData.rating = applicants.getString("rating");
//                                    applicantData.type = applicants.getString("type");
//                                    applicantData.cate_name = applicants.getString("cate_name");
//                                    applicantData.cat_img = applicants.getString("cat_img");

                                    applicantList.add(applicantData);
                                }
                            }

                            //check arraylist size
                            // Log.e("ArrayListSize resp= ", "" + applicantList.size());

                            if (applicantList.size() == 0) {
                                ll_post_job.setVisibility(View.GONE);

                            } else {
                                ll_post_job.setVisibility(View.VISIBLE);
                                Log.e("applicants==",""+applicantList.size());
                                tv_applicant_no.setText("You have " + applicantList.size() + " applicants!");
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("exception = ", "" + e);
                        }
                    }

                    @Override
                    public void onError(ANError error) {

                        Log.e("error = ", "" + error);
                    }
                });

    }

    private void ClickListner() {

        nav_header.setOnClickListener(this);
        //  fab.setOnClickListener(this);
        tv_view_more.setOnClickListener(this);
        btn_viewApplicants.setOnClickListener(this);
        btn_find.setOnClickListener(this);
        btn_post.setOnClickListener(this);
        tv_view_jobs.setOnClickListener(this);
        ll_baby_care.setOnClickListener(this);
        ll_lady_care.setOnClickListener(this);
        ll_gen.setOnClickListener(this);
        ll_senier_care.setOnClickListener(this);
        et_search.setOnClickListener(this);


        navigationView.setNavigationItemSelectedListener(this);

    }

    private void getdrList(String url) {
        //drList.clear();
        Utils.showDialog(this, "Loading Please Wait...");
        AndroidNetworking.get(url)
                /*.addPathParameter("pageNumber", "0")
                .addQueryParameter("limit", "3")
                .addHeaders("token", "1234")*/
                .setTag("dr list")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Utils.dismissDialog();
                        Log.e("dr list resp = ", "" + jsonObject);
                        try {
                            String message = jsonObject.getString("msg");
                            String result = jsonObject.getString("result");

                            if (result.equalsIgnoreCase("true")) {
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject job = jsonArray.getJSONObject(i);
                                    DrListDataExp drData = new DrListDataExp();
                                    drData.id = job.getString("dr_id");
                                    //drData.cat_id = job.getString("cat_id");
                                    drData.name = job.getString("dr_fname");
                                    drData.email = job.getString("dr_email");
                                    drData.contact = job.getString("dr_contact");
                                    drData.address = job.getString("dr_address");
                                    drData.specialization = job.getString("specialization");
                                    drData.experience = job.getString("experience");
                                    drData.image = job.getString("dr_image");
                                    drData.rating = job.getString("rating");
                                    drData.review = job.getString("review");
                                    drData.resume = job.getString("resume");
                                    drData.age = job.getString("age");
                                    drData.dr_unique_key = job.getString("dr_unique_key");
                                    drData.price = job.getString("doller_rate_hr");

                                    drList.add(drData);



                                }
                                mAdapter = new ExpDrListAdapterClient(drList,MainActivity.this );
                                RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(MainActivity.this );
                                recyclerview.setLayoutManager(mLayoutManger);
                                recyclerview.setLayoutManager(new LinearLayoutManager(MainActivity.this , LinearLayoutManager.HORIZONTAL, false));
                                recyclerview.setItemAnimator(new DefaultItemAnimator());
                                recyclerview.setAdapter(mAdapter);


                                mAdapter.notifyDataSetChanged();
                            }

                            //check arraylist size
                            if (drList.size() == 0) {
                                ll_view_dr.setVisibility(View.GONE);
                                /*swipeRefreshLayout.setVisibility(View.GONE);
                                l_no_record.setVisibility(View.VISIBLE);*/
                            } else {
                                ll_view_dr.setVisibility(View.VISIBLE);
                                /*swipeRefreshLayout.setVisibility(View.VISIBLE);
                                l_no_record.setVisibility(View.GONE);*/
                            }



                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("exception = ", "" + e);
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        Utils.dismissDialog();
                        Log.e("error_dr_list", "" + error.toString());
                    }
                });
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

//        if (id == R.id.nav_medical_record) {
//            Intent intent = new Intent(getApplicationContext(), ActivityDrDetailClient.class);
//            startActivity(intent);
//        }
         if (id == R.id.nav_message) {
            Intent intent = new Intent(getApplicationContext(), MessageConversation.class);
            startActivity(intent);
        } else if (id == R.id.nav_booking) {
            Intent intent = new Intent(getApplicationContext(), ActivityBookingClient.class);
            startActivity(intent);
        } else if (id == R.id.nav_jobs) {
            Intent intent = new Intent(getApplicationContext(), ActivityMyJobs.class);
            startActivity(intent);
        } else if (id == R.id.nav_favorites) {
            Intent intent = new Intent(getApplicationContext(), ActivityFavoritesClient.class);
            startActivity(intent);
        } else if (id == R.id.nav_support) {
             Intent intent = new Intent(getApplicationContext(), ActivitySupport.class);
             startActivity(intent);

        } else if (id == R.id.nav_blog) {
             Intent intent = new Intent(getApplicationContext(), ActivityBlog.class);
             startActivity(intent);

        } else if (id == R.id.nav_logout_dr) {
            showDialogConformation();
        } else if (id == R.id.nav_share) {
            /*Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Therapist Hire");
            String app_url = " https://play.google.com/store/apps/details?id=example.`therapist.hire";
            shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, app_url);
            startActivity(Intent.createChooser(shareIntent, "Share via"));
*/
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBody = "share drTherapist app";
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject Here");
            sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, "Share via"));
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
                //LogoutApi====;
                session.logout();
                //Google Logout==
                mGoogleSignInClient.signOut()
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                            }
                        });

                //LoginManager.getInstance().logOut();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
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

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.nav_header_lay:
                intent = new Intent(getApplicationContext(), ActivityEditProfileClient.class);
                startActivity(intent);
                break;

            case R.id.btn_find:
                intent = new Intent(getApplicationContext(), ActivityCategoryClient.class);
                startActivity(intent);
                break;
            case R.id.btn_post:
                //Utils.showDialog(MainActivity.this, "Loading Please Wait...");
                intent = new Intent(getApplicationContext(), ActivityPostJobClient.class);
                startActivity(intent);
                break;

            case R.id.btn_viewApplicants:
                intent = new Intent(getApplicationContext(), ActivityViewApplicant.class);
                startActivity(intent);
                break;

            case R.id.tv_view_more:
                intent = new Intent(getApplicationContext(), ActivityCategoryClient.class);
                startActivity(intent);
                break;
            case R.id.tv_view_jobs:
                intent = new Intent(getApplicationContext(), ActivityMyJobs.class);
                startActivity(intent);
                break;
            case R.id.ll_baby_care:
                intent = new Intent(this, ActivityDrListClient.class);
                intent.putExtra("CATEGORY", "1");
                intent.putExtra("CATE_NAME", "ABA");
                startActivity(intent);
                break;
            case R.id.ll_senier_care:
                intent = new Intent(this, ActivityDrListClient.class);
                intent.putExtra("CATEGORY", "2");
                intent.putExtra("CATE_NAME", "Occupation");
                startActivity(intent);
                break;
            case R.id.ll_lady_care:
                intent = new Intent(this, ActivityDrListClient.class);
                intent.putExtra("CATEGORY", "3");
                intent.putExtra("CATE_NAME", "Physical Therapy");
                startActivity(intent);
                break;
            case R.id.ll_gen:
                intent = new Intent(this, ActivityDrListClient.class);
                intent.putExtra("CATEGORY", "4");
                intent.putExtra("CATE_NAME", "Speech Therapy");
                startActivity(intent);
                break;
        }
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
//    private void getcount(String url) {
//        //Utils.showDialog(this, "Loading Please Wait...");
//        AndroidNetworking.post(url)
//                .addBodyParameter("user_id", user_id)
//                .setTag("Post List")
//                .setPriority(Priority.LOW)
//                .build()
//                .getAsJSONObject(new JSONObjectRequestListener() {
//                    @Override
//                    public void onResponse(JSONObject jsonObject) {
//                        // Utils.dismissDialog();
//                        Log.e("resp notification = ", "" + jsonObject);
//                        try {
//                            // String message = jsonObject.getString("msg");
//                            String result = jsonObject.getString("result");
//
//                            if (result.equalsIgnoreCase("true")) {
//
//                                JSONObject job = jsonObject.getJSONObject("data");
//                                //postModels = new ArrayList<>();
//
//                                //notificationData.notification_id = job.getString("notification_id");
//                                String notificationcount = job.getString("total");
//                                notification_count = String.valueOf(notificationcount);
//                                Log.e("notification count = ", notification_count);
//                                if (notification_count != null && notification_count.equalsIgnoreCase("0")) {
//                                    notifications_badge.setVisibility(GONE);
//                                }
//                                else {
//                                    Log.e("notification_count text", notification_count);
//                                    notifications_badge.setVisibility(VISIBLE);
//                                    notifications_badge.setText(String.valueOf(notification_count));
//                                }
//                            }
//
//
//
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                    @Override
//                    public void onError(ANError error) {
//                        Log.e("error = ", "" + error);
//                    }
//                });
//
//    }

}

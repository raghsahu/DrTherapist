package com.example.drtherapist.client.activity;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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
import com.example.drtherapist.client.model.ApplicantListData;
import com.example.drtherapist.client.model.DrListDataClient;
import com.example.drtherapist.client.model.DrListDataExp;
import com.example.drtherapist.client.model.FavoritesTherapist;
import com.example.drtherapist.client.model.SearchClient;
import com.example.drtherapist.common.BasicActivities.ChatActivity1;
import com.example.drtherapist.common.BasicActivities.ChatActivity_New;
import com.example.drtherapist.common.Interface.Config;
import com.example.drtherapist.common.Utils.NetworkUtil;
import com.example.drtherapist.common.Utils.ToastClass;
import com.example.drtherapist.common.Utils.Utils;
import com.example.drtherapist.common.remote.API;
import com.example.drtherapist.common.remote.Session;
import com.example.drtherapist.therapist.activity.ActivityMemberShipDr;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;

public class ActivityDrDetailClient extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_book_now, tv_ok, tv_contact_now, tv_exp,
            tv_rate, tv_address, tv_age, tv_ratting, tv_dr_bio, tv_gender, tv_age2, tv_specialization, tv_dr_name, tv_rate_hr;
    private RatingBar ratingBar_Dr_detail;
    private CircleImageView image_profile;
    ImageView iv_back, iv_pdf;
    private DrListDataClient drData;
    private DrListDataExp drListDataExp;
    private ApplicantListData applicantData;
    LinearLayout li_pdf;
    private FavoritesTherapist favoritesTherapist;
    SearchClient searchClient;
    Dialog dialog;
    Session session;
    String pdf, member,userId,username, catId, catName, emailIdDr, emailUser,
            firebase_uid,fcmToken,otherUser_id;
     String op_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dr_detail_client);
        session = new Session(this);
        emailUser = session.getUser().email;
        username = session.getUser().username;
        userId = session.getUser().id;
       // Log.e("User_id000",userId);

        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra("exp")) {
                drListDataExp = (DrListDataExp) intent.getSerializableExtra("MODEL");
            }
            if (intent.hasExtra("DrList")) {
                drData = (DrListDataClient) intent.getSerializableExtra("MODEL");
            }
            if (intent.hasExtra("applicantData")) {
                applicantData = (ApplicantListData) intent.getSerializableExtra("MODEL");
            }
            if (intent.hasExtra("fav")) {
                favoritesTherapist = (FavoritesTherapist) intent.getSerializableExtra("MODEL");
            }
            if (intent.hasExtra("SearchDrList")) {
                searchClient = (SearchClient) intent.getSerializableExtra("MODEL");
            }
        }

        initView();
        clickListner();

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        member = pref.getString("MEMBERED", null);
        Log.e("member", "" + member);
        if (member == null) {
            openDialogMember();
        }

    }

    private void openDialogMember() {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.membership_dialog_layout);
        tv_ok = dialog.findViewById(R.id.tv_ok);
//        btn_forgot_submit = dialog.findViewById(R.id.btn_forgot_submit);

        tv_ok.setOnClickListener(this);
        dialog.show();
    }

    private void initView() {
        tv_dr_name = findViewById(R.id.tv_dr_name);
        iv_back = findViewById(R.id.iv_back);
        image_profile = findViewById(R.id.image_profile);
        tv_book_now = findViewById(R.id.tv_book_now);
        tv_contact_now = findViewById(R.id.tv_contact_now);
        tv_exp = findViewById(R.id.tv_exp);
        tv_rate = findViewById(R.id.tv_rate);
        tv_address = findViewById(R.id.tv_address);
        tv_rate_hr = findViewById(R.id.tv_rate_hr);
        tv_age = findViewById(R.id.tv_age);
        tv_age2 = findViewById(R.id.tv_age2);
        tv_gender = findViewById(R.id.tv_gender);
        tv_specialization = findViewById(R.id.tv_specialization);
        tv_dr_bio = findViewById(R.id.tv_dr_bio);
        ratingBar_Dr_detail = findViewById(R.id.ratingBar_Dr_detail);
        tv_ratting = findViewById(R.id.tv_ratting);
        iv_pdf = findViewById(R.id.iv_pdf);
        li_pdf = findViewById(R.id.li_pdf);


        if (drListDataExp != null) {
            op_name=drListDataExp.name;
            otherUser_id=drListDataExp.id;
            Log.e("dr_id", otherUser_id);
            emailIdDr = drListDataExp.email;
            firebase_uid = drListDataExp.dr_unique_key;
            tv_ratting.setText(drListDataExp.rating);
            tv_dr_name.setText(drListDataExp.name);
            tv_specialization.setText(drListDataExp.specialization);
            tv_age.setText(drListDataExp.age + " yrs old");
            tv_exp.setText(drListDataExp.experience + " yrs exp.");
            tv_address.setText(drListDataExp.address);
            //tv_rate.setText(drListDataExp.fee + " /hr.");
            //tv_dr_bio.setText(drData. + " /hr.");
            ratingBar_Dr_detail.setRating(Float.parseFloat(drListDataExp.rating));
            pdf = drListDataExp.resume;
            if (drListDataExp.image != null && !drListDataExp.image.equalsIgnoreCase("")) {
                Picasso.with(this).load(Config.Image_Url+drListDataExp.image)
                        .placeholder(R.drawable.drprofile)
                        .error(R.drawable.drprofile)
                        .into(image_profile);
            }
        }

        if (drData != null) {
            otherUser_id=drData.id;
            op_name=drData.name;
            Log.e("dr_id", otherUser_id);
            emailIdDr = drData.email;
            catId = drData.cat_id;
            firebase_uid = drData.dr_unique_key;
            fcmToken = drData.fcm_id;
            catName = drData.cat_name;
            tv_ratting.setText(drData.rating);
            tv_dr_name.setText(drData.name);
            tv_gender.setText(drData.gender);
            tv_age.setText(drData.age + " yrs old");
            tv_age2.setText(drData.age + " yrs old");
            tv_specialization.setText(drData.specialization);
            tv_exp.setText(drData.experience + " yrs exp.");
            tv_address.setText(drData.location);
            tv_rate.setText(drData.fee + "/hr.");
            tv_rate_hr.setText(drData.fee + "/hr.");
            //tv_dr_bio.setText(drData. + " /hr.");
            ratingBar_Dr_detail.setRating(Float.parseFloat(drData.rating));
            pdf = drData.resume;

            if (drData.image != null && !drData.image.equalsIgnoreCase("")) {
                Picasso.with(this).load(drData.image)
                        .placeholder(R.drawable.drprofile)
                        .error(R.drawable.drprofile)
                        .into(image_profile);
            }
        }
        if (applicantData != null) {
            otherUser_id=applicantData.dr_id;
            op_name=applicantData.dr_fname;
            Log.e("dr_id", otherUser_id);
            emailIdDr = applicantData.dr_email;
            catId = applicantData.cat_id;
            firebase_uid = applicantData.dr_unique_key;
            tv_ratting.setText(applicantData.rating);
            tv_dr_name.setText(applicantData.dr_fname);
            tv_specialization.setText(applicantData.specialization);
            tv_age.setText(applicantData.age + " yrs old");
            tv_age2.setText(applicantData.age + " yrs old");
            tv_exp.setText(applicantData.experience + " yrs exp.");
            tv_address.setText(applicantData.location);
            tv_gender.setText(applicantData.gender);
            tv_rate.setText(applicantData.fee + "/hr.");
            tv_rate_hr.setText(applicantData.fee + "/hr.");
            //tv_dr_bio.setText(drData. + " /hr.");
            ratingBar_Dr_detail.setRating(Float.parseFloat(applicantData.rating));
            pdf = applicantData.resume;

            if (applicantData.dr_image != null && !applicantData.dr_image.equalsIgnoreCase("")) {
                Picasso.with(this).load(applicantData.dr_image)
                        .placeholder(R.drawable.drprofile)
                        .into(image_profile);
            }
        }
        if (favoritesTherapist != null) {
            otherUser_id=favoritesTherapist.dr_id;
            op_name=favoritesTherapist.dr_fname;
            Log.e("dr_id", otherUser_id);
            emailIdDr = favoritesTherapist.dr_email;
            catId = favoritesTherapist.cat_id;
            firebase_uid = favoritesTherapist.dr_unique_key;
            //tv_ratting.setText(favoritesTherapist.rating);
            tv_dr_name.setText(favoritesTherapist.dr_fname);
            tv_specialization.setText(favoritesTherapist.specialization);
            tv_age.setText(favoritesTherapist.age + " yrs old");
            tv_age2.setText(favoritesTherapist.age + " yrs old");
            tv_exp.setText(favoritesTherapist.experience + " yrs exp.");
            tv_address.setText(favoritesTherapist.location);
            tv_gender.setText(favoritesTherapist.gender);
            tv_rate.setText(favoritesTherapist.fee + "/hr.");
            tv_rate_hr.setText(favoritesTherapist.fee + "/hr.");
            //tv_dr_bio.setText(drData. + " /hr.");
            //ratingBar_Dr_detail.setRating(Float.parseFloat(applicantData.rating));
            pdf = favoritesTherapist.resume;
            if (favoritesTherapist.dr_image != null && !favoritesTherapist.dr_image.equalsIgnoreCase("")) {
                Picasso.with(this).load(favoritesTherapist.dr_image)
                        .placeholder(R.drawable.drprofile)
                        .into(image_profile);
            }
        }
        if (searchClient != null) {
            otherUser_id=searchClient.dr_id;
            op_name=searchClient.dr_fname;
            Log.e("dr_id", otherUser_id);
            emailIdDr = searchClient.dr_email;
            catId = searchClient.cat_id;
            firebase_uid = searchClient.dr_unique_key;
            //tv_ratting.setText(favoritesTherapist.rating);
            tv_dr_name.setText(searchClient.dr_fname);
            tv_specialization.setText(searchClient.specialization);
            tv_age.setText(searchClient.age + " yrs old");
            tv_age2.setText(searchClient.age + " yrs old");
            tv_exp.setText(searchClient.experience + " yrs exp.");
            tv_address.setText(searchClient.location);
            tv_gender.setText(searchClient.gender);
            tv_rate.setText(searchClient.fee + "/hr.");
            tv_rate_hr.setText(searchClient.fee + "/hr.");
            tv_dr_bio.setText(searchClient.bio);
            //ratingBar_Dr_detail.setRating(Float.parseFloat(applicantData.rating));
            pdf = searchClient.resume;
            if (searchClient.dr_image != null && !searchClient.dr_image.equalsIgnoreCase("")) {
                Picasso.with(this).load(Config.Image_Url+searchClient.dr_image)
                        .placeholder(R.drawable.drprofile)
                        .into(image_profile);
            }
        }
    }

    private void clickListner() {
        tv_book_now.setOnClickListener(this);
        tv_contact_now.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        iv_pdf.setOnClickListener(this);
        li_pdf.setOnClickListener(this);

    }

    @Override
    public void onBackPressed() {
        if (drListDataExp != null) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent(getApplicationContext(), ActivityDrListClient.class);
            intent.putExtra("CATEGORY", catId);
            intent.putExtra("CATE_NAME", catName);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.tv_book_now:

                // if membership not avail open membership page

//                intent = new Intent(getApplicationContext(), ActivityMemberShipClient.class);
//                startActivity(intent);

                if (NetworkUtil.isNetworkConnected(getApplicationContext())) {
                    try {
                       String url = API.BASE_URL + "Dr_APP_Membership";
                        userAppMemberApi(url);
                    } catch (NullPointerException e) {
                        ToastClass.showToast(getApplicationContext(), getString(R.string.too_slow));
                    }
                } else {
                    ToastClass.showToast(getApplicationContext(), getString(R.string.no_internet_access));
                }


                break;
            case R.id.iv_back:
                if (drListDataExp != null) {
                    intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    intent = new Intent(getApplicationContext(), ActivityDrListClient.class);
                    intent.putExtra("CATEGORY", catId);
                    intent.putExtra("CATE_NAME", catName);
                    startActivity(intent);
                    finish();
                }
                break;

            case R.id.tv_contact_now:
               // intent = new Intent(getApplicationContext(), ChatActivity1.class);
                intent = new Intent(getApplicationContext(), ChatActivity_New.class);
//                intent.putExtra("UID", firebase_uid);
//                intent.putExtra("fcmToken", fcmToken);
//                intent.putExtra("userId", userId);
//                intent.putExtra("username", username);

                intent.putExtra("FUID", otherUser_id);
                intent.putExtra("FCMTOKEN", fcmToken);
                intent.putExtra("user_name", op_name);
                Log.e("FUID", otherUser_id);
               // intent.putExtra("IMAGE", image1);
                startActivity(intent);
                break;
            case R.id.tv_ok:
                intent = new Intent(getApplicationContext(), ActivityMemberShipClient.class);
                intent.putExtra("EMAILUSER", emailUser);
                startActivity(intent);
                break;
            case R.id.iv_pdf:
                pdfOpen();
                break;
            case R.id.li_pdf:
                pdfOpen();
                break;
        }
    }

    private void userAppMemberApi(String url) {

        Utils.showDialog(this, "Loading Please Wait...");
        AndroidNetworking.post(url)
                .addBodyParameter("user_id", userId)
                .addBodyParameter("type", "client")
                .setTag("membershipApp job")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Utils.dismissDialog();
                        Log.e("memberAppUser_resp = ", "" + jsonObject);
                        try {
                            String message = jsonObject.getString("msg");
                            String result = jsonObject.getString("result");
                           // String is_status = jsonObject.getString("is_status");

                            if (result.equalsIgnoreCase("true")) {
                               // Toast.makeText(getApplicationContext(), "Thank you !!!!!!!!", Toast.LENGTH_LONG).show();

                               Intent intent1 = new Intent(getApplicationContext(), ActivityBookNowForm.class);
                               intent1.putExtra("otherDr_id",otherUser_id);
                                startActivity(intent1);

                            } else {

                                //************real page***
                                Intent intent = new Intent(getApplicationContext(), ActivityMemberShipDr.class);
                                intent.putExtra("login_type","client");
                                startActivity(intent);

                                Toast.makeText(getApplicationContext(), "" + message, Toast.LENGTH_LONG).show();
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

    public void pdfOpen() {

        try {
            if (pdf!=null){
                File pdfFile = new File(Environment.getExternalStorageDirectory(), pdf);

            if (pdfFile.exists()) {
                Uri path = Uri.fromFile(pdfFile);
                Intent objIntent = new Intent(Intent.ACTION_VIEW);
                objIntent.setDataAndType(path, "application/pdf");
                objIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(objIntent);
            } else {
                Toast.makeText(ActivityDrDetailClient.this, "File NotFound",
                        Toast.LENGTH_SHORT).show();
            }

        }

        }catch (ActivityNotFoundException e) {
            Toast.makeText(ActivityDrDetailClient.this,
                    "No Viewer Application Found", Toast.LENGTH_SHORT)
                    .show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

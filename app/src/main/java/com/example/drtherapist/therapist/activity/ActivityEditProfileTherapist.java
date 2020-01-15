package com.example.drtherapist.therapist.activity;

import android.Manifest;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;

import android.net.Uri;
import android.os.AsyncTask;

import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.UploadProgressListener;

import com.bumptech.glide.util.Util;
import com.example.drtherapist.R;

import com.example.drtherapist.client.model.UserInfoData;
import com.example.drtherapist.common.Interface.Config;
import com.example.drtherapist.common.Interface.UserInterface;
import com.example.drtherapist.common.Utils.AppHelper;
import com.example.drtherapist.common.Utils.Const;
import com.example.drtherapist.common.Utils.FileUtils;
import com.example.drtherapist.common.Utils.NetworkUtil;
import com.example.drtherapist.common.Utils.PDFFilePath;
import com.example.drtherapist.common.Utils.ToastClass;
import com.example.drtherapist.common.Utils.Utility;
import com.example.drtherapist.common.Utils.Utils;
import com.example.drtherapist.common.Utils.Validation;
import com.example.drtherapist.common.Utils.VolleyMultipartRequest;
import com.example.drtherapist.common.Utils.VolleySingleton;
import com.example.drtherapist.common.remote.API;
import com.example.drtherapist.common.remote.Session;
import com.example.drtherapist.therapist.model.Profile_Update_Model;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.squareup.picasso.Picasso;


import net.gotev.uploadservice.MultipartUploadRequest;


import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.ByteArrayOutputStream;
import java.io.File;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;

import java.util.Map;
import java.util.UUID;


import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.drtherapist.common.remote.API.BASE_URL;

public class ActivityEditProfileTherapist extends AppCompatActivity implements
        View.OnClickListener {
    Bitmap _FinalBitmap;
    String photoPath="";
    CircleImageView edit_profile_image;
    Context context;
    private Session session;
    String userId, catId;
    private String username = "";
    private String image = "";
    private String age = "";
    private String city = "";
    private String state = "";
    private String zip = "";
    private String Gender = "";
    private String dob = "";
    private String mobile = "";
    private String email = "";
    private String experience = "";
    private String resume = "";
    private String address = "";
    private String Createddate = "";
    private Uri pdffilePath = Uri.parse("");
    Dialog dialog;
    ProgressDialog progressDialog;
    EditText et_old_pass, et_new_pass, et_new_pass2;
    Button btn_change_pass;
    EditText edit_higher_educa, et_edit_linked, et_edit_licence;
    EditText et_forgot_email, et_edit_name,
            et_edit_gender, et_edit_city,
            et_edit_state, et_edit_zip, et_edit_phone, et_edit_email, et_edit_exp;
    TextView tv_change_password, tv_dob, tv_edit_resume, tv_hiden_password, tv_age, date, tv_category;
    Button btn_forgot_submit, btn_profile_submit_the, btn_profile_cencel_the;

    private int mYear, mMonth, mDay;
    String oldPass;
    String newPass;
    String newPass2;
    static String image_Str;
    private String serverResume;
    private String uploadedFileName;
    private File file;
    private Uri fileImage;
    String imgpath;
    RadioGroup radioGroup;
    public static final String UPLOAD_URL = "http://logicalsofttech.com/therapist/index.php/Api/edit_Doc";
    private static final int STORAGE_PERMISSION_CODE = 123;
    File pdfFile;
    File destination;
    private int REQUEST_CAMERA = 0, REQUEST_GALLERY = 1;
    String userChoosenTask;
    String filenew;
    Uri selectedPdfUri;
    LinearLayout linear_avalabletime;
    private int PICK_PDF_REQUEST = 2;
    String path;
    String filename;
    RadioButton radioButton_male, radioButton_female;
    //String Gender;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_therapist);
        context = ActivityEditProfileTherapist.this;
        requestStoragePermission();

        session = new Session(this);


        if (session != null) {
            userId = session.getUser().id;
            catId = session.getUser().cat_id;
            username = session.getUser().username;
            image = session.getUser().image;
            Log.e("image", "" + image);
            age = session.getUser().age;
            Createddate = session.getUser().date;
            city = session.getUser().city;
            state = session.getUser().state;
            zip = session.getUser().zip;
            Gender = session.getUser().gender;
            dob = session.getUser().dob;
            Log.e("dob", "" + dob);
            mobile = session.getUser().mobile;
            email = session.getUser().email;
            experience = session.getUser().experience;
            resume = session.getUser().resume;
            address = session.getUser().address;
            Log.e("userId ", "" + userId);
        }
        getDetailsAPiCall();

        Toolbar toolbar = findViewById(R.id.toolbar_edit_profile_therapist);
        tv_dob = findViewById(R.id.tv_dob);
        linear_avalabletime = findViewById(R.id.linear_avalabletime);

        tv_category = findViewById(R.id.tv_category);
        date = findViewById(R.id.date);
        et_edit_name = findViewById(R.id.et_edit_name);
        // et_edit_gender = findViewById(R.id.et_edit_gender);
        tv_age = findViewById(R.id.tv_age);
        radioButton_male = findViewById(R.id.male);
        radioGroup = findViewById(R.id.radio_group);
        radioButton_female = findViewById(R.id.female);
        et_edit_city = findViewById(R.id.et_edit_city);
        et_edit_state = findViewById(R.id.et_edit_state);
        et_edit_zip = findViewById(R.id.et_edit_zip);
        et_edit_phone = findViewById(R.id.et_edit_phone);
        tv_change_password = findViewById(R.id.tv_change_password);
        et_edit_email = findViewById(R.id.et_edit_email);
        et_edit_exp = findViewById(R.id.et_edit_exp);
        tv_edit_resume = findViewById(R.id.tv_edit_resume);
        tv_hiden_password = findViewById(R.id.tv_hiden_password);
        btn_profile_submit_the = findViewById(R.id.btn_profile_submit_the);
        edit_profile_image = findViewById(R.id.edit_profile_image);
        btn_profile_cencel_the = findViewById(R.id.btn_profile_cencel_the);
        et_edit_licence = findViewById(R.id.et_edit_licence);
        et_edit_linked = findViewById(R.id.et_edit_linked);
        edit_higher_educa = findViewById(R.id.edit_higher_educa);



        toolbar.setTitle("Edit Profile");
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_backspace_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        tv_hiden_password.setText(Html.fromHtml("<u>******</u>"));

        tv_dob.setOnClickListener(this);
        tv_edit_resume.setOnClickListener(this);
        tv_hiden_password.setOnClickListener(this);
        tv_change_password.setOnClickListener(this);
        btn_profile_submit_the.setOnClickListener(this);
        btn_profile_cencel_the.setOnClickListener(this);


        tv_age.setText(age + " yrs.");
        date.setText(Createddate);
        tv_dob.setText(dob);
        et_edit_city.setText(city);
        et_edit_state.setText(state);
        et_edit_zip.setText(zip);
        et_edit_phone.setText(mobile);
        et_edit_email.setText(email);
        et_edit_exp.setText(experience);

        Log.e("resumeProfile", "" + tv_edit_resume.getText().toString());
        if (session != null) {
            if (!session.getUser().username.equalsIgnoreCase("") && session.getUser().username != null) {
                et_edit_name.setText(username);
                edit_higher_educa.setText(session.getUser().highEducation);
                et_edit_linked.setText(session.getUser().linkedUrl);
                et_edit_licence.setText(session.getUser().licenceNmbr);
                tv_edit_resume.setText(session.getUser().resume.toString());
                Log.e("username", "" + username);
                Log.e("resumeProfile1", "" + resume.toString());
            }


            if (!session.getUser().image.equalsIgnoreCase("") && session.getUser().image != null) {

                Picasso.with(this).load(Config.DrImage_Url + image)
                        .placeholder(R.drawable.doctor)
                        .into(edit_profile_image);

            }


        }
        edit_profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        linear_avalabletime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActivityEditProfileTherapist.this, ActivityAvailableTherapist.class));
            }
        });


        if (session != null) {
            String data = session.getResume();

            String mStr_Gender = session.getGender();

            Log.e("genderr ", "" + session.getGender());
            Log.e("resume check", "ddd" + data);
            et_edit_name.setText(session.getName());
            tv_edit_resume.setText(session.getResume());

            if (!mStr_Gender.isEmpty() && mStr_Gender!=null ){
                if (mStr_Gender.equals("female")) {

                    radioButton_female.setChecked(true);
                } else if (mStr_Gender.equals("male")){

                    radioButton_male.setChecked(true);
                }

            }

            //  et_edit_gender.setText(session.getGender());
            date.setText(session.getDate());
            et_edit_city.setText(session.getCity());
            et_edit_state.setText(session.getState());
            et_edit_zip.setText(session.getZip());
            et_edit_phone.setText(session.getPhone());
            et_edit_exp.setText(session.getExeperience());
            tv_age.setText(session.getAge());
            edit_higher_educa.setText(session.getHighEdu());
            et_edit_linked.setText(session.getProfileUrl());
            et_edit_licence.setText(session.getLicenceNumber());
            image_Str=session.getImage();

            Log.e(" image or in session",session.getImage());


            Picasso.with(this).load(Config.DrImage_Url + image_Str)
                    .placeholder(R.drawable.doctor)
                    .into(edit_profile_image);

        }


    }

    private void getDetailsAPiCall() {
        System.out.println("check user id "+userId);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, BASE_URL+"DoctorDetails",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //hiding the progressbar after completion
                       // progressBar.setVisibility(View.INVISIBLE);


                        try {
                            //getting the whole json object from the response

                            if (response != null && response.length() > 0) {

                                JSONObject userObject = new JSONObject(response);
                                JSONObject jsonid = userObject.getJSONObject("data");
                                String dr_name = jsonid.getString("dr_fname");
                                String dr_contact = jsonid.getString("dr_contact");
                                String dr_email = jsonid.getString("dr_email");
                                String dr_pass = jsonid.getString("dr_pass");
                                String dr_address = jsonid.getString("dr_address");
                                String dr_image = jsonid.getString("dr_image");
                                String age = jsonid.getString("age");
                                String city = jsonid.getString("city");
                                String experience=jsonid.getString("experience");
                                String state = jsonid.getString("state");
                                String zip = jsonid.getString("zip");
                                String gender = jsonid.getString("gender");
                                String high_education = jsonid.getString("high_education");
                                String linked_url = jsonid.getString("linked_url");
                                String licence_nmbr = jsonid.getString("licence_nmbr");
                               // String dr_name = jsonid.getString("dr_fname");

                                et_edit_exp.setText(experience);
                                et_edit_name.setText(dr_name);
                                et_edit_phone.setText(dr_contact);
                                et_edit_city.setText(city);
                                et_edit_state.setText(state);
                                 et_edit_zip.setText(zip);
                                if (gender.equals("female"))
                                {
                                    radioButton_female.isChecked();

                                }
                                else
                                {
                                    radioButton_male.isChecked();
                                }

                                edit_higher_educa.setText(high_education);


                                et_edit_linked.setText(linked_url);

                                et_edit_licence.setText(licence_nmbr);

                                 tv_age.setText(age);


                                Picasso.with(ActivityEditProfileTherapist.this).load(Config.DrImage_Url + dr_image)
                                        .placeholder(R.drawable.doctor)
                                        .into(edit_profile_image);

                                System.out.println("chekc dr name"+dr_name);


                            }






                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {


                        Log.e("check exception ",error.toString());
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("dr_id", userId);

                return params;
            }
        };

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //adding the string request to request queue
        requestQueue.add(stringRequest);

    }


    //*****************************************************************
    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(ActivityEditProfileTherapist.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility.checkPermission(ActivityEditProfileTherapist.this);

                if (items[item].equals("Take Photo")) {
                    userChoosenTask = "Take Photo";
                    if (result)
                        cameraIntent();

                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask = "Choose from Library";
                    if (result)
                        galleryIntent();

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void galleryIntent() {
        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, 1);
    }

    private void cameraIntent() {
       // Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
      //  startActivityForResult(intent, 2);



        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
       // intent.putExtra(MediaStore.EXTRA_OUTPUT, fileImage);
        startActivityForResult(intent, 2);


      /*  String fileName = System.currentTimeMillis()+".jpg";
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, fileName);
        fileImage = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileImage);
        startActivityForResult(intent, 2);*/
    }



//***************************************************

    private void openChangePassDialog() {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.change_password_layout);
        et_old_pass = dialog.findViewById(R.id.et_old_pass);
        et_new_pass = dialog.findViewById(R.id.et_new_pass);
        et_new_pass2 = dialog.findViewById(R.id.et_new_pass2);
        btn_change_pass = dialog.findViewById(R.id.btn_change_pass);

        btn_change_pass.setOnClickListener(this);

        dialog.show();
    }

    public void checkValidationPass() {

        String oldPass = et_old_pass.getText().toString().trim();
        String pass = et_new_pass.getText().toString().trim();
        String repass = et_new_pass2.getText().toString().trim();

        Validation validation = new Validation(this);


        if (!validation.isValidPassword(oldPass)) {
            ToastClass.showToast(ActivityEditProfileTherapist.this, getString(R.string.password_null));
            et_old_pass.requestFocus();
        } else if (!validation.isValidPassword(pass)) {
            ToastClass.showToast(ActivityEditProfileTherapist.this, getString(R.string.password_null));
            et_new_pass.requestFocus();
        } else if (validation.isConfirmPassword(pass, repass)) {
            ToastClass.showToast(ActivityEditProfileTherapist.this, getString(R.string.cpassword_v));
            et_new_pass2.requestFocus();
        } else {

            if (NetworkUtil.isNetworkConnected(ActivityEditProfileTherapist.this)) {
                try {
                    String url = BASE_URL + "changeTherapistPassword";
                    changePassApi(url);

                } catch (NullPointerException e) {
                    ToastClass.showToast(ActivityEditProfileTherapist.this, getString(R.string.too_slow));
                }
            } else {
                ToastClass.showToast(ActivityEditProfileTherapist.this, getString(R.string.no_internet_access));
            }

        }
    }


    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.tv_edit_resume:
                Intent intent1 = new Intent();
                intent1.setType("application/pdf");
                intent1.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent1, "Select Pdf"), 0);


                break;

            case R.id.tv_dob:
                calender();
                break;
            case R.id.tv_hiden_password:
                openChangePassDialog();
                break;
            case R.id.tv_change_password:
                openChangePassDialog();
                break;
            case R.id.btn_profile_submit_the:
                checkValidation();
                break;
            case R.id.btn_profile_cencel_the:
                onBackPressed();
                break;
            case R.id.btn_change_pass:
                oldPass = et_old_pass.getText().toString();
                newPass = et_new_pass.getText().toString();
                checkValidationPass();
                break;
        }


    }

    private void changePassApi(String url) {

        Utils.showDialog(ActivityEditProfileTherapist.this, "Loading Please Wait...");
        AndroidNetworking.post(url)
                .addBodyParameter("dr_id", userId)
                .addBodyParameter("oldpassword", oldPass)
                .addBodyParameter("newpassword", newPass)
                /*.addBodyParameter("lat", "")
                .addBodyParameter("lon", "")
                .addBodyParameter("register_id", "")*/
                .setTag("userLogin")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        //Utils.hideProgress(mdialog);
                        Utils.dismissDialog();
                        Log.e("ChangePass  = ", "ChangePass res client" + jsonObject);
                        try {
                            String msg = jsonObject.getString("msg");
                            String result = jsonObject.getString("result");

                            if (result.equalsIgnoreCase("true")) {
                                dialog.dismiss();
                                ToastClass.showToast(ActivityEditProfileTherapist.this, msg);
                            } else {
                                Utils.openAlertDialog(ActivityEditProfileTherapist.this, msg);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                    }
                });

    }


    //Calender open===
    public void calender() {

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

//            c.setz(Calendar.MONTH, Calendar.JANUARY);
//            c.set(Calendar.DAY_OF_MONTH, 9);
//            c.set(Calendar.YEAR, 2015);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, R.style.calender_dialog_theme,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        tv_dob.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }


    //validations==
    private void checkValidation() {
        String name = et_edit_name.getText().toString();
        // String gender = et_edit_gender.getText().toString();
        String city = et_edit_city.getText().toString();
        String zip = et_edit_zip.getText().toString();
        String state = et_edit_state.getText().toString();
        String email = et_edit_email.getText().toString();
        String phone = et_edit_phone.getText().toString();
        String exp = et_edit_exp.getText().toString();

        Validation validation = new Validation(this);


        if (!validation.isEmpty(name)) {
            ToastClass.showToast(this, getString(R.string.fullname_v));
            et_edit_name.requestFocus();
        } else if (!validation.isEmpty(Gender)) {
            ToastClass.showToast(this, getString(R.string.gender_v));
            //et_edit_gender.requestFocus();
        } else if (!validation.isEmpty(city)) {
            ToastClass.showToast(this, getString(R.string.city_v));
            et_edit_city.requestFocus();

        } else if (!validation.isEmpty(state)) {
            ToastClass.showToast(this, getString(R.string.state_v));
            et_edit_state.requestFocus();

        } else if (!validation.isEmpty(zip)) {
            ToastClass.showToast(this, getString(R.string.zip_v));
            et_edit_zip.requestFocus();

        } else if (!validation.isEmpty(email)) {
            ToastClass.showToast(this, getString(R.string.email_v));
            et_edit_email.requestFocus();

        } else if (!validation.isEmpty(phone)) {
            ToastClass.showToast(this, getString(R.string.contact_v));
            et_edit_phone.requestFocus();

        } else if (!validation.isEmpty(exp)) {
            ToastClass.showToast(this, getString(R.string.exp_v));
            et_edit_exp.requestFocus();

        } else if (edit_profile_image == null) {
            Toast.makeText(this, "Please upload image", Toast.LENGTH_SHORT).show();
        }

        if (radioButton_male.isChecked()) {
            Gender = "male";
        } else {
            Gender = "female";

        }


        if (NetworkUtil.isNetworkConnected(this)) {
            EditThrepistApiFunCall();

            /*using volley */
            // Toast.makeText(context, "work", Toast.LENGTH_SHORT).show();
            // CallEditTherapist();
            // volley not use
            //CallEditTherapist1();//android netwoking call method
            //Log.e("edit dr url = ", url);
            //  UpdateProfileTherapist();// retrofilt call
            //  new Profile_Update_Excute().execute();//*****asynctask

        } else {
            ToastClass.showToast(this, getString(R.string.no_internet_access));
        }
    }


    ////////////////////////////////////////volley////////////////////////////
    public void EditThrepistApiFunCall() {
        //getting the actual path of the pdf
        try {

            path = PDFFilePath.getPath(this, pdffilePath);
           // Toast.makeText(context, "please select pdf file from internal path", Toast.LENGTH_SHORT).show();


            imgpath = PDFFilePath.getPath(this, fileImage);
            System.out.println("chekc file " + imgpath);


            if (path != null && imgpath != null) {
                try {
                    String uploadId = UUID.randomUUID().toString();

                    new MultipartUploadRequest(this, uploadId, UPLOAD_URL)
                            .addParameter("name", et_edit_name.getText().toString())
                            .addParameter("mobile", et_edit_phone.getText().toString())
                            .addParameter("email", et_edit_email.getText().toString())
                            .addParameter("address", address)
                            .addFileToUpload(imgpath, "dr_image")
                            .addParameter("exeprience", et_edit_exp.getText().toString())
                            .addParameter("gender", Gender)
                            .addParameter("dob", tv_dob.getText().toString())
                            .addParameter("state", et_edit_state.getText().toString())
                            .addParameter("zip", et_edit_zip.getText().toString())
                            .addParameter("age", age)
                            .addParameter("city", et_edit_city.getText().toString())
                            .addFileToUpload(path, "resume")
                            .addParameter("licence_nmbr", et_edit_licence.getText().toString())
                            .addParameter("linked_url", et_edit_linked.getText().toString())
                            .addParameter("high_education", edit_higher_educa.getText().toString())
                            .addParameter("cat_id", catId)
                            .setMaxRetries(2)
                            .startUpload();

                    Log.e("check destination ", path);
                    System.out.println("check destination " + path);
                    startActivity(new Intent(context, ActivityNavigationDr.class));
                    Toast.makeText(context, "Updated Successfully", Toast.LENGTH_SHORT).show();

                } catch (Exception exc) {

                    System.out.println("chekc file exc" + imgpath);
                    System.out.println("check exception " + exc);

                    Toast.makeText(this, " " + exc.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            }



            else {

                uploadBitmap(_FinalBitmap);
            }






        }catch(Exception e)
        {


            Toast.makeText(context, "please select pdf file from internal path", Toast.LENGTH_SHORT).show();


            System.out.println("check exce "+e.getMessage().toString());


        }
    }

    private void uploadBitmap(final Bitmap bitmap) {

        //getting the tag from the edittext
        //our custom volley request

        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, UPLOAD_URL,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {
                            JSONObject obj = new JSONObject(new String(response.data));
                            JSONObject jsonObject=new JSONObject(new String(response.data));
                            startActivity(new Intent(ActivityEditProfileTherapist.this,ActivityNavigationDr.class));
                            Toast.makeText(context, "Submitted Data Successfully", Toast.LENGTH_SHORT).show();


                        }
                        catch (JSONException e) {
                            Log.e("check exception ",e.toString());
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {


            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", et_edit_name.getText().toString());
                params.put("mobile", et_edit_phone.getText().toString());
                params.put("email", et_edit_email.getText().toString());
                params.put("address", address);
                params.put("dr_id", userId);
                params.put("gender", Gender);
                params.put("dob", tv_dob.getText().toString());
                params.put("state", et_edit_state.getText().toString());
                params.put("zip", et_edit_zip.getText().toString());
                params.put("age", age);
                params.put("city", et_edit_city.getText().toString());
                params.put("licence_nmbr", et_edit_licence.getText().toString());
                params.put("linked_url", et_edit_linked.toString());
                params.put("high_education", edit_higher_educa.getText().toString());
                params.put("cat_id", catId);
                return params;
            }

            /*
             * Here we are passing image by renaming it with a unique name
             * */
            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                if (bitmap!=null)
                {
                    params.put("dr_image", new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap)));

                }

                return params;
            }
        };

        //adding the request to volley
        Volley.newRequestQueue(this).add(volleyMultipartRequest);
    }



    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }




//**************************************retrofit*******************




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {


                try {
                    pdffilePath = data.getData();
                    String path = PDFFilePath.getPath(this, pdffilePath);
                    File file = new File(path);
                    filename = file.getName();
                    session.setResume(filename);

                    tv_edit_resume.setText(filename);


                } catch (Exception e) {
                    System.out.println(" exc " + e.getMessage().toString());
                    Toast.makeText(context, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }


            }
        }
            if (requestCode == 1) {
                if (resultCode == RESULT_OK) {
                    onSelectFromGalleryResult(data);
                }
            }


        if (requestCode == 2) {
            if (resultCode == RESULT_OK) {

                onCaptureImageResult(data);
            }
        }
            }

    //*****************************************************
    private void onSelectFromGalleryResult(Intent data) {
        Bitmap bm = null;

        if (data != null) {

            fileImage = data.getData();

            String[] filePath = {MediaStore.Images.Media.DATA};
            Cursor cursor = ActivityEditProfileTherapist.this.getContentResolver().query(fileImage, filePath, null, null, null);
            cursor.moveToFirst();
            destination = new File(cursor.getString(cursor.getColumnIndex(filePath[0])));
            cursor.close();

            if (destination != null) {
              //  filenew = destination.getAbsolutePath();
                filenew =  destination.getName();
                session.setImage(filenew);

            } else {
                Toast.makeText(ActivityEditProfileTherapist.this, "something wrong", Toast.LENGTH_SHORT).show();
            }

            try {
                bm = MediaStore.Images.Media.getBitmap(ActivityEditProfileTherapist.this.getContentResolver(), data.getData());

              _FinalBitmap=bm;

                //session.setImage(bm);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        edit_profile_image.setImageBitmap(bm);
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = null;
        if (data != null) {
            Uri uri=data.getData();
             Log.e("checl uri ", String.valueOf(uri));

           //  fileImage=data.getData();


             thumbnail = (Bitmap) data.getExtras().get("data");
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);


            // getImageUri(context, thumbnail);

            destination = new File(Environment.getExternalStorageDirectory(),
                    System.currentTimeMillis() + ".jpg");

            FileOutputStream fo;
            try {
                destination.createNewFile();
                fo = new FileOutputStream(destination);
                if (destination != null) {
                    filenew = destination.getAbsolutePath();
                    filenew = destination.getName();
                    session.setImage(filenew);

                    fileImage =  Uri.fromFile(destination);

                    System.out.println("check uri.."+fileImage);


                    //  session.setImage(filenew);
                    // Toast.makeText(getActivity(), "path is"+destination.getAbsolutePath(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ActivityEditProfileTherapist.this, "something wrong", Toast.LENGTH_SHORT).show();
                }
                fo.write(bytes.toByteArray());
                fo.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            _FinalBitmap=thumbnail;
            edit_profile_image.setImageBitmap(thumbnail);


        }
    }
    ///////////////////////////////////////for uploading pdf file////////////////////////////////////////
    //Requesting permission
    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }

    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if (requestCode == STORAGE_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Displaying a toast
                Toast.makeText(this, "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }

    }
}

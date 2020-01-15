package com.example.drtherapist.client.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.drtherapist.R;
import com.example.drtherapist.client.model.UserInfoData;
import com.example.drtherapist.common.Utils.AppHelper;
import com.example.drtherapist.common.Utils.NetworkUtil;
import com.example.drtherapist.common.Utils.ToastClass;
import com.example.drtherapist.common.Utils.Utils;
import com.example.drtherapist.common.Utils.Validation;
import com.example.drtherapist.common.Utils.VolleyMultipartRequest;
import com.example.drtherapist.common.remote.API;
import com.example.drtherapist.common.remote.Session;
import com.example.drtherapist.therapist.activity.ActivityEditProfileTherapist;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.squareup.picasso.Picasso;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickCancel;
import com.vansuita.pickimage.listeners.IPickResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.drtherapist.common.remote.API.URL_ClientImage;


public class ActivityEditProfileClient extends AppCompatActivity implements View.OnClickListener {

    Session session;
    String path;
    Dialog dialog;
    private static final String IMAGE_DIRECTORY = "/demonuts";
    Button btn_edit_profile_submit, btn_ProfileCencel;
    Button btn_change_pass;
    EditText et_old_pass, et_new_pass, et_new_pass2;
    EditText et_edit_client_location,
            et_name, et_email, et_phone_edit_client, et_age, et_family_bio, et_family_members;
    CircleImageView edit_profile_image_client;
    TextView tv_change_password_client,tv_created_date, tv_hiden_password_client;
    String imagepath;
    File myFile;
    String userId, username, image, location, mobile,date, email, age,gender,familyInfo,familyMember;
    ImageView iv_edit;
    private int GALLERY = 1, CAMERA = 2;
    String oldPass, newPass, newPass2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_client);

        Toolbar toolbar = findViewById(R.id.toolbar_edit_profile);
        btn_edit_profile_submit = findViewById(R.id.btn_edit_profile_submit);
        btn_ProfileCencel = findViewById(R.id.btn_profile_cencel);
        edit_profile_image_client = findViewById(R.id.edit_profile_image_client);
        et_edit_client_location = findViewById(R.id.et_edit_client_location);
        et_name = findViewById(R.id.et_name);
        et_email = findViewById(R.id.et_email);
        iv_edit = findViewById(R.id.iv_edit);
        et_age = findViewById(R.id.et_age);
        et_phone_edit_client = findViewById(R.id.et_phone_edit_client);
        tv_created_date = findViewById(R.id.tv_created_date);
        et_family_bio = findViewById(R.id.et_family_bio);
        et_family_members = findViewById(R.id.et_family_members);
        tv_change_password_client = findViewById(R.id.tv_change_password_client);
        tv_hiden_password_client = findViewById(R.id.tv_hiden_password_client);



        //setSupportActionBar(toolbar);

        toolbar.setTitle("Edit Profile");
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_backspace_black_24dp);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        session = new Session(this);
        userId = session.getUser().id;
        location = session.getUser().location;
        mobile = session.getUser().mobile;
        date = session.getUser().date;
        email = session.getUser().email;
        age = session.getUser().age;
        gender = session.getUser().gender;
        familyInfo = session.getUser().family_info;
        familyMember = session.getUser().famly_member;

        if (session != null) {
            username = session.getUser().username;
            image = session.getUser().image;

        }

        tv_hiden_password_client.setText(Html.fromHtml("<u>******</u>"));

        btn_edit_profile_submit.setOnClickListener(this);
        btn_ProfileCencel.setOnClickListener(this);
        tv_change_password_client.setOnClickListener(this);
        tv_hiden_password_client.setOnClickListener(this);


        et_edit_client_location.setText(location);
        et_phone_edit_client.setText(mobile);
        et_email.setText(email);
        tv_created_date.setText(date);
        et_age.setText(age);
        et_family_bio.setText(familyInfo);
        et_family_members.setText(familyMember);


        if (session != null) {

            if (!session.getUser().username.equalsIgnoreCase("") && session.getUser().username != null) {
                et_name.setText(username);
            }

            if (session.getUser().image != null && !session.getUser().image.isEmpty()) {
                Picasso.with(this).load(URL_ClientImage+image)
                        .placeholder(R.drawable.doctor)
                        .into(edit_profile_image_client);
            } else {
                Picasso.with(this).load(R.drawable.doctor)
                        .placeholder(R.drawable.doctor)
                        .into(edit_profile_image_client);
            }

        }

        iv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final PickImageDialog dialog = PickImageDialog.build(new PickSetup());
                dialog.setOnPickCancel(new IPickCancel() {
                    @Override
                    public void onCancelClick() {
                        dialog.dismiss();
                    }
                }).setOnPickResult(new IPickResult() {
                    @Override
                    public void onPickResult(PickResult r) {

                        if (r.getError() == null) {
                            path = r.getPath();
                            edit_profile_image_client.setImageBitmap(r.getBitmap());
                        } else {
                            //Handle possible errors
                            //TODO: do what you have to do with r.getError();Toast.makeText(getActivity(), r.getError().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }

                }).show(ActivityEditProfileClient.this);
                // showPictureDialog();
            }
        });


    }


    //open dialog box for change password===
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

    private void checkValidationPass() {

        String oldPass = et_old_pass.getText().toString().trim();
        String pass = et_new_pass.getText().toString().trim();
        String repass = et_new_pass2.getText().toString().trim();

        Validation validation = new Validation(this);


        if (!validation.isValidPassword(oldPass)) {
            ToastClass.showToast(ActivityEditProfileClient.this, getString(R.string.password_null));
            et_old_pass.requestFocus();
        } else if (!validation.isValidPassword(pass)) {
            ToastClass.showToast(ActivityEditProfileClient.this, getString(R.string.password_null));
            et_new_pass.requestFocus();
        } else if (validation.isConfirmPassword(pass, repass)) {
            ToastClass.showToast(ActivityEditProfileClient.this, getString(R.string.cpassword_v));
            et_new_pass2.requestFocus();
        } else {

            if (NetworkUtil.isNetworkConnected(ActivityEditProfileClient.this)) {
                try {
                    String url = API.BASE_URL + "changeUserPassword";
                    changePassApi(url);
                } catch (NullPointerException e) {
                    ToastClass.showToast(ActivityEditProfileClient.this, getString(R.string.too_slow));
                }
            } else {
                ToastClass.showToast(ActivityEditProfileClient.this, getString(R.string.no_internet_access));
            }

        }
    }


    //Validation==
    private void checkValidation() {
        String location = et_edit_client_location.getText().toString();
        String familybio = et_family_bio.getText().toString();
        String name = et_name.getText().toString();
        String phone = et_phone_edit_client.getText().toString();
        String familymembers = et_family_members.getText().toString();

        Validation validation = new Validation(this);

        if (!validation.isEmpty(location)) {
            ToastClass.showToast(this, getString(R.string.location_v));
            et_edit_client_location.requestFocus();

        } else if (!validation.isEmpty(name)) {
            ToastClass.showToast(this, getString(R.string.fullname_v));
            et_name.requestFocus();

        } else if (!validation.isEmpty(phone)) {
            ToastClass.showToast(this, getString(R.string.contact_v));
            et_phone_edit_client.requestFocus();

        } else if (!validation.isEmpty(familybio)) {
            ToastClass.showToast(this, getString(R.string.familybio_v));
            et_family_bio.requestFocus();

        } else if (!validation.isEmpty(familymembers)) {
            ToastClass.showToast(this, getString(R.string.familymember_v));
            et_family_members.requestFocus();

        } else {


            if (NetworkUtil.isNetworkConnected(this)) {
                try {
                    String url = API.BASE_URL + "Edit_User_Profile";
                    editProfileClient(url);
                    Log.e("Edit Profile Client = ", url);
                } catch (NullPointerException e) {
                    ToastClass.showToast(this, getString(R.string.too_slow));
                }
            } else {
                ToastClass.showToast(this, getString(R.string.no_internet_access));
            }
        }
    }


    private void editProfileClient(String url) {
        Utils.showDialog(ActivityEditProfileClient.this, "Loading Please Wait...");
        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, url, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                String resultResponse = new String(response.data);
                Utils.dismissDialog();
                try {
                    JSONObject jsonObject1 = new JSONObject(resultResponse);
                    Log.e("editClient Res", resultResponse);
                    String msg = jsonObject1.getString("msg");
                    String result = jsonObject1.getString("result");
                    if (result.equalsIgnoreCase("true")) {


                        JSONArray jsonArray = jsonObject1.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject job = jsonArray.getJSONObject(i);
                            UserInfoData user = new UserInfoData();

                            user.id = job.getString("user_id");
                            user.username = job.getString("user_name");
                            //user.cat_id = job.getString("cat_id");
                            user.email = job.getString("user_email");
                            user.password = job.getString("user_pass");
                            user.address = job.getString("user_address");
                            user.age = job.getString("user_age");
                            user.mobile = job.getString("user_mobile");
                            user.image = job.getString("user_image");
                            user.date = job.getString("created_date");
                            user.city = job.getString("city");
                            user.state = job.getString("state");
                            user.social_id = job.getString("auth_id");
                            user.register_id = job.getString("fcm_id");
                            user.doller_rate = job.getString("doller_rate");
                            user.lat = job.getString("lat");
                            user.lng = job.getString("lng");
                            user.location = job.getString("location");
                            user.type = job.getString("type");

                            session.createSession(user);
                            ToastClass.showToast(ActivityEditProfileClient.this, msg);

                            Intent intent = new Intent(ActivityEditProfileClient.this, MainActivity.class);
                            startActivity(intent);
                        }
                    } else if (jsonObject1.getString("result") != ("true")) {
                        Toast.makeText(ActivityEditProfileClient.this, "item_in_message", Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse networkResponse = error.networkResponse;
                String errorMessage = "Unknown error";
                if (networkResponse == null) {
                    if (error.getClass().equals(TimeoutError.class)) {
                        errorMessage = "Request timeout";
                    } else if (error.getClass().equals(NoConnectionError.class)) {
                        errorMessage = "Failed to connect server";
                    }
                } else {
                    String result = new String(networkResponse.data);
                    try {
                        JSONObject response = new JSONObject(result);
                        String status = response.getString("status");
                        String message = response.getString("item_in_message");
                        Log.e("Error Status", status);
                        Log.e("Error Message_new", message);

                        if (networkResponse.statusCode == 404) {
                            errorMessage = "Resource not found";
                        } else if (networkResponse.statusCode == 401) {
                            errorMessage = message + "Please login again";
                        } else if (networkResponse.statusCode == 400) {
                            errorMessage = message + "Check your inputs";
                        } else if (networkResponse.statusCode == 500) {
                            errorMessage = message + "Something is getting wrong";
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                Log.i("Error", errorMessage);
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                //Log.e("tag", "getParams: " + imagepath);
                Map<String, String> params = new HashMap<String, String>();

                params.put("user_id", userId);
                params.put("name", et_name.getText().toString());
                params.put("email", et_email.getText().toString());
                params.put("mobile", et_phone_edit_client.getText().toString());
                params.put("user_age", et_age.getText().toString());
                params.put("location", et_edit_client_location.getText().toString());
                params.put("family_info", et_family_bio.getText().toString());
                params.put("famly_member", et_family_members.getText().toString());
                params.put("user_image", path);

                // Log.e("tag", "getParams: " + params.put("product_img", String.valueOf(path)));

                return params;
            }

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                try {

                    Bitmap bitmap = ((BitmapDrawable) edit_profile_image_client.getDrawable()).getBitmap();
                    Log.e("Image_post", "Image_post" + bitmap);
                    if (bitmap != null)
                        params.put("user_image", new DataPart(System.currentTimeMillis() + "Image_event.png", AppHelper.getFileDataFromDrawable(bitmap), "image/png"));

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return params;
            }
        };
        //multipartRequest.setShouldCache(false);
        RequestQueue requestQueue = Volley.newRequestQueue(ActivityEditProfileClient.this);
        multipartRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 20, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(multipartRequest);

    }


    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.btn_edit_profile_submit:
                checkValidation();
                break;
            case R.id.tv_change_password_client:
                openChangePassDialog();
                break;
            case R.id.tv_hiden_password_client:
                openChangePassDialog();
                break;
            case R.id.btn_profile_cencel:
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

        Utils.showDialog(ActivityEditProfileClient.this, "Loading Please Wait...");
        AndroidNetworking.post(url)
                .addBodyParameter("user_id", userId)
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
                                ToastClass.showToast(ActivityEditProfileClient.this, msg);
                            } else {
                                Utils.openAlertDialog(ActivityEditProfileClient.this, msg);
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

}

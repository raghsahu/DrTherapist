package com.example.drtherapist.therapist.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.drtherapist.R;
import com.example.drtherapist.client.activity.MainActivity;
import com.example.drtherapist.client.model.UserInfoData;
import com.example.drtherapist.common.BasicActivities.SignUpActivity;
import com.example.drtherapist.common.Utils.GPSTracker;
import com.example.drtherapist.common.Utils.NetworkUtil;
import com.example.drtherapist.common.Utils.ToastClass;
import com.example.drtherapist.common.Utils.Utils;
import com.example.drtherapist.common.Utils.Validation;
import com.example.drtherapist.common.remote.API;
import com.example.drtherapist.common.remote.Session;
import com.example.drtherapist.therapist.activity.ActivityNavigationDr;
import com.example.drtherapist.therapist.model.CategoryListData;
import com.facebook.CallbackManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TherapistLoginFragment extends Fragment implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {
    public static GoogleApiClient mGoogleApiClient;
    private Button btn_login, btn_forgot_submit, btn_login_google, btn_fb;
    private TextView tv_SignUp_here_ther, tv_forgot_pass;
    private Context context;
    private EditText et_email, et_password, et_forgot_email;
    private Session session;
    private View view;
    private CallbackManager callbackManager;
    private GoogleSignInOptions gso;
    private int RC_SIGN_IN = 100;
    private String social_name = "", social_id = "", social_email = "", social_img = "";
    private List<CategoryListData> catagoryList;
    private String cat_url;
    Dialog dialog;
    Spinner spinner_category;
    String cat_id;
    String url;
    String fcm_token;

    private FirebaseAuth auth;

    //For Location==
    GPSTracker tracker;
    double longitude, latitude;
    private String address1, address2, city, state, country, county, PIN, postalCode, knownName;
    public static int APP_REQUEST_CODE = 99;
    String address = "";

    public TherapistLoginFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_therapist_login, container, false);
        tracker = new GPSTracker(context);

         fcm_token=FirebaseInstanceId.getInstance().getToken();
        Log.e("tokenLogin= ", "" + FirebaseInstanceId.getInstance().getToken());

        if (tracker.canGetLocation()) {
            latitude = tracker.getLatitude();
            longitude = tracker.getLongitude();
            Log.e("login_lat_therap", " " + tracker.getLatitude());
            Log.e("login_long_therap", " " + tracker.getLongitude());

            if ((latitude !=0.0)&&longitude !=0.0){
                address = getAddress(latitude, longitude);
                Log.e("Address ", " " + getAddress(latitude, longitude));
            }else {
                Toast.makeText(context, "Location not found", Toast.LENGTH_SHORT).show();
            }
        }

        session = new Session(context);
        auth = FirebaseAuth.getInstance();
        catagoryList = new ArrayList<>();
        initView();
        clickListner();

        return view;
    }

    private void initView() {
        btn_login = view.findViewById(R.id.btn_login);
        btn_login_google = view.findViewById(R.id.btn_login_google);
        tv_SignUp_here_ther = view.findViewById(R.id.tv_SignUp_here_ther);
        tv_forgot_pass = view.findViewById(R.id.tv_forgot_pass);
        et_email = view.findViewById(R.id.et_email);
        et_password = view.findViewById(R.id.et_password);


        /////////////////////////////////// google /////////////////////////////////////////////////

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        //Initializing google api client
        /*mGoogleApiClient = new GoogleApiClient.Builder(context)
                .enableAutoManage((FragmentActivity) context, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();*/

        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .enableAutoManage(getActivity(), 1, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        ////////////////////////////////////////google end ////////////////////////////////////////////
    }

    private void clickListner() {
        tv_SignUp_here_ther.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        btn_login_google.setOnClickListener(this);
        tv_forgot_pass.setOnClickListener(this);

    }

//    @Override
//    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//        category_id = catagoryList.get(position).id;
//        Log.e("select specialization =", category_id + "name = " + catagoryList.get(position).name);
//        //Toast.makeText(context,country[position] , Toast.LENGTH_LONG).show();
//    }
//    @Override
//    public void onNothingSelected(AdapterView<?> parent) {
//    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.btn_login:
                checkValidation();
                /*intent = new Intent(context, ActivityNavigationDr.class);
                startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                getActivity().finish();*/
                break;
            case R.id.tv_SignUp_here_ther:
                intent = new Intent(context, SignUpActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_forgot_pass:
                openForgotDialog();
                break;
            case R.id.btn_login_google:
                LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
                boolean gps_enabled = false;
                boolean network_enabled = false;

                try {
                    gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
                } catch (Exception ex) {
                }

                try {
                    network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
                } catch (Exception ex) {
                }

                if (!gps_enabled && !network_enabled) {
                    // notify user
                    new AlertDialog.Builder(context)
                            .setTitle("Location Enable")
                            .setMessage("Are you sure you want to Location Enable?")

                            // Specifying a listener allows you to take an action before dismissing the dialog.
                            // The dialog is automatically dismissed when a dialog button is clicked.
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // Continue with delete operation
                                    context.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                                }
                            })

                            // A null listener allows the button to dismiss the dialog and take no further action.
                            .setNegativeButton(android.R.string.no, null)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();

                } else {
                    googlesignin();
                }


                break;
            case R.id.btn_forgot_submit:

                Utils.hideSoftKeyboard(v);
                if (NetworkUtil.isNetworkConnected(context)) {
                    try {
                        String email = et_forgot_email.getText().toString();
                        Validation validation = new Validation(context);
                        if (!validation.isValidEmail(email)) {
                            ToastClass.showToast(context, getString(R.string.email_v));
                            et_forgot_email.requestFocus();

                        } else {
                            String url = API.BASE_URL + "Forgot_Password";
                            //Log.e("Forgot the_URL = ", url);
                            callForgotPassApi(url);
                            // ToastClass.showToast(context, "forgot success ....");
                            //sendEmailToServer(url);
                        }

                    } catch (NullPointerException e) {
                        ToastClass.showToast(context, getString(R.string.too_slow));
                    }
                } else {
                    ToastClass.showToast(context, getString(R.string.no_internet_access));
                }
                break;
        }
    }


    private void callForgotPassApi(String url) {

        //mdialog = Utils.showProgress(ActivityLogin.this);
        Utils.showDialog(context, "Loading Please Wait...");
        AndroidNetworking.post(url)
                .addBodyParameter("email", et_forgot_email.getText().toString().trim())
                .setTag("Forgot Password")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        //Utils.hideProgress(mdialog);
                        Utils.dismissDialog();
                        Log.e("Forgot resp = ", "" + jsonObject);
                        try {

                            //JSONObject jsonObject = new JSONObject(response);
                            String message = jsonObject.getString("msg");
                            String result = jsonObject.getString("result");

                            if (result.equalsIgnoreCase("true")) {

                                Toast.makeText(context, "Password Send Successfully", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();

                            } else {
                                //ToastClass.showToast(ActivityLogin.this, result);
                                Utils.openAlertDialog(context, message);
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


    //;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;; start google login ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
    private void getcategoryList(String cat_url) {
        Utils.showDialog(context, "Loading Please Wait...");
        AndroidNetworking.get(cat_url)
                /*.addPathParameter("pageNumber", "0")
                .addQueryParameter("limit", "3")
                .addHeaders("token", "1234")*/
                .setTag("category list")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Utils.dismissDialog();
                        try {
                            String message = jsonObject.getString("msg");
                            String result = jsonObject.getString("result");
                            catagoryList.clear();
                            //catagoryList.add(new CategoryListData(context.getString(R.string.select_category)));

                            if (result.equalsIgnoreCase("true")) {
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject job = jsonArray.getJSONObject(i);
                                    CategoryListData catData = new CategoryListData();
                                    catData.id = job.getString("cat_id");
                                    catData.name = job.getString("cate_name");
                                    catData.image = job.getString("cat_img");
                                    catagoryList.add(catData);
                                }
                            } else ToastClass.showToast(context, message);

                            //Setting the ArrayAdapter data on the Spinner
                            ArrayAdapter<CategoryListData> aa = new ArrayAdapter<CategoryListData>(getContext(), android.R.layout.simple_spinner_item, catagoryList) {
                                //ArrayAdapter aa = new ArrayAdapter(context, android.R.layout.simple_spinner_item, catagoryList) {
                                //set hint data in center_vertical on spinner
                                public View getView(int position, @Nullable View cView, @NonNull ViewGroup parent) {
                                    View view = super.getView(position, cView, parent);
                                    view.setPadding(0, view.getTop(), view.getRight(), view.getBottom());
                                    return view;
                                }
                            };
                            aa.setDropDownViewResource(R.layout.list_item_spinner);
                            //Setting the ArrayAdapter data on the Spinner
                            spinner_category.setAdapter(aa);

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

    private void googlesignin() {
        Utils.showDialog(context, "Loading Please Wait...");
        if (NetworkUtil.isNetworkConnected(context)) {
            try {
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent, RC_SIGN_IN);
            } catch (NullPointerException e) {
                Utils.dismissDialog();
                ToastClass.showToast(context, getString(R.string.too_slow));
            }
        } else {
            Utils.dismissDialog();
            ToastClass.showToast(context, getString(R.string.no_internet_access));
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //callback for fb
        // callbackManager.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            // Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

            result.getStatus().getStatusCode();
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        //If the login succeed
        if (result.isSuccess()) {
            //Getting google account
            GoogleSignInAccount acct = result.getSignInAccount();
            //firebaseAuthWithGoogle(acct);

            assert acct != null;
            social_name = acct.getDisplayName();
            social_email = acct.getEmail();

            if (acct.getPhotoUrl() != null) {
                social_img = acct.getPhotoUrl().toString();
            } else {
                social_img = "";
            }
            Log.e("social_img ", " " + social_img);
            social_id = acct.getId();

            acct.getId();
            Log.e("GoogleResult", social_id + "------" + social_name + "------" + social_email);

            if (NetworkUtil.isNetworkConnected(context)) {
                try {
                    //show dialog box for select category=====

                    final Dialog dialog = new Dialog(context, R.style.DialogTheme);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.spinner_dialog);
                    Button btn_submit = dialog.findViewById(R.id.btn_submit);
                    spinner_category = dialog.findViewById(R.id.spinner_category);


                    if (NetworkUtil.isNetworkConnected(context)) {
                        try {
                            cat_url = API.BASE_URL + "getCategory";
                            Log.e("Category  URL signup = ", cat_url);
                            getcategoryList(cat_url);
                        } catch (NullPointerException e) {
                            ToastClass.showToast(context, getString(R.string.too_slow));
                        }
                    } else {
                        ToastClass.showToast(context, getString(R.string.no_internet_access));
                    }

                    spinner_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            cat_id = catagoryList.get(position).id;
                            Log.e("catid = ", cat_id);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }


                    });

                    // if button is clicked, close the custom dialog
                    btn_submit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            url = API.BASE_URL + "Google_Login";
                            Utils.showDialog(context, "Loading Please Wait...");

                            auth.createUserWithEmailAndPassword(social_email, "123456789")
                                    .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {

                                            if (!task.isSuccessful()) {
                                                Log.e("firebase ", "Firebase failed" + task.getException());

                                                auth.signInWithEmailAndPassword(social_email, "123456789")
                                                        .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<AuthResult> task) {

                                                                if (!task.isSuccessful()) {
                                                                    Utils.dismissDialog();
                                                                    // there was an error

                                                                } else {
                                                                    SocialLoginApi(url);
                                                                }
                                                            }
                                                        });

                                            } else {
                                                SocialLoginApi(url);

                                            }
                                        }
                                    });


                            dialog.dismiss();

                        }
                    });

                    dialog.show();

                } catch (NullPointerException e) {
                    ToastClass.showToast(context, getString(R.string.too_slow));
                }
            } else {
                ToastClass.showToast(context, getString(R.string.no_internet_access));
            }
        } else {
            try {
                Utils.dismissDialog();
            }catch (Exception e){

            }

            //If login fails
            Toast.makeText(context, "Login Failed", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        ToastClass.showToast(context, "" + connectionResult);
    }

    //;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;; end google login ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


    private void checkValidation() {

        String email = et_email.getText().toString();
        String pass = et_password.getText().toString();

        Validation validation = new Validation(context);

        if (!validation.isValidEmail(email)) {
            ToastClass.showToast(context, getString(R.string.email_v));
            et_email.requestFocus();

        } else if (!validation.isValidPassword(pass)) {
            ToastClass.showToast(context, getString(R.string.password_v));
            et_password.requestFocus();
        } else {

            if (NetworkUtil.isNetworkConnected(context)) {
                try {
                    String url = API.BASE_URL + "Login";
                    CallLoginApi(url);
                } catch (NullPointerException e) {
                    ToastClass.showToast(context, getString(R.string.too_slow));
                }
            } else {
                ToastClass.showToast(context, getString(R.string.no_internet_access));
            }
        }

    }

    private void CallLoginApi(String url) {

        Utils.showDialog(context, "Loading Please Wait...");
        AndroidNetworking.post(url)
                .addBodyParameter("email", et_email.getText().toString().trim())
                .addBodyParameter("password", et_password.getText().toString().trim())
                .addBodyParameter("fcm_id", fcm_token)
                .addBodyParameter("lat", "")
                .addBodyParameter("lng", "")
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
                        Log.e("Login_res_ther = ", "" + jsonObject);
                        try {

                            //JSONObject jsonObject = new JSONObject(response);
                            // String status = jsonObject.getString("is_complete_profile");
                            String msg = jsonObject.getString("msg");
                            String result = jsonObject.getString("result");

                            if (result.equalsIgnoreCase("true")) {
                                Log.e("Login response = ", " " + jsonObject);
                                JSONObject job = jsonObject.getJSONObject("data");
                                UserInfoData user = new UserInfoData();


                                user.id = job.getString("dr_id");
                                user.cat_id = job.getString("cat_id");
                                user.username = job.getString("dr_fname");
                                user.mobile = job.getString("dr_contact");
                                user.email = job.getString("dr_email");
                                user.password = job.getString("dr_pass");
                                user.address = job.getString("dr_address");
                                user.image = job.getString("dr_image");
                                user.availability = job.getString("dr_availability");
                                user.timing = job.getString("timing");
                                user.fee = job.getString("fee");
                                user.specialization = job.getString("specialization");
                                user.lat = job.getString("lat");
                                user.lng = job.getString("lng");
                                user.location = job.getString("location");
                                user.experience = job.getString("experience");
                                user.review = job.getString("review");
                                user.resume = job.getString("resume");
                                user.age = job.getString("age");
                                user.date = job.getString("created_date");
                                user.dob = job.getString("dob");
                                user.city = job.getString("city");
                                user.state = job.getString("state");
                                user.zip = job.getString("zip");
                                user.social_id = job.getString("auth_id");
                                user.register_id = job.getString("fcm_id");
                                user.rating = job.getString("rating");
                                user.doller_rate = job.getString("doller_rate");
                                user.type = job.getString("type");
                                user.gender = job.getString("gender");
                                session.createSession(user);

                                //ToastClass.showToast(context, msg);
                                String emailId = job.getString("dr_email");
                                auth.signInWithEmailAndPassword(emailId, "123456789")
                                        .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                // If sign in fails, display a item_in_message to the user. If sign in succeeds
                                                // the auth state listener will be notified and logic to handle the
                                                // signed in user can be handled in the listener.
                                                //progressBar.setVisibility(View.GONE);
                                                if (!task.isSuccessful()) {
                                                    // there was an error

                                                    Utils.dismissDialog();
//                                                    if (password.length() < 6) {
//                                                        inputPassword.setError(getString(R.string.minimum_password));
//                                                    } else {
//                                                        Toast.makeText(context, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
//                                                    }
                                                } else {
                                                    Utils.dismissDialog();
                                                    Intent intent = new Intent(context, ActivityNavigationDr.class);
                                                    startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                                                    getActivity().finish();
                                                }
                                            }
                                        });


                            } else {
                                Utils.openAlertDialog(context, msg);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        Utils.dismissDialog();
                    }
                });

    }

    private void openForgotDialog() {
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.forgot_dialog);
        et_forgot_email = dialog.findViewById(R.id.et_forgot_email);
        btn_forgot_submit = dialog.findViewById(R.id.btn_forgot_submit);

        btn_forgot_submit.setOnClickListener(this);

        dialog.show();
    }


    public String getAddress(double lat, double lng) {
        Geocoder geocoder;
        List<Address> addresses = new ArrayList<>();

        geocoder = new Geocoder(getActivity(), Locale.ENGLISH);

        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()

            city = addresses.get(0).getLocality();
            state = addresses.get(0).getAdminArea();
            country = addresses.get(0).getCountryName();
            postalCode = addresses.get(0).getPostalCode();
            knownName = addresses.get(0).getFeatureName();
            Log.e("fullAddress", "" + address);
            Log.e("city", "" + city);
            Log.e("state", "" + state);
            Log.e("country", "" + country);
            Log.e("postalCode", "" + postalCode);
            Log.e("knownName", "" + knownName);// Only if available else return NULL
        }catch (Exception e) {
            e.printStackTrace();
        }

        return address;
    }

    private void SocialLoginApi(String url) {
        Log.e("socil url", "" + url);
        //Utils.showDialog(context, "Loading Please Wait...");
        AndroidNetworking.post(url)
                .addBodyParameter("name", social_name)
                .addBodyParameter("cat_id", cat_id)
                .addBodyParameter("dr_image", "mahesh.jpg")
                .addBodyParameter("email", social_email)
                .addBodyParameter("auth_id", social_id)
                .addBodyParameter("lat", String.valueOf(latitude))
                .addBodyParameter("lng", String.valueOf(longitude))
                .addBodyParameter("location", address)
                .addBodyParameter("fcm_id", fcm_token)
                .setTag("userLogin")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Log.e("dr google res = ", "" + jsonObject);
                        Utils.dismissDialog();
                        try {

                            String msg = jsonObject.getString("msg");
                            String result = jsonObject.getString("result");

                            if (result.equalsIgnoreCase("true")) {


                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject job = jsonArray.getJSONObject(i);

                                    UserInfoData user = new UserInfoData();
                                    user.id = job.getString("dr_id");
                                    user.cat_id = job.getString("cat_id");
                                    user.username = job.getString("dr_fname");
                                    user.mobile = job.getString("dr_contact");
                                    user.email = job.getString("dr_email");
                                    user.password = job.getString("dr_pass");
                                    user.address = job.getString("dr_address");
                                    user.image = job.getString("dr_image");
                                    user.availability = job.getString("dr_availability");
                                    user.timing = job.getString("timing");
                                    user.fee = job.getString("fee");
                                    user.specialization = job.getString("specialization");
                                    user.lat = job.getString("lat");
                                    user.lng = job.getString("lng");
                                    user.location = job.getString("location");
                                    user.experience = job.getString("experience");
                                    user.review = job.getString("review");
                                    user.resume = job.getString("resume");
                                    user.age = job.getString("age");
                                    user.date = job.getString("created_date");
                                    user.dob = job.getString("dob");
                                    user.city = job.getString("city");
                                    user.state = job.getString("state");
                                    user.zip = job.getString("zip");
                                    user.social_id = job.getString("auth_id");
                                    user.register_id = job.getString("fcm_id");
                                    user.rating = job.getString("rating");
                                    user.doller_rate = job.getString("doller_rate");
                                    user.type = job.getString("type");
                                    user.gender = job.getString("gender");

                                    session.createSession(user);

                                    // ToastClass.showToast(context, msg);
                                    Intent intent = new Intent(context, ActivityNavigationDr.class);
                                    startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                                    getActivity().finish();

                                }
                            } else {
                                //ToastClass.showToast(ActivityLogin.this, result);
                                Utils.openAlertDialog(context, msg);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        Utils.dismissDialog();
                        ToastClass.showToast(context, "" + error);
                    }
                });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }


    @Override
    public void onPause() {
        super.onPause();

    }
}

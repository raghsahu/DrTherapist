package com.example.drtherapist.client.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
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
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
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
import com.example.drtherapist.therapist.fragment.TherapistLoginFragment;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class ClientLoginFragment extends Fragment implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {

    public static GoogleApiClient mGoogleApiClient;
    private Button btn_login, btn_forgot_submit, btn_login_google, btn_fb;
    private Context context;
    private TextView tv_SignUp_here_client, tv_client_forgot_pass;
    private EditText et_forgot_email;
    private EditText et_email, et_password;
    private Session session;
    private View view;
    String emailId;
    String urllogin;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private GoogleSignInOptions gso;
    private int RC_SIGN_IN = 100;
    private String social_name = "", social_id = "", social_email = "", social_img = "";
    private File imgFile;
    Dialog dialog;
    String url;
    String fcm_token;
    private FirebaseAuth auth;
    //For Location==
    GPSTracker tracker;
    double longitude, latitude;
    private String address1, address2, city, state, country, county, PIN, postalCode, knownName;
    public static int APP_REQUEST_CODE = 99;
    String address = "";

    private LoginManager mLoginManager;
    private CallbackManager callbackManager;
    private AccessTokenTracker mAccessTokenTracker;

    public ClientLoginFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_client_login, container, false);
        fcm_token = FirebaseInstanceId.getInstance().getToken();
        Log.e("userloginToken", "" + fcm_token);
        session = new Session(context);
        auth = FirebaseAuth.getInstance();

        initView();
        clickListner();

        tracker = new GPSTracker(context);
        if (tracker.canGetLocation()) {
            latitude = tracker.getLatitude();
            longitude = tracker.getLongitude();
            Log.e("current_lat_login", " " + tracker.getLatitude());
            Log.e("current_long_login", " " + tracker.getLongitude());

            if ((latitude !=0.0)&&longitude !=0.0){
                address = getAddress(latitude, longitude);
                Log.e("Address ", " " + getAddress(latitude, longitude));
            }else {
                Toast.makeText(context, "Location not found", Toast.LENGTH_SHORT).show();
            }
        }

        return view;
    }

    private void initView() {
        btn_login_google = view.findViewById(R.id.btn_login_google);
        btn_fb = view.findViewById(R.id.btn_fb);
        btn_login = view.findViewById(R.id.btn_login);
        tv_client_forgot_pass = view.findViewById(R.id.tv_client_forgot_pass);
        tv_SignUp_here_client = view.findViewById(R.id.tv_SignUp_here_client);
        et_email = view.findViewById(R.id.et_email);
        et_password = view.findViewById(R.id.et_password);

        // mAuth = FirebaseAuth.getInstance();

        FacebookSdk.sdkInitialize(context);
        //callbackManager = CallbackManager.Factory.create();

        // Facebook
        //setupFacebook();

        /////////////////////////////////// google /////////////////////////////////////////////////

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(context.getResources().getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        //Initializing google api client
        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .enableAutoManage((FragmentActivity) context, 0, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

       /* mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .enableAutoManage(getActivity(), 0, this  )
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();*/

        ////////////////////////////////////////google end ////////////////////////////////////////////
    }

    private void clickListner() {
        btn_login_google.setOnClickListener(this);
        btn_fb.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        tv_SignUp_here_client.setOnClickListener(this);
        tv_client_forgot_pass.setOnClickListener(this);
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


        try{
            address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()

            city = addresses.get(0).getLocality();
            state = addresses.get(0).getAdminArea();
            country = addresses.get(0).getCountryName();
            postalCode = addresses.get(0).getPostalCode();
            knownName = addresses.get(0).getFeatureName();
            Log.e("fullAddressC", "" + address);
            Log.e("city", "" + city);
            Log.e("state", "" + state);
            Log.e("country", "" + country);
            Log.e("postalCode", "" + postalCode);
            Log.e("knownName", "" + knownName);// Only if available else return NULL
        }catch (Exception e){
            Log.e("error_ex",""+e.toString());
        }

        return address;
    }


    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.btn_login:
                checkValidation();
                /*intent = new Intent(context, MainActivity.class);
                startActivity(intent);*/
                break;
            case R.id.tv_SignUp_here_client:
                intent = new Intent(context, SignUpActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_client_forgot_pass:
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
                    Utils.showDialog(context, "Loading Please Wait...");
                }


                break;
            case R.id.btn_fb:
                mAccessTokenTracker.startTracking();
                mLoginManager.logInWithReadPermissions((Activity) context, Arrays.asList("public_profile", "email", "user_birthday"));
                break;
            case R.id.btn_forgot_submit:

                Utils.hideSoftKeyboard(v);
                if (NetworkUtil.isNetworkConnected(context)) {
                    try {
                        String email = et_forgot_email.getText().toString();
                        String url = API.BASE_URL + "forgotpassword";
                        Log.e("Forgot clientURL = ", url);
                        forgotPassApiClient(url);
                    } catch (NullPointerException e) {
                        ToastClass.showToast(context, getString(R.string.too_slow));
                    }
                } else {
                    ToastClass.showToast(context, getString(R.string.no_internet_access));
                }
                break;
        }
    }


    private void forgotPassApiClient(String url) {

        //mdialog = Utils.showProgress(ActivityLogin.this);
        Utils.showDialog(context, "Loading Please Wait...");
        AndroidNetworking.post(url)
                .addBodyParameter("user_email", et_forgot_email.getText().toString().trim())
                .setTag("Forgot Password")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        //Utils.hideProgress(mdialog);
                        Utils.dismissDialog();
                        //Log.e("Forgot resp = ", "" + jsonObject);
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

    private void googlesignin() {
        if (NetworkUtil.isNetworkConnected(context)) {
            try {
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent, RC_SIGN_IN);
            } catch (NullPointerException e) {
                ToastClass.showToast(context, getString(R.string.too_slow));
            }
        } else {
            ToastClass.showToast(context, getString(R.string.no_internet_access));
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //callback for fb
        //callbackManager.onActivityResult(requestCode, resultCode, data);

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
                //social_img = "User has not set his Profile Image";
                social_img = "";
            }
            Log.e("social_img ", " " + social_img);
            social_id = acct.getId();
            acct.getId();
            Log.e("GoogleResult client", social_id + "------" + social_name + "------" + social_email);

            url = API.BASE_URL + "Google_Login_User";
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


        } else {
            //If login fails

            Toast.makeText(context, "Login Failed", Toast.LENGTH_LONG).show();

        }
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        ToastClass.showToast(context, "" + connectionResult);
    }

    //;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;; end google login ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


    //..............................fb login ........................................

    private void setupFacebook() {
        mLoginManager = LoginManager.getInstance();
        callbackManager = CallbackManager.Factory.create();
        mAccessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                //Log.e("ERROR", error.toString());
                ToastClass.showToast(context, "oldAccessToken = " + oldAccessToken + "currentAccessToken =" + currentAccessToken);
            }
        };

        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {
                if (loginResult.getRecentlyGrantedPermissions().contains("email")) {
                    requestObjectUser(loginResult.getAccessToken());
                    //handleFacebookAccessToken(loginResult.getAccessToken());
                } else {
                    LoginManager.getInstance().logOut();
                    Toast.makeText(context, "Error permissions", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                Log.e("ERROR", error.toString());
            }
        });

        if (AccessToken.getCurrentAccessToken() != null) {
            requestObjectUser(AccessToken.getCurrentAccessToken());
            //handleFacebookAccessToken(AccessToken.getCurrentAccessToken());
        }
    }


    //fb
    private void requestObjectUser(final AccessToken accessToken) {
        GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                //if (response.getError() != null) {
                if (response == null) {
                    // handle error
                    Toast.makeText(context, "Login Failed", Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(context, MainActivity.class);
                    startActivity(intent);
                    try {
                        // loggedin = true;
                        String token = accessToken.getToken();

                        //get data from server
                        JSONObject data = response.getJSONObject();
                        Log.e("JSON FB ", "" + data);
                        if (data.has("picture")) {
                            social_img = data.getJSONObject("picture").getJSONObject("data").getString("url");

                            Log.e("fb url = ", social_img);
                        }

                        social_id = object.optString("job_id");
                        social_name = object.optString("first_name");
                        String Fname = object.optString("last_name");

                        Log.e("fb response = ", "token  = " + token + "job_id = " + object.optString("job_id") + "fname = " + object.optString("first_name") + "lname = " + object.optString("last_name"));

                        if (NetworkUtil.isNetworkConnected(context)) {
                            try {
                                String url = API.BASE_URL + "Google_Login";
                                //SocialLoginApi(url);
                            } catch (NullPointerException e) {
                                ToastClass.showToast(context, getString(R.string.too_slow));
                            }
                        } else {
                            ToastClass.showToast(context, getString(R.string.no_internet_access));
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

        });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "job_id,first_name,last_name,email,picture.type(large)");
        request.setParameters(parameters);
        request.executeAsync();
    }


    //..............................fb login ........................................

    //convert bitmap to file
    private File bitmapToFile(Bitmap bitmap) {
        try {
            String name = System.currentTimeMillis() + ".png";
            File file = new File(context.getCacheDir(), name);

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 60, bos);
            byte[] bArr = bos.toByteArray();
            bos.flush();
            bos.close();

            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bArr);
            fos.flush();
            fos.close();

            return file;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

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
                    emailId = et_email.getText().toString();
                    urllogin = API.BASE_URL + "User_Login";
                    Utils.showDialog(context, "Loading Please Wait...");
                    auth.createUserWithEmailAndPassword(emailId, "123456789")
                            .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (!task.isSuccessful()) {
                                        Log.e("firebase ", "Firebase failed" + task.getException());

                                        auth.signInWithEmailAndPassword(emailId, "123456789")
                                                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<AuthResult> task) {

                                                        if (!task.isSuccessful()) {
                                                            Utils.dismissDialog();
                                                            // there was an error
                                                            Log.e("login_firebase", ""+task.getException());
                                                            Toast.makeText(getActivity(), ""+task.getException(), Toast.LENGTH_SHORT).show();

                                                        } else {
                                                            CallLoginApi(urllogin);

                                                        }
                                                    }
                                                });

                                    } else {
                                        CallLoginApi(urllogin);
                                    }
                                }
                            });


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
                .setTag("userLogin")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Utils.dismissDialog();
                        Log.e("Login_res_client = ", "" + jsonObject);
                        try {
                            String msg = jsonObject.getString("msg");
                            String result = jsonObject.getString("result");

                            if (result.equalsIgnoreCase("true")) {
                                Log.e("Login respo client = ", " " + jsonObject);

                                JSONObject job = jsonObject.getJSONObject("data");
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
                                user.family_info = job.getString("family_info");
                                user.famly_member = job.getString("famly_member");

                                session.createSession(user);

                                Intent intent = new Intent(context, MainActivity.class);
                                startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                                getActivity().finish();
                            } else {
                                Utils.openAlertDialog(context, msg);
                            }
                        } catch (JSONException e) {
                            Utils.dismissDialog();
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        Utils.dismissDialog();
                        Log.e("login_error",""+ error.getErrorDetail());
                        Log.e("login_error", ""+error.getErrorCode());
                        Log.e("login_error", ""+error.getResponse());
                    }
                });

    }

    private void SocialLoginApi(String url) {
        AndroidNetworking.post(url)
                .addBodyParameter("name", social_name)
                //.addBodyParameter("mobile", "")
                .addBodyParameter("user_image", social_img)
                .addBodyParameter("email", social_email)
                .addBodyParameter("auth_id", social_id)
                .addBodyParameter("fcm_id", fcm_token)

                .setTag("userLogin")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Utils.dismissDialog();
                        Log.e("user resp google = ", "" + jsonObject);
                        try {

                            String msg = jsonObject.getString("msg");
                            String result = jsonObject.getString("result");

                            if (result.equalsIgnoreCase("true")) {

                                JSONArray jsonArray = jsonObject.getJSONArray("data");
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
                                    user.family_info = job.getString("family_info");
                                    user.famly_member = job.getString("famly_member");

                                    session.createSession(user);

                                    Intent intent = new Intent(context, MainActivity.class);
                                    startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                                    getActivity().finish();


                                }
                            } else {
                                Utils.openAlertDialog(context, msg);
                            }
                        } catch (JSONException e) {
                            Utils.dismissDialog();
                            e.printStackTrace();
                            ToastClass.showToast(context, e.toString());
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        Utils.dismissDialog();
                        ToastClass.showToast(context, "" + error);
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


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onPause() {
        super.onPause();
       /* mGoogleApiClient.stopAutoManage(getActivity());
        mGoogleApiClient.disconnect();*/
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mGoogleApiClient != null)
            mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.stopAutoManage((FragmentActivity) context);
            mGoogleApiClient.disconnect();
        }
    }
}

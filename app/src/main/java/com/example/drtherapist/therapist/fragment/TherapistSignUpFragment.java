package com.example.drtherapist.therapist.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
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
import com.example.drtherapist.client.fragment.ClientSignUpFragment;
import com.example.drtherapist.client.model.UserInfoData;
import com.example.drtherapist.common.BasicActivities.LoginActivity;
import com.example.drtherapist.common.Interface.UserInterface;
import com.example.drtherapist.common.Utils.AppHelper;
import com.example.drtherapist.common.Utils.GPSTracker;
import com.example.drtherapist.common.Utils.LocationAddress;
import com.example.drtherapist.common.Utils.LocationTrack;
import com.example.drtherapist.common.Utils.NetworkUtil;
import com.example.drtherapist.common.Utils.ToastClass;
import com.example.drtherapist.common.Utils.Utils;
import com.example.drtherapist.common.Utils.Validation;
import com.example.drtherapist.common.Utils.VolleyMultipartRequest;
import com.example.drtherapist.common.remote.API;
import com.example.drtherapist.common.remote.Session;
import com.example.drtherapist.therapist.activity.ActivityEditProfileTherapist;
import com.example.drtherapist.therapist.activity.ActivityNavigationDr;
import com.example.drtherapist.therapist.model.CategoryListData;
import com.example.drtherapist.therapist.model.DrSignInModel;
import com.example.drtherapist.therapist.model.Profile_Update_Model;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickCancel;
import com.vansuita.pickimage.listeners.IPickResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.location.LocationListener;
import static android.app.Activity.RESULT_OK;
import static com.example.drtherapist.common.Interface.Config.Base_Url;
import static com.facebook.FacebookSdk.getApplicationContext;


public class TherapistSignUpFragment extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener
//, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
//        com.google.android.gms.location.LocationListener
{
    private Button btnSignUpTherapist;
    private TextView btn_signup_therapist, tv_dob;
    private Spinner spinner;
    private EditText sign_name, sign_email, sign_phone, sign_password, et_address, et_exprience;
    private Context context;
    private TextView tv_resume;
    private Uri filePath;
    private Session session;
    String uploadedFileName;
    private String cat_url;
    String url;
    String uid;
    private String serverResume = "";
    private String category_id, cate_name;
    private CircleImageView img_profile, iv_edit;
    private File imgFile;
    private View view;
    private List<CategoryListData> catagoryList;
    String name, email, password, phone, exprience, DOb;
    private int mYear, mMonth, mDay;
    String path;
    String fcm_token;
    private FirebaseAuth auth;
    //For Location==
    GPSTracker tracker;
    LocationTrack locationTrack;
    double longitude, latitude;
    private String address1, address2, city, state, country, county, PIN, postalCode, knownName;
    public static int APP_REQUEST_CODE = 99;
    String address;

    Uri selectedPdfUri;
    File pdfFile;
    private File file;

    private GoogleApiClient mGoogleApiClient;
    private Location mLocation;
     LocationListener listener;
    private LocationRequest mLocationRequest;
    private LocationManager locationManager;

    private long UPDATE_INTERVAL = 5 * 1000;
    private long FASTEST_INTERVAL = 5000;
    private com.google.android.gms.location.LocationListener mLocationListener;


    public TherapistSignUpFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_therapist_sign_up, container, false);

        session = new Session(context);
        auth = FirebaseAuth.getInstance();
        fcm_token = FirebaseInstanceId.getInstance().getToken();
        initView();
        clickListner();
        tracker = new GPSTracker(context);
        locationTrack = new LocationTrack(context);
        if (tracker.canGetLocation()) {
            latitude = tracker.getLatitude();
            longitude = tracker.getLongitude();
            Log.e("sign_lat_therap", " " + tracker.getLatitude());
            Log.e("sign_long_therap", " " + tracker.getLongitude());

            if ((latitude !=0.0)&&longitude !=0.0){
                address = getAddress(latitude, longitude);
                Log.e("Address ", " " + getAddress(latitude, longitude));
            }else {
                Toast.makeText(context, "Location not found", Toast.LENGTH_SHORT).show();
            }
        }
        DOb = tv_dob.getText().toString();
        return view;
    }


    private void initView() {

        // tracker.showSettingsAlert();
        catagoryList = new ArrayList<>();
        btnSignUpTherapist = view.findViewById(R.id.btn_signup_therapist);
        btn_signup_therapist = view.findViewById(R.id.tv_login_here_ther);
        spinner = view.findViewById(R.id.spinner);
        tv_resume = view.findViewById(R.id.tv_resume);
        img_profile = view.findViewById(R.id.img_profile);
        iv_edit = view.findViewById(R.id.iv_edit);


        sign_name = view.findViewById(R.id.sign_name);
        sign_email = view.findViewById(R.id.sign_email);
        sign_phone = view.findViewById(R.id.sign_phone);
        sign_password = view.findViewById(R.id.sign_password);
        et_address = view.findViewById(R.id.et_address);
        tv_dob = view.findViewById(R.id.tv_dob);
        et_exprience = view.findViewById(R.id.et_exprience);


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
//*****************************************

//        mGoogleApiClient = new GoogleApiClient.Builder(context)
//                .addConnectionCallbacks(this)
//                .addOnConnectionFailedListener(this)
//                .addApi(LocationServices.API)
//                .build();
//
//        locationManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
//
//        checkLocation();

    }

    private void clickListner() {
        img_profile.setOnClickListener(this);
        iv_edit.setOnClickListener(this);
        tv_resume.setOnClickListener(this);
        btnSignUpTherapist.setOnClickListener(this);
        btn_signup_therapist.setOnClickListener(this);

        spinner.setOnItemSelectedListener(this);
        tv_dob.setOnClickListener(this);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        category_id = catagoryList.get(position).id;
        cate_name = catagoryList.get(position).name;

        Log.e("select specialization =", category_id + "name = " + catagoryList.get(position).name);
        //Toast.makeText(context,country[position] , Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }


    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.tv_resume:

                Intent intent1 = new Intent();
                intent1.setType("application/pdf");
                intent1.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent1, "Select Pdf"), 7);

                break;
            case R.id.btn_signup_therapist:
                name = sign_name.getText().toString();
                email = sign_email.getText().toString();
                password = sign_password.getText().toString();
                phone = sign_phone.getText().toString();
                exprience = et_exprience.getText().toString();

                checkValidation();
                break;

            case R.id.iv_edit:
                Utils.hideSoftKeyboard(v);
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
                            Log.e("path",""+path);
                            img_profile.setImageBitmap(r.getBitmap());
                        } else {
                            //Handle possible errors
                            //TODO: do what you have to do with r.getError();Toast.makeText(getActivity(), r.getError().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }

                }).show(getActivity());
                // showPictureDialog();
                //imagePick();
                break;
            case R.id.tv_dob:
                calender();
                break;

            case R.id.tv_login_here_ther:
                intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 7) {
//                Uri selectedImageUri = data.getData();
//                serverResume = selectedImageUri.getPath();
//
//                //String serverUrlAid = PathUtils.getFilePathFromContentUri(context, selectedImageUri);
//
//                Log.e("serverResume = ", serverResume);
//                File file = new File(serverResume);
//                uploadedFileName = file.getName();
//                Log.e("File name=", "File : " + file.getName());
//                if (uploadedFileName != null && !uploadedFileName.equals("")) {
//                    tv_resume.setText(uploadedFileName);
//                } else {
//                    Toast.makeText(context, "Cannot upload file to server", Toast.LENGTH_SHORT).show();
//                }
//            }
                selectedPdfUri = data.getData();
                serverResume = selectedPdfUri.getPath();
                pdfFile = new File(selectedPdfUri.getPath());
                //String serverUrlAid = PathUtils.getFilePathFromContentUri(context, selectedImageUri);
                Log.e("pdfFile = ", pdfFile.toString());
                file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(),
                        serverResume);
//                file = new File(serverResume);
                uploadedFileName = file.getName();
                Log.e("File_name=", "File : " + file.getName());
                if (uploadedFileName != null && !uploadedFileName.equals("")) {
                    tv_resume.setText(file.getPath());
                } else {
                    Toast.makeText(context, "Cannot upload file to server", Toast.LENGTH_SHORT).show();
                }



            }
        }
    }

    private void checkValidation() {
        String name = sign_name.getText().toString();
        String emailid = sign_email.getText().toString();
        String phone = sign_phone.getText().toString();
        String pass = sign_password.getText().toString();
        String add = et_address.getText().toString();
        String exp = et_exprience.getText().toString();
        String resume = tv_resume.getText().toString();

        Validation validation = new Validation(context);

        if (!validation.isEmpty(name)) {
            ToastClass.showToast(context, getString(R.string.fullname_v));
            sign_name.requestFocus();

        } else if (!validation.isValidEmail(emailid)) {
            ToastClass.showToast(context, getString(R.string.email_v));
            sign_email.requestFocus();

        } else if (!validation.isValidNo(phone)) {
            ToastClass.showToast(context, getString(R.string.contact_v));
            sign_phone.requestFocus();

        } else if (!validation.isValidPassword(pass)) {
            ToastClass.showToast(context, getString(R.string.password_v));
            sign_password.requestFocus();
        } else if (!validation.isEmpty(exp)) {
            ToastClass.showToast(context, getString(R.string.exprience));
            et_exprience.requestFocus();
        } else if (!validation.isEmpty(resume)) {
            ToastClass.showToast(context, getString(R.string.resume));
            tv_resume.requestFocus();
        }
        else if (path==null){
            Toast.makeText(context, "please upload image", Toast.LENGTH_SHORT).show();
        }
        else {
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
                if (NetworkUtil.isNetworkConnected(context)) {
                    try {
                        url = API.BASE_URL + "SignUp";
                        Utils.showDialog(context, "Loading Please Wait...");
                        auth.createUserWithEmailAndPassword(email, "123456789")
                                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        //Toast.makeText(context, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                        // progressBar.setVisibility(View.GONE);
                                        // If sign in fails, display a item_in_message to the user. If sign in succeeds
                                        // the auth state listener will be notified and logic to handle the
                                        // signed in user can be handled in the listener.

                                        if (!task.isSuccessful()) {
                                            Utils.dismissDialog();
                                            //Toast.makeText(context, "Authentication failed." + task.getException(), Toast.LENGTH_SHORT).show();
                                            Log.e("firebase ", "Firebase failed" + task.getException());
                                        } else {
                                            uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                            Log.e("firebase ", "Firebase Success");
                                            Log.e("uid ", "uid" + uid);
                                           // CallSignUpApi(url);
                                            SignupByRetrofit();
                                        }
                                    }
                                });
                        Log.e("signup url = ", url);
                    } catch (NullPointerException e) {
                        Utils.dismissDialog();
                        ToastClass.showToast(context, getString(R.string.too_slow));
                    }
                } else {
                    ToastClass.showToast(context, getString(R.string.no_internet_access));
                }
            }


        }
    }

    private void SignupByRetrofit() {

        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Processing...");
        progressDialog.show();

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(100, TimeUnit.SECONDS)
                .readTimeout(100,TimeUnit.SECONDS).build();
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Base_Url)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        UserInterface service = retrofit.create(UserInterface.class);

        File imgFile = new File(String.valueOf(path));
        File pdf_File = new File(String.valueOf(pdfFile));
        RequestBody fileBody = RequestBody.create(MediaType.parse("image/*"), imgFile);
        MultipartBody.Part body = MultipartBody.Part.createFormData("dr_image", imgFile.getName(),fileBody);

        RequestBody Pdf=RequestBody.create(MediaType.parse("application/pdf"), pdf_File.getName());
        //MultipartBody.Part pdfbody = MultipartBody.Part.createFormData("resume", pdfFile.getName(),Pdf);

        RequestBody dr_name = RequestBody.create(MediaType.parse("text/plain"), name);
        RequestBody email1 = RequestBody.create(MediaType.parse("text/plain"), email);
        RequestBody password1 = RequestBody.create(MediaType.parse("text/plain"), password);
        RequestBody mobile1 = RequestBody.create(MediaType.parse("text/plain"), phone);
        RequestBody address1 = RequestBody.create(MediaType.parse("text/plain"), address);
        RequestBody experience1 = RequestBody.create(MediaType.parse("text/plain"), exprience);
        RequestBody lat = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(latitude));
        RequestBody lng = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(longitude));
        RequestBody cat_id = RequestBody.create(MediaType.parse("text/plain"), category_id);
        RequestBody dr_unique_key = RequestBody.create(MediaType.parse("text/plain"), uid);
        RequestBody dob = RequestBody.create(MediaType.parse("text/plain"), DOb);
        RequestBody fcm_id = RequestBody.create(MediaType.parse("text/plain"), fcm_token);

        Call<DrSignInModel> call = service.dr_signIn(dr_name,email1,password1,mobile1,address1,
                experience1,lat,lng,cat_id,dr_unique_key,dob,fcm_id,body,Pdf);

        call.enqueue(new Callback<DrSignInModel>() {
            @Override
            public void onResponse(Call<DrSignInModel> call, retrofit2.Response<DrSignInModel> response) {
                progressDialog.dismiss();
                Log.e("responce_design", response.body().toString());
                if (response.body().getResult().equals("true")) {
                    Log.e("responce_drsign", response.body().getMsg());

                    Log.e("responce_drId", response.body().getData().id);
                    session.createSession(response.body().getData());
                    Intent intent = new Intent(context, ActivityNavigationDr.class);
                    startActivity(intent);
                    Toast.makeText(getActivity(), ""+response.body().getMsg(), Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getApplicationContext(), "result false", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<DrSignInModel> call, Throwable t) {
                progressDialog.dismiss();
                Log.e("retro_drsign_error", t.getMessage());
                Log.e("retrodrsign_error", t.getLocalizedMessage());
                //Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

//*************************************************************************
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

        }catch(Exception e){
            e.printStackTrace();

        }

//        Log.e("fullAddress", "" + address);
//        Log.e("city", "" + city);
//        Log.e("state", "" + state);
//        Log.e("country", "" + country);
//        Log.e("postalCode", "" + postalCode);
//        Log.e("knownName", "" + knownName);
// Only if available else return NULL
        return address;
    }


    private void CallSignUpApi(String url) {

        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, url, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                String resultResponse = new String(response.data);
                Utils.dismissDialog();
                try {
                    JSONObject jsonObject1 = new JSONObject(resultResponse);
                    Log.e("SignDr Response", resultResponse);
                    String msg = jsonObject1.getString("msg");
                    String result = jsonObject1.getString("result");
                    if (result.equalsIgnoreCase("true")) {
                        JSONObject job = jsonObject1.getJSONObject("data");
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

                        Intent intent = new Intent(context, ActivityNavigationDr.class);
                        startActivity(intent);
                        //getActivity().finish();

                    } else if (jsonObject1.getString("result") != ("true")) {
                        Utils.dismissDialog();
                        Toast.makeText(context, "result false", Toast.LENGTH_LONG).show();
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
                params.put("name", name);
                params.put("email", email);
                params.put("password", password);
                params.put("mobile", phone);
                params.put("address", address);
                params.put("experience", exprience);
                params.put("lat", String.valueOf(latitude));
                params.put("lng", String.valueOf(longitude));
                params.put("cat_id", category_id);
                params.put("dr_unique_key", uid);
                //params.put("cate_name", cate_name);
                params.put("dob", DOb);
                params.put("dr_image", path);
                params.put("fcm_id", fcm_token);
                params.put("resume", String.valueOf(serverResume));

                return params;
            }

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                try {
                    Bitmap bitmap = ((BitmapDrawable) img_profile.getDrawable()).getBitmap();
                    Log.e("Image_post", "Image_post" + bitmap);
                    if (bitmap != null)
                        params.put("dr_image", new DataPart(System.currentTimeMillis() + "Image_event.png", AppHelper.getFileDataFromDrawable(bitmap), "image/png"));
                        params.put("resume", new DataPart(System.currentTimeMillis() + "resume.pdf", AppHelper.getFileDataBitmap(bitmap), "application/pdf"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return params;
            }
        };
        //multipartRequest.setShouldCache(false);
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        multipartRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 20, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(multipartRequest);

    }


    private void getcategoryList(String cat_url) {
        // Utils.showDialog(context, "Loading Please Wait...");
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
                        //  Utils.dismissDialog();
                        try {
                            String message = jsonObject.getString("msg");
                            String result = jsonObject.getString("result");
                            //catagoryList.clear();
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

                            ArrayAdapter<CategoryListData> aa = new ArrayAdapter<CategoryListData>(context, android.R.layout.simple_spinner_item, catagoryList) {
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
                            spinner.setAdapter(aa);

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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
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

        DatePickerDialog datePickerDialog = new DatePickerDialog(context, R.style.calender_dialog_theme,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        tv_dob.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }
//**************************************************************************************
//@Override
//public void onConnected(Bundle bundle) {
//    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.
//            checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//        // TODO: Consider calling
//        //    ActivityCompat#requestPermissions
//        // here to request the missing permissions, and then overriding
//        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//        //                                          int[] grantResults)
//        // to handle the case where the user grants the permission. See the documentation
//        // for ActivityCompat#requestPermissions for more details.
//        return;
//    }
//
//    startLocationUpdates();
//
//    mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
//
//    if(mLocation == null){
//        startLocationUpdates();
//    }
//    if (mLocation != null) {
//
//        // mLatitudeTextView.setText(String.valueOf(mLocation.getLatitude()));
//        //mLongitudeTextView.setText(String.valueOf(mLocation.getLongitude()));
//    } else {
//        Toast.makeText(context, "Location not Detected", Toast.LENGTH_SHORT).show();
//    }
//}
//
//    protected void startLocationUpdates() {
//        // Create the location request
//        mLocationRequest = LocationRequest.create()
//                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
//                .setInterval(UPDATE_INTERVAL)
//                .setFastestInterval(FASTEST_INTERVAL);
//        // Request location updates
//        if (ActivityCompat.checkSelfPermission(context,
//                Manifest.permission.ACCESS_FINE_LOCATION)
//                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
//                (context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
//                mLocationRequest,   this);
//        Log.d("reque", "--->>>>");
//    }
//
//    @Override
//    public void onConnectionSuspended(int i) {
//        Log.e("connection_suspend", "Connection Suspended");
//        mGoogleApiClient.connect();
//    }
//
//    @Override
//    public void onConnectionFailed(ConnectionResult connectionResult) {
//        Log.e("Connection failed", "Connection failed. Error: " + connectionResult.getErrorCode());
//    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        if (mGoogleApiClient != null) {
//            mGoogleApiClient.connect();
//        }
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        if (mGoogleApiClient.isConnected()) {
//            mGoogleApiClient.disconnect();
//        }
//    }
//
//    @Override
//    public void onLocationChanged(Location location) {
//
//        String msg = "Updated Location: " +
//                Double.toString(location.getLatitude()) + "," +
//                Double.toString(location.getLongitude());
//        //mLatitudeTextView.setText(String.valueOf(location.getLatitude()));
//        //mLongitudeTextView.setText(String.valueOf(location.getLongitude() ));
//        // Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
//        // You can now create a LatLng Object for use with maps
//        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
//        //******************address
//
//        if (location != null) {
//            double latitude = location.getLatitude();
//            double longitude = location.getLongitude();
//            LocationAddress locationAddress = new LocationAddress();
//            locationAddress.getAddressFromLocation(latitude, longitude,
//                    getApplicationContext(), new GeoCoderHandler());
//        }
//
//    }
//
//
//
//    private boolean checkLocation() {
//        if(!isLocationEnabled())
//            showAlert();
//        return isLocationEnabled();
//    }
//
//
//    private boolean isLocationEnabled() {
//        locationManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
//        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
//                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
//    }
//
//    private void showAlert() {
//        final AlertDialog.Builder dialog = new AlertDialog.Builder(context);
//        dialog.setTitle("Enable Location")
//                .setMessage("Your Locations Settings is set to 'Off'.\nPlease Enable Location to " +
//                        "use this app")
//                .setPositiveButton("Location Settings", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
//
//                        Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//                        startActivity(myIntent);
//                    }
//                })
//                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
//
//                    }
//                });
//        dialog.show();
//    }
//
//
//    private class GeoCoderHandler extends Handler {
//        @Override
//        public void handleMessage(Message_new message) {
//            String locationAddress;
//            switch (message.what) {
//                case 1:
//                    Bundle bundle = message.getData();
//                    locationAddress = bundle.getString("address");
//                    break;
//                default:
//                    locationAddress = null;
//            }
//            address=locationAddress.toString();
//            Log.e("full_address",""+locationAddress);
//            // mAddress.setText(locationAddress);
//            //Toast.makeText(context, ""+locationAddress, Toast.LENGTH_SHORT).show();
//        }
//    }
}
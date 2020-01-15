package com.example.drtherapist.client.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.drtherapist.R;
import com.example.drtherapist.client.activity.MainActivity;
import com.example.drtherapist.client.model.UserInfoData;
import com.example.drtherapist.common.BasicActivities.LoginActivity;
import com.example.drtherapist.common.Utils.AppHelper;
import com.example.drtherapist.common.Utils.GPSTracker;
import com.example.drtherapist.common.Utils.LocationAddress;
import com.example.drtherapist.common.Utils.LocationTrack;
import com.example.drtherapist.common.Utils.NetworkUtil;
import com.example.drtherapist.common.Utils.PathUtils;
import com.example.drtherapist.common.Utils.ToastClass;
import com.example.drtherapist.common.Utils.Utils;
import com.example.drtherapist.common.Utils.Validation;
import com.example.drtherapist.common.Utils.VolleyMultipartRequest;
import com.example.drtherapist.common.remote.API;
import com.example.drtherapist.common.remote.Session;
import com.example.drtherapist.therapist.activity.ActivityNavigationDr;
import com.example.drtherapist.therapist.fragment.TherapistSignUpFragment;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.iid.FirebaseInstanceId;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickCancel;
import com.vansuita.pickimage.listeners.IPickResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.facebook.FacebookSdk.getApplicationContext;

public class ClientSignUpFragment extends Fragment implements View.OnClickListener
//        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
//        com.google.android.gms.location.LocationListener
{

    private GoogleApiClient mGoogleApiClient;
    private Location mLocation;
    private LocationManager mLocationManager;
    com.google.android.gms.location.LocationListener listener;
    private LocationRequest mLocationRequest;
    private LocationManager locationManager;

    private long UPDATE_INTERVAL = 5 * 1000;
    private long FASTEST_INTERVAL = 5000;

    private Button btn_signup;
    private TextView tv_login;
    private View view;
    private EditText et_name, et_email, et_age, et_phone, et_password, et_address;
    private Context context;
    private Session session;
    private CircleImageView img_profile, iv_edit;
    private File imgFile;
    private String img_uri;
    String uid;
    String path;
    String fcm_token;
    String name, email, password, age, phone;

    //For Location==
    GPSTracker tracker;
    LocationTrack locationTrack;
    double longitude, latitude;
    private String address1, address2, city, state, country, county, PIN, postalCode, knownName;
    public static int APP_REQUEST_CODE = 99;
    String address;
    String url;
    private FirebaseAuth auth;
    EditText mLatitudeTextView;
    EditText mLongitudeTextView;
    EditText mAddress;


    public ClientSignUpFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_client_sign_up, container, false);
        session = new Session(context);
        auth = FirebaseAuth.getInstance();
        initView();
        clickListner();

        fcm_token= FirebaseInstanceId.getInstance().getToken();

        tracker = new GPSTracker(context);
        locationTrack = new LocationTrack(context);
        if (tracker.canGetLocation()) {
            latitude = tracker.getLatitude();
            longitude = tracker.getLongitude();
            Log.e("sign_lat_client", " " + tracker.getLatitude());
            Log.e("sign_long_client", " " + tracker.getLongitude());

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
        et_name = view.findViewById(R.id.et_name);
        et_email = view.findViewById(R.id.et_email);
        et_age = view.findViewById(R.id.et_age);
        et_phone = view.findViewById(R.id.et_phone);
        et_password = view.findViewById(R.id.et_password);
        et_address = view.findViewById(R.id.et_address);
        img_profile = view.findViewById(R.id.img_profile);
        iv_edit = view.findViewById(R.id.iv_edit);
        btn_signup = view.findViewById(R.id.btn_signup);
        tv_login = view.findViewById(R.id.tv_login);



//        mGoogleApiClient = new GoogleApiClient.Builder(context)
//                .addConnectionCallbacks(this)
//                .addOnConnectionFailedListener(this)
//                .addApi(LocationServices.API)
//                .build();
//
//        mLocationManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
//
//        checkLocation();



    }

    private void clickListner() {
        btn_signup.setOnClickListener(this);
        tv_login.setOnClickListener(this);
        img_profile.setOnClickListener(this);
        iv_edit.setOnClickListener(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.btn_signup:
                name = et_name.getText().toString();
                email = et_email.getText().toString();
                password = et_password.getText().toString();
                phone = et_phone.getText().toString();
                age = et_age.getText().toString();

                checkValidation();
                break;
            case R.id.tv_login:
                intent = new Intent(getActivity(), LoginActivity.class);
                intent.putExtra("CLIENT", "client");
                startActivity(intent);
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
                            img_profile.setImageBitmap(r.getBitmap());
                        } else {
                            //Handle possible errors
                            //TODO: do what you have to do with r.getError();Toast.makeText(getActivity(), r.getError().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }

                }).show(getActivity());
                // imagePick();
                break;
        }
    }

    private void checkValidation() {
        String name = et_name.getText().toString();
        String email = et_email.getText().toString();
        String age = et_age.getText().toString();
        String phone = et_phone.getText().toString();
        String pass = et_password.getText().toString();
        String add = et_address.getText().toString();

        Validation validation = new Validation(context);

        if (!validation.isEmpty(name)) {
            ToastClass.showToast(context, getString(R.string.fullname_v));
            et_name.requestFocus();

        } else if (!validation.isValidEmail(email)) {
            ToastClass.showToast(context, getString(R.string.email_v));
            et_email.requestFocus();

        } else if (!validation.isEmpty(age)) {
            ToastClass.showToast(context, getString(R.string.age_v));
            et_age.requestFocus();

        } else if (!validation.isValidNo(phone)) {
            ToastClass.showToast(context, getString(R.string.contact_v));
            et_phone.requestFocus();

        } else if (!validation.isValidPassword(pass)) {
            ToastClass.showToast(context, getString(R.string.password_v));
            et_password.requestFocus();
        } else if (!validation.isEmpty(add)) {
            ToastClass.showToast(context, getString(R.string.address_v));
            et_address.requestFocus();
        } else {

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
                         url = API.BASE_URL + "SignUpUser";
                        Utils.showDialog(context, "Loading Please Wait...");
                        auth.createUserWithEmailAndPassword(et_email.getText().toString(), "123456789")
                                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        // Toast.makeText(context, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                        // progressBar.setVisibility(View.GONE);
                                        // If sign in fails, display a item_in_message to the user. If sign in succeeds
                                        // the auth state listener will be notified and logic to handle the
                                        // signed in user can be handled in the listener.

                                        if (!task.isSuccessful()) {
                                            Utils.dismissDialog();
                                            Toast.makeText(context, "" + task.getException(), Toast.LENGTH_SHORT).show();
                                            Log.e("firebase ", "Firebase failed" + task.getException());
                                        } else {
                                            uid=FirebaseAuth.getInstance().getCurrentUser().getUid();
                                            CallSignUpApi(url);
                                            Log.e("firebase ", "Firebase Success");
                                            Log.e("uid ", ""+uid);
                                        }
                                    }
                                });


                        Log.e("signup url user = ", url);
                    } catch (NullPointerException e) {
                        ToastClass.showToast(context, getString(R.string.too_slow));
                    }
                } else {
                    ToastClass.showToast(context, getString(R.string.no_internet_access));
                }
            }


        }
    }

/*    private void imagePick() {
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
                    //If you want the Uri.
                    //Mandatory to refresh image from Uri.
                    //getImageView().setImageURI(null);
                    //Setting the real returned image.
                    //getImageView().setImageURI(r.getUri());
                    //If you want the Bitmap.
                    img_profile.setImageBitmap(r.getBitmap());
                    Log.e("bitmap = ", "" + r.getBitmap());

                    Log.e("Imagepath", r.getPath());

                    img_uri = String.valueOf(r.getUri());
                    Log.e("img_uri", img_uri);


                    imgFile = PathUtils.bitmapToFile(context, r.getBitmap());
                    Log.e("imgFile", "" + imgFile);


                    //r.getPath();
                } else {
                    //Handle possible errors
                    //TODO: do what you have to do with r.getError();
                    Toast.makeText(getActivity(), r.getError().getMessage(), Toast.LENGTH_LONG).show();
                }
            }

        }).show((FragmentActivity) context);
    }*/

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
        }catch (Exception e){
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

                        user.id = job.getString("user_id");
                        user.username = job.getString("user_name");
                        //user.cat_id = job.getString("cat_id");
                        user.email = job.getString("user_email");
                        user.password = job.getString("user_pass");
                        user.address = job.getString("user_address");//can remove address its not use
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
                        user.famly_member = job.getString("famly_member");

                        session.createSession(user);
                       // ToastClass.showToast(context, msg);

                        Intent intent = new Intent(context, MainActivity.class);
                        startActivity(intent);
                        //String emailId = job.getString("user_email");


//                        auth.createUserWithEmailAndPassword(emailId, "123456789")
//                                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
//                                    @Override
//                                    public void onComplete(@NonNull Task<AuthResult> task) {
//                                       // Toast.makeText(context, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
//                                        // progressBar.setVisibility(View.GONE);
//                                        // If sign in fails, display a item_in_message to the user. If sign in succeeds
//                                        // the auth state listener will be notified and logic to handle the
//                                        // signed in user can be handled in the listener.
//
//                                        if (!task.isSuccessful()) {
//                                            //Toast.makeText(context, "Authentication failed." + task.getException(), Toast.LENGTH_SHORT).show();
//                                            Log.e("firebase ", "Firebase failed" + task.getException());
//                                        } else {
//                                            Intent intent = new Intent(context, MainActivity.class);
//                                            startActivity(intent);
//                                            Log.e("firebase ", "Firebase Success");
//                                        }
//                                    }
//                                });


                    } else  {
                        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
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
                params.put("location", address);
                params.put("user_age", age);
                params.put("user_unique_key",uid);
                params.put("lat", String.valueOf(latitude));
                params.put("lng", String.valueOf(longitude));
                params.put("user_image", path);
                params.put("fcm_id", fcm_token);

                // Log.e("tag", "getParams: " + params.put("product_img", String.valueOf(path)));

                return params;
            }

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                try {
                    Bitmap bitmap = ((BitmapDrawable) img_profile.getDrawable()).getBitmap();
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
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        multipartRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 20, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(multipartRequest);

    }
//********************************************************************
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
//        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) !=
//                PackageManager.PERMISSION_GRANTED && ActivityCompat.
//                checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
//                mLocationRequest,listener  );
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
//       // mLatitudeTextView.setText(String.valueOf(location.getLatitude()));
//       // mLongitudeTextView.setText(String.valueOf(location.getLongitude() ));
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
//
//        @Override
//            public void handleMessage(Message_new message) {
//                String locationAddress;
//                switch (message.what) {
//                    case 1:
//                        Bundle bundle = message.getData();
//                        locationAddress = bundle.getString("address");
//                        break;
//                    default:
//                        locationAddress = null;
//                }
//                address=locationAddress.toString();
//                Log.e("full_address",""+locationAddress);
//                // mAddress.setText(locationAddress);
//                //Toast.makeText(context, ""+locationAddress, Toast.LENGTH_SHORT).show();
//            }
//
//
//    }





//*************************************************************************

/*
    private void CallSignUpApi(String url) {
        Utils.showDialog(context, "Loading Please Wait...");

        AndroidNetworking.upload(url)


                .addMultipartParameter("name", name)
                .addMultipartParameter("email", email)
                .addMultipartParameter("password", password)
                .addMultipartParameter("location", address)
                .addMultipartParameter("mobile", phone)
                .addMultipartParameter("user_age", age)
                .addMultipartParameter("lat", String.valueOf(latitude))
                .addMultipartParameter("lng", String.valueOf(longitude))
                .addMultipartFile("user_image", imgFile)

                .setTag("SignUp")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Log.e("sigup respo client = ", "" + jsonObject);
                        Utils.dismissDialog();
                        try {
                            String result = jsonObject.getString("result");
                            //String status = jsonObject.getString("is_status");
                            String item_in_message = jsonObject.getString("msg");
                            // Log.e("status = ", status);

                            if (result.equalsIgnoreCase("true")) {
                                JSONObject job = jsonObject.getJSONObject("data");

                                UserInfoData user = new UserInfoData();
                                //  user.job_id = job.getString("user_id");
                                user.id = job.getString("user_id");
                                user.username = job.getString("user_name");
                                user.cat_id = job.getString("cat_id");
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
                                Log.e("name = ", user.username + "email =" + user.email);

                                Intent intent = new Intent(getActivity(), MainActivity.class);
                                startActivity(intent);

                            } else Utils.openAlertDialog(context, item_in_message);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("exp = ", "" + e);
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        ToastClass.showToast(context, error.toString());
                        Log.e("error = ", "" + error);
                    }
                });
    }
*/
}

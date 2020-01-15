package com.example.drtherapist.client.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.edmodo.rangebar.RangeBar;
import com.example.drtherapist.R;
import com.example.drtherapist.client.model.ApplicantListData;
import com.example.drtherapist.client.model.PostJobModel;
import com.example.drtherapist.common.Utils.AppConfig;
import com.example.drtherapist.common.Utils.NetworkUtil;
import com.example.drtherapist.common.Utils.ToastClass;
import com.example.drtherapist.common.Utils.Utils;
import com.example.drtherapist.common.Utils.Validation;
import com.example.drtherapist.common.remote.API;
import com.example.drtherapist.common.remote.Session;
import com.example.drtherapist.therapist.model.CategoryListData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

public class ActivityPostJobClient extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    private List<CategoryListData> catagoryList;
    private List<PostJobModel> postJobModelsList;
    List<String> jobTypeList = new ArrayList<String>();
    private String catId, cateName;
    private int mHour, mMinute;
    String url_postJob, url_getCategory;
    String title, location, user_msg, startdate, enddate, maxPrice, minPrice;
    ImageView iv_back_postjob;
    Button btn_postjob;
    EditText et_titile_postjob, et_job_desc_postjob, et_address;
    Spinner spinner_cat;
    TextView tv_sun, tv_mon, tv_tue, tv_wed, tv_thu, tv_fri, tv_sat,
            tv_start_time_sun, tv_end_time_sun, tv_start_time_mon, tv_end_time_mon,
            tv_start_time_tue, tv_end_time_tue, tv_start_time_wed, tv_end_time_wed,
            tv_start_time_thu, tv_end_time_thu, tv_start_time_fri, tv_end_time_fri,
            tv_start_time_sat, tv_end_time_sat;
    TextView tv_start_date, tv_end_date, tv_start_time, tv_end_time;
    LinearLayout lay_time_sun, lay_time_mon, lay_time_tue,
            lay_time_wed, lay_time_thu, lay_time_fri,
            lay_time_sat, time_detail_sun, time_detail_mon, time_detail_tue,
            time_detail_wed, time_detail_thu, time_detail_fri, time_detail_sat;

    TextView tv_minvalue_sun, tv_maxvalue_sun,
            tv_minvalue_mon, tv_maxvalue_mon, tv_minvalue_tue,
            tv_maxvalue_tue, tv_minvalue_wed, tv_maxvalue_wed,
            tv_minvalue_thu, tv_maxvalue_thu, tv_minvalue_fri,
            tv_maxvalue_fri, tv_minvalue_sat, tv_maxvalue_sat;

    String sunMinValue, sunMaxValue, monMinValue, monMaxValue, tueMinValue, tueMaxValue, wedMinValue, wedMaxValue, thuMinValue, thuMaxValue, friMinValue, friMaxValue, satMinValue, satMaxValue;

    private int mYear, mMonth, mDay;
    final int SMALLEST_HOUR_FRACTION = 12;
    TextView tv_minvalue_price, tv_maxvalue_price;


    String minpm = "am", maxpm = "am";
    Session session;
    String userId, getlocation;
    String currentDate;
    String full_part_tym;
    String count_number;
    String jobtype;

    List<String> daysList = new ArrayList<String>();
    List<String> startTimeList;
    List<String> endTimeList;

    List<MultipartBody.Part> daysPartList = new ArrayList<>();
    List<MultipartBody.Part> startTimePartList = new ArrayList<>();
    List<MultipartBody.Part> endTimePartList = new ArrayList<>();

    String check = "1";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_job_client);

        session = new Session(this);
        userId = session.getUser().id;
        getlocation = session.getUser().location;
        Log.e("getlocation", "" + getlocation);

        catagoryList = new ArrayList<>();
        postJobModelsList = new ArrayList<>();

        SimpleDateFormat currentDate = new SimpleDateFormat("dd/MM/yyyy");
        Date todayDate = new Date();
        this.currentDate = currentDate.format(todayDate);

        final Spinner spinner_kids = (Spinner) findViewById(R.id.spinner_kids);
        final Spinner spinner_jobtype = (Spinner) findViewById(R.id.spinner_jobtype);

        tv_sun = findViewById(R.id.tv_sun);
        tv_mon = findViewById(R.id.tv_mon);
        tv_tue = findViewById(R.id.tv_tue);
        tv_wed = findViewById(R.id.tv_wed);
        tv_thu = findViewById(R.id.tv_thu);
        tv_fri = findViewById(R.id.tv_fri);
        tv_sat = findViewById(R.id.tv_sat);

        et_titile_postjob = findViewById(R.id.et_titile_postjob);
        et_job_desc_postjob = findViewById(R.id.et_job_desc_postjob);
        spinner_cat = findViewById(R.id.spinner_cat);
        et_address = findViewById(R.id.et_address);


        tv_start_date = findViewById(R.id.tv_start_date);
        tv_end_date = findViewById(R.id.tv_end_date);
        tv_start_time = findViewById(R.id.tv_start_time);
        tv_end_time = findViewById(R.id.tv_end_time);
        tv_start_time_sun = findViewById(R.id.tv_start_time_sun);
        tv_end_time_sun = findViewById(R.id.tv_end_time_sun);
        tv_start_time_mon = findViewById(R.id.tv_start_time_mon);
        tv_end_time_mon = findViewById(R.id.tv_end_time_mon);
        tv_start_time_tue = findViewById(R.id.tv_start_time_tue);
        tv_end_time_tue = findViewById(R.id.tv_end_time_tue);
        tv_start_time_wed = findViewById(R.id.tv_start_time_wed);
        tv_end_time_wed = findViewById(R.id.tv_end_time_wed);
        tv_start_time_thu = findViewById(R.id.tv_start_time_thu);
        tv_end_time_thu = findViewById(R.id.tv_end_time_thu);
        tv_start_time_fri = findViewById(R.id.tv_start_time_fri);
        tv_end_time_fri = findViewById(R.id.tv_end_time_fri);
        tv_start_time_sat = findViewById(R.id.tv_start_time_sat);
        tv_end_time_sat = findViewById(R.id.tv_end_time_sat);
        //parent layout====
        lay_time_sun = findViewById(R.id.lay_time_sun);
        lay_time_mon = findViewById(R.id.lay_time_mon);
        lay_time_tue = findViewById(R.id.lay_time_tue);
        lay_time_wed = findViewById(R.id.lay_time_wed);
        lay_time_thu = findViewById(R.id.lay_time_thu);
        lay_time_wed = findViewById(R.id.lay_time_wed);
        lay_time_wed = findViewById(R.id.lay_time_wed);
        lay_time_fri = findViewById(R.id.lay_time_fri);
        lay_time_sat = findViewById(R.id.lay_time_sat);

        //child layout====
        time_detail_sun = findViewById(R.id.time_detail_sun);
        time_detail_mon = findViewById(R.id.time_detail_mon);
        time_detail_tue = findViewById(R.id.time_detail_tue);
        time_detail_wed = findViewById(R.id.time_detail_wed);
        time_detail_thu = findViewById(R.id.time_detail_thu);
        time_detail_fri = findViewById(R.id.time_detail_fri);
        time_detail_sat = findViewById(R.id.time_detail_sat);


        //back botton ===
        iv_back_postjob = findViewById(R.id.iv_back_postjob);
        btn_postjob = findViewById(R.id.btn_postjob);


        //days click===
        tv_sun.setOnClickListener(this);
        tv_mon.setOnClickListener(this);
        tv_tue.setOnClickListener(this);
        tv_wed.setOnClickListener(this);
        tv_thu.setOnClickListener(this);
        tv_fri.setOnClickListener(this);
        tv_sat.setOnClickListener(this);
        tv_sat.setOnClickListener(this);

        tv_start_date.setOnClickListener(this);
        tv_end_date.setOnClickListener(this);
        tv_start_time.setOnClickListener(this);
        tv_end_time.setOnClickListener(this);

        //time===
        lay_time_sun.setOnClickListener(this);
        lay_time_mon.setOnClickListener(this);
        lay_time_tue.setOnClickListener(this);
        lay_time_wed.setOnClickListener(this);
        lay_time_thu.setOnClickListener(this);
        lay_time_fri.setOnClickListener(this);
        lay_time_sat.setOnClickListener(this);

        spinner_cat.setOnItemSelectedListener(this);
        iv_back_postjob.setOnClickListener(this);
        btn_postjob.setOnClickListener(this);


        et_address.setText(getlocation);
        if (NetworkUtil.isNetworkConnected(this)) {
            try {
                url_postJob = API.BASE_URL + "jobPost";
                url_getCategory = API.BASE_URL + "getCategory";
                Log.e("postJob URL = ", url_postJob);
                getcategoryList(url_getCategory);
            } catch (NullPointerException e) {
                ToastClass.showToast(this, getString(R.string.too_slow));
            }
        } else {
            ToastClass.showToast(this, getString(R.string.no_internet_access));
        }

        spinner_jobtype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String kidsnumber = parent.getItemAtPosition(position).toString();
//                if (kidsnumber.equals(1)) {
//                    full_part_tym = "Full Time";
//                }
//                else {
//
//                }

                jobtype = jobTypeList.get(position);
                Log.e("jobtype", "" + jobtype);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner_kids.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                count_number = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //Spinner For Count Number Of Person and kids===
        List<String> countList = new ArrayList<String>();
        countList.add("1");
        countList.add("2");
        countList.add("3");
        countList.add("4");
        countList.add("5");
        countList.add("6");
        countList.add("7");
        countList.add("8");
        countList.add("9");
        countList.add("10");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, countList);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spinner_kids.setAdapter(dataAdapter);

        //Spinner For ===

        jobTypeList.add("Full Time");
        jobTypeList.add("Part Time");
        ArrayAdapter<String> jobTypeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, jobTypeList);
        jobTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_jobtype.setAdapter(jobTypeAdapter);


        //time seekbar =====
        tv_minvalue_sun = (TextView) findViewById(R.id.tv_minvalue_sun);
        tv_maxvalue_sun = (TextView) findViewById(R.id.tv_maxvalue_sun);

        RangeBar rangebar_sun;
        rangebar_sun = (RangeBar) findViewById(R.id.rangebar_sun);
        rangebar_sun.setTickCount(25 * SMALLEST_HOUR_FRACTION);//SMALLEST_HOUR_FRACTION = 4;
        rangebar_sun.setTickHeight(0);
        rangebar_sun.setThumbRadius(8);
        rangebar_sun.setConnectingLineWeight(3);


        // Sets the display values of the indices
        rangebar_sun.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onIndexChangeListener(RangeBar rangeBar, int leftThumbIndex, int rightThumbIndex) {
                DecimalFormat deciFormat = new DecimalFormat("00");

                int minHour = leftThumbIndex / SMALLEST_HOUR_FRACTION;
                int minMinute = (int) ((leftThumbIndex % SMALLEST_HOUR_FRACTION) * (60 / (float) SMALLEST_HOUR_FRACTION));
                int maxHour = rightThumbIndex / SMALLEST_HOUR_FRACTION;
                int maxMinute = (int) ((rightThumbIndex % SMALLEST_HOUR_FRACTION) * (60 / (float) SMALLEST_HOUR_FRACTION));
                int minHourFinal = getMin(minHour);
                int maxHourFinal = getMax(maxHour);

                tv_start_time_sun.setText(deciFormat.format(minHourFinal));
                tv_end_time_sun.setText(deciFormat.format(maxHourFinal));

                tv_minvalue_sun.setText(minHour + ":" + minHourFinal);
                tv_maxvalue_sun.setText(maxHour + ":" + maxHourFinal);

                tv_minvalue_sun.setText(deciFormat.format(minHourFinal) + ":" + deciFormat.format(minMinute));
                tv_maxvalue_sun.setText(deciFormat.format(maxHourFinal) + ":" + deciFormat.format(maxMinute));
            }
        });

        tv_minvalue_mon = (TextView) findViewById(R.id.tv_minvalue_mon);
        tv_maxvalue_mon = (TextView) findViewById(R.id.tv_maxvalue_mon);

        RangeBar rangebar_mon;
        rangebar_mon = (RangeBar) findViewById(R.id.rangebar_mon);
        rangebar_mon.setTickCount(25 * SMALLEST_HOUR_FRACTION);//SMALLEST_HOUR_FRACTION = 4;
        rangebar_mon.setTickHeight(0);
        rangebar_mon.setThumbRadius(8);
        rangebar_mon.setConnectingLineWeight(3);


        // Sets the display values of the indices
        rangebar_mon.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onIndexChangeListener(RangeBar rangeBar, int leftThumbIndex, int rightThumbIndex) {
                DecimalFormat deciFormat = new DecimalFormat("00");

                int minHour = leftThumbIndex / SMALLEST_HOUR_FRACTION;
                int minMinute = (int) ((leftThumbIndex % SMALLEST_HOUR_FRACTION) * (60 / (float) SMALLEST_HOUR_FRACTION));
                int maxHour = rightThumbIndex / SMALLEST_HOUR_FRACTION;
                int maxMinute = (int) ((rightThumbIndex % SMALLEST_HOUR_FRACTION) * (60 / (float) SMALLEST_HOUR_FRACTION));
                int minHourFinal = getMin(minHour);
                int maxHourFinal = getMax(maxHour);

                tv_start_time_mon.setText(deciFormat.format(minHourFinal));
                tv_end_time_mon.setText(deciFormat.format(maxHourFinal));

                tv_minvalue_mon.setText(minHour + ":" + minHourFinal);
                tv_maxvalue_mon.setText(maxHour + ":" + maxHourFinal);

                tv_minvalue_mon.setText(deciFormat.format(minHourFinal) + ":" + deciFormat.format(minMinute));
                tv_maxvalue_mon.setText(deciFormat.format(maxHourFinal) + ":" + deciFormat.format(maxMinute));
            }
        });

        tv_minvalue_tue = (TextView) findViewById(R.id.tv_minvalue_tue);
        tv_maxvalue_tue = (TextView) findViewById(R.id.tv_maxvalue_tue);

        RangeBar rangebar_tue;
        rangebar_tue = (RangeBar) findViewById(R.id.rangebar_tue);
        rangebar_tue.setTickCount(25 * SMALLEST_HOUR_FRACTION);//SMALLEST_HOUR_FRACTION = 4;
        rangebar_tue.setTickHeight(0);
        rangebar_tue.setThumbRadius(8);
        rangebar_tue.setConnectingLineWeight(3);


        // Sets the display values of the indices
        rangebar_tue.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onIndexChangeListener(RangeBar rangeBar, int leftThumbIndex, int rightThumbIndex) {
                DecimalFormat deciFormat = new DecimalFormat("00");

                int minHour = leftThumbIndex / SMALLEST_HOUR_FRACTION;
                int minMinute = (int) ((leftThumbIndex % SMALLEST_HOUR_FRACTION) * (60 / (float) SMALLEST_HOUR_FRACTION));
                int maxHour = rightThumbIndex / SMALLEST_HOUR_FRACTION;
                int maxMinute = (int) ((rightThumbIndex % SMALLEST_HOUR_FRACTION) * (60 / (float) SMALLEST_HOUR_FRACTION));
                int minHourFinal = getMin(minHour);
                int maxHourFinal = getMax(maxHour);

                tv_start_time_tue.setText(deciFormat.format(minHourFinal));
                tv_end_time_tue.setText(deciFormat.format(maxHourFinal));

                tv_minvalue_tue.setText(minHour + ":" + minHourFinal);
                tv_maxvalue_tue.setText(maxHour + ":" + maxHourFinal);

                tv_minvalue_tue.setText(deciFormat.format(minHourFinal) + ":" + deciFormat.format(minMinute));
                tv_maxvalue_tue.setText(deciFormat.format(maxHourFinal) + ":" + deciFormat.format(maxMinute));
            }
        });


        tv_minvalue_wed = (TextView) findViewById(R.id.tv_minvalue_wed);
        tv_maxvalue_wed = (TextView) findViewById(R.id.tv_maxvalue_wed);

        RangeBar rangebar_wed;
        rangebar_wed = (RangeBar) findViewById(R.id.rangebar_wed);
        rangebar_wed.setTickCount(25 * SMALLEST_HOUR_FRACTION);//SMALLEST_HOUR_FRACTION = 4;
        rangebar_wed.setTickHeight(0);
        rangebar_wed.setThumbRadius(8);
        rangebar_wed.setConnectingLineWeight(3);


        // Sets the display values of the indices
        rangebar_wed.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onIndexChangeListener(RangeBar rangeBar, int leftThumbIndex, int rightThumbIndex) {
                DecimalFormat deciFormat = new DecimalFormat("00");

                int minHour = leftThumbIndex / SMALLEST_HOUR_FRACTION;
                int minMinute = (int) ((leftThumbIndex % SMALLEST_HOUR_FRACTION) * (60 / (float) SMALLEST_HOUR_FRACTION));
                int maxHour = rightThumbIndex / SMALLEST_HOUR_FRACTION;
                int maxMinute = (int) ((rightThumbIndex % SMALLEST_HOUR_FRACTION) * (60 / (float) SMALLEST_HOUR_FRACTION));
                int minHourFinal = getMin(minHour);
                int maxHourFinal = getMax(maxHour);

                tv_start_time_wed.setText(deciFormat.format(minHourFinal));
                tv_end_time_wed.setText(deciFormat.format(maxHourFinal));

                tv_minvalue_wed.setText(minHour + ":" + minHourFinal);
                tv_maxvalue_wed.setText(maxHour + ":" + maxHourFinal);

                tv_minvalue_wed.setText(deciFormat.format(minHourFinal) + ":" + deciFormat.format(minMinute));
                tv_maxvalue_wed.setText(deciFormat.format(maxHourFinal) + ":" + deciFormat.format(maxMinute));
            }
        });


        tv_minvalue_thu = (TextView) findViewById(R.id.tv_minvalue_thu);
        tv_maxvalue_thu = (TextView) findViewById(R.id.tv_maxvalue_thu);

        RangeBar rangebar_thu;
        rangebar_thu = (RangeBar) findViewById(R.id.rangebar_thu);
        rangebar_thu.setTickCount(25 * SMALLEST_HOUR_FRACTION);//SMALLEST_HOUR_FRACTION = 4;
        rangebar_thu.setTickHeight(0);
        rangebar_thu.setThumbRadius(8);
        rangebar_thu.setConnectingLineWeight(3);


        // Sets the display values of the indices
        rangebar_thu.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onIndexChangeListener(RangeBar rangeBar, int leftThumbIndex, int rightThumbIndex) {
                DecimalFormat deciFormat = new DecimalFormat("00");

                int minHour = leftThumbIndex / SMALLEST_HOUR_FRACTION;
                int minMinute = (int) ((leftThumbIndex % SMALLEST_HOUR_FRACTION) * (60 / (float) SMALLEST_HOUR_FRACTION));
                int maxHour = rightThumbIndex / SMALLEST_HOUR_FRACTION;
                int maxMinute = (int) ((rightThumbIndex % SMALLEST_HOUR_FRACTION) * (60 / (float) SMALLEST_HOUR_FRACTION));
                int minHourFinal = getMin(minHour);
                int maxHourFinal = getMax(maxHour);

                tv_start_time_thu.setText(deciFormat.format(minHourFinal));
                tv_end_time_thu.setText(deciFormat.format(maxHourFinal));

                tv_minvalue_thu.setText(minHour + ":" + minHourFinal);
                tv_maxvalue_thu.setText(maxHour + ":" + maxHourFinal);

                tv_minvalue_thu.setText(deciFormat.format(minHourFinal) + ":" + deciFormat.format(minMinute));
                tv_maxvalue_thu.setText(deciFormat.format(maxHourFinal) + ":" + deciFormat.format(maxMinute));
            }
        });

        tv_minvalue_fri = (TextView) findViewById(R.id.tv_minvalue_fri);
        tv_maxvalue_fri = (TextView) findViewById(R.id.tv_maxvalue_fri);

        RangeBar rangebar_fri;
        rangebar_fri = (RangeBar) findViewById(R.id.rangebar_fri);
        rangebar_fri.setTickCount(25 * SMALLEST_HOUR_FRACTION);//SMALLEST_HOUR_FRACTION = 4;
        rangebar_fri.setTickHeight(0);
        rangebar_fri.setThumbRadius(8);
        rangebar_fri.setConnectingLineWeight(3);


        // Sets the display values of the indices
        rangebar_fri.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onIndexChangeListener(RangeBar rangeBar, int leftThumbIndex, int rightThumbIndex) {
                DecimalFormat deciFormat = new DecimalFormat("00");

                int minHour = leftThumbIndex / SMALLEST_HOUR_FRACTION;
                int minMinute = (int) ((leftThumbIndex % SMALLEST_HOUR_FRACTION) * (60 / (float) SMALLEST_HOUR_FRACTION));
                int maxHour = rightThumbIndex / SMALLEST_HOUR_FRACTION;
                int maxMinute = (int) ((rightThumbIndex % SMALLEST_HOUR_FRACTION) * (60 / (float) SMALLEST_HOUR_FRACTION));
                int minHourFinal = getMin(minHour);
                int maxHourFinal = getMax(maxHour);

                tv_start_time_fri.setText(deciFormat.format(minHourFinal));
                tv_end_time_fri.setText(deciFormat.format(maxHourFinal));

                tv_minvalue_fri.setText(minHour + ":" + minHourFinal);
                tv_maxvalue_fri.setText(maxHour + ":" + maxHourFinal);

                tv_minvalue_fri.setText(deciFormat.format(minHourFinal) + ":" + deciFormat.format(minMinute));
                tv_maxvalue_fri.setText(deciFormat.format(maxHourFinal) + ":" + deciFormat.format(maxMinute));
            }
        });


        tv_minvalue_sat = (TextView) findViewById(R.id.tv_minvalue_sat);
        tv_maxvalue_sat = (TextView) findViewById(R.id.tv_maxvalue_sat);

        RangeBar rangebar_sat;
        rangebar_sat = (RangeBar) findViewById(R.id.rangebar_sat);
        rangebar_sat.setTickCount(25 * SMALLEST_HOUR_FRACTION);//SMALLEST_HOUR_FRACTION = 4;
        rangebar_sat.setTickHeight(0);
        rangebar_sat.setThumbRadius(8);
        rangebar_sat.setConnectingLineWeight(3);


        // Sets the display values of the indices
        rangebar_sat.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onIndexChangeListener(RangeBar rangeBar, int leftThumbIndex, int rightThumbIndex) {
                DecimalFormat deciFormat = new DecimalFormat("00");

                int minHour = leftThumbIndex / SMALLEST_HOUR_FRACTION;
                int minMinute = (int) ((leftThumbIndex % SMALLEST_HOUR_FRACTION) * (60 / (float) SMALLEST_HOUR_FRACTION));
                int maxHour = rightThumbIndex / SMALLEST_HOUR_FRACTION;
                int maxMinute = (int) ((rightThumbIndex % SMALLEST_HOUR_FRACTION) * (60 / (float) SMALLEST_HOUR_FRACTION));
                int minHourFinal = getMin(minHour);
                int maxHourFinal = getMax(maxHour);

                tv_start_time_sat.setText(deciFormat.format(minHourFinal));
                tv_end_time_sat.setText(deciFormat.format(maxHourFinal));

                tv_minvalue_sat.setText(minHour + ":" + minHourFinal);
                tv_maxvalue_sat.setText(maxHour + ":" + maxHourFinal);

                tv_minvalue_sat.setText(deciFormat.format(minHourFinal) + ":" + deciFormat.format(minMinute));
                tv_maxvalue_sat.setText(deciFormat.format(maxHourFinal) + ":" + deciFormat.format(maxMinute));
            }
        });

        final CrystalRangeSeekbar rangeSeekbar = (CrystalRangeSeekbar) findViewById(R.id.rangeSeekbar1);
        tv_minvalue_price = (TextView) findViewById(R.id.tv_minvalue_price);
        tv_maxvalue_price = (TextView) findViewById(R.id.tv_maxvalue_price);
//
        rangeSeekbar.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {
                tv_minvalue_price.setText(String.valueOf(minValue));
                tv_maxvalue_price.setText(String.valueOf(maxValue));
            }
        });
// set final value listener
//        rangeSeekbar.setOnRangeSeekbarFinalValueListener(new OnRangeSeekbarFinalValueListener() {
//            @Override
//            public void finalValue(Number minValue, Number maxValue) {
//                Log.d("CRS=>", String.valueOf(minValue) + " : " + String.valueOf(maxValue));
//            }
//        });


    }


    public int getMin(int minHour) {
        if (minHour == 13) {
            minpm = "pm";
            minHour = 1;
        }
        if (minHour == 14) {
            minpm = "pm";
            minHour = 2;
        }
        if (minHour == 15) {
            minpm = "pm";
            minHour = 3;

        }
        if (minHour == 16) {
            minpm = "pm";
            minHour = 4;
        }
        if (minHour == 17) {
            minpm = "pm";
            minHour = 5;
        }
        if (minHour == 18) {
            minHour = 6;
        }
        if (minHour == 19) {
            minpm = "pm";
            minHour = 7;
        }
        if (minHour == 20) {
            minpm = "pm";
            minHour = 8;
        }
        if (minHour == 21) {
            minpm = "pm";
            minHour = 9;
        }
        if (minHour == 22) {
            minpm = "pm";
            minHour = 10;
        }
        if (minHour == 23) {
            minpm = "pm";
            minHour = 11;
        }
        if (minHour == 24) {
            minpm = "pm";
            minHour = 12;
        }

        return minHour;
    }

    public int getMax(int maxHour) {
        if (maxHour == 13) {
            maxpm = "pm";
            maxHour = 1;
        }
        if (maxHour == 14) {
            maxpm = "pm";
            maxHour = 2;
        }
        if (maxHour == 15) {
            maxpm = "pm";
            maxHour = 3;

        }
        if (maxHour == 16) {
            maxpm = "pm";
            maxHour = 4;
        }
        if (maxHour == 17) {
            maxpm = "pm";
            maxHour = 5;
        }
        if (maxHour == 18) {
            maxpm = "pm";
            maxHour = 6;
        }
        if (maxHour == 19) {
            maxpm = "pm";
            maxHour = 7;
        }
        if (maxHour == 20) {
            maxpm = "pm";
            maxHour = 8;
        }
        if (maxHour == 21) {
            maxpm = "pm";
            maxHour = 9;
        }
        if (maxHour == 22) {
            maxpm = "pm";
            maxHour = 10;
        }
        if (maxHour == 23) {
            maxpm = "pm";
            maxHour = 11;
        }
        if (maxHour == 24) {
            maxpm = "pm";
            maxHour = 12;
        }

        return maxHour;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.tv_sun:

                if (lay_time_sun.getVisibility() == v.VISIBLE) {

                    if (daysList != null && daysList.size() > 0) {
                        daysList.remove("sun");
                    }

                    lay_time_sun.setVisibility(View.GONE);
                    time_detail_sun.setVisibility(View.GONE);
                    tv_minvalue_sun.setText("00:00");
                    tv_maxvalue_sun.setText("00:00");

                    tv_sun.setTextColor(getResources().getColor(R.color.light_gray));
                    tv_sun.setBackgroundResource(R.drawable.shape_textview_circle);

                } else {
                    daysList.add("sun");
                    lay_time_sun.setVisibility(View.VISIBLE);
                    tv_sun.setBackgroundResource(R.drawable.shape_textview_circle_bg);
                    tv_sun.setTextColor(getResources().getColor(R.color.white));
                }
                break;
            case R.id.tv_mon:
                if (lay_time_mon.getVisibility() == v.VISIBLE) {
                    if (daysList != null && daysList.size() > 0) {
                        daysList.remove("mon");
                    }
                    lay_time_mon.setVisibility(View.GONE);
                    time_detail_mon.setVisibility(View.GONE);
                    tv_minvalue_mon.setText("00:00");
                    tv_maxvalue_mon.setText("00:00");
                    tv_mon.setTextColor(getResources().getColor(R.color.light_gray));
                    tv_mon.setBackgroundResource(R.drawable.shape_textview_circle);

                } else {
                    daysList.add("mon");
                    lay_time_mon.setVisibility(View.VISIBLE);
                    tv_mon.setBackgroundResource(R.drawable.shape_textview_circle_bg);
                    tv_mon.setTextColor(getResources().getColor(R.color.white));
                }
                break;
            case R.id.tv_tue:
                if (lay_time_tue.getVisibility() == v.VISIBLE) {
                    if (daysList != null && daysList.size() > 0) {
                        daysList.remove("tue");
                    }
                    lay_time_tue.setVisibility(View.GONE);
                    time_detail_tue.setVisibility(View.GONE);
                    tv_minvalue_tue.setText("00:00");
                    tv_maxvalue_tue.setText("00:00");
                    tv_tue.setTextColor(getResources().getColor(R.color.light_gray));
                    tv_tue.setBackgroundResource(R.drawable.shape_textview_circle);

                } else {
                    daysList.add("tue");
                    lay_time_tue.setVisibility(View.VISIBLE);
                    tv_tue.setBackgroundResource(R.drawable.shape_textview_circle_bg);
                    tv_tue.setTextColor(getResources().getColor(R.color.white));
                }
                break;
            case R.id.tv_wed:

                if (lay_time_wed.getVisibility() == v.VISIBLE) {
                    if (daysList != null && daysList.size() > 0) {
                        daysList.remove("wed");
                    }
                    lay_time_wed.setVisibility(View.GONE);
                    time_detail_wed.setVisibility(View.GONE);
                    tv_minvalue_wed.setText("00:00");
                    tv_maxvalue_wed.setText("00:00");
                    tv_wed.setTextColor(getResources().getColor(R.color.light_gray));
                    tv_wed.setBackgroundResource(R.drawable.shape_textview_circle);

                } else {
                    daysList.add("wed");
                    lay_time_wed.setVisibility(View.VISIBLE);
                    tv_wed.setBackgroundResource(R.drawable.shape_textview_circle_bg);
                    tv_wed.setTextColor(getResources().getColor(R.color.white));
                }
                break;

            case R.id.tv_thu:
                if (lay_time_thu.getVisibility() == v.VISIBLE) {
                    if (daysList != null && daysList.size() > 0) {
                        daysList.remove("thu");
                    }
                    lay_time_thu.setVisibility(View.GONE);
                    time_detail_thu.setVisibility(View.GONE);
                    tv_minvalue_thu.setText("00:00");
                    tv_maxvalue_thu.setText("00:00");
                    tv_thu.setTextColor(getResources().getColor(R.color.light_gray));
                    tv_thu.setBackgroundResource(R.drawable.shape_textview_circle);

                } else {
                    daysList.add("thu");
                    lay_time_thu.setVisibility(View.VISIBLE);
                    tv_thu.setBackgroundResource(R.drawable.shape_textview_circle_bg);
                    tv_thu.setTextColor(getResources().getColor(R.color.white));
                }
                break;

            case R.id.tv_fri:
                if (lay_time_fri.getVisibility() == v.VISIBLE) {
                    if (daysList != null && daysList.size() > 0) {
                        daysList.remove("fri");
                    }
                    lay_time_fri.setVisibility(View.GONE);
                    time_detail_fri.setVisibility(View.GONE);
                    tv_minvalue_fri.setText("00:00");
                    tv_maxvalue_fri.setText("00:00");
                    tv_fri.setTextColor(getResources().getColor(R.color.light_gray));
                    tv_fri.setBackgroundResource(R.drawable.shape_textview_circle);

                } else {
                    daysList.add("fri");
                    lay_time_fri.setVisibility(View.VISIBLE);
                    tv_fri.setBackgroundResource(R.drawable.shape_textview_circle_bg);
                    tv_fri.setTextColor(getResources().getColor(R.color.white));
                }
                break;
            case R.id.tv_sat:
                if (lay_time_sat.getVisibility() == v.VISIBLE) {
                    if (daysList != null && daysList.size() > 0) {
                        daysList.remove("sat");
                    }
                    lay_time_sat.setVisibility(View.GONE);
                    time_detail_sat.setVisibility(View.GONE);
                    tv_minvalue_sat.setText("00:00");
                    tv_maxvalue_sat.setText("00:00");
                    tv_sat.setTextColor(getResources().getColor(R.color.light_gray));
                    tv_sat.setBackgroundResource(R.drawable.shape_textview_circle);

                } else {
                    daysList.add("sat");
                    lay_time_sat.setVisibility(View.VISIBLE);
                    tv_sat.setBackgroundResource(R.drawable.shape_textview_circle_bg);
                    tv_sat.setTextColor(getResources().getColor(R.color.white));
                }
                break;
            case R.id.lay_time_sun:
                if (time_detail_sun.getVisibility() == v.VISIBLE) {
                    time_detail_sun.setVisibility(View.GONE);
                } else {
                    time_detail_sun.setVisibility(View.VISIBLE);

                }
                break;
            case R.id.lay_time_mon:
                if (time_detail_mon.getVisibility() == v.VISIBLE) {
                    time_detail_mon.setVisibility(View.GONE);
                } else {
                    time_detail_mon.setVisibility(View.VISIBLE);

                }
                break;
            case R.id.lay_time_tue:
                if (time_detail_tue.getVisibility() == v.VISIBLE) {
                    time_detail_tue.setVisibility(View.GONE);
                } else {
                    time_detail_tue.setVisibility(View.VISIBLE);

                }
                break;
            case R.id.lay_time_wed:
                if (time_detail_wed.getVisibility() == v.VISIBLE) {
                    time_detail_wed.setVisibility(View.GONE);
                } else {
                    time_detail_wed.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.lay_time_thu:
                if (time_detail_thu.getVisibility() == v.VISIBLE) {
                    time_detail_thu.setVisibility(View.GONE);
                } else {
                    time_detail_thu.setVisibility(View.VISIBLE);

                }
                break;
            case R.id.lay_time_fri:
                if (time_detail_fri.getVisibility() == v.VISIBLE) {
                    time_detail_fri.setVisibility(View.GONE);
                } else {
                    time_detail_fri.setVisibility(View.VISIBLE);

                }
                break;
            case R.id.lay_time_sat:
                if (time_detail_sat.getVisibility() == v.VISIBLE) {
                    time_detail_sat.setVisibility(View.GONE);
                } else {
                    time_detail_sat.setVisibility(View.VISIBLE);

                }
                break;
            case R.id.tv_start_date:
                startDateCalender();
                break;
            case R.id.tv_end_date:
                endDateCalender();
                break;
            case R.id.tv_start_time:
                starttime();
                break;
            case R.id.tv_end_time:
                endtime();
                break;
            case R.id.iv_back_postjob:
                onBackPressed();
                break;
            case R.id.btn_postjob:
                // addValue();
                // getValue();

//                for (int i = 0; i < daysList.size(); i++) {
//                    File file = new File(daysList.get(i));
//                    Log.e("dayList", "" + daysList.toString());
//                    RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
//                    MultipartBody.Part body = MultipartBody.Part.createFormData("days[]", file.getName(), requestFile);
//                    daysPartList.add(body);
//                }

                checkValidation();
                break;
        }


    }

    private void getValue() {
        title = et_titile_postjob.getText().toString();
        location = et_address.getText().toString();
        user_msg = et_job_desc_postjob.getText().toString();
        startdate = tv_start_date.getText().toString();
        enddate = tv_end_date.getText().toString();
        maxPrice = tv_maxvalue_price.getText().toString();
        minPrice = tv_minvalue_price.getText().toString();
    }

    public void addValue() {
        startTimeList = new ArrayList<String>();
        endTimeList = new ArrayList<String>();

        sunMinValue = tv_minvalue_sun.getText().toString();
        sunMaxValue = tv_maxvalue_sun.getText().toString();
        monMinValue = tv_minvalue_mon.getText().toString();
        monMaxValue = tv_maxvalue_mon.getText().toString();
        tueMinValue = tv_minvalue_tue.getText().toString();
        tueMaxValue = tv_maxvalue_tue.getText().toString();
        wedMinValue = tv_minvalue_wed.getText().toString();
        wedMaxValue = tv_maxvalue_wed.getText().toString();
        thuMinValue = tv_minvalue_thu.getText().toString();
        thuMaxValue = tv_maxvalue_thu.getText().toString();
        friMinValue = tv_minvalue_fri.getText().toString();
        friMaxValue = tv_maxvalue_fri.getText().toString();
        satMinValue = tv_minvalue_sat.getText().toString();
        satMaxValue = tv_maxvalue_sat.getText().toString();


        if (sunMinValue != null && !sunMinValue.equalsIgnoreCase("00:00")) {
            startTimeList.add(sunMinValue);
        }
        if (monMinValue != null && !monMinValue.equalsIgnoreCase("00:00")) {
            startTimeList.add(monMinValue);
        }
        if (tueMinValue != null && !tueMinValue.equalsIgnoreCase("00:00")) {
            startTimeList.add(tueMinValue);
        }
        if (wedMinValue != null && !wedMinValue.equalsIgnoreCase("00:00")) {
            startTimeList.add(wedMinValue);
        }
        if (thuMinValue != null && !thuMinValue.equalsIgnoreCase("00:00")) {
            startTimeList.add(thuMinValue);
        }
        if (friMinValue != null && !friMinValue.equalsIgnoreCase("00:00")) {
            startTimeList.add(friMinValue);
        }
        if (satMinValue != null && !satMinValue.equalsIgnoreCase("00:00")) {
            startTimeList.add(satMinValue);
        }


        if (sunMaxValue != null && !sunMaxValue.equalsIgnoreCase("00:00")) {
            endTimeList.add(sunMaxValue);
        }
        if (monMaxValue != null && !monMaxValue.equalsIgnoreCase("00:00")) {
            endTimeList.add(monMaxValue);
        }
        if (tueMaxValue != null && !tueMaxValue.equalsIgnoreCase("00:00")) {
            endTimeList.add(tueMaxValue);
        }
        if (wedMaxValue != null && !wedMaxValue.equalsIgnoreCase("00:00")) {
            endTimeList.add(wedMaxValue);
        }
        if (thuMaxValue != null && !thuMaxValue.equalsIgnoreCase("00:00")) {
            endTimeList.add(thuMaxValue);
        }
        if (friMaxValue != null && !friMaxValue.equalsIgnoreCase("00:00")) {
            endTimeList.add(friMaxValue);
        }
        if (satMaxValue != null && !satMaxValue.equalsIgnoreCase("00:00")) {
            endTimeList.add(satMaxValue);
        }


        for (int i = 0; i < startTimeList.size(); i++) {
            File file = new File(startTimeList.get(i));
            Log.e("startTimeList", "" + startTimeList.toString());
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part body = MultipartBody.Part.createFormData("start_time[]", file.getName(), requestFile);
            startTimePartList.add(body);
        }
        for (int i = 0; i < endTimeList.size(); i++) {
            File file = new File(endTimeList.get(i));
            Log.e("endTimeList", "" + endTimeList.toString());
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part body = MultipartBody.Part.createFormData("end_time[]", file.getName(), requestFile);
            endTimePartList.add(body);
        }
    }


    public void startDateCalender() {

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

//            c.set(Calendar.MONTH, Calendar.JANUARY);
//            c.set(Calendar.DAY_OF_MONTH, 9);
//            c.set(Calendar.YEAR, 2015);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, R.style.calender_dialog_theme,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        tv_start_date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());

        datePickerDialog.show();
    }

    public void endDateCalender() {

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

//            c.set(Calendar.MONTH, mMonth);
//            c.set(Calendar.DAY_OF_MONTH, mDay);
//            c.set(Calendar.YEAR, mYear);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, R.style.calender_dialog_theme,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        tv_end_date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());

        datePickerDialog.show();
    }


    //Retrofit call api===============
/*
    private void postJob() {
        Utils.showDialog(ActivityPostJobClient.this, "Loading Please Wait...");
        Call<ResponseBody> resultCall = AppConfig.loadInterface().jobPost(
                userId,
                title,
                "indore",
                "6",
                user_msg,
                jobtype,
                daysPartList,
                catId,
                cateName,
                startdate,
                enddate,
                startTimePartList,
                endTimePartList,
                maxPrice,
                minPrice,
                count_number,
                "10");
        resultCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                Utils.dismissDialog();
                if (response.isSuccessful()) {
                    try {
                        String responedata = response.body().string();
                        Log.e("Post response", " " + responedata);
                        JSONObject object = new JSONObject(responedata);
                        String result = object.getString("result");
                        if (result.equalsIgnoreCase("true")) {
                            Log.e("Post Response", responedata);

                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            Toast.makeText(getApplicationContext(), "Successfully Posted", Toast.LENGTH_LONG).show();

                        } else {
                            String message = object.getString("msg");
                            Toast.makeText(getApplicationContext(), "" + message, Toast.LENGTH_SHORT).show();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.e("Error", "This is else part");

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Utils.dismissDialog();
                Toast.makeText(getApplicationContext(), "Server problem", Toast.LENGTH_SHORT).show();
            }
        });
    }
*/
    private void checkValidation() {
        String title = et_titile_postjob.getText().toString();
        String address = et_address.getText().toString();
        String desc = et_job_desc_postjob.getText().toString();
        String startdate = tv_start_date.getText().toString();
        String enddate = tv_end_date.getText().toString();
        String startTime = tv_start_time.getText().toString();
        String endTime = tv_end_time.getText().toString();

        Validation validation = new Validation(this);

        if (!validation.isEmpty(title)) {
            ToastClass.showToast(this, getString(R.string.fullname_v));
            et_titile_postjob.requestFocus();

        } else if (!validation.isEmpty(address)) {
            ToastClass.showToast(this, getString(R.string.address_v));
            et_address.requestFocus();
        } else if (!validation.isEmpty(desc)) {
            ToastClass.showToast(this, getString(R.string.desc));
            et_job_desc_postjob.requestFocus();
        } else if (!validation.isEmpty(startdate)) {
            ToastClass.showToast(this, getString(R.string.startdate));
            tv_start_date.requestFocus();
        } else if (!validation.isEmpty(enddate)) {
            ToastClass.showToast(this, getString(R.string.enddate));
            tv_end_date.requestFocus();
        } else if (!validation.isEmpty(startTime)) {
            ToastClass.showToast(this, getString(R.string.starttime));
            tv_start_time.requestFocus();
        }else if (!validation.isEmpty(endTime)) {
            ToastClass.showToast(this, getString(R.string.endtime));
            tv_end_time.requestFocus();
        } else {

            if (NetworkUtil.isNetworkConnected(this)) {
                try {
                    url_postJob = API.BASE_URL + "jobPost";
                    url_getCategory = API.BASE_URL + "getCategory";
                    Log.e("postJob URL = ", url_postJob);
                    postJob(url_postJob);
                } catch (NullPointerException e) {
                    ToastClass.showToast(this, getString(R.string.too_slow));
                }
            } else {
                ToastClass.showToast(this, getString(R.string.no_internet_access));
            }
        }
    }


    private void postJob(String url) {

        Utils.showDialog(this, "Loading Please Wait...");
        AndroidNetworking.post(url)
                .addBodyParameter("title", et_titile_postjob.getText().toString())
                .addBodyParameter("location", et_address.getText().toString())
                .addBodyParameter("distance", "6")
                .addBodyParameter("user_job_msg", et_job_desc_postjob.getText().toString())
                .addBodyParameter("cat_id", catId)
                .addBodyParameter("user_id", userId)
                .addBodyParameter("start_date", tv_start_date.getText().toString())
                .addBodyParameter("end_date", tv_end_date.getText().toString())
                .addBodyParameter("start_time", tv_start_time.getText().toString())
                .addBodyParameter("end_time", tv_end_time.getText().toString())
                .addBodyParameter("max_price", tv_maxvalue_price.getText().toString())
                .addBodyParameter("min_price", tv_minvalue_price.getText().toString())
                .addBodyParameter("doller_rate", tv_minvalue_price.getText().toString())
                .addBodyParameter("cate_name", cateName)

                .setTag("post job")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Utils.dismissDialog();
                        Log.e("PostJob list resp = ", "" + jsonObject);
                        try {
                            String item_in_message = jsonObject.getString("msg");
                            String result = jsonObject.getString("result");
                            //Log.e("catId AferPost", "" + catId);
                            if (result.equalsIgnoreCase("true")) {
//                                JSONArray jsonArray = jsonObject.getJSONArray("data");
//                                for (int i = 0; i < jsonArray.length(); i++) {
//                                    JSONObject applicants = jsonArray.getJSONObject(i);
//                                    ApplicantListData applicantData = new ApplicantListData();
////                                    applicantData.job_id = applicants.getString("job_id");
//
////                                    applicantList.add(applicantData);
//                                    Toast.makeText(getApplicationContext(), "Successfully post Job", Toast.LENGTH_LONG).show();
//                                }
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
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

    private void getcategoryList(String cat_url) {
        Utils.showDialog(getApplicationContext(), "Loading Please Wait...");
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
                            } else ToastClass.showToast(getApplicationContext(), message);

                            ArrayAdapter<CategoryListData> aa = new ArrayAdapter<CategoryListData>(getApplicationContext(), android.R.layout.simple_spinner_item, catagoryList) {
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
                            spinner_cat.setAdapter(aa);

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
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        catId = catagoryList.get(position).id;
        cateName = catagoryList.get(position).name;

        ((TextView) spinner_cat.getSelectedView()).setTextColor(getResources().getColor(R.color.black));
        //Log.e("select specialization =", catId + "name = " + catagoryList.get(position).name);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void starttime() {
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, R.style.calender_dialog_theme,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {

                        tv_start_time.setText(hourOfDay + ":" + minute);
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }

    public void endtime() {
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, R.style.calender_dialog_theme,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {

                        tv_end_time.setText(hourOfDay + ":" + minute);
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }

}

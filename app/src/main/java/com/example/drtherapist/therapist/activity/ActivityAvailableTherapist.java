package com.example.drtherapist.therapist.activity;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.drtherapist.R;
import com.example.drtherapist.common.Utils.ToastClass;
import com.example.drtherapist.common.Utils.Utils;
import com.example.drtherapist.common.remote.API;
import com.example.drtherapist.common.remote.Session;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ActivityAvailableTherapist extends AppCompatActivity implements View.OnClickListener {

    private int mYear, mMonth, mDay, mHour, mMinute;
    private Session session;
    String userId, catId;
    Button submit;
       /*layout for monday*/
    LinearLayout mLinear_monday_startTime1,mLinear_monday_endtime1,mLinear_monday_startTime2,mLinear_monday_endtime2;
    TextView mText_starttime_monday1,mText_endtime_monday1,mText_starttime_monday2,mText_endtime_monday2;
    String mLinear_starttime_mon1,mLinear_endtime_mon1,mLinear_starttime_mon2,mLinear_endtime_mon2;

       /*layout for tuesday*/
    LinearLayout mLinear_tuesday_startTime1,mLinear_tuesday_endTime1,mLinear_tuesday_startTime2,mLinear_tuesday_endTime2;
    TextView mText_starttime_tuesday1,mText_endtime_tuesday1,mText_starttime_tuesday2,mText_endtime_tuesday2;
    String mLinear_starttime_tue1,mLinear_endtime_tue1,mLinear_starttime_tue2,mLinear_endtime_tue2;


    /*layout for wednesday*/
    LinearLayout mLinear_wednesday_startTime1,mLinear_wednesday_endTime1,mLinear_wednesday_startTime2,mLinear_wednesday_endTime2;
    TextView mText_starttime_wednesday1,mText_endtime_wednesday1,mText_starttime_wednesday2,mText_endtime_wednesday2;
    String mLinear_starttime_wed1,mLinear_endtime_wed1,mLinear_starttime_wed2,mLinear_endtime_wed2;


    /*layout for thursday*/
    LinearLayout mLinear_thursday_startTime1,mLinear_thursday_endTime1,mLinear_thursday_startTime2,mLinear_thursday_endTime2;
    TextView mText_starttime_thursday1,mText_endtime_thursday1,mText_starttime_thursday2,mText_endtime_thursday2;
    String mLinear_starttime_thu1,mLinear_endtime_thu1,mLinear_starttime_thu2,mLinear_endtime_thu2;


    /*layout for friday*/
    LinearLayout mLinear_friday_startTime1,mLinear_friday_endTime1,mLinear_friday_startTime2,mLinear_friday_endTime2;
    TextView mText_starttime_friday1,mText_endtime_friday1,mText_starttime_friday2,mText_endtime_friday2;
    String mLinear_starttime_fri1,mLinear_endtime_fri1,mLinear_starttime_fri2,mLinear_endtime_fri2;


    /*layout for saturday*/
    LinearLayout mLinear_saturday_startTime1,mLinear_saturday_endTime1,mLinear_saturday_startTime2,mLinear_saturday_endTime2;
    TextView mText_starttime_saturday,mText_endtime_saturday1,mText_starttime_saturday2,mText_endtime_saturday2;
    String mLinear_starttime_sat1,mLinear_endtime_sat1,mLinear_starttime_sat2,mLinear_endtime_sat2;


    /*layout for sunday*/
    LinearLayout mLinear_sunday_startTime1,mLinear_sunday_endTime1,mLinear_sunday_startTime2,mLinear_sunday_endTime2;
    TextView mText_starttime_sunday,mText_endtime_sunday,mText_starttime_sunday2,mText_endtime_sunday2;
    String mLinear_starttime_sun1,mLinear_endtime_sun1,mLinear_starttime_sun2,mLinear_endtime_sun2;


    String mon_DayString,tue_DayString,wed_DayString,thu_DayString,fri_DayString,sat_DayString,sun_DayString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_therapist);
        initView();


        session = new Session(this);
        if (session != null) {
            userId = session.getUser().id;
          //  catId = session.getUser().cat_id;
        }


    }


    public void initView()
    {

           /*for monday*/
        //linearlayout
        mLinear_monday_startTime1=findViewById(R.id.mLinear_monday_startTime1);
        mLinear_monday_endtime1=findViewById(R.id.mLinear_monday_endtime1);
        mLinear_monday_startTime2=findViewById(R.id.mLinear_monday_startTime2);
        mLinear_monday_endtime2=findViewById(R.id.mLinear_monday_endtime2);

        mLinear_monday_startTime1.setOnClickListener(this);
        mLinear_monday_endtime1.setOnClickListener(this);
        mLinear_monday_startTime2.setOnClickListener(this);
        mLinear_monday_endtime2.setOnClickListener(this);

        //textview
        mText_starttime_monday1=findViewById(R.id.mText_starttime_monday1);
        mText_endtime_monday1=findViewById(R.id.mText_endtime_monday1);
        mText_starttime_monday2=findViewById(R.id.mText_starttime_monday2);
        mText_endtime_monday2=findViewById(R.id.mText_endtime_monday2);



        /////////////////////////////////////////////////////////////////////////////////////////////

        /*for tuesday*/

        //linearlayout
        mLinear_tuesday_startTime1=findViewById(R.id.mLinear_tuesday_startTime1);
        mLinear_tuesday_endTime1=findViewById(R.id.mLinear_tuesday_endTime1);
        mLinear_tuesday_startTime2=findViewById(R.id.mLinear_tuesday_startTime2);
        mLinear_tuesday_endTime2=findViewById(R.id.mLinear_tuesday_endTime2);

        mLinear_tuesday_startTime1.setOnClickListener(this);
        mLinear_tuesday_endTime1.setOnClickListener(this);
        mLinear_tuesday_startTime2.setOnClickListener(this);
        mLinear_tuesday_endTime2.setOnClickListener(this);


        //textview
        mText_starttime_tuesday1=findViewById(R.id.mText_starttime_tuesday1);
        mText_endtime_tuesday1=findViewById(R.id.mText_endtime_tuesday1);
        mText_starttime_tuesday2=findViewById(R.id.mText_starttime_tuesday2);
        mText_endtime_tuesday2=findViewById(R.id.mText_endtime_tuesday2);


        ////////////////////////////////////////////////////////////////////////////////////

      /*  for wednesday*/




        //linearlayout
        mLinear_wednesday_startTime1=findViewById(R.id.mLinear_wednesday_startTime1);
        mLinear_wednesday_endTime1=findViewById(R.id.mLinear_wednesday_endTime1);
        mLinear_wednesday_startTime2=findViewById(R.id.mLinear_wednesday_startTime2);
        mLinear_wednesday_endTime2=findViewById(R.id.mLinear_wednesday_endTime2);

        mLinear_wednesday_startTime1.setOnClickListener(this);
        mLinear_wednesday_endTime1.setOnClickListener(this);
        mLinear_wednesday_startTime2.setOnClickListener(this);
        mLinear_wednesday_endTime2.setOnClickListener(this);


        //textview
        mText_starttime_wednesday1=findViewById(R.id.mText_starttime_wednesday1);
        mText_endtime_wednesday1=findViewById(R.id.mText_endtime_wednesday1);
        mText_starttime_wednesday2=findViewById(R.id.mText_starttime_wednesday2);
        mText_endtime_wednesday2=findViewById(R.id.mText_endtime_wednesday2);


        /////////////////////////////////////////////////////////////////////////////////////////



      /*  for thursday*/



        //linearlayout
        mLinear_thursday_startTime1=findViewById(R.id.mLinear_thursday_startTime1);
        mLinear_thursday_endTime1=findViewById(R.id.mLinear_thursday_endTime1);
        mLinear_thursday_startTime2=findViewById(R.id.mLinear_thursday_startTime2);
        mLinear_thursday_endTime2=findViewById(R.id.mLinear_thursday_endTime2);

        mLinear_thursday_startTime1.setOnClickListener(this);
        mLinear_thursday_endTime1.setOnClickListener(this);
        mLinear_thursday_startTime2.setOnClickListener(this);
        mLinear_thursday_endTime2.setOnClickListener(this);


        //textview
        mText_starttime_thursday1=findViewById(R.id.mText_starttime_thursday1);
        mText_endtime_thursday1=findViewById(R.id.mText_endtime_thursday1);
        mText_starttime_thursday2=findViewById(R.id.mText_starttime_thursday2);
        mText_endtime_thursday2=findViewById(R.id.mText_endtime_thursday2);

        ////////////////////////////////////////////////////////////////////////////////////

        /*  for friday*/


        //linearlayout
        mLinear_friday_startTime1=findViewById(R.id.mLinear_friday_startTime1);
        mLinear_friday_endTime1=findViewById(R.id.mLinear_friday_endTime1);
        mLinear_friday_startTime2=findViewById(R.id.mLinear_friday_startTime2);
        mLinear_friday_endTime2=findViewById(R.id.mLinear_friday_endTime2);

        mLinear_friday_startTime1.setOnClickListener(this);
        mLinear_friday_endTime1.setOnClickListener(this);
        mLinear_friday_startTime2.setOnClickListener(this);
        mLinear_friday_endTime2.setOnClickListener(this);


        //textview
        mText_starttime_friday1=findViewById(R.id.mText_starttime_friday1);
        mText_endtime_friday1=findViewById(R.id.mText_endtime_friday1);
        mText_starttime_friday2=findViewById(R.id.mText_starttime_friday2);
        mText_endtime_friday2=findViewById(R.id.mText_endtime_friday2);

        ////////////////////////////////////////////////////////////////////////////////////////////






        /*  for saturday*/


        //linearlayout
        mLinear_saturday_startTime1=findViewById(R.id.mLinear_saturday_startTime1);
        mLinear_saturday_endTime1=findViewById(R.id.mLinear_saturday_endTime1);
        mLinear_saturday_startTime2=findViewById(R.id.mLinear_saturday_startTime2);
        mLinear_saturday_endTime2=findViewById(R.id.mLinear_saturday_endTime2);

        mLinear_saturday_startTime1.setOnClickListener(this);
        mLinear_saturday_endTime1.setOnClickListener(this);
        mLinear_saturday_startTime2.setOnClickListener(this);
        mLinear_saturday_endTime2.setOnClickListener(this);


        //textview
        mText_starttime_saturday=findViewById(R.id.mText_starttime_saturday);
        mText_endtime_saturday1=findViewById(R.id.mText_endtime_saturday1);
        mText_starttime_saturday2=findViewById(R.id.mText_starttime_saturday2);
        mText_endtime_saturday2=findViewById(R.id.mText_endtime_saturday2);

        ////////////////////////////////////////////////////////////////////////////////////////




        /*   for sunday*/




        //linearlayout
        mLinear_sunday_startTime1=findViewById(R.id.mLinear_sunday_startTime1);
        mLinear_sunday_endTime1=findViewById(R.id.mLinear_sunday_endTime1);
        mLinear_sunday_startTime2=findViewById(R.id.mLinear_sunday_startTime2);
        mLinear_sunday_endTime2=findViewById(R.id.mLinear_sunday_endTime2);

        mLinear_sunday_startTime1.setOnClickListener(this);
        mLinear_sunday_endTime1.setOnClickListener(this);
        mLinear_sunday_startTime2.setOnClickListener(this);
        mLinear_sunday_endTime2.setOnClickListener(this);


        //textview
        mText_starttime_sunday=findViewById(R.id.mText_starttime_sunday);
        mText_endtime_sunday=findViewById(R.id.mText_endtime_sunday);
        mText_starttime_sunday2=findViewById(R.id.mText_starttime_sunday2);
        mText_endtime_sunday2=findViewById(R.id.mText_endtime_sunday2);





        ///////////on button click.///////////////
        submit=findViewById(R.id.submit);
        submit.setOnClickListener(this);






    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mLinear_monday_startTime1:
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                int  mHours=hourOfDay;
                                int  mMins=minute;

                                String AM_PM ;
                                if(hourOfDay < 12) {
                                    AM_PM = "AM";

                                } else {
                                    AM_PM = "PM";
                                    mHours=mHours-12;
                                }

                                mLinear_starttime_mon1 = (mHours+":"+mMins+" "+AM_PM);


                                mText_starttime_monday1.setText(mLinear_starttime_mon1);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();

                break;


            case R.id.mLinear_monday_endtime1:
                final Calendar c2 = Calendar.getInstance();
                mHour = c2.get(Calendar.HOUR_OF_DAY);
                mMinute = c2.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog2 = new TimePickerDialog(this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                int  mHours=hourOfDay;
                                int  mMins=minute;

                                String AM_PM ;
                                if(hourOfDay < 12) {
                                    AM_PM = "AM";

                                } else {
                                    AM_PM = "PM";
                                    mHours=mHours-12;
                                }
                                mLinear_endtime_mon1 = (mHours+":"+mMins+" "+AM_PM);


                                mText_endtime_monday1.setText(mLinear_endtime_mon1);


                            }
                        }, mHour, mMinute, false);
                timePickerDialog2.show();
                break;


            case R.id.mLinear_monday_startTime2:


                final Calendar c3 = Calendar.getInstance();
                mHour = c3.get(Calendar.HOUR_OF_DAY);
                mMinute = c3.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog3 = new TimePickerDialog(this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                int  mHours=hourOfDay;
                                int  mMins=minute;

                                String AM_PM ;
                                if(hourOfDay < 12) {
                                    AM_PM = "AM";

                                } else {
                                    AM_PM = "PM";
                                    mHours=mHours-12;
                                }
                                mLinear_starttime_mon2 = (mHours+":"+mMins+" "+AM_PM);
                                mText_starttime_monday2.setText(mLinear_starttime_mon2);

                            }
                        }, mHour, mMinute, false);
                timePickerDialog3.show();


                break;


            case R.id.mLinear_monday_endtime2:
                final Calendar c4 = Calendar.getInstance();
                mHour = c4.get(Calendar.HOUR_OF_DAY);
                mMinute = c4.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog4 = new TimePickerDialog(this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                int  mHours=hourOfDay;
                                int  mMins=minute;

                                String AM_PM ;
                                if(hourOfDay < 12) {
                                    AM_PM = "AM";

                                } else {
                                    AM_PM = "PM";
                                    mHours=mHours-12;
                                }
                                mLinear_endtime_mon2 = (mHours+":"+mMins+" "+AM_PM);
                                mText_endtime_monday2.setText(mLinear_endtime_mon2);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog4.show();
                break;
////////////////////////////////////////////////////Tuesday/////////////////////////////////////////////////////

            case R.id.mLinear_tuesday_startTime1:
                final Calendar c5 = Calendar.getInstance();
                mHour = c5.get(Calendar.HOUR_OF_DAY);
                mMinute = c5.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog5 = new TimePickerDialog(this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                int  mHours=hourOfDay;
                                int  mMins=minute;

                                String AM_PM ;
                                if(hourOfDay < 12) {
                                    AM_PM = "AM";

                                } else {
                                    AM_PM = "PM";
                                    mHours=mHours-12;
                                }
                                mLinear_starttime_tue1 = (mHours+":"+mMins+" "+AM_PM);
                                mText_starttime_tuesday1.setText(mLinear_starttime_tue1);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog5.show();
                break;


            case R.id.mLinear_tuesday_endTime1:
                final Calendar c6 = Calendar.getInstance();
                mHour = c6.get(Calendar.HOUR_OF_DAY);
                mMinute = c6.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog6 = new TimePickerDialog(this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                int  mHours=hourOfDay;
                                int  mMins=minute;

                                String AM_PM ;
                                if(hourOfDay < 12) {
                                    AM_PM = "AM";

                                } else {
                                    AM_PM = "PM";
                                    mHours=mHours-12;
                                }
                                mLinear_starttime_tue2 = (mHours+":"+mMins+" "+AM_PM);
                                mText_starttime_tuesday2.setText(mLinear_starttime_tue2);

                            }
                        }, mHour, mMinute, false);
                timePickerDialog6.show();
                break;



            case R.id.mLinear_tuesday_endTime2:
                final Calendar c7 = Calendar.getInstance();
                mHour = c7.get(Calendar.HOUR_OF_DAY);
                mMinute = c7.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog7 = new TimePickerDialog(this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                int  mHours=hourOfDay;
                                int  mMins=minute;

                                String AM_PM ;
                                if(hourOfDay < 12) {
                                    AM_PM = "AM";

                                } else {
                                    AM_PM = "PM";
                                    mHours=mHours-12;
                                }

                                mLinear_endtime_tue1 = (mHours+":"+mMins+" "+AM_PM);
                                mText_endtime_tuesday1.setText(mLinear_endtime_tue1);

                            }
                        }, mHour, mMinute, false);
                timePickerDialog7.show();

                break;


            case R.id.mLinear_tuesday_startTime2:
                final Calendar c8 = Calendar.getInstance();
                mHour = c8.get(Calendar.HOUR_OF_DAY);
                mMinute = c8.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog8 = new TimePickerDialog(this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                int  mHours=hourOfDay;
                                int  mMins=minute;

                                String AM_PM ;
                                if(hourOfDay < 12) {
                                    AM_PM = "AM";

                                } else {
                                    AM_PM = "PM";
                                    mHours=mHours-12;
                                }

                                mLinear_endtime_tue2 = (mHours+":"+mMins+" "+AM_PM);
                                mText_endtime_tuesday2.setText(mLinear_endtime_tue2);

                            }
                        }, mHour, mMinute, false);
                timePickerDialog8.show();

                break;

////////////////////////////////////////////////////wednesday///////////////////////////////

            case R.id.mLinear_wednesday_startTime1:
                final Calendar c9 = Calendar.getInstance();
                mHour = c9.get(Calendar.HOUR_OF_DAY);
                mMinute = c9.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog9 = new TimePickerDialog(this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                int  mHours=hourOfDay;
                                int  mMins=minute;

                                String AM_PM ;
                                if(hourOfDay < 12) {
                                    AM_PM = "AM";

                                } else {
                                    AM_PM = "PM";
                                    mHours=mHours-12;
                                }
                                mLinear_starttime_wed1 = (mHours+":"+mMins+" "+AM_PM);
                                mText_starttime_wednesday1.setText(mLinear_starttime_wed1);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog9.show();

                break;




            case R.id.mLinear_wednesday_endTime1:
                final Calendar c10 = Calendar.getInstance();
                mHour = c10.get(Calendar.HOUR_OF_DAY);
                mMinute = c10.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog10 = new TimePickerDialog(this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                int  mHours=hourOfDay;
                                int  mMins=minute;

                                String AM_PM ;
                                if(hourOfDay < 12) {
                                    AM_PM = "AM";

                                } else {
                                    AM_PM = "PM";
                                    mHours=mHours-12;
                                }
                                mLinear_endtime_wed1 = (mHours+":"+mMins+" "+AM_PM);
                                mText_endtime_wednesday1.setText(mLinear_endtime_wed1);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog10.show();

                break;





            case R.id.mLinear_wednesday_startTime2:
                final Calendar c11 = Calendar.getInstance();
                mHour = c11.get(Calendar.HOUR_OF_DAY);
                mMinute = c11.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog11 = new TimePickerDialog(this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                int  mHours=hourOfDay;
                                int  mMins=minute;

                                String AM_PM ;
                                if(hourOfDay < 12) {
                                    AM_PM = "AM";

                                } else {
                                    AM_PM = "PM";
                                    mHours=mHours-12;
                                }
                                mLinear_starttime_wed2 = (mHours+":"+mMins+" "+AM_PM);
                                mText_starttime_wednesday2.setText(mLinear_starttime_wed2);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog11.show();

                break;





            case R.id.mLinear_wednesday_endTime2:
                final Calendar c12 = Calendar.getInstance();
                mHour = c12.get(Calendar.HOUR_OF_DAY);
                mMinute = c12.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog12 = new TimePickerDialog(this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                int  mHours=hourOfDay;
                                int  mMins=minute;

                                String AM_PM ;
                                if(hourOfDay < 12) {
                                    AM_PM = "AM";

                                } else {
                                    AM_PM = "PM";
                                    mHours=mHours-12;
                                }
                                mLinear_endtime_wed2 =(mHours+":"+mMins+" "+AM_PM);
                                mText_endtime_wednesday2.setText(mLinear_endtime_wed2);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog12.show();

                break;
/////////////////////////////////////////////////thursday/////////////////////////////////////////////







            case R.id.mLinear_thursday_startTime1:
                final Calendar c13 = Calendar.getInstance();
                mHour = c13.get(Calendar.HOUR_OF_DAY);
                mMinute = c13.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog13 = new TimePickerDialog(this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                int  mHours=hourOfDay;
                                int  mMins=minute;

                                String AM_PM ;
                                if(hourOfDay < 12) {
                                    AM_PM = "AM";

                                } else {
                                    AM_PM = "PM";
                                    mHours=mHours-12;
                                }

                                mLinear_starttime_thu1 = (mHours+":"+mMins+" "+AM_PM);
                                mText_starttime_thursday1.setText(mLinear_starttime_thu1);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog13.show();

                break;




            case R.id.mLinear_thursday_endTime1:
                final Calendar c14 = Calendar.getInstance();
                mHour = c14.get(Calendar.HOUR_OF_DAY);
                mMinute = c14.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog14 = new TimePickerDialog(this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                int  mHours=hourOfDay;
                                int  mMins=minute;

                                String AM_PM ;
                                if(hourOfDay < 12) {
                                    AM_PM = "AM";

                                } else {
                                    AM_PM = "PM";
                                    mHours=mHours-12;
                                }

                                mLinear_endtime_thu1 = (mHours+":"+mMins+" "+AM_PM);
                                mText_endtime_thursday1.setText(mLinear_endtime_thu1);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog14.show();

                break;





            case R.id.mLinear_thursday_startTime2:
                final Calendar c15 = Calendar.getInstance();
                mHour = c15.get(Calendar.HOUR_OF_DAY);
                mMinute = c15.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog15 = new TimePickerDialog(this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                int  mHours=hourOfDay;
                                int  mMins=minute;

                                String AM_PM ;
                                if(hourOfDay < 12) {
                                    AM_PM = "AM";

                                } else {
                                    AM_PM = "PM";
                                    mHours=mHours-12;
                                }

                                mLinear_starttime_thu2 = (mHours+":"+mMins+" "+AM_PM);
                                mText_starttime_thursday2.setText(mLinear_starttime_thu2);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog15.show();

                break;





            case R.id.mLinear_thursday_endTime2:
                final Calendar c16 = Calendar.getInstance();
                mHour = c16.get(Calendar.HOUR_OF_DAY);
                mMinute = c16.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog16 = new TimePickerDialog(this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                int  mHours=hourOfDay;
                                int  mMins=minute;

                                String AM_PM ;
                                if(hourOfDay < 12) {
                                    AM_PM = "AM";

                                } else {
                                    AM_PM = "PM";
                                    mHours=mHours-12;
                                }

                                mLinear_endtime_thu2 = (mHours+":"+mMins+" "+AM_PM);
                                mText_endtime_thursday2.setText(mLinear_endtime_thu2);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog16.show();

                break;


       //////////////////////////////////////////////friday/////////////////////////////////////////


            case R.id.mLinear_friday_startTime1:
                final Calendar c17 = Calendar.getInstance();
                mHour = c17.get(Calendar.HOUR_OF_DAY);
                mMinute = c17.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog17 = new TimePickerDialog(this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                int  mHours=hourOfDay;
                                int  mMins=minute;

                                String AM_PM ;
                                if(hourOfDay < 12) {
                                    AM_PM = "AM";

                                } else {
                                    AM_PM = "PM";
                                    mHours=mHours-12;
                                }

                                mLinear_starttime_fri1 = (mHours+":"+mMins+" "+AM_PM);
                                mText_starttime_friday1.setText(mLinear_starttime_fri1);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog17.show();

                break;




            case R.id.mLinear_friday_endTime1:
                final Calendar c18 = Calendar.getInstance();
                mHour = c18.get(Calendar.HOUR_OF_DAY);
                mMinute = c18.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog18 = new TimePickerDialog(this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                int  mHours=hourOfDay;
                                int  mMins=minute;

                                String AM_PM ;
                                if(hourOfDay < 12) {
                                    AM_PM = "AM";

                                } else {
                                    AM_PM = "PM";
                                    mHours=mHours-12;
                                }

                                mLinear_endtime_fri1 = (mHours+":"+mMins+" "+AM_PM);
                                mText_endtime_friday1.setText(mLinear_endtime_fri1);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog18.show();

                break;





            case R.id.mLinear_friday_startTime2:
                final Calendar c19 = Calendar.getInstance();
                mHour = c19.get(Calendar.HOUR_OF_DAY);
                mMinute = c19.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog19 = new TimePickerDialog(this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                int  mHours=hourOfDay;
                                int  mMins=minute;

                                String AM_PM ;
                                if(hourOfDay < 12) {
                                    AM_PM = "AM";

                                } else {
                                    AM_PM = "PM";
                                    mHours=mHours-12;
                                }

                                mLinear_starttime_fri2 = (mHours+":"+mMins+" "+AM_PM);
                                mText_starttime_friday2.setText(mLinear_starttime_fri2);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog19.show();

                break;





            case R.id.mLinear_friday_endTime2:
                final Calendar c20 = Calendar.getInstance();
                mHour = c20.get(Calendar.HOUR_OF_DAY);
                mMinute = c20.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog20 = new TimePickerDialog(this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                int  mHours=hourOfDay;
                                int  mMins=minute;

                                String AM_PM ;
                                if(hourOfDay < 12) {
                                    AM_PM = "AM";

                                } else {
                                    AM_PM = "PM";
                                    mHours=mHours-12;
                                }

                                mLinear_endtime_fri2 = (mHours+":"+mMins+" "+AM_PM);
                                mText_endtime_friday2.setText(mLinear_endtime_fri2);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog20.show();

                break;


        ///////////////////////////////////////////////////saturday/////////////////////////



            case R.id.mLinear_saturday_startTime1:
                final Calendar c21 = Calendar.getInstance();
                mHour = c21.get(Calendar.HOUR_OF_DAY);
                mMinute = c21.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog21 = new TimePickerDialog(this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                int  mHours=hourOfDay;
                                int  mMins=minute;

                                String AM_PM ;
                                if(hourOfDay < 12) {
                                    AM_PM = "AM";

                                } else {
                                    AM_PM = "PM";
                                    mHours=mHours-12;
                                }

                                mLinear_starttime_sat1 = (mHours+":"+mMins+" "+AM_PM);
                                mText_starttime_saturday.setText(mLinear_starttime_sat1);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog21.show();

                break;




            case R.id.mLinear_saturday_endTime1:
                final Calendar c22 = Calendar.getInstance();
                mHour = c22.get(Calendar.HOUR_OF_DAY);
                mMinute = c22.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog22 = new TimePickerDialog(this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                int  mHours=hourOfDay;
                                int  mMins=minute;

                                String AM_PM ;
                                if(hourOfDay < 12) {
                                    AM_PM = "AM";

                                } else {
                                    AM_PM = "PM";
                                    mHours=mHours-12;
                                }

                                mLinear_endtime_sat1 = (mHours+":"+mMins+" "+AM_PM);
                                mText_endtime_saturday1.setText(mLinear_endtime_sat1);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog22.show();

                break;





            case R.id.mLinear_saturday_startTime2:
                final Calendar c23 = Calendar.getInstance();
                mHour = c23.get(Calendar.HOUR_OF_DAY);
                mMinute = c23.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog23 = new TimePickerDialog(this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                int  mHours=hourOfDay;
                                int  mMins=minute;

                                String AM_PM ;
                                if(hourOfDay < 12) {
                                    AM_PM = "AM";

                                } else {
                                    AM_PM = "PM";
                                    mHours=mHours-12;
                                }

                                mLinear_starttime_sat2 = (mHours+":"+mMins+" "+AM_PM);
                                mText_starttime_saturday2.setText(mLinear_starttime_sat2);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog23.show();

                break;





            case R.id.mLinear_saturday_endTime2:
                final Calendar c24 = Calendar.getInstance();
                mHour = c24.get(Calendar.HOUR_OF_DAY);
                mMinute = c24.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog24 = new TimePickerDialog(this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                int  mHours=hourOfDay;
                                int  mMins=minute;

                                String AM_PM ;
                                if(hourOfDay < 12) {
                                    AM_PM = "AM";

                                } else {
                                    AM_PM = "PM";
                                    mHours=mHours-12;
                                }

                                mLinear_endtime_sat2 = (mHours+":"+mMins+" "+AM_PM);
                                mText_endtime_saturday2.setText(mLinear_endtime_sat2);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog24.show();

                break;

/////////////////////////////////////////////////sunday///////////////////////////////////////





            case R.id.mLinear_sunday_startTime1:
                final Calendar c25 = Calendar.getInstance();
                mHour = c25.get(Calendar.HOUR_OF_DAY);
                mMinute = c25.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog25 = new TimePickerDialog(this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                int  mHours=hourOfDay;
                                int  mMins=minute;

                                String AM_PM ;
                                if(hourOfDay < 12) {
                                    AM_PM = "AM";

                                } else {
                                    AM_PM = "PM";
                                    mHours=mHours-12;
                                }

                                mLinear_starttime_sun1 = (mHours+":"+mMins+" "+AM_PM);
                                mText_starttime_sunday.setText(mLinear_starttime_sun1);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog25.show();

                break;




            case R.id.mLinear_sunday_endTime1:
                final Calendar c26 = Calendar.getInstance();
                mHour = c26.get(Calendar.HOUR_OF_DAY);
                mMinute = c26.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog26 = new TimePickerDialog(this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                int  mHours=hourOfDay;
                                int  mMins=minute;

                                String AM_PM ;
                                if(hourOfDay < 12) {
                                    AM_PM = "AM";

                                } else {
                                    AM_PM = "PM";
                                    mHours=mHours-12;
                                }

                                mLinear_endtime_sun1 = (mHours+":"+mMins+" "+AM_PM);
                                mText_endtime_sunday.setText(mLinear_endtime_sun1);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog26.show();

                break;





            case R.id.mLinear_sunday_startTime2:
                final Calendar c27 = Calendar.getInstance();
                mHour = c27.get(Calendar.HOUR_OF_DAY);
                mMinute = c27.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog27 = new TimePickerDialog(this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                int  mHours=hourOfDay;
                                int  mMins=minute;

                                String AM_PM ;
                                if(hourOfDay < 12) {
                                    AM_PM = "AM";

                                } else {
                                    AM_PM = "PM";
                                    mHours=mHours-12;
                                }

                                mLinear_starttime_sun2 = (mHours+":"+mMins+" "+AM_PM);
                                mText_starttime_sunday2.setText(mLinear_starttime_sun2);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog27.show();

                break;


            case R.id.mLinear_sunday_endTime2:
                final Calendar c28 = Calendar.getInstance();
                mHour = c28.get(Calendar.HOUR_OF_DAY);
                mMinute = c28.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog28 = new TimePickerDialog(this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                              int  mHours=hourOfDay;
                              int  mMins=minute;

                                String AM_PM ;
                                if(hourOfDay < 12) {
                                    AM_PM = "AM";

                                } else {
                                    AM_PM = "PM";
                                    mHours=mHours-12;
                                }

                              //  mLinear_endtime_sun2 = hourOfDay + ":" + minute ;
                                mLinear_endtime_sun2 = (mHours+":"+mMins+" "+AM_PM);
                                mText_endtime_sunday2.setText(mLinear_endtime_sun2);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog28.show();

                break;



                //********************button final submit***************


            case R.id.submit:
                String url = API.BASE_URL + "post_doctor_time_table";

                //////////////for monday///////////////
                if (mLinear_starttime_mon1==null && mLinear_endtime_mon1 == null)
                {
                    mLinear_starttime_mon1="";
                    mLinear_endtime_mon1="";
                    mon_DayString = "No";


                }
                else
                {
                    mon_DayString = "yes";

                }
                if (mLinear_starttime_mon2==null && mLinear_endtime_mon2 ==null)
                {
                    mLinear_starttime_mon2="";
                    mLinear_endtime_mon2="";
                    mon_DayString = "No";
                }
                else
                { mon_DayString = "yes";

                }

                /////////////for tuesday///////////////////

                if (mLinear_starttime_tue1==null && mLinear_endtime_tue1 == null)
                {
                    mLinear_starttime_tue1="";
                    mLinear_endtime_tue1="";
                    tue_DayString = "No";
                }
                else
                {
                    tue_DayString = "yes";
                }
                if (mLinear_starttime_tue2==null && mLinear_endtime_tue2 ==null)
                {
                    mLinear_starttime_tue2="";
                    mLinear_endtime_tue2="";
                    tue_DayString = "No";
                }
                else
                {
                    tue_DayString = "yes";

                }
                /////////////////////////////second time for wednesday ////////////////////
                if (mLinear_starttime_wed1==null && mLinear_endtime_wed1 == null)
                {
                    mLinear_starttime_wed1="";
                    mLinear_endtime_wed1="";
                    wed_DayString = "No";
                }
                else
                {
                    wed_DayString = "yes";
                }

                if (mLinear_starttime_wed2==null && mLinear_endtime_wed2 ==null)
                {
                    mLinear_starttime_wed2="";
                    mLinear_endtime_wed2="";
                    wed_DayString = "No";
                }
                else
                {
                    wed_DayString = "yes";

                }
                ///////////////////////////////// thursday ////////////////////////////


                if (mLinear_starttime_thu1==null && mLinear_endtime_thu1 == null)
                {
                    mLinear_starttime_thu1="";
                    mLinear_endtime_thu1="";
                    thu_DayString = "No";
                }
                else
                {
                    thu_DayString = "yes";
                }

                if (mLinear_starttime_thu2==null && mLinear_endtime_thu2 ==null)
                {
                    mLinear_starttime_thu2="";
                    mLinear_endtime_thu2="";
                    thu_DayString = "No";
                }
                else
                {
                    thu_DayString = "yes";

                }

            /////////////////////////////////////////////////friday/////////////////////////////////
                if (mLinear_starttime_fri1==null && mLinear_endtime_fri1 == null)
                {
                    mLinear_starttime_fri1="";
                    mLinear_endtime_fri1="";
                    fri_DayString = "No";
                }
                else
                {
                    fri_DayString = "yes";
                }

                if (mLinear_starttime_fri2==null && mLinear_endtime_fri2 ==null)
                {
                    mLinear_starttime_fri2="";
                    mLinear_endtime_fri2="";
                    fri_DayString = "No";
                }
                else
                {
                    fri_DayString = "yes";

                }

                ////////////////////////////////////////////////////saturday///////////////////////

                if (mLinear_starttime_sat1==null && mLinear_endtime_sat1 == null)
                {
                    mLinear_starttime_sat1="";
                    mLinear_endtime_sat1="";
                    sat_DayString = "No";
                }
                else
                {
                    sat_DayString = "yes";
                }

                if (mLinear_starttime_sat2==null && mLinear_endtime_sat2 ==null)
                {
                    mLinear_starttime_sat2="";
                    mLinear_endtime_sat2="";
                    sat_DayString = "No";
                }
                else
                {
                    sat_DayString = "yes";

                }
                ///////////////////////////////////////////sunday////////////////////////////



                if (mLinear_starttime_sun1==null && mLinear_endtime_sun1 == null)
                {
                    mLinear_starttime_sun1="";
                    mLinear_endtime_sun1="";
                    sun_DayString = "No";
                }
                else
                {
                    sun_DayString = "yes";
                }

                if (mLinear_starttime_sun2==null && mLinear_endtime_sun2 ==null)
                {
                    mLinear_starttime_sun2="";
                    mLinear_endtime_sun2="";
                    sun_DayString = "No";
                }
                else
                {
                    sun_DayString = "yes";

                }

                //////////////////all day set time in one parameter///////////////////////////////////
                String final_mon=mLinear_starttime_mon1+"-"+mLinear_endtime_mon1 +","+mLinear_starttime_mon2+"-"+mLinear_endtime_mon2;
                String final_tues=mLinear_starttime_tue1+"-"+mLinear_endtime_tue1 +","+mLinear_starttime_tue2+"-"+mLinear_endtime_tue2;
                String final_wed=mLinear_starttime_wed1+"-"+mLinear_endtime_wed1 +","+mLinear_starttime_wed2+"-"+mLinear_endtime_wed2;
                String final_thurs=mLinear_starttime_thu1+"-"+mLinear_endtime_thu1 +","+mLinear_starttime_thu2+"-"+mLinear_endtime_thu2;
                String final_fri=mLinear_starttime_fri1+"-"+mLinear_endtime_fri1 +","+mLinear_starttime_fri2+"-"+mLinear_endtime_fri2;
                String final_sat=mLinear_starttime_sat1+"-"+mLinear_endtime_sat1 +","+mLinear_starttime_sat2+"-"+mLinear_endtime_sat2;
                String final_sun=mLinear_starttime_sun1+"-"+mLinear_endtime_sun1 +","+mLinear_starttime_sun2+"-"+mLinear_endtime_sun2;


             mFunApiCall(url,final_mon,final_tues,final_wed,final_thurs,final_fri,final_sat,final_sun);


            break;








    }
    }


    public void mFunApiCall(String url, String final_mon, String final_tues, String final_wed, String final_thurs, final String final_fri, String final_sat, String final_sun)
    {

        Utils.showDialog(ActivityAvailableTherapist.this, "Loading Please Wait...");
        AndroidNetworking.post(url)
                .addBodyParameter("doctor_id", userId)

                .addBodyParameter("monday", mon_DayString)
                .addBodyParameter("monday_start1",mLinear_starttime_mon1)
                .addBodyParameter("monday_end1",mLinear_endtime_mon1)
                .addBodyParameter("monday_start2",mLinear_starttime_mon2)
                .addBodyParameter("monday_end2",mLinear_endtime_mon2)


                .addBodyParameter("tuesday",tue_DayString)
                .addBodyParameter("tuesday_start1",mLinear_starttime_tue1)
                .addBodyParameter("tuesday_end1",mLinear_endtime_tue1)
                .addBodyParameter("tuesday_start2",mLinear_starttime_tue2)
                .addBodyParameter("tuesday_end2",mLinear_endtime_tue1)


                .addBodyParameter("wednesday",wed_DayString)
                .addBodyParameter("wednesday_start1",mLinear_starttime_wed1)
                .addBodyParameter("wednesday_end1",mLinear_endtime_wed1)
                .addBodyParameter("wednesday_start2",mLinear_starttime_wed2)
                .addBodyParameter("wednesday_end2",mLinear_endtime_wed2)

                .addBodyParameter("thursday",thu_DayString)
                .addBodyParameter("thursday_start1",mLinear_starttime_thu1)
                .addBodyParameter("thursday_end1",mLinear_endtime_thu1)
                .addBodyParameter("thursday_start2",mLinear_starttime_thu2)
                .addBodyParameter("thursday_end2",mLinear_endtime_thu2)

                .addBodyParameter("friday",fri_DayString)
                .addBodyParameter("friday_start1",mLinear_starttime_fri1)
                .addBodyParameter("friday_end1",mLinear_endtime_fri1)
                .addBodyParameter("friday_start2",mLinear_starttime_fri2)
                .addBodyParameter("friday_end2",mLinear_endtime_fri2)

                .addBodyParameter("saturday",sat_DayString)
                .addBodyParameter("saturday_start1",mLinear_starttime_sat1)
                .addBodyParameter("saturday_end1",mLinear_endtime_sat1)
                .addBodyParameter("saturday_start2",mLinear_starttime_sat2)
                .addBodyParameter("saturday_end2",mLinear_endtime_sat2)

                .addBodyParameter("sunday",sun_DayString)
                .addBodyParameter("sunday_start1",mLinear_starttime_sun1)
                .addBodyParameter("sunday_end1",mLinear_endtime_sun1)
                .addBodyParameter("sunday_start2",mLinear_starttime_sun2)
                .addBodyParameter("sunday_end2",mLinear_endtime_sun2)

                .setTag("useravailable")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        //Utils.hideProgress(mdialog);
                        Utils.dismissDialog();
                        Log.e("available time  = ", "ChangePass res client" + jsonObject);
                        try {
                            String msg = jsonObject.getString("msg");
                            String result = jsonObject.getString("result");


                            if (result.equalsIgnoreCase("true")) {
                                ToastClass.showToast(ActivityAvailableTherapist.this, "data submitted successfully");
                               startActivity(new Intent(ActivityAvailableTherapist.this,ActivityEditProfileTherapist.class));
                                finish();


                            } else {

                                Toast.makeText(ActivityAvailableTherapist.this, "please give start time and end time both", Toast.LENGTH_SHORT).show();
                                Utils.dismissDialog();
                               // Utils.openAlertDialog(ActivityAvailableTherapist.this, msg);
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










    }




package com.example.drtherapist.client.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.drtherapist.R;
import com.example.drtherapist.common.Utils.NetworkUtil;
import com.example.drtherapist.common.Utils.Utils;
import com.example.drtherapist.common.remote.API;
import com.example.drtherapist.common.remote.Session;
import com.example.drtherapist.therapist.activity.ActivityMemberShipDr;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

public class ActivityBookNowForm extends AppCompatActivity {

    EditText et_name,et_email,et_phone,et_desc,et_member,et_start_date,et_end_date;
    private int mYear, mMonth, mDay;
    boolean date_apply;
    Button btn_booknow_submit;
    Session session;
     String userId;
     String otherDr_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_now_form);

        Toolbar toolbar = findViewById(R.id.toolbar_edit_profile);

        toolbar.setTitle("Edit Profile");
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_backspace_black_24dp);

        session = new Session(this);
        userId = session.getUser().id;

        try {
            if (getIntent()!=null){
                otherDr_id=getIntent().getStringExtra("otherDr_id");

            }
        }catch (Exception e){

        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              onBackPressed();
            }
        });

        et_name=findViewById(R.id.et_name);
        et_email=findViewById(R.id.et_email);
        et_phone=findViewById(R.id.et_phone);
        et_desc=findViewById(R.id.et_desc);
        et_member=findViewById(R.id.et_member);
        et_start_date=findViewById(R.id.et_start_date);
        et_end_date=findViewById(R.id.et_end_date);
        btn_booknow_submit=findViewById(R.id.btn_booknow_submit);


        et_start_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date_apply=true;
                OpenCalendar();
            }
        });

        et_end_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date_apply=false;
                OpenCalendar();
            }
        });

        btn_booknow_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Et_Name=et_name.getText().toString();
                String Et_Email=et_email.getText().toString();
                String Et_Phone=et_phone.getText().toString();
                String Et_Desc=et_desc.getText().toString();
                String Et_Member=et_member.getText().toString();
                String Et_StartDate=et_start_date.getText().toString();
                String Et_EndDate=et_end_date.getText().toString();

                if (!Et_Name.isEmpty() && !Et_Email.isEmpty() && !Et_Phone.isEmpty() && !Et_Desc.isEmpty()
                    && !Et_Member.isEmpty() && !Et_StartDate.isEmpty() && Et_EndDate.isEmpty()){

                    if (NetworkUtil.isNetworkConnected(ActivityBookNowForm.this)){

                        BookNowSubmit(userId,otherDr_id,Et_Name,Et_Email,Et_Phone,Et_Desc,Et_Member,Et_StartDate,Et_EndDate);//call api

                    }else {
                        Toast.makeText(ActivityBookNowForm.this, "Please check internet", Toast.LENGTH_SHORT).show();
                    }

                }else {
                    Toast.makeText(ActivityBookNowForm.this, "Please enter all field", Toast.LENGTH_SHORT).show();
                }












            }
        });






    }

    private void BookNowSubmit(String userId, String otherDr_id, String et_name, String et_email, String et_phone,
                               String et_desc, String et_member, String et_startDate, String et_endDate) {



        Utils.showDialog(this, "Loading Please Wait...");
        AndroidNetworking.post(API.BASE_URL+"book_doctor_appointment")
                .addBodyParameter("user_id", userId)
                .addBodyParameter("dr_id", otherDr_id)
                .addBodyParameter("name", et_name)
                .addBodyParameter("email", et_email)
                .addBodyParameter("mobile", et_phone)
                .addBodyParameter("start_date", et_startDate)
                .addBodyParameter("end_date", et_endDate)
                .addBodyParameter("description", et_desc)
                .addBodyParameter("member", et_member)
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
                               Toast.makeText(getApplicationContext(), ""+message, Toast.LENGTH_LONG).show();

                                Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent1);

                            } else {

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

    private void OpenCalendar() {

            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);

//            c.setz(Calendar.MONTH, Calendar.JANUARY);
//            c.set(Calendar.DAY_OF_MONTH, 9);
//            c.set(Calendar.YEAR, 2015);

            DatePickerDialog datePickerDialog = new DatePickerDialog(ActivityBookNowForm.this, R.style.calender_dialog_theme,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            if (date_apply){
                                et_start_date.setText(year+ "-" +(monthOfYear + 1) + "-" +dayOfMonth);
                            }else {
                                et_end_date.setText(year+ "-" +(monthOfYear + 1) + "-" +dayOfMonth);
                            }


                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}

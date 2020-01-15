package com.example.drtherapist.common.BasicActivities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.drtherapist.Config.PayPalConfig;
import com.example.drtherapist.R;
import com.example.drtherapist.client.activity.ActivityBookNowForm;
import com.example.drtherapist.common.Utils.Utils;
import com.example.drtherapist.common.remote.API;
import com.example.drtherapist.common.remote.Session;
import com.example.drtherapist.therapist.activity.ActivityMemberShipDr;
import com.google.android.gms.common.api.Api;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;

public class ActivityPaypalActivity extends AppCompatActivity {

    private String MemberAmount,MemberPlanId;
    TextView tv_amount;
    Button btn_paynow;
    Session session;
    CheckBox check_t_c;

    public static final int PAYPAL_REQUEST_CODE = 123;

    private static PayPalConfiguration config = new PayPalConfiguration()
            // Start with mock environment.  When ready, switch to sandbox (ENVIRONMENT_SANDBOX)
            // or live (ENVIRONMENT_PRODUCTION)
           // .environment(PayPalConfiguration.ENVIRONMENT_PRODUCTION)
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(PayPalConfig.PAYPAL_CLIENT_ID);
    private String userId;
    String Login_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paypal);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_booknow);
        toolbar.setTitle("Payment");
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_backspace_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        session = new Session(this);
        userId = session.getUser().id;
//        Intent intent = new Intent(getApplicationContext(), TermsOfUse.class);
//        startActivity(intent);
        
        tv_amount=findViewById(R.id.tv_amount);
        btn_paynow=findViewById(R.id.btn_paynow);
        check_t_c=findViewById(R.id.check_t_c);

        try {

            MemberAmount=getIntent().getStringExtra("MemberAmount");
            Login_type=getIntent().getStringExtra("Login_type");
            MemberPlanId=getIntent().getStringExtra("MemberPlanId");
            Log.e("MemberAmount",MemberAmount);
            tv_amount.setText(MemberAmount);

        }catch (Exception e){

        }

        //********************paypal initialize
        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        startService(intent);


        btn_paynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (check_t_c.isChecked()){
                    CallPaypal();
                }else {
                    Toast.makeText(ActivityPaypalActivity.this, "Please accept T&C", Toast.LENGTH_SHORT).show();
                }


            }
        });



    }

    private void CallPaypal() {

        // paymentAmount = editTextAmount.getText().toString();

        //Creating a paypalpayment
        PayPalPayment payment = new PayPalPayment(new BigDecimal(String.valueOf(MemberAmount)),
                "USD", "Fee",
                PayPalPayment.PAYMENT_INTENT_SALE);

        //Creating Paypal Payment activity intent
        Intent intent = new Intent(this, PaymentActivity.class);

        //putting the paypal configuration to the intent
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);


        //Puting paypal payment to the intent
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);

        //Starting the intent activity for result
        //the request code will be used on the method onActivityResult
        startActivityForResult(intent, PAYPAL_REQUEST_CODE);
    }

    @Override
    public void onDestroy() {
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //If the result is from paypal
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PAYPAL_REQUEST_CODE) {

            //If the result is OK i.e. user has not canceled the payment
            if (resultCode == Activity.RESULT_OK) {
                //Getting the payment confirmation
                PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);

                //if confirmation is not null
                if (confirm != null) {
                    try {
                        //Getting the payment details
                        String paymentDetails = confirm.toJSONObject().toString(4);
                        Log.e("paymentExample", paymentDetails);
                        Toast.makeText(this, "Payment Success", Toast.LENGTH_LONG).show();
                        //Starting a new activity for the payment details and also putting the payment details with intent



                        try {
                            JSONObject jsonDetails = new JSONObject(paymentDetails);

                            String trns_id=jsonDetails.getJSONObject("response").getString("id");
                            String status=jsonDetails.getJSONObject("response").getString("state");

                            callPayment_HistoryApi(trns_id,status,paymentDetails);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    } catch (JSONException e) {
                        Log.e("paymentExample", "an extremely unlikely failure occurred: ", e);
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.i("paymentExample", "The user canceled.");
            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                Log.i("paymentExample", "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
            }
        }
    }

    private void callPayment_HistoryApi(String trns_id, String status, final String paymentDetails) {

        Utils.showDialog(this, "Loading Please Wait...");
        AndroidNetworking.post(API.BASE_URL + "payment")
                .addBodyParameter("user_id", userId)
                .addBodyParameter("member_plan_id", MemberPlanId)
                .addBodyParameter("type", Login_type)
                .addBodyParameter("status", status)
                .addBodyParameter("transc_id", trns_id)

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
                                startActivity(new Intent(ActivityPaypalActivity.this, ConfirmationActivity.class)
                                        .putExtra("PaymentDetails", paymentDetails)
                                        .putExtra("PaymentAmount", MemberAmount));
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


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}

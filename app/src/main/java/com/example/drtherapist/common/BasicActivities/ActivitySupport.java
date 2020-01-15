package com.example.drtherapist.common.BasicActivities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.drtherapist.R;
import com.example.drtherapist.client.activity.MainActivity;
import com.example.drtherapist.common.Utils.Utils;
import com.example.drtherapist.common.remote.Session;

import org.json.JSONException;
import org.json.JSONObject;

import static com.example.drtherapist.common.remote.API.BASE_URL;

public class ActivitySupport extends AppCompatActivity {
    Session session;
    private String user_id,type;
    EditText et_comments;
    Button btn_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);

        Toolbar toolbar = findViewById(R.id.toolbar_edit_profile);
        et_comments = findViewById(R.id.et_comments);
        btn_submit = findViewById(R.id.btn_submit);
        //setSupportActionBar(toolbar);
        toolbar.setTitle("Support");
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_backspace_black_24dp);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        session = new Session(this);

        try {
            if (session != null) {
                user_id = session.getUser().id;
                type = session.getUser().type;
                Log.e("session_id", user_id);
            }
        }catch (Exception e){
        }


        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Et_Description=et_comments.getText().toString();

                if ( !Et_Description.isEmpty()){

                    AddSupport(Et_Description);

                }else {
                    Toast.makeText(ActivitySupport.this, "Please enter comments", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void AddSupport(final String et_description) {
        Utils.showDialog(this, "Loading Please Wait...");
        AndroidNetworking.post(BASE_URL+"support")
                .addBodyParameter("type", type)
                .addBodyParameter("message", et_description)
                .addBodyParameter("user_id", user_id)
                .setTag("joblist")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Utils.dismissDialog();
                        Log.e("navig jo = ", "" + jsonObject);
                        try {
                            String message = jsonObject.getString("msg");
                            String result = jsonObject.getString("result");

                            Toast.makeText(ActivitySupport.this, ""+message, Toast.LENGTH_SHORT).show();
                            if (result.equalsIgnoreCase("true")) {

                                et_comments.setText("");

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        Utils.dismissDialog();
                        Log.e("error = ", "" + error);
                    }
                });



    }
}

package com.example.drtherapist.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.drtherapist.R;

public class AlertFragment extends Fragment {

    public AlertFragment() {
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alert, container, false);


//        btnLoginClient=(Button)view.findViewById(R.job_id.btn_login_client);
//        signUphereC=(TextView)view.findViewById(R.job_id.tv_SignUp_here_client);
//        btnLoginClient.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(getActivity(), MainActivity.class);
//                startActivity(intent);
//
//            }
//        });

//        signUphereC.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(getActivity(), SignUpActivity.class);
//                startActivity(intent);
//
//            }
//        });

        return view;
    }
}

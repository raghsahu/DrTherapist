package com.example.drtherapist.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.drtherapist.R;

public class MessageFragment extends Fragment {

    public MessageFragment() {
    }

    TextView tv_user_name;
    LinearLayout li_second_lay;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, container, false);

        tv_user_name=view.findViewById(R.id.tv_user_name);
        li_second_lay=view.findViewById(R.id.li_second_lay);
        li_second_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent=new Intent(getActivity(), ChatActivity.class);
//                startActivity(intent);
            }
        });


        return view;
    }
}

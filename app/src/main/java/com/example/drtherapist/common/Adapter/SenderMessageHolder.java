package com.example.drtherapist.common.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.drtherapist.R;
import com.example.drtherapist.common.Model.Message_new;
import com.example.drtherapist.common.Model.Messages;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class SenderMessageHolder extends RecyclerView.ViewHolder {
    TextView messageText, timeText;

    SenderMessageHolder(View itemView) {
        super(itemView);
        messageText = (TextView) itemView.findViewById(R.id.text_message_body);
        timeText = (TextView) itemView.findViewById(R.id.text_message_time);

    }

    void bind(Message_new message) {
        long timeStamp = message.time;
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTimeInMillis(timeStamp);
        String cal[] = calendar.getTime().toString().split(" ");
        String time_of_message = cal[1]+","+cal[2]+"  "+cal[3].substring(0,5);
        Log.e("TIME IS : ",calendar.getTime().toString());
        messageText.setText(message.message);
        timeText.setText(time_of_message);
    }

}
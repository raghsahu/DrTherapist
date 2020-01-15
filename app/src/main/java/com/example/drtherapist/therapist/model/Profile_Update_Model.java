package com.example.drtherapist.therapist.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Profile_Update_Model {
    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("data")
    @Expose
    private Profile_Data data;
    @SerializedName("msg")
    @Expose
    private String msg;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Profile_Data getData() {
        return data;
    }

    public void setData(Profile_Data data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }



}

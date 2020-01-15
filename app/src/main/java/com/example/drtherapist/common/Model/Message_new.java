package com.example.drtherapist.common.Model;

import java.io.Serializable;

public class Message_new implements Serializable {
    public String message;
    public String senderId;
    public String image;
    public Long time;
    public String receiverId;
    public boolean seen;
}
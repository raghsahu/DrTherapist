package com.example.drtherapist.common.Model;

import java.io.Serializable;

    public class ChatHistory implements Serializable {
        public String message;
        public String roomId;
        public Boolean seen;
        public String senderId;
        public String receiverId;
        public String historyName;
        public String image;
        public String register_id;
        public Long time;

}

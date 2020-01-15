package com.example.drtherapist.fcm;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;

import com.example.drtherapist.R;
import com.example.drtherapist.client.activity.MainActivity;
import com.example.drtherapist.common.BasicActivities.ChatActivity1;
import com.example.drtherapist.common.BasicActivities.ChatActivity_New;
import com.example.drtherapist.common.BasicActivities.MessageConversation;
import com.example.drtherapist.common.Model.ChatUserModal;
import com.example.drtherapist.common.Utils.Config;
import com.example.drtherapist.db.DatabaseHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ravi on 14/12/2017.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    FirebaseAuth auth;
    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();
    private NotificationUtils notificationUtils;
    String notification_status, format;
    private String message = "";
    RemoteMessage remoteMessage;

    final int NOTIFY_ID=1; // any integer number
    int count = 0;
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.e(TAG, "From_firebase: " + remoteMessage.getFrom());

//        if (remoteMessage == null)
//            return;
//
//        if (remoteMessage.getNotification() != null) {
//            Log.e(TAG, "Notification_Body: " + remoteMessage.getNotification().getBody());
//            handleNotification("");
//        }
//
//        if (remoteMessage.getData().size() > 0) {
//            Log.e(TAG, "Data_Payload: " + remoteMessage.getData().toString());
//            Log.e(TAG, "Data Payload_user: " + remoteMessage.getData().get("userName"));
//            this.remoteMessage = remoteMessage;
//            try {
//                Log.e(TAG, "try: "+"aaaaa " );
//                JSONObject json = new JSONObject(remoteMessage.getData());
//                json.getString("userId");
//                json.getString("Udid");
//               String message = json.getString("message");
//               String user_id = json.getString("Udid");
//               String userName = json.getString("userName");
//               Log.e("message_firebase", message);
//               Log.e("message_firebase", user_id);
//                Log.e(TAG, "Data_Payload_user: " + json.getString("userId"));
//                Log.e(TAG, "Data_Payload_userId: " + json.getString("Udid"));
//
//                handleDataMessage(json);
//
//
//
//            } catch (Exception e) {
//                Log.e(TAG, "Exception: " + e.getMessage());
//                SetFirebaseMessage();
//            }
//        }
//    }
//
//    private void SetFirebaseMessage() {
//        String fcmId = remoteMessage.getData().get("fcmId");
//        //String udid = remoteMessage.getData().get("Udid");
//        String userId = remoteMessage.getData().get("userId");
//        String userName = remoteMessage.getData().get("userName");
//        message = remoteMessage.getData().get("message");
//
//        sendNotificationFirebase(message, userName, "Dr. Therapist");
//    }
//
//    private void sendNotificationFirebase(String message, String userName, String title) {
//        PendingIntent pendingIntent = null;
//
//        Intent intent = new Intent(this, MessageConversation.class);
//        intent.putExtra("message", message);
//        intent.putExtra("userName", userName);
//        intent.putExtra("title", title);
//
//
//        //playNotificationSound();
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_ONE_SHOT);
//
//        String CHANNEL_ID = "com.example.datesinglegetmingle";// The id of the channel.
//        CharSequence name = "MyChannal";// The user-visible name of the channel.
//        int importance = NotificationManager.IMPORTANCE_HIGH;
//        NotificationChannel mChannel = null;
//
//
//        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
//                .setSmallIcon(R.drawable.ic_launcher_background)
//                .setContentTitle(title)
//                .setContentText(message)
//                .setPriority(Notification.PRIORITY_HIGH)
//                .setAutoCancel(true)
//                .setContentIntent(pendingIntent);
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            notificationBuilder.setSmallIcon(R.drawable.logo2);
//            notificationBuilder.setColor(getResources().getColor(R.color.colorPrimary));
//        } else {
//            notificationBuilder.setSmallIcon(R.drawable.logo2);
//        }
//
//        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
//            assert notificationManager != null;
//            notificationManager.createNotificationChannel(mChannel);
//        }
//        assert notificationManager != null;
//        notificationManager.notify(1, notificationBuilder.build());
//    }
//
//    private void handleNotification(String message) {
//        try
//        {
//            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            format = simpleDateFormat.format(new Date());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
//            // app is in foreground, broadcast the push message
//            Intent pushNotification = new Intent(Constants.PUSH_NOTIFICATION);
//            pushNotification.putExtra("message", message);
//            //LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);
//            // play notification sound
//            NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
//            notificationUtils.playNotificationSound();
//
//        } else {
//            sendNoti(message, "DrTherapist");
//
////            Intent pushNotification = new Intent(Constants.PUSH_NOTIFICATION);
////            pushNotification.putExtra("message", message);
////            LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);
////            // play notification sound
////            NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
////            notificationUtils.playNotificationSound();
//
//        }
//    }
//
//    private void handleDataMessage(JSONObject json) {
//        Log.e(TAG, "push json: " + json.toString());
//        try {
//            String fcmId = json.getString("fcmId");
//            String udid = json.getString("Udid");
//            String userId = json.getString("userId");
//            String userName = json.getString("userName");
//            String message = json.getString("message");
//
//            Intent intent = new Intent("custom-event-name");
//            intent.putExtra("message", message);
//            intent.putExtra("Udid", udid);
//            LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
//
//            DatabaseHelper db=new DatabaseHelper(this);
//             ChatUserModal modal=new ChatUserModal();
//             modal.setFcmId(fcmId);
//             modal.setUdid(udid);
//             modal.setMessage(message);
//             modal.setUserName(userName);
//             modal.setUserId(userId);
//             Log.e("nudid",udid);
//             Log.e("user_udid",modal.getUserName());
//
//            if (db.checkUser(userId)){
//                db.updateUser(modal);
//            }else {
//                db.addUser(modal);
//                db.updateUser(modal);
//            }
//            Log.e(TAG, "Data From Local: " + db.getAllUser());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//    }
//
//    private void sendNoti(String message, String drTherapist){
//
//        Intent intent = new Intent(this, ChatActivity_New.class);
//        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//        NotificationCompat.Builder b = new NotificationCompat.Builder(this);
//        String channelId = "Default";
//
//        b.setAutoCancel(true);
//        b.setDefaults(Notification.DEFAULT_ALL);
//        b.setWhen(System.currentTimeMillis());
//        b.setSmallIcon(R.drawable.logo2);
//        b.setTicker("Hearty365");
//        b.setContentTitle("Default notification");
//        b.setContentText("message");
//        b.setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND);
//        b.setContentIntent(contentIntent);
//        b.setContentInfo("Info");
//
//        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            NotificationChannel channel = new NotificationChannel(channelId, "Default channel", NotificationManager.IMPORTANCE_DEFAULT);
//            notificationManager.createNotificationChannel(channel);
//        }
//        notificationManager.notify(1, b.build());

       // String msg=remoteMessage.getData().get("");
        FirebaseUser firebaseUser=FirebaseAuth.getInstance().getCurrentUser();

        sentNotification(remoteMessage);

        if (remoteMessage == null)
            return;

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.e(TAG, "Notification Body: " + remoteMessage.getNotification().getBody());
            handleNotification(remoteMessage.getNotification().getBody());
            sentNotification(remoteMessage);
        }else {
            sentNotification(remoteMessage);
        }

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Data Payload: " + remoteMessage.getData().toString());

            try {
                JSONObject json = new JSONObject(remoteMessage.getData().toString());
                handleDataMessage(json);
            } catch (Exception e) {
                sentNotification(remoteMessage);
                Log.e(TAG, "Exception: " + e.getMessage());
            }
        }
    }

    private void sentNotification(RemoteMessage remoteMessage) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        String channelId = "com.example.drtherapist";
        NotificationCompat.Builder builder = new  NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(remoteMessage.getNotification().getTitle())
                .setContentText(remoteMessage.getNotification().getBody())
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

//        if (count == 0)
//           Log.e("noti_count", "noti....");
//        else
//            Log.e("noti_count", count+" noti....");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, "Default channel", NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(channel);
        }
        manager.notify(0, builder.build());

        count++;

        Log.e("noti_count_count", count+" new_noti.");



    }

    private void handleNotification(String message) {
        Log.e(TAG, "push_mesg: " + message);
        if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
            // app is in foreground, broadcast the push message
            Intent pushNotification = new Intent(Config.PUSH_NOTIFICATION);
            pushNotification.putExtra("message", message);
            LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

            // play notification sound
            NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
            notificationUtils.playNotificationSound();
        }else{
            // If the app is in background, firebase itself handles the notification
        }
    }

    private void handleDataMessage(JSONObject json) {
        Log.e(TAG, "push_json:" + json.toString());

        try {
            JSONObject data = json.getJSONObject("data");

            String title = data.getString("title");
            String message = data.getString("message");
            boolean isBackground = data.getBoolean("is_background");
            String imageUrl = data.getString("image");
            String timestamp = data.getString("timestamp");
            JSONObject payload = data.getJSONObject("payload");

            Log.e(TAG, "title: " + title);
            Log.e(TAG, "message: " + message);
            Log.e(TAG, "isBackground: " + isBackground);
            Log.e(TAG, "payload: " + payload.toString());
            Log.e(TAG, "imageUrl: " + imageUrl);
            Log.e(TAG, "timestamp: " + timestamp);


            if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
                // app is in foreground, broadcast the push message
                Intent pushNotification = new Intent(Config.PUSH_NOTIFICATION);
                pushNotification.putExtra("message", message);
                LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

                // play notification sound
                NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
                notificationUtils.playNotificationSound();
            } else {
                // app is in background, show the notification in notification tray
                Intent resultIntent = new Intent(getApplicationContext(), MainActivity.class);
                resultIntent.putExtra("message", message);

                // check for image attachment
                if (TextUtils.isEmpty(imageUrl)) {
                    showNotificationMessage(getApplicationContext(), title, message, timestamp, resultIntent);
                } else {
                    // image is present, show notification with image
                    showNotificationMessageWithBigImage(getApplicationContext(), title, message, timestamp, resultIntent, imageUrl);
                }
            }
        } catch (JSONException e) {
            Log.e(TAG, "Json Exception: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }

    private void showNotificationMessage(Context context, String title, String message, String timeStamp, Intent intent) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent);
    }

    private void showNotificationMessageWithBigImage(Context context, String title, String message, String timeStamp, Intent intent, String imageUrl) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent, imageUrl);
    }






}
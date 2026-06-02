package com.phc.cimplus.Activities.Notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.phc.cimplus.R;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "FCMService";
    private static final String CHANNEL_ID = "phc_fcm_channel";

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        Log.d(TAG, "New FCM Token: " + token);

        // Save token locally
        SharedPreferences prefs = getSharedPreferences("MyPrefsFile", MODE_PRIVATE);
        prefs.edit().putString("FCMToken", token).apply();

        // Send token to server
        sendTokenToServer(token);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.d(TAG, "Message received from: " + remoteMessage.getFrom());

        String title = "PHC Notification";
        String body = "";
        int notificationId = 0;

        // Check notification payload
        if (remoteMessage.getNotification() != null) {
            title = remoteMessage.getNotification().getTitle();
            body = remoteMessage.getNotification().getBody();
        }

        // Check data payload (server-sent custom data)
        if (remoteMessage.getData().size() > 0) {
            if (remoteMessage.getData().containsKey("title")) {
                title = remoteMessage.getData().get("title");
            }
            if (remoteMessage.getData().containsKey("body")) {
                body = remoteMessage.getData().get("body");
            }
            if (remoteMessage.getData().containsKey("notificationId")) {
                try {
                    notificationId = Integer.parseInt(remoteMessage.getData().get("notificationId"));
                } catch (NumberFormatException e) {
                    notificationId = (int) System.currentTimeMillis();
                }
            }
        }

        showNotification(title, body, notificationId);
    }

    private void showNotification(String title, String body, int notificationId) {
        createNotificationChannel();

        Intent intent = new Intent(this, NotificationListActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(
                this, notificationId, intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.phclogo1)
                .setContentTitle(title)
                .setContentText(body)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(body))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(notificationId, builder.build());
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "PHC Push Notifications",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel.setDescription("Real-time notifications from PHC");
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            manager.createNotificationChannel(channel);
        }
    }

    private void sendTokenToServer(String token) {
        SharedPreferences prefs = getSharedPreferences("MyPrefsFile", MODE_PRIVATE);
        String userId = prefs.getString("UserID", null);

        if (userId == null || userId.isEmpty()) {
            Log.w(TAG, "User not logged in, skipping token registration");
            return;
        }

        String BASE_URL = getResources().getString(R.string.baseurl);
        String encodedToken = "";
        try {
            encodedToken = java.net.URLEncoder.encode(token, "UTF-8");
        } catch (Exception e) {
            encodedToken = token;
        }
        String url = BASE_URL + "RegisterFCMToken?UserId=" + userId + "&Token=" + encodedToken;

        Log.d(TAG, "Registering FCM Token URL: " + url);

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(
                Request.Method.GET, url,
                response -> Log.d(TAG, "Token registered on server: " + response),
                error -> Log.e(TAG, "Failed to register token on server", error)
        );
        queue.add(request);
    }
}

package com.phc.cim.Activities.Notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.phc.cim.Activities.Common.NotificationActivity;
import com.phc.cim.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Background service to fetch notifications from the API
 */
public class NotificationBackgroundService extends Service {
    private static final String TAG = "NotificationBgService";
    private static final String CHANNEL_ID = "phc_notification_channel";
    private static final String CHANNEL_NAME = "PHC Notifications";
    private static final int NOTIFICATION_ID = 1001;
    private static final long FETCH_INTERVAL_MS = TimeUnit.MINUTES.toMillis(15); // Fetch every 15 minutes
    
    private Handler handler;
    private Runnable fetchRunnable;
    private NotificationApiClient apiClient;
    private NotificationManager notificationManager;
    private List<NotificationModel> lastNotifications = new ArrayList<>();
    private boolean isServiceRunning = false;
    
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "Service created");
        
        handler = new Handler();
        apiClient = new NotificationApiClient(this);
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        
        // Create notification channel for Android O and above
        createNotificationChannel();
        
        // Initialize the fetch runnable
        fetchRunnable = new Runnable() {
            @Override
            public void run() {
                fetchNotifications();
                // Schedule next fetch
                handler.postDelayed(this, FETCH_INTERVAL_MS);
            }
        };
    }
    
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "Service started");
        
        if (!isServiceRunning) {
            isServiceRunning = true;
            
            // Start as a foreground service with a persistent notification
            startForeground(NOTIFICATION_ID, createForegroundNotification());
            
            // Start fetching notifications
            handler.post(fetchRunnable);
        }
        
        return START_STICKY;
    }
    
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    
    @Override
    public void onDestroy() {
        Log.d(TAG, "Service destroyed");
        
        // Remove callbacks to prevent memory leaks
        handler.removeCallbacks(fetchRunnable);
        isServiceRunning = false;
        
        super.onDestroy();
    }
    
    /**
     * Create the notification channel for Android O and above
     */
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            channel.setDescription("Channel for PHC notifications");
            notificationManager.createNotificationChannel(channel);
        }
    }
    
    /**
     * Create a foreground notification for the service
     */
    private Notification createForegroundNotification() {
        Intent notificationIntent = new Intent(this, NotificationActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this,
                0,
                notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT | (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ? PendingIntent.FLAG_IMMUTABLE : 0)
        );
        
        return new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("PHC Notification Service")
                .setContentText("Checking for new notifications")
                .setSmallIcon(R.drawable.phclogo1)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build();
    }
    
    /**
     * Fetch notifications from the API
     */
    private void fetchNotifications() {
        Log.d(TAG, "Fetching notifications");
        
        // Get user ID from SharedPreferences
        SharedPreferences prefs = getSharedPreferences("MyPrefsFile", MODE_PRIVATE);
        String userIdStr = prefs.getString("UserID", null);
        
        if (userIdStr == null || userIdStr.isEmpty()) {
            Log.e(TAG, "User ID not found");
            return;
        }
        
        try {
            // Convert userId to integer since the API expects an integer
            apiClient.getNotifications(userIdStr, new NotificationApiClient.NotificationResponseListener() {
                @Override
                public void onResponse(List<NotificationModel> notifications) {
                    if (notifications == null) {
                        Log.w(TAG, "Received null notifications list");
                        return;
                    }
                    
                    Log.d(TAG, "Received " + notifications.size() + " notifications");
                    
                    // Check for new notifications
                    List<NotificationModel> newNotifications = findNewNotifications(notifications);
                    
                    // Update last notifications
                    lastNotifications = notifications;
                    
                    // Show notifications for new items
                    for (NotificationModel notification : newNotifications) {
                        showNotification(notification);
                    }
                    
                    // Update notification count in FilterActivity
                    updateNotificationCount(notifications.size());
                }
                
                @Override
                public void onError(String error) {
                    Log.e(TAG, "Error fetching notifications: " + error);
                    // Don't update UI or show error notifications to the user
                    // Just log the error and continue with the service
                }
            });
        } catch (Exception e) {
            // Catch any exceptions to prevent service from crashing
            Log.e(TAG, "Exception while fetching notifications: " + e.getMessage(), e);
        }
    }
    
    /**
     * Find new notifications that weren't in the last fetch
     */
    private List<NotificationModel> findNewNotifications(List<NotificationModel> currentNotifications) {
        List<NotificationModel> newNotifications = new ArrayList<>();
        
        // If this is the first fetch, don't show notifications for all items
        if (lastNotifications.isEmpty()) {
            return newNotifications;
        }
        
        // Find notifications that weren't in the last fetch
        for (NotificationModel notification : currentNotifications) {
            boolean isNew = true;
            
            for (NotificationModel lastNotification : lastNotifications) {
                if (notification.getId() == lastNotification.getId()) {
                    isNew = false;
                    break;
                }
            }
            
            if (isNew && !notification.isRead()) {
                newNotifications.add(notification);
            }
        }
        
        return newNotifications;
    }
    
    /**
     * Show a notification for a new notification item
     */
    private void showNotification(NotificationModel notification) {
        Intent intent = new Intent(this, NotificationActivity.class);
        intent.putExtra("notificationId", notification.getId());
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this,
                notification.getId(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT | (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ? PendingIntent.FLAG_IMMUTABLE : 0)
        );
        
        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.phclogo1);
        
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.phclogo1)
                .setLargeIcon(largeIcon)
                .setContentTitle(notification.getTitle())
                .setContentText(notification.getMessage())
                .setStyle(new NotificationCompat.BigTextStyle().bigText(notification.getMessage()))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);
        
        notificationManager.notify(notification.getId(), builder.build());
    }
    
    /**
     * Update notification count in FilterActivity
     */
    private void updateNotificationCount(int count) {
        Intent intent = new Intent("com.phc.cim.NOTIFICATION_COUNT_UPDATE");
        intent.putExtra("count", count);
        sendBroadcast(intent);
    }
} 
package com.phc.cim.Activities.Notification;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.List;

/**
 * Manager class for handling notification operations
 */
public class NotificationManager {
    private static final String TAG = "NotificationManager";
    private static NotificationManager instance;
    
    private Context context;
    private NotificationApiClient apiClient;
    private NotificationReceiver receiver;
    private NotificationReceiver.NotificationCountListener countListener;
    
    private NotificationManager(Context context) {
        this.context = context.getApplicationContext();
        this.apiClient = new NotificationApiClient(context);
    }
    
    /**
     * Get singleton instance of NotificationManager
     */
    public static synchronized NotificationManager getInstance(Context context) {
        if (instance == null) {
            instance = new NotificationManager(context);
        }
        return instance;
    }
    
    /**
     * Start the notification background service
     */
    public void startNotificationService() {
        Intent serviceIntent = new Intent(context, NotificationBackgroundService.class);
        context.startService(serviceIntent);
        Log.d(TAG, "Started notification service");
    }
    
    /**
     * Stop the notification background service
     */
    public void stopNotificationService() {
        Intent serviceIntent = new Intent(context, NotificationBackgroundService.class);
        context.stopService(serviceIntent);
        Log.d(TAG, "Stopped notification service");
    }
    
    /**
     * Register for notification count updates
     */
    public void registerNotificationCountListener(NotificationReceiver.NotificationCountListener listener) {
        this.countListener = listener;
        
        // Unregister existing receiver if any
        if (receiver != null) {
            try {
                context.unregisterReceiver(receiver);
            } catch (Exception e) {
                Log.e(TAG, "Error unregistering receiver", e);
            }
        }
        
        // Register new receiver
        receiver = new NotificationReceiver(listener);
        IntentFilter filter = new IntentFilter("com.phc.cim.NOTIFICATION_COUNT_UPDATE");
        context.registerReceiver(receiver, filter);
        
        Log.d(TAG, "Registered notification count listener");
    }
    
    /**
     * Unregister notification count listener
     */
    public void unregisterNotificationCountListener() {
        if (receiver != null) {
            try {
                context.unregisterReceiver(receiver);
                receiver = null;
                countListener = null;
                Log.d(TAG, "Unregistered notification count listener");
            } catch (Exception e) {
                Log.e(TAG, "Error unregistering receiver", e);
            }
        }
    }
    
    /**
     * Fetch notifications manually
     */
    public void fetchNotifications(final NotificationApiClient.NotificationResponseListener listener) {
        // Get user ID and token from SharedPreferences
        SharedPreferences prefs = context.getSharedPreferences("PHCPrefs", Context.MODE_PRIVATE);
        int userId = prefs.getInt("userId", -1);
        String token = prefs.getString("authToken", "");
        
        if (userId == -1 || token.isEmpty()) {
            Log.e(TAG, "User ID or token not found");
            if (listener != null) {
                listener.onError("User ID or token not found");
            }
            return;
        }
        
        apiClient.getNotifications(userId, token, new NotificationApiClient.NotificationResponseListener() {
            @Override
            public void onResponse(List<NotificationModel> notifications) {
                if (listener != null) {
                    listener.onResponse(notifications);
                }
            }
            
            @Override
            public void onError(String error) {
                if (listener != null) {
                    listener.onError(error);
                }
            }
        });
    }
    
    /**
     * Mark a notification as read
     */
    public void markNotificationAsRead(int notificationId, final NotificationApiClient.NotificationResponseListener listener) {
        // Get token from SharedPreferences
        SharedPreferences prefs = context.getSharedPreferences("PHCPrefs", Context.MODE_PRIVATE);
        String token = prefs.getString("authToken", "");
        
        if (token.isEmpty()) {
            Log.e(TAG, "Token not found");
            if (listener != null) {
                listener.onError("Token not found");
            }
            return;
        }
        
        apiClient.markNotificationAsRead(notificationId, token, listener);
    }
} 
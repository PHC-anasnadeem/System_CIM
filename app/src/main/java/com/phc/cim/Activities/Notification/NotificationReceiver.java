package com.phc.cim.Activities.Notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Broadcast receiver for notification updates
 */
public class NotificationReceiver extends BroadcastReceiver {
    private static final String TAG = "NotificationReceiver";
    
    public interface NotificationCountListener {
        void onNotificationCountUpdated(int count);
    }
    
    private NotificationCountListener listener;
    
    public NotificationReceiver(NotificationCountListener listener) {
        this.listener = listener;
    }
    
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() != null && intent.getAction().equals("com.phc.cim.NOTIFICATION_COUNT_UPDATE")) {
            int count = intent.getIntExtra("count", 0);
            Log.d(TAG, "Received notification count update: " + count);
            
            if (listener != null) {
                listener.onNotificationCountUpdated(count);
            }
        }
    }
} 
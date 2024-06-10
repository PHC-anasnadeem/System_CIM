package com.phc.cim.Extra;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import com.phc.cim.Activities.Common.NotificationActivity;
import com.phc.cim.R;

public class NotificationService extends Service {
    private static final String CHANNEL_ID = "your_channel_id";
    private static final int REQUEST_CODE = 1;
    private static final int NOTIFICATION_ID = 1;

    private static final String TAG = "NotificationService";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "Service started");
        String title = intent.getStringExtra("title");
        String outletName = intent.getStringExtra("outletName");
        createNotification(title, outletName);
        return START_NOT_STICKY;
    }

    private void createNotification(String title, String outletName) {
        Log.d(TAG, "Creating notification");

        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.phclogo1, null);
        if (drawable == null) {
            Log.e(TAG, "Drawable is null");
            return;
        }
        BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
        Bitmap largeIcon = bitmapDrawable.getBitmap();

        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (nm == null) {
            Log.e(TAG, "NotificationManager is null");
            return;
        }
        Notification notification;

        Intent iNotify = new Intent(this, NotificationActivity.class);
        iNotify.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Intent[] intents = {iNotify};
        PendingIntent pi = PendingIntent.getActivities(this, REQUEST_CODE, intents, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification.BigPictureStyle bigPictureStyle = new Notification.BigPictureStyle()
                .bigPicture(bitmapDrawable.getBitmap())
                .bigLargeIcon(largeIcon)
                .setBigContentTitle(title)
                .setSummaryText(outletName);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "New Channel", NotificationManager.IMPORTANCE_HIGH);
            nm.createNotificationChannel(channel);
            notification = new Notification.Builder(this, CHANNEL_ID)
                    .setLargeIcon(largeIcon)
                    .setSmallIcon(R.drawable.phclogo1)
                    .setContentTitle("New Notification from PHC")
                    .setContentText(title)
                    .setStyle(bigPictureStyle)
                    .setContentIntent(pi)
                    .build();
        } else {
            notification = new Notification.Builder(this)
                    .setLargeIcon(largeIcon)
                    .setSmallIcon(R.drawable.phclogo1)
                    .setContentTitle("New Notification from PHC")
                    .setContentText(title)
                    .setStyle(bigPictureStyle)
                    .setContentIntent(pi)
                    .build();
        }

        nm.notify(NOTIFICATION_ID, notification);
        Log.d(TAG, "Notification shown");
    }
}

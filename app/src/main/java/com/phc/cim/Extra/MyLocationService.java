package com.phc.cim.Extra;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.phc.cim.R;

import java.net.HttpURLConnection;
import java.net.URL;

public class MyLocationService extends Service {

    private FusedLocationProviderClient fusedLocationClient;
    private LocationCallback locationCallback;

    private String userID;
    private String username;

    // Update interval
    private static final long INTERVAL_MS = 30 * 1000; // 30 seconds
    private static final long FASTEST_INTERVAL_MS = 10 * 1000; // 10 seconds

    @Override
    public void onCreate() {
        super.onCreate();
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            userID = intent.getStringExtra("UserID");
            username = intent.getStringExtra("username");
        }

        startForegroundServiceNotification();
        startLocationUpdates();

        return START_STICKY;
    }

    private void startForegroundServiceNotification() {
        String channelId = "location_channel";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Live Location Service",
                    NotificationManager.IMPORTANCE_LOW
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        Notification notification = new NotificationCompat.Builder(this, channelId)
                .setContentTitle("Live Tracking Active")
                .setContentText("Sending location every 30 seconds")
                .setSmallIcon(android.R.drawable.ic_menu_mylocation)
                .build();

        startForeground(1, notification);
    }

    private void startLocationUpdates() {

        // Permissions check
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.e("LocationService", "Location permission not granted!");
            return;
        }

        // GPS enabled check
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Log.e("LocationService", "GPS is turned off!");
        }

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(INTERVAL_MS);
        locationRequest.setFastestInterval(FASTEST_INTERVAL_MS);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult != null && userID != null) {
                    double lat = locationResult.getLastLocation().getLatitude();
                    double lng = locationResult.getLastLocation().getLongitude();

                    // Skip invalid coordinates
                    if (lat == 0.0 && lng == 0.0) {
                        Log.d("LocationService", "Location not ready yet");
                        return;
                    }

                    sendLocationToServer(lat, lng);
                }
            }
        };

        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
    }

    private void sendLocationToServer(double lat, double lng) {
        new Thread(() -> {
            try {
                String urlStr = getString(R.string.baseurl) +
                        "UpdateMyLocation?UserID=" + userID +
                        "&Latitude=" + lat +
                        "&Longitude=" + lng;
                URL url = new URL(urlStr);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.connect();
                conn.getResponseCode(); // trigger request
                conn.disconnect();
                Log.d("LocationService", "Location sent: " + lat + ", " + lng);
            } catch (Exception e) {
                Log.e("LocationService", "Error sending location", e);
            }
        }).start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (fusedLocationClient != null && locationCallback != null) {
            fusedLocationClient.removeLocationUpdates(locationCallback);
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

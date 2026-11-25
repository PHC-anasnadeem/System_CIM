package com.phc.cimplus.Others;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.phc.cimplus.Activities.Common.Login_Activity;
import com.phc.cimplus.Extra.MyLocationService;
import com.phc.cimplus.R;

public class Logout extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences prefs = getSharedPreferences("MyPrefsFile", MODE_PRIVATE);
        final String email      = prefs.getString("email",      "");
        final String deviceID   = prefs.getString("deviceID",   "");
        final String baseUrl    = getResources().getString(R.string.baseurl);

        // Fire-and-forget: notify server that this session has ended.
        // This marks the row in tblUserLoginHistory as IsActive=0 so admins
        // can see the accurate logout time and reason.
        if (!email.isEmpty() && !deviceID.isEmpty()) {
            new Thread(() -> {
                try {
                    String url = baseUrl
                            + "LogoutUser?username=" + java.net.URLEncoder.encode(email,    "UTF-8")
                            + "&deviceID="           + java.net.URLEncoder.encode(deviceID, "UTF-8")
                            + "&reason=Manual";
                    java.net.HttpURLConnection conn = (java.net.HttpURLConnection)
                            new java.net.URL(url).openConnection();
                    conn.setRequestMethod("POST");
                    conn.setConnectTimeout(5000);
                    conn.setReadTimeout(5000);
                    conn.getResponseCode(); // fire and forget
                    conn.disconnect();
                } catch (Exception ignored) {
                    // Non-critical: session is cleared locally regardless
                }
            }).start();
        }

        // Clear session data
        prefs.edit().clear().commit();

        // Stop location tracking service
        try {
            stopService(new Intent(this, MyLocationService.class));
        } catch (Exception ignored) {}

        // Navigate directly to Login_Activity so endpoints/lookups loaded during initial splash are preserved
        Intent intent = new Intent(this, Login_Activity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_CLEAR_TASK |
                Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}

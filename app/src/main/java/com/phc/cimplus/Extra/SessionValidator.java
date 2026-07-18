package com.phc.cimplus.Extra;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.phc.cimplus.Activities.Common.Login_Activity;
import com.phc.cimplus.R;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * SessionValidator
 *
 * Performs a silent background credential check against the GetAccountInfo API
 * whenever the app starts or returns to the foreground.
 *
 * Security rationale:
 *   - The app stores email, password (encrypted on server), and RoleID in
 *     SharedPreferences ("MyPrefsFile") after a successful login.
 *   - When a user's password is changed or reset on any other device (or via
 *     the web portal), the stored password in SharedPreferences no longer matches
 *     the new encrypted password in the database.
 *   - GetAccountInfo validates email + password + RoleID against the database.
 *     If the stored credentials are stale (password changed), the server returns
 *     MID = "0", and we immediately clear the local session and force login.
 *   - If the network is unreachable, we KEEP the session alive (fail-open for
 *     offline use) and log a warning. This avoids locking out field workers who
 *     are legitimately offline.
 */
public class SessionValidator {

    private static final String TAG = "SessionValidator";
    private static final String PREFS_NAME = "MyPrefsFile";

    public interface ValidationCallback {
        /** Called on the main thread after validation completes. */
        void onResult(boolean isValid);
    }

    /**
     * Validate the credentials stored in SharedPreferences against the server.
     *
     * @param context  Application or Activity context.
     * @param callback Receives {@code true} if valid (or network unavailable),
     *                 {@code false} if the server explicitly rejects the credentials.
     */
    public static void validateSession(final Context context, final ValidationCallback callback) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        final String email    = prefs.getString("email", "");
        final String password = prefs.getString("password", "");
        final String roleID   = prefs.getString("RoleID", "");

        // Nothing to validate if no session exists
        if (email.isEmpty() || password.isEmpty() || roleID.isEmpty()) {
            if (callback != null) callback.onResult(false);
            return;
        }

        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            boolean isValid = checkWithServer(context, email, password, roleID);
            // Post result back to main thread
            android.os.Handler mainHandler = new android.os.Handler(android.os.Looper.getMainLooper());
            mainHandler.post(() -> {
                if (callback != null) callback.onResult(isValid);
            });
        });
    }

    /**
     * If validation fails (isValid == false), clear the session and redirect to
     * Login_Activity. Should be called from the main thread.
     *
     * @param context The current Activity context.
     */
    public static void invalidateAndRedirect(Context context) {
        // 1. Clear locally stored session
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .edit().clear().apply();

        // 2. Stop location tracking service
        try {
            context.stopService(new Intent(context, MyLocationService.class));
        } catch (Exception ignored) {}

        // 3. Navigate to Login_Activity, clearing all back-stack activities
        Intent intent = new Intent(context, Login_Activity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_CLEAR_TASK
                | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    // -------------------------------------------------------------------------
    // Private helpers
    // -------------------------------------------------------------------------

    /** Network call — runs on a background thread. */
    private static boolean checkWithServer(Context context, String email,
                                           String password, String roleID) {
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            String baseUrl = context.getResources().getString(R.string.baseurl);
            String deviceID = android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
            String urlStr  = baseUrl
                    + "GetAccountInfo?username=" + encode(email)
                    + "&userpassword=" + encode(password)
                    + "&RoleID=" + encode(roleID)
                    + "&deviceID=" + encode(deviceID);

            URL url = new URL(urlStr);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setConnectTimeout(8000);  // 8 s connect timeout
            urlConnection.setReadTimeout(10000);    // 10 s read timeout
            urlConnection.connect();

            int statusCode = urlConnection.getResponseCode();
            if (statusCode >= 200 && statusCode < 400) {
                iStream = urlConnection.getInputStream();
            } else {
                // Server error — treat as network issue, keep session
                Log.w(TAG, "Server responded with HTTP " + statusCode + ", keeping session.");
                return true;
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) sb.append(line);
            br.close();

            String jsonStr = sb.toString();
            if (jsonStr.isEmpty()) {
                Log.w(TAG, "Empty response from server, keeping session.");
                return true; // treat as network issue
            }

            JSONObject json = new JSONObject(jsonStr);
            String mid = json.optString("MID", "0");

            if ("1".equals(mid)) {
                Log.d(TAG, "Session valid — credentials accepted by server.");
                return true;
            } else {
                Log.w(TAG, "Session INVALID — server rejected credentials (password may have changed).");
                return false;
            }

        } catch (Exception e) {
            // Network unavailable or timeout — keep session alive (fail-open for offline use)
            Log.w(TAG, "Network error during session validation, keeping session alive: " + e.getMessage());
            return true;
        } finally {
            if (iStream != null) { try { iStream.close(); } catch (IOException ignored) {} }
            if (urlConnection != null) { urlConnection.disconnect(); }
        }
    }

    /** Simple URL encoding for query parameter values. */
    private static String encode(String value) {
        if (value == null) return "";
        return value.replaceAll(" ", "%20");
    }
}

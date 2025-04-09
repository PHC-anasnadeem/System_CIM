package com.phc.cim.Activities.Notification;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * API client for fetching notifications from C# API
 */
public class NotificationApiClient {
    private static final String TAG = "NotificationApiClient";
    private static final String BASE_URL = "https://www.phc.org.pk:8099/PHCCensusData.svc/"; // PHC API URL
    private static final String NOTIFICATIONS_ENDPOINT = "GetNotifications";
    
    private RequestQueue requestQueue;
    private Context context;
    private Gson gson;
    
    public interface NotificationResponseListener {
        void onResponse(List<NotificationModel> notifications);
        void onError(String error);
    }
    
    public NotificationApiClient(Context context) {
        this.context = context;
        this.requestQueue = Volley.newRequestQueue(context);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        this.gson = gsonBuilder.create();
    }
    
    /**
     * Fetch notifications for a specific user
     * @param userId The user ID to fetch notifications for
     * @param listener Response listener
     */
    public void getNotifications(String userId, final NotificationResponseListener listener) {
        String url = BASE_URL + NOTIFICATIONS_ENDPOINT + "?UserId=" + userId;
        
        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            Type listType = new TypeToken<ArrayList<NotificationModel>>(){}.getType();
                            List<NotificationModel> notifications = gson.fromJson(response.toString(), listType);
                            listener.onResponse(notifications);
                        } catch (Exception e) {
                            Log.e(TAG, "Error parsing notification response", e);
                            listener.onError("Failed to parse notifications: " + e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Get the status code from the error
                        String errorMessage = "Failed to fetch notifications";
                        
                        if (error.networkResponse != null) {
                            int statusCode = error.networkResponse.statusCode;
                            
                            if (statusCode == 404) {
                                // Handle 404 Not Found - this is likely because there are no notifications
                                Log.i(TAG, "No notifications found (404 response)");
                                listener.onResponse(new ArrayList<>()); // Return empty list instead of error
                                return;
                            } else if (statusCode >= 500) {
                                errorMessage = "Server error (code " + statusCode + ")";
                            } else {
                                errorMessage = "Request error (code " + statusCode + ")";
                            }
                        } else if (error.getCause() != null) {
                            // Network connectivity issues
                            errorMessage = "Network error: " + error.getCause().getMessage();
                        }
                        
                        Log.e(TAG, "Error fetching notifications: " + errorMessage, error);
                        listener.onError(errorMessage);
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };

        // ⬇️ SET RETRY POLICY HERE
        request.setRetryPolicy(new com.android.volley.DefaultRetryPolicy(
                30000, // timeout in milliseconds
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));
        
        // Set retry policy to avoid long timeouts
        request.setShouldRetryServerErrors(false);
        
        requestQueue.add(request);
    }
    
    /**
     * Mark a notification as read
     * @param notificationId The notification ID to mark as read
     * @param listener Response listener
     */
    public void markNotificationAsRead(int notificationId, final NotificationResponseListener listener) {
        // For now, we'll just return an empty list since the actual endpoint is not provided
        // You can update this with the correct endpoint when available
        listener.onResponse(new ArrayList<>());
    }
} 
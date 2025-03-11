package com.phc.cim.Activities.Notification;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
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
    private static final String BASE_URL = "https://api.example.com/"; // Replace with your actual C# API URL
    private static final String NOTIFICATIONS_ENDPOINT = "api/notifications";
    
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
     * @param token Authentication token
     * @param listener Response listener
     */
    public void getNotifications(int userId, String token, final NotificationResponseListener listener) {
        String url = BASE_URL + NOTIFICATIONS_ENDPOINT + "?userId=" + userId;
        
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
                        Log.e(TAG, "Error fetching notifications", error);
                        listener.onError("Failed to fetch notifications: " + error.getMessage());
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }
        };
        
        requestQueue.add(request);
    }
    
    /**
     * Mark a notification as read
     * @param notificationId The notification ID to mark as read
     * @param token Authentication token
     * @param listener Response listener
     */
    public void markNotificationAsRead(int notificationId, String token, final NotificationResponseListener listener) {
        String url = BASE_URL + NOTIFICATIONS_ENDPOINT + "/" + notificationId + "/read";
        
        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.PUT,
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
                        Log.e(TAG, "Error marking notification as read", error);
                        listener.onError("Failed to mark notification as read: " + error.getMessage());
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }
        };
        
        requestQueue.add(request);
    }
} 
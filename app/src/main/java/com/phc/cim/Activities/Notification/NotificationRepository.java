package com.phc.cim.Activities.Notification;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.phc.cim.DataElements.NotificationDao;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class NotificationRepository {
    private NotificationApiClient apiClient;
    private NotificationDao notificationDao;
    private boolean isDataLoading = false;
    private MutableLiveData<Boolean> loading = new MutableLiveData<>(false);
    private MutableLiveData<List<NotificationModel>> notificationsLiveData = new MutableLiveData<>();
    private Context context;

    public NotificationRepository(Context context) {
        this.context = context;
        apiClient = new NotificationApiClient(context);
        // Initialize your Room database and DAO here
    }

    public LiveData<Boolean> isLoading() {
        return loading;
    }

    public LiveData<List<NotificationModel>> getNotifications() {
        return notificationsLiveData;
    }

    public void fetchNotifications(String userId, boolean forceRefresh) {
        // Don't reload if already loading
        if (isDataLoading && !forceRefresh) {
            return;
        }

        isDataLoading = true;
        loading.setValue(true);

        // First try to load cached data
        new Thread(() -> {
            // Load from cache/database first if available
            List<NotificationModel> cachedData = getCachedNotifications();
            if (cachedData != null && !cachedData.isEmpty() && !forceRefresh) {
                notificationsLiveData.postValue(cachedData);
                loading.postValue(false);
                isDataLoading = false;
            } else {
                // Then load from network
                loadFromNetwork(userId);
            }
        }).start();
    }

    private void loadFromNetwork(String userId) {
        apiClient.getNotifications(userId, new NotificationApiClient.NotificationResponseListener() {
            @Override
            public void onResponse(List<NotificationModel> notifications) {
                // Save to cache
                saveNotificationsToCache(notifications);

                // Update LiveData
                notificationsLiveData.setValue(notifications);
                loading.setValue(false);
                isDataLoading = false;
            }

            @Override
            public void onError(String error) {
                // If error, still show cached data if available
                List<NotificationModel> cachedData = getCachedNotifications();
                if (cachedData != null && !cachedData.isEmpty()) {
                    notificationsLiveData.setValue(cachedData);
                } else {
                    notificationsLiveData.setValue(new ArrayList<>());
                }

                loading.setValue(false);
                isDataLoading = false;
            }
        });
    }

    private List<NotificationModel> getCachedNotifications() {
        // Implement this with SharedPreferences or Room
        // For a simple solution you could use SharedPreferences and Gson

        // Example with SharedPreferences:
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String json = prefs.getString("cached_notifications", null);
        if (json != null) {
            Type type = new TypeToken<List<NotificationModel>>(){}.getType();
            return new Gson().fromJson(json, type);
        }
        return new ArrayList<>();
    }

    private void saveNotificationsToCache(List<NotificationModel> notifications) {
        // Example with SharedPreferences:
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        String json = new Gson().toJson(notifications);
        editor.putString("cached_notifications", json);
        editor.apply();
    }
}

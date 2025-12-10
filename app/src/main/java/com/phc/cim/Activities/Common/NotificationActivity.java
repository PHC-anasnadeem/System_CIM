package com.phc.cim.Activities.Common;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.phc.cim.Adapters.NotificationAdapter;
import com.phc.cim.DataElements.NotificationItem;
import com.phc.cim.Extra.NotificationService;
import com.phc.cim.Extra.NotificationWorker;
import com.phc.cim.Managers.NotificationDatabase;
import com.phc.cim.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NotificationActivity extends AppCompatActivity {
    private NotificationAdapter adapter;
    private List<NotificationItem> notificationList;
    private NotificationDatabase db;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        notificationList = new ArrayList<>();
        adapter = new NotificationAdapter(this, notificationList);
        recyclerView.setAdapter(adapter);

        db = Room.databaseBuilder(getApplicationContext(),
                NotificationDatabase.class, "notification_database").build();

        db.notificationDao().getAllNotifications().observe(this, new Observer<List<NotificationItem>>() {
            @Override
            public void onChanged(List<NotificationItem> notifications) {
                notificationList.clear();
                notificationList.addAll(notifications);
                adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
            }
        });

        // Show the progress bar while processing
        progressBar.setVisibility(View.VISIBLE);

        // Call the scheduleNotificationWork method here to trigger the worker
        NotificationWorker.scheduleNotificationWork(this);
    }


}

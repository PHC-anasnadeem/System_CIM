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

//    private void GetNotification(String userID) {
//        progressBar.setVisibility(View.VISIBLE);
//
//        // Construct the URL with AuthorizedOfficer parameter
//        String url = "https://census.phc.org.pk:51599/api/Allocation/GetComplaintsDataAgainstOfficer?AuthorizedOfficer=" + userID;
//
//        // Create JSON request
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        progressBar.setVisibility(View.GONE);
//                        try {
//                            JSONArray complaintsData = response.getJSONArray("ComplaintsData");
//                            if (complaintsData.length() == 0) {
//                                Toast.makeText(NotificationActivity.this, "There is no notification", Toast.LENGTH_SHORT).show();
//                                return;
//                            }
//                            List<NotificationItem> newEntries = new ArrayList<>();
//
//                            // Parse JSON response and create NotificationItem objects
//                            for (int i = 0; i < complaintsData.length(); i++) {
//                                JSONObject item = complaintsData.getJSONObject(i);
//
//                                int complaintsDetailSeq = item.getInt("ComplaintsDetail_SEQ");
//                                String title = item.getString("title");
//                                String outletName = item.getString("OutletName");
//                                String complainantAddress = item.getString("ComplainantAddress");
//                                String complaintDate = item.getString("ComplaintDate");
//                                String complainantContactNo = item.getString("ComplainantContactNo");
//                                String aqcComplaintsSourceDesc = item.getString("AQC_ComplaintsSource_Desc");
//                                String district = item.getString("District");
//                                String tehsilDesc = item.getString("TehsilDesc");
//                                String phcRegistrationNo = item.isNull("PHC_RegistrationNo") ? null : item.getString("PHC_RegistrationNo");
//                                String comments = item.getString("Comments");
//                                String finalId = item.isNull("FinalID") ? null : item.getString("FinalID");
//                                String typeDesc = item.isNull("TypeDesc") ? null : item.getString("TypeDesc");
//                                String isRegWithPHC = item.getString("isRegWithPHC");
//                                String userName = item.getString("User_Name");
//                                String lastUpdatedBy = item.getString("LastUpdatedBy");
//
//                                NotificationItem notificationItem = new NotificationItem(
//                                        complaintsDetailSeq, title, outletName, complainantAddress,
//                                        complaintDate, complainantContactNo, aqcComplaintsSourceDesc,
//                                        district, tehsilDesc, phcRegistrationNo, comments, finalId,
//                                        typeDesc, isRegWithPHC, userName, lastUpdatedBy
//                                );
//                                newEntries.add(notificationItem);
//
//                                // Start NotificationService to create notification
//                                Intent intent = new Intent(NotificationActivity.this, NotificationService.class);
//                                intent.putExtra("title", title);
//                                intent.putExtra("outletName", outletName);
//                                startService(intent);
//
//                                // Create notification for each new entry
////                                createNotification(title, outletName);
//                            }
//
//                            // Update diaryEntryList and notify adapter
//                            notificationList.clear();
//                            notificationList.addAll(newEntries);
//                            adapter.notifyDataSetChanged();
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                            Toast.makeText(NotificationActivity.this, "Error parsing response", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        progressBar.setVisibility(View.GONE);
//                        Log.e("Volley Error", error.toString());
//                        // Handle error response
//                        Toast.makeText(NotificationActivity.this, "Failed. Please try again later.", Toast.LENGTH_SHORT).show();
//                    }
//                }
//        );
//
//        // Add the request to the RequestQueue
//        requestQueue.add(jsonObjectRequest);
//    }

//    private void createNotification(String title, String outletName) {
//        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.phclogo1, null);
//        BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
//        Bitmap largeIcon = bitmapDrawable.getBitmap();
//
//        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//        Notification notification;
//
//        Intent iNotify = new Intent(this, NotificationActivity.class);
//        iNotify.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        Intent[] intents = {iNotify};
//        PendingIntent pi = PendingIntent.getActivities(this, REQUEST_CODE, intents, PendingIntent.FLAG_UPDATE_CURRENT);
//
//        // Big Picture Style
//        Notification.BigPictureStyle bigPictureStyle = new Notification.BigPictureStyle()
//                .bigPicture(bitmapDrawable.getBitmap())
//                .bigLargeIcon(largeIcon)
//                .setBigContentTitle(title)
//                .setSummaryText(outletName);
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            notification = new Notification.Builder(this)
//                    .setLargeIcon(largeIcon)
//                    .setSmallIcon(R.drawable.phclogo1)
//                    .setContentTitle("New Notification from PHC")
//                    .setContentText(title)
//                    .setStyle(bigPictureStyle)
//                    .setContentIntent(pi)
//                    .setChannelId(CHANNEL_ID)
//                    .build();
//            nm.createNotificationChannel(new NotificationChannel(CHANNEL_ID, "New Channel", NotificationManager.IMPORTANCE_HIGH));
//        } else {
//            notification = new Notification.Builder(this)
//                    .setLargeIcon(largeIcon)
//                    .setSmallIcon(R.drawable.phclogo1)
//                    .setContentTitle("New Notification from PHC")
//                    .setContentText(title)
//                    .setStyle(bigPictureStyle)
//                    .setContentIntent(pi)
//                    .build();
//        }
//
//        nm.notify(NOTIFICATION_ID, notification);
//    }


}

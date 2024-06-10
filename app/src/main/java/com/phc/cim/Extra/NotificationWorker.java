package com.phc.cim.Extra;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.phc.cim.DataElements.NotificationItem;
import com.phc.cim.Managers.NotificationDatabase;
import com.phc.cim.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class NotificationWorker extends Worker {

    private static final String TAG = "NotificationWorker";
    Context context;
    String userID;

    public NotificationWorker(@NonNull Context context, @NonNull WorkerParameters params) {
        super(context, params);
        this.context = context;  // Initialize the context
    }

    @NonNull
    @Override
    public Result doWork() {
        // Fetch notifications from API
        fetchNotifications(context);
        return Result.success();
    }

    Constraints constraints = new Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED) // Run only when network is connected
            .build();

    OneTimeWorkRequest notificationWork = new OneTimeWorkRequest.Builder(NotificationWorker.class)
            .setConstraints(constraints) // Apply constraints to the WorkRequest
            .build();



    private void fetchNotifications(Context context) {

        if (context == null) {
            throw new IllegalArgumentException("Context cannot be null");
        }

        // Assume the userID is set somehow
        if (userID == null || userID.isEmpty()) {
            Log.e(TAG, "UserID is not set");
            return;
        }


        // Construct the URL with AuthorizedOfficer parameter
        SharedPreferences prefs = getApplicationContext().getSharedPreferences("MyPrefsFile", Context.MODE_PRIVATE);
        userID = prefs.getString("UserID", null);
        // Building the url to the web service
        String baseurl = context.getResources().getString(R.string.baseurl);
        String token = context.getResources().getString(R.string.token);
        String url =  baseurl +"GetComplaintsDataAgainstOfficer?AuthorizedOfficer=" + userID;

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray complaintsData = response.getJSONArray("ComplaintsData");
                            List<NotificationItem> newEntries = new ArrayList<>();

                            for (int i = 0; i < complaintsData.length(); i++) {
                                JSONObject item = complaintsData.getJSONObject(i);

                                int complaintsDetailSeq = item.getInt("ComplaintsDetail_SEQ");
                                String title = item.getString("title");
                                String outletName = item.getString("OutletName");
                                String complainantAddress = item.getString("ComplainantAddress");
                                String complaintDate = item.getString("ComplaintDate");
                                String complainantContactNo = item.getString("ComplainantContactNo");
                                String aqcComplaintsSourceDesc = item.getString("AQC_ComplaintsSource_Desc");
                                String district = item.getString("District");
                                String tehsilDesc = item.getString("TehsilDesc");
                                String phcRegistrationNo = item.isNull("PHC_RegistrationNo") ? null : item.getString("PHC_RegistrationNo");
                                String comments = item.getString("Comments");
                                String finalId = item.isNull("FinalID") ? null : item.getString("FinalID");
                                String typeDesc = item.isNull("TypeDesc") ? null : item.getString("TypeDesc");
                                String isRegWithPHC = item.getString("isRegWithPHC");
                                String userName = item.getString("User_Name");
                                String lastUpdatedBy = item.getString("LastUpdatedBy");

                                NotificationItem notificationItem = new NotificationItem(
                                        complaintsDetailSeq, title, outletName, complainantAddress,
                                        complaintDate, complainantContactNo, aqcComplaintsSourceDesc,
                                        district, tehsilDesc, phcRegistrationNo, comments, finalId,
                                        typeDesc, isRegWithPHC, userName, lastUpdatedBy
                                );
                                newEntries.add(notificationItem);
                            }

                            // Save new entries to local database
                            saveToDatabase(newEntries);

                            // Start NotificationService to create notification
                            for (NotificationItem Nitem : newEntries) {
                                Intent intent = new Intent(getApplicationContext(), NotificationService.class);
                                intent.putExtra("title", Nitem.getTitle());
                                intent.putExtra("outletName", Nitem.getOutletName());
                                getApplicationContext().startService(intent);
                            }


                        } catch (JSONException e) {
                            Log.e(TAG, "Error parsing response", e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Error fetching notifications", error);
                    }
                }
        );

        requestQueue.add(jsonObjectRequest);
    }

    private void saveToDatabase(List<NotificationItem> notifications) {
        // Get an instance of the database
        NotificationDatabase db = NotificationDatabase.getInstance(getApplicationContext());

        // Run the database operations in a background thread to avoid blocking the main thread
        Executors.newSingleThreadExecutor().execute(() -> {
            db.notificationDao().insertAll(notifications);
        });
    }

    // Define constraints and create OneTimeWorkRequest
    public static void scheduleNotificationWork(Context context) {
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED) // Run only when network is connected
                .build();

        OneTimeWorkRequest notificationWork = new OneTimeWorkRequest.Builder(NotificationWorker.class)
                .setConstraints(constraints) // Apply constraints to the WorkRequest
                .build();

        // Enqueue the work request
        WorkManager.getInstance(context).enqueue(notificationWork);
    }
}


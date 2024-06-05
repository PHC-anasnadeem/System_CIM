package com.phc.cim.Activities.Common;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
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
import com.phc.cim.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NotificationActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private NotificationAdapter adapter;
    private List<NotificationItem> notificationList;
    private RequestQueue requestQueue;
    ProgressBar progressBar;
    String roleID, UserID, username, isStat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);


        progressBar = findViewById(R.id.progressBar);

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize Notification List
        notificationList = new ArrayList<>();
        adapter = new NotificationAdapter(this, notificationList);
        recyclerView.setAdapter(adapter);

        requestQueue = Volley.newRequestQueue(this);

        SharedPreferences prefs = getSharedPreferences("MyPrefsFile", MODE_PRIVATE);
        roleID = prefs.getString("roleID", null);
        UserID = prefs.getString("UserID", null);
        username = prefs.getString("username", null);
        isStat = prefs.getString("isStat", null);

        GetNotification(UserID);

    }

//    private void GetNotification(String userID) {
//
//            progressBar.setVisibility(View.VISIBLE);
//
//            // Construct the URL with FinalID parameter
//            //String url = "https://census.phc.org.pk:51599/api/Allocation/GetComplaintsDataAgainstOfficer?AuthorizedOfficer="+ finalID;
//            String url = "https://localhost:44352/api/Allocation/GetComplaintsDataAgainstOfficer?AuthorizedOfficer="+
//
//            // Create JSON request
//            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
//                    new Response.Listener<JSONObject>() {
//                        @Override
//                        public void onResponse(JSONObject response) {
//                            progressBar.setVisibility(View.GONE);
//                            try {
//                                JSONArray complaintsData = response.getJSONArray("ComplaintsData");
//                                if (complaintsData.length() == 0) {
//                                    Toast.makeText(NotificationActivity.this, "There is no notification", Toast.LENGTH_SHORT).show();
//                                    return;
//                                }
//                                List<NotificationItem> newEntries = new ArrayList<>();
//
//                                // Parse JSON response and create DiaryEntry objects
//                                for (int i = 0; i < complaintsData.length(); i++) {
//                                    JSONObject item = complaintsData.getJSONObject(i);
//
//                                    int complaintsDetailSeq = item.getInt("ComplaintsDetail_SEQ");
//                                    String title = item.getString("title");
//                                    String outletName = item.getString("OutletName");
//                                    String complainantAddress = item.getString("ComplainantAddress");
//                                    String complaintDate = item.getString("ComplaintDate");
//                                    String complainantContactNo = item.getString("ComplainantContactNo");
//                                    String aqcComplaintsSourceDesc = item.getString("AQC_ComplaintsSource_Desc");
//                                    String district = item.getString("District");
//                                    String tehsilDesc = item.getString("TehsilDesc");
//                                    String phcRegistrationNo = item.isNull("PHC_RegistrationNo") ? null : item.getString("PHC_RegistrationNo");
//                                    String comments = item.getString("Comments");
//                                    String finalId = item.isNull("FinalID") ? null : item.getString("FinalID");
//                                    String typeDesc = item.isNull("TypeDesc") ? null : item.getString("TypeDesc");
//                                    String isRegWithPHC = item.getString("isRegWithPHC");
//                                    String userName = item.getString("User_Name");
//                                    String lastUpdatedBy = item.getString("LastUpdatedBy");
//
//                                    NotificationItem notificationItem = new NotificationItem(
//                                            complaintsDetailSeq, title, outletName, complainantAddress,
//                                            complaintDate, complainantContactNo, aqcComplaintsSourceDesc,
//                                            district, tehsilDesc, phcRegistrationNo, comments, finalId,
//                                            typeDesc, isRegWithPHC, userName, lastUpdatedBy
//                                    );
//                                    newEntries.add(notificationItem);
//                                }
//
//                                // Update diaryEntryList and notify adapter
////                                diaryEntryList.clear();
////                                diaryEntryList.addAll(newDiaryEntries);
//                                adapter.notifyDataSetChanged();
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    },
//                    new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//                            progressBar.setVisibility(View.GONE);
//                            Log.e("Volley Error", error.toString());
//                            // Handle error response
//                            Toast.makeText(NotificationActivity.this, "Failed to fetch data. Please try again later.", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//            );
//
//            // Add the request to the RequestQueue
//            requestQueue.add(jsonObjectRequest);
//        }

    private void GetNotification(String userID) {
        progressBar.setVisibility(View.VISIBLE);

        // Construct the URL with AuthorizedOfficer parameter
        String url = "https://census.phc.org.pk:51599/api/Allocation/GetComplaintsDataAgainstOfficer?AuthorizedOfficer=" + userID;

        // Create JSON request
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressBar.setVisibility(View.GONE);
                        try {
                            JSONArray complaintsData = response.getJSONArray("ComplaintsData");
                            if (complaintsData.length() == 0) {
                                Toast.makeText(NotificationActivity.this, "There is no notification", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            List<NotificationItem> newEntries = new ArrayList<>();

                            // Parse JSON response and create NotificationItem objects
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

                            // Update diaryEntryList and notify adapter
                            notificationList.clear();
                            notificationList.addAll(newEntries);
                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(NotificationActivity.this, "Error parsing response", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE);
                        Log.e("Volley Error", error.toString());
                        // Handle error response
                        Toast.makeText(NotificationActivity.this, "Failed. Please try again later.", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        // Add the request to the RequestQueue
        requestQueue.add(jsonObjectRequest);
    }




}

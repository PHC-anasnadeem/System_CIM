package com.phc.cim.Activities.Common;

import android.app.DatePickerDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.phc.cim.Adapters.DiaryEntryAdapter;
import com.phc.cim.DataElements.DiaryEntry;
import com.phc.cim.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DesealListing extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<DiaryEntry> diaryEntryList;
    private DiaryEntryAdapter diaryEntryAdapter;
    private RequestQueue requestQueue;
    private EditText etFinalID;
    private Button btnFetch;
    private ProgressBar progressBar;
    private TextView tvNoResults;
    private Toolbar toolbar;
    private String finalID;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deseal_listing);

        // Initialize UI components
        initializeViews();
        
        // Set up toolbar
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        // Set up RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        diaryEntryList = new ArrayList<>();
        diaryEntryAdapter = new DiaryEntryAdapter(diaryEntryList);
        recyclerView.setAdapter(diaryEntryAdapter);

        // Initialize Volley request queue
        requestQueue = Volley.newRequestQueue(this);

        // Set click listener for fetch button
        btnFetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInput()) {
                    if (isInternetAvailable()) {
                        fetchDiaryEntries(DesealListing.this);
                    } else {
                        Toast.makeText(getApplicationContext(), "Internet not available. Please check your connection.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void initializeViews() {
        toolbar = findViewById(R.id.toolbar);
        etFinalID = findViewById(R.id.FinalID);
        btnFetch = findViewById(R.id.btnFetch);
        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.recyclerView);
        tvNoResults = findViewById(R.id.tvNoResults);
    }

    private boolean validateInput() {
        String finalIDInput = etFinalID.getText().toString().trim();
        if (finalIDInput.isEmpty()) {
            etFinalID.setError("Please enter a Final ID");
            etFinalID.requestFocus();
            return false;
        }
        return true;
    }

    private void fetchDiaryEntries(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("Context cannot be null");
        }

        // Retrieve user input
        finalID = etFinalID.getText().toString().trim();
        
        // Show progress and hide no results message
        progressBar.setVisibility(View.VISIBLE);
        tvNoResults.setVisibility(View.GONE);
        
        // Clear previous results
        diaryEntryList.clear();
        diaryEntryAdapter.notifyDataSetChanged();

        // Construct the URL with FinalID parameter
        String baseurl = context.getResources().getString(R.string.baseurl);
        String url = baseurl + "GetDeSealOrders?FinalID=" + finalID;

        Log.d("DesealListing", "Fetching data from URL: " + url);

        // Create JSON array request
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        progressBar.setVisibility(View.GONE);
                        try {
                            if (response.length() == 0) {
                                tvNoResults.setVisibility(View.VISIBLE);
                                return;
                            }
                            
                            List<DiaryEntry> newDiaryEntries = new ArrayList<>();

                            // Parse JSON response and create DiaryEntry objects
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject item = response.getJSONObject(i);
                                
                                // Extract data with null/error handling
                                int finalID = item.optInt("finalID", 0);
                                int aqcFileNo = item.optInt("caseFileID", 0);
                                String comments = item.optString("Comments", "No comments available");
                                String diaryNo = item.optString("OrderNo", "N/A");
                                String district = item.optString("District", "N/A");
                                String outletName = item.optString("OutletName", "N/A");
                                String desealDate = item.optString("DesealDate", "");

                                // Create DiaryEntry object and add to list
                                DiaryEntry diaryEntry = new DiaryEntry(finalID, aqcFileNo, comments, diaryNo, district, outletName, desealDate);
                                newDiaryEntries.add(diaryEntry);
                            }

                            // Update diaryEntryList and notify adapter
                            diaryEntryList.addAll(newDiaryEntries);
                            diaryEntryAdapter.notifyDataSetChanged();
                            
                            // Scroll to top if results are found
                            if (!diaryEntryList.isEmpty()) {
                                recyclerView.smoothScrollToPosition(0);
                            }
                        } catch (JSONException e) {
                            Log.e("DesealListing", "JSON parsing error: " + e.getMessage());
                            Toast.makeText(DesealListing.this, "Error parsing data. Please try again.", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE);
                        Log.e("DesealListing", "Volley Error: " + error.toString());
                        
                        // Show appropriate error message based on error type
                        String errorMessage = "Failed to fetch data. Please try again later.";
                        if (error.networkResponse != null) {
                            if (error.networkResponse.statusCode == 404) {
                                errorMessage = "Data not found. Please check the Final ID.";
                            } else if (error.networkResponse.statusCode >= 500) {
                                errorMessage = "Server error. Please try again later.";
                            }
                        }
                        Toast.makeText(DesealListing.this, errorMessage, Toast.LENGTH_SHORT).show();
                    }
                }
        );

        // Add the request to the RequestQueue
        requestQueue.add(jsonArrayRequest);
    }

    private long extractTimestamp(String formattedDate) {
        // Extract the numeric part of the date string
        String timestampStr = formattedDate.replaceAll("[^0-9]", "");
        return Long.parseLong(timestampStr);
    }

    private boolean isInternetAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }
        return false;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
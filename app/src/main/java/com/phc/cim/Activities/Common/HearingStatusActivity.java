package com.phc.cim.Activities.Common;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.phc.cim.Adapters.HearingAdapter;

import com.phc.cim.DataElements.Hearing;
import com.phc.cim.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class HearingStatusActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private HearingAdapter hearingAdapter;
    private List<Hearing> hearingList;
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
        setContentView(R.layout.activity_hearing_status);

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
        hearingList = new ArrayList<>();
        hearingAdapter = new HearingAdapter(this, hearingList);
        recyclerView.setAdapter(hearingAdapter);

        // Initialize Volley request queue
        requestQueue = Volley.newRequestQueue(this);

        // Set click listener for fetch button
        btnFetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInput()) {
                    if (isInternetAvailable()) {
                        fetchHearingStatus(HearingStatusActivity.this);
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

    private void fetchHearingStatus(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("Context cannot be null");
        }

        // Retrieve user input
        finalID = etFinalID.getText().toString().trim();
        
        // Show progress and hide no results message
        progressBar.setVisibility(View.VISIBLE);
        tvNoResults.setVisibility(View.GONE);
        
        // Clear previous results
        hearingList.clear();
        hearingAdapter.notifyDataSetChanged();

        // Construct the URL with FinalID parameter
        String baseurl = context.getResources().getString(R.string.baseurl);
        String url = baseurl + "GetHearingStatus?FinalID=" + finalID;

        Log.d("HearingStatusActivity", "Fetching data from URL: " + url);

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
                            
                            List<Hearing> newHearingEntries = new ArrayList<>();

                            // Parse JSON response and create Hearing objects
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject item = response.getJSONObject(i);
                                
                                // Extract data with null/error handling
                                String district = item.optString("District", "N/A");
                                String activeStatus = item.optString("activestatus", "N/A");
                                String caseFileId = item.optString("casefileid", "N/A");
                                String comments = item.optString("comments", "No comments available");
                                String committees = item.optString("committees", "N/A");
                                String hearingStatusDesc = item.optString("hearingstatus_desc", "N/A");
                                String outletAddress = item.optString("outletaddress", "N/A");
                                String outletName = item.optString("outletname", "N/A");
                                int finalId = item.optInt("final_id", 0);
                                String fineImposed = item.optString("fineimposed", "N/A");
                                Integer isFineImposed = item.isNull("isfineimposed") ? null : item.optInt("isfineimposed");

                                String createDateFormatted = item.getString("create_date");
                                String hearingScheduledDateFormatted = item.getString("hearingscheduledate");

                                // Create Hearing object
                                Hearing hearing = new Hearing(
                                        district, activeStatus,
                                        caseFileId, comments, committees,
                                        createDateFormatted, finalId, fineImposed,
                                        hearingScheduledDateFormatted, hearingStatusDesc,
                                        isFineImposed, outletAddress, outletName);

                                newHearingEntries.add(hearing);
                            }

                            // Update hearingList and notify adapter
                            hearingList.addAll(newHearingEntries);
                            hearingAdapter.notifyDataSetChanged();
                            
                            // Scroll to top if results are found
                            if (!hearingList.isEmpty()) {
                                recyclerView.smoothScrollToPosition(0);
                            }
                        } catch (JSONException e) {
                            Log.e("HearingStatusActivity", "JSON parsing error: " + e.getMessage());
                            Toast.makeText(HearingStatusActivity.this, "Error parsing data. Please try again.", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE);
                        Log.e("HearingStatusActivity", "Volley Error: " + error.toString());
                        
                        // Show appropriate error message based on error type
                        String errorMessage = "Failed to fetch data. Please try again later.";
                        if (error.networkResponse != null) {
                            if (error.networkResponse.statusCode == 404) {
                                errorMessage = "Data not found. Please check the Final ID.";
                                tvNoResults.setVisibility(View.VISIBLE);
                            } else if (error.networkResponse.statusCode >= 500) {
                                errorMessage = "Server error. Please try again later.";
                            }
                        }
                        Toast.makeText(HearingStatusActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                    }
                }
        );

        // Add the request to the RequestQueue
        requestQueue.add(jsonArrayRequest);
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
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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.phc.cim.Adapters.DiaryEntryAdapter;
import com.phc.cim.DataElements.DiaryEntry;
import com.phc.cim.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DesealListing extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private DiaryEntryAdapter diaryEntryAdapter;
    private List<DiaryEntry> diaryEntryList;
    private RequestQueue requestQueue;
    private EditText etFinalID, etDistrict, etStartDate, etEndDate;
    private Button btnFetch;
    String savedToken,savedUsername,savedEmail;
    int savedHceUserIdSeq;
    ProgressBar progressBar;
    String finalID;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deseal_listing);

        etFinalID = findViewById(R.id.FinalID);
        btnFetch = findViewById(R.id.btnFetch);

        progressBar = findViewById(R.id.progressBar);


        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        diaryEntryList = new ArrayList<>();
        diaryEntryAdapter = new DiaryEntryAdapter(diaryEntryList);
        recyclerView.setAdapter(diaryEntryAdapter);

        requestQueue = Volley.newRequestQueue(this);

        btnFetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isInternetAvailable()) {
                    fetchDiaryEntries(DesealListing.this);
                } else {
                    Toast.makeText(getApplicationContext(), "Internet not available. Please check your connection.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void fetchDiaryEntries(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("Context cannot be null");
        }

        // Retrieve user input
        finalID = etFinalID.getText().toString().trim();
        progressBar.setVisibility(View.VISIBLE);

        // Construct the URL with FinalID parameter
        String baseurl = context.getResources().getString(R.string.baseurl);
        String token = context.getResources().getString(R.string.token);
        String url = baseurl + "GetDeSealOrders?FinalID=" + finalID;

        // Create JSON array request
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        progressBar.setVisibility(View.GONE);
                        try {
                            if (response.length() == 0) {
                                Toast.makeText(DesealListing.this, "This final ID has not been de-sealed yet", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            List<DiaryEntry> newDiaryEntries = new ArrayList<>();

                            // Parse JSON response and create DiaryEntry objects
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject item = response.getJSONObject(i);
                                int finalID = item.getInt("finalID");
                                int aqcFileNo = item.getInt("caseFileID");
                                String comments = item.optString("Comments", "");
                                String diaryNo = item.getString("OrderNo");
                                String district = item.getString("District");
                                String outletName = item.getString("OutletName");
                                String formattedDate = item.getString("DesealDate");

                                // Extract the timestamp from the formattedDate
                                long timestamp = extractTimestamp(formattedDate);
                                Date date = new Date(timestamp);

                                // Format the date
                                SimpleDateFormat targetFormat = new SimpleDateFormat("dd MMM yyyy");
                                String desealDate = targetFormat.format(date);

                                // Assuming DiaryEntry has the appropriate constructor
                                DiaryEntry diaryEntry = new DiaryEntry(finalID, aqcFileNo, comments, diaryNo, district, outletName, desealDate);
                                newDiaryEntries.add(diaryEntry);
                            }

                            // Update diaryEntryList and notify adapter
                            diaryEntryList.clear();
                            diaryEntryList.addAll(newDiaryEntries);
                            diaryEntryAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE);
                        Log.e("Volley Error", error.toString());
                        // Handle error response
                        Toast.makeText(DesealListing.this, "Failed to fetch data. Please try again later.", Toast.LENGTH_SHORT).show();
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

    private void showDatePickerDialog(final EditText dateField) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(DesealListing.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String selectedDate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                dateField.setText(selectedDate);
            }
        }, year, month, day);
        datePickerDialog.show();
    }
}
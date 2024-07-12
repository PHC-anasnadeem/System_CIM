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
import com.android.volley.toolbox.Volley;
import com.phc.cim.Adapters.DiaryEntryAdapter;
import com.phc.cim.Adapters.HearingAdapter;
import com.phc.cim.DataElements.DiaryEntry;
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
import java.util.TimeZone;

public class HearingStatusActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private HearingAdapter hearingAdapter;
    private List<Hearing> HearingList;
    private RequestQueue requestQueue;
    private EditText etFinalID;
    private Button btnFetch;
    ProgressBar progressBar;
    String finalID;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hearing_status);

        etFinalID = findViewById(R.id.FinalID);
        btnFetch = findViewById(R.id.btnFetch);

        progressBar = findViewById(R.id.progressBar);


        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        HearingList = new ArrayList<>();
        hearingAdapter = new HearingAdapter(context, HearingList);
        recyclerView.setAdapter(hearingAdapter);

        requestQueue = Volley.newRequestQueue(this);

        btnFetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isInternetAvailable()) {
                    fetchDiaryEntries(HearingStatusActivity.this);
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
        String url = baseurl + "GetHearingStatus?FinalID=" + finalID;

        // Create JSON array request
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        progressBar.setVisibility(View.GONE);
                        try {
                            if (response.length() == 0) {
                                Toast.makeText(HearingStatusActivity.this, "This final ID has not been de-sealed yet", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            List<Hearing> hearingEntries = new ArrayList<>();

                            // Parse JSON response and create Hearing objects
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject item = response.getJSONObject(i);

                                String district = item.getString("District");
                                String phcQuackSealedFileNo = item.getString("PHCQUACKSEALEDFILENO");
                                String activeStatus = item.getString("activestatus");
                                String caseFileId = item.getString("casefileid");
                                String comments = item.getString("comments");
                                String committees = item.getString("committees");
                                String hearingStatusDesc = item.getString("hearingstatus_desc");
                                String outletAddress = item.getString("outletaddress");
                                String outletName = item.getString("outletname");
                                int finalId = item.getInt("final_id");
                                String fineImposed = item.getString("fineimposed");
                                Integer isFineImposed = item.isNull("isfineimposed") ? null : item.getInt("isfineimposed");

                                String createDateString = item.getString("create_date");
                                String createDate = createDateString.replaceAll("[^0-9]", "");

                                String hearingScheduledDateString = item.getString("hearingscheduledate");
                                String hearingScheduledDate = hearingScheduledDateString.replaceAll("[^0-9]", "");

                                // Convert the numeric strings to long
                                long createDateMillis = Long.parseLong(createDate);
                                long hearingScheduledDateMillis = Long.parseLong(hearingScheduledDate);

                                // Create Date objects from milliseconds
                                Date createDateObj = new Date(createDateMillis);
                                Date hearingScheduledDateObj = new Date(hearingScheduledDateMillis);

                                // Debug: Print Date objects
                                System.out.println("Create Date Obj: " + createDateObj);
                                System.out.println("Hearing Scheduled Date Obj: " + hearingScheduledDateObj);

                                // Format the dates
                                SimpleDateFormat targetFormat = new SimpleDateFormat("dd-MM-yyy", Locale.ENGLISH);
                                String createDateFormatted = targetFormat.format(createDateObj);
                                String hearingScheduledDateFormatted = targetFormat.format(hearingScheduledDateObj);


                                // Create Hearing object
                                Hearing hearing = new Hearing(
                                        district, activeStatus,
                                        caseFileId, comments, committees,
                                        createDateFormatted, finalId, fineImposed,
                                        hearingScheduledDateFormatted, hearingStatusDesc,
                                        isFineImposed, outletAddress, outletName);

                                hearingEntries.add(hearing);
                            }

                            // Update hearingList and notify adapter
                            HearingList.clear();
                            HearingList.addAll(hearingEntries);
                            hearingAdapter.notifyDataSetChanged();
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
                        Toast.makeText(HearingStatusActivity.this, "Failed. Please try again later.", Toast.LENGTH_SHORT).show();
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

        DatePickerDialog datePickerDialog = new DatePickerDialog(HearingStatusActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String selectedDate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                dateField.setText(selectedDate);
            }
        }, year, month, day);
        datePickerDialog.show();
    }

}
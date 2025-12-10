package com.phc.cim.Activities.Common;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.phc.cim.Adapters.RegistrationItemAdapter;
import com.phc.cim.DataElements.RegistrationItem;
import com.phc.cim.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RegistrationStatus extends AppCompatActivity {

    private EditText searchField1;
    private Button btnSearch, btnClear;
    private RecyclerView recyclerView;
    private TextView tvNoResults;
    private RegistrationItemAdapter adapter;

    private List<RegistrationItem> itemList = new ArrayList<>();
    private ProgressDialog progressDialog;

    private String baseurl;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_status);

        // ---------------- TOOLBAR ----------------
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        // ---------------- INIT -------------------
        searchField1 = findViewById(R.id.searchField1);
        btnSearch = findViewById(R.id.btnSearch);
        btnClear = findViewById(R.id.btnClear);
        tvNoResults = findViewById(R.id.tvNoResults);
        recyclerView = findViewById(R.id.recyclerView);

        // Optional: Force uppercase input
        searchField1.setFilters(new InputFilter[]{new InputFilter.AllCaps()});

        // Add TextWatcher to validate prefix
        searchField1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                String input = s.toString();
                if (!input.isEmpty() && !input.startsWith("OAP") && !input.startsWith("R-") &&
                        !input.startsWith("PL-") && !input.startsWith("RL-")) {
                    searchField1.setError("Must start with OAP, R-, PL-, or RL-");
                }
            }
        });

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);

        baseurl = getResources().getString(R.string.baseurl);
        token = getResources().getString(R.string.token);

        // ---------------- RECYCLER VIEW ----------------
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RegistrationItemAdapter(itemList);
        recyclerView.setAdapter(adapter);

        // ---------------- BUTTON LISTENERS ----------------
        btnSearch.setOnClickListener(v -> performSearch());
        btnClear.setOnClickListener(v -> clearSearch());
    }

    // ---------------- SEARCH ----------------
    private void performSearch() {
        String query = searchField1.getText().toString().trim();

        if (query.isEmpty()) {
            Toast.makeText(this, "Enter Registration Number / License No / OAP", Toast.LENGTH_SHORT).show();
            return;
        }

        hideKeyboard();
        callApi(query);
    }

    // ---------------- API CALL ----------------
    private void callApi(String search) {
        progressDialog.show();

        String url ="https://cim.phc.org.pk:8099/PHCCensusData.svc/GetHCERegistrationData?Search_String=" + search;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    progressDialog.dismiss();
                    parseResponse(response);
                },
                error -> {
                    progressDialog.dismiss();

                    String message = "Unknown error occurred";
                    if (error.networkResponse != null) {
                        message = "Error Code: " + error.networkResponse.statusCode;
                    } else if (error.getMessage() != null) {
                        message = error.getMessage();
                    }

                    Toast.makeText(this, "API Error: " + message, Toast.LENGTH_LONG).show();
                });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }

    // ---------------- PARSE RESPONSE ----------------
    private void parseResponse(JSONArray response) {
        itemList.clear();

        try {
            for (int i = 0; i < response.length(); i++) {
                JSONObject obj = response.getJSONObject(i);

                RegistrationItem item = new RegistrationItem(
                        obj.optString("Registration_Number", ""),
                        obj.optString("HCE_Name", ""),
                        obj.optString("HCE_License_Type", ""),
                        obj.optString("HCE_District", ""),
                        obj.optString("Registration_Date", "")
                );

                itemList.add(item);
            }

            adapter.updateList(itemList);

            tvNoResults.setVisibility(itemList.isEmpty() ? View.VISIBLE : View.GONE);

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Parsing error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    // ---------------- CLEAR SEARCH ----------------
    private void clearSearch() {
        searchField1.setText("");
        itemList.clear();
        adapter.updateList(itemList);
        tvNoResults.setVisibility(View.GONE);
        hideKeyboard();
    }

    // ---------------- HIDE KEYBOARD ----------------
    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null && getCurrentFocus() != null) {
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }
}

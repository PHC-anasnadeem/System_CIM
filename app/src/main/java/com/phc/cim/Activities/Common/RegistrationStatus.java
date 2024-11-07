package com.phc.cim.Activities.Common;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;


import com.phc.cim.Adapters.RegistrationItemAdapter;
import com.phc.cim.DataElements.RegistrationItem;
import com.phc.cim.R;

import java.util.ArrayList;
import java.util.List;

public class RegistrationStatus extends AppCompatActivity {

    private EditText searchField1, searchField2, searchField3;
    private RecyclerView recyclerView;
    private RegistrationItemAdapter adapter; // adapter class
    private List<RegistrationItem> itemList; // The original data list

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_status);

        searchField1 = findViewById(R.id.searchField1);
        searchField2 = findViewById(R.id.searchField2);
        searchField3 = findViewById(R.id.searchField3);
        recyclerView = findViewById(R.id.recyclerView);

        // Initialize itemList with your data
        itemList = getData(); // Replace with your method to get data

        // Set up RecyclerView and adapter
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RegistrationItemAdapter(itemList);
        recyclerView.setAdapter(adapter);

        // Set up search functionality
        setupSearchListeners();
    }

    private void setupSearchListeners() {
        // Set up OnEditorActionListener for the search field
        searchField1.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                String nameQuery = searchField1.getText().toString();
                String idQuery = searchField2.getText().toString();
                String statusQuery = searchField3.getText().toString();

                // Filter the adapter with the search queries
                adapter.filter(nameQuery, idQuery, statusQuery);

                // Hide the keyboard after searching
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(searchField1.getWindowToken(), 0);
                }
                return true; // Indicates the action was handled
            }
            return false;
        });

        // Optionally, add similar listeners for the other search fields if needed
        searchField2.setOnEditorActionListener((v, actionId, event) -> {
            // Similar logic for searchField2 if you want it to trigger search
            return false; // Return false to let the system handle other actions
        });

        searchField3.setOnEditorActionListener((v, actionId, event) -> {
            // Similar logic for searchField3 if you want it to trigger search
            return false; // Return false to let the system handle other actions
        });
    }

    private List<RegistrationItem> getData() {
        // Implement your data fetching logic here
        return new ArrayList<>(); // Return your data
    }
}


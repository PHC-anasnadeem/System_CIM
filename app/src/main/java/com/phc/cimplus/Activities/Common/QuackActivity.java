package com.phc.cimplus.Activities.Common;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.phc.cimplus.Activities.Aqc.ActionActivity;
import com.phc.cimplus.DataElements.District;
import com.phc.cimplus.DataElements.Division;
import com.phc.cimplus.DataElements.SectorType;
import com.phc.cimplus.DataElements.Tehsil;
import com.phc.cimplus.Managers.DataManager;
import com.phc.cimplus.Others.CurrentLocation;
import com.phc.cimplus.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

public class QuackActivity extends AppCompatActivity {

    double des_lat;
    double des_lng;
    DataManager dataManager;
    String MID;
    String MText;
    String jsonStr;
    Context context;
    CurrentLocation gps;
    EditText hce_nameEdit;
    EditText AddressEdit;
    EditText HCSP_nameEdit;
    EditText HCSP_SOEdit;
    EditText CNIC_Edit;
    EditText HCSP_ContactEdit;
    EditText coments;
    int a = 0;

    Spinner division_spinner;
    Spinner district_spinner;
    Spinner tehsil_spinner;
    Spinner uc_spinner;
    Spinner quackloc_spinner;

    LinearLayout layout_current_uc;
    TextView txt_current_uc_display;
    TextView txt_current_location_details;
    ImageView btn_refresh_uc;
    com.phc.cimplus.DataElements.CurrentUC detectedCurrentUC;
    double lastUCLatitude = 0.0;
    double lastUCLongitude = 0.0;


    String hce_nameText = "";
    String AddressText = "";
    String HCSP_nameText = "";
    String HCSP_SOText = "";
    String CNIC_Text = "";
    String HCSP_ContactText = "";

    String comnt = "";
    String final_id = "";
    String districtText = "";
    String quackloctext;
    String divisionText = "";
    String tehsilText = "";
    String ucText = "";
    String email = "";
    String password;
    String isEdit;
    String username;
    TextView errortext;
    ProgressDialog pDialog;
    double cur_latitude;
    double cur_longitude;
    float loc_accuracy;
    double user_latitude;
    double user_longitude;
    ImageButton refreshbtn;
    TextView accuracyTextView;
    ArrayList<Division> divisions;
    ArrayList<District> districts;
    ArrayList<SectorType> sectorTypes;
    ArrayList<Tehsil> tehsils;
    private ArrayList<String> _images;
    String roleid;

    private TextInputEditText latitudeEditText;
    private TextInputEditText longitudeEditText;
    private LinearLayout locationPickerBlock;
    private Button btnPickLocation;
    private static final int REQUEST_LOCATION_PICKER = 1001;
    double latitude;
    double longitude;
    EditText startTimeEditText, endTimeEditText;
    String startTimeText, endTimeText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quack);

        context = this;
        
        // Initialize and set up the toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        
        dataManager = new DataManager(context);
        gps = new CurrentLocation(this);

        accuracyTextView = (TextView) findViewById(R.id.accuracylabel);
        hce_nameEdit = (EditText) findViewById(R.id.hce_name);
        AddressEdit = (EditText) findViewById(R.id.Address);
        HCSP_nameEdit = (EditText) findViewById(R.id.HCSP_Name);
        HCSP_SOEdit = (EditText) findViewById(R.id.HCSP_SO);
        CNIC_Edit = (EditText) findViewById(R.id.CNIC);
        HCSP_ContactEdit = (EditText) findViewById(R.id.Mobile);
        coments = (EditText) findViewById(R.id.comments);
        errortext = (TextView) findViewById(R.id.errortext);
        pDialog = new ProgressDialog(context);
        layout_current_uc = (LinearLayout) findViewById(R.id.layout_current_uc);
        txt_current_uc_display = (TextView) findViewById(R.id.txt_current_uc_display);
        txt_current_location_details = (TextView) findViewById(R.id.txt_current_location_details);
        btn_refresh_uc = (ImageView) findViewById(R.id.btn_refresh_uc);
        if (btn_refresh_uc != null) {
            btn_refresh_uc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (gps != null && !gps.hasLocationPermission()) {
                        gps.showPermissionSettingsDialog();
                        return;
                    }
                    Toast.makeText(context, "Refreshing location...", Toast.LENGTH_SHORT).show();
                    if (gps != null) {
                        gps.refreshLocation();
                        cur_latitude = gps.getLatitude();
                        cur_longitude = gps.getLongitude();
                    }
                    detectAndShowCurrentUC(true);
                }
            });
        }
        division_spinner = (Spinner) findViewById(R.id.division_spinner);
        district_spinner = (Spinner) findViewById(R.id.district);
        tehsil_spinner = (Spinner) findViewById(R.id.Tehsil_spinner);
        uc_spinner = (Spinner) findViewById(R.id.uc_spinner);
        quackloc_spinner = (Spinner) findViewById(R.id.quackloc_spinner);
        latitudeEditText = findViewById(R.id.lat);
        longitudeEditText = findViewById(R.id.lng);
        locationPickerBlock = findViewById(R.id.location_picker_block);
        btnPickLocation = findViewById(R.id.btn_pick_location);
        startTimeEditText = findViewById(R.id.Start_Time);
        endTimeEditText = findViewById(R.id.End_Time);

        btnPickLocation.setOnClickListener(v -> {
            Intent intentMap = new Intent(context, LocationPickerActivity.class);
            double currentLat = cur_latitude;
            double currentLng = cur_longitude;
            
            try {
                if (!latitudeEditText.getText().toString().isEmpty())
                    currentLat = Double.parseDouble(latitudeEditText.getText().toString());
                if (!longitudeEditText.getText().toString().isEmpty())
                    currentLng = Double.parseDouble(longitudeEditText.getText().toString());
            } catch (Exception ignored) {}
            
            intentMap.putExtra("lat", currentLat);
            intentMap.putExtra("lng", currentLng);
            startActivityForResult(intentMap, REQUEST_LOCATION_PICKER);
        });
        errortext.setVisibility(View.GONE);

        Intent intent = getIntent();
        email = (String) intent.getSerializableExtra("email");
        password = (String) intent.getSerializableExtra("password");
        username = (String) intent.getSerializableExtra("username");
        isEdit = (String) intent.getSerializableExtra("isEdit");
        SharedPreferences prefs = getSharedPreferences("MyPrefsFile", MODE_PRIVATE);
        roleid = prefs.getString("RoleID", null);//"No name defined" is the default value.

        // Initialize pDialog in onCreate
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Submitting your update, Please wait...");
        pDialog.setCancelable(false);

        // Check location permissions
        if (checkLocationPermissions()) {
            initializeLocationServices();
        } else {
            requestLocationPermissions();
        }

        setupDivisionSpinner();
        setupQuackLocationSpinner();

        Button markasdone = findViewById(R.id.btn_submit);
        markasdone.setOnClickListener(v -> handleSubmit());

        startTimeEditText.setOnClickListener(v -> showTimePicker(startTimeEditText));
        endTimeEditText.setOnClickListener(v -> showTimePicker(endTimeEditText));


        //-------------------------Current Location----------------------------

//        if (gps.canGetLocation()) {
//
//            cur_latitude = gps.getLatitude();
//            cur_longitude = gps.getLongitude();
//            loc_accuracy = gps.getAccuracy();
//            accuracyTextView.setText("Location Accurate to " + loc_accuracy + " meters");
//            accuracyTextView.setTextColor(Color.parseColor("#303F9F"));
//
//            if (cur_latitude != 0.0 && cur_longitude != 0.0) {
//
//
//            } else {
//                cur_latitude = gps.getLatitude();
//                cur_longitude = gps.getLongitude();
//
//
//            }
//        } else {
//            // Can't get location.
//            // GPS or network is not enabled.
//            // Ask user to enable GPS/network in settings.
//            gps.showSettingsAlert();
//        }
//        this.runThread();


    }

    private void showTimePicker(EditText targetEditText) {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePicker = new TimePickerDialog(this,
                (view, selectedHour, selectedMinute) -> {
                    String formattedTime = String.format("%02d:%02d", selectedHour, selectedMinute);
                    targetEditText.setText(formattedTime);
                },
                hour, minute, true);

        timePicker.show();
    }



    private void setupQuackLocationSpinner() {
        String[] quackloc = {"Please Select", "Yes", "No"};
        ArrayAdapter<String> quackloc_adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, quackloc) {
            @Override
            public boolean isEnabled(int position) {
                return position != 0;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        quackloc_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        quackloc_spinner.setAdapter(quackloc_adapter);

        quackloc_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                quackloctext = parent.getItemAtPosition(position).toString();
                if (position == 2) { // "No"
                    locationPickerBlock.setVisibility(View.VISIBLE);
                    latitudeEditText.setText(String.valueOf(cur_latitude));
                    longitudeEditText.setText(String.valueOf(cur_longitude));
                } else {
                    locationPickerBlock.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }


    private boolean checkLocationPermissions() {
        // Implement your logic to check location permissions
        return true;
    }

    private void initializeLocationServices() {
        if (gps.canGetLocation()) {
            cur_latitude = gps.getLatitude();
            cur_longitude = gps.getLongitude();
            loc_accuracy = gps.getAccuracy();
            accuracyTextView.setText("Location Accurate to " + loc_accuracy + " meters");
            accuracyTextView.setTextColor(Color.parseColor("#303F9F"));

            gps.setOnLocationChangeListener(new CurrentLocation.OnLocationChangeListener() {
                @Override
                public void onLocationChanged(double lat, double lng, float accuracy) {
                    cur_latitude = lat;
                    cur_longitude = lng;
                    loc_accuracy = accuracy;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            accuracyTextView.setText("Location Accurate to " + loc_accuracy + " meters");
                        }
                    });
                    detectAndShowCurrentUC(false);
                }
            });

            detectAndShowCurrentUC(false);
            runThread();
        } else {
            gps.showSettingsAlert();
        }
    }

    private void detectAndShowCurrentUC() {
        detectAndShowCurrentUC(false);
    }

    private void detectAndShowCurrentUC(final boolean forceRefresh) {
        if (cur_latitude == 0.0 || cur_longitude == 0.0) return;

        if (!forceRefresh && lastUCLatitude != 0.0 && lastUCLongitude != 0.0) {
            float[] results = new float[1];
            android.location.Location.distanceBetween(cur_latitude, cur_longitude, lastUCLatitude, lastUCLongitude, results);
            if (results[0] < 200) {
                // Moved less than 200 meters, keep existing
                return;
            }
        }

        new com.phc.cimplus.DownloadClases.DownloadCurrentUC(context, cur_latitude, cur_longitude, new com.phc.cimplus.DownloadClases.DownloadCurrentUC.OnUCDownloadedListener() {
            @Override
            public void onUCDetected(final com.phc.cimplus.DataElements.CurrentUC currentUC) {
                if (currentUC != null && currentUC.getDisplayName() != null && !currentUC.getDisplayName().isEmpty()) {
                    lastUCLatitude = cur_latitude;
                    lastUCLongitude = cur_longitude;
                    detectedCurrentUC = currentUC;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (layout_current_uc != null) {
                                layout_current_uc.setVisibility(View.VISIBLE);
                                if (txt_current_uc_display != null) {
                                    txt_current_uc_display.setText(currentUC.getDisplayName());
                                }
                                if (txt_current_location_details != null) {
                                    txt_current_location_details.setText("Division: " + currentUC.getDivision_N() + " | District: " + currentUC.getDistrict_N() + " | Tehsil: " + currentUC.getTehsil_N());
                                }
                            }
                            preselectLocationDropdowns(currentUC);
                        }
                    });
                }
            }
        });
    }

    private void preselectLocationDropdowns(final com.phc.cimplus.DataElements.CurrentUC currentUC) {
        if (currentUC == null) return;
        try {
            // 1. Division
            String targetDivision = currentUC.getDivision_N();
            if (targetDivision != null && !targetDivision.isEmpty() && division_spinner != null && division_spinner.getAdapter() != null) {
                for (int i = 0; i < division_spinner.getAdapter().getCount(); i++) {
                    if (division_spinner.getAdapter().getItem(i).toString().equalsIgnoreCase(targetDivision)) {
                        division_spinner.setSelection(i);
                        break;
                    }
                }
            }

            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    // 2. District
                    String targetDistrict = currentUC.getDistrict_N();
                    if (targetDistrict != null && !targetDistrict.isEmpty() && district_spinner != null && district_spinner.getAdapter() != null) {
                        for (int i = 0; i < district_spinner.getAdapter().getCount(); i++) {
                            if (district_spinner.getAdapter().getItem(i).toString().equalsIgnoreCase(targetDistrict)) {
                                district_spinner.setSelection(i);
                                break;
                            }
                        }
                    }

                    new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // 3. Tehsil
                            String targetTehsil = currentUC.getTehsil_N();
                            if (targetTehsil != null && !targetTehsil.isEmpty() && tehsil_spinner != null && tehsil_spinner.getAdapter() != null) {
                                for (int i = 0; i < tehsil_spinner.getAdapter().getCount(); i++) {
                                    if (tehsil_spinner.getAdapter().getItem(i).toString().equalsIgnoreCase(targetTehsil)) {
                                        tehsil_spinner.setSelection(i);
                                        break;
                                    }
                                }
                            }

                            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    // 4. UC
                                    String targetDisplayName = currentUC.getDisplayName();
                                    String targetUCName = currentUC.getUC_Name();
                                    if (uc_spinner != null && uc_spinner.getAdapter() != null) {
                                        for (int i = 0; i < uc_spinner.getAdapter().getCount(); i++) {
                                            String item = uc_spinner.getAdapter().getItem(i).toString();
                                            if ((targetDisplayName != null && item.equalsIgnoreCase(targetDisplayName)) ||
                                                (targetUCName != null && item.contains(targetUCName))) {
                                                uc_spinner.setSelection(i);
                                                break;
                                            }
                                        }
                                    }
                                }
                            }, 300);
                        }
                    }, 300);
                }
            }, 300);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void requestLocationPermissions() {
        // Implement your logic to request location permissions
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (gps != null) {
            gps.refreshLocation();
            cur_latitude = gps.getLatitude();
            cur_longitude = gps.getLongitude();
        }
        detectAndShowCurrentUC(false);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1 || requestCode == 50) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (gps != null) {
                    gps.refreshLocation();
                    cur_latitude = gps.getLatitude();
                    cur_longitude = gps.getLongitude();
                }
                detectAndShowCurrentUC(true);
            } else {
                if (gps != null) {
                    gps.showPermissionSettingsDialog();
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_LOCATION_PICKER && resultCode == RESULT_OK && data != null) {
            double pickedLat = data.getDoubleExtra("lat", 0);
            double pickedLng = data.getDoubleExtra("lng", 0);
            
            latitudeEditText.setText(String.valueOf(pickedLat));
            longitudeEditText.setText(String.valueOf(pickedLng));

            if (pickedLat != 0.0 && pickedLng != 0.0) {
                cur_latitude = pickedLat;
                cur_longitude = pickedLng;
                detectAndShowCurrentUC(true);
            }
        }
    }

    private void handleSubmit() {
        int count = 0;
        hce_nameText = hce_nameEdit.getText().toString();
        AddressText = AddressEdit.getText().toString();
        HCSP_nameText = HCSP_nameEdit.getText().toString();
        HCSP_SOText = HCSP_SOEdit.getText().toString();
        CNIC_Text = CNIC_Edit.getText().toString();
        HCSP_ContactText = HCSP_ContactEdit.getText().toString();
        comnt = coments.getText().toString();
        startTimeText = startTimeEditText.getText().toString();
        endTimeText = endTimeEditText.getText().toString();

        latitude = cur_latitude;
        longitude = cur_longitude;

        if (locationPickerBlock.getVisibility() == View.VISIBLE) {
            try {
                latitude = Double.parseDouble(latitudeEditText.getText().toString());
                longitude = Double.parseDouble(longitudeEditText.getText().toString());
            } catch (Exception e) {
                // fallback to current if parsing fails
            }
        }

        _images = new ArrayList<String>();


        if (comnt.equals("")) {
            coments.setError("Please Enter Comments");
            count++;
        }
        if (hce_nameText.equals("")) {
            hce_nameEdit.setError("Name can't be empty");
            count++;
        }
        if (AddressText.equals("")) {
            AddressEdit.setError("Address can't be empty");
            count++;
        }
        if (divisionText.equals("Please Select")) {
            setSpinnerError(division_spinner, ("Please select division"));
            count++;
        }
        if (districtText.equals("Please Select")) {
            setSpinnerError(district_spinner, ("Please select district"));
            count++;
        }
        if (tehsilText.equals("Please Select")) {
            setSpinnerError(tehsil_spinner, ("Please select tehsil"));
            count++;
        }
        if (quackloctext.equals("Please Select")) {
            setSpinnerError(quackloc_spinner, ("Please select Quack location"));
            count++;
        }
//        if (startTimeText.isEmpty()) {
//            startTimeEditText.setError("Please select start time");
//            count++;
//        }
//        if (endTimeText.isEmpty()) {
//            endTimeEditText.setError("Please select end time");
//            count++;
//        }

        if (count > 0) {
            errortext.setVisibility(View.VISIBLE);
        }
        //User Latitude & Longitude

        user_latitude = latitude;
        user_longitude = longitude;
        if (count < 1) {

            if (quackloctext.equals("No")) {
                latitude = cur_latitude;
                longitude = cur_longitude;
            }

            if (CNIC_Text.equals("")) {
                CNIC_Text = "00000-0000000-0";
            }
            if (HCSP_ContactText.equals("")) {
                HCSP_ContactText = "00000000000";
            }
            if (divisionText.equals("Please Select")) {
                divisionText = "";
            }
            if (districtText.equals("Please Select")) {
                districtText = "";
            }
            if (tehsilText.equals("Please Select")) {
                tehsilText = "";
            }

            pDialog.show();

            String url = getDirectionsUrl(context);
            DownloadTask downloadTask = new DownloadTask();
            //Start downloading json data from Google Directions API
            downloadTask.execute(url);

        }
    }


    private void runThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                boolean running = true;
                while (running) {
                    try {
                        Thread.sleep(30000); // Update every 30 seconds to reduce frequency
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        running = false; // Exit loop on interruption
                    }

                    // Fetch the current location
                    gps = new CurrentLocation(context);
                    cur_latitude = gps.getLatitude();
                    cur_longitude = gps.getLongitude();
                    loc_accuracy = gps.getAccuracy();

                    if (cur_latitude != 0.0 && cur_longitude != 0.0) {
                        detectAndShowCurrentUC(false);
                    }

                    // Update the UI on the main thread
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            accuracyTextView.setText("Location Accurate to " + loc_accuracy + " meters ");
                            accuracyTextView.setTextColor(Color.parseColor("#303F9F"));
                        }
                    });
                }
                Looper.loop();
            }
        }).start();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void setSpinnerError(Spinner spinner, String error) {
        View selectedView = spinner.getSelectedView();
        if (selectedView != null && selectedView instanceof TextView) {
            spinner.requestFocus();
            TextView selectedTextView = (TextView) selectedView;
            selectedTextView.setError("error"); // any name of the error will do
            selectedTextView.setTextColor(Color.RED); //text color in which you want your error message to be displayed
            selectedTextView.setText(error); // actual error message
            //spinner.performClick(); // to open the spinner list if error is found.

        }
    }

    private ArrayList<String> getsectorTypes() {

        ArrayList<String> sectorTypeList = new ArrayList<String>();
        int i = 0;
        for (SectorType sectorType : sectorTypes) {
            String sectortypelists = sectorType.getSectorType();
            sectorTypeList.add(sectortypelists);
            i++;
        }
        return sectorTypeList;
    }

    private void setupDivisionSpinner() {
        divisions = dataManager.getDivision();

        ArrayAdapter<String> division_spinneradapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, getDivision()) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return true;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);

                if (position == 0) {
                    // Set the hint text color gray
                    //tv.setTextColor(Color.GRAY);
                } else {
                    // tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        division_spinneradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        division_spinner.setAdapter(division_spinneradapter);
        division_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                //  ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
                divisionText = parent.getItemAtPosition(position).toString();
                getDistrict();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private ArrayList<String> getDivision() {

        ArrayList<String> divisionList = new ArrayList<String>();
        int i = 0;
        if (divisions != null && !divisions.isEmpty()) {
            for (Division division : divisions) {
                String divisionLists = division.getDivision();
                divisionList.add(divisionLists);
                i++;
            }
        }
        return divisionList;
    }

    private ArrayList<String> getDistrict() {
        districts = dataManager.getDistrictList(divisionText);
        ArrayList<String> districtList = new ArrayList<String>();
        int i = 0;
        for (District district : districts) {
            String districtLists = district.getDistrict();
            districtList.add(districtLists);
            i++;
        }

        ArrayAdapter<String> district_spinneradapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, districtList) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return true;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);

                if (position == 0) {
                    // Set the hint text color gray
                    //tv.setTextColor(Color.GRAY);
                } else {
                    // tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        district_spinneradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        district_spinner.setAdapter(district_spinneradapter);
        district_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                //  ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
                districtText = parent.getItemAtPosition(position).toString();
                getTehsil();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        return districtList;
    }



//    private ArrayList<String> getTehsil() {
//
//        tehsils = dataManager.getTehsilList(districtText);
//        ArrayList<String> TehsilList = new ArrayList<String>();
//        int i = 0;
//        for (Tehsil tehsil : tehsils) {
//            String Tehsillists = tehsil.getTehsil();
//            TehsilList.add(Tehsillists);
//            i++;
//        }
//
//
//        ArrayAdapter<String> tehsil_spinneradapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, TehsilList) {
//            @Override
//            public boolean isEnabled(int position) {
//                if (position == 0) {
//                    // Disable the first item from Spinner
//                    // First item will be use for hint
//                    return true;
//                } else {
//                    return true;
//                }
//            }
//
//            @Override
//            public View getDropDownView(int position, View convertView,
//                                        ViewGroup parent) {
//                View view = super.getDropDownView(position, convertView, parent);
//
//                if (position == 0) {
//                    // Set the hint text color gray
//                    //tv.setTextColor(Color.GRAY);
//                } else {
//                    // tv.setTextColor(Color.BLACK);
//                }
//                return view;
//            }
//        };
//        tehsil_spinneradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        tehsil_spinner.setAdapter(tehsil_spinneradapter);
//        tehsil_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            public void onItemSelected(AdapterView<?> parent, View view,
//                                       int position, long id) {
//                //  ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
//                tehsilText = parent.getItemAtPosition(position).toString();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//
//        return TehsilList;
//    }

    private ArrayList<String> getTehsil() {

        tehsils= dataManager.getTehsilList(districtText);
        ArrayList<String> TehsilList = new ArrayList<String>();
        int i=0;
        for (Tehsil tehsil : tehsils) {
            String Tehsillists= tehsil.getTehsil();
            if(!Tehsillists.equals("null"))
                TehsilList.add(Tehsillists);
            i++;
        }
        ArrayAdapter<String> tehsiladapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, TehsilList) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return true;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);

                if (position == 0) {
                    // Set the hint text color gray
                    //tv.setTextColor(Color.GRAY);
                } else {
                    // tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        tehsiladapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tehsil_spinner.setAdapter(tehsiladapter);
        tehsil_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
                tehsilText = parent.getItemAtPosition(position).toString();
                getUC();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return TehsilList;
    }

    private ArrayList<String> getUC() {
        // Filter by district only (not tehsil) so all UCs of detected district are shown
        // If district also not set, show all UCs
        ArrayList<com.phc.cimplus.DataElements.UC> ucs = dataManager.getUCList(districtText, "");
        ArrayList<String> UCList = new ArrayList<String>();
        if (ucs != null) {
            for (com.phc.cimplus.DataElements.UC uc : ucs) {
                String ucName = uc.getUcName();
                if (ucName != null && !ucName.equals("null")) {
                    UCList.add(ucName);
                }
            }
        }

        if (UCList.isEmpty() || !UCList.get(0).equals("Please Select")) {
            UCList.add(0, "Please Select");
        }

        ArrayAdapter<String> ucadapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, UCList) {
            @Override
            public boolean isEnabled(int position) {
                return true;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                return super.getDropDownView(position, convertView, parent);
            }
        };
        ucadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        if (uc_spinner != null) {
            uc_spinner.setAdapter(ucadapter);
            uc_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    ucText = parent.getItemAtPosition(position).toString();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

            final ArrayList<String> finalUCList = UCList;
            uc_spinner.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, android.view.MotionEvent event) {
                    if (event.getAction() == android.view.MotionEvent.ACTION_UP) {
                        com.phc.cimplus.Others.SearchableListDialog.show(context, "Select UC", finalUCList, new com.phc.cimplus.Others.SearchableListDialog.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(String item, int originalPosition) {
                                uc_spinner.setSelection(originalPosition);
                                ucText = item;
                            }
                        });
                        return true;
                    }
                    return false;
                }
            });
        }
        return UCList;
    }

    private class DownloadTask extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... url) {

            // For storing data from web service
            String data = "";

            try {
                // Fetching the data from web service
                data = downloadUrl(url[0]);
                Log.d("Background Task data", data.toString());
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();
            jsonStr = result;
            // Invokes the thread for parsing the JSON data
            parserTask.execute();

        }
    }

    private String getDirectionsUrl(Context context) {

        // Building the url to the web service
        String baseurl = context.getResources().getString(R.string.baseurl);
        String token = context.getResources().getString(R.string.token);

        double finalLat = cur_latitude;
        double finalLng = cur_longitude;

        if (locationPickerBlock.getVisibility() == View.VISIBLE) {
            try {
                finalLat = Double.parseDouble(latitudeEditText.getText().toString());
                finalLng = Double.parseDouble(longitudeEditText.getText().toString());
            } catch (Exception ignored) {}
        }

        String url = baseurl + "ReportQuack?strToken=" + token + "&HCEName=" + hce_nameText + "&HCEAddress=" + AddressText + "&Division=" + divisionText + "&District=" + districtText + "&Tehsil=" + tehsilText + "&SectorType=Private&HCSPName=" + HCSP_nameText + "&HCSP_SO=" + HCSP_SOText + "&HCSP_CNIC=" + CNIC_Text + "&HCSPContactNo=" + HCSP_ContactText + "&lat=" + finalLat + "&lng=" + finalLng + "&emailAddress=" + email + "&Comments=" + comnt + "&Userlat=" + user_latitude + "&Userlng=" + user_longitude + "&Start_Time=" + startTimeText + "&End_Time=" + endTimeText + "&QuackCategory=&QuackSubCategory=&RoleID=" + roleid + "&UC=" + ucText;
        url = url.replaceAll(" ", "%20");
        url = url.replaceAll("#", "%23");
        url = url.replaceAll(",", "%2C");

        return url;
    }

    private String downloadUrl(String strUrl) throws IOException {


        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {

            //Post Request
            URL url = new URL(strUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.setRequestMethod("POST");
            urlConnection.connect();

            int statusCode = urlConnection.getResponseCode();


            if (statusCode >= 200 && statusCode < 400) {
                // Create an InputStream in order to extract the response object
                iStream = urlConnection.getInputStream();
            } else {
                iStream = urlConnection.getErrorStream();
            }


            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        } catch (Exception e) {
            Log.d("Exception", e.toString());
        } finally {
            if (iStream != null) {
                try { iStream.close(); } catch (IOException ignored) {}
            }
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return data;
    }

    private class ParserTask extends AsyncTask<Object, Object, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog


        }

        @Override
        protected String doInBackground(Object... arg0) {

            // Making a request to url and getting response

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    MID = jsonObj.getString("MID");
                    MText = jsonObj.getString("MText");
                    return MID;
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                Log.e("exception", "Couldn't get json from server.");
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (pDialog.isShowing())
                pDialog.dismiss();
            if (result != null) {
                if (MID.equals("0")) {
                    Toast.makeText(context, "Record not saved! Please try again", Toast.LENGTH_SHORT).show();
                } else {
                    // Show MID value as a Toast message
                    Toast.makeText(context, "MID: " + MID, Toast.LENGTH_SHORT).show();

                    final_id = MID;
                    Intent firstpage = new Intent(context, ActionActivity.class);
                    firstpage.putExtra("HCEName", hce_nameText);
                    firstpage.putExtra("HCEAddress", AddressText);
                    firstpage.putExtra("District", districtText);
                    firstpage.putExtra("OrgType", "");
                    firstpage.putExtra("HCSPType", "");
                    firstpage.putExtra("HCSPName", HCSP_nameText);
                    firstpage.putExtra("HCSPSO", HCSP_SOText);
                    firstpage.putExtra("HCSPCNIC", CNIC_Text);
                    firstpage.putExtra("HCSPContactNo", HCSP_ContactText);
                    firstpage.putExtra("RegStatus", "");
                    firstpage.putExtra("RegNum", "");
                    firstpage.putExtra("RegType", "");
                    firstpage.putExtra("total_beds", "");
                    firstpage.putExtra("RecordLockedForUpdate", "");
                    firstpage.putExtra("CouncilStatus", "");
                    firstpage.putExtra("CouncilName", "");
                    firstpage.putExtra("CouncilNum", "");
                    firstpage.putExtra("final_id", final_id);
                    firstpage.putExtra("latitude", cur_latitude);
                    firstpage.putExtra("longitude", cur_longitude);
                    firstpage.putExtra("email", email);
                    firstpage.putExtra("Password", password);
                    firstpage.putExtra("username", username);
                    firstpage.putExtra("isEdit", isEdit);
                    firstpage.putExtra("index", "0");
                    firstpage.putStringArrayListExtra("imageurls", _images);
                    finish();
                    context.startActivity(firstpage);
                }


            } else {
                Toast.makeText(context, "Server error! Please try again", Toast.LENGTH_SHORT).show();
            }


        }

    }

}
